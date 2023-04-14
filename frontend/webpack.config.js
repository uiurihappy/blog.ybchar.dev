var path = require('path')
var webpack = require('webpack')
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    entry: './src/main.ts', // 최초 실행될 entry 파일
    output: {
      path: path.resolve(__dirname, './dist'), // build 결과가 저장될 폴더
      publicPath: '/dist/', // 이걸 지우게 되면 css가 적용이 안되는게 확인됨. 약간 경로 관련해서 문제가 있던걸로 기억하는데 일단 path랑 똑같이 지정해준다.
      filename: 'build.js' // build 결과 파일 이름
    },
  module: {
    rules: [ // build 규칙들
      {
        test: /\.vue$/, // .vue파일을
        loader: 'vue-loader' // vue-loader로 해석
      },
      {
        test: /\.ts$/, // .ts파일을
        loader: 'ts-loader' // ts-loader로 해석
        },
      {
        test: /\.css$/, // .css파일을
        use: [
          'vue-style-loader', // vue-style-lodaer을 이용해서
          {
            loader: 'css-loader', // css-loader로 해석. 구글링을 찾은 방법
          }
        ]
      }
    ]
  },
  devServer: { // webpack-dev-server 옵션 설정
    host: "localhost", // localhost 설정
    port: 5500, // 포트 설정
    // noInfo: true, // true로 했더니 실행이 안되는줄알고 몇시간 삽질을 했다. 지워주자.
    open: true, // localhost:5500 으로 새탭 열림
  },
  plugins: [
    new VueLoaderPlugin() // 이것도 오류가나서 내용 보니까 설정해주라 해서 설정해줌.
  ]
}