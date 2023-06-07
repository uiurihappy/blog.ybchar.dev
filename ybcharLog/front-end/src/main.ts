import { createApp } from 'vue';
import { createPinia } from 'pinia';
// import piniaPersist from 'pinia-plugin-persist';
// const App = require('./App.vue');
// const router = require('./router');
import App from './App.vue';
import router from './router';
import mavonEditor from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';
// import './assets/main.css';
import 'normalize.css';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import 'bootstrap/dist/css/bootstrap-utilities.css';

const pinia = createPinia();
// pinia.use(piniaPersist);
createApp(App)
  .use(pinia)
  .use(router)
  .use(ElementPlus)
  .use(mavonEditor)
  .mount('#app');
