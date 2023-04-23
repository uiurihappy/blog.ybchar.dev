import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from './App.vue';
import router from './router';
import mavonEditor from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';

// import './assets/main.css';
import 'normalize.css';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import 'bootstrap/dist/css/bootstrap-utilities.css';

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(ElementPlus);
app.use(mavonEditor);
app.mount('#app');
