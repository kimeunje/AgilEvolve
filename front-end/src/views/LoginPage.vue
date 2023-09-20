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
              <label for="username">Username or email address</label>
              <div class="field-error" v-if="v$.form.username.$dirty">
                <div class="error" v-if="v$.form.username.required.$invalid">Username or email address is required</div>
              </div>
            </div>
            <div class="form-group form-floating mb-3">
              <input type="password" class="form-control" id="password" placeholder="Password" v-model="form.password">
              <label for="password">Password</label>
              <div class="field-error" v-if="v$.form.password.$dirty">
                <div class="error" v-if="v$.form.password.required.$invalid">Password is required</div>
              </div>
            </div>
            <button class="btn btn-primary w-100 py-2" type="submit">Sign in</button>
            <div class="links">
              <p class="sign-up text-muted">Don't have an account yet? <a href="/register" class="link-sign-up">Sign up
                  here</a></p>
              <p class="text-center mt-5 mb-3 text-body-secondary"><a href="#">Forgot your password?</a></p>
            </div>
          </form>
        </div>
      </div>
    </main>
    <PageFooter />
  </div>
</template>

<script lang="ts">
import { useVuelidate } from '@vuelidate/core'
import { required } from '@vuelidate/validators'
import authenticationService from '@/services/authentication'
import Logo from '@/components/Logo.vue'
import PageFooter from '@/components/PageFooter.vue'

export default {
  name: 'LoginPage',
  setup() {
    return { v$: useVuelidate() }
  },
  data: function () {
    return {
      form: {
        username: '',
        password: ''
      },
      errorMessage: ''
    }
  },
  components: {
    Logo,
    PageFooter
  },
  validations() {
    return {
      form: {
        username: { required },
        password: { required }
      }
    }
  },
  methods: {
    submitForm() {
      this.v$.$touch()
      if (this.v$.$invalid) {
        return
      }

      authenticationService.authenticate(this.form).then(() => {
        this.$router.push({ name: 'HomePage' })
      }).catch((error: any) => {
        this.errorMessage = error.message
      })
    }
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
