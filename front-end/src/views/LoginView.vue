<template>
  <div class="login">
    <h2>Login</h2>
    <form>
      <div class="form-control">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div class="form-control">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit" class="btn btn-primary" @click.prevent="login">
        Login
      </button>
    </form>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
export default {
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    login() {
      // 로그인 처리 로직 작성
      // this.email, this.password 값으로 API 요청 등을 통해 로그인 처리
      axios
        .post('/api/auth/login', {
          email: this.email,
          password: this.password,
        })
        .then(response => {
          const accessToken = response.data.accessToken;
          sessionStorage.setItem('accessToken', accessToken);
          window.location.href = '/';
        })
        .catch(err => {
          console.log(err);
          alert('로그인에 실패하였습니다.');
        });
    },
  },
};
</script>

<style scoped>
.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 30px;
}
.form-control {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}
label {
  font-size: 18px;
  margin-bottom: 5px;
}
input[type='email'],
input[type='password'] {
  justify-content: center;
  border-radius: 4px;
  border: 1px solid #ced4da;
  padding: 10px;
  width: 100%;
  font-size: 16px;
}
button[type='submit'] {
  justify-content: center;
  width: 100%;
  font-size: 18px;
  margin-top: 20px;
}
</style>
