import { defineStore } from 'pinia'
import meService from '@/services/me'
import type { Team } from '@/interfaces/TeamInterface'
import type { Board } from '@/interfaces/BoardInterface'
import type { User } from '@/interfaces/UserInterface'

interface State {
  user: User
  teams: Team[]
  boards: Board[]
}

export const useBoardUserStore = defineStore('boardUser', {
  state: (): State => {
    return {
      user: { name: '' },
      teams: [],
      boards: []
    }
  },
  getters: {
    getUser: (state) => {
      return state.user
    },
    hasBoards: (state) => {
      return state.boards.length > 0
    },
    personalBoards: (state) => {
      return state.boards.filter((board: Board) => board.teamId === 0)
    },
    teamBoards: (state) => {
      const teams: Team[] = []

      state.teams.forEach((team: Team) => {
        teams.push({
          id: team.id,
          name: team.name,
          boards: state.boards.filter((board: Board) => board.teamId === team.id)
        })
      })

      return teams
    }
  },
  actions: {
    async getMyData(): Promise<void> {
      const res: State = await meService.getMyData()
      this.updateMyData(res)
    },
    updateMyData(res: State) {
      this.$patch({ user: res.user, teams: res.teams, boards: res.boards })
    },
    addTeam(team: Team) {
      this.teams.push(team)
    },
    addBoard(board: Board) {
      this.boards.push(board)
    }
  }
})
