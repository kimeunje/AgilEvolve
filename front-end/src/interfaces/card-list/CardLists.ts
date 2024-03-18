export interface CardList {
    id: number,
    name: string,
    cards: Array<{
        id: number,
        title: string,
        position: number
    }>,
    cardForm: {
        open: boolean,
        title: string
    }
}