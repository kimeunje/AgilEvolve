import axios from 'axios'
import errorParser from '@/utils/error-parser'

import type { Board, BoardDetail } from '@/interfaces/BoardInterface'

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
  }
}
