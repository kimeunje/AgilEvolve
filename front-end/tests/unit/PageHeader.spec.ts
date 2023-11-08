import { describe, it, expect, beforeEach, afterEach, afterAll, vi, type SpyInstance } from 'vitest'
import { mount, VueWrapper, flushPromises, DOMWrapper } from '@vue/test-utils'
import { createRouter, createWebHistory } from 'vue-router'
import { createTestingPinia } from '@pinia/testing'

import meService from '@/services/me'
import PageHeader from '@/components/PageHeader.vue'
import { useBoardUserStore } from '@/stores/useBoardUserStore'

vi.mock('@/services/me')
describe('PageHeader', () => {
  let wrapper: VueWrapper<any>
  let fieldProfileName: DOMWrapper<Element>
  let getMyDataSpy: SpyInstance

  let store: ReturnType<typeof useBoardUserStore>

  beforeEach(() => {
    const mockRouter = createRouter({
      history: createWebHistory(),
      routes: []
    })

    getMyDataSpy = vi.spyOn(meService, 'getMyData')

    wrapper = mount(PageHeader, {
      global: {
        plugins: [createTestingPinia({ createSpy: vi.fn, stubActions: false }), mockRouter],
        mocks: {
          $router: mockRouter
        }
      }
    })

    store = useBoardUserStore()

    fieldProfileName = wrapper.find('#profileMenu')
  })

  afterEach(() => {
    getMyDataSpy.mockReset()
    getMyDataSpy.mockRestore()
    wrapper.unmount()
  })

  afterAll(() => {
    vi.restoreAllMocks()
  })

  it('pinia 스토어가 바인딩 돼야 합니다.', async () => {
    // pinia 게터 바인딩 확인
    const profile = store.getUser
    expect(profile.name).toEqual('raccoon')
  })

  it('페이지 헤더 로딩 후 유저 정보를 불러와야 합니다.', async () => {
    const profile = 'raccoon'
    const teamBoard = ['2023 Team Planning', 'Ongoing Campaigns']

    // render 이후에 실행되는 mounted hook을 실행
    expect(store.getMyData).toHaveBeenCalledOnce()
    expect(getMyDataSpy).toBeCalled()

    expect(fieldProfileName.text()).toEqual(profile)
    expect(
      wrapper.findAll('.team-board').forEach((board, i) => {
        board.text() === teamBoard[i]
      })
    )
  })

  it('보드를 클릭하면 보드 상세 페이지로 가져야 합니다.', async () => {
    const openBoardFn = vi.fn()
    // 첫번째 팀 보드를 가져옴
    const board = store.teamBoards[0]
    const openBoardButton = wrapper.find('button.team-board')

    wrapper.vm.$router.push = openBoardFn
    wrapper.vm.openBoard(board)

    openBoardButton.trigger('click')
    expect(openBoardFn).toHaveBeenCalledWith({ name: 'board', params: { boardId: 2 } })
  })

  it('보드에 정보가 없을 시 보드를 불러 올 수 없어야 합니다.', async () => {
    store.$patch({
      boards: []
    })
    await flushPromises()

    expect(wrapper.find('.no-board').isVisible()).toBe(true)
  })

  it('보드에 teamId가 0일 경우 개인 보드를 불러와야 합니다.', async () => {
    store.$patch({
      boards: [
        {
          teamId: 0,
          id: 1,
          name: '2023 개인 계획',
          description: '2023 영업 및 마케팅 계획'
        }
      ]
    })
    await flushPromises()

    expect(wrapper.find('.personal-board').isVisible()).toBe(true)
  })

  it('보드에 teamId가 0 이외일 경우 팀 보드를 불러와야 합니다.', async () => {
    store.$patch({
      boards: [
        {
          teamId: 1,
          id: 2,
          name: '2018 Planning',
          description: '2018 sales & marketing planning'
        },
        {
          teamId: 1,
          id: 3,
          name: 'Ongoing Campaigns',
          description: '2018 ongoing marketing campaigns'
        }
      ]
    })
    store.$patch({
      teams: [
        {
          id: 1,
          name: 'Sales & Marketing'
        }
      ]
    })
    await flushPromises()

    expect(wrapper.find('.team-board').isVisible()).toBe(true)
  })
})
