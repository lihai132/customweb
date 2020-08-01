//index.js
import Vue from 'vue'
import vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
Vue.use(vuex);
import LoginStore from './login';
// import AssetsStore from './assets';
import orderStore from './order';
import PopupStore from './popup';
import newsListStore from './newsList';
export default new vuex.Store({
    modules: {
        Login: LoginStore,
        Order: orderStore,
        Popup: PopupStore,
        newsList: newsListStore

    },
    plugins: [createPersistedState()]
})