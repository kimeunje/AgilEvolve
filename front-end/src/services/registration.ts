import axios from 'axios'
import errorParser from '@/utils/error-parser'

import type { RegisterDetail } from '@/interfaces/UserInterface'

export default {
  register(detail: RegisterDetail) {
    return new Promise((resolve, reject) => {
      axios
        .post('/registrations', detail)
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
