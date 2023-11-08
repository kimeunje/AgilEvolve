import axios from 'axios'
import errorParser from '@/utils/error-parser'

import type { UserData } from '@/interfaces/UserInterface'

export default {
  /**
   * 현재 사용자의 이름, 보드, 팀 조회하기
   */
  getMyData(): Promise<UserData> {
    return new Promise((resolve, reject) => {
      axios
        .post('/me')
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
