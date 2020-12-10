<template>
  <div class="col-md-12">
    <div class="card card-container">
      <img
        id="profile-img"
        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        class="profile-img-card"
      />
      <form @submit.prevent="handleRegister">
        <div v-if="!successful">
          <div class="form-group">
            <label for="pseudo" >Pseudo</label>
            <input id="pseudo"
              v-model="form.pseudo"
              v-validate="'required|min:3|max:20'"
              type="text"
              class="form-control"
              name=""
            />
            <div
              v-if="submitted && errors.has('pseudo')"
              class="alert-danger"
            >{{errors.first('pseudo')}}</div>
          </div>

          <div class="form-group">
            <label for="firstName" >Firstname</label>
            <input id="firstName"
                   v-model="form.firstName"
                   v-validate="'required|min:3|max:20'"
                   type="text"
                   class="form-control"
                   name=""
            />
            <div
                v-if="submitted && errors.has('firstName')"
                class="alert-danger"
            >{{errors.first('firstName')}}</div>
          </div>

          <div class="form-group">
            <label for="lastName" >Lastname</label>
            <input id="lastName"
                   v-model="form.lastName"
                   v-validate="'required|min:3|max:20'"
                   type="text"
                   class="form-control"
                   name=""
            />
            <div
                v-if="submitted && errors.has('lastName')"
                class="alert-danger"
            >{{errors.first('lastName')}}</div>
          </div>

          <div class="form-group">
            <label for="email">Email</label>
            <input id="email"
              v-model="form.email"
              v-validate="'required|email|max:50'"
              type="email"
              class="form-control"
              name="email"
            />
            <div
              v-if="submitted && errors.has('email')"
              class="alert-danger"
            >{{errors.first('email')}}</div>
          </div>

          <div class="form-group">
            <label for="city">City</label>
            <input id="city"
                   v-model="form.city"
                   type="text"
                   class="form-control"
                   name="city"
            />
            <div
                v-if="submitted && errors.has('city')"
                class="alert-danger"
            >{{errors.first('city')}}</div>
          </div>


          <div class="form-group">
            <label for="password">Password</label>
            <input id="password"
              v-model="form.password"
              v-validate="'required|min:6|max:40'"
              type="password"
              class="form-control"
              name="password"
            />
            <div
              v-if="submitted && errors.has('password')"
              class="alert-danger"
            >{{errors.first('password')}}</div>
          </div>


          <div class="form-group">
            <label for="passwordAgain">PasswordAgain</label>
            <input id="passwordAgain"
                   v-model="form.passwordAgain"
                   v-validate="'required|min:6|max:40'"
                   type="password"
                   class="form-control"
                   name="passwordAgain"
            />
            <div
                v-if="submitted && errors.has('passwordAgain')"
                class="alert-danger"
            >{{errors.first('passwordAgain')}}</div>
          </div>

          <div class="form-group">
            <button class="btn btn-primary btn-block">Sign Up</button>
          </div>
        </div>
      </form>

      <div
        v-if="message"
        class="alert"
        :class="successful ? 'alert-success' : 'alert-danger'"
      >{{message}}</div>
    </div>
  </div>
</template>

<script>
import RegisterForm from "@/models/registerForm";

export default {
  name: 'Register',
  data() {
    return {
      form: new RegisterForm('', '', '' , '','','',''),
      submitted: false,
      successful: false,
      message: ''
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/profile');
    }
  },
  methods: {
    handleRegister() {
      this.message = '';
      this.submitted = true;

      var form = this.form;

      this.$validator.validate().then(isValid => {
        if (isValid) {

          console.log(form);

          this.$store.dispatch('auth/register', form).then(
            data => {
              this.message = data.message;
              this.successful = true;
            },
            error => {
              this.message =
                (error.response && error.response.data && error.response.data.message) ||
                error.message ||
                error.toString();
              this.successful = false;
            }
          );
        }
      });
    }
  }
};
</script>

<style scoped>
label {
  display: block;
  margin-top: 10px;
}

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}

.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
  width: 96px;
  height: 96px;
  margin: 0 auto 10px;
  display: block;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
</style>