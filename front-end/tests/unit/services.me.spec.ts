import { describe, it, expect, beforeEach, afterEach } from 'vitest'
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import meService from '@/services/me'

describe('services/me', () => {
  let mock: MockAdapter

  beforeEach(() => {
    mock = new MockAdapter(axios)
  })

  afterEach(() => {
    mock.restore()
  })

  it('`/me` API를 호출해야 합니다.', () => {
    mock.onPost('/me').reply(200, { result: 'success' })
    return meService.getMyData()
  })

  it('요청이 성공하면 유저 정보를 가져와야 합니다..', async () => {
    expect.assertions(1)

    mock.onPost('/me').reply(200, { result: 'success' })

    try {
      const response: any = await meService.getMyData()
      expect(response.result).toEqual('success')
    } catch (error) {
      throw error
    }
  })

  it('요청이 실패하면 유저 정보를 가져오는데 실패해야 합니다.', async () => {
    expect.assertions(1)

    mock.onPost('/me').reply(400, { data: { message: 'Bad request' } })

    try {
      const response = await meService.getMyData()
      // 의미없는 변수를 나타내기 위해 _를 사용합니다.
      const _ = response
    } catch (error: any) {
      expect(error.message).toEqual('잘못된 요청입니다.')
    }
  })
})
