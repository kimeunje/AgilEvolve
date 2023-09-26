import { describe, it, expect, beforeEach, afterEach, afterAll, vi, type SpyInstance } from 'vitest'
import { mount, VueWrapper, DOMWrapper, flushPromises } from '@vue/test-utils'
import LoginPage from '@/views/LoginPage.vue'
import authenticationService from '@/services/authentication'

vi.mock('@/services/authentication')
describe('LoginPage', () => {
  let wrapper: VueWrapper<any>
  let fieldUsername: DOMWrapper<Element>
  let fieldPassword: DOMWrapper<Element>
  let buttonSubmit: DOMWrapper<Element>
  let authenticateSpy: SpyInstance

  beforeEach(() => {
    const mockRouter = {
      push: vi.fn()
    }

    authenticateSpy = vi.spyOn(authenticationService, 'authenticate')

    wrapper = mount(LoginPage, {
      props: {
        isAuthenticated: true
      },
      global: {
        mocks: {
          $router: mockRouter
        }
      }
    })

    fieldUsername = wrapper.find('#username')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
  })

  afterEach(() => {
    authenticateSpy.mockReset()
    authenticateSpy.mockRestore()
  })

  afterAll(() => {
    vi.restoreAllMocks()
  })

  it('로그인 폼을 렌더링해야 합니다.', () => {
    expect(wrapper.find('.logo').attributes().src).toEqual('/static/images/logo.png')
    expect(wrapper.find('.tagline').text()).toEqual('Open source task management tool')
    expect((fieldUsername.element as HTMLInputElement).value).toEqual('')
    expect((fieldPassword.element as HTMLInputElement).value).toEqual('')
    expect(buttonSubmit.text()).toEqual('Sign in')
    expect(wrapper.find('.link-sign-up').attributes().href).toEqual('/register')
    expect(wrapper.find('.link-forgot-password')).toBeTruthy()
  })

  it('초깃갑을 갖는 데이터 모델을 포함해야 합니다.', () => {
    const loginData = wrapper.vm.$data

    expect(loginData.form.username).toEqual('')
    expect(loginData.form.password).toEqual('')
  })

  it('데이터 모델과 연결된 폼 입력을 가져야 합니다.', async () => {
    const username = 'sunny'
    const password = 'MyPassword123!'

    const loginData = wrapper.vm.$data

    loginData.form.username = username
    loginData.form.password = password

    await flushPromises()
    expect((fieldUsername.element as HTMLInputElement).value).toEqual(username)
    expect((fieldPassword.element as HTMLInputElement).value).toEqual(password)
  })

  it('submitForm 폼 제출 핸들러를 가져야 합니다.', () => {
    const submitFormSpy = vi.spyOn(wrapper.vm, 'submitForm')

    buttonSubmit.trigger('submit')
    expect(submitFormSpy).toHaveBeenCalled()
  })

  it('자격이 유효하면 성공해야 합니다.', async () => {
    const submitFormFn = vi.fn()
    const loginData = wrapper.vm.$data

    wrapper.vm.$router.push = submitFormFn

    loginData.form.username = 'sunny'
    loginData.form.password = 'MyPassword123!'

    wrapper.vm.submitForm()
    expect(authenticateSpy).toBeCalled()
    await flushPromises()
    expect(submitFormFn).toHaveBeenCalledWith({ name: 'home' })
  })

  it('자격이 유효하지 않으면 실패해야 합니다.', async () => {
    const loginData = wrapper.vm.$data

    // mock에서, 비밀번호는 `MyPassword123!` 와 일치해야 합니다.
    // 또한, 사용자명 `sunny` 또는 `sunny@taskagile.com` 만 유효합니다.
    loginData.form.username = 'sunny'
    loginData.form.password = 'BadPassword'

    expect(wrapper.find('.failed').exists()).toBe(false)
    wrapper.vm.submitForm()
    expect(authenticateSpy).toBeCalled()
    await flushPromises()
    expect(wrapper.find('.failed').exists()).toBe(true)
  })

  it('유효하지 않은 데이터 제출을 방지하기 위한 검증이 존재해야 합니다.', () => {
    const loginData = wrapper.vm.$data

    wrapper.vm.submitForm()
    expect(authenticateSpy).not.toHaveBeenCalled()

    // 사용자명만 유효합니다.
    loginData.form.username = 'sunny'
    wrapper.vm.submitForm()
    expect(authenticateSpy).not.toHaveBeenCalled()

    // 이메일만 유효합니다.
    loginData.form.username = 'sunny@taskagile.com'
    wrapper.vm.submitForm()
    expect(authenticateSpy).not.toHaveBeenCalled()

    // 비밀번호만 유효합니다.
    loginData.form.password = 'MyPassword123!'
    loginData.form.username = ''
    wrapper.vm.submitForm()
    expect(authenticateSpy).not.toHaveBeenCalled()
  })
})
