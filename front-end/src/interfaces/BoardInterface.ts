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

export interface GetBoardRes {
  team: {
    name: string
  },
  board: {
    name: string,
    personal: boolean
    id: number
  },
  members: [{
    userId: number,
    shortName: string
  }],
  cardLists: [{
    id: number,
    name: string,
    position: number,
    archived: boolean,
    cards: [{
      id: number,
      title: string,
      position: number
    }]
  }]
}
