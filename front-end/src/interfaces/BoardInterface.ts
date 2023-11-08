export interface Board {
  teamId: number
  id: number
  name: string
  description: string
}

export interface BoardDetail {
  teamId: number | undefined
  name: string
  description: string
}
