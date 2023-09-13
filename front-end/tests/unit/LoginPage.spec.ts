import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import LoginPage from '@/views/LoginPage.vue'

describe('LoginPage', () => {
  it('올바른 콘텐츠를 렌더링해야 합니다.', () => {
    const wrapper = mount(LoginPage)
    expect(wrapper.find('div').text()).toEqual('TaskAgile')
  })
})
