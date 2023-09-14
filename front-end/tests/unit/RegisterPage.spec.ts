import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import RegisterPage from '@/views/RegisterPage.vue'

describe('RegisterPage', () => {
  it('올바른 콘텐츠를 렌더링해야 합니다.', () => {
    const wrapper = mount(RegisterPage)
    // 이미지 속성 검사
    const logo = wrapper.find('.logo')
    expect(logo.attributes('src')).toEqual('/static/images/logo.png')
    // 텍스트 검사
    const tagline = wrapper.find('.tagline')
    expect(tagline.text()).toEqual('Open source task management tool')
    // 이름 input 요소의 값을 검사
    const usernameInput = wrapper.find('#username')
    expect((usernameInput.element as HTMLInputElement).value).toEqual('')
    // 이메일 input 요소의 값을 검사
    const emailInput = wrapper.find('#emailAddress')
    expect((emailInput.element as HTMLInputElement).value).toEqual('')
    // 비밀번호 input 요소의 값을 검사
    const passwordInput = wrapper.find('#password')
    expect((passwordInput.element as HTMLInputElement).value).toEqual('')
    // 제출 버튼 검사
    const submitButton = wrapper.find('form button[type="submit"]')
    expect(submitButton.text()).toEqual('Create account')
  })
})
