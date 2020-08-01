<template>
    <div class="SelfSourcing">
        <div class="top">
            <div class="start">
                <div class="block">
                    <el-col :span="20">
                        <el-autocomplete
                            class="inline-input"
                            v-model="search"
                            :fetch-suggestions="querySearch"
                            placeholder="请输入地址"
                            :trigger-on-focus="false"
                            @select="startChang"
                        ></el-autocomplete>
                    </el-col>
                </div>
            </div>

            <div class="but">
                <el-button type="success" @click="condition" :disabled="searchBut">查询</el-button>
                <el-button type="success" @click="reset">重置</el-button>
                <!-- <el-button type="success" @click="myRegion" :disabled="regionBut">我的区域</el-button> -->
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
                <el-table-column
                    prop="oid"
                    label="订单号"
                    width="80"
                    class="el-table__fixed"
                    align="center"
                    :show-overflow-tooltip="true"
                ></el-table-column>
                <el-table-column
                    prop="zid"
                    label="起运地"
                    width="240"
                    class="el-table__fixed"
                    align="center"
                    :show-overflow-tooltip="true"
                ></el-table-column>
                <el-table-column
                    prop="xid"
                    label="目的地"
                    width="240"
                    class="el-table__fixed"
                    align="center"
                    :show-overflow-tooltip="true"
                ></el-table-column>
                <el-table-column
                    prop="on"
                    label="货物名称"
                    width="150"
                    class="el-table__fixed"
                    align="center"
                    :show-overflow-tooltip="true"
                ></el-table-column>

                <el-table-column prop="dis" label="距离(km)" width="130" align="center"></el-table-column>
                <el-table-column prop="pdw" label="单车重量(吨)" width="130" align="right"></el-table-column>
                <el-table-column prop="pdv" label="单车体积(方)" width="130" align="right"></el-table-column>
                <el-table-column prop="pzdy" label="装货时间" width="160" align="center"></el-table-column>
                <el-table-column
                    prop="zxte"
                    label="运输类型"
                    min-width="130"
                    :formatter="stateFormat3"
                    align="left"
                ></el-table-column>
                <!-- <el-table-column prop="te" label="车辆要求" width="100" :formatter="stateFormat1"></el-table-column>
                <el-table-column prop="cl" label="车长要求" width="100" :formatter="stateFormat2"></el-table-column>
                <el-table-column prop="utm" label="创建时间" width="200"></el-table-column>-->
                <el-table-column label="操作" width="160" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="success"
                            size="mini"
                            @click="meet(scope.$index, scope.row)"
                        >接单</el-button>
                        <!-- <el-button
                            type="success"
                            size="mini"
                            @click="follow(scope.$index, scope.row)"
                        >关注</el-button>-->
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
        <meet ref="meet" @getData="getData"></meet>
    </div>
</template>

<script>
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
import meet from '../popup/meet.vue';
export default {
    data() {
        return {
            query: {
                address: '',
                name: '',
                pageSize: 15
            },
            searchBut: false,
            regionBut: false,
            show: false,
            form: { name: '', address: '' },
            editVisible: false,
            tableData: [],
            options: [],
            loginData: {},
            sk: '',
            checked: false,
            pageIndex: 1,
            pageSize: 18,
            restaurants: [],
            search: '',
            page: '',
            currentPage: 1,
            totalNumber: 0,
            id: 123456789,
            oid: '',
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

            input1: '',
            input2: '',
            popup: {}
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.DispatchData;
        this.getData();
    },
    methods: {
        //获取货源数据
        async getData() {
            let action = this.$store.state.Login.url + '/30015';

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
                pi: 30015,
                si: this.loginData.si,
                sq: 9
            };

            let res = await request('/30015', hd, bd);
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

                this.totalNumber = data.pg.tn;
                let tableData = data.olst;
                // console.log(data);
                if (tableData.length == 0) {
                    this.tableData = [];
                } else {
                    this.startProvinceChange(tableData);
                }
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //处理货源数据
        startProvinceChange(tableData) {
            this.loginData = this.$store.state.Login.DispatchData;

            var storage = window.localStorage;
            let AddressList = JSON.parse(storage.AddressList);
            this.restaurants = AddressList;

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

        //查询
        condition() {
            this.getData();

            this.searchBut = true;
            setTimeout(() => {
                this.searchBut = false;
            }, 1000);
        },

        //我的区域
        myRegion() {
            this.regionBut = true;
            setTimeout(() => {
                this.regionBut = false;
            }, 1000);
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
        //调度货源详情
        async receipt(index, row) {
            this.oid = row.oid;
            this.idx = index;
            this.form = row;
            this.editVisible = true;
            // console.log(row)
            let action = this.$store.state.Login.url + '/30016';
            this.loginData = this.$store.state.Login.DispatchData;

            var bd = {
                tid: this.loginData.tid,
                oid: row.oid
            };
            let hd = {
                pi: 30016,
                si: this.loginData.si,
                sq: 9
            };

            let res = await request('/30016', hd, bd);

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
                this.popup = dataList;
                this.id = dataList.ocd;
                this.time = dataList.ctm;
                this.start = dataList.zad;
                this.end = dataList.xad;
                this.distance = dataList.dis;
                this.ct = dataList.ct;
                this.cl = dataList.cl;
                this.dw = dataList.dw;
                this.dv = dataList.dv;
                this.num = this.on = dataList.on;
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

                this.popup2 = {
                    tid: this.loginData.tid,
                    oid: row.oid,
                    data: this.popup
                };
                this.$store.commit('setPopup2', this.popup2);
                this.$refs.meet.receipt();
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //调度货源关注
        follow(index, row) {
            this.oid = row.oid;
            this.idx = index;
            this.form = row;

            let action = this.$store.state.Login.url + '/20042';
            this.loginData = this.$store.state.Login.DispatchData;

            var bd = {
                tid: this.loginData.tid,
                oid: row.oid,
                opt: 1,
                dc: ''
            };
            // console.log(row);
            this.$axios
                .post(action, {
                    hd: {
                        pi: 20042,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then((res) => {
                    // let dataList = JSON.parse(res.data.bd);
                    console.log(res);
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        handleClick(tab, event) {
            console.log(tab, event);
        },
        //调度货源接单
        meet(index, row) {
            this.receipt(index, row);
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
                duration: 1500
            });
        },
        handleChange(value) {
            console.log(value);
        }
    },
    mounted() {
        this.restaurants = this.loadAll();
    },
    components: {
        meet
    }
};
</script>
<style >
.el-table__body tr.current-row > td {
    background-color: #a4ccec;
}
.el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #a4ccec;
}
.el-icon-close:hover {
    background: #eee;
    color: #fff;
}
.right-menu a:hover {
    background: #eee;
    color: #fff;
}
.el-dialog__headerbtn {
    top: 16px;
}
</style>
<style lang="scss">
// .el-table--small td {
//     padding: 2px 0;
// }
.el-dialog__header {
    padding: 10px 10px;
    background: #409eff;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .el-dialog__title {
        color: #fff;
    }
}
.el-dialog__body {
    // padding: 0 0px 20px 0px;
    margin: auto;
}
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
.el-dialog__body {
    padding: 10px 20px;
    color: #606266;
    font-size: 14px;
    word-break: break-all;
}
.el-dialog {
    margin-top: 10vh !important;
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
        overflow: hidden;
        .table {
            height: 100%;
            /deep/ .el-table__body-wrapper {
                height: 91.6% !important;
                overflow-y: scroll;
                // overflow-x: auto;
            }
            // /deep/ .el-table__body-wrapper::-webkit-scrollbar {
            //     display: none;
            // }
            // /deep/ .el-table__body-wrapper {
            //     overflow-x: none;
            // }
        }
    }
    .page {
        margin-top: 10px;
        padding-bottom: 10px;
        height: 42px;
        box-sizing: border-box;
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
</style>
