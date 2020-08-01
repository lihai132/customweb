import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);
import map from "../components/page/map.vue";
import map1 from "../components/page/map1.vue";
import canvas from "../components/page/canvas.vue";
import getCode from "../components/Login/Login.vue";
import Popup from "../components/page/Popup.vue";

let router = new Router({
    mode: 'hash',
    routes: [{
            path: '/',
            redirect: '/login'
        },
        {
            name: 'map',
            path: '/map',
            component: map,
            meta: {

                requiresAuth: true
            }
        },
        {
            name: 'map1',
            path: '/map1',
            component: map1,
            meta: {

                requiresAuth: true
            }
        },
        {
            name: 'canvas',
            path: '/canvas',
            component: canvas,
            meta: {

                requiresAuth: true
            }
        },
        {
            name: 'getCode',
            path: '/getCode',
            component: getCode,
            meta: {

                requiresAuth: true
            }
        },



        {
            path: '/',
            component: () => import('../components/common/Home.vue'),
            meta: {
                title: '自述文件',
                requiresAuth: true
            },
            children: [{
                    path: '/dashboard',
                    component: () => import('../components/page/Dashboard.vue'),
                    meta: {
                        title: '首页',
                        requiresAuth: true

                    }
                },
                {
                    path: '/Popup',
                    component: () => import('../components/page/Popup.vue'),
                    meta: {
                        title: '弹窗',
                        requiresAuth: true

                    }
                },
                {
                    path: '/tabs',
                    component: () => import('../components/page/tabs.vue'),
                    meta: {
                        title: '消息',
                        requiresAuth: true

                    }
                },

                {
                    path: '/upload-excel',
                    component: () => import('../components/page/upload-excel.vue'),
                    meta: {
                        title: '导入',
                        requiresAuth: true

                    }
                },
                {
                    path: '/refresh',
                    component: () => import('../components/page/refresh.vue'),

                },
                {
                    path: '/goods',
                    component: () => import('../components/page/goods.vue'),
                    meta: {
                        title: '发货',
                        requiresAuth: true
                    }
                },
                {
                    path: '/CustomerSource',
                    component: () => import('../components/page/CustomerSource.vue'),
                    meta: {
                        title: '客户货源',
                        requiresAuth: true
                    }
                },
                {

                    path: '/PlatformSourcing',
                    component: () => import('../components/page/PlatformSourcing.vue'),
                    meta: {
                        title: '调度货源',
                        requiresAuth: true
                    }
                },
                {
                    path: '/MyOrder',
                    component: () => import('../components/page/MyOrder.vue'),
                    meta: {
                        title: '我的订单',
                        requiresAuth: true
                    }
                },
                {
                    path: '/MyWaybill',
                    component: () => import('../components/page/MyWaybill.vue'),
                    meta: {
                        title: '我的运单',
                        requiresAuth: true
                    }
                },
                {
                    path: '/DispatchOrder',
                    component: () => import('../components/page/DispatchOrder.vue'),
                    meta: {
                        title: '调度订单',
                        requiresAuth: true
                    }
                },

                {

                    path: '/DispatchWaybill',
                    component: () => import('../components/page/DispatchWaybill.vue'),
                    meta: {
                        title: '调度运单',
                        requiresAuth: true
                    }
                },
                {

                    path: '/MyDriver',
                    component: () => import('../components/page/MyDriver.vue'),
                    meta: {
                        title: '我的司机',
                        requiresAuth: true
                    }
                },
                {

                    path: '/MyCustomers',
                    component: () => import('../components/page/MyCustomers.vue'),
                    meta: {
                        title: '我的客户',
                        requiresAuth: true
                    }
                },
                {

                    path: '/FundDetails',
                    component: () => import('../components/page/FundDetails.vue'),
                    meta: {
                        title: '资金明细查询',
                        requiresAuth: true
                    }
                },
                {

                    path: '/DeliveryDetails',
                    component: () => import('../components/page/DeliveryDetails.vue'),
                    meta: {
                        title: '发货明细查询',
                        requiresAuth: true
                    }
                },
                {

                    path: '/CustomerOrderQuery',
                    component: () => import('../components/page/CustomerOrderQuery.vue'),
                    meta: {
                        title: '客服订单查询',
                        requiresAuth: true,
                        permission: true
                    }
                },
                {

                    path: '/CustomerWaybillQuery',
                    component: () => import('../components/page/CustomerWaybillQuery.vue'),
                    meta: {
                        title: '客服运单查询',
                        permission: true,
                        requiresAuth: true
                    }
                },
                {
                    path: '/SchedulingOrderQuery',
                    component: () => import('../components/page/SchedulingOrderQuery.vue'),
                    meta: {
                        title: '调度订单查询',
                        requiresAuth: true
                    }
                },
                {
                    path: '/DispatchWaybillQuery',
                    component: () => import('../components/page/DispatchWaybillQuery.vue'),
                    meta: {
                        title: '调度运单查询',
                        requiresAuth: true
                    }
                },
                {
                    path: '/MarketQuotation',
                    component: () => import('../components/page/MarketQuotation.vue'),
                    meta: {
                        title: '市场行情',
                        requiresAuth: true
                    }
                },
                {
                    path: '/ReferenceMarket',
                    component: () => import('../components/page/ReferenceMarket.vue'),
                    meta: {
                        title: '参考行情',
                        requiresAuth: true
                    }
                },
                {
                    path: '/HistoricalMarket',
                    component: () => import('../components/page/HistoricalMarket.vue'),
                    meta: {
                        title: '历史行情',
                        requiresAuth: true
                    }
                },
                {
                    path: '/PlatformDriver',
                    component: () => import('../components/page/PlatformDriver.vue'),
                    meta: {
                        title: '平台司机',
                        requiresAuth: true
                    }
                },
                {
                    path: '/404',
                    component: () => import('../components/page/404.vue'),
                    meta: {
                        title: '404',
                        requiresAuth: true
                    }
                },
                {
                    path: '/403',
                    component: () => import('../components/page/403.vue'),
                    meta: {
                        title: '403',
                        requiresAuth: true
                    }
                },
                {
                    path: '/news',
                    component: () => import('../components/page/news.vue'),
                    meta: {
                        title: 'news'
                    }
                }
            ]
        },
        {
            path: '/login',
            component: () => import('../components/Login/Login.vue'),
            meta: {
                title: '登录'
            }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    if (to.meta.requiresAuth) {
        //如需鉴权，则先拿到保存在localStrorage里面的信息，然后再判断
        let cookies = document.cookie;

        if (cookies.length > 0) {
            // console.log(111)
            let arrCookie = cookies.split(";");
            let Token = false;
            if (arrCookie.length == 1) {
                for (var i = 0; i < arrCookie.length; i++) {
                    var arr = arrCookie[i].split("=");
                    if ("Token" == arr[0]) {
                        Token = true;
                    }
                }
                if (Token) {
                    next();
                } else {
                    next('/login');
                }
            } else {
                for (var i = 0; i < arrCookie.length; i++) {
                    var arr = arrCookie[i].split("=");
                    if (" Token" == arr[0]) {
                        Token = true;
                    }
                }
                if (Token) {
                    next();
                } else {
                    next('/login');
                }
            }


        } else {
            next('/login');
        }
    } else {
        next();
    }

});

export default router;