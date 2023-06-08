import { createApp } from 'vue';
import { createPinia } from 'pinia';
// import piniaPersist from 'pinia-plugin-persist';
// const App = require('./App.vue');
// const router = require('./router');
import App from './App.vue';
import router from './router';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/vue-editor';
import 'normalize.css';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import 'bootstrap/dist/css/bootstrap-utilities.css';
import '@toast-ui/editor/dist/toastui-editor.css';

const pinia = createPinia();
// pinia.use(piniaPersist);
const app = createApp(App);
app.config.errorHandler = () => null;
app.config.warnHandler = () => null;
app
  .use(pinia)
  .use(router)
  .use(ElementPlus)
  // .component('Editor', Editor)
  .mount('#app');
