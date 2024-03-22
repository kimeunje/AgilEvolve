import { describe, it, expect, beforeEach, afterEach } from 'vitest'
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import registrationService from '@/services/registration'

describe('services/registration', () => {
  let mock: MockAdapter

  const loginDetail = {
    username: 'sunny',
    emailAddress: 'sunny@agilevolve.com',
    password: 'MyPassword123!'
  }

  beforeEach(() => {
    mock = new MockAdapter(axios)
  })

  afterEach(() => {
    mock.restore()
  })

  it('`/registrations` API를 호출해야 합니다.', () => {
    mock.onPost('/registrations').reply(200, { result: 'success' })
    return registrationService.register(loginDetail)
  })

  it('요청이 성공하면 회원가입해야 합니다.', async () => {
    expect.assertions(1)

    mock.onPost('/registrations').reply(200, { result: 'success' })

    try {
      const response: any = await registrationService.register(loginDetail)
      expect(response.result).toEqual('success')
    } catch (error) {
      throw error
    }
  })

  it('요청이 실패하면 회원가입에 실패해야 합니다.', async () => {
    expect.assertions(1)

    mock.onPost('/registrations').reply(400, { data: { message: 'Bad request' } })

    try {
      const response = await registrationService.register(loginDetail)
      // 의미없는 변수를 나타내기 위해 _를 사용합니다.
      const _ = response
    } catch (error: any) {
      expect(error.message).toEqual('잘못된 요청입니다.')
    }
  })
})
