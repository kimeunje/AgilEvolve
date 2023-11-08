import axios from 'axios'
import errorParser from '@/utils/error-parser'

import type { Team, TeamDetail } from '@/interfaces/TeamInterface'

export default {
  /**
   * 팀 생성하기
   * @param - 팀의 세부사항
   */
  create(detail: TeamDetail): Promise<Team> {
    return new Promise((resolve, reject) => {
      axios
        .post('/teams', detail)
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
