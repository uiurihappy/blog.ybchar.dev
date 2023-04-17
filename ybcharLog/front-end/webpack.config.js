const webpack = require('webpack');
const dotenv = require('dotenv');
const env = dotenv.config().parsed;

[
  new webpack.DefinePlugin({
    VUE_APP_LOCAL_URI: JSON.stringify(env.BASE_URL),
  }),
];
