<template>
  <div class="container">
    <main class="row">
      <div class="register-form w-100 mx-auto">
        <div class="logo-wrapper text-center">
          <img class="logo" src="/static/images/logo.png" alt="Logo">
          <div class="tagline">Open source task management tool</div>
        </div>

        <form @submit.prevent="submitForm">
          <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
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
    </main>


    <footer class="footer pt-4 my-md-5 pt-md-5 border-top">
      <div class="row">
        <div class="col-6 col-md">
          <span class="copyright">&copy; 2023 TaskAgile.com</span>
          <ul class="footer-links list-unstyled text-small list-inline float-md-end">
            <li class="mb-1 list-inline-item"><a class="link-secondary" href="#">About</a></li>
            <li class="mb-1 list-inline-item"><a class="link-secondary" href="#">Terms of Service</a></li>
            <li class="mb-1 list-inline-item"><a class="link-secondary" href="#">Privacy Policy</a></li>
            <li class="mb-1 list-inline-item"><a class="link-secondary" href="#">GitHub</a></li>
          </ul>
        </div>
      </div>
    </footer>
  </div>
</template>

<script lang="ts">
import registrationService from '@/services/registration'
import { useVuelidate } from '@vuelidate/core'
import { required, email, minLength, maxLength, alphaNum } from '@vuelidate/validators'

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
.container {
  max-width: 900px;
}


.register-form {
  margin-top: 50px;
  max-width: 400px;
}

.logo-wrapper {
  text-align: center;
  margin-bottom: 40px;


  .tagline {
    line-height: 180%;
    color: #666;
  }

  .logo {
    max-width: 150px;
    margin: 0 auto;
  }
}

.register-form {
  .form-group label {
    // font-weight: bold;
    color: #555;
  }

  .accept-terms {
    margin: 20px 0 40px 0;
  }
}

.footer {
  width: 100%;
  font-size: 13px;
  color: #666;
  line-height: 40px;
  margin-top: 50px;


  .list-inline-item {
    margin-right: 10px;
  }

  a {
    color: #666;
  }
}
</style>