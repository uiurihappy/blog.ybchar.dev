const path = require('path');

module.exports = {
  build: {
    // Template for index.html
    index: path.resolve(__dirname, '../src/main/resources/static/index.html'), // 변경 후 - Vue 프로젝트의 빌드 결과물 위치 변경 설정

    // Paths
    assetsRoot: path.resolve(__dirname, '../src/main/resources/static'), // 변경 후 - Vue 프로젝트의 빌드 결과물 위치 변경 설정
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
  },
  css: {
    loaderOptions: {
      sass: {
        additionalData: `@import "~vue3-dropzone/dist/vue3Dropzone.min.css";`,
      },
    },
  },
};
