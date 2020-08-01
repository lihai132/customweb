<template>
    <div class="cargoPhoto">
        <el-dialog
            title="支付运费"
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
                        <el-tab-pane label="支付运费" name="first">
                            <div class="from">
                                <el-form
                                    ref="form"
                                    :model="form"
                                    label-width="110px"
                                    class="from-nav"
                                >
                                    <el-form-item label="运费" required>
                                        <el-input-number
                                            v-model="form.freight"
                                            controls-position="right"
                                            :min="0"
                                            :max="9999999"
                                            style="width:230px;"
                                            :step="50"
                                            :maxlength="8"
                                            :precision="2"
                                        ></el-input-number>
                                    </el-form-item>
                                    <el-form-item label="银行服务费" required>
                                        <el-input-number
                                            v-model="form.bankPrice"
                                            controls-position="right"
                                            :min="0"
                                            :max="9999999"
                                            style="width:230px;"
                                            :step="50"
                                            :maxlength="8"
                                            :disabled="true"
                                            :precision="2"
                                        ></el-input-number>
                                    </el-form-item>
                                    <el-form-item label="总费用" required>
                                        <el-input-number
                                            v-model="form.totalPrice"
                                            controls-position="right"
                                            :min="0"
                                            :max="9999999"
                                            style="width:230px;"
                                            :step="50"
                                            :maxlength="8"
                                            :disabled="true"
                                            :precision="2"
                                        ></el-input-number>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <div class="cargoPicture">
                                <div class="title">支付方式</div>

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
                        </el-tab-pane>
                    </el-tabs>
                </el-main>
                <el-footer style="height:50px;">
                    <el-button size="small" @click="determine" :disabled="But">支付</el-button>
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
            radio: 1,
            form: {
                totalPrice: '',
                freight: '',
                bankPrice: ''
            },
            head: {
                start: '',
                end: '',
                dis: ''
            },
            activeClass: 0,
            activeName: 'first',
            cargoPicture: [
                require('../../assets/img/UnionPay.png'),
                require('../../assets/img/agricultural bank.jpg'),
                require('../../assets/img/agricultural bank.jpg')
            ],
            type: ['银联', '农业银行个人账户', '农业银行企业账户'],
            loginData: {},
            orderData: {},
            newsData: {},
            bankList: [],
            sk: '',
            type1: {},
            type2: {}
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.CustomerData;

        this.bankCharges();
    },
    methods: {
        //点击弹窗事件
        receipt(index, row) {
            this.editVisible = true;
            this.orderData = this.$store.state.Order.orderData;
            this.newsData = this.$store.state.Order.newsData;

            this.head.end = this.orderData.xct;
            this.head.dis = this.orderData.dis;
            this.head.start = this.orderData.zct;
        },
        //取消事件
        cancel() {
            this.editVisible = false;
            this.form = {
                totalPrice: '',
                freight: '',
                bankPrice: ''
            };
        },

        //获取银行手续费参数
        async bankCharges() {
            let action = this.$store.state.Login.url + '/30077';
            let bd = {
                tid: this.loginData.tid
            };
            var hd = {
                pi: 30077,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30077', hd, bd);
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
                this.bankList = data.olst;
                this.bankList.map((item) => {
                    if (item.id == 4) {
                        this.type1 = item;
                    }
                    if (item.id == 5) {
                        this.type2 = item;
                    }
                });
                // console.log(this.type2);
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },

        // 确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/20022';
            let ptid = 0;
            let at = '';
            if (this.radio == 0) {
                ptid = 0;
            }
            if (this.radio == 1) {
                ptid = 'ImmediatePay';
                at = 1;
            }
            if (this.radio == 2) {
                ptid = 'ImmediatePay';
                at = 2;
            }
            if (this.form.totalPrice.length == 0) {
                this.open2('运费不能为空');
            } else {
                let bd = {
                    tid: this.loginData.tid,
                    wid: this.orderData.wid,
                    pyf: this.form.freight,
                    pte: this.radio == 0 ? 5 : 4,
                    ptid: ptid,
                    tx: 0,
                    fee: this.form.bankPrice,
                    amt: this.form.totalPrice,
                    at: at,
                    dc: ''
                };
                console.log(bd);
                var hd = {
                    pi: 20022,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/20022', hd, bd);
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
                        this.$emit('paymentOpen');
                    } else {
                        this.open1('支付成功');
                        this.cancel();
                        this.$emit('Refresh');
                    }
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
                message: text,
                type: 'error',
                duration: 900
            });
        }
    },
    mounted() {},
    watch: {
        'form.freight': function (newVal) {
            // console.log(this.bankList);
            if (this.radio == 0) {
                if (this.type2.sf == 1) {
                    this.form.bankPrice = this.type2.fr;
                    this.form.totalPrice = newVal + this.form.bankPrice;
                }
                if (this.type2.sf == 2) {
                    this.form.bankPrice = (newVal * this.type2.fr).toFixed(2);
                    this.form.totalPrice = newVal * 1 + this.form.bankPrice * 1;
                    console.log(this.form.totalPrice, this.form.bankPrice);
                }
            }
            if (this.radio == 1) {
                if (this.type1.sf == 1) {
                    this.form.bankPrice = this.type1.fr;
                    this.form.totalPrice = newVal + this.form.bankPrice;
                }
                if (this.type1.sf == 2) {
                    this.form.bankPrice = (newVal * this.type1.fr).toFixed(2);
                    this.form.totalPrice = newVal + this.form.bankPrice * 1;
                }
            }
            if (this.radio == 2) {
                if (this.type1.sf == 1) {
                    this.form.bankPrice = this.type1.fr;
                    this.form.totalPrice = newVal + this.form.bankPrice;
                }
                if (this.type1.sf == 2) {
                    this.form.bankPrice = (newVal * this.type1.fr).toFixed(2);
                    this.form.totalPrice = newVal + this.form.bankPrice * 1;
                }
            }
        },
        radio: function (newVal) {
            if (newVal == 0) {
                if (this.type2.sf == 1) {
                    this.form.bankPrice = this.type2.fr;
                    this.form.totalPrice = this.form.freight + this.form.bankPrice;
                    console.log(111);
                }
                if (this.type2.sf == 2) {
                    this.form.bankPrice = (this.form.freight * this.type2.fr).toFixed(2);
                    this.form.totalPrice = this.form.freight * 1 + this.form.bankPrice * 1;
                    console.log(this.form.totalPrice, this.form.bankPrice);
                }
            }
            if (newVal == 1) {
                if (this.type1.sf == 1) {
                    this.form.bankPrice = this.type1.fr;
                    this.form.totalPrice = this.form.freight + this.form.bankPrice;
                }
                if (this.type1.sf == 2) {
                    this.form.bankPrice = (this.form.freight * this.type1.fr).toFixed(2);
                    this.form.totalPrice = this.form.freight + this.form.bankPrice * 1;
                }
            }
            if (newVal == 2) {
                if (this.type1.sf == 1) {
                    this.form.bankPrice = this.type1.fr;
                    this.form.totalPrice = this.form.freight + this.form.bankPrice;
                }
                if (this.type1.sf == 2) {
                    this.form.bankPrice = (this.form.freight * this.type1.fr).toFixed(2);
                    this.form.totalPrice = this.form.freight + this.form.bankPrice * 1;
                }
            }
        }
    }
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
                        display: flex;
                        justify-content: space-around;
                        padding-bottom: 15px;
                        .list {
                            display: flex;
                            flex-direction: column;
                            justify-content: center;
                            align-items: center;
                        }
                        .list:nth-child(1) {
                            img {
                                width: 184px;
                                height: 84px;
                                padding-top: 16.5px;
                                padding-bottom: 26.5px;
                            }
                        }
                        .list:nth-child(2) {
                            img {
                                width: 117px;
                                height: 117px;
                                padding-right: 20px;
                                padding-bottom: 10px;
                            }
                        }
                        .list:nth-child(3) {
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

