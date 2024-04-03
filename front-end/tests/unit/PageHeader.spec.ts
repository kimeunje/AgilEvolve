import { VueWrapper, mount } from '@vue/test-utils'
import { vi, describe, beforeEach, afterEach, it, expect } from 'vitest'
import { createTestingPinia } from '@pinia/testing'
import { useBoardUserStore } from '@/stores/useBoardUserStore'
import { i18n } from '@/locales'
import router from '@/router'
import PageHeader from '@/components/PageHeader.vue'

import { vuetify } from '@/vuetify'

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

  it('로고를 렌더링합니다', () => {
    expect(wrapper.find('.logo').exists()).toBe(true)
    expect(wrapper.find('.logo img').exists()).toBe(true)
    expect(wrapper.find('.logo .home-icon').exists()).toBe(true)
  })

  it('보드 메뉴에 보드를 렌더링합니다', () => {
    expect(wrapper.find('.boards-menu-toggle').exists()).toBe(true)
    expect(wrapper.find('.dropdown-toggle#boardsMenu').text()).toBe('header.boardsMenu.label')
    // board 가 없을 경우 v-show로 표시하지 않음
    expect(wrapper.findAll('.dropdown-item')[0].isVisible()).toBe(false)
    // board 가 있을 경우 v-show로 표시
    expect(wrapper.findAll('.dropdown-item')[1].text()).toBe('Personal Board')
    expect(wrapper.findAll('.dropdown-item')[2].text()).toBe('Team Board')
  })

  // it('검색창을 렌더링합니다.', () => {
  //   expect(wrapper.find('.search-box').exists()).toBe(true)
  //   expect(wrapper.find('.search-box input').exists()).toBe(true)
  // })

  it('프로필 메뉴를 렌더링합니다', () => {
    expect(wrapper.find('.profile-menu-toggle').exists()).toBe(true)
    expect(wrapper.find('.dropdown-toggle#profileMenu').text()).toBe('John Doe')
    expect(wrapper.findAll('.dropdown-item')[3].text()).toBe('header.profile')
    expect(wrapper.findAll('.dropdown-item')[4].text()).toBe('header.signOut')
  })

  it('로고를 클릭하면 goHome 메서드를 호출합니다', async () => {
    const goHome = vi.spyOn(wrapper.vm, 'goHome')
    await wrapper.find('.logo').trigger('click')
    expect(goHome).toHaveBeenCalled()
  })

  it('보드를 클릭하면 openBoard 메서드를 호출합니다', async () => {
    const openBoard = vi.spyOn(wrapper.vm, 'openBoard')
    await wrapper.findAll('.dropdown-item')[1].trigger('click')
    expect(openBoard).toHaveBeenCalledWith({
      id: 1,
      description: 'Test',
      name: 'Personal Board',
      teamId: 0
    })
    await wrapper.findAll('.dropdown-item')[2].trigger('click')
    expect(openBoard).toHaveBeenCalledWith({
      id: 2,
      description: 'Test',
      name: 'Team Board',
      teamId: 1
    })
  })

  it('로고를 클릭하면 홈 페이지로 이동합니다', async () => {
    const push = vi.spyOn(router, 'push')
    await wrapper.find('.logo').trigger('click')

    expect(push).toHaveBeenCalledTimes(1)
    expect(push).toHaveBeenCalledWith({ name: 'home' })
  })

  it('보드를 클릭하면 보드 페이지로 이동합니다', async () => {
    const push = vi.spyOn(router, 'push')
    await wrapper.findAll('.dropdown-item')[1].trigger('click')

    expect(push).toHaveBeenCalledTimes(1)
    expect(push).toHaveBeenCalledWith({ name: 'board', params: { boardId: 1 } })
  })

  it('마운트될 때 getMyData를 호출합니다', () => {
    expect(store.getMyData).toHaveBeenCalled()
  })
})
