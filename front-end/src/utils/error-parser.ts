import _ from 'lodash'
import { AxiosError } from 'axios'
import { i18n } from '@/locales'

type ErrorResponse = {
  message: string
}

export default {
  parse(error: AxiosError<ErrorResponse>) {
    if (error.response) {
      const status = error.response.status
      const data = error.response.data
      if (status === 400) {
        if (data && data.message) {
          return new Error(data.message)
        } else {
          return new Error(i18n.global.t('error.request.bad'))
        }
      } else if (status === 401) {
        return new Error(i18n.global.t('error.request.notAuthorized'))
      } else if (status === 403) {
        return new Error(i18n.global.t('error.request.forbidden'))
      } else if (status === 404) {
        return new Error(i18n.global.t('error.request.notFound'))
      } else if (status === 500) {
        if (data && data.message) {
          return new Error(data.message)
        } else {
          return new Error(i18n.global.t('error.request.unknownServerError'))
        }
      } else {
        return new Error(i18n.global.t('error.request.failed'))
      }
    } else if (error.request) {
      return new Error(i18n.global.t('error.request.noResponse'))
    } else {
      return _.isError(error) ? error : new Error(error)
    }
  }
}
