<template>
    <div class="orderBox">
        <div class="MyOrder" v-if="box">
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
                            @click="getData"
                            :disabled="But"
                            style="width:80px"
                        >搜索</el-button>
                    </div>
                </div>
                <ul v-bind:class="[box1 == true ? 'list':'leftList']" ref="leftList">
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
                            <el-button
                                size="mini"
                                style="width:100px;"
                                type="primary"
                                icon="el-icon-lx-forward"
                                @click="appoint"
                                :disabled="disabled"
                            >指派调度</el-button>
                            <!-- <el-button
                                size="mini"
                                style="width:100px;"
                                type="primary"
                                icon="el-icon-s-promotion"
                            >分享</el-button>-->
                            <el-button
                                size="mini"
                                style="width:100px;"
                                @click="cancel"
                                type="primary"
                                icon="el-icon-lx-forwardfill"
                            >撤单</el-button>
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
                            @keyup.enter.native="sendOut"
                        ></el-input>
                    </div>
                    <div class="but">
                        <el-button
                            size="mini"
                            style="width:80px;"
                            @click="speak"
                            :disabled="button2"
                        >发送</el-button>
                    </div>
                </div>
                <!-- <el-container v-if="navShow">
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
                            <div class="news-left" ref="copy">
                                <div class="CargoInformation">
                                    <div class="key">货物信息：</div>
                                    <div
                                        class="value"
                                    >{{news.on?news.on+"，":""}}{{news.dw?news.dw+"吨":""}}{{news.dv?news.dv+"方，":""}}{{news.qyf?news.qyf+"元":""}}{{news.zdy?"，"+news.zdy:""}} {{news.zte?news.zte:""}}</div>
                                </div>
                                <div class="VehicleDemand">
                                    <div class="key">用车需求：</div>
                                    <div
                                        class="value"
                                    >{{news.te?news.te+"，":""}}{{news.ct?news.ct:""}}{{news.cl?"，"+news.cl:""}}</div>
                                </div>
                                <div class="other">
                                    <div class="key">其他需求：</div>
                                    <div
                                        class="value"
                                    >{{news.pte?"货到现付":""}}{{news.zxte?"，"+news.zxte:""}}{{news.odc?"，"+news.odc:""}}</div>
                                </div>
                            </div>
                            <div class="news-right">
                                <div class="cargoPicture">
                                    <img
                                        :src="item"
                                        preview="1"
                                        preview-textss
                                        alt
                                        v-for="(item,index) in cargoPicture"
                                        :key="index"
                                    />
                                </div>
                                <div class="operation">
                                    <el-button
                                        type="primary"
                                        style="width:104px;margin: 0;"
                                        @click="modify"
                                    >修改订单</el-button>
                                    <el-button
                                        type="primary"
                                        style="width:104px;margin: 0;"
                                        @click="orderMessage"
                                    >完善收发信息</el-button>
                                    <el-button
                                        type="primary"
                                        style="width:104px;margin: 0;"
                                        @click="uploadPhotos"
                                    >上传货物照片</el-button>
                                </div>
                                <div class="butCopy">
                                    <el-button
                                        type="primary"
                                        class="bar_btn copy_btn"
                                        :data-clipboard-text="message"
                                        @click="copy"
                                        :disabled="copyBut"
                                    >复制</el-button>
                                </div>
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
                                        <div class="list-text" v-if="chat.opdc?false:true">
                                            <span class="span-text">{{item.opdc}}</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="chat-operation">
                                    <el-button
                                        size="mini"
                                        plain
                                        style="width:80px;"
                                        @click="refresh"
                                        :disabled="button1"
                                    >刷新信息</el-button>
                                    <el-button
                                        size="mini"
                                        plain
                                        style="width:80px;"
                                        @click="appoint"
                                        :disabled="disabled"
                                    >指派调度</el-button>
                                    <el-button size="mini" plain style="width:80px;">分享</el-button>
                                    <el-button
                                        size="mini"
                                        plain
                                        style="width:80px;"
                                        @click="cancel"
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
                                            :disabled="button2"
                                        >发送</el-button>
                                    </div>
                                </div>
                            </div>
                            <div class="nav-right">
                                <div class="passerby">
                                    <el-table
                                        :data="number"
                                        border
                                        class="table"
                                        ref="multipleTable"
                                        header-cell-class-name="table-header"
                                        highlight-current-row
                                    >
                                        <el-table-column
                                            prop="type"
                                            label="类型"
                                            width="80"
                                            align="center"
                                        >调度员</el-table-column>
                                        <el-table-column
                                            prop="name"
                                            label="名称"
                                            width="80"
                                            align="center"
                                        ></el-table-column>
                                        <el-table-column prop="phone" label="联系方式"></el-table-column>
                                        <el-table-column
                                            prop="operation"
                                            label="操作"
                                            width="100"
                                            align="center"
                                        >
                                            <el-button
                                                size="mini"
                                                plain
                                                style="width:70px;"
                                                @click="revoke"
                                            >撤单</el-button>
                                        </el-table-column>
                                    </el-table>
                                </div>
                                <div class="offer">
                                    <el-tabs
                                        v-model="activeName"
                                        @tab-click="handleClick"
                                        class="tabs"
                                    >
                                        <el-tab-pane label="市场报价" name="first"></el-tab-pane>
                                        <el-tab-pane label="参考报价" name="second"></el-tab-pane>
                                    </el-tabs>
                                    <el-table
                                        :data="offerType"
                                        border
                                        class="quotation"
                                        ref="multipleTable"
                                        header-cell-class-name="table-header"
                                        highlight-current-row
                                        style="height:300px;"
                                    >
                                        <el-table-column
                                            prop="on"
                                            align="center"
                                            label="货物"
                                            :show-overflow-tooltip="true"
                                        ></el-table-column>
                                        <el-table-column
                                            prop="qt"
                                            width="90"
                                            align="center"
                                            label="成交价"
                                            :show-overflow-tooltip="true"
                                        ></el-table-column>
                                        <el-table-column
                                            prop="model"
                                            width="100"
                                            label="车型"
                                            align="center"
                                            :show-overflow-tooltip="true"
                                        ></el-table-column>
                                        <el-table-column
                                            prop="weight"
                                            width="100"
                                            align="center"
                                            label="体积"
                                            :show-overflow-tooltip="true"
                                        ></el-table-column>
                                    </el-table>
                                </div>
                            </div>
                        </div>
                    </el-main>
                </el-container>-->
            </div>
            <div class="right" v-if="box1">
                <div class="tab">
                    <el-tabs v-model="activeName1" @tab-click="handleClick">
                        <el-tab-pane label="基本信息" name="first">
                            <div class="news" ref="copy">
                                <div class="news1">
                                    <div class="news1-left">订单号：{{head.ocd}}</div>
                                    <div class="news1-right">{{head.time}}</div>
                                </div>
                                <div
                                    class="news1"
                                >{{news.on?news.on+"、":""}}{{news.dwv?news.dwv:""}}{{news.zdy?"、"+news.zdy:""}}装货</div>
                                <div
                                    class="news1"
                                >{{news.te?news.te:""}}{{news.ct?"、"+news.ct:""}}{{news.cl?news.cl:""}}</div>
                                <div class="news1 odc">
                                    {{news.zxte?news.zxte:""}}{{news.odc?"、"+news.odc:""}}{{news.dc?"、"+news.dc:""}}
                                    <!-- <el-tooltip
                                        class="item"
                                        effect="dark"
                                        content="Top Center 提示文字"
                                        placement="top"
                                    >
                                       <span>{{news.pte?"货到现付、":""}}{{news.zxte?news.zxte+"、":""}}{{news.odc?news.odc:""}}{{news.dc?"、"+news.dc:""}} </span>
                                    </el-tooltip>-->
                                </div>
                                <div class="price">
                                    期望运费：
                                    <span class="num">{{news.qyf?news.qyf:"0"}}</span>&nbsp;元
                                </div>
                            </div>
                            <!-- <div class="price">
                                期望运费：
                                <span class="num">{{news.qyf?news.qyf:"0"}}</span>&nbsp;元
                                
                            </div>-->
                        </el-tab-pane>
                        <el-tab-pane label="详细地址" name="second">
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
                                <div class="text" v-if="news.xadp">{{news.zadp}}</div>
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
                        <el-tab-pane label="图片信息" name="third">
                            <div class="cargoPicture">
                                <img
                                    :src="item"
                                    preview="1"
                                    preview-textss
                                    alt
                                    v-for="(item,index) in cargoPicture"
                                    :key="index"
                                />
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
                <div class="tab-operation">
                    <a href="javascript:;" class="operation-text" @click="modify">修改订单</a>
                    <a href="javascript:;" class="operation-text" @click="orderMessage">修改地址</a>
                    <a href="javascript:;" class="operation-text" @click="uploadPhotos">上传照片</a>
                    <a
                        href="javascript:;"
                        class="operation-text bar_btn copy_btn"
                        @click="copy"
                        :data-clipboard-text="message"
                    >复制</a>
                    <!-- 订单编号：131231231232
                    负责客服：张三 18569524656
                    装卸地址：黑龙江 葫芦岛 建昌县 → 重庆市 沙坪坝区
                    货物类型：20吨饲料20方左右/25公斤一包
                    用车需求：整车 6.8米 高栏
                    装货时间：2020-07-12 12:40
                    其他备注：客户预算/550一吨，客户比较急，望尽快报价-->
                </div>
                <div class="dispatchNew">
                    <div class="yuan" v-if="number.name">调</div>
                    <div class="name" v-if="number.name">{{number.name}}</div>
                    <div class="phone" v-if="number.name">{{number.phone}}</div>
                    <a href="javascript:;" class="revoke" @click="revoke" v-if="number.name">撤销</a>
                    <div class="tixin" v-if="!number.name">未指派调度</div>
                </div>
                <div class="offer">
                    <el-tabs v-model="activeName2" @tab-click="handleClick" class="tabs">
                        <el-tab-pane label="市场报价" name="first"></el-tab-pane>
                        <el-tab-pane label="参考报价" name="second"></el-tab-pane>
                    </el-tabs>
                    <el-table
                        :data="offerType"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        highlight-current-row
                    >
                        <el-table-column
                            prop="on"
                            align="center"
                            label="货物信息"
                            :show-overflow-tooltip="true"
                        ></el-table-column>
                        <el-table-column
                            prop="model"
                            width="150"
                            align="center"
                            label="运输类型"
                            :show-overflow-tooltip="true"
                        ></el-table-column>
                        <el-table-column
                            prop="qt"
                            width="100"
                            label="成交价"
                            align="center"
                            :show-overflow-tooltip="true"
                        ></el-table-column>
                    </el-table>
                </div>
            </div>
            <div class="box3" v-if="box3">暂无数据</div>
        </div>
        <modify v-if="box2" @modifyRefresh="modifyRefresh"></modify>
        <orderMessage ref="orderMessage" @refresh="refresh"></orderMessage>
        <cargoPhoto ref="cargoPhoto" @refresh="refresh"></cargoPhoto>
        <cancel ref="cancel" @getData="getData"></cancel>
        <assign ref="assign" @refresh="refresh"></assign>
    </div>
</template>
<script>
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
import '../../assets/css/table/table.css';
import '../../assets/css/head/head.css';
import Clipboard from 'clipboard';
import modify from './modify.vue';
import orderMessage from '../popup/orderMessage.vue';
import cargoPhoto from '../popup/cargoPhoto.vue';
import cancel from '../popup/cancel.vue';
import assign from '../popup/assign.vue';
export default {
    data() {
        return {
            button1: false,
            button2: false,
            copyBut: false,
            navShow: true,
            But: false,
            loginData: {},
            sk: {},
            disabled: false,
            box: true,
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

            textarea: '',
            number: { name: '', phone: '' },
            activeName1: 'first',
            activeName2: 'first',
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
                dwv: ''
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
                opdc: '',
                bph: '',
                shr: '',
                shrp: ''
            },
            chatList: [],
            zid: '',
            xid: '',
            oid: '',
            tid: '',

            orderData: {},
            popup2: {},
            detailsData: {
                item: {},
                index: ''
            }
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.CustomerData;
        this.getData();
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
                this.open('复制成功');
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
        async getData() {
            let action = this.$store.state.Login.url + '/30003';

            var bd = {
                tid: this.loginData.tid,
                st: 2,
                ss: this.search,
                pg: this.page,
                sz: this.pageSize
            };
            var hd = {
                pi: 30003,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30003', hd, bd);
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
                } else {
                    tableData.forEach((item) => {
                        item.ctm = item.ctm.slice(0, 16);
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
            this.loginData = this.$store.state.Login.CustomerData;

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
        //订单详情
        async details(item, index) {
            this.textarea = '';
            this.activeName2 = 'first';
            this.cargoPicture = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            this.activeClass = index;
            this.detailsData.item = item;
            this.$store.commit('setNewsData', item);
            this.detailsData.index = index;
            this.head.start = item.zct;
            this.head.end = item.xct;
            let action = this.$store.state.Login.url + '/30004';
            this.loginData = this.$store.state.Login.CustomerData;

            console.log(item);
            this.tid = this.loginData.tid;
            this.oid = item.oid;
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
                this.orderData = data;
                this.$store.commit('setOrderData', this.orderData);
                console.log(data);
                if (data.did > 0) {
                    // this.disabled = true;
                }
                this.head.startAddress = data.zad;
                this.head.endAddress = data.xad;
                this.head.dis = data.dis;
                this.head.ocd = data.ocd;
                this.head.time = data.ctm;

                //处理货物图片
                this.pic(data.pic);

                if (data.zadp) {
                    console.log(111);
                } else {
                    this.show1 = false;
                    this.show2 = false;
                }
                // console.log(item);
                this.news.dw = data.dw;
                this.news.dv = data.dv;
                this.news.on = data.on;
                this.news.zdy = data.zdy;
                this.news.zte = data.zte;
                this.news.qyf = data.qyf;
                this.news.dc = data.dc;
                this.news.zad = data.zad;
                this.news.zadp = data.zadp;
                this.news.xad = data.xad;
                this.news.xadp = data.xadp;
                // {{news.on?news.on+"、":""}}{{news.dw?news.dw+"吨":""}}{{news.dv!=0||news.dv!=undefined?news.dv+"方、":""}}{{news.zdy?news.zdy:""}}装货
                if ((data.dv + '').indexOf('-') == -1) {
                    if (data.dv == 0) {
                        this.news.dwv = data.dw + '吨';
                    } else {
                        this.news.dwv = data.dw + '吨' + data.dv + '方';
                    }
                } else {
                    this.news.dwv = data.dw + '吨' + data.dv + '方';
                }

                let ct = '';
                if (data.ct.indexOf(',') != -1) {
                    let ctArr = data.ct.split(',');
                    ctArr.forEach((item) => {
                        ct += item + '、';
                        // console.log(item);
                    });
                    this.news.ct = ct.substring(0, ct.length - 1);
                } else {
                    this.news.ct = data.ct;
                }

                let cl = '';
                if (data.cl.indexOf(',') != -1) {
                    let clArr = data.cl.split(',');
                    clArr.forEach((item) => {
                        cl += item + '、';
                        // console.log(item);
                    });
                    this.news.cl = cl.substring(0, cl.length - 1);
                } else {
                    this.news.cl = data.cl;
                }
                console.log(this.news.cl, this.news.ct);
                // let clArr = data.cl.split(',');
                // clArr.forEach((item) => {
                //     cl += item + '、';
                // });
                // this.news.cl = cl.substring(0, cl.length - 1);
                console.log(data.odc);

                let odc = '';
                if (data.odc.indexOf(',') != -1) {
                    let odcArr = data.odc.split(',');
                    console.log(odcArr);
                    odcArr.forEach((item) => {
                        if (item != undefined && item != '') {
                            odc += item + '、';
                        }
                    });
                    console.log(odc);
                    this.news.odc = odc.substring(0, odc.length - 1);
                } else {
                    this.news.odc = data.odc;
                }

                if (data.te == 1) {
                    this.news.te = data.te = '整车';
                } else {
                    this.news.te = data.te = '配货';
                }
                if (data.pte == 1) {
                    this.news.pte = data.pte = '平台代付';
                } else {
                    this.news.pte = data.pte = '货到现付';
                }

                if (data.zxte == 1) {
                    this.news.zxte = data.zxte = '一装一卸';
                }
                if (data.zxte == 2) {
                    this.news.zxte = data.zxte = '一装两卸';
                }
                if (data.zxte == 3) {
                    this.news.zxte = data.zxte = '两装一卸';
                }
                if (data.zxte == 4) {
                    this.news.zxte = data.zxte = '两装两卸';
                }
                console.log(this.news.zxte);

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
                    oid: item.oid
                };
                this.Journal(Journal);

                this.zid = data.zid;
                this.xid = data.xid;
                let makeData = {
                    tid: this.loginData.tid,
                    xid: data.xid,
                    zid: data.zid,
                    ss: '',
                    pg: 1,
                    sz: 100
                };
                this.marketQuotation(makeData, '/30041');
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //订单日志记录
        async Journal(data) {
            let action = this.$store.state.Login.url + '/30014';
            var bd = {
                tid: data.tid,
                oid: data.oid,
                pg: this.page,
                sz: this.pageSize
            };
            var hd = {
                pi: 30014,
                si: data.si,
                sq: 9
            };
            let res = await request('/30014', hd, bd);
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
        //行情
        async marketQuotation(data, url) {
            // console.log(data);
            // this.activeName2 = 'first';
            let action = this.$store.state.Login.url + url;
            this.loginData = this.$store.state.Login.CustomerData;
            let str = url.substr(1);
            let hd = {
                pi: str,
                si: this.loginData.si,
                sq: 9
            };

            let res = await request(url, hd, data);
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

                let list = data.olst;
                console.log(list);
                if (list.length == 0) {
                    this.offerType = [];
                } else {
                    this.offerType = [];
                    list.forEach((item) => {
                        if (item.ct == 1) {
                            item.ct = '平板';
                        }
                        if (item.ct == 2) {
                            item.ct = '高栏';
                        }
                        if (item.ct == 3) {
                            item.ct = '中栏';
                        }
                        if (item.ct == 4) {
                            item.ct = '低栏';
                        }
                        if (item.ct == 5) {
                            item.ct = '高低板';
                        }
                        if (item.ct == 6) {
                            item.ct = '厢式';
                        }
                        if (item.ct == 7) {
                            item.ct = '自卸';
                        }
                        if (item.ct == 8) {
                            item.ct = '保温';
                        }
                        if (item.ct == 9) {
                            item.ct = '冷藏';
                        }
                        if (item.ct == 10) {
                            item.ct = '危险品';
                        }
                        if (item.ct == 11) {
                            item.ct = '集装箱';
                        }
                        if (item.ct == 12) {
                            item.ct = '特种';
                        }
                        if (item.ct == 13) {
                            item.ct = '其他';
                        }

                        if (item.cl == 1) {
                            item.cl = '4.2米';
                        }
                        if (item.cl == 2) {
                            item.cl = '5.0米';
                        }
                        if (item.cl == 3) {
                            item.cl = '6.2米';
                        }
                        if (item.cl == 4) {
                            item.cl = '6.8米';
                        }
                        if (item.cl == 5) {
                            item.cl = '7.2米';
                        }
                        if (item.cl == 6) {
                            item.cl = '7.7米';
                        }
                        if (item.cl == 7) {
                            item.cl = '7.8米';
                        }
                        if (item.cl == 8) {
                            item.cl = '8.2米';
                        }
                        if (item.cl == 9) {
                            item.cl = '8.7米';
                        }
                        if (item.cl == 10) {
                            item.cl = '9.6米';
                        }
                        if (item.cl == 11) {
                            item.cl = '12.5米';
                        }
                        if (item.cl == 12) {
                            item.cl = '13.0米';
                        }
                        if (item.cl == 13) {
                            item.cl = '14.6米';
                        }
                        if (item.cl == 14) {
                            item.cl = '15.0米';
                        }
                        if (item.cl == 15) {
                            item.cl = '16.5米';
                        }
                        if (item.cl == 16) {
                            item.cl = '17.5米';
                        }
                        if (item.cl == 17) {
                            item.cl = '13.5米';
                        }
                        if (item.te == 1) {
                            item.te = '整车';
                        }
                        if (item.te == 2) {
                            item.te = '配货';
                        }
                        this.offerType.push({
                            on: item.on + ' ' + (item.dw == 0 ? '' : +item.dw + '吨 ') + (item.dv == 0 ? '' : item.dv + '方'),
                            qt: item.qt,
                            model: item.te + ' ' + item.ct + ' ' + item.cl
                        });
                    });
                }
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //修改订单
        modify() {
            this.box = false;
            this.box2 = true;
            // this.$store.commit('setOrderData', this.orderData);
            // let address = {
            //     start: this.head.start,
            //     end: this.head.end
            // };
            // this.$store.commit('setAddress', address);
        },
        //修改后刷新页面
        modifyRefresh() {
            this.box = true;
            this.box2 = false;
            this.refresh();
        },

        //上传货物照片
        uploadPhotos() {
            this.popup2 = {
                tid: this.tid,
                oid: this.oid,
                si: this.loginData.si,
                start: this.head.start,
                end: this.head.end,
                dis: this.head.dis
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.cargoPhoto.receipt();
        },
        //刷新详情页面
        refresh() {
            this.details(this.detailsData.item, this.detailsData.index);
            this.button1 = true;
            setTimeout(() => {
                this.button1 = false;
            }, 1000);
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
        handleClick(tab, event) {
            this.loginData = this.$store.state.Login.CustomerData;
            if (tab.label == '市场报价') {
                let makeData = {
                    tid: this.loginData.tid,
                    xid: this.xid,
                    zid: this.zid,
                    ss: '',
                    pg: 1,
                    sz: 100
                };
                this.marketQuotation(makeData, '/30041');
            }
            if (tab.label == '参考报价') {
                console.log(tab.label);
                let makeData = {
                    tid: this.loginData.tid,
                    xid: this.xid,
                    zid: this.zid,
                    ocd: '',
                    ss: '',
                    pg: 1,
                    sz: 100
                };
                this.marketQuotation(makeData, '/30042');
            }
        },
        open(text) {
            this.$message({
                message: text,
                type: 'success',
                duration: 900
            });
        },
        open1(text) {
            this.$message({
                message: text,
                type: 'primary',
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
        //完善收发信息
        orderMessage() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                zid: this.zid,
                xid: this.xid,
                tid: this.tid,
                oid: this.oid,
                si: this.loginData.si
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.orderMessage.receipt();
        },
        //撤单
        cancel() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                dis: this.head.dis,
                tid: this.tid,
                oid: this.oid,
                si: this.loginData.si
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.cancel.receipt();
        },
        //指派调度
        appoint() {
            this.popup2 = {
                start: this.head.start,
                end: this.head.end,
                tid: this.tid,
                oid: this.oid,
                si: this.loginData.si,
                dis: this.head.dis
            };
            this.$store.commit('setPopup2', this.popup2);
            this.$refs.assign.receipt();
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
                    bid: this.oid,
                    bte: 1,
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

            this.button2 = true;
            setTimeout(() => {
                this.button2 = false;
            }, 1000);
        },
        //回车事件
        sendOut() {
            console.log(111);
        },
        //订单撤销调度
        async revoke() {
            // 20011;
            let action = this.$store.state.Login.url + '/20011';
            let bd = {
                tid: this.tid,
                oid: this.oid
            };
            var hd = {
                pi: 20011,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/20011', hd, bd);
            let rid = res.data.hd.rid;

            if (rid >= 0) {
                this.open('撤销成功');
                this.refresh();
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //处理图片
        pic(url) {
            if (url.length > 0) {
                let num = url.indexOf(',');
                if (num == -1) {
                    this.cargoPicture = [this.loginData.murl + url];
                } else {
                    let arr = url.split(',');

                    this.cargoPicture = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
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
        modify,
        orderMessage,
        cargoPhoto,
        cancel,
        assign
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
.el-input.is-disabled .el-input__inner {
    color: #000 !important;
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
                .el-button--mini.is-round {
                    padding: 12px 15px !important;
                }
            }
        }

        .list {
            padding-top: 100px;
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
                        width: 160px;

                        font-size: 13px;
                        line-height: 22px;
                    }
                    .start,
                    .end {
                        padding-right: 15px;
                        padding-left: 15px;
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
                        margin-left: 21px;
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
                    height: 34px;

                    .div-text {
                        background: #fff;
                        color: rgba($color: #000000, $alpha: 0.6);
                        font-size: 14px;
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
                padding: 0 17%;
            }
            /deep/#pane-first {
                position: relative;
                padding-bottom: 105px;
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
                }
                .odc {
                    line-height: 25px;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;
                    -webkit-line-clamp: 2;
                    -webkit-box-orient: vertical;
                }
                .price {
                    padding-left: 10px;
                    padding-right: 10px;
                    margin-top: 120px;
                    font-size: 18px;
                    display: flex;
                    align-items: center;
                    position: absolute;
                    bottom: -80px;
                    .num {
                        color: red;
                        font-size: 18px;
                        font-weight: 550;
                        // width: 80px;
                    }
                    .copy {
                        text-decoration: underline;
                        position: absolute;
                        right: -188px;
                        color: #409eff;
                        font-size: 14px;
                        // margin-left: 80px;
                    }
                }
            }

            /deep/#pane-second {
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
            /deep/#pane-third {
                .cargoPicture {
                    margin-top: 64px;
                    display: flex;
                    justify-content: space-around;
                    align-items: center;
                    img {
                        width: 130px;
                        height: 130px;
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
                font-size: 18px;
                margin-left: 10px;
            }
            .phone {
                font-size: 18px;
                margin-left: 18px;
            }
            .revoke {
                font-size: 18px;
                position: absolute;
                font-size: 14px;
                right: 15px;
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
        .offer {
            flex: 1;
            display: flex;
            flex-direction: column;

            .tabs {
                height: 40px;
            }
            .table {
                // flex: 1;
                height: 200px;
                /deep/.el-table__body-wrapper {
                    height: 95%;
                    overflow: scroll;
                    /deep/ .el-table__body {
                        // height: 95%;
                        overflow: scroll;
                        padding-bottom: 15px;
                    }
                }
                /deep/.el-table__body-wrapper::-webkit-scrollbar {
                    display: none;
                }
            }
        }
    }
    .box3 {
        font-size: 22px;
        line-height: 30px;
        font-weight: 550;
        color: rgba($color: #ccc, $alpha: 0.8);
        text-align: center;
        width: 100%;
        height: 30px;
        margin: auto;

        // margin-top: 20px;
        // line-height: 100%;
    }
}
</style>