<template>
    <div class="addDriver">
        <el-dialog
            title="添加司机"
            :visible.sync="dialogFormVisible"
            width="628px"
            class="a_dri"
            :close-on-click-modal="false"
            :before-close="handleClose"
        >
            <el-form :model="form" label-width="80px" size="mini" inline>
                <!-- <div class="title">
                    <div class="left">- 基本信息</div>
                </div>-->

                <el-form-item label="司机电话">
                    <el-input
                        v-model="form.phone"
                        maxlength="11"
                        :disabled="isDisabled"
                        style="width: 100%;"
                    ></el-input>
                </el-form-item>

                <!-- <el-form-item label>
                    <span>(例：粤B88888)</span>
                </el-form-item>-->

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
                    :file-list="driveFileList"
                    style="display: inline-block;width:148px; height:148px;margin:0 20px"
                >
                    <i class="el-icon-plus"></i>
                </el-upload>
                <el-form-item label="司机姓名">
                    <el-input v-model="form.ne" style="width: 100%;"></el-input>
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
                        :file-list="vehicleFileList"
                        style="display: inline-block;width:148px; height:148px;margin:0 20px"
                    >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <!-- <el-image v-if="disabled" :src="form.src_jpc"></el-image> -->
                    <div class="v_right">
                        <el-form-item label="车 牌 号">
                            <el-input v-model="form.numberPlate" maxlength="7" style="width: 100%;"></el-input>
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
                    ref="_carImg"
                    :auto-upload="false"
                    list-type="picture-card"
                    :on-change="getCar"
                    :on-remove="c_remove"
                    :file-list="carImgFileList"
                    style="margin:0 20px;"
                >
                    <i class="el-icon-plus"></i>
                </el-upload>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAdd">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            driveFileList: [],
            vehicleFileList: [],
            cpcArray: [],//车辆照片
            carImgFileList: [],
            // array: [{
            //     name: 'dd',
            //     url: 'http://192.168.0.231:4869/faf7d5f94a22fe14eb3f6182d4a17fd3'
            // }],
            _token: '',
            isDisabled: false,
            uploadDisabled: false,
            uploadDisabled2: false,
            uploadDisabled3: false,
            dialogFormVisible: false,
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
                src_xpc: '',
                cpc: ''
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
                    value: '18',
                    label: '13.5米'
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
            loginData: ''
        };
    },
    created() {
        this.getToken();
    },
    methods: {
        // 获取Access Token
        getToken() {
            var API_Key = 'xfL8VlHVjY1ubdZU8tpyhzoN';
            var Secret_Key = '4u8FDsdEUE2PPazEOSvyrfLZvmapPpky';
            var url = 'https://aip.baidubce.com/oauth/2.0/token';
            this.$axios
                .get(`/baidu_token/oauth/2.0/token?grant_type=client_credentials&client_id=${API_Key}&client_secret=${Secret_Key}`)
                .then(res => {
                    this._token = res.data.access_token;
                });
        },
        a_open() {
            this.isDisabled = false;
            for (var item in this.form) {
                this.form[item] = '';
            }

            // this.$refs.driveFile.clearFiles();
            // this.$refs.vehicleFile.clearFiles();
            // this.isDisabled = false;
            this.uploadDisabled = false;
            this.uploadDisabled2 = false;
            this.uploadDisabled3 = false;
            this.dialogFormVisible = true;
        },
        //修改时调用
        c_open() {
            // this.isDisabled = false;
            this.dialogFormVisible = true;
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
            // console.log(file);
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

                    this.form.ne = res.data.words_result.姓名.words;
                    // this.form.userID = res.data.words_result.证号.words;
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
                // this.uploadDisabled2 = fileList.length >= 1;
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
            this.getBase64(file.raw).then(res => {
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                let action = `${this.$store.state.Login.url}/60001`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 8,
                    img: imgBase64,
                    pn: ''
                };
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
                        this.cpcArray.push(data.url);
                        // console.log(this.cpcArray);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        c_remove(file, fileList) {
            this.cpcArray.pop();
            setTimeout(() => {
                this.uploadDisabled3 = fileList.length >= 2;
            }, 500);
        },
        // 新增司机
        saveEdit() {
            this.dialogFormVisible = false;
            // console.log(this.form);
            

            let action = `${this.$store.state.Login.url}/40001`;
            this.loginData = this.$store.state.Login.DispatchData;
            // console.log(this.cpcArray.slice(0,2).join(','));
            
            this.form.cpc = this.cpcArray.slice(0,2).join(',');
            var bd = {
                tid: this.loginData.tid,
                ne: this.form.ne,
                ph: this.form.phone,
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
                cpc: this.form.cpc, //车辆照片
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
                .then(res => {
                    let data = res.data.hd;
                    if (data.rid >= 0) {
                        // this.$message({
                        //     type: 'success',
                        //     message: '新增成功'
                        // });
                        // this.getData();
                        this.$emit('getData');
                        for (var item in this.form) {
                            this.form[item] = '';
                        }
                        this.cpcArray.length = 0;
                        this.$refs.driveFile.clearFiles();
                        this.$refs.vehicleFile.clearFiles();
                        this.$refs._carImg.clearFiles();
                        this.uploadDisabled = false;
                        this.uploadDisabled2 = false;
                        this.uploadDisabled3 = false;
                    } else {
                        this.$message({
                            type: 'info',
                            message: data.rmsg
                        });
                        for (var item in this.form) {
                            this.form[item] = '';
                        }
                        this.cpcArray.length = 0;
                        // console.log(this.cpcArray);
                        this.$refs.driveFile.clearFiles();
                        this.$refs.vehicleFile.clearFiles();
                        this.$refs._carImg.clearFiles();
                        this.uploadDisabled = false;
                        this.uploadDisabled2 = false;
                        this.uploadDisabled3 = false;
                    }
                    // console.log(res.data.hd.rid);
                })
                .catch(error => {
                    this.$message({
                        type: 'info',
                        message: `新增失败!!${error}`
                    });
                });
        },
        //取消增加
        cancelAdd() {
           
            // console.log(this.cpcArray.slice(0,2).join(','));
            if(this.cpcArray.length != 0){
                this.cpcArray.length = 0;
            //     this.$refs._carImg.clearFiles();
            }
            
            this.$refs.driveFile.clearFiles();
            this.$refs.vehicleFile.clearFiles();
            this.$refs._carImg.clearFiles();
            this.dialogFormVisible = false;
        },
        //修改
        d_change(row) {
            // console.log(row);
            this.isDisabled = true;
            this.dialogFormVisible = true;
            this.form.ne = row.ne;
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
            var loginData = this.$store.state.Login.DispatchData;
            this.form.jpc = row.jpc;
            this.form.xpc = row.xpc;
            if(row.cpc) this.cpcArray = row.cpc.split(',');
            // console.log(this.cpcArray);
            this.form.src_jpc = `${loginData.murl}${row.jpc}`;
            this.form.src_xpc = `${loginData.murl}${row.xpc}`;
            // console.log(this.form.src_jpc,this.form.src_xpc);
            this.driveFileList = [{ url: this.form.src_jpc }];
            this.vehicleFileList = [{ url: this.form.src_xpc }];
            this.uploadDisabled = this.driveFileList.length >= 1;
            this.uploadDisabled2 = this.vehicleFileList.length >= 1;

            if(row.cpc){
                var imgArray = row.cpc.split(',');
                // console.log(imgArray);
                if(imgArray.length == 1){
                    this.carImgFileList = [{ url: `${loginData.murl}${imgArray[0]}`}];
                    this.uploadDisabled3 = this.carImgFileList.length >= 2;
                }else if(imgArray.length == 2){
                    this.carImgFileList = [{ url: `${loginData.murl}${imgArray[0]}`},{ url: `${loginData.murl}${imgArray[1]}`}];
                    this.uploadDisabled3 = this.carImgFileList.length >= 2;
                }
            }else{
                this.uploadDisabled3 = false;
            }
        },
        //XX
        handleClose() {
            this.cancelAdd();
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
            padding: 25px 15px;
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
    }
}
</style>
