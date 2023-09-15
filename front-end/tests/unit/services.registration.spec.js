import { describe, it, expect, beforeEach, afterEach } from 'vitest'
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import registrationService from '@/services/registration'

describe('services/registration', () => {
  let mock

  beforeEach(() => {
    mock = new MockAdapter(axios)
  })

  afterEach(() => {
    mock.restore()
  })

  it('should successfully register', async () => {
    expect.assertions(1)

    mock.onPost('/registrations').reply(200, { result: 'success' })

    return registrationService.register().then((data) => {
      expect(data.result).toEqual('success')
    })
  })

  it('should handle registration failure', async () => {
    expect.assertions(1)

    mock.onPost('/registrations').reply(400, { message: 'Bad request' })

    return registrationService.register().catch((error) => {
      expect(error.response.data.message).toEqual('Bad request')
    })
  })
})
