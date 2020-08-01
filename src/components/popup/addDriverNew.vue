<template>
    <div class="addDriver">
        <el-dialog
            :title="titleMsg"
            :visible.sync="dialogFormVisible"
            width="628px"
            class="a_dri"
            :close-on-click-modal="false"
            :before-close="handleClose"
        >
            <el-form :model="form" label-width="80px" size="mini" inline>
                <el-form-item label="司机电话">
                    <el-input
                        v-model="form.phone"
                        maxlength="11"
                        :disabled="isDisabled"
                        style="width: 100%;"
                    ></el-input>
                </el-form-item>
                <div class="title _first">
                    <div class="left">- 身份证 驾驶证</div>
                </div>
                <div class="userImgInfo">
                    <el-upload
                        class="avatar-uploader"
                        action
                        :auto-upload="false"
                        :show-file-list="false"
                        :on-change="getIdCard"
                    >
                        <el-image :src="idcard_imageUrl" class="avatar"></el-image>
                    </el-upload>
                    <el-upload
                        class="avatar-uploader"
                        action
                        :auto-upload="false"
                        :show-file-list="false"
                        :on-change="getDriverCard"
                    >
                        <el-image :src="driverCard_imageUrl" class="avatar"></el-image>
                    </el-upload>
                </div>
                <div class="userTxtInfo">
                    <el-form-item label="身份证号">
                        <el-input v-model="form.userID" style="width: 100%; margin-rigin:20px"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名">
                        <el-input v-model="form.ne" style="width: 100%;"></el-input>
                    </el-form-item>
                </div>

                <div class="title _first">
                    <div class="left">- 行驶证</div>
                </div>

                <div class="userImgInfo">
                    <el-upload
                        class="avatar-uploader"
                        action
                        :auto-upload="false"
                        :show-file-list="false"
                        :on-change="getvehicleHomepage"
                    >
                        <el-image :src="vehicleHomepage_imageUrl" class="avatar"></el-image>
                    </el-upload>
                    <el-upload
                        class="avatar-uploader"
                        action
                        :auto-upload="false"
                        :show-file-list="false"
                        :on-change="getvehicleVicepage"
                    >
                        <el-image :src="vehicleVicepage_imageUrl" class="avatar"></el-image>
                    </el-upload>
                </div>

                <div class="carTxtInfo">
                    <el-form-item label="车型">
                        <el-select v-model="form.car_type" placeholder="请选择" style="width: 12.75em">
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
                    <el-form-item label="车 牌 号">
                        <el-input v-model="form.numberPlate" maxlength="7" style="width: 100%;"></el-input>
                    </el-form-item>
                </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAdd">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import request from '../../assets/js/request.js';
import encryptUtil from '../../assets/js/encryptUtil.js';
export default {
    data() {
        return {
            titleMsg: '添加司机',
            dialogFormVisible: false,
            isDisabled: false,
            _token: '',
            loginData: '',
            sk: '',
            idcard_: require('../../assets/img/increaseDriver/idCard.png'),
            driverCard_: require('../../assets/img/increaseDriver/driverCard.png'),
            vehicleHomepage_: require('../../assets/img/increaseDriver/vehicleHomepage.png'),
            vehicleVicepage_: require('../../assets/img/increaseDriver/vehicleVicepage.png'),
            idcard_imageUrl: '',
            driverCard_imageUrl: '',
            vehicleHomepage_imageUrl: '',
            vehicleVicepage_imageUrl: '',
            form: {
                ne: '',
                phone: '',
                userID: '',
                numberPlate: '',
                Identifier: '',
                car_type: '',
                car_length: '',
                jpc: '', //驾驶证
                xpcf: '', //行驶证主页
                xpcb: '', //行驶证副业
                src_jpc: '',
                src_xpc: '',
                cpc: '',
                cu: '' //身份证正面
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
                    value: '5',
                    label: '高低板'
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
                }
            ]
        };
    },
    components: {},
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.DispatchData;
        this.getToken();
        this.defaultImg();
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
            this.titleMsg = '添加司机';
            this.isDisabled = false;
            for (var item in this.form) {
                this.form[item] = '';
            }
            this.defaultImg();
            this.dialogFormVisible = true;
        },
        //XX
        handleClose() {
            this.cancelAdd();
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
        getIdCard(file, fileList) {
            fileList.length > 0 ? (fileList.length = 0) : fileList.length;
            this.getBase64(file.raw).then(res => {
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/idcard?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}&id_card_side=front`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then(res => {
                    this.form.userID = res.data.words_result.公民身份号码.words;
                    this.idcard_imageUrl = URL.createObjectURL(file.raw);
                });
                // 上传图片
                let action = `${this.$store.state.Login.url}/60002`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 5,
                    img: imgBase64,
                    pn: ''
                };
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60002,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let data = JSON.parse(res.data.bd);
                        this.form.cu = data.url;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        getDriverCard(file, fileList) {
            fileList.length > 0 ? (fileList.length = 0) : fileList.length;
            this.getBase64(file.raw).then(res => {
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/driving_license?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then(res => {
                    this.form.ne = res.data.words_result.姓名.words;
                    this.driverCard_imageUrl = URL.createObjectURL(file.raw);
                });
                // 上传图片
                let action = `${this.$store.state.Login.url}/60002`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 6,
                    img: imgBase64,
                    pn: ''
                };
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60002,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let data = JSON.parse(res.data.bd);
                        this.form.jpc = data.url;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        getvehicleHomepage(file, fileList) {
            fileList.length > 0 ? (fileList.length = 0) : fileList.length;
            this.getBase64(file.raw).then(res => {
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/vehicle_license?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}&vehicle_license_side=front`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then(res => {
                    this.form.numberPlate = res.data.words_result.号牌号码.words;
                    this.vehicleHomepage_imageUrl = URL.createObjectURL(file.raw);
                });
                // 上传图片
                let action = `${this.$store.state.Login.url}/60002`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 7,
                    img: imgBase64,
                    pn: ''
                };
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60002,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let data = JSON.parse(res.data.bd);
                        this.form.xpcf = data.url;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        getvehicleVicepage(file, fileList) {
            fileList.length > 0 ? (fileList.length = 0) : fileList.length;
            this.vehicleVicepage_imageUrl = URL.createObjectURL(file.raw);
            this.getBase64(file.raw).then(res => {
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                //上传图片
                let action = `${this.$store.state.Login.url}/60002`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 7,
                    img: imgBase64,
                    pn: ''
                };
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60002,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let data = JSON.parse(res.data.bd);
                        this.form.xpcb = data.url;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        cancelAdd() {
            this.dialogFormVisible = false;
        },
        //新增
        async saveEdit() {
            if (this.form.phone.length != 11) {
                this.$alert('请输入正确的电话号码！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (!/^1[3|4|5|7|8]\d{9}$/.test(this.form.phone)) {
                this.$alert('请输入正确的电话号码！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (!this.form.phone || !this.form.ne || !this.form.userID || !this.form.numberPlate) {
                this.$alert('姓名、身份证号、车牌号不能为空！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (!this.form.phone.trim() || !this.form.ne.trim() || !this.form.userID.trim() || !this.form.numberPlate.trim()) {
                this.$alert('姓名、身份证号、车牌号不能为空！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (
                !/[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}/.test(
                    this.form.numberPlate
                )
            ) {
                this.$alert('请输入有效的车牌号!', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (!this.form.car_type || !this.form.car_length) {
                this.$alert('车型、车长不能为空！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (!this.form.cu || !this.form.jpc || !this.form.xpcf || !this.form.xpcb) {
                this.$alert('身份证照片、驾驶证照片、行驶证照片不能为空！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            let action = `${this.$store.state.Login.url}/40001`;
            
            var bd = {
                tid: this.loginData.tid,
                ph: this.form.phone,
                ne: this.form.ne,
                idc: this.form.userID,
                cu: this.form.cu, //身份证
                jpc: this.form.jpc, //驾驶证
                xpcf: this.form.xpcf, //行驶证正
                xpcb: this.form.xpcb, //行驶证副
                cn: this.form.numberPlate,
                ct: this.form.car_type,
                cl: this.form.car_length
            };
            let hd = {
                pi: 40001,
                si: this.loginData.si,
                sq: 9
            };

            let res = await request('/40001', hd, bd);
            // console.log(res);
            this.dialogFormVisible = false;
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                this.$emit('getData');
                // this.$emit('search');
                for (var item in this.form) {
                    this.form[item] = '';
                }
                this.defaultImg();
            } else {
                this.$message({
                    message: res.data.hd.rmsg,
                    type: 'error'
                });
                for (var item in this.form) {
                    this.form[item] = '';
                }
                this.defaultImg();
            }
            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 40001,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         this.dialogFormVisible = false;
            //         let data = res.data.hd;
            //         if (data.rid >= 0) {
            //             this.$emit('getData');
            //             for (var item in this.form) {
            //                 this.form[item] = '';
            //             }
            //             this.defaultImg();
            //         } else {
            //             this.$message({
            //                 type: 'info',
            //                 message: data.rmsg
            //             });
            //             for (var item in this.form) {
            //                 this.form[item] = '';
            //             }
            //             this.defaultImg();
            //         }
            //     });
        },
        defaultImg() {
            this.idcard_imageUrl = this.idcard_;
            this.driverCard_imageUrl = this.driverCard_;
            this.vehicleHomepage_imageUrl = this.vehicleHomepage_;
            this.vehicleVicepage_imageUrl = this.vehicleVicepage_;
        },
        //修改
        d_change(row) {
            console.log(row);
            this.titleMsg = '修改信息';
            this.isDisabled = true;
            this.dialogFormVisible = true;
            this.form.phone = row.ph;
            this.form.userID = row.idc;
            this.form.ne = row.ne;
            this.form.numberPlate = row.cn;
            this.form.car_type = row.ct;
            this.form.car_length = row.cl;
            this.car_types.forEach(element => {
                for (const key in element) {
                    if (key === 'value') {
                        if (element[key] == row.ct) {
                            this.form.car_type = element.value;
                        } else if (row.ct == 0) {
                            this.form.car_type = '请选择';
                        }
                    }
                }
            });
            this.car_long.forEach(element => {
                for (const key in element) {
                    if (key === 'value') {
                        if (element[key] == row.cl) {
                            this.form.car_length = element.value;
                        } else if (row.cl == 0) {
                            this.form.car_length = '请选择';
                        }
                    }
                }
            });
            this.form.cu = row.cu;
            this.form.jpc = row.jpc;
            this.form.xpcf = row.xpcf;
            this.form.xpcb = row.xpcb;
            var loginData = this.$store.state.Login.DispatchData;
            this.idcard_imageUrl = row.cu == '' ? this.idcard_ : `${loginData.murl}${row.cu}`;
            this.driverCard_imageUrl = row.jpc == '' ? this.driverCard_ : `${loginData.murl}${row.jpc}`;
            this.vehicleHomepage_imageUrl = row.xpcf == '' ? this.vehicleHomepage_ : `${loginData.murl}${row.xpcf}`;
            this.vehicleVicepage_imageUrl = row.xpcb == '' ? this.vehicleVicepage_ : `${loginData.murl}${row.xpcb}`;
        }
    }
};
</script>
<style>
.avatar-uploader .el-upload {
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}
.el-upload--text {
    width: 270px;
    height: 180px;
    border: none;
}
.avatar {
    width: 100%;
    height: 178px;
    display: block;
}
</style>
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
                width: 150px;
                text-align: center;
            }
        }
        ._first {
            margin-top: 0;
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
        .userImgInfo {
            display: flex;
            justify-content: space-around;
        }
        .userTxtInfo {
            // display: flex;
            // padding: 0 10px;
            margin-top: 20px;
            padding-left: 12px;
            // justify-content: space-around;
        }
        .carTxtInfo {
            margin-top: 20px;
            padding-left: 12px;
        }
        /deep/ .el-dialog__footer {
            padding: 12px 20px;
            background-color: #f8f8f8;
        }
    }
}
</style>
