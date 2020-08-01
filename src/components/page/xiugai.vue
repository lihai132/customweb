<template>
    <div class="MyWaybill">
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
                    ></el-autocomplete>
                </div>
                <div class="search-but">
                    <el-button
                        size="mini"
                        type="primary"
                        prefix-icon="el-icon-search"
                        round
                        @click="headSearch"
                        :disabled="searchBut"
                    >搜索</el-button>
                </div>
            </div>
            <div class="state">
                <el-tabs v-model="activeName1" @tab-click="handleClick1" style="width:229px;">
                    <el-tab-pane label="全部" name="0"></el-tab-pane>
                    <el-tab-pane label="待签约" name="2"></el-tab-pane>
                    <el-tab-pane label="待收货" name="4"></el-tab-pane>
                    <el-tab-pane label="待关闭" name="6"></el-tab-pane>
                </el-tabs>
            </div>
            <ul class="list">
                <li
                    v-bind:class="[activeClass == index ? 'actived':'', errorClass]"
                    v-for="(item,index) in tableData"
                    :key="index"
                    @click="details(item,index)"
                    ref="content"
                >
                    <div class="top">
                        <div class="start">{{item.zct}}</div>
                        <div class="goto">
                            <img src="../../assets/img/right.png" alt />
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
        <div class="order-right" v-if="operationShow">
            <el-container>
                <el-header style="height:161px;">
                    <div class="head">
                        <div class="head-start">
                            <div class="start-icon">
                                <img src="../../assets/img/start.png" alt />
                            </div>
                            <div class="abbreviate">{{head.start}}</div>
                            <div class="address">{{head.startAddress}}</div>
                        </div>
                        <div class="head-center">
                            <div class="distance">
                                {{head.dis}}
                                <span>公里</span>
                            </div>
                            <div class="img">
                                <img src="../../assets/img/xuxian.png" alt />
                            </div>
                            <div class="type" v-if="waybillType?true:false">{{waybillType}}</div>
                            <div class="cart1">
                                <div class="tooltip">
                                    <div
                                        class="text"
                                        @mouseenter="enter1()"
                                        @mouseleave="leave1()"
                                        v-if="show1"
                                    ></div>
                                    <div class="triangle" v-if="show1"></div>
                                    <img src="../../assets/img/cart1.png" alt />
                                </div>
                            </div>
                            <div class="cart2">
                                <div class="tooltip">
                                    <div
                                        class="text"
                                        @mouseenter="enter2()"
                                        @mouseleave="leave2()"
                                        v-if="show2"
                                    ></div>
                                    <div class="triangle" v-if="show2"></div>
                                    <img src="../../assets/img/cart2.png" alt />
                                </div>
                            </div>
                            <div class="cart-center">
                                <img src="../../assets/img/center.png" alt />
                            </div>
                        </div>
                        <div class="head-end">
                            <div class="end-icon">
                                <img src="../../assets/img/end.png" alt />
                            </div>
                            <div class="abbreviate">{{head.end}}</div>
                            <div class="address">{{head.endAddress}}</div>
                        </div>
                        <div class="number">{{head.ocd}}</div>
                        <div class="time">{{head.time}}</div>
                    </div>
                </el-header>
                <el-main>
                    <div class="news">
                        <div class="news-left">
                            <div class="CargoInformation">
                                <div class="key">运单信息：</div>
                                <div
                                    class="value"
                                >{{news.on?news.on+"，":""}}{{news.dw?news.dw+"吨":""}}{{news.dv?news.dv+"方，":""}}{{news.zdy?"，"+news.zdy:""}} {{news.zte?news.zte:""}}{{news.te?"，"+news.te:""}}{{news.ct?"，"+news.ct:""}}{{news.cl?"，"+news.cl:""}}{{news.pte?"，货到现付":""}}{{news.zxte?"，"+news.zxte:""}}{{news.odc?"，"+news.odc:""}}</div>
                            </div>
                            <div class="VehicleDemand">
                                <div class="key">运输时间：</div>
                                <div
                                    class="value"
                                >{{news.qzte?news.qzte:""}}&nbsp; 至 &nbsp;{{news.qxte?news.qxte:""}}</div>
                            </div>
                            <div class="other">
                                <div class="key">费用信息：</div>
                                <div
                                    class="value"
                                >应付运费：{{news.yf?news.yf:""}}{{(news.pyf+"").length>0?"，已付运费："+news.pyf:""}}{{news.zxte?"，待付运费："+news.dyf:""}}</div>
                            </div>
                        </div>
                        <div class="news-right">
                            <div class="operation1">
                                <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="operation"
                                >完善收发信息</el-button>
                                <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="loading"
                                >装货</el-button>
                                <!-- <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="adjustment"
                                >申请代付</el-button>-->

                                <!-- <el-button type="primary" style="width:104px;margin: 0;">购买保险</el-button> -->
                            </div>
                            <div class="operation2">
                                <!-- <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="payment"
                                >支付运费</el-button>
                                <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="rectify"
                                >冲正运费</el-button>-->
                                <!-- <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="loading"
                                >装货</el-button>-->
                                <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="discharge"
                                >卸货</el-button>
                                <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="single"
                                >上传回单</el-button>
                            </div>
                            <!-- <div class="operation3">
                                <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="discharge"
                                >卸货</el-button>
                                <el-button
                                    type="primary"
                                    style="width:104px;margin: 0;"
                                    @click="single"
                                >上传回单</el-button>
                            </div>-->
                        </div>
                    </div>
                    <div class="main-nav">
                        <div class="chat">
                            <div class="chat-top">
                                <div class="consignor">
                                    <div class="title">发货人：</div>
                                    <div class="name">{{chat.fhr?chat.fhr+"":"暂无数据"}}</div>
                                    <div class="phone">{{chat.fhrp?"，"+chat.fhrp+"":""}}</div>
                                </div>
                                <div class="consignee">
                                    <div class="title">收货人：</div>
                                    <div class="name">{{chat.shr?chat.shr+"":"暂无数据"}}</div>
                                    <div class="phone">{{chat.shrp?"，"+chat.shrp+"":""}}</div>
                                </div>
                            </div>
                            <div class="chat-nav" ref="chatNav">
                                <div
                                    :class="item.class"
                                    v-for="(item,index) in chatList"
                                    :key="index"
                                >
                                    <div class="list-title">
                                        <div class="type">{{item.opte}}</div>
                                        <div class="name">{{item.opne}}</div>
                                        <div class="phone">{{item.opph}}</div>
                                        <div class="time">{{item.optm}}</div>
                                        <div class="main">我</div>
                                    </div>
                                    <div class="list-text" v-if="chat.opdc">
                                        <span>{{item.opdc}}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="chat-operation">
                                <el-button
                                    size="mini"
                                    plain
                                    style="width:80px;"
                                    @click="Refresh"
                                    :disabled="RefreshBut"
                                >刷新信息</el-button>
                                <el-button
                                    size="mini"
                                    plain
                                    style="width:80px;"
                                    @click="Signing"
                                    v-if="operation1"
                                >签约</el-button>
                                <!-- <el-button
                                    type="success"
                                    size="mini"
                                    plain
                                    style="width:80px;"
                                    @click="Receipt"
                                    v-if="operation2"
                                >确认收货</el-button>-->
                                <el-button
                                    size="mini"
                                    plain
                                    style="width:80px;"
                                    @click="close"
                                    v-if="operation3"
                                >关闭</el-button>
                                <el-button size="mini" plain style="width:80px;">分享</el-button>
                                <el-button
                                    size="mini"
                                    plain
                                    style="width:80px;"
                                    @click="Withdraw"
                                    v-if="operation0"
                                >撤单</el-button>
                            </div>
                            <div class="chat-input">
                                <el-input
                                    class="add-content"
                                    type="textarea"
                                    :autosize="{ minRows: 7, maxRows: 4}"
                                    placeholder="请输入内容"
                                    v-model="textarea"
                                ></el-input>
                                <div class="sendOut">
                                    <el-button
                                        type="primary"
                                        size="mini"
                                        style="width:80px;"
                                        @click="speak"
                                        :disabled="speakBut"
                                    >发送</el-button>
                                </div>
                            </div>
                        </div>
                        <div class="nav-right">
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

                            <div class="vehicle">
                                <el-table
                                    :data="offerType"
                                    border
                                    class="table"
                                    ref="multipleTable"
                                    header-cell-class-name="table-header"
                                    highlight-current-row
                                    :show-header="false"
                                >
                                    <el-table-column
                                        prop="licensePlate"
                                        width="97"
                                        align="center"
                                        :show-overflow-tooltip="true"
                                    ></el-table-column>
                                    <el-table-column
                                        prop="name"
                                        width="100"
                                        align="center"
                                        :show-overflow-tooltip="true"
                                    ></el-table-column>

                                    <el-table-column
                                        prop="type"
                                        width="100"
                                        align="center"
                                        :show-overflow-tooltip="true"
                                    ></el-table-column>

                                    <el-table-column
                                        prop="operation"
                                        :show-overflow-tooltip="true"
                                        align="center"
                                    >
                                        <el-button type="primary" size="mini">刷新轨迹</el-button>
                                    </el-table-column>
                                </el-table>
                            </div>
                            <div class="map">
                                <!-- <ditu></ditu> -->
                                <iframe
                                    :src="src"
                                    frameborder="0"
                                    width="100%"
                                    height="100%"
                                    scrolling="no"
                                ></iframe>
                            </div>
                        </div>
                    </div>
                </el-main>
            </el-container>
        </div>
        <receipt ref="receipt"></receipt>
        <close ref="close" @getData="getData"></close>
        <perfect ref="perfect" @Refresh="Refresh"></perfect>
        <adjustment ref="adjustment"></adjustment>
        <loading ref="loading" @Refresh="Refresh"></loading>
        <discharge ref="discharge" @Refresh="Refresh"></discharge>
        <single ref="single" @Refresh="Refresh"></single>
        <Signing ref="Signing" @getData="getData" @open="open"></Signing>
        <payment ref="payment"></payment>
        <rectify ref="rectify" @getData="getData"></rectify>
        <Withdraw ref="Withdraw" @getData="getData"></Withdraw>
    </div>
</template>
<script>
import '../../assets/css/table/table.css';
import '../../assets/css/head/head.css';
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

export default {
    data() {
        return {
            waybillType: '',
            operationShow: true,
            search: '',
            searchBut: false,
            RefreshBut: false,
            speakBut: false,
            restaurants: '',
            page: 1,
            pageSize: 100,
            tableData: [],
            current: 0,
            stateLi: ['全部', '待签约', '待收货', '待关闭'],
            show1: true,
            show2: true,
            // tabPosition: 'top',
            errorClass: 'list-nav',
            activeClass: 0,
            cargoPicture1: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            cargoPicture2: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            cargoPicture3: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            cargoPicture4: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],

            textarea: '',
            number: [
                { type: '经纪人', name: '张三', phone: '18617271159' },
                { type: '经纪人', name: '张三', phone: '18617271159' },
                { type: '经纪人', name: '张三', phone: '18617271159' }
            ],
            activeName1: '0',
            activeType: '',
            activeName2: 'first',
            offerType: [{ licensePlate: '粤AG1M79', name: '张三丰 18617271159', type: '高栏 13.5米' }],

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
                qzte: '',
                qxte: '',
                yf: '',
                pyf: '',
                dyf: ''
            },
            chat: {
                fhr: '',
                fhrp: '',
                shr: '',
                shrp: '',
                dlte: '',
                opte: '',
                opme: '',
                optm: '',
                opdc: ''
            },
            chatList: [],
            oid: '',
            wid: '',
            tid: '',
            index: '',
            item: {},
            car_types: [
                {
                    value: '1',
                    label: '平板'
                },
                {
                    value: '2',
                    label: '高栏'
                },
                {
                    value: '3',
                    label: '中栏'
                },
                {
                    value: '4',
                    label: '底栏'
                },
                {
                    value: '6',
                    label: '厢式'
                },
                {
                    value: '7',
                    label: '自卸'
                },
                {
                    value: '8',
                    label: '保温'
                },
                {
                    value: '9',
                    label: '冷藏'
                },
                {
                    value: '10',
                    label: '危险品'
                },
                {
                    value: '11',
                    label: '集装箱'
                },
                {
                    value: '12',
                    label: '特种'
                },
                {
                    value: '13',
                    label: '其他'
                }
            ],
            car_long: [
                {
                    value: '1',
                    label: '4.2米'
                },
                {
                    value: '2',
                    label: '5.0米'
                },
                {
                    value: '3',
                    label: '6.2米'
                },
                {
                    value: '4',
                    label: '6.8米'
                },
                {
                    value: '5',
                    label: '7.2米'
                },
                {
                    value: '6',
                    label: '7.7米'
                },
                {
                    value: '7',
                    label: '7.8米'
                },
                {
                    value: '8',
                    label: '8.2米'
                },
                {
                    value: '9',
                    label: '8.7米'
                },
                {
                    value: '10',
                    label: '9.6米'
                },
                {
                    value: '11',
                    label: '12.5米'
                },
                {
                    value: '12',
                    label: '13.0米'
                },
                {
                    value: '13',
                    label: '14.6米'
                },
                {
                    value: '14',
                    label: '15.0米'
                },
                {
                    value: '15',
                    label: '16.5米'
                },
                {
                    value: '16',
                    label: '17.5米'
                },
                {
                    value: '17',
                    label: '其他'
                }
            ],
            operation0: false,
            operation1: false,
            operation2: false,
            operation3: false,
            operation4: false,
            popup: {
                sendName: '',
                sendPhone: '',
                collectName: '',
                collectPhone: '',
                zid: '',
                xid: '',
                zte: '',
                dch: ''
            },
            src: ''
        };
    },
    created() {
        this.getData(0);
        this.startProvinceChange();
        // console.log(this.activeClass)
    },
    methods: {
        getData(type) {
            let action = this.$store.state.Login.url + '/30017';
            this.loginData = this.$store.state.Login.DispatchData;
            this.tid = this.loginData.tid;
            var bd = {
                tid: this.loginData.tid,
                st: type,
                ss: this.search,
                pg: this.page,
                sz: this.pageSize
            };
            // console.log(bd);
            this.$axios
                .post(action, {
                    hd: {
                        pi: 30017,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let rid = res.data.hd.rid;
                    if (rid >= 0) {
                        let data = JSON.parse(res.data.bd);

                        let tableData = data.olst;
                        console.log(tableData);
                        if (tableData.length > 0) {
                            tableData.forEach(item => {
                                item.ctm = item.ctm.slice(0, 16);
                            });

                            this.tableData = tableData;
                            this.details(this.tableData[0], 0);
                        } else {
                            this.open2('暂无数据');
                            this.tableData = [];
                            this.operationShow = false;
                        }
                    } else {
                        this.open2(res.data.hd.rmsg);
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        //处理货源数据
        startProvinceChange() {
            this.loginData = this.$store.state.Login.DispatchData;

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
            return houseNumber => {
                return houseNumber.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1;
            };
        },

        loadAll() {
            return this.restaurants;
        },
        startChang(item) {},
        //头部搜索
        headSearch() {
            this.getData(0);

            this.searchBut = true;
            setTimeout(() => {
                this.searchBut = false;
            }, 1000);
        },
        //运单详情
        details(item, index) {
            this.index = index;
            this.item = item;
            this.activeClass = index;
            // console.log(111);
            this.head.start = item.zct;
            this.head.end = item.xct;
            let action = this.$store.state.Login.url + '/30018';
            this.loginData = this.$store.state.Login.DispatchData;
            this.wid = item.wid;
            this.src = './map.html?tid=' + this.loginData.tid + '&wid=' + this.wid + '&si=' + this.loginData.si;
            var bd = {
                tid: this.loginData.tid,
                wid: item.wid
            };
            // console.log(bd);
            this.$axios
                .post(action, {
                    hd: {
                        pi: 30018,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let data = JSON.parse(res.data.bd);
                    console.log(data);

                    this.head.startAddress = data.zad;
                    this.head.endAddress = data.xad;
                    this.head.dis = data.dis;
                    this.head.ocd = data.ocd;
                    this.head.time = data.ctm;
                    this.popup.zte = data.zte;

                    //货物图片
                    this.zpc(data.zpc);
                    this.xpc(data.xpc);
                    this.hdpc(data.hdpc);
                    this.opc(data.opc);

                    //发收货人信息
                    this.popup.sendName = data.fhr;
                    this.popup.sendPhone = data.fhrp;
                    this.popup.collectName = data.shr;
                    this.popup.collectPhone = data.shrp;
                    this.popup.zid = data.zid;
                    this.popup.xid = data.xid;
                    this.popup.dch = data.dch;

                    if (data.zadp) {
                        console.log(111);
                    } else {
                        this.show1 = false;
                        this.show2 = false;
                    }
                    console.log(data);
                    this.news.dw = data.dw;
                    this.news.dv = data.dv;
                    this.news.on = data.on;
                    this.news.zdy = data.zdy;
                    this.news.zte = data.zte;
                    this.news.qzte = data.qzte;
                    this.news.qxte = data.qxte;
                    this.news.yf = data.yf;
                    this.news.pyf = data.pyf;
                    this.news.dyf = data.yf - data.pyf;
                    this.oid = data.oid;
                    this.wid = data.wid;
                    this.car_types.map(item => {
                        if (item.value == data.ct) {
                            this.news.ct = item.label;
                            // console.log(this.news.ct);
                        }
                    });
                    this.car_long.map(item => {
                        if (item.value == data.cl) {
                            this.news.cl = item.label;
                            // console.log(this.news.cl);
                        }
                    });

                    let odc = '';

                    let odcArr = data.odc.split(',');
                    odcArr.forEach(item => {
                        odc += item + '、';
                    });
                    this.news.odc = odc.substring(0, odc.length - 1);

                    //  this.news.pic = data.pic;
                    if (data.pic) {
                        let num = data.pic.indexOf(',');
                        if (num == -1) {
                        } else {
                        }
                    } else {
                    }
                    // console.log(data.pic);

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

                    //司机信息
                    this.offerType = [{ licensePlate: data.cn, name: data.cne + ' ' + data.cph, type: this.news.ct + ' ' + this.news.cl }];

                    this.chat.fhr = data.fhr;
                    this.chat.fhrp = data.fhrp;
                    this.chat.shr = data.shr;
                    this.chat.shrp = data.shrp;

                    let Journal = {
                        si: this.loginData.si,
                        tid: this.loginData.tid,
                        wid: item.wid
                    };
                    this.Journal(Journal);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        //运单日志记录
        Journal(data) {
            let action = this.$store.state.Login.url + '/30028';
            var bd = {
                tid: data.tid,
                wid: data.wid,
                pg: this.page,
                sz: this.pageSize
            };
            this.$axios
                .post(action, {
                    hd: {
                        pi: 30029,
                        si: data.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let rid = res.data.hd.rid;
                    if (rid >= 0) {
                        let list = JSON.parse(res.data.bd);
                        let news = list.olst;

                        news.forEach(item => {
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
                        });
                        this.chatList = news;
                        // console.log(news);
                        this.chat.dlte = news.dlte;
                        this.chat.opte = news.opte;
                        this.chat.opme = news.opme;
                        this.chat.optm = news.optm;
                        // console.log(news.opdc);
                        this.chat.opdc = true;
                        this.add();
                    } else {
                        this.open2(res.data.hd.rmsg);
                    }
                })

                .catch(error => {
                    console.log(error);
                });
        },
        enter1() {
            this.show1 = true;
            this.show2 = false;
        },
        leave1() {
            this.show1 = true;
            this.show2 = true;
        },
        enter2() {
            this.show1 = false;
            this.show2 = true;
        },
        leave2() {
            this.show1 = true;
            this.show2 = true;
        },
        handleClick1(tab, event) {
            this.activeType = tab.name;
            this.operationShow = true;
            // console.log(this.activeType);
            if (tab.label == '全部') {
                this.getData(0);
                this.operation0 = false;
                this.operation1 = false;
                this.operation2 = false;
                this.operation3 = false;
                this.waybillType = '';
            }
            if (tab.label == '待签约') {
                this.getData(2);
                this.operation0 = true;
                this.operation1 = true;
                this.operation2 = false;
                this.operation3 = false;
                this.waybillType = '待签约';
            }
            if (tab.label == '待收货') {
                this.getData(4);
                this.operation0 = true;
                this.operation1 = false;
                this.operation2 = true;
                this.operation3 = false;
                this.waybillType = '待收货';
            }
            if (tab.label == '待关闭') {
                this.getData(6);
                this.operation0 = false;
                this.operation1 = false;
                this.operation2 = false;
                this.operation3 = true;
                this.waybillType = '待关闭';
            }
        },
        handleClick2(tab, event) {},
        Receipt() {
            this.popup2 = {
                // start: this.head.start,
                // end: this.head.end,
                // tid: this.tid,
                // oid: this.oid,
                // si: this.loginData.si,
                // dis: this.head.dis
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.receipt.receipt();
        },
        //关闭运单
        close() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                dis: this.head.dis,
                tid: this.loginData.tid,
                si: this.loginData.si,
                wid: this.wid,
                zte: this.popup.zte,
                type: this.activeType
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.close.receipt();
        },
        //完善收发信息
        operation() {
            this.popup2 = {
                tid: this.loginData.tid,
                si: this.loginData.si,
                wid: this.wid,
                start: this.head.start,
                end: this.head.end,
                sendName: this.popup.sendName,
                sendPhone: this.popup.sendPhone,
                collectName: this.popup.collectName,
                collectPhone: this.popup.collectPhone,
                zid: this.popup.zid,
                xid: this.popup.xid
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.perfect.receipt();
        },
        //申请代付
        adjustment() {
            this.popup2 = {
                // start: this.head.start,
                // end: this.head.end,
                // zid: this.zid,
                // xid: this.xid,
                // tid: this.tid,
                // oid: this.oid,
                // si: this.loginData.si
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.adjustment.receipt();
        },

        //装货
        loading() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                dis: this.head.dis,
                tid: this.tid,
                wid: this.wid,
                si: this.loginData.si,
                index: this.activeClass,
                item: this.tableData[this.activeClass]
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.loading.receipt();
        },
        //卸货
        discharge() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                dis: this.head.dis,
                tid: this.tid,
                wid: this.wid,
                si: this.loginData.si
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.discharge.receipt();
        },
        //回单
        single() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                dis: this.head.dis,
                tid: this.tid,
                wid: this.wid,
                si: this.loginData.si
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.single.receipt();
        },
        //签约
        Signing() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                dis: this.head.dis,
                tid: this.loginData.tid,
                si: this.loginData.si,
                wid: this.wid,
                type: this.activeType,
                dch: this.popup.dch
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.Signing.receipt();
        },

        //订单留言
        speak() {
            let action = this.$store.state.Login.url + '/20046';
            if (this.textarea.length == 0) {
                this.open2('留言不能为空');
            } else {
                let bd = {
                    tid: this.loginData.tid,
                    bid: this.wid,
                    bte: 2,
                    ct: this.textarea
                };
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 20046,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        console.log(res);
                        let rid = res.data.hd.rid;
                        console.log(rid);
                        if (rid >= 0) {
                            let Journal = {
                                si: this.loginData.si,
                                tid: this.loginData.tid,
                                wid: this.wid
                            };
                            this.Journal(Journal);
                            this.textarea = '';
                        } else {
                            this.open2(res.data.hd.rmsg);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
            this.speakBut = true;
            setTimeout(() => {
                this.speakBut = false;
            }, 1000);
        },
        //撤单
        Withdraw() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                dis: this.head.dis,
                wid: this.wid,
                tid: this.tid,
                type: this.activeType,
                si: this.loginData.si
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.Withdraw.receipt();
        },
        //刷新详情
        Refresh() {
            this.details(this.item, this.index);
            this.RefreshBut = true;
            setTimeout(() => {
                this.RefreshBut = false;
            }, 1000);
        },
        open() {
            this.$alert('确认已完成签约支付', {
                confirmButtonText: '确定',
                callback: action => {
                    this.getData(2);
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
                type: 'error',
                duration: 900
            });
        },
        //处理货物图片
        zpc(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture2 = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture2 = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            } else {
                this.cargoPicture2 = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            }
        },
        xpc(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture3 = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture3 = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            } else {
                this.cargoPicture3 = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            }
        },
        hdpc(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture4 = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture4 = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            } else {
                this.cargoPicture4 = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            }
        },
        opc(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture1 = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture1 = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            } else {
                this.cargoPicture1 = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
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
        Withdraw
    },
    beforeUpdate() {
        var div = this.$refs.chatNav;
    },
    updated() {
        var div = this.$refs.chatNav;
    }
};
</script>

<style lang="scss" scoped>
* {
    box-sizing: border-box;
    list-style: none;
}
.MyWaybill {
    display: flex;
    height: 100%;
    background: #fff;
    padding-bottom: 10px;
    border-top: 1px solid #ebeef5;

    .left {
        width: 229px;
        height: 100%;
        display: flex;
        flex-direction: column;
        position: relative;
        .list-search {
            height: 42px;
            box-sizing: border-box;
            display: flex;
            border-left: 1px solid #ebeef5;
            border-right: 1px solid #ebeef5;
            background: #fff;
            width: 100%;
            padding: 5px 10px;
            position: absolute;
            .search-but {
                margin-left: 5px;
            }
        }
        .state {
            position: absolute;
            top: 42px;
            width: 100%;
            height: 44px;
            padding: 0 7px;
            background: #fff;
            border-left: 1px solid #ebeef5;
            border-right: 1px solid #ebeef5;
            z-index: 100;
            .el-tabs {
                display: flex;
                /deep/ .el-tabs__item {
                    padding: 0 8px;
                }
            }
            .state-ul {
                display: flex;
                // justify-content: space-around;
                background: #fff;
                padding: 8px 8px;
                border-left: 1px solid #ccc;
                border-right: 1px solid #ccc;
                width: 100%;
                .state-li {
                    font-size: 14px;
                    line-height: 26px;
                    border: 1px solid #ccc;
                    width: 25%;
                    text-align: center;
                    border-radius: 5px;
                }
            }
        }

        .list {
            padding-top: 85px;
            box-sizing: border-box;
            width: 229px;
            flex: 1;
            background: #fff;
            border-left: 1px solid #ebeef5;
            overflow: scroll;
            border-bottom: 1px solid #ebeef5;
            border-right: 1px solid #ebeef5;
            overflow: scroll;
            .list-nav {
                box-sizing: border-box;
                // background: rgba($color: #caf982, $alpha: 0.5);
                border-bottom: 1px dashed rgba($color: #000000, $alpha: 0.1);
                // box-shadow: 0 0 1px 1px #ccc;
                .top {
                    padding: 5px 10px 0 10px;
                }
                .center {
                    padding: 0 5px 5px 5px;
                }
                .top,
                .center {
                    display: flex;
                    text-align: center;
                    justify-content: space-between;
                    .start,
                    .end {
                        width: 100px;
                        tab-size: center;
                        font-size: 13px;
                        line-height: 22px;
                    }
                    .name {
                        width: 100px;
                        font-size: 13px;
                        line-height: 22px;
                        text-align: center;
                        color: rgba($color: #000000, $alpha: 0.5);
                    }
                    .time {
                        flex: 1;
                        font-size: 12px;
                        line-height: 22px;
                        text-align: center;
                        color: rgba($color: #000000, $alpha: 0.5);
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
    }
    .order-right {
        flex: 1;
        .el-container {
            display: flex;
            flex-direction: column;
            height: 100%;
            .el-header {
                padding: 0;
                .head {
                    height: 161px;
                    padding: 10px;
                    width: 100%;
                    margin: auto;
                    background: #009dd9;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    position: relative;
                    .head-start,
                    .head-end {
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        align-items: center;
                        .abbreviate {
                            font-size: 24px;
                            color: #fff;
                            font-weight: 600;
                            text-align: center;
                            padding-bottom: 10px;
                        }
                        .detailed {
                            font-size: 12px;
                            line-height: 16px;
                            margin-top: 5px;
                            color: #fff;
                            margin: 0 10px;
                        }
                    }
                    .head-start {
                        height: 100%;
                        padding-bottom: 18px;
                        // position: relative;
                        width: 200px;
                        .abbreviate {
                            text-align: right;

                            // margin-right: 20px;
                        }
                        .start-icon {
                            text-align: center;
                        }
                        .address {
                            // position: absolute;
                            // left: -51px;
                            // top: 80px;
                            font-size: 12px;
                            color: #fff;
                            width: 250px;
                            text-align: center;
                            height: 32px;
                        }
                        .start-icon {
                            // margin-right: 15px;
                            margin-top: 10px;
                        }
                    }
                    .head-end {
                        padding-bottom: 18px;
                        height: 100%;
                        width: 200px;
                        .abbreviate {
                            text-align: left;

                            // margin-left: 20px;
                        }
                        .end-icon {
                            text-align: center;
                        }
                        .address {
                            // position: absolute;
                            // left: -51px;
                            // top: 80px;
                            font-size: 12px;
                            color: #fff;
                            width: 250px;
                            text-align: center;
                            height: 32px;
                        }
                        .end-icon {
                            // margin-left: 25px;
                            margin-top: 10px;
                        }
                    }
                    .head-center {
                        // margin-top: 8px;
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        align-items: center;
                        position: relative;
                        .distance {
                            color: #fff;
                            margin-bottom: 25px;
                        }
                        .type {
                            width: 101px;
                            height: 32px;
                            text-align: center;
                            font-size: 18px;
                            font-weight: 600;
                            color: #fff;
                            background: #00cc00;
                            line-height: 32px;
                            border-radius: 3px;
                            margin-right: 11px;
                        }
                        .cart1 {
                            position: absolute;
                            top: 31px;
                            left: 23%;
                            .tooltip {
                                position: relative;
                                .text {
                                    width: 150px;
                                    position: absolute;
                                    top: -40px;
                                    left: -65px;
                                    font-size: 12px;
                                    line-height: 30px;
                                    color: #fff;
                                    background: #00cc00;
                                    text-align: center;
                                    overflow: hidden;
                                    text-overflow: ellipsis;
                                    display: -webkit-box;
                                    -webkit-line-clamp: 1;
                                    -webkit-box-orient: vertical;
                                }
                                .text:hover {
                                    width: 400px;
                                    left: -190px;

                                    z-index: 10;
                                }
                                .triangle {
                                    width: 0;
                                    height: 0;
                                    border: 10px solid transparent;
                                    border-top-color: #00cc00;
                                    position: absolute;
                                    top: -10px;
                                    left: 0px;
                                }
                            }
                        }
                        .cart2 {
                            position: absolute;
                            top: 31px;
                            left: 73%;
                            .tooltip {
                                position: relative;
                                .text {
                                    width: 150px;
                                    position: absolute;
                                    top: -40px;
                                    left: -65px;
                                    font-size: 12px;
                                    line-height: 30px;
                                    color: #fff;
                                    background: #00cc00;
                                    text-align: center;
                                    overflow: hidden;
                                    text-overflow: ellipsis;
                                    display: -webkit-box;
                                    -webkit-line-clamp: 1;
                                    -webkit-box-orient: vertical;
                                }
                                .text:hover {
                                    width: 400px;
                                    left: -190px;

                                    z-index: 10;
                                }
                                .triangle {
                                    width: 0;
                                    height: 0;
                                    border: 10px solid transparent;
                                    border-top-color: #00cc00;
                                    position: absolute;
                                    top: -10px;
                                    left: 0px;
                                }
                            }
                        }
                        .cart-center {
                            position: absolute;
                            top: 33px;
                            left: 46.7%;
                        }
                    }
                    .number {
                        position: absolute;
                        left: 20px;
                        top: 10px;
                        color: #fff;
                    }
                    .time {
                        position: absolute;
                        right: 20px;
                        top: 10px;
                        color: #fff;
                    }
                }
            }
            .el-main {
                padding: 0;
                flex: 1;
                display: flex;
                flex-direction: column;
                background: #fff;
                .news {
                    height: 130px;
                    display: flex;
                    justify-content: space-between;
                    border: 1px solid #ebeef5;
                    border-radius: 3px;
                    box-shadow: 0 1px 0 0 #ccc;
                    padding: 10px 10px;
                    box-sizing: border-box;
                    border-left: none;
                    .news-left {
                        height: 100%;
                        flex: 1;
                        display: flex;
                        flex-direction: column;
                        margin-left: 10px;
                        justify-content: space-around;
                        box-sizing: border-box;
                        .CargoInformation,
                        .VehicleDemand,
                        .other {
                            display: flex;
                            padding-right: 60px;
                            // justify-content: start;

                            .key {
                                color: rgba($color: #000000, $alpha: 0.5);
                                width: 80px;
                            }
                            .value {
                                font-size: 16px;
                                color: #000;
                                flex: 1;
                                flex-wrap: wrap;
                            }
                        }
                    }
                    .news-right {
                        height: 100%;
                        width: 390px;
                        border-left: 1px solid #ebeef5;
                        display: flex;
                        justify-content: center;
                        .operation1,
                        .operation2,
                        .operation3 {
                            display: flex;
                            flex-direction: column;
                            justify-content: space-around;
                            align-items: center;
                        }
                        .operation3 {
                            padding-top: 2px;
                            padding-bottom: 2px;
                            display: flex;
                            flex-direction: column;
                            justify-content: space-between;
                            align-items: center;
                        }
                        .operation2,
                        .operation3 {
                            margin-left: 10px;
                        }
                    }
                }
                .main-nav {
                    display: flex;
                    justify-content: space-between;
                    flex: 1;
                    background: #fff;
                    overflow: hidden;
                    .chat {
                        flex: 1;
                        display: flex;
                        flex-direction: column;
                        background: #fff;
                        border: 1px solid #ebeef5;
                        border-top: none;
                        border-left: none;
                        .chat-top {
                            height: 30px;
                            width: 100%;
                            height: 39px;
                            display: flex;
                            justify-content: space-between;
                            padding: 0 10px;
                            .consignor,
                            .consignee {
                                display: flex;
                                align-items: center;
                                font-size: 14px;
                                color: rgba($color: red, $alpha: 0.7);
                            }
                        }
                        .chat-nav {
                            height: 300px;
                            border-top: 1px solid #ebeef5;
                            border-bottom: 1px solid #ebeef5;
                            overflow: scroll;
                            .list {
                                margin-top: 8px;
                                margin-left: 8px;
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

                                    span {
                                        background: rgba($color: #ccc, $alpha: 0.3);
                                        color: rgba($color: #000000, $alpha: 0.6);
                                        font-size: 14px;
                                        font-weight: 550;
                                        border-radius: 8px;
                                        padding: 8px 8px;
                                    }
                                }
                            }
                            .myNews {
                                height: 71px;
                                margin-top: 8px;
                                margin-left: 8px;
                                display: flex;
                                flex-direction: column;
                                align-items: flex-end;

                                text-align: right;
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
                                        display: none;
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
                                    }
                                }
                                .list-text {
                                    margin-left: 37px;
                                    margin-right: 37px;

                                    span {
                                        background: rgba($color: #ccc, $alpha: 0.3);
                                        color: rgba($color: #000000, $alpha: 0.6);
                                        font-size: 14px;
                                        font-weight: 550;
                                        border-radius: 8px;
                                        padding: 8px 8px;
                                    }
                                }
                            }
                        }
                        .chat-operation {
                            width: 100%;
                            height: 34px;
                            display: flex;
                            align-items: center;
                        }
                        .chat-input {
                            // height: 126px;
                            flex: 1;
                            position: relative;
                            .add-content {
                                height: 100%;
                                /deep/.el-textarea__inner {
                                    height: 100% !important;
                                }
                                /deep/textarea {
                                    resize: none;
                                    border: 1px solid #ebeef5;
                                }
                            }
                            .sendOut {
                                position: absolute;
                                right: 10px;
                                bottom: 10px;
                            }
                        }
                    }
                    .nav-right {
                        width: 400px;
                        height: 100%;
                        display: flex;
                        flex-direction: column;
                        .tabs {
                            /deep/.el-tabs__header {
                                margin: 0;
                            }
                            /deep/.el-tabs__nav-scroll {
                                padding: 0 21%;
                            }
                            /deep/.el-tabs__nav-wrap::after {
                                height: 1px !important;
                            }
                        }
                        .cargoPicture {
                            display: flex;
                            justify-content: center;
                            padding: 10px 0;

                            img {
                                width: 100px;
                                height: 100px;
                                margin: 0 10px;
                            }
                        }

                        .vehicle {
                            /deep/.el-table__body tr.current-row > td {
                                background-color: #fff;
                            }

                            /deep/.el-table--enable-row-hover .el-table__body tr:hover > td {
                                background-color: #fff;
                            }
                        }
                        .map {
                            flex: 1;
                        }
                    }
                }
            }
        }
    }
}
</style>