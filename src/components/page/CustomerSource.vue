<template>
    <!-- 客户货源 -->
    <div class="SelfSourcing">
        <div class="top">
            <div class="start">
                <div class="block">
                    <el-col :span="40">
                        <el-autocomplete
                            class="inline-input"
                            v-model="search"
                            :fetch-suggestions="querySearch"
                            placeholder
                            :trigger-on-focus="false"
                            @select="startChang"
                            :maxlength="20"
                        ></el-autocomplete>
                    </el-col>
                </div>
            </div>

            <div class="but">
                <el-button type="success" @click="getData" :disabled="But">查询</el-button>
                <el-button type="success" @click="reset">重置</el-button>
                <!-- <el-button type="success" @click="getData" :disabled="But">我的客户</el-button> -->
            </div>
        </div>
        <div class="container">
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                :fit="true"
                :header-cell-style="headClass"
            >
                <el-table-column width="50" align="center">
                    <template scope="scope">{{scope.$index + addIndex}}</template>
                </el-table-column>
                <el-table-column
                    prop="oid"
                    label="订单号"
                    width="80"
                    class="el-table__fixed"
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="zid"
                    label="起运地"
                    :show-overflow-tooltip="true"
                    class="el-table__fixed"
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="xid"
                    label="目的地"
                    :show-overflow-tooltip="true"
                    class="el-table__fixed"
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="on"
                    label="货物名称"
                    width="120"
                    class="el-table__fixed"
                    align="center"
                ></el-table-column>
                <!-- <el-table-column prop="yft" label="可接数量" width="80" align="right"></el-table-column>
                <el-table-column prop="qyf" label="单车运费" width="80" align="right">
                    <template scope="scope">
                        <span style="color: red">{{scope.row.qyf}}</span>
                    </template>
                </el-table-column>-->
                <!-- <el-table-column prop label="客服服务费" width="100" align="right">--</el-table-column> -->
                <el-table-column prop="dis" label="距离(km)" width="100" align="right"></el-table-column>
                <el-table-column prop="dw" label="单车重量(吨)" width="120" align="right"></el-table-column>
                <el-table-column prop="dv" label="单车体积(方)" width="110" align="right"></el-table-column>
                <el-table-column prop="zdy" label="装货时间" width="120" align="center"></el-table-column>
                <el-table-column prop="zxte" label="运输类型" width="100" :formatter="stateFormat3"></el-table-column>
                <!-- <el-table-column prop="te" label="车辆要求" width="100" :formatter="stateFormat1"></el-table-column>
                <el-table-column prop="cl" label="车长要求" width="100" :formatter="stateFormat2"></el-table-column>
                <el-table-column prop="utm" label="创建时间" width="200"></el-table-column>-->
                <el-table-column label="操作" width="80" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="success"
                            size="mini"
                            @click="receipt(scope.$index, scope.row)"
                        >接单</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="page">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="21"
                layout="total,  prev, pager, next, jumper"
                :total="totalNumber"
            ></el-pagination>
        </div>

        <!-- 弹出框 -->
        <!-- <el-dialog title="接单" :visible.sync="editVisible" width="775px" class="dialog">
            <el-container>
                <el-header style="height:156px">
                    <div class="head">
                        <div class="bg-img">
                            <div class="title">
                                <div class="name">客服订单</div>
                            </div>
                            <div class="right">
                                <div class="id">
                                    ID:
                                    <span>{{id}}</span>
                                </div>
                                <div class="ct">
                                    <span>{{time}}</span>
                                </div>
                            </div>
                        </div>
                        <div class="nav">
                            <div class="start">{{start}}</div>

                            <div class="distance">
                                <span>{{distance}}</span> 公里
                            </div>

                            <div class="end">{{end}}</div>
                        </div>
                    </div>
                </el-header>
                <el-main>
                    <div class="CargoInformation">
                        <div class="title">货物信息</div>
                        <div class="center">
                            <div class="text">
                                <div class="key">货物信息：</div>
                                <div
                                    class="value"
                                >私家汽车，{{dw?dw+"吨，":""}}{{dv?dv+"方,":""}}{{on?on:""}}</div>
                            </div>
                            <div class="text">
                                <div class="key">期望运费：</div>
                                <div class="value">{{qyf?qyf+"元":""}}</div>
                            </div>
                            <div class="text">
                                <div class="key">装货时间：</div>
                                <div class="value">{{zdy?zdy+" ":""}} {{zte?zte:""}}</div>
                            </div>
                        </div>
                    </div>
                    <div class="VehicleDemand">
                        <div class="title">用车需求</div>
                        <div class="center">
                            <div class="text">
                                <div class="key">用车需求：</div>
                                <div class="value">整车，{{ct?ct+"，":""}}{{cl?cl:""}}</div>
                            </div>
                            <div class="text">
                                <div class="key">其他需求：</div>
                                <div class="value">整车，{{zxte?zxte:""}}{{pte?pte:""}}三不超、禁止配货</div>
                            </div>
                        </div>
                    </div>
                    <div class="CargoPicture">
                        <div class="title">货物照片</div>
                        <div class="center">
                            <div class="img">
                                <img
                                    src="../../assets/img/frame.png"
                                    preview="1"
                                    preview-textss
                                    alt
                                />
                            </div>
                            <div class="img">
                                <img
                                    src="../../assets/img/frame.png"
                                    preview="1"
                                    preview-textss
                                    alt
                                />
                            </div>
                        </div>
                    </div>
                </el-main>
                <el-footer style="height:50px;">
                    <el-button type="primary" style="width:144px;" @click="determine">接单</el-button>
                </el-footer>
            </el-container>
            <el-dialog :visible.sync="dialogVisible">
                <img width="100%" src="../../assets/img/logo.jpg" alt />
            </el-dialog>
        </el-dialog>-->
        <myMeet ref="myMeet" @getData="getData"></myMeet>
    </div>
</template>

<script>
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
import myMeet from '../popup/myMeet.vue';
export default {
    data() {
        return {
            addIndex: 1,
            query: {
                address: '',
                name: '',
                pageSize: 15
            },
            But: false,

            show: false,
            form: { name: '', address: '' },
            editVisible: false,
            tableData: [],
            options: [],
            loginData: {},
            sk: '',
            pageIndex: 1,
            pageSize: 21,

            restaurants: [],
            search: '',
            page: '',
            currentPage: 1,
            totalNumber: 0,
            id: 20030405123,
            start: '广州 佛山',
            distance: '',
            type: '客服订单',
            time: '2020-12-12 12:12:12',
            end: '广西 云南',
            ct: '',
            cl: '',
            dw: '',
            dv: '',
            on: '',
            zdy: '',
            zte: '',
            qyf: '',
            pte: '',
            zxte: '',
            oid: '',
            price: '7500',
            time: '2020-04-08',
            Requirement: '整车，平板，高栏，底栏，9.6米、13.5米',
            other: '整车，货到现付，一装一卸，三不超、禁止配货',
            dialogVisible: false,
            CargoPicture1: require('../../assets/img/frame.png'),
            CargoPicture2: require('../../assets/img/frame.png')
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.CustomerData;
        this.getData();
        // this.get();
    },
    methods: {
        //获取货源数据
        async getData() {
            var bd = {
                tid: this.loginData.tid,
                zid: '',
                xid: '',
                ss: this.search,
                qt: this.checked ? 1 : 0,
                ct: '',
                cl: '',
                te: '',
                tte: '',
                zdy: '',
                pg: this.pageIndex,
                sz: this.pageSize
            };
            let hd = {
                pi: 30001,
                si: this.loginData.si,
                sq: 9
            };

            let res = await request('/30001', hd, bd);
            // console.log(res);
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
                this.totalNumber = data.pg.tn;
                let tableData = data.olst;

                if (tableData.length == 0) {
                    this.tableData = [];
                } else {
                    this.startProvinceChange(tableData);
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
        startProvinceChange(tableData) {
            this.loginData = this.$store.state.Login.CustomerData;

            var storage = window.localStorage;
            let AddressList = JSON.parse(storage.AddressList);
            this.restaurants = AddressList;
            // console.log(AddressList);

            tableData.forEach((item) => {
                this.restaurants.forEach((e) => {
                    if (item.zid == e.id) {
                        item.zid = e.value;
                    }
                    if (item.xid == e.id) {
                        item.xid = e.value;
                    }
                });
            });
            this.tableData = tableData;
        },
        //返回建议搜索框的方法
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
        startChang(item) {
            this.search = item.address;
        },
        //处理返回数据
        stateFormat1(row) {
            if (row.te == 1) {
                return '平板';
            }
            if (row.te == 2) {
                return '高栏';
            }
            if (row.te == 3) {
                return '中栏';
            }
            if (row.te == 4) {
                return '低栏';
            }
            if (row.state == 5) {
                return '高低板';
            }
            if (row.te == 6) {
                return '厢式';
            }
            if (row.te == 7) {
                return '自卸';
            }
            if (row.te == 8) {
                return '保温';
            }
            if (row.te == 9) {
                return '冷藏';
            }
            if (row.te == 10) {
                return '危险品';
            }
            if (row.te == 11) {
                return '集装箱';
            }
            if (row.te == 12) {
                return '特种';
            }
            if (row.te == 13) {
                return '其他';
            } else {
                return '其他';
            }
        },
        stateFormat2(row) {
            if (row.te == 1) {
                return '4.2米';
            }
            if (row.te == 2) {
                return '5.0米';
            }
            if (row.te == 3) {
                return '6.2米';
            }
            if (row.te == 4) {
                return '6.8米';
            }
            if (row.state == 5) {
                return '7.2米';
            }
            if (row.te == 6) {
                return '7.7米';
            }
            if (row.te == 7) {
                return '7.8米';
            }
            if (row.te == 8) {
                return '8.2米';
            }
            if (row.te == 9) {
                return '8.7米';
            }
            if (row.te == 10) {
                return '9.6米';
            }
            if (row.te == 11) {
                return '12.5米';
            }
            if (row.te == 12) {
                return '13.0米';
            }
            if (row.te == 13) {
                return '14.6米';
            }
            if (row.te == 14) {
                return '15.0米';
            }
            if (row.te == 15) {
                return '16.5米';
            }
            if (row.te == 16) {
                return '17.5米';
            }
            if (row.te == 17) {
                return '其他';
            } else {
                return '其他';
            }
        },
        stateFormat3(row) {
            if (row.zxte == 1) {
                return '一装一卸';
            }
            if (row.zxte == 2) {
                return '一装两卸';
            }

            if (row.zxte == 3) {
                return '两装一卸';
            }
            if (row.zxte == 4) {
                return '两装两卸';
            }
        },
        //分页方法
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            let num = Math.ceil(this.totalNumber / this.query.pageSize);
            console.log(val);
            if (val > num) {
                alert('输入页数超出范围');
            } else {
                this.pageIndex = val;
                this.addIndex = (this.currentPage - 1) * this.pageSize + 1;
                this.getData();
            }
        },
        headClass() {
            return 'text-align: center;background:#ff0000;';
        },
        //重置按钮方法
        reset() {
            this.checked = false;
            this.search = '';
            this.getData();
        },
        saveEdit() {
            this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            this.$set(this.tableData, this.idx, this.form);
        },
        //客服货源详情和接单
        async receipt(index, row) {
            this.oid = row.oid;
            this.idx = index;
            this.form = row;
            this.editVisible = true;

            let action = this.$store.state.Login.url + '/30002';
            this.loginData = this.$store.state.Login.CustomerData;

            var bd = {
                tid: this.loginData.tid,
                oid: row.oid
            };
            let hd = {
                pi: 30002,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30002', hd, bd);
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                let dataList = {};
                if (this.loginData.em == 0) {
                    dataList = JSON.parse(res.data.bd);
                }
                if (this.loginData.em == 1) {
                    let decryptData = encryptUtil.decrypt(res.data.bd, this.sk);
                    dataList = JSON.parse(decryptData);
                }
                let http = this.loginData.murl;
                // console.log(dataList.pic)
                if (dataList.pic) {
                    let srcArr = dataList.pic.split(',');
                    let imgArr = [];
                    srcArr.map((item) => {
                        imgArr.push(http + item);
                    });
                    this.CargoPicture1 = imgArr[0];
                    this.CargoPicture2 = imgArr[1];
                }

                this.id = dataList.ocd;
                this.time = dataList.ctm;
                this.start = dataList.zad;
                this.end = dataList.xad;
                this.distance = dataList.dis;
                this.ct = dataList.ct;
                this.cl = dataList.cl;
                this.dw = dataList.dw;
                this.dv = dataList.dv;
                this.on = dataList.on;
                this.zdy = dataList.zdy;
                this.zte = dataList.zte;
                this.qyf = dataList.qyf;
                if (dataList.zxte == 1) {
                    dataList.zxte = '一装一卸，';
                }
                if (dataList.zxte == 2) {
                    dataList.zxte = '一装两卸，';
                }
                if (dataList.zxte == 3) {
                    dataList.zxte = '两装一卸，';
                }
                if (dataList.zxte == 4) {
                    dataList.zxte = '两装两卸，';
                }

                // this.pte = dataList.pte;

                if (dataList.pte == 1) {
                    dataList.pte = '平台付款，';
                }
                if (dataList.pte == 2) {
                    dataList.pte = '货到付款，';
                }
                this.zxte = dataList.zxte;
                //接单
                this.popup2 = {
                    tid: this.loginData.tid,
                    oid: row.oid,
                    data: dataList
                };
                this.$store.commit('setPopup2', this.popup2);
                this.$refs.myMeet.receipt();
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        handleClick(tab, event) {
            console.log(tab, event);
        },
        determine() {
            // let action = this.$store.state.Login.url + '/20008';
            // this.loginData = this.$store.state.Login.CustomerData;
            // // console.log(this.loginData);
            // var bd = {
            //     tid: this.loginData.tid,
            //     oid: this.oid
            // };
            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 20008,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         let body = res.data.hd;
            //         let rid = body.rid;
            //         if (rid == 0) {
            //             this.editVisible = false;
            //             this.open1();
            //         } else {
            //             this.open1();
            //         }
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
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
        }
    },
    mounted() {
        this.restaurants = this.loadAll();
    },
    components: {
        myMeet
    }
};
</script>
<style >
/* .el-table__body tr.current-row > td {
    background-color: #a4ccec;
}
.el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #a4ccec;
} */
.el-icon-close:hover {
    background: #eee;
    color: #fff;
}
.right-menu a:hover {
    background: #eee;
    color: #fff;
}
</style>
<style lang="scss">
// .btn-prev:before {
//     content: '上一页';
// }
// .btn-next:before {
//     content: '下一页';
// }

// .el-icon-arrow-left,
// .el-icon-arrow-right {
//     display: none !important;
// }
.el-dialog__headerbtn .el-dialog__close {
    color: #fff;
}
.pswp,
.pswp--supports-fs,
.pswp--open,
.pswp--animate_opacity,
.pswp--notouch,
.pswp--css_animation,
.pswp--svg,
.pswp--zoom-allowed,
.pswp--visible,
.pswp--zoom-allowed,
.pswp--animated-in,
.pswp--has_mouse {
    z-index: 9999 !important;
}
.pswp__bg {
    background: rgba($color: #000000, $alpha: 0.5);
}

.el-dialog {
    margin-top: 10vh !important;
}
</style>
<style lang="scss" scoped>
/deep/.el-table--small td {
    padding: 3px 0;
}
.SelfSourcing {
    display: flex;
    flex-direction: column;
    height: 100%;

    td {
        padding: 0;
    }
    .top {
        height: 42px;
    }
    .container {
        flex: 1;
        .table {
            height: 100%;
            /deep/.el-table__body-wrapper {
                height: 95% !important;
            }
        }
    }
    .page {
        margin-top: 10px;
        padding-bottom: 10px;
        height: 32px;
        text-align: right;
        /deep/ .el-pagination {
            /deep/ .btn-prev:before {
                content: '上一页';
            }
            /deep/ .btn-next:before {
                content: '下一页';
            }
            /deep/ .el-icon-arrow-left,
            .el-icon-arrow-right {
                display: none !important;
            }
        }
    }
}
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
.top {
    display: flex;
    align-items: center;
    padding-bottom: 10px;
    .block {
        display: flex;
    }
    .type {
        display: flex;
        align-items: center;
        padding-bottom: 10px;
        .title {
            margin-right: 10px;
        }
    }
    .start {
        display: flex;
        align-items: center;

        .title {
            margin-right: 10px;
        }
    }
    .end {
        display: flex;
        align-items: center;

        .title {
            margin-right: 10px;
        }
    }
    .el-button {
        margin-left: 20px;
    }
    .el-table--small td,
    .el-table--small th {
        padding: 0;
    }
    .el-table td,
    .el-table th {
        padding: 0;
    }
}

.dialog {
    /deep/.el-dialog__header {
        padding: 10px 10px;
        background: #409eff;
        display: flex;
        justify-content: space-between;
        align-items: center;

        .el-dialog__title {
            color: #fff;
        }
    }
    /deep/.el-dialog__body {
        margin: auto;
    }
    /deep/.el-dialog__headerbtn {
        top: 16px;
    }
    /deep/.el-dialog__body {
        padding: 10px 20px;
        color: #606266;
        font-size: 14px;
        word-break: break-all;
    }
    .el-container {
        width: 775px;
        height: 700px;
        padding-top: 10px;
        .el-header {
            width: 100%;
            height: 178px;
            background-image: url(../../assets/img/bg1.png);
            background-size: contain;
            background-repeat: no-repeat;
            padding: 0;

            margin-left: 10px;
            margin-right: 10px;
            .head {
                height: 156px;
                margin-bottom: 20xp;

                .bg-img {
                    display: flex;
                    box-sizing: border-box;
                    .title {
                        width: 77px;
                        height: 74px;
                        background-image: url(../../assets/img/loading.png);
                        background-repeat: no-repeat;
                        color: #fff;
                        .name {
                            transform: rotate(-44deg);
                            text-align: left;
                            font-size: 12px;
                            line-height: 25px;
                            width: 80px;
                            height: 30px;
                        }
                    }
                    .right {
                        flex: 1;
                        display: flex;
                        height: 30px;
                        justify-content: space-between;
                        padding: 0 30px 0 0px;
                        .id,
                        .ct {
                            color: #fff;
                            font-size: 16px;
                            line-height: 30px;
                        }
                    }
                }
                .nav {
                    display: flex;
                    position: relative;
                    padding: 0 83px;
                    justify-content: space-between;

                    .start {
                        color: #fff;
                        font-size: 20px;
                        padding-top: 10px;
                    }
                    .end {
                        color: #fff;
                        font-size: 20px;
                        padding-top: 10px;
                        margin-right: 17px;
                    }
                    .distance {
                        padding-top: 35px;
                        color: #fff;
                        font-size: 22px;
                        margin-right: 8px;
                    }
                }
            }
        }

        .el-main {
            margin-left: 10px;
            margin-right: 10px;
            .CargoInformation {
                margin-top: 30px;
                .title {
                    width: 140px;
                    height: 26px;
                    background: #166cf3;
                    border-radius: 10px 0 10px 0;
                    font-size: 16px;
                    line-height: 26px;
                    text-align: center;
                    color: #fff;
                    margin-left: 42px;
                }
                .center {
                    box-sizing: border-box;
                    width: 648px;
                    height: 150px;
                    padding: 20px;
                    margin: auto;
                    border-radius: 0 10px 10px 10px;
                    box-shadow: 0px 0px 2px 1px rgba(0, 0, 0, 0.2);
                    .text {
                        display: flex;
                        align-items: center;
                        margin-bottom: 20px;
                        .key {
                            color: rgba($color: #000000, $alpha: 0.5);
                        }
                        .value {
                            font-size: 16px;
                            // font-weight: 600;
                            color: #000;
                        }
                    }
                }
            }
            .VehicleDemand {
                margin-top: 30px;
                .title {
                    width: 140px;
                    height: 26px;
                    background: #166cf3;
                    border-radius: 10px 0 10px 0;
                    font-size: 16px;
                    line-height: 26px;
                    text-align: center;
                    color: #fff;
                    margin-left: 42px;
                }
                .center {
                    box-sizing: border-box;
                    width: 648px;
                    height: 120px;
                    padding: 20px;
                    margin: auto;
                    border-radius: 0 10px 10px 10px;
                    box-shadow: 0px 0px 2px 1px rgba(0, 0, 0, 0.2);
                    .text {
                        display: flex;
                        align-items: center;
                        margin-bottom: 20px;
                        .key {
                            color: rgba($color: #000000, $alpha: 0.5);
                        }
                        .value {
                            font-size: 16px;
                            // font-weight: 600;
                            color: #000;
                        }
                    }
                }
            }
            .CargoPicture {
                margin-top: 30px;
                .title {
                    width: 140px;
                    height: 26px;
                    background: #166cf3;
                    border-radius: 10px 0 10px 0;
                    font-size: 16px;
                    line-height: 26px;
                    text-align: center;
                    color: #fff;
                    margin-left: 42px;
                }
                .center {
                    box-sizing: border-box;
                    width: 648px;
                    height: 191px;
                    padding: 20px;
                    margin: auto;
                    border-radius: 0 10px 10px 10px;
                    box-shadow: 0px 0px 2px 1px rgba(0, 0, 0, 0.2);
                    display: flex;
                    .img {
                        margin-right: 20px;
                        img {
                            width: 218px;
                            height: 151px;
                        }
                    }
                }
                padding-bottom: 20px;
            }
        }
        .el-footer {
            height: 40px;
            background: rgba($color: #ccc, $alpha: 0.2);
            border: none;
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
    .PicturePreview {
        max-width: 600px;
        max-height: 600px;
    }
    .el-main::-webkit-scrollbar {
        display: none;
    }
}
</style>
