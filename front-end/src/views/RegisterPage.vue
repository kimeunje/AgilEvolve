<template>
  <div class="container public">
    <main class="row">
      <div class="register-form w-100 mx-auto">
        <div class="form">
          <Logo />
          <form @submit.prevent="registerService.submitForm">
            <div v-if="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
            <div class="form-group form-floating mb-3">
              <input type="text" class="form-control" id="username" placeholder="username" v-model="form.username">
              <label for="username">{{ t('registerPage.form.username.label') }}</label>
              <div class="field-error" v-if="v$.form.username.$dirty">
                <div class="error" v-if="v$.form.username.required.$invalid">
                  {{ t('registerPage.form.username.required') }}</div>
                <div class="error" v-if="v$.form.username.alphaNum.$invalid">
                  {{ t('registerPage.form.username.alphaNum') }}</div>
                <div class="error" v-if="v$.form.username.minLength.$invalid">{{
            t('registerPage.form.username.minLength',
              { minLength: v$.form.username.minLength.$params.min }) }}</div>
                <div class="error" v-if="v$.form.username.maxLength.$invalid">{{
            t('registerPage.form.username.maxLength',
              { maxLength: v$.form.username.maxLength.$params.max }) }}</div>
              </div>
            </div>
            <div class="form-group form-floating mb-3">
              <input type="email" class="form-control" id="emailAddress" placeholder="email" v-model="form.emailAddress">
              <label for="emailAddress">{{ t('registerPage.form.emailAddress.label') }}</label>
              <div class="field-error" v-if="v$.form.emailAddress.$dirty">
                <div class="error" v-if="v$.form.emailAddress.required.$invalid">
                  {{ t('registerPage.form.emailAddress.required') }}
                </div>
                <div class="error" v-if="v$.form.emailAddress.email.$invalid">
                  {{ t('registerPage.form.emailAddress.email') }}
                </div>
                <div class="error" v-if="v$.form.emailAddress.maxLength.$invalid">
                  {{ t('registerPage.form.emailAddress.maxLength',
            { maxLength: v$.form.emailAddress.maxLength.$params.max }) }}</div>
              </div>
            </div>
            <div class="form-group form-floating mb-3">
              <input type="password" class="form-control" id="password" placeholder="Password" v-model="form.password">
              <label for="password">{{ t('registerPage.form.password.label') }}</label>
              <div class="field-error" v-if="v$.form.password.$dirty">
                <div class="error" v-if="v$.form.password.required.$invalid">
                  {{ t('registerPage.form.password.required') }}
                </div>
                <div class="error" v-else-if="v$.form.password.minLength.$invalid">
                  {{ t('registerPage.form.password.minLength',
            { minLength: v$.form.password.minLength.$params.min }) }}
                </div>
                <div class="error" v-else-if="v$.form.password.maxLength.$invalid">
                  {{ t('registerPage.form.password.maxLength',
            { maxLength: v$.form.password.maxLength.$params.max }) }}
                </div>
                <div class="error" v-else-if="v$.form.password.containsSpecialCharacter.$invalid">
                  비밀번호에 특수 문자를 1개 이상 포함시켜주세요.
                </div>
              </div>
            </div>
            <button class="btn btn-primary w-100 py-2" type="submit">{{ t('registerPage.form.submit') }}</button>
            <p class="accept-terms mt-5 mb-3 text-body-secondary">
              <i18n-t keypath="registerPage.form.terms.accept" tag="p" class="accept-terms text-muted" scope="global">
                <template #termsOfService>
                  <a href="#">{{ t('registerPage.form.terms.termsOfService') }}</a>
                </template>
                <template #privacyPolicy>
                  <a href="#">{{ t('registerPage.form.terms.privacyPolicy') }}</a>
                </template>
              </i18n-t>
            </p>
            <p class="text-center mt-5 mb-3 text-body-secondary">{{ t('registerPage.form.alreadyHaveAccount') }}
              <router-link to="/login">{{ t("registerPage.form.signIn") }}</router-link>
            </p>
          </form>
        </div>
      </div>
    </main>
    <PageFooter />
  </div>
</template>

<script setup lang="ts">
import router from '@/router'
import { useVuelidate } from '@vuelidate/core'
import { required, email, minLength, maxLength, alphaNum, helpers } from '@vuelidate/validators'

import { setLocale } from "@/locales"
import registrationService from '@/services/registration'

import Logo from '@/components/Logo.vue'
import PageFooter from '@/components/PageFooter.vue'
import { useI18n } from 'vue-i18n'
import { ref } from 'vue'

const { t } = useI18n();

const errorMessage = ref('')
const form = ref({
  username: '',
  emailAddress: '',
  password: ''
})

const containsSpecialCharacter = helpers.regex(/^(?=.*[!@#$%^&*()_+]).*$/)

const validations = {
  form: {
    username: { required, minLength: minLength(2), maxLength: maxLength(50), alphaNum },
    emailAddress: { required, email, maxLength: maxLength(100) },
    password: { required, minLength: minLength(6), maxLength: maxLength(30), containsSpecialCharacter }
  }
};

const v$ = ref(useVuelidate(validations, { form }));

function submitForm() {
  v$.value.$touch()
  if (v$.value.$invalid) {
    return
  }

  const registerForm = {
    username: form.value.username,
    emailAddress: form.value.emailAddress,
    password: form.value.password
  }

  registrationService.register(registerForm).then(() => {
    router.push({ name: 'login' })
  }).catch((error: Error) => {
    errorMessage.value = '회원가입에 실패 했습니다. 원인: ' + (error.message ? error.message : '알 수 없는 에러입니다.') + '.'
  })
}

const registerService = {
  submitForm
}

const changeLocale = () => {
  setLocale('ko_KR')
}
</script>

<style lang="scss" scoped>
.register-form {
  margin-top: 50px;
  max-width: 400px;
}

.accept-terms {
  margin: 20px 0 40px 0;
}
</style>
