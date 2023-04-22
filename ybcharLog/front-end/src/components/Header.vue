<template>
  <el-header class="header">
    <el-menu class="header-menu" mode="horizontal" router>
      <el-menu-item class="header-menu-item" index="/"
        >ybchar blog</el-menu-item
      >
      <el-menu-item class="header-menu-item" index="/write"
        >글 작성</el-menu-item
      >
    </el-menu>
  </el-header>
  <el-main class="main">
    <router-view></router-view>
  </el-main>
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
  height: 60px;
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-menu {
  height: 100%;
}

.header-menu-item {
  font-family: 'dohyeon';
  font-size: 20px;
  color: black;
  padding: 10px;
}

.header-menu-item.router-link-active:first-child {
  color: black;
}

.main {
  flex-grow: 1;
  margin: 20px 0;
}
</style>
