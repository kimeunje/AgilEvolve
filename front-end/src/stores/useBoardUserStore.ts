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
      // return state.user
      return { name: 'raccoon' }
    },
    hasBoards: (state) => {
      // return state.boards.length > 0
      return true
    },
    personalBoards: (state) => {
      // return state.boards.filter((board: Board) => board.teamId === 0)
      return [
        {
          id: 1,
          name: 'vuejs.spring-boot.mariadb',
          description:
            'An implementation of TaskAgile application with Vue.js, Spring Boot, and mariadb'
        }
      ]
    },
    teamBoards: (state) => {
      // const teams: Team[] = []

      // state.teams.forEach((team: Team) => {
      //   teams.push({
      //     id: team.id,
      //     name: team.name,
      //     boards: state.boards.filter((board: Board) => board.teamId === team.id)
      //   })
      // })

      // return teams
      return [
        {
          id: 1,
          name: 'Sales & Marketing',
          boards: [
            {
              id: 2,
              name: '2023 Planning',
              description: '2023 sales & marketing planning'
            },
            {
              id: 3,
              name: 'Ongoing Campaigns',
              description: '2023 ongoing marketing campaigns'
            }
          ]
        }
      ]
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
