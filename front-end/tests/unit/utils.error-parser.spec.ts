import { describe, it, expect } from 'vitest'
import errorParser from '@/utils/error-parser'

describe('utils/error-parser', () => {
  it('HTTP 400 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 400,
        data: {
          result: 'failure',
          message: '오류입니다.'
        }
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('오류입니다.')
  })

  it('HTTP 400 단위 테스트 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 400,
        data: {
          message: '잘못된 요청입니다.'
        }
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('잘못된 요청입니다.')
  })

  it('메시지가 없는 HTTP 400 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 400
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('잘못된 요청입니다.')
  })

  it('HTTP 401 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 401,
        statusText: 'Unauthorized'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('요청이 승인되지 않았습니다.')
  })

  it('HTTP 403 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 403,
        statusText: 'Forbidden'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('요청이 금지되었습니다.')
  })

  it('HTTP 404 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 404,
        statusText: 'Not Found'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual(
      '요청에 실패했습니다. 서버에서 요청 엔드포인트를 찾을 수 없습니다.'
    )
  })

  it('알려진 HTTP 500 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 500,
        statusText: 'Internal Server Error',
        data: {
          message: '오류 메시지의 문구가 변경되었습니다.'
        }
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual(
      '오류 메시지의 문구가 변경되었습니다. 나중에 다시 시도해 주세요.'
    )
  })

  it('알려지지 않은 HTTP 500 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 500,
        statusText: 'Internal Server Error'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('서버 측에 오류가 발생했습니다. 나중에 다시 시도해 주세요.')
  })

  it('HTTP 503 오류를 구문 분석해야 합니다.', () => {
    const error: any = {
      response: {
        status: 503,
        statusText: 'Service Unavailable'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('요청이 실패했습니다. 나중에 다시 시도해 주세요.')
  })

  it('HTTP 504 오류를 구문 분석해야 합니다', () => {
    const error: any = {
      response: {
        status: 504,
        statusText: 'Gateway Timeout'
      }
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('요청이 실패했습니다. 나중에 다시 시도해 주세요.')
  })

  it('응답이 없는 서버 오류를 구문 분석해야 합니다', () => {
    const error: any = {
      request: {}
    }
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('요청이 실패했습니다. 서버에서 응답이 없습니다.')
  })

  it('알 수 없는 문자열 오류를 구문 분석해야 합니다.', () => {
    const error: any = 'Unknown error'
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Unknown error')
  })

  it('알 수 없는 래핑 오류를 구문 분석해야 합니다', () => {
    const error: any = new Error('Unknown error')
    const parsed = errorParser.parse(error)
    expect(parsed.message).toEqual('Unknown error')
  })
})
