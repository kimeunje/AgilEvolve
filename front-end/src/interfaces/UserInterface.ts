import type { Team } from '@/interfaces/TeamInterface'
import type { Board } from '@/interfaces/BoardInterface'

export interface UserData {
  user: User
  teams: Team[]
  boards: Board[]
}

export interface User {
  name: string
}

export interface LoginDetail {
  username: string
  password: string
}

export interface RegisterDetail {
  username: string
  emailAddress: string
  password: string
}
