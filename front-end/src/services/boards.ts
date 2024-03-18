import axios from 'axios'
import errorParser from '@/utils/error-parser'

import type { Board, BoardDetail, GetBoardRes } from '@/interfaces/BoardInterface'

export default {
  /**
   * 새로운 보드 생성하기
   * @param - 보드의 세부사항
   */
  create(detail: BoardDetail): Promise<Board> {
    return new Promise((resolve, reject) => {
      axios
        .post('/boards', detail)
        .then(({ data }) => {
          resolve(data)
        })
        .catch((error) => {
          if (axios.isAxiosError(error)) {
            reject(errorParser.parse(error))
          }
        })
    })
  },
  /**
   * 보드 및 보드에 소속되어 있는 모든 것을 조회
   * @param {*} boardId 보드 id
   */
  getBoard(boardId: number): Promise<GetBoardRes> {
    return new Promise((resolve, reject) => {
      axios.get('/boards/' + boardId).then(({ data }) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}
