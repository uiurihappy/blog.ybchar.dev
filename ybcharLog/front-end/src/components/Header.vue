<template>
  <div class="blog">
    <el-header class="header">
      <div class="logo">
        <el-menu-item class="indexHeader" index="/">ybchar blog</el-menu-item>
      </div>
      <div class="menu">
        <el-menu mode="horizontal" router>
          <el-menu-item class="saveHeader" index="/write">글 작성</el-menu-item>
        </el-menu>
      </div>
    </el-header>
    <el-main class="main">
      <router-view></router-view>
    </el-main>
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
.blog {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.header {
  height: auto;
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
}

.logo {
  font-family: 'jua';
  font-size: 24px;
  color: black;
  padding: 10px;
}

.indexHeader {
  color: black;
}

.menu {
  padding: 10px;
}

.el-menu {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.main {
  flex-grow: 1;
  margin: 20px 0;
}
</style>
