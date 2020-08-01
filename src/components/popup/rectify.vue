<template>
    <div class="cancel">
        <el-dialog
            title="冲正运费"
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
                            <div class="abbreviate">{{this.head.start}}</div>
                        </div>
                        <div class="head-center">
                            <div class="distance">
                                {{this.head.dis}}
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
                            <div class="abbreviate">{{this.head.end}}</div>
                        </div>
                    </div>
                    <el-form ref="form" :model="form" label-width="80px" class="form">
                        <el-form-item label="冲正类型">
                            <el-radio-group
                                v-model="form.isCollapse"
                                style="margin-bottom: 20px;"
                                @change="change1"
                            >
                                <el-radio-button :label="1">增加</el-radio-button>
                                <el-radio-button :label="2">减少</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="运费类型" v-if="orderData.wt==1?false:true">
                            <el-radio-group
                                v-model="form.priceType"
                                style="margin-bottom: 20px;"
                                @change="change2"
                            >
                                <el-radio-button :label="1">货主运费</el-radio-button>
                                <el-radio-button :label="2">司机运费</el-radio-button>
                                <!-- <el-radio v-model="radio" label="1">货主运费</el-radio>
                                <el-radio v-model="radio" label="2">司机运费</el-radio>-->
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="冲正金额" style="margin-top:20px
                        ">
                            <!-- <el-input v-model="form.price" maxlength="5" :min="0"></el-input> -->
                            <el-input-number
                                v-model="form.price"
                                controls-position="right"
                                :min="0"
                                :max="9999999"
                                style="width:230px;"
                                :step="50"
                                :maxlength="8"
                                :precision="2"
                            ></el-input-number>
                        </el-form-item>
                    </el-form>
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
            popupData: {},

            form: {
                price: '',
                opt: 1,
                isCollapse: 1,
                priceType: 1
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
        receipt() {
            this.editVisible = true;
            this.loginData = this.$store.state.Login.CustomerData;
            this.orderData = this.$store.state.Order.orderData;
            this.newsData = this.$store.state.Order.newsData;
            console.log(this.loginData);
            this.head.end = this.orderData.xct;
            this.head.dis = this.orderData.dis;
            this.head.start = this.orderData.zct;
        },
        //取消事件
        cancel() {
            this.editVisible = false;
        },
        //确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/20035';
            console.log(this.form.opt);
            if (this.form.price == 0) {
                this.open2('增加或减少的运费不能为0');
            } else {
                let bd = {
                    tid: this.loginData.tid,
                    wid: this.orderData.wid,
                    opt: this.form.opt,
                    ft: this.form.priceType,
                    zyf: this.form.price,
                    dc: ''
                };
                var hd = {
                    pi: 20035,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/20035', hd, bd);
                let rid = res.data.hd.rid;
                if (rid >= 0) {
                    this.cancel();
                    this.open1();
                    this.form.price = '';
                    this.$emit('refresh');
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }

            this.But = true;
            setTimeout(() => {
                this.But = false;
            }, 1000);
        },

        open1() {
            this.$message({
                message: '调整成功',
                type: 'success',
                duration: 900
            });
        },
        open2(text) {
            // this.$message.error('提交失败：' + text);
            this.$message({
                message: text,
                type: 'error',
                duration: 900
            });
        },

        change1(data) {
            this.form.opt = data;
            console.log(data);
        },
        change2(data) {
            this.form.priceType = data;
            console.log(data);
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
            font-size: 14px;
        }
        /deep/.el-dialog__title {
            color: #fff;
        }
        /deep/.el-dialog__headerbtn {
            top: 9px !important;
            font-size: 14px;
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
        height: 600px;

        .el-main {
            padding: 0;
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
            .form {
                margin-top: 60px;
                /deep/.el-form-item--small {
                    margin-bottom: 15px !important;
                    height: 32px !important;
                    width: 300px !important;
                    margin: auto;
                }
                /deep/.el-form-item__content {
                    height: 32px !important;
                    margin-bottom: 0 !important;
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

