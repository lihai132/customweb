<template>
    <div class="container">
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
                            style="margin:0 0 0px 0px; width:250px"
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
                            style="margin:0 0 0px 0px; width:250px"
                        ></el-autocomplete>
                    </el-col>
                </div>
                <div class="but">
                    <el-button type="success" @click="getData">查询</el-button>
                    <!-- <el-button type="success" @click="getData">刷新</el-button> -->
                </div>
            </div>
        </div>

        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="成交价" name="first">
                <div class>
                    <el-table
                        :data="tableData"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        :height="tableHeight"
                        :fit="true"
                        :header-cell-style="headClass"
                        :cell-style="cellBg"
                    >
                        <el-table-column width="80">
                            <template scope="scope">{{scope.$index + addIndex}}</template>
                        </el-table-column>
                        <el-table-column prop="zid" label="起运地" width="320" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="xid" label="目的地" width="320" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="dis" label="距离(公里)" width="120"></el-table-column>
                        <el-table-column prop="qt" label="成交价(元)" width="120" align="right">
                            <template slot-scope="scope">
                                <span style="color: red">{{scope.row.qt}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="on" label="货物信息" width="200" show-overflow-tooltip>
                            <template slot-scope="scope">
                                <span>{{scope.row.on}} {{scope.row.dw}}吨 {{scope.row.dv}}方</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="te" label="运输车辆" width="170" show-overflow-tooltip>
                            <template slot-scope="scope">
                                <span>{{scope.row.te == 1 ? '整车' : '配货'}} {{stateFormat1(scope.row)}} {{stateFormat2(scope.row)}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="ne" label="承运人" width="120"></el-table-column>
                        <el-table-column prop="ctm" label="成交时间" min-width="160" show-overflow-tooltip></el-table-column>
                    </el-table>
                </div>
                <div class="page">
                    <el-pagination
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-size="20"
                        layout="total,  prev, pager, next, jumper"
                        :total="totalNumber"
                    ></el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="报 价" name="second">
                <div class>
                    <el-table
                        :data="quote_tableData"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        :height="tableHeight"
                        :fit="true"
                        :header-cell-style="headClass"
                        :cell-style="cellBg"
                    >
                        <el-table-column width="80">
                            <template scope="scope">{{scope.$index + addIndex}}</template>
                        </el-table-column>
                        <el-table-column prop="zid" label="起运地" width="320" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="xid" label="目的地" width="320" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="dis" label="距离(公里)" width="120"></el-table-column>
                        <el-table-column prop="qt" label="成交价(元)" width="120" align="right">
                            <template slot-scope="scope">
                                <span style="color: red">{{scope.row.qt}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="on" label="货物信息" width="200" show-overflow-tooltip>
                            <template slot-scope="scope">
                                <span>{{scope.row.on}} {{scope.row.dw}}吨 {{scope.row.dv}}方</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="te" label="运输车辆" width="170" show-overflow-tooltip>
                            <template slot-scope="scope">
                                <span>{{scope.row.te == 1 ? '整车' : '配货'}} {{stateFormat1(scope.row)}} {{stateFormat2(scope.row)}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="ne" label="报价司机" width="110" show-overflow-tooltip></el-table-column>
                        <el-table-column prop="ctm" label="成交时间" min-width="160" show-overflow-tooltip></el-table-column>
                    </el-table>
                </div>
                <div class="page">
                    <el-pagination
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-size="20"
                        layout="total,  prev, pager, next, jumper"
                        :total="totalNumber2"
                    ></el-pagination>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- <div class="white"></div> -->
    </div>
</template>

<script>
import request from '../../assets/js/request.js';
import encryptUtil from '../../assets/js/encryptUtil.js';
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
export default {
    data() {
        return {
            addIndex: 1,
            query: {
                address: '',
                name: '',
                pageIndex: 1,
                pageSize: 10
            },
            tableData: [],
            quote_tableData: [],
            options: [],
            loginData: {},
            sk: '',
            radio: '1',
            state1: '',
            state2: '',
            restaurants: [],
            startId: '',
            endId: '',
            activeName: 'first',
            tableHeight: 600,
            currentPage: 1,
            totalNumber: 0,
            // currentPage2: 1,
            totalNumber2: 0,
            pageSize: 20
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData =
            this.$store.state.Login.LogingType == 3 ? this.$store.state.Login.DispatchData : this.$store.state.Login.CustomerData;
        // console.log(this.sk);
        this.getData();
    },
    mounted() {
        this.$nextTick(() => {
            this.tableHeight = window.innerHeight - this.$refs.multipleTable.$el.offsetTop - 380;
        });
        this.restaurants = this.loadAll();
    },
    methods: {
        handleCurrentChange(val) {
            this.currentPage = val;
            this.addIndex = (this.currentPage - 1) * this.pageSize + 1;
            this.getData();
            // console.log(`当前页: ${val}`);
        },
        // handleCurrentChange2(val) {
        //     this.currentPage2 = val;
        //     this.getData();
        //     // console.log(`当前页: ${val}`);
        // },
        handleClick(tab, event) {
            // console.log(tab, event);
        },
        headClass() {
            return 'background:#ff0000;color:#606266;text-align:center;';
        },
        cellBg({ row, column, rowIndex, columnIndex }) {
            if (columnIndex == 3 || columnIndex == 4) {
                return 'text-align:right;';
            } else if(columnIndex == 8){
                return 'text-align:left;'
            } else {
                return 'text-align:center;';
            }
            // return 'text-align:center;';
        },
        //获取成交价、报价数据
        async getData() {
            let action = `${this.$store.state.Login.url}/30041`; //成交价
            let q_action = `${this.$store.state.Login.url}/30042`; //报价
            // console.log(typeof this.$store.state.Login.LogingType);
            //3调度；4客服
            // this.loginData =
            //     this.$store.state.Login.LogingType == 3 ? this.$store.state.Login.DispatchData : this.$store.state.Login.CustomerData;

            // console.log(this.startId, this.endId);

            var bd = {
                tid: this.loginData.tid,
                zid: this.state1 == '' ? '' : this.startId,
                xid: this.state2 == '' ? '' : this.endId,
                pg: this.currentPage,
                sz: this.pageSize
            };
            // 成交价
            let hd = {
                pi: 30041,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30041', hd, bd);
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
                    // console.log(data);
                }
                // console.log(data);
                // this.tableData = data.olst;
                let tableData = data.olst;
                this.totalNumber = data.pg.tn;
                if (tableData.length == 0) {
                    this.tableData = [];
                } else {
                    this.tableData = this.startProvinceChange(tableData);
                    // console.log(this.tableData);
                }
            } else {
                this.$message({
                    message: res.data.hd.rmsg,
                    type: 'error',
                    duration: 900
                });
            }
            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 30041,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         let data = JSON.parse(res.data.bd);
            //         let tableData = data.olst;
            //         this.totalNumber = data.pg.tn;
            //         // console.log(data.olst);
            //         // this.startProvinceChange(tableData);
            //         this.tableData = this.startProvinceChange(tableData);
            //         // console.log()
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });

            //报价
            let _hd = {
                pi: 30042,
                si: this.loginData.si,
                sq: 9
            };
            let _res = await request('/30042', _hd, bd);
            // console.log(_res);
            let _rid = _res.data.hd.rid;
            if (_rid >= 0) {
                let data = {};
                if (this.loginData.em == 0) {
                    data = JSON.parse(_res.data.bd);
                }
                if (this.loginData.em == 1) {
                    let decryptData = encryptUtil.decrypt(_res.data.bd, this.sk);
                    data = JSON.parse(decryptData);
                }
                let tableData = data.olst;
                this.totalNumber2 = data.pg.tn;
                if (tableData.length == 0) {
                    this.quote_tableData = [];
                } else {
                    this.quote_tableData = this.startProvinceChange(tableData);
                }
            } else {
                this.$message({
                    message: _res.data.hd.rmsg,
                    type: 'error',
                    duration: 900
                });
            }

            // this.$axios
            //     .post(q_action, {
            //         hd: {
            //             pi: 30042,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         let data = JSON.parse(res.data.bd);
            //         let tableData = data.olst;
            //         this.totalNumber2 = data.pg.tn;
            //         // console.log(data.olst);
            //         this.quote_tableData = this.startProvinceChange(tableData);
            //         // console.log(data.olst)
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
        },
        //地址
        startProvinceChange(tableData) {
            // let action = `${this.$store.state.Login.url}/31009`;
            // this.loginData = this.$store.state.Login.DispatchData;

            var storage = window.localStorage;
            let AddressList = storage.AddressList;
            this.restaurants = JSON.parse(AddressList);
            tableData.forEach((item) => {
                this.restaurants.forEach((e) => {
                    if (item.zid === e.id) {
                        item.zid = e.value;
                    }
                    if (item.xid === e.id) {
                        item.xid = e.value;
                    }
                });
            });
            // this.tableData = tableData;
            return tableData;

            // if (AddressList) {
            //     this.restaurants = JSON.parse(AddressList);
            //     tableData.forEach(item => {
            //         this.restaurants.forEach(e => {
            //             if (item.zid === e.id) {
            //                 item.zid = e.value;
            //             }
            //             if (item.xid === e.id) {
            //                 item.xid = e.value;
            //             }
            //         });
            //     });
            //     return tableData;
            // } else {
            //     var bd = {
            //         tid: this.loginData.tid,
            //         vn: '20190101'
            //     };
            //     this.$axios
            //         .post(action, {
            //             hd: {
            //                 pi: 31009,
            //                 sq: 8,
            //                 si: this.loginData.si
            //             },
            //             bd: bd
            //         })
            //         .then(res => {
            //             let assetsData = JSON.parse(res.data.bd);
            //             let assetsList = assetsData.olst;
            //             assetsList.map(item => {
            //                 this.restaurants.push({ value: item.mne, address: item.ne, id: item.id });
            //             });
            //             storage.AddressList = JSON.stringify(this.restaurants);
            //             tableData.forEach(item => {
            //                 this.restaurants.forEach(e => {
            //                     if (item.zid === e.id) {
            //                         item.zid = e.value;
            //                     }
            //                     if (item.xid === e.id) {
            //                         item.xid = e.value;
            //                     }
            //                 });
            //             });
            //             return tableData;
            //         })
            //         .catch(error => {
            //             console.log(error);
            //         });
            // }
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
        startChang(item) {
            this.startId = this.state1 == '' ? '' : item.id;
        },
        endChang(item) {
            this.endId = this.state2 == '' ? '' : item.id;
        },
        reset() {
            this.state1 = '';
            this.state2 = '';
        },

        stateFormat(row) {
            if (row.state === 1) {
                return '整车';
            } else {
                return '配货';
            }
        },

        //处理返回数据 车型
        stateFormat1(row) {
            if (row.ct == 1) {
                return '平板';
            }
            if (row.ct == 2) {
                return '高栏';
            }
            if (row.ct == 3) {
                return '中栏';
            }
            if (row.ct == 4) {
                return '低栏';
            }
            if (row.state == 5) {
                return '高低板';
            }
            if (row.ct == 6) {
                return '厢式';
            }
            if (row.ct == 7) {
                return '自卸';
            }
            if (row.ct == 8) {
                return '保温';
            }
            if (row.ct == 9) {
                return '冷藏';
            }
            if (row.ct == 10) {
                return '危险品';
            }
            if (row.ct == 11) {
                return '集装箱';
            }
            if (row.ct == 12) {
                return '特种';
            }
            if (row.ct == 13) {
                return '其他';
            } else {
                return '其他';
            }
        },
        // 车长
        stateFormat2(row) {
            if (row.cl == 1) {
                return '4.2米';
            }
            if (row.cl == 2) {
                return '5.0米';
            }
            if (row.cl == 3) {
                return '6.2米';
            }
            if (row.cl == 4) {
                return '6.8米';
            }
            if (row.state == 5) {
                return '7.2米';
            }
            if (row.cl == 6) {
                return '7.7米';
            }
            if (row.cl == 7) {
                return '7.8米';
            }
            if (row.cl == 8) {
                return '8.2米';
            }
            if (row.cl == 9) {
                return '8.7米';
            }
            if (row.cl == 10) {
                return '9.6米';
            }
            if (row.cl == 11) {
                return '12.5米';
            }
            if (row.cl == 12) {
                return '13.0米';
            }
            if (row.cl == 13) {
                return '14.6米';
            }
            if (row.cl == 14) {
                return '15.0米';
            }
            if (row.cl == 15) {
                return '16.5米';
            }
            if (row.cl == 16) {
                return '17.5米';
            }
            if (row.cl == 17) {
                return '其他';
            } else {
                return '其他';
            }
        }
    }
};
</script>
<style>
/* .btn-prev:before {
    content: '上一页';
}
.btn-next:before {
    content: '下一页';
}
.el-icon-arrow-left,
.el-icon-arrow-right {
    display: none !important;
} */
</style>
<style lang="scss" scoped>
.table {
    /deep/ td {
        padding: 5px 0;
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
    // display: flex;
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
        padding-bottom: 10px;
        .title {
            margin-right: 10px;
        }
    }
    .end {
        display: flex;
        align-items: center;
        padding-bottom: 10px;
        .title {
            margin-right: 10px;
        }
    }
    .el-button {
        margin-left: 20px;
    }
}
.final_price {
    font-size: 32px;
    // text-align: center;
    line-height: 55px;
    color: #333;
    // font-weight: 550;
}
.quote {
    font-size: 32px;
    // text-align: center;
    line-height: 55px;
    color: #333;
}
.white {
    width: 100%;
    height: 60px;
}
.el-tabs {
    /deep/ .el-tabs__item {
        font-size: 15px;
        font-weight: 550;
    }
}
.page {
    margin-top: 20px;
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
</style>
