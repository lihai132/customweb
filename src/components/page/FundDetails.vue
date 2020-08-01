<template>
    <div>
        <div class="top">
            <!-- <span>{{sTime}}</span> -->
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
            <el-input
                v-model="sValue"
                placeholder="运单号查询"
                style="margin:0 0 0px 20px; width:300px"
                clearable
                oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
            ></el-input>
            <el-button type="success" style="margin:0 0 0px 20px;" @click="getData">查 询</el-button>
            <el-button
                :loading="downloadLoading"
                style="margin:0 0 0px 20px;"
                type="primary"
                icon="el-icon-document"
                @click="handleDownload"
            >导出 Excel</el-button>
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
                <el-table-column prop="rnno" label="关联运单号" width="220"></el-table-column>
                <el-table-column prop="td" label="结算日期" width="200"></el-table-column>
                <el-table-column prop="fid" label="资金流水号" width="200"></el-table-column>
                <el-table-column prop="fte" label="流水类型" width="200" :formatter="stateFormat"></el-table-column>
                <el-table-column prop="atmy" label="变动金额（元）" width="150" align="right">
                    <template slot-scope="scope">
                        <span style="color:red;">{{scope.row.atmy}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="ctm" label="时间" width="220"></el-table-column>
                <el-table-column prop="dc" label="描述" min-width="200"></el-table-column>
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
            sValue: '',
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
            downloadLoading: false,
            tableHeight: 600,
            currentPage: 1,
            totalNumber: 0,
            pageSize: 20,
            sk: '',
            loginData: {}
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData =
            this.$store.state.Login.LogingType == 3 ? this.$store.state.Login.DispatchData : this.$store.state.Login.CustomerData;
        this.getData();
    },
    mounted() {
        this.$nextTick(() => {
            this.tableHeight = window.innerHeight - this.$refs.multipleTable.$el.offsetTop - 160;
        });
    },
    methods: {
        //获取资金明细
        async getData() {
            if (!this.sTime) {
                this.sTime = '';
            }

            let action = `${this.$store.state.Login.url}/31005`;
            // this.loginData = this.$store.state.Login.DispatchData;
            //3调度；4客服
            // this.loginData =
            //     this.$store.state.Login.LogingType == 3 ? this.$store.state.Login.DispatchData : this.$store.state.Login.CustomerData;

            var bd = {
                tid: this.loginData.tid,
                bte: this.sTime[0] || '',
                ete: this.sTime[1] || '',
                wcd: this.sValue,
                pg: this.currentPage,
                sz: this.pageSize
            };
            let hd = {
                pi: 31005,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/31005', hd, bd);
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
                    type: 'error'
                });
            }

            // console.log(this.loginData.tid, this.phone,this.loginData.si);
            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 31005,
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
        //导出
        handleDownload() {
            this.downloadLoading = true;
            import('../vendor/Export2Excel').then((excel) => {
                const tHeader = ['结算日期', '资金流水号', '流水类型', '变动金额', '关联运单号', '时间', '描述'];
                const filterVal = ['td', 'fid', 'fte', 'atmy', 'rnno', 'ctm', 'dc'];
                const list = this.tableData;
                const data = this.formatJson(filterVal, list);
                excel.export_json_to_excel({
                    header: tHeader,
                    data,
                    filename: '资金明细',
                    autoWidth: true,
                    bookType: 'xlsx'
                });
                this.downloadLoading = false;
            });
        },
        formatJson(filterVal, jsonData) {
            return jsonData.map((v) =>
                filterVal.map((j) => {
                    if (j === 'timestamp') {
                        // return parseTime(v[j])
                        return v[j];
                    } else {
                        return v[j];
                    }
                })
            );
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.addIndex = (this.currentPage - 1) * this.pageSize + 1;
            this.getData();
            // console.log(`当前页: ${val}`);
        },
        stateFormat(row) {
            switch (row.fte) {
                case 11:
                    return '服务费支付';
                case 12:
                    return '服务费返还';
                case 21:
                    return '运费支付';
                case 22:
                    return '运费返还';
                case 29:
                    return '银行服务费支付';
                case 35:
                    return '运费支出退款';
                default:
                    return row.fte;
            }
        },
        headClass() {
            return 'background:#ff0000;color:#606266;text-align:center;';
        },
        cellBg({ row, column, rowIndex, columnIndex }) {
            if (columnIndex == 5) {
                return 'text-align:right;';
            } else if(columnIndex == 7){
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
/* .el-table--small td {
    padding: 5px 0;
} */
.el-date-range-picker.has-sidebar {
    top: 140px !important;
    left: 220px !important;
}
/* .btn-prev:before {
    content: '上一页';
}
.btn-next:before {
    content: '下一页';
} */
/* .el-icon-arrow-left,
.el-icon-arrow-right {
    display: none !important;
} */
</style>

<style lang="scss" scoped>
// .el-date-range-picker.has-sidebar {
//     left: 250px !important;
// }

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