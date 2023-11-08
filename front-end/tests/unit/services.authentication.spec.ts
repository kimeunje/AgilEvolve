import { describe, it, expect, beforeEach, afterEach } from 'vitest'
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import authenticationService from '@/services/authentication'

describe('services/registration', () => {
  let mock: MockAdapter

  const loginDetail = {
    username: 'sunny',
    password: 'MyPassword123!'
  }

  beforeEach(() => {
    mock = new MockAdapter(axios)
  })

  afterEach(() => {
    mock.restore()
  })

  it('`/authentications` API를 호출해야 한다.', () => {
    mock.onPost('/authentications').reply(200, { result: 'success' })
    return authenticationService.authenticate(loginDetail)
  })

  it('요청이 성공하면 호출한 곳에 응답해야 한다.', async () => {
    expect.assertions(1)

    mock.onPost('/authentications').reply(200, { result: 'success' })

    try {
      const response: any = await authenticationService.authenticate(loginDetail)
      expect(response.result).toEqual('success')
    } catch (error) {
      throw error
    }
  })

  it('요청이 실패하면 호출한 곳에 에러를 전파해야 한다.', async () => {
    expect.assertions(1)

    mock.onPost('/authentications').reply(400, { data: { message: 'Bad request' } })

    try {
      const response = await authenticationService.authenticate(loginDetail)
      // 의미없는 변수를 나타내기 위해 _를 사용합니다.
      const _ = response
    } catch (error: any) {
      expect(error.message).toEqual('잘못된 요청입니다.')
    }
  })
})
