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
            <el-select v-model="value" placeholder="运单状态" style="margin:0 0 0px 20px;">
                <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                ></el-option>
            </el-select>
            <!-- <el-button type="success" style="margin:0 0 0px 20px;" @click="getData">查 询</el-button> -->

            <el-input
                v-model="sValue"
                placeholder="货物名称/起运地/目的地"
                style="margin:0 0 0px 20px; width:300px"
            ></el-input>
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
                <el-table-column width="50">
                    <template scope="scope">{{scope.$index + addIndex}}</template>
                </el-table-column>
                <el-table-column prop="wcd" label="运单号" width="130"></el-table-column>
                <el-table-column prop="st" label="运单状态" width="120" :formatter="stateFormat"></el-table-column>
                <el-table-column prop="zct" label="起运地" width="180" show-overflow-tooltip></el-table-column>
                <el-table-column prop="xct" label="目的地" width="180" show-overflow-tooltip></el-table-column>
                <el-table-column prop="on" label="货物名称" width="150" show-overflow-tooltip></el-table-column>
                <!-- <el-table-column label="重量体积" width="120">
                    <template slot-scope="scope">
                        <span>{{scope.row.dw}}吨 {{scope.row.dv}}方</span>
                    </template>
                </el-table-column>-->
                <el-table-column prop="dw" label="重量（吨）" width="130"></el-table-column>
                <el-table-column prop="dv" label="体积（方）" width="130"></el-table-column>
                <el-table-column prop="dis" label="距离（公里）" width="120"></el-table-column>
                <!-- <el-table-column prop="dch" label="总服务费（元）" width="120" align="right">
                    <template slot-scope="scope">
                        <span style="color:red;">{{scope.row.uch}}</span>
                    </template>
                </el-table-column>-->
                <el-table-column prop="dch" label="调度服务费（元）" width="150" align="right">
                    <template slot-scope="scope">
                        <span style="color:red;">{{scope.row.dch}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="yf" label="总运费（元）" width="120" align="right">
                    <template slot-scope="scope">
                        <span style="color:red;">{{scope.row.yf}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="司机运费（元）" width="120" align="right">
                    <template slot-scope="scope">
                        <span style="color:red;">{{scope.row.dyf}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="待付司机运费（元）" width="150" align="right">
                    <template slot-scope="scope">
                        <span style="color:red;">{{scope.row.dyf - scope.row.pdyf - scope.row.poil}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="zte" label="装货时间" width="150">
                    <template slot-scope="scope">
                        <span>{{(scope.row.zte).substring(0,10)}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="xte" label="卸货时间" width="150">
                    <template slot-scope="scope">
                        <span>{{(scope.row.xte).substring(0,10)}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="ctm" label="创建时间" width="180" show-overflow-tooltip></el-table-column>
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
            value: '',
            sValue: '',
            options: [
                {
                    value: '0',
                    label: '全部'
                },
                {
                    value: '2',
                    label: '待签约'
                },
                {
                    value: '4',
                    label: '待收货'
                },
                {
                    value: '6',
                    label: '待关闭'
                },
                {
                    value: '7',
                    label: '已完成'
                },
                {
                    value: '8',
                    label: '已撤单'
                },
                {
                    value: '9',
                    label: '撤单待审核'
                }
            ],
            tableHeight: 600,
            currentPage: 1,
            totalNumber: 0,
            pageSize: 20
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.DispatchData;
        this.getData();
    },
    mounted() {
        this.$nextTick(() => {
            this.tableHeight = window.innerHeight - this.$refs.multipleTable.$el.offsetTop - 160;
        });
    },
    methods: {
        //调度运单
        async getData() {
            if (!this.sTime) {
                this.sTime = '';
            }

            let action = `${this.$store.state.Login.url}/31003`;
            // this.loginData = this.$store.state.Login.DispatchData;

            var bd = {
                tid: this.loginData.tid,
                bte: this.sTime[0] || '',
                ete: this.sTime[1] || '',
                st: this.value * 1 || 0,
                ss: this.sValue,
                pg: this.currentPage,
                sz: this.pageSize
            };
            let hd = {
                pi: 31003,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/31003', hd, bd);
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
                    type: 'error'
                });
            }


            // console.log(this.loginData.tid, this.phone,this.loginData.si);
            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 31003,
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
            if (row.st == 10) {
                return '已违约';
            }
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
        handleCurrentChange(val) {
            this.currentPage = val;
            this.addIndex = (this.currentPage - 1) * this.pageSize + 1;
            this.getData();
            // console.log(`当前页: ${val}`);
        },
        headClass() {
            return 'background:#ff0000;color:#606266;text-align:center;';
        },
        cellBg({ row, column, rowIndex, columnIndex }) {
            if (columnIndex == 12 || columnIndex == 11 || columnIndex == 10 || columnIndex == 9 || columnIndex == 8 || columnIndex == 7 || columnIndex == 6) {
                return 'text-align:right;';
            } else if(columnIndex == 15){
                return 'text-align:left;'
            }else {
                return 'text-align:center;';
            }
            // return 'text-align:center;';
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
        padding: 5px 0;
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