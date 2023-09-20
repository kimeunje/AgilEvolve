export default {
  register(detail: { emailAddress: string }) {
    return new Promise((resolve, reject) => {
      detail.emailAddress === 'sunny@taskagile.com'
        ? resolve({ result: 'success' })
        : reject(new Error('User already exist'))
    })
  }
}
