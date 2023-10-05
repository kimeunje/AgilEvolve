import axios from 'axios'
import errorParser from '@/utils/error-parser'

export default {
  /**
   * 팀 생성하기
   * @param - 팀의 세부사항
   */
  create(detail: any) {
    return new Promise((resolve, reject) => {
      axios
        .post('/teams', detail)
        .then(({ data }) => {
          resolve(data)
        })
        .catch((error) => {
          reject(errorParser.parse(error))
        })
    })
  }
}
