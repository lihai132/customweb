<template>
    <div>
        <div class="top">
            <div class="search">
                <div class="title">按手机号查询</div>
                <el-input v-model="phone" placeholder="请输入手机号码"></el-input>
            </div>
            <div class="but">
                <el-button type="success" @click="getData">查询</el-button>
                <el-button type="success" @click="reData">重置</el-button>
            </div>
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
                    <template scope="scope">
                        {{scope.$index + addIndex}}
                    </template>
                </el-table-column>
                <el-table-column prop="ne" label="司机名称" width="150"></el-table-column>
                <el-table-column prop="ph" label="司机电话" width="150"></el-table-column>
                <el-table-column prop="st" label="司机状态" width="130" :formatter="stateFormat"></el-table-column>
                <el-table-column prop="cn" label="车牌号" width="120"></el-table-column>
                <el-table-column prop="ct" label="车型" width="120" :formatter="stateFormat1"></el-table-column>
                <el-table-column prop="cl" label="车长" width="120" :formatter="stateFormat2"></el-table-column>
                <el-table-column prop="idc" label="司机身份证" width="250"></el-table-column>
                <el-table-column prop="vin" label="车辆识别代码" width="230" show-overflow-tooltip></el-table-column>
                <el-table-column prop="ctm" label="创建时间" min-width="200" show-overflow-tooltip></el-table-column>
                <!-- <el-table-column
                    
                    label="操作"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="handleClick(scope.row)" type="text" size="small">派车</el-button>
                        <el-button type="text" size="small" @click="handleDetails(scope.row)">详情</el-button>
                    </template>
                </el-table-column>-->
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
            phone: '',
            tableData: [],
            editVisible: false,
            disabled: true,
            form: {
                name: '',
                phone: '',
                userID: '',
                numberPlate: '',
                Identifier: '',
                car_type: '',
                car_length: '',
                jpc: '',
                xpc: '',
                src_jpc: '',
                src_xpc: ''
            },
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
        //获取平台司机数据
        async getData() {
            let action = `${this.$store.state.Login.url}/30073`;
            //3调度；4客服
            // this.loginData =
            //     this.$store.state.Login.LogingType == 3 ? this.$store.state.Login.DispatchData : this.$store.state.Login.CustomerData;
            var bd = {
                tid: this.loginData.tid,
                im: 0,
                ss: this.phone,
                pg: this.currentPage,
                sz: this.pageSize
            };
            let hd = {
                pi: 30073,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30073', hd, bd);
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
            //             pi: 30073,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         let data = JSON.parse(res.data.bd);
            //         this.totalNumber = data.pg.tn;
            //         this.tableData = data.olst;
            //         // console.log(this.tableData);
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
        },
        reData() {
            this.phone = '';
        },
        // 详情
        handleDetails(row) {
            // console.log(row);
            this.disabled = true;
            // this.newAddBtn = false;
            this.editVisible = true;
            this.form.name = row.dne;
            this.form.phone = row.dph;
            this.form.userID = row.didc;
            this.form.numberPlate = row.cn;
            this.form.Identifier = row.cidc;
            this.car_types.forEach(element => {
                for (const key in element) {
                    if (key === 'value') {
                        if (element[key] == row.ct) {
                            this.form.car_type = element.label;
                        }
                    }
                }
            });
            this.car_long.forEach(element => {
                for (const key in element) {
                    if (key === 'value') {
                        if (element[key] == row.cl) {
                            this.form.car_length = element.label;
                        }
                    }
                }
            });
            var loginData = this.$store.state.Login.DispatchData;
            this.form.src_jpc = `${loginData.murl}${row.jpc}`;
            this.form.src_xpc = `${loginData.murl}${row.xpc}`;
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
        },
        stateFormat(row) {
            if (row.st == 1) {
                return '已开户';
            }
            if (row.st == 2) {
                return '待签约';
            }
            if (row.st == 3) {
                return '正常';
            }
            if (row.st == 4) {
                return '冻结';
            }
            if (row.st == 5) {
                return '已注销';
            }
            if (row.st == 6) {
                return '已登记';
            }
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.addIndex = (this.currentPage - 1) * this.pageSize + 1
            this.getData();
            // console.log(`当前页: ${val}`);
        },
        headClass() {
            return 'background:#ff0000;color:#606266;text-align:center;';
        },
        cellBg({ row, column, rowIndex, columnIndex }) {
            // if (columnIndex == 11 || columnIndex == 10 || columnIndex == 9 || columnIndex == 8 || columnIndex == 7) {
            //     return 'text-align:right;';
            // } else {
            //     return 'text-align:center;';
            // }
            if(columnIndex == 9){
                return 'text-align:left;'
            }else{
                return 'text-align:center;';
            }
        }
    }
};
</script>

<style>
/* .el-table--small td {
    padding: 5px 0;
} */
</style>

<style lang="scss" scoped>
.container {
    .table {
        /deep/ td {
            padding: 5px 0;
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