import axios from 'axios'
import errorParser from '@/utils/error-parser'

import type { LoginDetail } from '@/interfaces/UserInterface'

export default {
  /**
   * 로그인 요청에 대한 인증
   * @param - 로그인 정보
   */
  authenticate(detail: LoginDetail) {
    return new Promise((resolve, reject) => {
      axios
        .post('/authentications', detail)
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
