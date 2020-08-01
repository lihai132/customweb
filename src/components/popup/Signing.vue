<template>
    <div class="cargoPhoto">
        <el-dialog
            title="签约"
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
                    <el-tabs v-model="activeName" class="tabs">
                        <el-tab-pane label="签约" name="first">
                            <div class="from">
                                <el-form
                                    ref="form"
                                    :model="form"
                                    label-width="80px"
                                    class="from-nav"
                                >
                                    <el-form-item label="服务费">
                                        <!-- <el-input
                                            v-model="form.totalPrice"
                                            style="width:230px;"
                                            maxlength="5"
                                        ></el-input>-->
                                        <el-input-number
                                            v-model="form.totalPrice"
                                            controls-position="right"
                                            :min="0"
                                            :max="999999"
                                            style="width:230px;"
                                            :step="50"
                                            :maxlength="8"
                                            :disabled="true"
                                        ></el-input-number>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <div class="cargoPicture">
                                <!-- <div class="title">支付方式</div> -->

                                <div class="imgList">
                                    <el-radio-group v-model="radio" class="radio">
                                        <div
                                            class="list"
                                            v-for="(item,index) in cargoPicture"
                                            :key="index"
                                        >
                                            <img :src="item" preview="1" preview-textss alt />
                                            <el-radio :label="index">{{type[index]}}</el-radio>
                                        </div>
                                    </el-radio-group>
                                </div>
                            </div>

                            <!-- <div class="tip">提示：不同的支付通道需要收取一定的服务费，具体收费标准如下</div>
                            <div class="table">
                                <div class="t-head">
                                    <div class="name1">银行</div>
                                    <div class="name2">收费标准</div>
                                </div>
                                <div class="t-body">
                                    <div class="th1">
                                        <div class="td1">农行</div>
                                        <div class="td2">0</div>
                                    </div>
                                    <div class="th2">
                                        <div class="td1">银联</div>
                                        <div class="td2">3%</div>
                                    </div>
                                </div>
                            </div>-->
                        </el-tab-pane>
                    </el-tabs>
                </el-main>
                <el-footer style="height:50px;">
                    <el-button size="small" @click="determine">确定</el-button>
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
            radio: 1,
            form: {
                totalPrice: '',
                oilCard: '',
                deposit: '',
                tax: '',
                cash: '',
                at: ''
            },
            head: {
                start: '',
                end: '',
                dis: ''
            },
            activeClass: 0,
            activeName: 'first',
            cargoPicture: [
                require('../../assets/img/agricultural bank.jpg'),
                require('../../assets/img/agricultural bank.jpg'),
                require('../../assets/img/UnionPay.png')
            ],
            type: ['农业银行企业账户', '农业银行个人账户', '银联'],
            loginData: {},
            orderData: {},
            newsData: {},
            sk: ''
        };
    },
    created() {},
    methods: {
        //点击弹窗事件
        receipt(index, row) {
            this.editVisible = true;

            this.loginData = this.$store.state.Login.DispatchData;
            this.sk = this.$store.state.Login.key;
            this.orderData = this.$store.state.Order.orderData;
            this.newsData = this.$store.state.Order.newsData;
            console.log(this.orderData);
            this.head.end = this.orderData.xct;
            this.head.dis = this.orderData.dis;
            this.head.start = this.orderData.zct;
            this.form.totalPrice = this.orderData.dch;
        },
        //取消事件
        cancel() {
            this.editVisible = false;
        },

        // 确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/20015';

            let ptid = 0;
            if (this.radio == 0) {
                this.form.at = 2;
                ptid = 'ImmediatePay';
            }
            if (this.radio == 1) {
                this.form.at = 1;
                ptid = 'ImmediatePay';
            }
            if (this.radio == 2) {
                this.form.at = 0;
                ptid = 0;
            }
            if (this.form.totalPrice.length == 0) {
                this.open2('请填写服务费');
            } else {
                let bd = {
                    tid: this.loginData.tid,
                    pte: this.radio == 2 ? 5 : 4,
                    at: this.form.at,
                    ptid: ptid,
                    wid: this.orderData.wid,
                    ce: this.form.totalPrice,
                    dc: ''
                };
                let hd = {
                    pi: 20015,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/20015', hd, bd);
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
                    if (data.ispt > 0) {
                        window.open(data.purl);

                        this.cancel();
                        this.$emit('open3');
                    } else {
                        this.open1('签约成功');
                        this.cancel();
                        this.$emit('open3');
                    }
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
        },

        open1(text) {
            this.$message({
                message: text,
                type: 'success'
            });
        },
        open2(text) {
            this.$message.error(text);
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
        height: 600px;

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
            .tabs {
                /deep/.el-tabs__nav-scroll {
                    padding: 0 49%;
                }
                .from-nav {
                    width: 350px;
                    margin: auto;
                }
                .cargoPicture {
                    text-align: center;
                    .title {
                        width: 100%;
                        text-align: center;
                        font-size: 18px;
                        font-weight: 550;
                        line-height: 18px;
                        padding-bottom: 20px;
                        padding-left: 20px;
                        box-sizing: border-box;
                    }
                    .radio {
                        padding-top: 30px;
                        display: flex;
                        justify-content: space-around;
                        padding-bottom: 15px;
                        .list {
                            display: flex;
                            flex-direction: column;
                            justify-content: center;
                            align-items: center;
                        }
                        .list:nth-child(3) {
                            width: 33.33% !important;
                            img {
                                width: 184px;
                                height: 84px;
                                padding-top: 16.5px;
                                padding-bottom: 26.5px;
                            }
                        }
                        .list:nth-child(1) {
                            width: 33.33% !important;
                            img {
                                width: 117px;
                                height: 117px;
                                padding-right: 20px;
                                padding-bottom: 10px;
                            }
                        }
                        .list:nth-child(2) {
                            width: 33.33% !important;
                            img {
                                width: 117px;
                                height: 117px;
                                padding-right: 20px;
                                padding-bottom: 10px;
                            }
                        }
                    }
                }
                .tip {
                    font-size: 12px;
                    color: rgba($color: red, $alpha: 0.5);
                    line-height: 30px;
                    padding-left: 20px;
                }
                .table {
                    border: 1px solid rgba($color: #000000, $alpha: 0.5);
                    width: 202px;
                    margin-left: 20px;
                    .t-head {
                        display: flex;
                        border-bottom: 1px solid rgba($color: #000000, $alpha: 0.5);

                        .name1 {
                            border-right: 1px solid rgba($color: #000000, $alpha: 0.5);
                            width: 100px;
                            height: 30px;
                            line-height: 30px;
                            text-align: center;
                        }
                        .name2 {
                            width: 100px;
                            height: 30px;
                            line-height: 30px;
                            text-align: center;
                        }
                    }

                    .t-body {
                        .th1 {
                            border-bottom: 1px solid rgba($color: #000000, $alpha: 0.5);
                            display: flex;
                            .td1 {
                                border-right: 1px solid rgba($color: #000000, $alpha: 0.5);
                                width: 100px;
                                height: 30px;
                                line-height: 30px;
                                text-align: center;
                            }
                            .td2 {
                                width: 100px;
                                height: 30px;
                                line-height: 30px;
                                text-align: center;
                            }
                        }
                        .th2 {
                            display: flex;
                            .td1 {
                                border-right: 1px solid rgba($color: #000000, $alpha: 0.5);
                                width: 100px;
                                height: 30px;
                                line-height: 30px;
                                text-align: center;
                            }
                            .td2 {
                                width: 100px;
                                height: 30px;
                                line-height: 30px;
                                text-align: center;
                            }
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

