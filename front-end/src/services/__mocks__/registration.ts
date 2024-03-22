export default {
  register(detail: { emailAddress: string, password: string }) {
    return new Promise((resolve, reject) => {
      detail.emailAddress === 'sunny@agilevolve.com' &&
        detail.password === '@MyPassword123'
        ? resolve({ result: 'success' })
        : reject(new Error('User already exist'))
    })
  }
}
