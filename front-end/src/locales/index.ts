import { createI18n, type I18n, type Locale } from 'vue-i18n'
import enUS from '@/locales/messages/en_US.json'
import zhCN from '@/locales/messages/zh_CN.json'
import jaJP from '@/locales/messages/ja_JP.json'
import koKR from '@/locales/messages/ko_KR.json'
import type { WritableComputedRef } from 'vue'

const i18n = createI18n({
  locale: 'ko_KR',
  fallbackLocale: 'en_US',
  legacy: false,
  globalInjection: true,
  messages: {
    en_US: enUS,
    zh_CN: zhCN,
    ja_JP: jaJP,
    ko_KR: koKR
  }
})

const setLocale = (locale: any): void => {
  i18n.global.locale = locale
}

export { i18n, setLocale }
