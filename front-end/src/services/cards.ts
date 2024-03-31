import axios from 'axios'
import errorParser from '@/utils/error-parser'

interface Detail {
  boardId: number;
  cardListId: number;
  title: string;
  position: number;
}

interface SavedCard {
  id: number;
  position: number;
  title: string;
}

interface CardPosition {
  cardListId: number;
  cardId: number;
  position: number;
}

interface positionChanges {
  boardId: number;
  cardListPositions: CardPosition[];
}

export default {
  /**
   * 카드 추가
   * @param {*} detail 카드 정보
   */
  add(detail: Detail): Promise<SavedCard> {
    return new Promise((resolve, reject) => {
      axios.post('/cards', detail).then(({ data }) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  },
  /**
   * 카드 포지션 변경
   * 
   * @param positionChanges 
   * @returns 
   */
  changePositions(positionChanges: positionChanges): Promise<any> {
    return new Promise((resolve, reject) => {
      axios.post('/cards/positions', positionChanges).then(({ data }) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}