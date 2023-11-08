import type { Board } from './BoardInterface'

export interface Team {
  id: number
  name: string
  // pinia teamBoards 게터에서만 사용하는 필드
  boards: Board[]
}

export interface TeamDetail {
  name: string
}
