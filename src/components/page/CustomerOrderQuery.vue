<template>
    <div>
        <div class="top">
            <el-date-picker
                v-model="sTime"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions"
                :default-time="['00:00:00', '23:59:59']"
                value-format="yyyy-MM-dd"
                style="margin:0 0 0px 0px;"
            ></el-date-picker>
            <el-select v-model="vState" placeholder="订单状态" style="margin:0 0 0px 20px;">
                <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                ></el-option>
            </el-select>

            <!-- <el-button type="success" style="margin:0 0 0px 20px;" @click="getData">查 询</el-button> -->
        </div>
        <div style="margin-bottom:10px;">
            <el-input
                v-model="sValue"
                placeholder="货物名称/起运地/目的地"
                style="margin:0 0 0px 0px; width:350px"
            ></el-input>
            <el-select v-model="vSource" placeholder="订单来源" style="margin:0 0 0px 20px;">
                <el-option
                    v-for="item in sources"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                ></el-option>
            </el-select>
            <!-- <el-checkbox style="margin:0 0 0px 20px;" v-model="checked">自建订单</el-checkbox> -->
            <el-button type="success" style="margin:0 0 0px 20px;" @click="getData">查 询</el-button>
        </div>
        <div class="container">
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
                <!-- <el-table-column prop="oid" label="编号" width="100"></el-table-column> -->
                <el-table-column width="50">
                    <template scope="scope">
                        {{scope.$index + addIndex}}
                    </template>
                </el-table-column>
                <el-table-column prop="ocd" label="订单号" width="120"></el-table-column>
                <el-table-column prop="st" label="订单状态" width="120" :formatter="stateFormat"></el-table-column>
                <el-table-column prop="tte" label="订单来源" width="100" :formatter="sourceFormat"></el-table-column>
                <el-table-column prop="zct" label="起运地" width="180"></el-table-column>
                <el-table-column prop="xct" label="目的地" width="180"></el-table-column>
                <el-table-column prop="on" label="货物名称" width="100"></el-table-column>

                <!-- <el-table-column label="重量体积" width="180">
                    <template slot-scope="scope">
                        <span>{{scope.row.dw}}吨 {{scope.row.dv}}方</span>
                    </template>
                </el-table-column>-->

                <el-table-column prop="dw" label="重量（吨）" width="130"></el-table-column>
                <el-table-column prop="dv" label="体积（方）" width="130"></el-table-column>

                <el-table-column prop="dis" label="距离（公里）" width="110"></el-table-column>
                <el-table-column prop="qyf" label="期望运费（元）" width="120" align="right">
                    <template slot-scope="scope">
                        <span style="color:red;">{{scope.row.qyf}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="zdy" label="装货日期" width="150"></el-table-column>
                <el-table-column prop="ctm" label="创建时间" width="170"></el-table-column>
                <el-table-column label="操作" width="150">
                    <template slot-scope="scope">
                        <el-button type="success" size="small" @click="againList(scope.row)">再来一单</el-button>
                    </template>
                </el-table-column>
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
        <!-- 再来一单 -->
        <deliver-goods ref="dGoods"></deliver-goods>
    </div>
</template>

<script>
import request from '../../assets/js/request.js';
import encryptUtil from '../../assets/js/encryptUtil.js';
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
import deliverGoods from '../popup/deliverGoods.vue'
export default {
    data() {
        return {
            addIndex: 1,
            pickerOptions: {
                shortcuts: [
                    {
                        text: '今天',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            // start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '昨天',
                        onClick(picker) {
                            // const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1);
                            picker.$emit('pick', [start, start]);
                        }
                    },
                    {
                        text: '最近七天',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    // {
                    //     text: '上周',
                    //     onClick(picker) {
                    //         const end = new Date();
                    //         const start = new Date();
                    //         start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    //         picker.$emit('pick', [start, end]);
                    //     }
                    // },
                    {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '上月',
                        onClick(picker) {
                            let date = new Date();
                            let year = date.getFullYear();
                            let month = date.getMonth();
                            const start = new Date(year, month - 1, '01');
                            let day = new Date(year, month, 0).getDate();
                            const end = new Date(year, month - 1, day);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '本月',
                        onClick(picker) {
                            let date = new Date();
                            let year = date.getFullYear();
                            let month = date.getMonth();
                            const start = new Date(year, month, '01');
                            const end = new Date();
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }
                ]
            },
            tableData: [],
            sTime: '',
            vState: '',
            vSource: '',
            sValue: '',
            options: [
                {
                    value: '0',
                    label: '全部'
                },
                {
                    value: '1',
                    label: '待接单'
                },
                {
                    value: '2',
                    label: '待派车'
                },
                {
                    value: '3',
                    label: '已派车'
                },
                {
                    value: '4',
                    label: '已撤单'
                }
            ],
            sources: [
                {
                    value: '',
                    label: '全部'
                },
                {
                    value: '1',
                    label: '客户订单'
                },
                {
                    value: '3',
                    label: '客服订单'
                },
                {
                    value: '4',
                    label: '游客订单'
                },
                {
                    value: '5',
                    label: '经纪人代客下单'
                }
            ],
            checked: false,
            tableHeight: 600,
            currentPage: 1,
            totalNumber: 0,
            pageSize: 20,
            loginData: {},
            sk: ''
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.CustomerData;
        this.getData();
    },
    mounted() {
        this.$nextTick(() => {
            this.tableHeight = window.innerHeight - this.$refs.multipleTable.$el.offsetTop - 160;
        });
    },
    components:{
        deliverGoods
    },
    methods: {
        //客服订单
        async getData() {
            if (!this.sTime) {
                this.sTime = '';
            }

            let action = `${this.$store.state.Login.url}/31002`;
            // this.loginData = this.$store.state.Login.CustomerData;

            var bd = {
                tid: this.loginData.tid,
                bte: this.sTime[0] || '',
                ete: this.sTime[1] || '',
                st: this.vState * 1 || 0,
                ss: this.sValue,
                os: this.vSource,
                pg: this.currentPage,
                sz: this.pageSize
            };
            let hd = {
                pi: 31002,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/31002', hd, bd);
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
                this.tableData = data.olst;
                this.totalNumber = data.pg.tn;
            } else {
                this.$message({
                    message: res.data.hd.rmsg,
                    type: 'error',
                    duration: 900
                });
            }
            // console.log(this.loginData.tid, this.phone,this.loginData.si);
            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 31002,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         let data = JSON.parse(res.data.bd);
            //         this.tableData = data.olst;
            //         this.totalNumber = data.pg.tn;
            //         // console.log(res.data);
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
        },
        stateFormat(row) {
            let _val = row.st;
            this.options.forEach(ele => {
                for (const key in ele) {
                    if (key == 'value') {
                        if (ele[key] == row.st) {
                            _val = ele.label;
                            return;
                        }
                    }
                }
                return;
            });
            return _val;
        },
        sourceFormat(row) {
            if (row.tte == 1) {
                return '客户订单';
            }
            if (row.tte == 5) {
                return '经纪人订单';
            }
            if (row.tte == 3) {
                return '客服订单';
            }
            if (row.tte == 4) {
                return '游客订单';
            }
        },
        againList(row) {
            // console.log(row);
            this.$refs.dGoods.againOpen(row)
            // this.$router.push({
            //     path: 'goods'
            // });
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.addIndex = (this.currentPage -1) * this.pageSize + 1;
            this.getData();
            // console.log(`当前页: ${val}`);
        },
        headClass() {
            return 'background:#ff0000;color:#606266;text-align:center;';
        },
        cellBg({ row, column, rowIndex, columnIndex }) {
            if (columnIndex == 10 || columnIndex == 9 || columnIndex == 8 || columnIndex == 7) {
                return 'text-align:right;';
            } else if(columnIndex == 12){
                return 'text-align:left;';
            }else {
                return 'text-align:center;';
            }
        }
    }
};
</script>

<style>
.el-date-range-picker.has-sidebar {
    top: 140px !important;
    left: 220px !important;
}
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
    /deep/ td {
        padding: 2px 0;
    }
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
    padding-bottom: 10px;
    .search {
        display: flex;
        width: 300px;
        justify-content: space-between;
        align-items: center;
        margin-right: 30px;
        .el-input {
            width: 200px;
        }
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