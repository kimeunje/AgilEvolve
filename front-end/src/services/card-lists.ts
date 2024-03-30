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

interface CardListPosition {
  cardListId: number;
  position: number;
}

interface positionChanges {
  boardId: number;
  cardListPositions: CardListPosition[];
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
  /**
   * 카드 리스트 포지션 변경
   * 
   * @param positionChanges 
   * @returns 
   */
  changePositions(positionChanges: positionChanges): Promise<any> {
    return new Promise((resolve, reject) => {
      axios.post('/card-lists/positions', positionChanges).then(({ data }) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}