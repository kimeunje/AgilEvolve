interface Detail {
    boardId: number;
    name: string;
    position: number;
}

interface SavedCardList {
    id: number;
    name: string;
}

export default {
    add(detail: Detail): Promise<SavedCardList> {
        return new Promise((resolve, reject) => {
            try {
                resolve({ id: 1, name: 'New List' })
            } catch (error) {
                reject(new Error('에러가 발생했습니다.'))
            }
        })
    }
}
