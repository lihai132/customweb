<template>
    <div class="cargoPhoto">
        <el-dialog
            title="关闭运单"
            :visible.sync="editVisible"
            width="700px"
            class="dialog"
            :close-on-click-modal="false"
        >
            <!-- <img src="../../assets/img/bg-img3.png" alt=""> -->
            <el-container>
                <el-main>
                    <div class="head">
                        <div class="head-start">
                            <div class="start-icon">
                                <img src="../../assets/img/start.png" alt />
                            </div>
                            <div class="abbreviate">{{head.start}}</div>
                            <!-- <div class="abbreviate">西藏 拉萨克斯坦我们的撒</div> -->
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
                            <!-- <div class="abbreviate">西藏 拉萨克斯坦</div> -->
                        </div>
                    </div>
                    <div class="from">
                        <el-form ref="form" :model="form" label-width="80px" class="from-nav">
                            <el-form-item label="实际装货时间">
                                <el-col :span="11">
                                    <el-date-picker
                                        type="date"
                                        placeholder="选择日期"
                                        v-model="form.start"
                                        style="width: 100%;"
                                        value-format="yyyy-MM-dd"
                                        
                                    ></el-date-picker>
                                </el-col>
                            </el-form-item>
                            <el-form-item label="实际卸货时间">
                                <el-col :span="11">
                                    <el-date-picker
                                        type="date"
                                        placeholder="选择日期"
                                        v-model="form.end"
                                        style="width: 100%;"
                                        value-format="yyyy-MM-dd"
                                    ></el-date-picker>
                                </el-col>
                            </el-form-item>
                        </el-form>
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
            But: false,
            editVisible: false,
            form: {
                start: '',
                end: ''
            },
            head: {
                start: '',
                end: '',
                dis: ''
            },
            loginData: {},
            orderData: {},
            newsData: {}
        };
    },
    created() {},
    methods: {
        //点击弹窗事件
        receipt(index, row) {
            this.editVisible = true;

            this.loginData = this.$store.state.Login.DispatchData;
            this.orderData = this.$store.state.Order.orderData;
            this.newsData = this.$store.state.Order.newsData;
            console.log(this.orderData);
            this.head.end = this.orderData.xct;
            this.head.dis = this.orderData.dis;
            this.head.start = this.orderData.zct;

            let startArr = this.orderData.zte.split(' ');
            this.form.start = startArr[0];

            let endArr = this.orderData.xte.split(' ');
            this.form.end = endArr[0];
        },
        //取消事件
        cancel() {
            this.editVisible = false;
        },
        onSubmit() {
            console.log('submit!');
        },
        //确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/20023';

            if (this.form.start.length == 0) {
                this.open2('装货日期不能为空');
                return;
            }
            if (this.form.end.length == 0) {
                this.open2('卸货日期不能为空');
                return;
            }
            if (this.form.start > this.form.end) {
                this.open2('装货日期不能大于卸货日期');
                return;
            }
            if (this.form.start.length > 0 && this.form.end.length > 0) {
                let bd = {
                    tid: this.loginData.tid,
                    wid: this.orderData.wid,
                    zte: this.form.start,
                    xte: this.form.end,
                    ishs: 0,
                    isdy: 0,
                    dc: ''
                };
                var hd = {
                    pi: 20023,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/20023', hd, bd);
                let rid = res.data.hd.rid;

                if (rid >= 0) {
                    this.cancel();
                    this.open1('关闭成功');
                    this.$emit('Refresh');
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
            this.But = true;
            setTimeout(() => {
                this.But = false;
            }, 1000);
        },

        open1(text) {
            this.$message({
                message: text,
                type: 'success',
                duration: 900
            });
        },
        open2(text) {
            this.$message({
                showClose: true,
                message: text,
                type: 'error',
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
        height: 300px;

        .el-main {
            flex: 1;
            padding: 0;
            padding-bottom: 10px;
            .head {
                height: 120px;
                padding-bottom: 0;
                width: 100%;
                margin: auto;
                // background: #009dd9;
                background-image: url(../../assets/img/bg-img4.png);
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
                    // flex: 1;
                    width: 340px;
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
            .from {
                padding-left: 10px;
                padding-right: 10px;
                display: flex;
                flex-direction: column;
                .from-nav {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    margin-top: 20px;
                    /deep/.el-form-item__label {
                        width: 110px !important;
                    }
                    /deep/ .el-form-item__content {
                        margin-left: 110px !important;
                    }
                    /deep/.el-form-item .el-form-item--small {
                        text-align: center;
                    }
                    /deep/.el-col-11 {
                        width: 100% !important;
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

