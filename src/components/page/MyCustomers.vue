<template>
    <div>
        <div class="top">
            <div class="search">
                <div class="title">搜索</div>
                <el-input v-model="phone" placeholder="请输入手机号码或者姓名"></el-input>
            </div>
            <div class="but">
                <el-button type="success" @click="getData">查询</el-button>
                <el-button type="success" @click="reData">重置</el-button>
                <el-button type="primary" @click="open">新增客户</el-button>
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
                @cell-click="getOpen"
            >
                <el-table-column width="50">
                    <template scope="scope">{{scope.$index + addIndex}}</template>
                </el-table-column>
                <el-table-column prop="ne" label="名称" width="200" show-overflow-tooltip></el-table-column>
                <el-table-column prop="ph" label="电话" width="220"></el-table-column>
                <el-table-column prop="com" label="企业名称" width="220" show-overflow-tooltip></el-table-column>
                <el-table-column prop="st" label="客户状态" width="200" :formatter="stateFormat"></el-table-column>
                <el-table-column prop="ctm" label="创建时间" min-width="180"></el-table-column>
                <el-table-column label="操作" width="130">
                    <template slot-scope="scope">
                        <!-- <el-button @click="handleClick(scope.row)" type="text" size="small">派车</el-button> -->
                        <!-- <el-button @click="handleDetails(scope.row)" type="success" size="small">详情</el-button> -->
                        <!-- <el-button @click="handleDetails(scope.row)" type="text" size="small">新增</el-button> -->
                        <el-button @click="handleChange(scope.row)" type="success" size="small">修改</el-button>
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
        <!-- <div class="popup"></div> -->

        <!-- 新增客户 -->
        <add-custom ref="a_custom" @getData="getData"></add-custom>

        <!-- 客户详情 -->
        <customer-details ref="c_details"></customer-details>
    </div>
</template>

<script>
import request from '../../assets/js/request.js';
import encryptUtil from '../../assets/js/encryptUtil.js';
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
import customerDetails from '../popup/customerDetails.vue';
import addCustom from '../popup/addCustom.vue';
export default {
    data() {
        return {
            addIndex: 1,
            disabled: false,
            newTitle: '新增司机',
            newAdd: '取 消',
            newAddBtn: true,
            dialogImageUrl: '',
            dialogVisible: false,
            phone: '',
            tableData: [],
            editVisible: false,
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
            _token: '',
            isCell: false,
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
        this.getToken();
    },
    mounted() {
        this.$nextTick(() => {
            this.tableHeight = window.innerHeight - this.$refs.multipleTable.$el.offsetTop - 160;
        });
    },
    methods: {
        // 获取Access Token
        getToken() {
            var API_Key = 'xfL8VlHVjY1ubdZU8tpyhzoN';
            var Secret_Key = '4u8FDsdEUE2PPazEOSvyrfLZvmapPpky';
            var url = 'https://aip.baidubce.com/oauth/2.0/token';
            this.$axios
                .get(`/baidu_token/oauth/2.0/token?grant_type=client_credentials&client_id=${API_Key}&client_secret=${Secret_Key}`)
                .then((res) => {
                    this._token = res.data.access_token;
                });
        },
        //获取我的客户数据
        async getData() {
            let action = `${this.$store.state.Login.url}/30079`;
            // this.loginData = this.$store.state.Login.CustomerData;

            var bd = {
                tid: this.loginData.tid,
                ss: this.phone,
                im: 1,
                pg: this.currentPage,
                sz: this.pageSize
            };
            let hd = {
                pi: 30079,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30079', hd, bd);
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
            //             pi: 30079,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         let rid = res.data.hd.rid;
            //         if (rid >= 0) {
            //             let data = JSON.parse(res.data.bd);
            //             this.tableData = data.olst;
            //             this.totalNumber = data.pg.tn;
            //             // console.log(res.data.bd);
            //         } else {
            //             this.open2(res.data.hd.rmsg);
            //         }
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
        },
        reData() {
            this.phone = '';
        },
        open() {
            this.$refs.a_custom.a_open();
        },
        open1(text) {
            this.$message({
                message: text,
                type: 'success',
                duration: 900
            });
        },
        open2(text) {
            // this.$message.error('提交失败：' + text);
            this.$message({
                message: text,
                type: 'error    ',
                duration: 900
            });
        },
        //修改
        handleChange(row) {
            this.$refs.a_custom.changeInfo(row);
        },

        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        getBase64(file) {
            return new Promise(function (resolve, reject) {
                let reader = new FileReader();
                let imgResult = '';
                reader.readAsDataURL(file);
                reader.onload = function () {
                    imgResult = reader.result;
                };
                reader.onerror = function (error) {
                    reject(error);
                };
                reader.onloadend = function () {
                    resolve(imgResult);
                };
            });
        },
        stateFormat(row) {
            if (row.st == 3) {
                return '正常';
            } else {
                return '已登记';
            }
        },
        getOpen(row, cell) {
            // if (cell.label == '名称') {
            //     this.$refs.c_details.d_open(row);
            //     this.$refs.c_details.getData();
            // } else {
            //     return;
            // }
            // console.log(row,cell.label);
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
            // if (columnIndex == 11 || columnIndex == 10 || columnIndex == 9 || columnIndex == 8 || columnIndex == 7) {
            //     return 'text-align:right;';
            // } else {
            //     return 'text-align:center;';
            // }
            if(columnIndex == 5){
                return 'text-align:left;'
            }else{
                return 'text-align:center;';
            }
        }
    },
    components: {
        customerDetails,
        addCustom
    }
};
</script>

<style>
.cellClass {
    cursor: pointer;
    /* .el-table-column--selection {
      background-color: #ebf7ff;
    } */
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
        width: 250px;
        justify-content: space-between;
        align-items: center;
        margin-right: 30px;
        .el-input {
            width: 200px;
        }
    }
}

.driver_info {
    text-align: center;
    margin-top: -34px;
    margin-bottom: 15px;
    color: #ddd;
}
.vehicle_info {
    text-align: center;
    // margin-top: -34px;
    margin-bottom: 15px;
    color: #ddd;
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
