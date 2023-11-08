export default {
  getMyData() {
    return new Promise((resolve, reject) => {
      try {
        resolve({
          user: {
            name: 'raccoon'
          },
          teams: [{ id: 1, name: 'Sales & Marketing' }],
          boards: [
            {
              id: 2,
              name: '2023 Team Planning',
              description: '2018 sales & marketing planning',
              teamId: 1
            },
            {
              id: 3,
              name: 'Ongoing Campaigns',
              description: '2018 ongoing marketing campaigns',
              teamId: 1
            }
          ]
        })
      } catch (error) {
        reject(new Error('에러가 발생했습니다.'))
      }
    })
  }
}
