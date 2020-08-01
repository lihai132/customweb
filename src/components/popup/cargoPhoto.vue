<template>
    <div class="cargoPhoto">
        <el-dialog
            title="上传货物照片"
            :visible.sync="editVisible"
            width="700px"
            class="dialog"
            :close-on-click-modal="false"
        >
            <el-container>
                <el-main>
                    <div class="head">
                        <div class="head-start">
                            <div class="start-icon">
                                <img src="../../assets/img/start.png" alt />
                            </div>
                            <div class="abbreviate">{{head.start}}</div>
                        </div>
                        <div class="head-center">
                            <div class="distance">
                                {{head.dis}}
                                <span>公里</span>
                            </div>
                            <div class="img">
                                <img src="../../assets/img/xuxian.png" alt />
                            </div>

                            <div class="cart-center">
                                <img src="../../assets/img/center.png" alt />
                            </div>
                        </div>
                        <div class="head-end">
                            <div class="end-icon">
                                <img src="../../assets/img/end.png" alt />
                            </div>
                            <div class="abbreviate">{{head.end}}</div>
                        </div>
                    </div>
                    <div class="photo">
                        <!-- <div class="title">
                            <div class="left">- 上传图片</div>
                        </div>-->
                        <div class="load">
                            <div class="container">
                                <!-- 文件上传得主要功能部分 -->
                                <el-upload
                                    action
                                    ref="driveFile1"
                                    :auto-upload="false"
                                    list-type="picture-card"
                                    :on-preview="handlePictureCardPreview"
                                    :on-change="getFile1"
                                    :on-remove="remove1"
                                >
                                    <i class="el-icon-plus"></i>
                                </el-upload>
                            </div>
                            <div class="container">
                                <!-- 文件上传得主要功能部分 -->
                                <el-upload
                                    action
                                    ref="driveFile2"
                                    :auto-upload="false"
                                    list-type="picture-card"
                                    :on-preview="handlePictureCardPreview"
                                    :on-change="getFile2"
                                    :on-remove="remove2"
                                >
                                    <i class="el-icon-plus"></i>
                                </el-upload>
                            </div>
                        </div>
                    </div>
                </el-main>
                <el-footer style="height:50px;">
                    <el-button size="small" @click="determine" :disabled="But">确定</el-button>
                    <el-button size="small" @click="cancel">取消</el-button>
                </el-footer>
            </el-container>
        </el-dialog>
    </div>
</template>
<script>
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';

export default {
    data() {
        return {
            editVisible: false,
            But: false,
            head: {
                start: '',
                end: '',
                dis: ''
            },
            popupData: {},
            dialogImageUrl: '',
            dialogVisible: false,
            loginData: {},
            sk: '',
            img: {
                url1: '',
                url2: ''
            }
        };
    },
    created() {},
    methods: {
        //点击弹窗事件
        receipt(index, row) {
            this.editVisible = true;

            this.popupData = this.$store.state.Popup.popup2;
            this.head.start = this.popupData.start;
            this.head.end = this.popupData.end;
            this.head.dis = this.popupData.dis;

            this.loginData = this.$store.state.Login.CustomerData;
            // console.log(this.popupData);
        },
        //取消事件
        cancel() {
            this.editVisible = false;
        },
        //确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/20027';
            if (this.img.url1.length == 0 && this.img.url2.length == 0) {
                this.open3();
            }
            if (this.img.url1.length > 0 && this.img.url2.length == 0) {
                let pic = this.img.url1;
                let bd = {
                    tid: this.popupData.tid,
                    oid: this.popupData.oid,
                    pic: pic
                };
                let hd = {
                    pi: 20027,
                    si: this.popupData.si,
                    sq: 9
                };
                let res = await request('/20027', hd, bd);
                let rid = res.data.hd.rid;
                if (rid >= 0) {
                    this.cancel();
                    this.clearFiles();
                    this.img.url1 = '';
                    this.img.url2 = '';
                    this.$emit('refresh');
                    this.open1();
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
            if (this.img.url1.length == 0 && this.img.url2.length > 0) {
                let pic = this.img.url2;
                let bd = {
                    tid: this.popupData.tid,
                    oid: this.popupData.oid,
                    pic: pic
                };
                let hd = {
                    pi: 20027,
                    si: this.popupData.si,
                    sq: 9
                };
                let res = await request('/20027', hd, bd);
                let rid = res.data.hd.rid;
                if (rid >= 0) {
                    this.cancel();
                    this.clearFiles();
                    this.img.url1 = '';
                    this.img.url2 = '';
                    this.$emit('refresh');
                    this.open1();
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
            if (this.img.url1.length > 0 && this.img.url2.length > 0) {
                let pic = this.img.url1 + ',' + this.img.url2;
                let bd = {
                    tid: this.popupData.tid,
                    oid: this.popupData.oid,
                    pic: pic
                };
                let hd = {
                    pi: 20027,
                    si: this.popupData.si,
                    sq: 9
                };
                let res = await request('/20027', hd, bd);
                let rid = res.data.hd.rid;
                if (rid >= 0) {
                    this.cancel();
                    this.clearFiles();
                    this.img.url1 = '';
                    this.img.url2 = '';
                    this.$emit('refresh');
                    this.open1();
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }

            this.But = true;
            setTimeout(() => {
                this.But = false;
            }, 1000);
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
        //清空
        clearFiles() {
            this.$refs['driveFile1'].clearFiles();
            this.$refs['driveFile2'].clearFiles();
        },

        getFile1(file, fileList) {
            this.getBase64(file.raw).then(res => {
                // baseUrl = res;
                // console.log(baseUrl);
                let arr = res.split(',');
                // console.log(arr);
                let baseUrl = arr[1];
                let action = this.$store.state.Login.url + '/60002';
                // this.loginData = this.$store.state.Login.CustomerData;

                var bd = {
                    tid: this.popupData.tid,
                    pte: '',
                    img: baseUrl,
                    pn: ''
                };

                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60002,
                            si: this.popupData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let data = JSON.parse(res.data.bd);

                        this.img.url1 = data.url;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
            this.But = true;
            setTimeout(() => {
                this.But = false;
            }, 1500);
        },
        getFile2(file, fileList) {
            this.getBase64(file.raw).then(res => {
                // baseUrl = res;
                // console.log(baseUrl);
                let arr = res.split(',');
                // console.log(arr);
                let baseUrl = arr[1];
                let action = this.$store.state.Login.url + '/60002';
                // this.loginData = this.$store.state.Login.CustomerData;

                var bd = {
                    tid: this.popupData.tid,
                    pte: '',
                    img: baseUrl,
                    pn: ''
                };
                // console.log(bd);
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60002,
                            si: this.popupData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let data = JSON.parse(res.data.bd);
                        this.img.url2 = data.url;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
            this.But = true;
            setTimeout(() => {
                this.But = false;
            }, 1500);
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        //删除函数
        remove1(file, fileList) {
            this.img.url1 = '';
            fileList = [];
        },
        remove2(file, fileList) {
            this.img.url2 = '';
            fileList = [];
        },

        open1() {
            this.$message({
                message: '上传成功',
                type: 'success',
                duration: 900
            });
        },
        open2(text) {
            this.$message({
                message: '上传成功',
                type: 'error',
                duration: 900
            });
        },
        open3() {
            this.$message({
                message: '最少需要上传一张货物照片',
                type: 'warning',
                duration: 900
            });
        }
    },
    mounted() {}
};
</script>

<style lang="scss" scoped>
.dialog {
    /deep/.el-dialog__body {
        padding: 0 !important;
    }

    /deep/ .el-dialog__header {
        padding: 6px 10px !important;
        background: #409eff;
        display: flex;
        justify-content: space-between;
        align-items: center;
        /deep/.el-dialog__title {
            line-height: 20px;
            font-size: 16px;
        }
        /deep/.el-dialog__title {
            color: #fff;
        }
        /deep/.el-dialog__headerbtn {
            top: 8px !important;
        }
    }

    /deep/.el-icon-close:hover {
        background: #eee;
        color: #fff;
    }

    /deep/.el-dialog__headerbtn .el-dialog__close {
        color: #fff !important;
    }

    /deep/.pswp,
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

    /deep/.pswp__bg {
        background: rgba($color: #000000, $alpha: 0.6) !important;
    }

    .el-container {
        height: 430px;

        .el-main {
            flex: 1;
            padding: 0;
            padding-bottom: 10px;
            .head {
                height: 120px;
                padding-bottom: 0;
                width: 100%;
                margin: auto;
                background: #009dd9;
                display: flex;
                justify-content: space-between;
                align-items: center;
                .head-start,
                .head-end {
                    // width: 100px;
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                    align-items: center;
                    .abbreviate {
                        font-size: 16px;
                        color: #fff;
                        font-weight: 600;
                        text-align: center;
                        padding-bottom: 10px;
                    }
                    .detailed {
                        font-size: 12px;
                        line-height: 16px;
                        margin-top: 5px;
                        color: #fff;
                        margin: 0 10px;
                    }
                }
                .head-start {
                    height: 100%;
                    padding-bottom: 18px;
                    // position: relative;
                    width: 100px;
                    .abbreviate {
                        text-align: right;

                        // margin-right: 20px;
                    }
                    .start-icon {
                        text-align: center;
                    }

                    .start-icon {
                        // margin-right: 15px;
                        margin-top: 10px;
                    }
                }
                .head-end {
                    padding-bottom: 18px;
                    height: 100%;
                    width: 100px;
                    .abbreviate {
                        text-align: left;

                        // margin-left: 20px;
                    }
                    .end-icon {
                        text-align: center;
                    }

                    .end-icon {
                        // margin-left: 25px;
                        margin-top: 10px;
                    }
                }
                .head-center {
                    flex: 1;
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                    align-items: center;
                    position: relative;
                    .distance {
                        color: #fff;
                        margin-bottom: 25px;
                    }
                    .type {
                        width: 101px;
                        height: 32px;
                        text-align: center;
                        font-size: 18px;
                        font-weight: 600;
                        color: #fff;
                        background: #00cc00;
                        line-height: 32px;
                        border-radius: 3px;
                        margin-right: 11px;
                    }

                    .cart-center {
                        position: absolute;
                        top: 33px;
                        left: 46.7%;
                    }
                }
            }
            .photo {
                padding-left: 10px;
                padding-right: 10px;
                display: flex;
                flex-direction: column;
                .title {
                    border-bottom: 2px solid #409eff;
                    padding-top: 10px;
                    height: 24px;

                    .left {
                        font-size: 16px;
                        line-height: 24px;
                        border-radius: 12px 0 12px 0;
                        background: #409eff;
                        color: #fff;
                        width: 100px;
                        text-align: center;
                    }
                }
                .load {
                    height: 210px;
                    display: flex;
                    // justify-content: center;
                    .container {
                        width: 148px;
                        height: 148px;
                        border: none;
                        overflow: hidden;
                        // box-sizing: border-box;
                        padding: 0;
                        margin-left: 35px;
                        margin-top: 35px;
                        /deep/.el-upload .el-upload--picture-card {
                            display: none;
                        }
                        /deep/.el-upload-list__item-preview {
                            display: none;
                        }
                        /deep/.el-upload-list__item-delete {
                            text-align: center;
                            margin-left: 0;
                        }
                    }
                }
            }
        }

        .el-footer {
            text-align: center;
            border-top: 1px solid #aaaaaa;
            background: #f8f8f8;
            line-height: 50px;
        }
    }

    .el-main::-webkit-scrollbar {
        display: none;
    }
}
</style>

