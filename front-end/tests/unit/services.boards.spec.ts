import { describe, it, expect, beforeEach, afterEach } from 'vitest'
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import boardService from '@/services/boards'

describe('services/boards', () => {
  let mock: MockAdapter

  const boardDetail = {
    teamId: 1,
    name: 'blue',
    description: 'test board'
  }

  beforeEach(() => {
    mock = new MockAdapter(axios)
  })

  afterEach(() => {
    mock.restore()
  })

  it('`/boards` API를 호출해야 합니다.', () => {
    mock.onPost('/boards').reply(200, { result: 'success' })
    return boardService.create(boardDetail)
  })

  it('요청이 성공하면 보드를 생성해야 합니다.', async () => {
    expect.assertions(1)

    mock.onPost('/boards').reply(200, { result: 'success' })

    try {
      const response: any = await boardService.create(boardDetail)
      expect(response.result).toEqual('success')
    } catch (error) {
      throw error
    }
  })

  it('요청이 실패하면 보드 생성에 실패해야 합니다.', async () => {
    expect.assertions(1)

    mock.onPost('/boards').reply(400, { data: { message: 'Bad request' } })

    try {
      const response = await boardService.create(boardDetail)
      // 의미없는 변수를 나타내기 위해 _를 사용합니다.
      const _ = response
    } catch (error: any) {
      expect(error.message).toEqual('잘못된 요청입니다.')
    }
  })
})
