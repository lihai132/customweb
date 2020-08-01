<template>
    <div class="cargoPhoto">
        <el-dialog
            title="申请代付"
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
                    <div class="nav2">
                        <div class="title">司机费用</div>
                        <div class="center">
                            <el-form
                                :model="form2"
                                ref="ruleForm"
                                label-width="100px"
                                class="form"
                                :disabled="true"
                            >
                                <div class="row">
                                    <!-- <span class="tipe">*</span> -->
                                    <el-form-item label="司机服务费">
                                        <el-input-number
                                            v-model="form2.service"
                                            controls-position="right"
                                            :min="0"
                                            :max="99999"
                                            style="width:215px;"
                                            :step="50"
                                            :maxlength="8"
                                        ></el-input-number>
                                    </el-form-item>
                                </div>

                                <el-form-item label="司机运费">
                                    <el-input-number
                                        v-model="form2.freight"
                                        controls-position="right"
                                        :min="0"
                                        :max="99999"
                                        style="width:215px;"
                                        :step="50"
                                        :maxlength="8"
                                    ></el-input-number>
                                </el-form-item>
                                <el-form-item label="回单押金">
                                    <el-input-number
                                        v-model="form2.deposit"
                                        controls-position="right"
                                        :min="0"
                                        :max="99999"
                                        style="width:215px;"
                                        :step="50"
                                        :maxlength="8"
                                    ></el-input-number>
                                </el-form-item>
                                <el-form-item label="油卡费">
                                    <el-input-number
                                        v-model="form2.oilCard"
                                        controls-position="right"
                                        :min="0"
                                        :max="99999"
                                        style="width:215px;"
                                        :step="50"
                                        :maxlength="8"
                                    ></el-input-number>
                                </el-form-item>
                            </el-form>
                        </div>
                    </div>
                    <div class="nav3">
                        <div class="title">
                            货主运费
                            <!-- <div class="check">
                                <el-checkbox
                                    v-model="form3.check"
                                    style="margin-left:20px;"
                                >开具增值税发票(当前税率)</el-checkbox>
                            </div>-->
                        </div>
                        <div class="center">
                            <el-form
                                :model="form3"
                                ref="ruleForm"
                                label-width="100px"
                                class="form"
                                :disabled="true"
                            >
                                <div class="row">
                                    <!-- <span class="tipe">*</span> -->
                                    <el-form-item label="佣金">
                                        <el-input-number
                                            v-model="form3.commission"
                                            controls-position="right"
                                            :min="0"
                                            :max="99999"
                                            style="width:215px;"
                                            :step="50"
                                            :maxlength="8"
                                        ></el-input-number>
                                    </el-form-item>
                                </div>

                                <el-form-item label="税费">
                                    <el-input-number
                                        v-model="form3.insurance"
                                        controls-position="right"
                                        :min="0"
                                        :max="99999"
                                        style="width:215px;"
                                        :step="50"
                                        :maxlength="8"
                                        :disabled="true"
                                    ></el-input-number>
                                </el-form-item>
                                <el-form-item label="货主运费">
                                    <el-input-number
                                        v-model="form3.totaLfreight"
                                        controls-position="right"
                                        :min="0"
                                        :max="99999"
                                        style="width:215px;"
                                        :step="50"
                                        :maxlength="8"
                                        :disabled="true"
                                    ></el-input-number>
                                </el-form-item>
                            </el-form>
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
            checked: false,
            But: false,
            form2: {
                service: '',
                freight: '',
                deposit: '',
                oilCard: ''
            },
            form3: {
                commission: '',
                insurance: 0,
                totaLfreight: ''
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
            this.loginData = this.$store.state.Login.CustomerData;
            this.orderData = this.$store.state.Order.orderData;
            this.newsData = this.$store.state.Order.newsData;
            console.log(this.orderData);

            this.head.end = this.orderData.xct;
            this.head.dis = this.orderData.dis;
            this.head.start = this.orderData.zct;

            this.form2.service = this.orderData.dch;
            this.form2.freight = this.orderData.dyf;
            this.form2.deposit = this.orderData.hdyf;
            this.form2.oilCard = this.orderData.oil;

            this.form3.commission = this.orderData.opch;
            this.form3.insurance = this.orderData.tn;
            this.form3.totaLfreight = this.orderData.yf;
        },
        //取消事件
        cancel() {
            this.editVisible = false;
        },

        //确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/20048';

            let bd = {
                tid: this.loginData.tid,
                wid: this.orderData.wid,
                dc: ''
            };

            var hd = {
                pi: 20048,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/20048', hd, bd);
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                this.cancel();
                this.open1('已申请代付成功');
                this.$emit('refresh');
            } else {
                this.open2(res.data.hd.rmsg);
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
        height: 676px;

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

            .nav2,
            .nav3 {
                height: 255px;

                padding-top: 20px;
                padding-left: 10px;
                padding-right: 10px;
                .title {
                    border-bottom: 1px solid #eee;
                    text-align: center;
                    padding-bottom: 10px;
                }
                .center {
                    // position: relative;
                    padding-left: 140px;
                    display: flex;
                    flex-direction: column;
                    .form {
                        margin-top: 22px;
                        position: relative;
                        height: 52px;
                        .el-input-number {
                            /deep/.el-input--smal {
                                font-size: 16px !important;
                                font-weight: 600 !important;
                            }
                        }
                        .dis {
                            position: absolute;
                            left: 600px;
                            top: 40%;
                            display: flex;
                            flex-direction: column;
                            justify-content: center;
                            .text {
                                font-size: 20px;
                                text-align: center;
                            }
                            .num {
                                font-size: 20px;
                                text-align: center;
                            }
                        }
                        .row {
                            display: flex;
                            position: relative;
                            .el-form-item {
                                margin-bottom: 18px;
                            }
                            .tipe {
                                position: absolute;
                                top: 10px;
                                color: #f56c6c;
                                left: 8px;
                            }
                        }
                    }
                    // .table {
                    //     flex: 1;
                    //     /deep/.el-table__body-wrapper {
                    //         height: 400px;
                    //         overflow: scroll;
                    //     }
                    //     /deep/.el-table__body-wrapper::-webkit-scrollbar {
                    //         display: none;
                    //     }
                    // }
                }
            }
            .nav3 {
                height: 200px;
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

