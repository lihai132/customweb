<template>
    <div class="orderBox">
        <div class="MyOrder">
            <div class="left">
                <div class="list-search">
                    <div class="search-inout">
                        <el-autocomplete
                            class="inline-input"
                            v-model="search"
                            :fetch-suggestions="querySearch"
                            placeholder="请输入内容"
                            :trigger-on-focus="false"
                            @select="startChang"
                            style="width:240px;"
                        ></el-autocomplete>
                    </div>
                    <div class="search-but">
                        <el-button
                            size="mini"
                            type="primary"
                            prefix-icon="el-icon-search"
                            @click="headSearch"
                            :disabled="But"
                            style="width:80px"
                        >搜索</el-button>
                    </div>
                </div>
                <div class="state">
                    <el-tabs
                        v-model="activeName3"
                        @tab-click="handleClick1"
                        style="width:100%;"
                        class="tabs"
                    >
                        <el-tab-pane label="全部" name="246"></el-tab-pane>
                        <el-tab-pane label="待签约" name="2"></el-tab-pane>
                        <el-tab-pane label="待收货" name="4"></el-tab-pane>
                        <el-tab-pane label="待关闭" name="6"></el-tab-pane>
                    </el-tabs>
                </div>
                <ul v-bind:class="[box1 == true ? 'list':'leftList']">
                    <li
                        v-bind:class="[activeClass == index ? 'actived':'', errorClass]"
                        v-for="(item,index) in tableData"
                        :key="index"
                        @click="details(item,index)"
                        ref="content"
                    >
                        <div class="top">
                            <div :class="item.listType"></div>
                            <div class="start">{{item.zct}}</div>
                            <div class="goto">
                                <img src="../../assets/img/jiantou.png" alt />
                            </div>
                            <div class="end">{{item.xct}}</div>
                        </div>
                        <div class="center">
                            <div class="name">{{item.on}}</div>

                            <div class="time">{{item.ctm}}</div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="chat" v-if="box1">
                <div class="chat-top">
                    <div class="start">{{head.start}}</div>
                    <div class="center">
                        <div class="start-icon">
                            <img src="../../assets/img/start1.png" alt />
                        </div>
                        <div class="center-icon">
                            <div class="distance">{{head.dis}}&nbsp;公里</div>
                            <!-- <img src="../../assets/img/xuxian1.png" alt /> -->
                        </div>
                        <div class="end-icon">
                            <img src="../../assets/img/end1.png" alt />
                        </div>
                    </div>
                    <div class="end">{{head.end}}</div>
                </div>
                <div class="chat-nav" ref="chatNav">
                    <div :class="item.class" v-for="(item,index) in chatList" :key="index">
                        <div class="list-title">
                            <div class="type">{{item.opte}}</div>
                            <div class="name">{{item.opne}}</div>
                            <!-- <div class="phone">{{item.opph}}</div> -->
                            <div class="time">{{item.optm}}</div>
                            <div class="main">我</div>
                        </div>
                        <div class="list-text" v-if="chat.opdc?false:true">
                            <div class="div-text">{{item.opdc}}</div>
                        </div>
                    </div>
                </div>
                <div class="chat-input">
                    <div class="operation">
                        <div class="operation-left">
                            <!-- <el-button
                                size="mini"
                                style="width:100px;"
                                type="primary"
                                icon="el-icon-s-promotion"
                            >分享</el-button>-->
                            <el-button
                                size="mini"
                                style="width:100px;"
                                type="primary"
                                icon="el-icon-lx-forwardfill"
                                v-if="button5"
                                @click="Withdraw"
                            >撤单</el-button>
                            <el-button
                                size="mini"
                                style="width:100px;"
                                type="primary"
                                icon="el-icon-lx-roundcheck"
                                v-if="button6"
                                @click="Receipt"
                            >确认收货</el-button>
                        </div>
                        <!-- <div class="operation-right">
                            <el-button size="mini" @click="modify">修改订单</el-button>
                            <el-button size="mini" @click="orderMessage">完善收发信息</el-button>
                            <el-button size="mini" @click="uploadPhotos">上传货物照片</el-button>
                        </div>-->
                    </div>
                    <div class="chatInformation">
                        <el-input
                            type="textarea"
                            :rows="2"
                            placeholder="请输入内容"
                            v-model="textarea"
                            :maxlength="64"
                        ></el-input>
                    </div>
                    <div class="but">
                        <el-button
                            size="mini"
                            style="width:80px;"
                            @click="speak"
                            :disabled="chatButton"
                        >发送</el-button>
                    </div>
                </div>
            </div>
            <div class="right" v-if="box1">
                <div class="tab">
                    <el-tabs v-model="activeName1" @tab-click="handleClick">
                        <el-tab-pane label="运单信息" name="first">
                            <div class="news" ref="copy">
                                <div class="news1">
                                    <div class="news1-left">运单号：{{head.ocd}}</div>
                                    <div class="news1-right">{{head.time}}</div>
                                </div>
                                <div
                                    class="news1"
                                >{{news.on?news.on:""}}{{news.dwv?"、"+news.dwv:""}}</div>
                                <div
                                    class="news1"
                                >{{news.te?news.te:""}}{{news.ct?"、"+news.ct:""}}{{news.cl?"、"+news.cl:""}}</div>
                                <div class="news1">装卸日期：{{orderData.zxTime}}</div>
                                <div class="time"></div>
                                <div class="price">
                                    <div class="price-list">
                                        <div class="price-left">
                                            货主应付运费:
                                            <span class="num">{{news.goodsPrice}}</span>&nbsp;元
                                            <!-- <span class="num">1000000</span>&nbsp;元 -->
                                        </div>
                                        <div class="price-right">
                                            <span class="text">已付:&nbsp;</span>
                                            <span class="num">{{news.goodsPaid}}</span>&nbsp;元
                                            <!-- <span class="num">1000000</span>&nbsp;元 -->
                                        </div>
                                    </div>
                                    <div class="price-list">
                                        <div class="price-left">
                                            司机应收运费:
                                            <span class="num">{{news.driverPrice}}</span>&nbsp;元
                                        </div>
                                        <div class="price-right">
                                            <span class="text">已收:&nbsp;</span>
                                            <span class="num">{{news.driverPaid}}</span>&nbsp;元
                                        </div>
                                    </div>
                                    <div class="price-list">
                                        <div class="price-left">
                                            服务费:
                                            <span class="num">{{news.informationFee}}</span>&nbsp;元
                                        </div>
                                        <div class="price-right">
                                            <span class="text">已付:&nbsp;</span>
                                            <span class="num">{{news.Paid}}</span>&nbsp;元
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="订单信息" name="second">
                            <div class="news" ref="copy">
                                <div class="news1">
                                    <div class="news1-left">订单号：{{orderNews.ocd}}</div>
                                    <div class="news1-right">{{orderNews.ctm}}</div>
                                </div>
                                <div
                                    class="news1"
                                >{{news.on?news.on:""}}{{news.dwv?"、"+news.dwv:""}}</div>
                                <div
                                    class="news1"
                                    v-if="orderNews.cartNews"
                                >{{orderNews.cartNews?orderNews.cartNews:""}}</div>
                                <div
                                    class="news1"
                                >装货日期：{{orderNews.zdy?orderNews.zdy+" ":""}}{{orderNews.zte?orderNews.zte:""}}</div>
                                <div class="time"></div>
                                <div class="news1" v-if="orderNews.remarks">
                                    <el-tooltip
                                        class="item"
                                        effect="dark"
                                        :content="orderNews.remarks"
                                        placement="top"
                                    >
                                        <span
                                            class="text"
                                        >{{orderNews.remarks?orderNews.remarks+" ":""}}</span>
                                    </el-tooltip>

                                    <!-- {{orderNews.remarks?orderNews.remarks+" ":""}} -->
                                </div>
                                <div class="orderNews">
                                    <div class="yuan">调</div>
                                    <!-- <div class="name">{{orderNews.dne}}</div> -->
                                    <div class="name">{{orderNews.dne}}</div>
                                    <div class="phone">{{orderNews.dph}}</div>
                                </div>

                                <div class="time"></div>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="详细地址" name="third">
                            <div class="start">
                                <div class="yuan">发</div>
                                <div
                                    class="text"
                                >{{chat.fhr?chat.fhr:""}}&nbsp;&nbsp;&nbsp;{{chat.fhrp?chat.fhrp+"":""}}</div>
                            </div>
                            <div class="line"></div>
                            <div class="startAddress">
                                <div class="yuan"></div>
                                <div class="text" v-if="news.zad">{{news.zad}}</div>
                            </div>
                            <div class="line1"></div>
                            <div class="startAddress">
                                <div class="yuan"></div>
                                <div class="text" v-if="news.zadp">{{news.zadp}}</div>
                            </div>
                            <div class="line1"></div>
                            <div class="endAddress">
                                <div class="yuan"></div>
                                <div class="text" v-if="news.xad">{{news.xad}}</div>
                            </div>
                            <div class="line1"></div>
                            <div class="endAddress">
                                <div class="yuan"></div>
                                <div class="text" v-if="news.xadp">{{news.xadp}}</div>
                            </div>
                            <div class="line"></div>
                            <div class="end">
                                <div class="yuan">收</div>
                                <div
                                    class="text"
                                >{{chat.shr?chat.shr:""}}&nbsp;&nbsp;&nbsp;{{chat.shrp?chat.shrp+"":""}}</div>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="图片信息" name="fourth">
                            <div class="picture">
                                <el-tabs
                                    v-model="activeName2"
                                    @tab-click="handleClick2"
                                    style="width:400px;"
                                    class="tabs"
                                >
                                    <el-tab-pane label="货物" name="first">
                                        <div class="cargoPicture">
                                            <img
                                                :src="item"
                                                preview="1"
                                                preview-textss
                                                alt
                                                v-for="(item,index) in cargoPicture1"
                                                :key="index"
                                            />
                                        </div>
                                    </el-tab-pane>
                                    <el-tab-pane label="装货" name="second">
                                        <div class="cargoPicture">
                                            <img
                                                :src="item"
                                                preview="1"
                                                preview-textss
                                                alt
                                                v-for="(item,index) in cargoPicture2"
                                                :key="index"
                                            />
                                        </div>
                                    </el-tab-pane>
                                    <el-tab-pane label="卸货" name="third">
                                        <div class="cargoPicture">
                                            <img
                                                :src="item"
                                                preview="1"
                                                preview-textss
                                                alt
                                                v-for="(item,index) in cargoPicture3"
                                                :key="index"
                                            />
                                        </div>
                                    </el-tab-pane>
                                    <el-tab-pane label="回单" name="fourth">
                                        <div class="cargoPicture">
                                            <img
                                                :src="item"
                                                preview="1"
                                                preview-textss
                                                alt
                                                v-for="(item,index) in cargoPicture4"
                                                :key="index"
                                            />
                                        </div>
                                    </el-tab-pane>
                                </el-tabs>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
                <div class="tab-operation">
                    <a
                        href="javascript:;"
                        class="operation-text"
                        v-if="button1"
                        @click="operation"
                    >修改地址</a>
                    <a
                        href="javascript:;"
                        class="operation-text"
                        v-if="button2"
                        @click="payment"
                    >支付运费</a>
                    <a
                        href="javascript:;"
                        class="operation-text"
                        v-if="button3"
                        @click="adjustment"
                    >申请代付</a>
                    <a
                        href="javascript:;"
                        class="operation-text"
                        v-if="button4"
                        @click="rectify"
                    >冲正运费</a>
                    <a
                        href="javascript:;"
                        class="operation-text bar_btn copy_btn"
                        @click="copy"
                        :data-clipboard-text="message"
                    >复制</a>
                    <!-- 整车运输 ● 中象福达 ● 门到门服务
                    运单编号：
                    负责调度：调度 185694235665
                    装货联系：
                    卸货联系：
                    运费结款：货到现付 6500元
                    运输司机：Android司机1 13910000044
                    运输车辆：川S560V1 高栏 8.7米
                    运输货物：冰粒 100.00吨 200.00方
                    装货时间：2020-06-12 16:27
                    装货地址：天津 天津市 河西区 (见定位)
                    卸货地址：河北省 邯郸市 肥乡区 (见定位)-->
                </div>
                <div class="dispatchNew">
                    <div class="yuan">司</div>
                    <div class="name">{{this.orderData.cn}}</div>
                    <div class="name">{{this.orderData.cne}}</div>
                    <div class="phone">{{this.orderData.cph}}</div>
                    <a href="javascript:;" class="revoke" @click="replace" ref="revoke">{{mapName}}</a>
                </div>
                <div class="map">
                    <iframe
                        :src="src"
                        frameborder="0"
                        width="100%"
                        height="100%"
                        scrolling="no"
                        v-if="map1"
                    ></iframe>
                    <div class="addressList" v-if="map2">
                        <mapList></mapList>
                    </div>
                </div>
            </div>
            <div class="box3" v-if="box3">暂无数据</div>
        </div>
        <receipt ref="receipt" @Refresh="Refresh"></receipt>
        <close ref="close"></close>
        <perfect ref="perfect" @refresh="refresh"></perfect>
        <adjustment ref="adjustment" @refresh="refresh"></adjustment>
        <loading ref="loading"></loading>
        <discharge ref="discharge"></discharge>
        <single ref="single"></single>
        <Signing ref="Signing"></Signing>
        <payment ref="payment" @paymentOpen="paymentOpen"></payment>
        <rectify ref="rectify" @refresh="refresh"></rectify>
        <Withdraw ref="Withdraw" @Refresh="Refresh"></Withdraw>
    </div>
</template>
<script>
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
import '../../assets/css/table/table.css';
import '../../assets/css/head/head.css';
import Clipboard from 'clipboard';
import receipt from '../popup/Receipt.vue';
import close from '../popup/close.vue';
import perfect from '../popup/perfect.vue';
import adjustment from '../popup/adjustment.vue';
import loading from '../popup/loading.vue';
import discharge from '../popup/discharge.vue';
import single from '../popup/single.vue';
import Signing from '../popup/Signing.vue';
import payment from '../popup/payment.vue';
import rectify from '../popup/rectify.vue';
import Withdraw from '../popup/Withdraw.vue';
import mapList from './mapList.vue';
export default {
    data() {
        return {
            button1: true,
            button2: true,
            button3: true,
            button4: true,
            button5: true,
            button6: true,
            copyBut: false,
            chatButton: false,
            navShow: true,
            But: false,
            loginData: {},
            sk: '',
            disabled: false,
            box1: true,
            box2: false,
            box3: false,
            search: '',
            restaurants: '',
            page: 1,
            pageSize: 100,
            tableData: [],
            current: 0,
            show1: true,
            show2: true,
            message: '这是复制的文本',
            errorClass: 'list-nav',
            activeClass: 0,
            cargoPicture: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            src: '',
            textarea: '',
            number: { name: '', phone: '' },
            activeName1: 'first',
            activeName2: 'first',
            activeName3: '246',
            offerType: [],

            head: {
                start: '',
                end: '',
                zad: '',
                xad: '',
                startAddress: '',
                endAddress: '',
                dis: '',
                ocd: '',
                time: ''
            },
            news: {
                dw: '',
                dv: '',
                on: '',
                qyf: '',
                zdy: '',
                zte: '',
                ct: '',
                cl: '',
                te: '',
                pte: '',
                zxte: '',
                odc: '',
                pic: '',
                dc: '',
                zad: '',
                zadp: '',
                xad: '',
                xadp: '',
                goodsPrice: '',
                goodsPaid: '',
                driverPrice: '',
                driverPaid: '',
                informationFee: '',
                Paid: '',
                zeType: '',
                dwv: ''
            },
            orderNews: {},
            chat: {
                fhr: '',
                fhrp: '',
                shr: '',
                shrp: '',
                dlte: '',
                opte: '',
                opme: '',
                optm: '',
                opdc: '',
                bph: '',
                shr: '',
                shrp: ''
            },
            chatList: [],
            zid: '',
            xid: '',
            wid: '',
            tid: '',

            orderData: {},
            popup2: {},
            detailsData: {
                item: {},
                index: ''
            },
            truckType: ['平板', '高栏', '中栏', '低栏', '高低板', '厢车', '自卸', '保温', '冷藏', '危险品', '集装箱', '特种'],
            conductor: [
                '4.2米',
                '5.0米',
                '6.2米',
                '6.8米',
                '7.2米',
                '7.7米',
                '7.8米',
                '8.2米',
                '8.7米',
                '9.6米',
                '12.5米',
                '13.0米',
                '14.6米',
                '15.0米',
                '16.5米',
                '17.5米',
                '13.5米'
            ],
            cargoPicture1: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            cargoPicture2: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            cargoPicture3: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            cargoPicture4: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            activeType: 246,
            map1: true,
            map2: false,
            mapName: '途经'
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.CustomerData;
        this.getData(246);
        this.startProvinceChange();
    },
    methods: {
        copy() {
            let news = this.$refs.copy;

            let text = news.innerText;
            this.message = text;
            // console.log(this.message);
            var clipboard = new Clipboard('.copy_btn');
            clipboard.on('success', (e) => {
                this.open1('复制成功');
                this.copyBut = true;
                setTimeout(() => {
                    this.copyBut = false;
                }, 1000);
            });

            clipboard.on('error', (e) => {
                this.open1('复制失败');
                this.copyBut = true;
                setTimeout(() => {
                    this.copyBut = false;
                }, 1000);
            });
        },
        //头部搜索
        headSearch() {
            this.getData(this.activeType);
            // console.log(this.activeClass);
            this.searchBut = true;
            setTimeout(() => {
                this.searchBut = false;
            }, 1000);
        },
        async getData(type) {
            let action = this.$store.state.Login.url + '/30017';
            // this.loginData = this.$store.state.Login.loginData;

            var bd = {
                tid: this.loginData.tid,
                st: type,
                ss: this.search,
                pg: this.page,
                sz: this.pageSize
            };
            var hd = {
                pi: 30017,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30017', hd, bd);
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                let data = {};
                if (this.loginData.em == 0) {
                    data = JSON.parse(res.data.bd);
                }
                if (this.loginData.em == 1) {
                    let decryptData = encryptUtil.decrypt(res.data.bd, this.sk);
                    data = JSON.parse(decryptData);
                }
                let tableData = data.olst;
                if (tableData.length == 0) {
                    this.tableData = [];
                    this.box1 = false;
                    this.box2 = false;
                    this.box3 = true;
                    // this.navShow = false;
                } else {
                    this.box1 = true;
                    this.box2 = true;
                    this.box3 = false;
                    tableData.forEach((item) => {
                        item.ctm = item.ctm.slice(0, 16);
                        if (item.st == 2) {
                            item.listType = 'type1';
                        }
                        if (item.st == 4) {
                            item.listType = 'type2';
                        }
                        if (item.st == 6) {
                            item.listType = 'type3';
                        }
                    });
                    this.tableData = tableData;
                    this.details(this.tableData[0], 0);
                }
            } else {
                this.open2(res.data.hd.rmsg);
            }

            this.But = true;
            setTimeout(() => {
                this.But = false;
            }, 1000);
        },
        //处理货源数据
        startProvinceChange() {
            // this.loginData = this.$store.state.Login.CustomerData;

            var storage = window.localStorage;
            let AddressList = JSON.parse(storage.AddressList);
            this.restaurants = AddressList;

            // tableData.forEach(item => {
            //     this.restaurants.forEach(e => {
            //         if (item.zid == e.id) {
            //             item.zid = e.value;
            //         }
            //         if (item.xid == e.id) {
            //             item.xid = e.value;
            //         }
            //     });
            // });
            // this.tableData = tableData;
        },
        querySearch(queryString, cb) {
            var restaurants = this.restaurants;
            var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
            // console.log(queryString, cb);
        },

        createStateFilter(queryString) {
            return (houseNumber) => {
                return houseNumber.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1;
            };
        },

        loadAll() {
            return this.restaurants;
        },
        startChang(item) {},
        //运单详情
        async details(item, index) {
            console.log(item);
            this.textarea = '';
            this.activeName2 = 'first';
            this.cargoPicture = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            this.cargoPicture1 = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            this.cargoPicture2 = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            this.cargoPicture3 = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            this.cargoPicture4 = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            this.activeClass = index;
            this.detailsData.item = item;
            this.$store.commit('setNewsData', item);
            this.orderDetails(item);
            this.detailsData.index = index;
            this.head.start = item.zct;
            this.head.end = item.xct;
            let action = this.$store.state.Login.url + '/30018';
            // this.loginData = this.$store.state.Login.CustomerData;
            // console.log(item);

            // console.log(this.loginData);
            this.tid = this.loginData.tid;
            this.wid = item.wid;

            //地图轨迹
            // this.src = './map.html?tid=' + this.loginData.tid + '&wid=' + this.wid + '&si=' + this.loginData.si;
            this.getMap();
            var bd = {
                tid: this.loginData.tid,
                wid: item.wid
            };
            var hd = {
                pi: 30018,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30018', hd, bd);
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                let data = {};
                if (this.loginData.em == 0) {
                    data = JSON.parse(res.data.bd);
                }
                if (this.loginData.em == 1) {
                    let decryptData = encryptUtil.decrypt(res.data.bd, this.sk);
                    data = JSON.parse(decryptData);
                }
                if (this.activeType != 6) {
                    let startTime = data.qzte.split(' ');
                    // data.startTime = startTime[0];

                    let endTime = data.qxte.split(' ');
                    // data.endTime = endTime[0];
                    data.zxTime = startTime[0] + ' ' + '-' + ' ' + endTime[0];
                } else {
                    let startTime = data.zte.split(' ');
                    // data.startTime = startTime[0];

                    let endTime = data.xte.split(' ');
                    // data.endTime = endTime[0];
                    data.zxTime = startTime[0] + ' ' + '-' + ' ' + endTime[0];
                }

                this.orderData = data;
                this.$store.commit('setOrderData', this.orderData);
                console.log(data);
                if (data.did > 0) {
                    // this.disabled = true;
                }
                this.head.startAddress = data.zad;
                this.head.endAddress = data.xad;
                this.head.dis = data.dis;
                this.head.ocd = data.wcd;
                this.head.time = data.ctm;

                //费用信息
                this.news.goodsPrice = data.yf;
                this.news.goodsPaid = data.pyf;
                this.news.driverPrice = data.dyf;
                this.news.driverPaid = data.pdyf;
                this.news.informationFee = data.dch;
                this.news.Paid = data.pdch;
                if (this.activeType == 6) {
                    //  this.news.zxTime = data.pdch;
                }

                //处理货物图片
                this.opc(data.opc);
                this.zpc(data.zpc);
                this.xpc(data.xpc);
                this.hdpc(data.hdpc);
                console.log(item);
                if (data.zadp) {
                    // console.log(111);
                } else {
                    this.show1 = false;
                    this.show2 = false;
                }

                if (item.st == 2) {
                    this.button1 = true;
                    this.button2 = false;
                    this.button3 = false;
                    this.button4 = false;
                    this.button5 = true;
                    this.button6 = false;
                }
                if (item.st == 4) {
                    this.button1 = true;
                    this.button2 = true;
                    this.button3 = true;
                    this.button4 = true;
                    this.button5 = true;
                    this.button6 = true;
                    // if (data.wt == 1) {
                    //     this.button4 = true;
                    // }
                    // if (data.wt == 2) {
                    //     this.button4 = false;
                    // }
                }
                if (item.st == 6) {
                    this.button1 = true;
                    this.button2 = true;
                    this.button3 = true;
                    this.button4 = true;
                    this.button5 = false;
                    this.button6 = false;
                    // if (data.wt == 1) {
                    //     this.button4 = true;
                    // }
                    // if (data.wt == 2) {
                    //     this.button4 = false;
                    // }
                }

                // console.log(item);
                this.news.dw = data.dw;
                this.news.dv = data.dv;
                this.news.on = data.on;
                this.news.zdy = data.zdy;
                this.news.zte = data.zte;
                this.news.qyf = data.dyf;
                this.news.dc = data.dc;
                this.news.zad = data.zad;
                this.news.zadp = data.zadp;
                this.news.xad = data.xad;
                this.news.xadp = data.xadp;
                console.log(data.dv);
                if ((data.dv + '').indexOf('-') == -1) {
                    if (data.dv == 0) {
                        this.news.dwv = data.dw + '吨';
                    } else {
                        this.news.dwv = data.dw + '吨' + data.dv + '方';
                    }
                } else {
                    this.news.dwv = data.dw + '吨' + data.dv + '方';
                }

                this.truckType.map((item, index) => {
                    if (this.orderData.ct == index + 1) {
                        this.orderData.ctType = item;
                        this.news.ct = item;
                    }
                });
                this.conductor.map((item, index) => {
                    if (this.orderData.cl == index + 1) {
                        this.orderData.clType = item;
                        this.news.cl = item;
                    }
                });

                // let ct = '';
                // let ctArr = data.ct.split(',');
                // ctArr.forEach(item => {
                //     ct += item + '、';
                // });
                // this.news.ct = ct.substring(0, ct.length - 1);

                // let cl = '';
                // let clArr = data.cl.split(',');
                // clArr.forEach(item => {
                //     cl += item + '、';
                // });
                // this.news.cl = cl.substring(0, cl.length - 1);

                let odc = '';

                let odcArr = data.odc.split(',');
                odcArr.forEach((item) => {
                    odc += item + '、';
                });
                this.news.odc = odc.substring(0, odc.length - 1);

                if (item.te == 1) {
                    this.news.te = data.te = '整车';
                } else {
                    this.news.te = data.te = '配货';
                }
                if (item.pte == 1) {
                    this.news.pte = data.pte = '平台代付';
                } else {
                    this.news.pte = data.pte = '货到现付';
                }

                if (item.zxte == 1) {
                    this.news.zxte = data.zxte = '一装一卸';
                }
                if (item.zxte == 2) {
                    this.news.zxte = data.zxte = '一装两卸';
                }
                if (item.zxte == 3) {
                    this.news.zxte = data.zxte = '两装一卸';
                }
                if (item.zxte == 4) {
                    this.news.zxte = data.zxte = '两装两卸';
                }

                this.chat.fhr = data.fhr;
                this.chat.fhrp = data.fhrp;
                this.chat.shr = data.shr;
                this.chat.shrp = data.shrp;

                // console.log(this.number[0].name)
                // this.number[0].name = data.dne;
                // this.number[0].phone = data.dne;
                if (data.dtp == 3) {
                    data.dtp = '调度';
                }
                if (data.dtp == 4) {
                    data.dtp = '客服';
                }
                if (data.dtp == 5) {
                    data.dtp = '经纪人';
                } else {
                    data.dtp = '';
                }
                this.number = { name: data.dne, phone: data.dph };
                if (this.number.name.length == 0 && this.number.phone.length == 0) {
                    this.number = {};
                }

                let Journal = {
                    si: this.loginData.si,
                    tid: this.loginData.tid,
                    wid: item.wid
                };
                this.Journal(Journal);

                this.zid = data.zid;
                this.wid = data.wid;
                let makeData = {
                    tid: this.loginData.tid,
                    xid: data.xid,
                    zid: data.zid,
                    ss: '',
                    pg: 1,
                    sz: 100
                };
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //订单详情
        async orderDetails(item) {
            let action = this.$store.state.Login.url + '/30004';
            var bd = {
                tid: this.loginData.tid,
                oid: item.oid
            };
            var hd = {
                pi: 30004,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30004', hd, bd);
            let rid = res.data.hd.rid;

            if (rid >= 0) {
                let data = {};
                if (this.loginData.em == 0) {
                    data = JSON.parse(res.data.bd);
                }
                if (this.loginData.em == 1) {
                    let decryptData = encryptUtil.decrypt(res.data.bd, this.sk);
                    data = JSON.parse(decryptData);
                }

                if (data.ct.length == 0 && data.cl.length == 0) {
                    data.cartNews = '';
                }
                if (data.ct.length > 0 && data.cl.length == 0) {
                    data.cartNews = data.ct;
                }
                if (data.ct.length == 0 && data.cl.length > 0) {
                    data.cartNews = data.cl;
                }
                if (data.ct.length > 0 && data.cl.length > 0) {
                    data.cartNews = data.ct + '、' + data.cl;
                }

                if (data.dc.length == 0 && data.odc.length == 0) {
                    data.remarks = '';
                }
                if (data.dc.length > 0 && data.odc.length == 0) {
                    data.remarks = data.dc;
                }
                if (data.dc.length == 0 && data.odc.length > 0) {
                    data.remarks = data.odc;
                }
                if (data.dc.length > 0 && data.odc.length > 0) {
                    data.remarks = data.odc + '、' + data.dc;
                }

                this.orderNews = data;
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //订单日志记录
        async Journal(data) {
            let action = this.$store.state.Login.url + '/30028';
            var bd = {
                tid: data.tid,
                wid: data.wid,
                pg: this.page,
                sz: this.pageSize
            };
            var hd = {
                pi: 30028,
                si: data.si,
                sq: 9
            };
            let res = await request('/30028', hd, bd);
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                let list = {};
                if (this.loginData.em == 0) {
                    list = JSON.parse(res.data.bd);
                }
                if (this.loginData.em == 1) {
                    let decryptData = encryptUtil.decrypt(res.data.bd, this.sk);
                    list = JSON.parse(decryptData);
                }
                let news = list.olst;
                news.forEach((item) => {
                    if (item.opid == data.tid) {
                        item.class = 'myNews';
                    } else {
                        item.class = 'list';
                    }
                    if (item.opte == 1) {
                        item.opte = '货';
                    }
                    if (item.opte == 2) {
                        item.opte = '司';
                    }
                    if (item.opte == 3) {
                        item.opte = '调';
                    }
                    if (item.opte == 4) {
                        item.opte = '客';
                    }
                    if (item.opte == 5) {
                        item.opte = '经';
                    }
                    if (item.opte == 0) {
                        item.opte = '游';
                    }
                    if (item.opte == -1) {
                        item.opte = '交';
                    }
                    if (item.opte == -2) {
                        item.opte = '中';
                    }
                    if (item.opte == -3) {
                        item.opte = '运';
                    }
                    if (item.opte == -6) {
                        item.opte = '企';
                    }
                });
                this.chatList = news;
                // console.log(this.chatList)
                this.chat.dlte = news.dlte;

                this.chat.opte = news.opte;
                this.chat.opme = news.opme;
                this.chat.optm = news.optm;
                this.chat.opdc = news.opdc;
                this.add();
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        handleClick(tab, event) {},
        handleClick1(tab, event) {
            this.activeType = tab.name;
            // console.log(tab.name);
            if (this.activeType == 2) {
                this.signShow = false;
            } else {
                this.signShow = true;
            }
            if (tab.label == '全部') {
                this.getData(246);
            }
            if (tab.label == '待签约') {
                this.getData(2);
            }
            if (tab.label == '待收货') {
                this.getData(4);
            }
            if (tab.label == '待关闭') {
                this.getData(6);
            }
        },
        handleClick2(tab, event) {},
        open(text) {
            this.$message({
                message: text,
                type: 'success',
                duration: 900
            });
        },
        open() {
            this.$alert('确认已完成支付', {
                confirmButtonText: '确定',
                callback: (action) => {
                    this.refresh();
                }
            });
        },
        paymentOpen() {
            this.$alert('确认已完成支付', {
                confirmButtonText: '确定',
                callback: (action) => {
                    this.Refresh();
                }
            });
        },
        open1(text) {
            this.$message({
                message: text,
                type: 'success',
                duration: 900
            });
        },
        open2(text) {
            this.$message({
                message: text,
                type: 'error    ',
                duration: 900
            });
        },
        submit() {
            this.box1 = true;
            this.box2 = false;
            this.getData();
        },
        //订单留言
        async speak() {
            let action = this.$store.state.Login.url + '/20046';
            this.textarea = this.textarea.trim();
            if (this.textarea.length == 0) {
                this.open2('留言不能为空');
            } else {
                let bd = {
                    tid: this.tid,
                    bid: this.wid,
                    bte: 2,
                    ct: this.textarea
                };
                var hd = {
                    pi: 20046,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/20046', hd, bd);
                let rid = res.data.hd.rid;

                if (rid >= 0) {
                    this.refresh();
                    this.textarea = '';
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }

            this.chatButton = true;
            setTimeout(() => {
                this.chatButton = false;
            }, 1000);
        },
        //刷新页面详情
        refresh() {
            this.details(this.detailsData.item, this.activeClass);
        },
        //刷新列表数据
        Refresh() {
            this.getData(this.activeType);
        },
        //确认收货
        Receipt() {
            this.$refs.receipt.receipt();
        },
        //撤单
        Withdraw() {
            this.$refs.Withdraw.receipt();
        },
        //完善收发信息
        operation() {
            this.$refs.perfect.receipt();
        },
        //支付运费
        payment() {
            // this.popup2 = {
            //     start: this.head.start,
            //     end: this.head.end,
            //     dis: this.head.dis,
            //     tid: this.tid,
            //     si: this.loginData.si,
            //     wid: this.wid
            // };
            // this.$store.commit('setPopup2', this.popup2);
            this.$refs.payment.receipt();
        },
        //冲正运费
        rectify() {
            this.$refs.rectify.receipt();
        },
        //申请代付
        adjustment() {
            this.$refs.adjustment.receipt();
        },
        //获取地图经纬度
        async getMap() {
            let action = this.$store.state.Login.url + '/30078';
            // this.loginData = this.$store.state.Login.loginData;

            var bd = {
                tid: this.loginData.tid,
                wid: this.wid
            };
            var hd = {
                pi: 30078,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30078', hd, bd);
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                let data = {};
                if (this.loginData.em == 0) {
                    data = JSON.parse(res.data.bd);
                }
                if (this.loginData.em == 1) {
                    let decryptData = encryptUtil.decrypt(res.data.bd, this.sk);
                    data = JSON.parse(decryptData);
                }
                console.log(data);
                let list = data.olst;
                // let longitude = [bd.zlng, bd.zlat];
                // let dimension = [bd.xlng, bd.xlat];
                // longitude  dimension
                let start = data.zlng + ',' + data.zlat;
                let end = data.xlng + ',' + data.xlat;
                let center = '';
                let add = '';

                if (list.length == 0) {
                    center = '';
                    add = '';
                } else {
                    center = [list[list.length - 1].log, list[list.length - 1].lat];
                    // let addArr = list[list.length - 1].add;
                    add = list[list.length - 1].add;
                }
                this.src = './map.html?start=' + start + '&end=' + end + '&center=' + center + '&add=' + add;
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //地图切换
        replace() {
            // this.map1 = true;
            // this.map2 = false;
            let text = this.$refs.revoke.innerText;
            // console.log(text);
            if (text == '导航') {
                this.map1 = true;
                this.map2 = false;
                this.mapName = '途经';
            }
            if (text == '途经') {
                this.map1 = false;
                this.map2 = true;
                this.mapName = '导航';
            }
        },
        //货物图片
        opc(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture1 = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture1 = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            }
        },
        //装货图片
        zpc(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture2 = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture2 = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            }
        },
        //卸货图片
        xpc(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture3 = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture3 = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            }
        },
        //回单图片
        hdpc(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture4 = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture4 = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            }
        },
        //订单日志滚动到底部
        add() {
            var div = this.$refs.chatNav;

            //此时必须异步执行滚动条滑动至底部
            setTimeout(() => {
                div.scrollTop = div.scrollHeight;
            }, 0);
        }
    },
    components: {
        receipt,
        close,
        perfect,
        adjustment,
        loading,
        discharge,
        single,
        Signing,
        payment,
        rectify,
        Withdraw,
        mapList
    },
    beforeUpdate() {
        var div = this.$refs.chatNav;
    },
    updated() {
        var div = this.$refs.chatNav;
    }
};
</script>
<style lang="scss" >
.el-header {
    .head {
        .head-start,
        .head-end {
            padding-top: 20px !important;
        }
        .head-center {
            padding-top: 20px;
        }
    }
}
.pswp__bg {
    opacity: 0.5 !important;
}
</style>
<style lang="scss" scoped>
* {
    box-sizing: border-box;
}
.orderBox {
    height: 100%;
}
.MyOrder {
    display: flex;
    width: 100%;
    height: 100%;
    background: #fff;
    padding-bottom: 10px;

    .left {
        width: 350px;
        height: 100%;
        display: flex;
        flex-direction: column;
        position: relative;
        border-top: 1px solid #ebeef5;
        .list-search {
            height: 100px;
            box-sizing: border-box;
            display: flex;
            border-left: 1px solid #ebeef5;
            border-right: 1px solid #ebeef5;
            background: #fff;
            width: 100%;
            padding: 32px 10px;
            position: absolute;
            /deep/.el-input--small .el-input__inner {
                height: 40px !important;
                line-height: 40px !important;
            }
            .search-but {
                margin-left: 5px;
                /deep/.el-button--mini,
                /deep/.el-button--mini.is-round {
                    padding: 12px 15px !important;
                }
            }
        }
        .state {
            padding-top: 100px;
            border-left: 1px solid #ebeef5;
            border-right: 1px solid #ebeef5;
            .tabs {
                /deep/.el-tabs__nav-wrap {
                    padding: 0 10.3%;
                }
                /deep/.el-tabs__header {
                    margin: 0 !important;
                }
            }
        }

        .list {
            box-sizing: border-box;
            width: 350px;
            flex: 1;
            background: #fff;
            border-left: 1px solid #ebeef5;
            overflow: scroll;
            border-bottom: 1px solid #ebeef5;
            border-right: 1px solid #ebeef5;
            .list-nav {
                box-sizing: border-box;
                // background: rgba($color: #caf982, $alpha: 0.5);
                // border-bottom: 1px dashed rgba($color: #000000, $alpha: 0.1);
                height: 78px;
                padding-top: 10px;
                padding-bottom: 10px;
                // box-shadow: 0 0 1px 1px #ccc;
                .top {
                    padding: 5px 10px 10px 10px;
                    // .start {
                    //     width: 160px;
                    // }
                    // .goto {
                    //     widows: 27px;
                    // }
                    // .end {
                    //     width: 160px;
                    // }
                    .type3 {
                        width: 12px;
                        height: 10px;
                        border-radius: 50%;
                        background: #ff7300;
                        margin-left: 5px;
                    }
                    .type2 {
                        width: 12px;
                        height: 10px;
                        border-radius: 50%;
                        background: #59e400;
                        margin-left: 5px;
                    }
                    .type1 {
                        width: 12px;
                        height: 10px;
                        border-radius: 50%;
                        background: #42a2f9;
                        margin-left: 5px;
                    }
                }
                .center {
                    padding: 0 5px 5px 5px;
                }
                .top,
                .center {
                    display: flex;
                    text-align: center;
                    justify-content: space-between;
                    align-items: center;
                    .start,
                    .end {
                        width: 160px;

                        font-size: 13px;
                        line-height: 22px;
                    }
                    .start,
                    .end {
                        padding-right: 15px;
                        padding-left: 10px;
                        text-align: left;
                        font-size: 18px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        display: -webkit-box;
                        -webkit-line-clamp: 1;
                        -webkit-box-orient: vertical;
                    }
                    .end {
                        margin-left: 15px;
                        text-align: left;
                        font-size: 18px;
                        padding-right: 10px;
                    }
                    .goto {
                        width: 27px;
                        img {
                            width: 27px;
                        }
                    }
                    .name {
                        width: 50%;
                        font-size: 13px;
                        line-height: 22px;
                        text-align: left;
                        color: rgba($color: #000000, $alpha: 0.5);
                        margin-left: 30px;
                    }
                    .time {
                        width: 50%;
                        font-size: 12px;
                        line-height: 22px;
                        text-align: center;
                        color: rgba($color: #000000, $alpha: 0.5);
                        margin-right: 6%;
                    }
                }
            }
            .list-nav:hover {
                background: #ebeef5;
            }

            .actived {
                background: #ebeef5;
            }
        }
        .list::-webkit-scrollbar {
            display: none;
        }
        .leftList {
            border-left: 1px solid #ebeef5;
            border-right: 1px solid #ebeef5;
            width: 350px;
            flex: 1;
            padding-top: 100px;
            border-bottom: 1px solid #ebeef5;
        }
    }
    .chat {
        flex: 1;
        border-right: 1px solid #ebeef5;
        display: flex;
        flex-direction: column;
        min-width: 669px;
        .chat-top {
            width: 100%;
            height: 101px;
            padding: 10px 20px;
            padding-top: 20px;
            display: flex;
            align-items: center;
            .start {
                flex: 1;
                text-align: right;
                font-size: 20px;
                font-weight: 550;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 1;
                -webkit-box-orient: vertical;
                margin-right: 20px;
            }

            .end {
                flex: 1;
                text-align: left;
                font-size: 20px;
                font-weight: 550;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 1;
                -webkit-box-orient: vertical;
                margin-left: 20px;
            }
            .center {
                width: 300px;
                display: flex;
                align-items: center;
                padding: 0 10px;
                position: relative;

                .start-icon {
                    position: absolute;
                    top: -26px;
                    left: 0;
                    img {
                        width: 33px;
                        height: 42px;
                    }
                }
                .center-icon {
                    margin-left: 33px;
                    margin-right: 33px;
                    background: url(../../assets/img/xuxian1.png);
                    width: 240px;
                    height: 2px;
                    // padding-top: 20px;
                    margin-top: 14px;
                    position: relative;
                    .distance {
                        position: absolute;
                        top: -30px;
                        width: 100%;
                        text-align: center;
                        font-size: 18px;
                    }
                }
                .end-icon {
                    position: absolute;
                    top: -26px;
                    right: 0;
                    img {
                        width: 33px;
                        height: 42px;
                    }
                }
            }
        }
        .chat-nav {
            flex: 1;
            border-top: 1px solid #ebeef5;
            border-bottom: 1px solid #ebeef5;
            background: #eef2f5;
            overflow: scroll;
            .list {
                margin-top: 8px;
                margin-left: 8px;
                display: flex;
                flex-direction: column;
                .list-title {
                    display: flex;
                    align-items: center;
                    padding-bottom: 8px;
                    .type {
                        height: 30px;
                        width: 30px;
                        text-align: center;
                        border-radius: 15px;
                        font-size: 14px;
                        font-weight: 600;
                        line-height: 30px;
                        color: #fff;
                        background: #009dd9;
                        margin-right: 7px;
                    }
                    .name {
                        font-size: 14px;
                        line-height: 30px;
                        color: rgba($color: #000000, $alpha: 0.4);
                        margin-right: 7px;
                    }
                    .phone {
                        font-size: 14px;
                        line-height: 30px;
                        color: rgba($color: #000000, $alpha: 0.4);
                        margin-right: 15px;
                    }
                    .time {
                        font-size: 14px;
                        line-height: 30px;
                        color: rgba($color: #000000, $alpha: 0.4);
                        margin-right: 7px;
                    }
                    .main {
                        height: 30px;
                        width: 30px;
                        text-align: center;
                        border-radius: 15px;
                        font-size: 14px;
                        font-weight: 600;
                        line-height: 30px;
                        color: #fff;
                        background: #00cc00;
                        margin-right: 7px;
                        display: none;
                    }
                }
                .list-text {
                    margin-left: 37px;
                    margin-right: 37px;
                    width: 50%;
                    margin-top: -10px;
                    // height: 34px;

                    .div-text {
                        background: #fff;
                        color: rgba($color: #000000, $alpha: 0.6);
                        font-size: 14px;
                        line-height: 24px;
                        font-weight: 550;
                        border-radius: 8px;
                        padding: 8px 8px;
                        float: left;
                    }
                }
            }
            .myNews {
                // height: 71px;
                margin-top: 8px;
                margin-left: 8px;
                display: flex;
                flex-direction: column;
                align-items: flex-end;

                text-align: right;
                .list-title {
                    display: flex;
                    align-items: center;
                    // padding-bottom: 8px;
                    .type {
                        height: 32px;
                        width: 32px;
                        text-align: center;
                        border-radius: 15px;
                        font-size: 14px;
                        font-weight: 600;
                        line-height: 30px;
                        color: #fff;
                        background: #009dd9;
                        margin-right: 7px;
                        display: none;
                        padding-top: 1px;
                    }
                    .name {
                        font-size: 14px;
                        line-height: 30px;
                        color: rgba($color: #000000, $alpha: 0.4);
                        margin-right: 7px;
                    }
                    .phone {
                        font-size: 14px;
                        line-height: 30px;
                        color: rgba($color: #000000, $alpha: 0.4);
                        margin-right: 15px;
                    }
                    .time {
                        font-size: 14px;
                        line-height: 30px;
                        color: rgba($color: #000000, $alpha: 0.4);
                        margin-right: 7px;
                    }
                    .main {
                        height: 32px;
                        width: 32px;
                        text-align: center;
                        border-radius: 16px;
                        font-size: 14px;
                        font-weight: 600;
                        line-height: 32px;
                        color: #fff;
                        background: #00cc00;
                        margin-right: 7px;
                        padding-top: 1px;
                    }
                }
                .list-text {
                    width: 50%;
                    margin-left: 37px;
                    margin-right: 37px;
                    text-align: right;
                    margin-top: -8px;
                    // position: relative;

                    .div-text {
                        background: #c4e2fa;
                        color: rgba($color: #000000, $alpha: 0.6);
                        font-size: 14px;
                        line-height: 24px;
                        font-weight: 550;
                        border-radius: 8px;
                        padding: 8px 8px;
                        text-align: left;
                        // width: 100%;
                        float: right;
                        // position: absolute;
                        // right: 10px;
                    }
                }
            }
        }
        .chat-input {
            width: 100%;
            height: 253px;
            position: relative;
            border-bottom: 1px solid #ebeef5;
            .operation {
                display: flex;
                justify-content: space-between;
                padding: 5px 5px;
            }
            .but {
                position: absolute;
                right: 10px;
                bottom: 10px;
            }
            .chatInformation {
                height: 84%;
                /deep/ .el-textarea {
                    height: 100%;
                    /deep/textarea {
                        resize: none !important;
                        height: 100% !important;
                        width: 100%;
                        border: none;
                    }
                }
            }
        }
    }
    .right {
        width: 400px;
        display: flex;
        flex-direction: column;
        min-width: 400px;
        .tab {
            padding: 15px;
            padding-right: 5px;
            height: 320px;

            // border-bottom: 1px solid #e5e5e5;

            /deep/.el-tabs__nav-scroll {
                overflow: hidden;
                padding: 0 4.2%;
            }
            /deep/#pane-first,
            /deep/#pane-second {
                position: relative;
                // padding-bottom: 105px;
                height: 255px;
                .news {
                    position: relative;
                    height: 150px;
                }
                .news1 {
                    padding-left: 10px;
                    padding-right: 10px;
                    display: flex;
                    justify-content: space-between;
                    margin-bottom: 18px;
                    font-size: 16px;
                    line-height: 16px;
                    .text {
                        overflow: hidden;
                        text-overflow: ellipsis;
                        display: -webkit-box;
                        -webkit-line-clamp: 2;
                        -webkit-box-orient: vertical;
                        line-height: 25px;
                    }
                }
                .price {
                    padding-left: 10px;
                    padding-right: 10px;
                    height: 78px;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    position: absolute;
                    bottom: -85px;
                    width: 100%;
                    .price-list {
                        width: 100%;
                        display: flex;
                        justify-content: space-between;
                        margin-bottom: 5px;
                        justify-content: space-between;
                        .price-left,
                        .price-right {
                            font-size: 16px;
                            .num {
                                font-size: 18px;
                                color: red;
                                font-weight: 550;
                            }
                        }
                        .price-right {
                            width: 131.5px;
                            display: flex;
                            .num {
                                display: block;
                                width: 70.2px;
                            }
                        }
                    }
                    .price-list:nth-child(3) {
                        padding-left: 48px;
                        box-sizing: border-box;
                    }
                }
            }

            /deep/#pane-third {
                padding-left: 10px;
                padding-right: 10px;
                .text {
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }
                .start {
                    display: flex;
                    .yuan {
                        width: 32px;
                        height: 32px;
                        border-radius: 16px;
                        background: #409eff;
                        text-align: center;
                        line-height: 32px;
                        color: #fff;
                    }
                    .text {
                        font-size: 18px;
                        margin-left: 10px;
                        line-height: 32px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        display: -webkit-box;
                        -webkit-line-clamp: 1;
                        -webkit-box-orient: vertical;
                    }
                }
                .line {
                    height: 18px;
                    width: 2px;
                    margin-left: 16px;
                    background: url(../../assets/img/xx1.png);
                }
                .line1 {
                    height: 30px;
                    width: 2px;
                    margin-left: 16px;
                    background: url(../../assets/img/xx1.png);
                }
                .startAddress {
                    display: flex;
                    margin-left: 10px;
                    position: relative;
                    // align-items: center;
                    .yuan {
                        width: 14px;
                        height: 14px;
                        border-radius: 16px;
                        background: #409eff;
                        text-align: center;
                        line-height: 32px;
                        color: #fff;
                    }
                    .text {
                        font-size: 14px;
                        margin-left: 10px;
                        line-height: 18px;
                        position: absolute;
                        top: 0px;
                        left: 23px;
                    }
                }
                .endAddress {
                    display: flex;
                    margin-left: 10px;
                    position: relative;
                    // align-items: center;
                    .yuan {
                        width: 14px;
                        height: 14px;
                        border-radius: 16px;
                        background: #ff7000;
                        text-align: center;
                        line-height: 32px;
                        color: #fff;
                    }
                    .text {
                        font-size: 14px;
                        margin-left: 10px;
                        line-height: 18px;
                        position: absolute;
                        top: 0px;
                        left: 23px;
                    }
                }
                .end {
                    display: flex;
                    .yuan {
                        width: 32px;
                        height: 32px;
                        border-radius: 16px;
                        background: #ff7000;
                        text-align: center;
                        line-height: 32px;
                        color: #fff;
                    }
                    .text {
                        font-size: 18px;
                        margin-left: 10px;
                        line-height: 32px;
                    }
                }
            }
            /deep/#pane-fourth {
                /deep/.el-tabs__nav-scroll {
                    overflow: hidden;
                    padding: 0 21%;
                }
                /deep/.el-tabs__nav-wrap::after {
                    display: none;
                }
                /deep/#pane-first,
                /deep/ #pane-second {
                    height: 200px;
                }
                /deep/ #pane-second,
                /deep/ #pane-third {
                    padding-left: 0px;
                    padding-right: 0px;
                }
                .picture {
                    .cargoPicture {
                        display: flex;
                        justify-content: space-around;
                        align-items: center;
                        padding-top: 40px;
                        img {
                            width: 130px;
                            height: 130px;
                        }
                    }
                }
            }
        }
        .tab-operation {
            // display: flex;
            height: 30px;
            display: flex;
            justify-content: space-around;
            align-items: center;
            border-bottom: 1px solid #e5e5e5;
            padding-left: 7px;

            .operation-text {
                line-height: 30px;
                font-size: 14px;
                display: block;
                text-decoration: underline;
                color: #409eff;
            }
        }
        .orderNews {
            display: flex;
            align-items: center;
            height: 68px;
            // border-bottom: 1px solid #e5e5e5;
            padding-left: 10px;
            // padding-right: 25px;
            position: relative;
            .yuan {
                width: 32px;
                height: 32px;
                border-radius: 16px;
                background: #59e400;
                text-align: center;
                line-height: 32px;
                color: #fff;
            }
            .name {
                font-size: 16px;
                margin-left: 10px;
            }
            .phone {
                font-size: 16px;
                margin-left: 18px;
            }
            .revoke {
                font-size: 16px;
                position: absolute;
                font-size: 14px;
                right: 13px;
                color: #409eff;
                text-decoration: underline;
            }
            .tixin {
                font-size: 16px;
                font-weight: 550;
                margin: auto;
                color: rgba($color: #ccc, $alpha: 0.8);
            }
        }
        .dispatchNew {
            display: flex;
            align-items: center;
            height: 68px;
            border-bottom: 1px solid #e5e5e5;
            padding-left: 25px;
            padding-right: 25px;
            position: relative;
            .yuan {
                width: 32px;
                height: 32px;
                border-radius: 16px;
                background: #272f44;
                text-align: center;
                line-height: 32px;
                color: #fff;
            }
            .name {
                font-size: 16px;
                margin-left: 10px;
            }
            .phone {
                font-size: 16px;
                margin-left: 18px;
            }
            .revoke {
                font-size: 16px;
                position: absolute;
                font-size: 14px;
                right: 13px;
                color: #409eff;
                text-decoration: underline;
            }
            .tixin {
                font-size: 16px;
                font-weight: 550;
                margin: auto;
                color: rgba($color: #ccc, $alpha: 0.8);
            }
        }
        .tabs {
            /deep/.el-tabs__nav-scroll {
                // overflow: hidden;
                padding: 0 31%;
            }
            /deep/.el-tabs__header {
                padding: 0;
                position: relative;
                margin: 0 !important;
            }
        }
        .map {
            flex: 1;
            .addressList {
                width: 100%;
                height: 100%;
                // background: red;
            }
        }
    }
    .box3 {
        font-size: 18px;
        line-height: 30px;
        font-weight: 550;
        color: rgba($color: #ccc, $alpha: 0.8);
        text-align: center;
        width: 100%;
        height: 30px;
        margin: auto;
    }
}
</style>