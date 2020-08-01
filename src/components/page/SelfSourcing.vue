<template>
    <div class="SelfSourcing">
        <div class="top">
            <div class="start">
                <div class="title">起运地</div>
                <div class="block">
                    <el-col :span="20">
                        <el-autocomplete
                            class="inline-input"
                            v-model="state1"
                            :fetch-suggestions="querySearch"
                            placeholder="请输入内容"
                            :trigger-on-focus="false"
                            @select="startChang"
                        ></el-autocomplete>
                    </el-col>
                </div>
            </div>
            <div class="end">
                <div class="title">目的地</div>
                <div class="block">
                    <el-col :span="20">
                        <el-autocomplete
                            class="inline-input"
                            v-model="state2"
                            :fetch-suggestions="querySearch"
                            placeholder="请输入内容"
                            :trigger-on-focus="false"
                            @select="endChang"
                        ></el-autocomplete>
                    </el-col>
                </div>
            </div>

            <div class="main">
                <el-checkbox v-model="checked">我的区域</el-checkbox>
            </div>
            <div class="but">
                <el-button type="success" @click="getData">查询</el-button>
                <el-button type="success" @click="reset">重置</el-button>
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
                <el-table-column prop="oid" label="订单号" width="80" class="el-table__fixed"></el-table-column>
                <el-table-column prop="zid" label="起运地" width="240" class="el-table__fixed"></el-table-column>
                <el-table-column prop="xid" label="目的地" width="240" class="el-table__fixed"></el-table-column>
                <el-table-column prop="on" label="货物名称" width="120" class="el-table__fixed"></el-table-column>
                <el-table-column prop="yft" label="可接数量" width="80" align="right"></el-table-column>
                <el-table-column prop="qyf" label="单车运费" width="80" align="right">
                    <template scope="scope">
                        <span style="color: red">{{scope.row.qyf}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop label="客服服务费" width="100" align="right">--</el-table-column>
                <el-table-column prop="dis" label="距离(km)" width="100" align="right"></el-table-column>
                <el-table-column prop="dw" label="单车重量(吨)" width="120" align="right"></el-table-column>
                <el-table-column prop="dv" label="单车体积(方)" width="110" align="right"></el-table-column>
                <el-table-column prop="zdy" label="装货时间" width="120" align="right"></el-table-column>
                <el-table-column prop="te" label="运输类型" width="100" :formatter="stateFormat3"></el-table-column>
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
                :page-size="15"
                layout="total,  prev, pager, next, jumper"
                :total="totalNumber"
            ></el-pagination>
        </div>
        <!-- 弹出框 -->
        <el-dialog title="接单" :visible.sync="editVisible" width="600px">
            <div class="dialog">
                <div class="head">
                    <div class="title">
                        <div class="idNum">ID:{{id}}</div>
                        <div class="time">{{time}}</div>
                    </div>
                    <div class="center">
                        <div class="start">{{start}}</div>
                        <div class="middle">
                            <div class="distance">{{distance}}公里</div>
                            <div class="arrow">
                                <img src="../../assets/img/jt.png" alt />
                            </div>
                        </div>
                        <div class="end">{{end}}</div>
                    </div>

                    <div class="type">{{type}}</div>
                </div>
                <div class="nav">
                    <div class="information">
                        <div class="title">货物信息</div>
                        <div class="frame">
                            <div class="news">
                                <div class="name">货物信息：</div>
                                <div class="text">私家汽车，{{dw?dw+"吨":""}}{{dv?dv+"方,":""}}{{on?on:""}}</div>
                            </div>
                            <div class="time">
                                <div class="name">装货时间：</div>
                                <div class="text">{{zdy?zdy+" ":""}} {{zte?zte:""}}</div>
                            </div>
                            <div class="freight">
                                <div class="name">期望运费：</div>
                                <div class="text">{{qyf?qyf+"元":""}}</div>
                            </div>
                        </div>
                    </div>
                    <div class="demand">
                        <div class="title">用车需求</div>
                        <div class="frame">
                            <div class="Requirement">
                                <div class="name">用车需求：</div>
                                <div class="text">整车，{{ct?ct+"，":""}}{{cl?cl:""}}</div>
                            </div>
                            <div class="other">
                                <div class="name">其他要求：</div>
                                <div class="text">整车，{{zxte?zxte:""}}{{pte?pte:""}}三不超、禁止配货</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="foot">
                    <el-button
                        type="primary"
                        style="width:243px;height:40px;background:#003399"
                        @click="determine"
                    >接单</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
export default {
    data() {
        return {
            query: {
                address: '',
                name: ''
            },
            form: { name: '', address: '' },
            editVisible: false,
            tableData: [],
            options: [],
            loginData: {},
            radio: '1',
            pageIndex: 1,
            pageSize: 18,
            state1: '',
            state2: '',
            restaurants: [],
            startId: '',
            endId: '',
            page: '',
            currentPage: 1,
            totalNumber: 0,
            checked: false,
            id: 20030405123,
            start: '广州 佛山',
            distance: 150,
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
            other: '整车，货到现付，一装一卸，三不超、禁止配货'
        };
    },
    created() {
        this.getData();
    },
    methods: {
        getData() {
            let action = this.$store.state.Login.url + '/30001';
            this.loginData = JSON.parse(this.$store.state.Login.loginData);
            // console.log(this.loginData);

            var bd = {
                tid: this.loginData.tid,
                zid: this.startId,
                xid: this.endId,
                ct: '',
                cl: '',
                te: '',
                tte: '',
                zdy: '',
                pg: this.pageIndex,
                sz: this.pageSize
            };

            this.$axios
                .post(action, {
                    hd: {
                        pi: 30001,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    // this.page=
                    let data = JSON.parse(res.data.bd);
                    this.totalNumber = data.pg.tn;
                    let tableData = data.olst;
                    // console.log(tableData);
                    this.startProvinceChange(tableData);
                })
                .catch(error => {
                    console.log(error);
                });
        },

        startProvinceChange(tableData) {
            this.loginData = JSON.parse(this.$store.state.Login.loginData);

            var storage = window.localStorage;
            let AddressList = JSON.parse(storage.AddressList);
            this.restaurants = AddressList;

            tableData.forEach(item => {
                this.restaurants.forEach(e => {
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
        startChang(item) {
            this.startId = item.id;
        },
        endChang(item) {
            this.endId = item.id;
        },
        reset() {
            this.state1 = '';
            this.state2 = '';
        },
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
            if (row.te == 1) {
                return '一装一卸';
            }
            if (row.te == 2) {
                return '一装两卸';
            }
            if (row.te == 3) {
                return '一装多卸';
            }
            if (row.te == 4) {
                return '两装一卸';
            }
            if (row.state == 5) {
                return '两装两卸';
            }
            if (row.te == 6) {
                return '两装多卸';
            } else {
                return '其他';
            }
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            let num = Math.ceil(this.totalNumber / this.query.pageSize);
            if (val > num) {
                alert("输入页数超出范围")
            } else {
                this.pageIndex = num;
                this.getData();
            }
            // let action = this.$store.state.Login.url + '/30001';
            // this.loginData = JSON.parse(this.$store.state.Login.loginData);
            // // console.log(this.loginData);

            // var bd = {
            //     tid: this.loginData.tid,
            //     zid: this.startId,
            //     xid: this.endId,
            //     ct: '',
            //     cl: '',
            //     te: '',
            //     tte: '',
            //     zdy: '',
            //     pg: val,
            //     sz: this.pageSize
            // };

            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 30001,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         // this.page=
            //         let data = JSON.parse(res.data.bd);

            //         this.totalNumber = data.pg.tn;
            //         let tableData = data.olst;
            //         // console.log(tableData);
            //         this.startProvinceChange(tableData);
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
        },
        headClass() {
            return 'text-align: center;background:#ff0000;';
        },
        reset() {
            this.state1 = '';
            this.state2 = '';
            this.startId = '';
            this.endId = '';
            this.checked = false;
            this.getData();
        },
        saveEdit() {
            this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            this.$set(this.tableData, this.idx, this.form);
        },

        receipt(index, row) {
            this.oid = row.oid;
            this.idx = index;
            this.form = row;
            this.editVisible = true;

            let action = this.$store.state.Login.url + '/30002';
            this.loginData = JSON.parse(this.$store.state.Login.loginData);

            var bd = {
                tid: this.loginData.tid,
                oid: row.oid
            };
            // console.log(row);
            this.$axios
                .post(action, {
                    hd: {
                        pi: 30002,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let dataList = JSON.parse(res.data.bd);

                    // console.log(dataList);
                    this.id = dataList.ocd;
                    this.time = dataList.ctm;
                    this.start = dataList.zad;
                    this.end = dataList.xad;

                    this.ct = dataList.ct;
                    this.cl = dataList.cl;
                    this.dw = dataList.dw;
                    this.dv = dataList.dv;
                    this.on = dataList.on;
                    this.zdy = dataList.zdy;
                    this.zte = dataList.zte;
                    this.qyf = dataList.qyf;
                    if (dataList.pte == 1) {
                        dataList.pte = '一装一卸，';
                    }
                    if (dataList.pte == 2) {
                        dataList.pte = '一装两卸，';
                    }
                    if (dataList.pte == 3) {
                        dataList.pte = '一装多卸，';
                    }
                    if (dataList.pte == 4) {
                        dataList.pte = '两装一卸，';
                    }
                    if (dataList.pte == 5) {
                        dataList.pte = '两装两卸，';
                    }
                    if (dataList.pte == 6) {
                        dataList.pte = '两装多卸，';
                    }
                    this.pte = dataList.pte;

                    if (dataList.zxte == 1) {
                        dataList.zxte = '平台付款，';
                    }
                    if (dataList.zxte == 2) {
                        dataList.zxte = '货到付款，';
                    }
                    this.zxte = dataList.zxte;
                })
                .catch(error => {
                    console.log(error);
                });
        },
        handleClick(tab, event) {
            console.log(tab, event);
        },
        determine() {
            let action = this.$store.state.Login.url + '/20008';
            this.loginData = JSON.parse(this.$store.state.Login.loginData);
            // console.log(this.loginData);

            var bd = {
                tid: this.loginData.tid,
                oid: this.oid
            };

            this.$axios
                .post(action, {
                    hd: {
                        pi: 20008,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let body = res.data.hd;
                    let rid = body.rid;

                    if (rid == 0) {
                        this.editVisible = false;
                        this.open1();
                    } else {
                        this.open1();
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        open1() {
            this.$message({
                message: '接单成功',
                type: 'success'
            });
        },
        open2() {
            this.$message({
                showClose: true,
                message: '接单失败',
                type: 'error'
            });
        }
    },
    mounted() {
        this.restaurants = this.loadAll();
    }
};
</script>
<style lang="scss">
// .el-table--small td {
//     padding: 2px 0;
// }
.el-dialog__header {
    padding: 10px 10px;
    background: #409eff;

    .el-dialog__title {
        color: #fff;
    }
}
.el-dialog__body {
    padding: 0 20px 20px 20px;
}
.btn-prev:before {
    content: '上一页';
}
.btn-next:before {
    content: '下一页';
}

.el-icon-arrow-left,
.el-icon-arrow-right {
    display: none !important;
}
.el-dialog__headerbtn .el-dialog__close {
    color: #fff;
}
</style>
<style lang="scss" scoped>
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
            // height: 100%;
        }
    }
    .page {
        margin-top: 10px;
        padding-bottom: 10px;
        height: 32px;
        text-align: right;
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

.el-dialog {
    .dialog {
        width: 563px;
        padding: 0 10px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        // border: 1px solid rgba($color: #000000, $alpha: 0.3);

        .head {
            margin-top: 10px;
            background: #009dd9;
            padding: 0 60px;
            height: 134px;
            .title {
                display: flex;
                justify-content: space-between;
                color: #fff;
                font-size: 14px;
                line-height: 14px;
                padding-top: 8px;
                padding-bottom: 15px;
            }
            .center {
                display: flex;
                height: 40px;
                justify-content: space-between;
                align-items: center;
                .start {
                    width: 130px;
                    text-align: center;
                    font-size: 24px;
                    line-height: 24px;
                    color: #fff;
                    padding-top: 8px;
                }
                .middle {
                    .distance {
                        font-size: 14px;
                        line-height: 14px;
                        color: #fff;
                        text-align: center;
                    }
                }
                .end {
                    width: 130px;
                    text-align: center;
                    font-size: 24px;
                    line-height: 24px;
                    color: #fff;
                    padding-top: 8px;
                }
            }
            .type {
                text-align: center;
                width: 101px;
                height: 32px;
                line-height: 18px;
                color: #fff;
                margin: auto;
                background: #00cc00;
                line-height: 32px;
                margin-bottom: 20px;
                border-radius: 6px;
            }
        }
        .nav {
            display: flex;
            flex-direction: column;
            .information {
                .title {
                    font-size: 20px;
                    line-height: 50px;
                    color: rgba($color: #000000, $alpha: 0.5);
                    text-align: center;
                }
                .frame {
                    border: 1px solid rgba($color: #000000, $alpha: 0.5);
                    padding: 0 60px;
                    .news,
                    .time,
                    .freight {
                        margin: 15px 0;
                        display: flex;
                        align-items: center;
                        .name {
                            font-size: 14px;
                            color: #333333;
                            margin-right: 8px;
                        }
                        .text {
                            font-size: 18px;
                            font-weight: 700;
                            line-height: 34px;
                        }
                    }
                }
            }
            .demand {
                .title {
                    font-size: 20px;
                    line-height: 50px;
                    color: rgba($color: #000000, $alpha: 0.5);
                    text-align: center;
                }
                .frame {
                    border: 1px solid rgba($color: #000000, $alpha: 0.5);
                    padding-left: 60px;
                    .Requirement,
                    .other {
                        margin: 15px 0;
                        display: flex;
                        align-items: center;
                        .name {
                            font-size: 14px;
                            color: #333333;
                            margin-right: 8px;
                        }
                        .text {
                            font-size: 18px;
                            font-weight: 700;
                            line-height: 34px;
                        }
                    }
                }
            }
        }
        .foot {
            margin-top: 16px;
            // padding-bottom: 16px;
            text-align: center;
            .el-button {
                font-size: 18px;
                // line-height: 40px;
            }
        }
    }
}
</style>
