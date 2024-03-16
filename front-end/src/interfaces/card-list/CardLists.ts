export interface CardList {
    id: number;
    name: string;
    cards: string[];
    cardForm: {
        open: boolean;
        name: string;
    };
}