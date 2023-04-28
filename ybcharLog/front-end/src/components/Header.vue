<template>
  <div class="container">
    <el-header class="header">
      <div class="logo" type="primary" active-text-color="transparent">
        <router-link :to="{ name: 'Home' }" style="text-decoration: none"
          >ybchar blog</router-link
        >
      </div>
      <el-menu class="menu" mode="horizontal" router>
        <el-menu-item
          class="menu-item"
          index="/"
          active-text-color="transparent"
          >Home</el-menu-item
        >
        <el-menu-item
          class="menu-item"
          index="/write"
          active-text-color="transparent"
          >Write</el-menu-item
        >
      </el-menu>
    </el-header>
    <!-- 페이지 컨텐츠 영역 -->
    <!-- <h1>Welcome to my blog!</h1>
      <p>This is my blog where I share my thoughts and ideas.</p>
      <p>Here are some of my recent posts:</p>
      <ul>
        <li><a href="#">Post 1</a></li>
        <li><a href="#">Post 2</a></li>
        <li><a href="#">Post 3</a></li>
      </ul> -->
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
  max-width: 1111px;
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

.main {
  padding-top: 64px; /* 헤더 높이만큼 padding 추가 */
  margin-top: 16px; /* 예시로 추가한 마진 */
  flex-grow: 1;
  padding: 0 24px;
  opacity: 0;
}
</style>
