export interface CardList {
    id: number,
    name: string,
    cards: Array<{
        id: number,
        title: string,
        position: number
    }>,
    archived: boolean,
    cardForm: {
        open: boolean,
        title: string
    },
}
