import { defineStore } from 'pinia'
import meService from '@/services/me'

interface State {
  user: User
  teams: Team[]
  boards: Board[]
}

interface User {
  name: string | null
}

interface Team {
  // id: number | null
  // name: string | null
}

interface Board {
  // id: number | null
  // name: string | null
  // description: string | null
  // teamId: number | null
}

export const useBoardUserStore = defineStore('boardUser', {
  state: (): State => ({
    user: {
      name: null
    },
    teams: [
      /* {id, name} */
    ],
    boards: [
      /* {id, name, description, teamId} */
    ]
  }),
  getters: {
    user: (state) => {
      return { name: 'k.eunseo' }
    },
    hasBoards: (state) => {
      return true
    },
    personalBoards: (state) => {
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
    // async getMyData() {
    //   const res = await meService.getMyData()
    //   this.updateMyData(res as State)
    // },
    // updateMyData(data: { user: { name: any }; teams: any; boards: any }) {
    //   this.user.name = data.user.name
    //   this.teams = data.teams
    //   this.boards = data.boards
    // },
    addTeam(team: any) {
      this.teams.push(team)
    },
    addBoard(board: any) {
      this.boards.push(board)
    }
  }
})
