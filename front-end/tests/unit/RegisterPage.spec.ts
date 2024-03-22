import { describe, it, expect, beforeEach, afterEach, afterAll, vi, type SpyInstance } from 'vitest'
import { mount, VueWrapper, DOMWrapper, flushPromises, config } from '@vue/test-utils'

import RegisterPage from '@/views/RegisterPage.vue'
import registrationService from '@/services/registration'
import { i18n } from '@/locales'
import router from '@/router'

vi.mock('@/services/registration')
describe('RegisterPage', () => {
  let wrapper: VueWrapper<any>
  let fieldUsername: DOMWrapper<Element>
  let fieldEmailAddress: DOMWrapper<Element>
  let fieldPassword: DOMWrapper<Element>
  let buttonSubmit: DOMWrapper<Element>
  let registerSpy: SpyInstance

  beforeEach(() => {
    registerSpy = vi.spyOn(registrationService, 'register')

    wrapper = mount(RegisterPage, {
      global: {
        plugins: [i18n, router],
        mocks: {
          t: (tKey: string) => tKey
        },
        stubs: ['router-link', 'router-view']
      }
    })

    fieldUsername = wrapper.find('#username')
    fieldEmailAddress = wrapper.find('#emailAddress')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
  })

  afterEach(() => {
    registerSpy.mockReset()
    registerSpy.mockRestore()
  })

  afterAll(() => {
    vi.restoreAllMocks()
  })

  it('올바른 콘텐츠를 렌더링해야 합니다.', () => {
    expect(wrapper.find('.logo').attributes('src')).toEqual('/images/logo.png')
    expect(wrapper.find('.tagline').text()).toEqual('logo.tagLine')
    expect((fieldUsername.element as HTMLInputElement).value).toEqual('')
    expect((fieldEmailAddress.element as HTMLInputElement).value).toEqual('')
    expect((fieldPassword.element as HTMLInputElement).value).toEqual('')

    expect(buttonSubmit.text()).toEqual('registerPage.form.submit')
  })

  it('초기 값으로 데이터 모델을 포함하고 있어야 합니다.', async () => {
    const registerData = wrapper.vm
    expect(registerData.form.username).toEqual('')
    expect(registerData.form.emailAddress).toEqual('')
    expect(registerData.form.password).toEqual('')
  })

  it('폼 입력과 데이터 모델 바인딩이 되어야 합니다.', async () => {
    const username = 'sunny'
    const emailAddress = 'sunny@local'
    const password = 'VueJsRocks!'

    const registerData = wrapper.vm

    registerData.form.username = username
    registerData.form.emailAddress = emailAddress
    registerData.form.password = password

    await flushPromises()
    expect((fieldUsername.element as HTMLInputElement).value).toEqual(username)
    expect((fieldEmailAddress.element as HTMLInputElement).value).toEqual(emailAddress)
    expect((fieldPassword.element as HTMLInputElement).value).toEqual(password)
  })

  it('폼 안에 `submitForm` 이벤트 핸들러가 있어야 합니다.', async () => {
    const submitFormSpy = vi.spyOn(wrapper.vm.registerService, 'submitForm')

    buttonSubmit.trigger('submit')
    expect(submitFormSpy).toHaveBeenCalled()
  })

  it('신규 사용자일 때 회원가입에 성공해야 합니다.', async () => {
    expect.assertions(2)
    const submitFormSpy = vi.fn()
    const registerData = wrapper.vm

    router.push = submitFormSpy

    registerData.form.username = 'sunny'
    registerData.form.emailAddress = 'sunny@taskagile.com'
    registerData.form.password = '@MyPassword123'

    wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()
    await flushPromises()
    expect(submitFormSpy).toHaveBeenCalledWith({ name: 'login' })
  })

  it('기존 사용자면 회원가입에 실패해야 합니다.', async () => {
    expect.assertions(3)

    const RegisterData = wrapper.vm
    RegisterData.form.username = 'ted'
    RegisterData.form.emailAddress = 'ted@taskagile.com'
    RegisterData.form.password = 'JestRocks!'

    expect(wrapper.find('.failed').exists()).toBe(false)
    wrapper.vm.submitForm()
    expect(registerSpy).toBeCalled()
    await flushPromises()
    expect(wrapper.find('.failed').exists()).toBe(true)
  })

  it('이메일 주소가 유효하지 않을 경우 회원가입을 할 수 없어야 합니다.', () => {
    const RegisterData = wrapper.vm
    RegisterData.form.username = 'test'
    RegisterData.form.emailAddress = 'test@taskagile'
    RegisterData.form.password = 'abcdefg!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })

  it('아이디가 유효하지 않을 경우 회원가입을 할 수 없어야 합니다.', () => {
    const RegisterData = wrapper.vm
    RegisterData.form.username = 'a'
    RegisterData.form.emailAddress = 'test@taskagile.com'
    RegisterData.form.password = 'abcdefg!'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })

  it('비밀번호가 유효하지 않을 경우 회원가입을 할 수 없어야 합니다.', () => {
    const RegisterData = wrapper.vm
    RegisterData.form.username = 'test'
    RegisterData.form.emailAddress = 'test@taskagile.com'
    RegisterData.form.password = 'abc3333'
    wrapper.vm.submitForm()
    expect(registerSpy).not.toHaveBeenCalled()
  })
})
