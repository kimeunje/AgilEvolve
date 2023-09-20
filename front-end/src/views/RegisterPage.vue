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
              <label for="username">Username</label>
            </div>
            <div class="form-group form-floating mb-3">
              <input type="email" class="form-control" id="emailAddress" placeholder="email" v-model="form.emailAddress">
              <label for="emailAddress">email</label>
            </div>
            <div class="form-group form-floating mb-3">
              <input type="password" class="form-control" id="password" placeholder="Password" v-model="form.password">
              <label for="password">Password</label>
            </div>
            <button class="btn btn-primary w-100 py-2" type="submit">Create account</button>
            <p class="accept-terms mt-5 mb-3 text-body-secondary">By clicking “Create account”, you agree to our <a
                href="#">terms of
                service</a> and <a href="#">privacy policy</a>.</p>
            <p class="text-center mt-5 mb-3 text-body-secondary">Already have an account? <a href="/login">Sign in</a></p>
          </form>
        </div>
      </div>
    </main>
    <PageFooter />
  </div>
</template>

<script lang="ts">
import registrationService from '@/services/registration'
import { useVuelidate } from '@vuelidate/core'
import { required, email, minLength, maxLength, alphaNum } from '@vuelidate/validators'
import Logo from '@/components/Logo.vue'
import PageFooter from '@/components/PageFooter.vue'

export default {
  name: 'RegisterPage',
  setup() {
    return { v$: useVuelidate() }
  },
  data: () => {
    return {
      form: {
        username: '',
        emailAddress: '',
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
        username: { required, minLength: minLength(2), maxLength: maxLength(50), alphaNum },
        emailAddress: { required, email, maxLength: maxLength(100) },
        password: { required, minLength: minLength(6), maxLength: maxLength(30) }
      }
    }
  },
  methods: {
    submitForm() {
      this.v$.$touch()
      if (this.v$.$invalid) {
        return
      }

      registrationService.register(this.form).then(() => {
        this.$router.push({ name: 'LoginPage' })
      }).catch((error: any) => {
        this.errorMessage = 'Failed to register user. Reason: ' + (error.message ? error.message : 'Unknown') + '.'
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

.accept-terms {
  margin: 20px 0 40px 0;
}
</style>
