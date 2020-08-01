import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import {
    messages
} from './components/common/i18n';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';

Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(ElementUI, {
    size: 'small'
});
const i18n = new VueI18n({
    locale: 'zh',
    messages
});


import VueAMap from 'vue-amap'
Vue.use(VueAMap)
VueAMap.initAMapApiLoader({
    key: '2074795e94495c8399a38293b5b705ca&plugin',
    plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PlaceSearch', 'AMap.Geolocation', 'AMap.Geocoder'],
    v: '1.4.4',
    uiVersion: '1.0'
})

//引入发送网络请求的axios
import axios from 'axios';
//把axios放在Vue的原型对象上面
Vue.prototype.$axios = axios;

import store from './store/index';

import preview from 'vue-photo-preview'
import 'vue-photo-preview/dist/skin.css'
Vue.use(preview)

//使用钩子函数对路由进行权限跳转
// router.beforeEach((to, from, next) => {
//     let Token = document.cookie.Token;
//     console.log(Token)
//     if (Token) {
//         next();
//     } else {
//         this.$router.push('/login');
//     }
// })

// //使用钩子函数对路由进行权限跳转
// router.beforeEach((to, from, next) => {
//     var cookies = document.cookie;
//     // let arr = cookies.split("=");
//     if (cookies) {
//         // if (!document.cookie && to.path !== '/login') {
//         next();
//     } else {
//         next('/login');
//     }

// });





new Vue({
    router,
    i18n,
    store, //这个store是刚才创建的index文件引入的
    render: h => h(App)
}).$mount('#app');