import { describe, it, expect, beforeEach, vi } from 'vitest'
import { mount, VueWrapper } from '@vue/test-utils'
import HomePage from '@/views/HomePage.vue'
import { useBoardUserStore } from '@/stores/useBoardUserStore'
import { createTestingPinia } from '@pinia/testing'
import { i18n } from '@/locales'
import router from '@/router'

describe('HomePage', () => {
  let wrapper: VueWrapper<any>
  let store: ReturnType<typeof useBoardUserStore>

  beforeEach(() => {
    wrapper = mount(HomePage, {
      global: {
        plugins: [createTestingPinia({ createSpy: vi.fn, stubActions: true }), i18n, router],
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

  it('클릭 시 보드를 열어야 합니다', () => {
    const board = { id: 1 }
    const push = vi.spyOn(router, 'push')

    wrapper.vm.openBoard(board)

    expect(push).toHaveBeenCalledWith({ name: 'board', params: { boardId: board.id } })
  })

  it('팀과 함께 보드를 생성해야 합니다', () => {
    const team = { id: 1 }
    const boardModalShowMock = vi.fn()
    wrapper.vm.boardModalObj.value = { show: boardModalShowMock }

    wrapper.vm.createBoard(team)

    expect(wrapper.vm.selectedTeamId).toBe(team.id)
    expect(wrapper.vm.boardModalObj.value).toEqual({ show: boardModalShowMock })
  })

  it('팀 없이 보드를 생성해야 합니다', () => {
    const boardModalShowMock = vi.fn()
    wrapper.vm.boardModalObj.value = { show: boardModalShowMock }

    wrapper.vm.createBoard(null)

    expect(wrapper.vm.selectedTeamId).toBe(0)
    expect(wrapper.vm.boardModalObj.value).toEqual({ show: boardModalShowMock })
  })

  it('팀을 생성해야 합니다', () => {
    const teamModalShowMock = vi.fn()
    wrapper.vm.teamModalObj.value = { show: teamModalShowMock }

    wrapper.vm.createTeam()

    expect(wrapper.vm.teamModalObj.value).toEqual({ show: teamModalShowMock })
  })

  it('boardModalClose가 호출되면 보드 모달이 닫혀야 합니다', async () => {
    const teamModalHideMock = vi.fn()
    wrapper.vm.teamModalObj.value = { hide: teamModalHideMock }

    await wrapper.vm.createBoard(null)
    wrapper.vm.boardModalClose()

    expect(wrapper.vm.teamModalObj.value).toEqual({ hide: teamModalHideMock })
  })
})
