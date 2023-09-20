import axios from 'axios'

export default {
  register(detail: { username: string; emailAddress: string; password: string }) {
    return new Promise((resolve, reject) => {
      axios
        .post('/registrations', detail)
        .then(({ data }) => {
          resolve(data)
        })
        .catch((error) => {
          reject(error)
        })
    })
  }
}
