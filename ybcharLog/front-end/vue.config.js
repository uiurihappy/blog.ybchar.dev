const webpack = require('webpack');

module.exports = {
  configureWebpack: {
    plugins: [
      new webpack.DefinePlugin({
        'process.env': {
          VUE_API_URL: JSON.stringify(process.env.VUE_API_URL),
        },
      }),
    ],
  },
  css: {
    loaderOptions: {
      sass: {
        additionalData: `@import "~vue3-dropzone/dist/vue3Dropzone.min.css";`,
      },
    },
  },
};
