<template>
    <div>
        <div class="top">
            <div class="search">
                <div class="title">搜索</div>
                <el-input v-model="phone" placeholder="请输入手机号码"></el-input>
            </div>
            <div class="but">
                <el-button type="success" @click="reGetData">查询</el-button>
                <el-button type="success" @click="reData">重置</el-button>
                <el-button type="primary" @click="open">新增司机</el-button>
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
                @cell-click="getOpen"
                :header-cell-style="headClass"
                :cell-style="cellBg"
            >
                <!-- <el-table-column prop="did" label="司机编号" width="150"></el-table-column> -->
                <el-table-column width="50">
                    <template scope="scope">{{scope.$index + addIndex}}</template>
                </el-table-column>
                <el-table-column label="名称" width="200">
                    <template slot-scope="scope">
                        <span>{{scope.row.ne}}</span>
                        <!-- <el-link>{{scope.row.ne}}</el-link> -->
                    </template>
                    <!-- <a href="javascript:;">司机名称</a> -->
                </el-table-column>
                <el-table-column prop="ph" label="电话" width="220"></el-table-column>
                <el-table-column prop="cn" label="车牌号" width="200"></el-table-column>
                <!-- <el-table-column prop="ct" label="车型" width="120"></el-table-column>
                <el-table-column prop="cl" label="车长" width="120"></el-table-column>
                <el-table-column prop="didc" label="司机身份证" width="250"></el-table-column>
                <el-table-column prop="cidc" label="车辆识别代码"></el-table-column>-->
                <el-table-column prop="st" label="司机状态" width="200" :formatter="stateFormat"></el-table-column>
                <el-table-column prop="ctm" label="创建时间" min-width="160" show-overflow-tooltip></el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <!-- <el-button @click="handleClick(scope.row)" type="text" size="small">派车</el-button> -->
                        <!-- <el-button @click="handleDetails(scope.row)" type="text" size="small">新增</el-button> -->
                        <el-button @click="handleChange(scope.row)" type="success" size="small">修改</el-button>
                        <el-button @click="handleDetails(scope.row)" type="success" size="small">详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="page">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="18"
                layout="total,  prev, pager, next, jumper"
                :total="totalNumber"
            ></el-pagination>
        </div>
        <!-- <div class="popup"></div> -->

        <!-- 新增司机 -->
        <add-driver ref="a_driver" @getData="getData"></add-driver>

        <!-- 司机详情 -->
        <driver-details ref="d_driver"></driver-details>

        <el-dialog :title="newTitle" visible.sync width="33%">
            <el-form ref="form" :model="form" label-width="85px" size="mini" inline>
                <!-- <div class="driver_info">- - - - - 司机信息 - - - - -</div> -->
                <el-form-item label="姓名">
                    <el-input v-model="form.ne" :disabled="disabled" style="width: 100%;"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input
                        v-model="form.phone"
                        maxlength="11"
                        :disabled="disabled"
                        style="width: 100%;"
                    ></el-input>
                </el-form-item>
                <!-- <el-form-item label="身份证号" style="display:block;">
                    <el-input v-model="form.userID" :disabled="disabled" style="width:37.5em"></el-input>
                </el-form-item>-->
                <!-- <div class="vehicle_info">- - - - - 车辆信息 - - - - -</div> -->
                <el-form-item label="车牌号">
                    <el-input v-model="form.numberPlate" :disabled="disabled" style="width: 100%;"></el-input>
                </el-form-item>
                <span>(例：粤B88888)</span>
                <!-- <el-form-item label="车型">
                    <el-select
                        v-model="form.car_type"
                        :disabled="disabled"
                        placeholder="请选择"
                        style="width: 12.75em"
                    >
                        <el-option
                            v-for="item in car_types"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车长">
                    <el-select
                        v-model="form.car_length"
                        :disabled="disabled"
                        placeholder="请选择"
                        style="width: 12.75em;"
                    >
                        <el-option
                            v-for="item in car_long"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车辆识别码" style="display:block;">
                    <el-input v-model="form.Identifier" :disabled="disabled" style="width:37.5em"></el-input>
                </el-form-item>
                <el-form-item label="车辆描述" style="display:block;">
                    <el-input
                        type="textarea"
                        v-model="form.desc"
                        :disabled="disabled"
                        style="width:37.5em"
                    ></el-input>
                </el-form-item>-->

                <el-tabs type="border-card" style="width:38em;margin-left:8px;">
                    <el-tab-pane label="驾驶证">
                        <el-upload
                            v-if="newAddBtn"
                            action
                            ref="driveFile"
                            :auto-upload="false"
                            list-type="picture-card"
                            :on-preview="handlePictureCardPreview"
                            :on-change="getDrive"
                        >
                            <i class="el-icon-plus"></i>
                        </el-upload>
                        <el-image v-if="disabled" :src="form.src_jpc"></el-image>
                        <!-- <el-dialog :visible.sync="dialogVisible">
                            <img width="100%" :src="dialogImageUrl" alt="">
                        </el-dialog>-->
                    </el-tab-pane>
                    <el-tab-pane label="行驶证">
                        <el-upload
                            v-if="newAddBtn"
                            action
                            ref="vehicleFile"
                            :auto-upload="false"
                            list-type="picture-card"
                            :on-preview="handlePictureCardPreview"
                            :on-change="getVehicle"
                        >
                            <i class="el-icon-plus"></i>
                        </el-upload>

                        <el-image v-if="disabled" :src="form.src_xpc"></el-image>

                        <!-- <el-dialog :visible.sync="dialogVisible">
                            <img width="100%" :src="dialogImageUrl" alt="">
                        </el-dialog>-->
                    </el-tab-pane>
                    <el-tab-pane label="身份证">
                        <el-upload
                            v-if="newAddBtn"
                            action
                            :auto-upload="false"
                            list-type="picture-card"
                            :on-preview="handlePictureCardPreview"
                            :on-change="getDrive"
                        >
                            <i class="el-icon-plus"></i>
                        </el-upload>
                        <!-- <el-dialog :visible.sync="dialogVisible">
                            <img width="100%" :src="dialogImageUrl" alt="">
                        </el-dialog>-->
                    </el-tab-pane>
                    <el-tab-pane label="车辆照片">
                        <el-upload
                            v-if="newAddBtn"
                            action
                            :auto-upload="false"
                            list-type="picture-card"
                            :on-preview="handlePictureCardPreview"
                            :on-change="getDrive"
                        >
                            <i class="el-icon-plus"></i>
                        </el-upload>
                        <!-- <el-dialog :visible.sync="dialogVisible">
                            <img width="100%" :src="dialogImageUrl" alt="">
                        </el-dialog>-->
                    </el-tab-pane>
                </el-tabs>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">{{newAdd}}</el-button>
                <el-button type="primary" @click="saveEdit" v-if="newAddBtn">新 增</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import request from '../../assets/js/request.js';
import encryptUtil from '../../assets/js/encryptUtil.js';
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
import addDriver from '../popup/addDriverNew.vue';
import driverDetails from '../popup/driverDetails.vue';
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
            tableHeight: 600,
            tableData: [],
            editVisible: false,
            form: {
                ne: '',
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
                    label: '低栏'
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
            currentPage: 1,
            totalNumber: 0,
            pageSize: 18,
            sk: '',
            loginData: {},
            isSelect: true,
            timer: null
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.DispatchData;
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

        // 查询
        reGetData() {
            if (this.isSelect) {
                this.phone = this.phone.trim();
                if (this.phone.length < 2 && this.phone.length != 0) {
                    // this.$message({
                    //     type: 'warning',
                    //     message: '搜索长度不能小于2位字符',
                    //     duration: 900
                    // })
                } else {
                    this.getData();
                }
                // this.getData();
            }
            this.isSelect = false;
            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                this.isSelect = true;
            }, 1000);
        },
        //获取我的司机数据
        async getData() {
            let action = `${this.$store.state.Login.url}/30073`;

            var bd = {
                tid: this.loginData.tid,
                ss: this.phone,
                im: 1,
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
            //         this.tableData = data.olst;
            //         this.totalNumber = data.pg.tn;
            //         // console.log(res.data);
            //         // console.log(res.data.bd);
            //         // console.log(JSON.stringify(res.data.bd,undefined,2));
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
        },
        reData() {
            this.phone = '';
        },
        //新增司机弹窗
        open() {
            this.$refs.a_driver.a_open();
        },
        // handleDetails(row) {
        //     // console.log(row);
        //     this.disabled = true;
        //     this.newTitle = '司机详情';
        //     this.newAdd = '确 定';
        //     this.newAddBtn = false;
        //     this.editVisible = true;
        //     this.form.name = row.dne;
        //     this.form.phone = row.dph;
        //     this.form.userID = row.didc;
        //     this.form.numberPlate = row.cn;
        //     this.form.Identifier = row.cidc;
        //     this.car_types.forEach(element => {
        //         for (const key in element) {
        //             if (key === 'value') {
        //                 if (element[key] == row.ct) {
        //                     this.form.car_type = element.label;
        //                 }
        //             }
        //         }
        //     });
        //     this.car_long.forEach(element => {
        //         for (const key in element) {
        //             if (key === 'value') {
        //                 if (element[key] == row.cl) {
        //                     this.form.car_length = element.label;
        //                 }
        //             }
        //         }
        //     });
        //     var loginData = this.$store.state.Login.DispatchData;
        //     this.form.src_jpc = `${loginData.murl}${row.jpc}`;
        //     this.form.src_xpc = `${loginData.murl}${row.xpc}`;

        //     // this.form.name = row.dne;
        //     // this.form.name = row.dne;
        // },
        handleDetails(row) {
            this.$refs.d_driver.d_open(row);
            this.$refs.d_driver.getData();
        },
        // 新增司机
        saveEdit() {
            this.editVisible = false;
            // console.log(this.form);

            let action = `${this.$store.state.Login.url}/40001`;
            this.loginData = this.$store.state.Login.DispatchData;

            var bd = {
                tid: this.loginData.tid,
                dne: this.form.name,
                dph: this.form.phone,
                didc: this.form.userID,
                cn: this.form.numberPlate,
                ct: this.form.car_type,
                cl: this.form.car_length,
                cb: '',
                cct: '',
                cidc: this.form.Identifier,
                eecd: '',
                dfpc: '',
                dbpc: '',
                xpc: this.form.xpc, //行驶证
                jpc: this.form.jpc, //驾驶证
                cpc: '',
                dc: ''
            };
            this.$axios
                .post(action, {
                    hd: {
                        pi: 40001,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then((res) => {
                    let data = res.data.hd;
                    if (data.rid >= 0) {
                        this.$message({
                            type: 'success',
                            message: '新增成功'
                        });
                        this.getData();
                        for (var item in this.form) {
                            this.form[item] = '';
                        }
                        this.$refs.driveFile.clearFiles();
                        this.$refs.vehicleFile.clearFiles();
                    } else {
                        this.$message({
                            type: 'info',
                            message: '新增失败'
                        });
                    }
                    // console.log(res.data.hd.rid);
                })
                .catch((error) => {
                    this.$message({
                        type: 'info',
                        message: `新增失败!!${error}`
                    });
                });
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
        getDrive(file, fileList) {
            this.getBase64(file.raw).then((res) => {
                // this.form.jpc = res;
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/driving_license?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then((res) => {
                    this.form.name = res.data.words_result.姓名.words;
                    this.form.userID = res.data.words_result.证号.words;
                });

                let action = `${this.$store.state.Login.url}/60001`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 6,
                    img: imgBase64,
                    pn: ''
                };
                // console.log(this.loginData.tid, this.phone,this.loginData.si);
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60001,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then((res) => {
                        let data = JSON.parse(res.data.bd);
                        this.form.jpc = data.url;
                        // console.log(data.url);
                        // this.drive_fileList = fileList;
                        // console.log(fileList);
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            });
        },
        getVehicle(file, fileList) {
            this.getBase64(file.raw).then((res) => {
                this.form.xpc = res;
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/vehicle_license?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then((res) => {
                    this.form.numberPlate = res.data.words_result.号牌号码.words;
                    this.form.Identifier = res.data.words_result.车辆识别代号.words;
                    // console.log(res.data.words_result);
                    // // console.log(res.data.words_result.证号.words);
                });

                let action = `${this.$store.state.Login.url}/60001`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 7,
                    img: imgBase64,
                    pn: ''
                };
                // console.log(this.loginData.tid, this.phone,this.loginData.si);
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60001,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then((res) => {
                        let data = JSON.parse(res.data.bd);
                        this.form.xpc = data.url;
                        // console.log(data.url);
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            });
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
        getOpen(row, cell) {
            // if (cell.label == '名称') {
            //     // this.handleDetails(row);
            //     // console.log(row);
            //     this.$refs.d_driver.d_open(row);
            //     this.$refs.d_driver.getData();
            // } else {
            //     return;
            // }
            // console.log(row,cell.label);
        },
        //修改
        handleChange(row) {
            this.$refs.a_driver.d_change(row);
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
        addDriver,
        driverDetails
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
