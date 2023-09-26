import _ from 'lodash'

export default {
  parse (error: any) {
    if (error.response) {
      const status = error.response.status
      const data = error.response.data
      if (status === 400) {
        if (data && data.message) {
          return new Error(data.message)
        } else {
          return new Error('잘못된 요청입니다.')
        }
      } else if (status === 401) {
        return new Error('요청이 승인되지 않았습니다.')
      } else if (status === 403) {
        return new Error('요청이 금지되었습니다.')
      } else if (status === 404) {
        return new Error('요청에 실패했습니다. 서버에서 요청 엔드포인트를 찾을 수 없습니다.')
      } else if (status === 500) {
        if (data && data.message) {
          return new Error(data.message + ' 나중에 다시 시도해 주세요.')
        } else {
          return new Error('서버 측에 오류가 발생했습니다. 나중에 다시 시도해 주세요.')
        }
      } else {
        return new Error('요청이 실패했습니다. 나중에 다시 시도해 주세요.')
      }
    } else if (error.request) {
      return new Error('요청이 실패했습니다. 서버에서 응답이 없습니다.')
    } else {
      return _.isError(error) ? error : new Error(error)
    }
  }
}
