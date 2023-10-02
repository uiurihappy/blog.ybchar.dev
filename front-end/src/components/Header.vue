<!-- eslint-disable vue/multi-word-component-names -->
<script lang="ts">
import axios from 'axios';
import jwt_decode from 'jwt-decode';

export default {
  data() {
    return {
      accessToken: sessionStorage.getItem('accessToken'),
      isAccessTokenValid: false,
    };
  },
  created() {
    this.checkAccessTokenValidity();
  },
  methods: {
    checkAccessTokenValidity() {
      if (!this.accessToken) {
        this.isAccessTokenValid = false;
        return;
      }
      const { exp } = jwt_decode(this.accessToken) as { exp: number };
      const currentTime = Date.now() / 1000; // in seconds
      if (exp < currentTime) {
        this.isAccessTokenValid = false;
      } else {
        this.isAccessTokenValid = true;
      }
    },
    logout() {
      axios.get('/api/auth/logout').then(() => {
        sessionStorage.removeItem('accessToken');
        this.accessToken = null;
        this.isAccessTokenValid = false;
        this.$router.push({ name: 'Home' });
      });
    },
  },
};
</script>

<template>
  <div class="container">
    <el-header class="header">
      <div class="logo" type="primary" active-text-color="transparent">
        <router-link :to="{ name: 'Home' }" style="text-decoration: none"
          >ybchar blog</router-link
        >
      </div>

      <el-menu class="menu" mode="horizontal" router>
        <div>
          <el-menu-item
            class="about-me"
            index="/about"
            active-text-color="transparent"
            >About me</el-menu-item
          >
        </div>
        <div>
          <el-menu-item
            class="menu-item"
            index="/"
            active-text-color="transparent"
            >Home</el-menu-item
          >
        </div>
        <div v-if="isAccessTokenValid">
          <el-menu-item
            class="menu-item"
            index="/write"
            active-text-color="transparent"
            >Write</el-menu-item
          >
        </div>
        <div v-else>
          <el-menu-item
            class="menu-item"
            index="/login"
            active-text-color="transparent"
            >Login</el-menu-item
          >
        </div>
        <div v-if="isAccessTokenValid">
          <el-menu-item
            class="menu-item"
            active-text-color="transparent"
            @click="logout"
          >
            Logout
          </el-menu-item>
        </div>
      </el-menu>
    </el-header>
  </div>
</template>

<style lang="scss" scoped>
@font-face {
  font-family: 'jua';
  src: url('../../public/fonts/BMJUA_ttf.ttf');
}
@font-face {
  font-family: 'dohyeon';
  src: url('../../public/fonts/BMDOHYEON_ttf.ttf');
}
.container {
  border: 0.7px solid #666;
  background-color: #fff;
  max-width: 1120px;
  margin: 0 auto;
  position: sticky;
  top: 0px;
  z-index: 1;
  padding {
    opacity: 0.2;
  }
}
.header {
  z-index: 2;
  background-color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 35px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 100%;
  height: 64px;
}

.logo {
  font-size: 28px;
  font-family: 'dohyeon', sans-serif;
  font-weight: bold;
  color: #333;
  text-transform: uppercase;
  letter-spacing: 2px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease-out;
}

.logo:hover {
  cursor: pointer;
  color: #ff6b6b;
  text-shadow: none;
  text-decoration: none;
}
.menu {
  height: 100%;
  text-decoration: none;
}
.menu-item {
  font-size: 16px;
  font-family: 'dohyeon';
  color: #666;
  padding: 0 16px;
  text-decoration: none;
  opacity: 100%;
  border-top: transparent;
  border-bottom: transparent;
  transition: all ease-out;
}

.menu-item:hover {
  border-top-color: #666;
  color: #333;
  cursor: pointer;
}
.menu-item.router-link-active {
  color: black;
  background-color: #f5f5f5;
}
.about-me {
  font-size: 16px;
  font-family: 'dohyeon';
  color: #666;
  padding: 0 16px;
  text-decoration: none;
  opacity: 100%;
  border-top: transparent;
  border-bottom: transparent;
  transition: all ease-out;
  justify-content: left;
}

.main {
  padding-top: 64px; /* 헤더 높이만큼 padding 추가 */
  margin-top: 16px; /* 예시로 추가한 마진 */
  flex-grow: 1;
  padding: 0 24px;
  opacity: 0;
}
</style>
