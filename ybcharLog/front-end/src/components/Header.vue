<template>
  <div>
    <el-header class="header">
      <div class="logo">ybchar blog</div>
      <el-menu class="menu" mode="horizontal" router>
        <el-menu-item class="menu-item" index="/">Home</el-menu-item>
        <el-menu-item class="menu-item" index="/write">Write</el-menu-item>
      </el-menu>
    </el-header>
    <div class="main">
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

.header {
  background-color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 100%;
  z-index: 1;
  height: 64px;
}

.logo {
  font-size: 24px;
  font-family: 'dohyeon';
}

.menu {
  height: 100%;
}

.menu-item {
  font-size: 16px;
  font-family: 'dohyeon';
  color: #666;
  padding: 0 16px;
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
}
</style>
