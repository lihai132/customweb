<template>
    <div class="addDriver">
        <el-dialog title="司机详情" :visible.sync="dialogFormVisible" width="628px" class="a_dri" :close-on-click-modal="false">
            <!-- <div class="title _first">
                <div class="left">- 基本信息</div>
            </div>-->
            <div class="d_info">
                <div class="d_car">
                    <div class="car_img"></div>
                </div>
                <div class="_info">
                    <p class="cph">{{driverInfo.cn}}</p>
                    <p class="ne_ph">
                        <span class="ne">{{driverInfo.ne}}</span>
                        <span class="ph">{{driverInfo.ph}}</span>
                    </p>
                </div>
            </div>
            <div class="carImg">
                <el-image :src="src" style="width:265px;height:160px"></el-image>
                <el-image :src="src" style="width:265px;height:160px"></el-image>
            </div>

            <div class="title _first">
                <div class="left">- 发货记录</div>
            </div>

            <div class="waybill_record">
                <div class="cplx">
                    <p class="r_title">
                        <span class="_txt">常发城市：</span>
                        <b>{{runOften}}</b>
                    </p>
                </div>
                <el-table :data="tableData" border style="width: 100%" class="dd_table" height="200">
                    <el-table-column prop="wcd" label="运单号" width="100"></el-table-column>
                    <el-table-column prop="zct" label="起运地" width="100"></el-table-column>
                    <el-table-column prop="xct" label="目的地" width="100"></el-table-column>
                    <el-table-column label="货物信息" width="120">
                        <template slot-scope="scope">
                            <span>{{scope.row.on}}  {{scope.row.dw}} {{scope.row.dv}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="zdy" label="发货日期"></el-table-column>
                </el-table>
            </div>

            <!-- <el-form :model="form" label-width="80px" size="mini" inline>
                <div class="title">
                    <div class="left">- 基本信息</div>
                </div>

                <el-form-item
                    label="司机电话"
                    :rules="{
                        required: true, 
                        message: '电话不能为空', 
                        trigger: 'blur'
                    }"
                >
                    <el-input v-model="form.phone" maxlength="11" style="width: 100%;"></el-input>
                </el-form-item>

                <el-form-item label>
                    <span>(例：粤B88888)</span>
                </el-form-item>

                <div class="title _first">
                    <div class="left">- 驾驶证</div>
                </div>
                <el-upload
                    :class="{hide:uploadDisabled}"
                    action
                    ref="driveFile"
                    :auto-upload="false"
                    list-type="picture-card"
                    :on-change="getDrive"
                    :on-remove="d_remove"
                    style="display: inline-block;width:148px; height:148px;margin:0 20px"
                >
                    <i class="el-icon-plus"></i>
                </el-upload>
                <el-form-item label="司机姓名">
                    <el-input v-model="form.name" style="width: 100%;"></el-input>
                </el-form-item>

                <div class="title">
                    <div class="left">- 行驶证</div>
                </div>
                <div class="v_con">
                    <el-upload
                        :class="{hide:uploadDisabled2}"
                        action
                        ref="vehicleFile"
                        :auto-upload="false"
                        list-type="picture-card"
                        :on-change="getVehicle"
                        :on-remove="v_remove"
                        style="display: inline-block;width:148px; height:148px;margin:0 20px"
                    >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <div class="v_right">
                        <el-form-item label="车 牌 号">
                            <el-input v-model="form.numberPlate" style="width: 100%;"></el-input>
                        </el-form-item>
                        <el-form-item label="车型">
                            <el-select
                                v-model="form.car_type"
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
                    </div>
                </div>
                <div class="title">
                    <div class="left">- 车辆照片</div>
                </div>
                <el-upload
                    :class="{hide:uploadDisabled3}"
                    action
                    :auto-upload="false"
                    list-type="picture-card"
                    :on-change="getCar"
                    :on-remove="c_remove"
                    style="margin:0 20px"
                >
                    <i class="el-icon-plus"></i>
                </el-upload>
            </el-form>-->
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            driverInfo: {},
            src: '/static/img/frame.a541d9c3.png',
            runOften: '广州、深圳、佛山、上海',
            tableData: [
                {
                    date: '2016-05-02',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                },
                {
                    date: '2016-05-04',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1517 弄'
                },
                {
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄'
                },
                {
                    date: '2016-05-03',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1516 弄'
                }
            ],
            loginData: '',
            // array: [{
            //     name: 'dd',
            //     url: 'http://192.168.0.231:4869/faf7d5f94a22fe14eb3f6182d4a17fd3'
            // }],
            _token: '',
            uploadDisabled: false,
            uploadDisabled2: false,
            uploadDisabled3: false,
            dialogFormVisible: false,
            form: {
                ne: '张三',
                ph: '188888888856',
                userID: '',
                cn: '粤B8888',
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
            ]
        };
    },
    created() {
        // this.getData();
    },
    methods: {
        getData() {
            console.log(this.driverInfo.tid);

            let action = `${this.$store.state.Login.url}/30075`;
            this.loginData = JSON.parse(this.$store.state.Login.loginData);

            var bd = {
                tid: this.loginData.tid,
                qtid: this.driverInfo.tid,
                pg: 1,
                sz: 20
            };
            this.$axios
                .post(action, {
                    hd: {
                        pi: 30075,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let data = JSON.parse(res.data.bd);
                    this.tableData = data.olst;
                    console.log(res.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        d_open(row) {
            this.dialogFormVisible = true;
            this.driverInfo = row;
            // console.log(row);
        },
        getBase64(file) {
            return new Promise(function(resolve, reject) {
                let reader = new FileReader();
                let imgResult = '';
                reader.readAsDataURL(file);
                reader.onload = function() {
                    imgResult = reader.result;
                };
                reader.onerror = function(error) {
                    reject(error);
                };
                reader.onloadend = function() {
                    resolve(imgResult);
                };
            });
        },
        getDrive(file, fileList) {
            this.uploadDisabled = fileList.length >= 1;

            this.getBase64(file.raw).then(res => {
                // this.form.jpc = res;
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/driving_license?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then(res => {
                    // console.log(res.data);

                    this.form.name = res.data.words_result.姓名.words;
                    // this.form.userID = res.data.words_result.证号.words;
                });

                let action = `${this.$store.state.Login.url}/60001`;
                this.loginData = JSON.parse(this.$store.state.Login.loginData);
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
                    .then(res => {
                        let data = JSON.parse(res.data.bd);
                        this.form.jpc = data.url;
                        // console.log(data.url);
                        // this.drive_fileList = fileList;
                        // console.log(fileList);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        d_remove(file, fileList) {
            setTimeout(() => {
                this.uploadDisabled = fileList.length >= 1;
                this.uploadDisabled2 = fileList.length >= 1;
            }, 500);
        },
        getVehicle(file, fileList) {
            this.uploadDisabled2 = fileList.length >= 1;
            this.getBase64(file.raw).then(res => {
                this.form.xpc = res;
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/vehicle_license?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then(res => {
                    this.form.numberPlate = res.data.words_result.号牌号码.words;
                    this.form.Identifier = res.data.words_result.车辆识别代号.words;
                    // console.log(res.data.words_result);
                    // // console.log(res.data.words_result.证号.words);
                });

                let action = `${this.$store.state.Login.url}/60001`;
                this.loginData = JSON.parse(this.$store.state.Login.loginData);
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
                    .then(res => {
                        let data = JSON.parse(res.data.bd);
                        this.form.xpc = data.url;
                        // console.log(data.url);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        v_remove(file, fileList) {
            setTimeout(() => {
                this.uploadDisabled2 = fileList.length >= 1;
            }, 500);
        },
        getCar(file, fileList) {
            this.uploadDisabled3 = fileList.length >= 2;
        },
        c_remove(file, fileList) {
            setTimeout(() => {
                this.uploadDisabled3 = fileList.length >= 2;
            }, 500);
        },
        // 新增司机
        saveEdit() {
            this.dialogFormVisible = false;
            // console.log(this.form);
        },
        //修改
        d_change(row) {
            console.log(row);

            this.form.name = row.ne;
            this.form.phone = row.ph;
            this.form.numberPlate = row.cn;
            this.car_types.forEach(element => {
                for (const key in element) {
                    if (key === 'value') {
                        if (element[key] == row.ct) {
                            this.form.car_type = element.value;
                        }
                    }
                }
            });
            this.car_long.forEach(element => {
                for (const key in element) {
                    if (key === 'value') {
                        if (element[key] == row.cl) {
                            this.form.car_length = element.value;
                        }
                    }
                }
            });
            var loginData = JSON.parse(this.$store.state.Login.loginData);
            this.form.jpc = row.jpc;
            this.form.xpc = row.xpc;
            this.form.src_jpc = `${loginData.murl}${row.jpc}`;
            this.form.src_xpc = `${loginData.murl}${row.xpc}`;
        }
    },
    components: {}
};
</script>

<style scoped lang="scss">
.addDriver {
    .a_dri {
        .title {
            border-bottom: 2px solid #409eff;
            margin: 20px 0;
            .left {
                font-size: 14px;
                line-height: 24px;
                border-radius: 12px 0 12px 0;
                background: #409eff;
                color: #fff;
                width: 100px;
                text-align: center;
            }
        }
        ._first {
            margin-top: 0;
        }
        /deep/ .el-form-item--mini.el-form-item,
        .el-form-item--small.el-form-item {
            margin-right: 25px;
            margin-bottom: 15px;
        }
        /deep/ .el-dialog__header {
            padding: 6px 10px;
            background: #409eff;
            .el-dialog__title {
                color: #fff;
                font-size: 14px;
            }
            .el-dialog__headerbtn .el-dialog__close {
                color: #fff;
            }
            .el-dialog__headerbtn {
                top: 10px;
            }
        }
        /deep/ .el-dialog__body {
            padding: 15px 15px;
        }
        .hide {
            display: inline-block;
            margin: 0 20px;
            /deep/ .el-upload--picture-card {
                display: none;
            }
        }
        .v_con {
            display: flex;
            flex-direction: row;
            .v_right {
                display: flex;
                flex-direction: column;
            }
        }
        /deep/ .el-dialog__footer {
            padding: 12px 20px;
            background-color: #f8f8f8;
        }
        .d_info {
            display: flex;
            flex-direction: row;
            padding: 20px 0;
            background-color: #199ed8;
            border-radius: 4px;
            box-shadow: 0 3px 6px #3333337a;
            .d_car {
                width: 250px;
                .car_img {
                    width: 100px;
                    height: 100px;
                    margin-left: 90px;
                    background: url('../../assets/img/truck.png') no-repeat 0 30px;
                    background-size: 60%;
                    background-position-x: right;
                }
            }
            ._info {
                padding: 10px 0;
                p {
                    color: #333;
                    font-size: 16px;
                    line-height: 33px;
                }
                .cph {
                    font-weight: bold;
                }
                .ne_ph {
                    margin-top: 10px;
                    font-weight: bold;
                    .ne {
                        margin-right: 25px;
                    }
                }
            }
        }
        .carImg {
            display: flex;
            // width: 598px;
            height: 160px;
            padding: 30px 20px;
            flex-direction: row;
            justify-content: space-between;
        }
        .waybill_record {
            padding: 10px 15px;
            .cplx {
                margin-bottom: 20px;
                .r_title {
                    font-size: 15px;
                    ._txt {
                        color: #333;
                        margin: 0 10px;
                    }
                    b {
                        font-size: 17px;
                        color: #000;
                    }
                }
            }
            .dd_table {
                /deep/ .has-gutter {
                    .is-leaf {
                        padding: 5px 0;
                    }
                }
            }
        }
    }
}
</style>
