export default {
  authenticate(detail: { username: string; emailAddress: string; password: string }) {
    return new Promise((resolve, reject) => {
      ;(detail.username === 'sunny' || detail.emailAddress === 'sunny@taskagile.com') &&
      detail.password === 'MyPassword123!'
        ? resolve({ result: 'success' })
        : reject(new Error('유효하지 않은 자격입니다.'))
    })
  }
}
