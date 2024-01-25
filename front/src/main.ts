import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import axios from 'axios'

import "normalize.css"

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import "bootstrap/dist/css/bootstrap-utilities.css"

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('user-accessToken');
        if (token) {
            config.headers.Authorization = `${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);


app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.mount('#app')
