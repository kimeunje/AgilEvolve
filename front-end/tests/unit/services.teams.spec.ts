import { describe, it, expect, beforeEach, afterEach } from 'vitest'
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'

import teamService from '@/services/teams'

describe('services/teams', () => {
  let mock: MockAdapter

  const teamDetail = {
    name: 'rainbow Team',
  }

  beforeEach(() => {
    mock = new MockAdapter(axios)
  })

  afterEach(() => {
    mock.restore()
  })

  it('`/teams` API를 호출해야 합니다.', () => {
    mock.onPost('/teams').reply(200, { result: 'success' })
    return teamService.create(teamDetail)
  })

  it('요청이 성공하면 팀을 생성해야 합니다.', async () => {
    expect.assertions(1)

    mock.onPost('/teams').reply(200, { result: 'success' })

    try {
      const response: any = await teamService.create(teamDetail)
      expect(response.result).toEqual('success')
    } catch (error) {
      throw error
    }
  })

  it('요청이 실패하면 팀 생성에 실패해야 합니다.', async () => {
    expect.assertions(1)

    mock.onPost('/teams').reply(400, { data: { message: 'Bad request' } })

    try {
      const response = await teamService.create(teamDetail)
      // 의미없는 변수를 나타내기 위해 _를 사용합니다.
      const _ = response
    } catch (error: any) {
      expect(error.message).toEqual('잘못된 요청입니다.')
    }
  })
})
