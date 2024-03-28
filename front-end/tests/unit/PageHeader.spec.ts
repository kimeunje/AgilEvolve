import { VueWrapper, mount } from '@vue/test-utils'
import { vi, describe, beforeEach, afterEach, it, expect } from 'vitest'
import { createTestingPinia } from '@pinia/testing'
import { useBoardUserStore } from '@/stores/useBoardUserStore'
import { i18n } from '@/locales'
import router from '@/router'
import PageHeader from '@/components/PageHeader.vue'

import { vuetify } from '@/vuetify'


global.ResizeObserver = require('resize-observer-polyfill')

vi.mock('@/services/me')
describe('PageHeader', () => {
  let wrapper: VueWrapper<any>
  let store: ReturnType<typeof useBoardUserStore>



  beforeEach(() => {
    wrapper = mount(PageHeader, {
      global: {
        plugins: [createTestingPinia({ createSpy: vi.fn, stubActions: true }), i18n, router, vuetify],
        mocks: {
          t: (msg: string) => msg
        },
        stubs: { FontAwesomeIcon: true }
      }
    })
    store = useBoardUserStore()

    // pinia store 값 설정
    store.$patch({ user: { name: 'John Doe' } })
    store.$patch({ teams: [{ id: 1, name: 'Team 1' }] })
    store.$patch({
      boards: [
        { teamId: 0, id: 1, name: 'Personal Board', description: 'Test' },
        { teamId: 1, id: 2, name: 'Team Board', description: 'Test' }
      ]
    })
  })

  afterEach(() => {
    wrapper.unmount()
  })

  it('로고를 클릭하면 goHome 메서드를 호출합니다', async () => {
    const goHome = vi.spyOn(wrapper.vm, 'goHome')
    await wrapper.find('.logo').trigger('click')
    expect(goHome).toHaveBeenCalled()
  })

  it('보드를 클릭하면 openBoard 메서드를 호출합니다', async () => {

  })

  it('로고를 클릭하면 홈 페이지로 이동합니다', async () => {
    const push = vi.spyOn(router, 'push')
    await wrapper.find('.logo').trigger('click')

    expect(push).toHaveBeenCalledTimes(1)
    expect(push).toHaveBeenCalledWith({ name: 'home' })
  })

  it('보드를 클릭하면 보드 페이지로 이동합니다', async () => {

  })

  it('마운트될 때 getMyData를 호출합니다', () => {
    expect(store.getMyData).toHaveBeenCalled()
  })
})
