import { describe, it, expect, beforeEach, afterEach } from 'vitest'
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import registrationService from '@/services/registration'

describe('services/registration', () => {
  let mock: MockAdapter

  const loginDetail = {
    username: 'sunny',
    emailAddress: 'sunny@taskagile.com',
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

    mock.onPost('/registrations').reply(400, { message: 'Bad request' })

    try {
      const response: any = await registrationService.register(loginDetail)
    } catch (error: any) {
      expect(error.response.data.message).toEqual('Bad request')
    }
  })
})
