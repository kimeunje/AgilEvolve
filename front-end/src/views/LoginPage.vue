<template>
  <div class="container public">
    <main class="row">
      <div class="register-form w-100 mx-auto">
        <div class="form">
          <Logo />
          <form @submit.prevent="submitForm">
            <div v-if="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
            <div class="form-group form-floating mb-3">
              <input type="text" class="form-control" id="username" placeholder="username" v-model="form.username">
              <label for="username">{{ t("loginPage.form.username.label") }}</label>
              <div class="field-error" v-if="v$.username.$dirty">
                <div class="error" v-if="v$.username.required.$invalid">{{ t("loginPage.form.username.required") }}
                </div>
              </div>
            </div>
            <div class="form-group form-floating mb-3">
              <input type="password" class="form-control" id="password" placeholder="Password" v-model="form.password">
              <label for="password">{{ t("loginPage.form.password.label") }}</label>
              <div class="field-error" v-if="v$.password.$dirty">
                <div class="error" v-if="v$.password.required.$invalid">{{ t("loginPage.form.password.required") }}
                </div>
              </div>
            </div>
            <button class="btn btn-primary w-100 py-2" type="submit">{{ t("loginPage.form.submit") }}</button>
            <div class="links">
              <p class="sign-up text-muted">{{ t("loginPage.form.noAccountYet") }}
                <router-link to="/register" class="link-sign-up">{{ t("loginPage.form.signUpHere") }}</router-link>
              </p>
              <p class="text-center mt-5 mb-3 text-body-secondary">
                <router-link to="#">{{ t("loginPage.form.forgotPassword") }}</router-link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </main>
    <PageFooter />
  </div>
</template>

<script lang="ts">
import { ref } from 'vue'
import { useVuelidate } from '@vuelidate/core'
import { required } from '@vuelidate/validators'
import { useI18n } from 'vue-i18n'
import { useRouter } from 'vue-router'

import Logo from '@/components/Logo.vue'
import PageFooter from '@/components/PageFooter.vue'
import authenticationService from '@/services/authentication'


export default {
  name: 'LoginPage',
  setup() {
    const router = useRouter()
    const { t } = useI18n()

    const form = ref({
      username: '',
      password: ''
    })

    const errorMessage = ref('')

    const rules = {
      username: { required },
      password: { required }
    }

    const v$ = useVuelidate(rules, form)


    function submitForm() {
      v$.value.$touch()
      if (v$.value.$invalid) {
        return
      }

      authenticationService.authenticate(form.value).then(() => {
        router.push({ name: 'home' })
      }).catch((error: Error) => {
        errorMessage.value = error.message
      })
    }

    return { v$, t, form, errorMessage, submitForm }
  },
  components: {
    Logo,
    PageFooter
  }
}
</script>

<style lang="scss" scoped>
.register-form {
  margin-top: 50px;
  max-width: 400px;
}

.links {
  margin: 30px 0 50px 0;
  text-align: center;
}
</style>
