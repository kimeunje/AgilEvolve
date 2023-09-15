import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest'
import { mount, VueWrapper, DOMWrapper } from '@vue/test-utils'
import RegisterPage from '@/views/RegisterPage.vue'

vi.mock('@/services/registration')
describe('RegisterPage', () => {
  let wrapper: VueWrapper<any>
  let fieldUsername: DOMWrapper<Element>
  let fieldEmailAddress: DOMWrapper<Element>
  let fieldPassword: DOMWrapper<Element>
  let buttonSubmit: DOMWrapper<Element>

  const mockRouter = {
    push: vi.fn()
  }

  beforeEach(() => {
    wrapper = mount(RegisterPage, {
      global: {
        mocks: {
          $router: mockRouter
        }
      }
    })

    fieldUsername = wrapper.find('#username')
    fieldEmailAddress = wrapper.find('#emailAddress')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('올바른 콘텐츠를 렌더링해야 합니다.', () => {
    const logo = wrapper.find('.logo')
    expect(logo.attributes('src')).toEqual('/static/images/logo.png')

    const tagline = wrapper.find('.tagline')
    expect(tagline.text()).toEqual('Open source task management tool')

    expect((fieldUsername.element as HTMLInputElement).value).toEqual('')
    expect((fieldEmailAddress.element as HTMLInputElement).value).toEqual('')
    expect((fieldPassword.element as HTMLInputElement).value).toEqual('')

    expect(buttonSubmit.text()).toEqual('Create account')
  })

  it('초기 값으로 데이터 모델을 포함하고 있어야 합니다.', () => {
    const registerData = wrapper.vm.$data

    expect(registerData.form.username).toEqual('')
    expect(registerData.form.emailAddress).toEqual('')
    expect(registerData.form.password).toEqual('')
  })

  it('폼 입력과 데이터 모델 바인딩이 되어야 합니다.', () => {
    const username = 'sunny'
    const emailAddress = 'sunny@local'
    const password = 'VueJsRocks!'

    const registerData = wrapper.vm.$data

    registerData.form.username = username
    registerData.form.emailAddress = emailAddress
    registerData.form.password = password

    wrapper.vm.$nextTick(null, () => {
      expect((fieldUsername.element as HTMLInputElement).value).toEqual(username)
      expect((fieldEmailAddress.element as HTMLInputElement).value).toEqual(emailAddress)
      expect((fieldPassword.element as HTMLInputElement).value).toEqual(password)
    })
  })

  it('폼 안에 `submitForm` 이벤트 핸들러가 있어야 합니다.', () => {
    const submitFormSpy = vi.spyOn(wrapper.vm, 'submitForm')

    buttonSubmit.trigger('submit')
    expect(submitFormSpy).toHaveBeenCalled()
  })

  it('새로운 유저가 회원가입을 할 수 있어야 합니다.', () => {
    const submitFormSpy = vi.fn()
    const registerData = wrapper.vm.$data

    registerData.form.username = 'sunny'
    registerData.form.emailAddress = 'sunny@local'
    registerData.form.password = 'VueJsRocks!'

    wrapper.vm.$router.push = submitFormSpy

    wrapper.vm.submitForm()
    wrapper.vm.$nextTick(null, () => {
      expect(submitFormSpy).toHaveBeenCalledWith({ name: 'LoginPage' })
    })
  })

  it('기존 유저가 회원가입을 할 수 없어야 합니다.', () => {
    const registerData = wrapper.vm.$data

    registerData.form.emailAddress = 'ted@local'

    expect(wrapper.find('.failed').isVisible()).toBe(false)

    wrapper.vm.submitForm()
    wrapper.vm.$nextTick(null, () => {
      expect(wrapper.find('.failed').isVisible()).toBe(true)
    })
  })
})
