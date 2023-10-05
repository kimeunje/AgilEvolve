import axios from 'axios'
import errorParser from '@/utils/error-parser'

export default {
  /**
   * 새로운 보드 생성하기
   * @param - 보드의 세부사항
   */
  create(detail: any) {
    return new Promise((resolve, reject) => {
      axios
        .post('/boards', detail)
        .then(({ data }) => {
          resolve(data)
        })
        .catch((error) => {
          reject(errorParser.parse(error))
        })
    })
  }
}
