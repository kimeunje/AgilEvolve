import axios from 'axios'
import errorParser from '@/utils/error-parser'

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
    /**
     * 카드 리스트 추가
     * @param {*} detail 카드 리스트 정보
     */
    add(detail: Detail): Promise<SavedCardList> {
        return new Promise((resolve, reject) => {
            axios.post('/card-lists', detail).then(({ data }) => {
                resolve(data)
            }).catch((error) => {
                reject(errorParser.parse(error))
            })
        })
    },
}