<template>
    <div class="cargoPhoto">
        <el-dialog
            title="报价"
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
                    <div class="nav">
                        <div class="text-title1">货物信息</div>
                        <!-- <div class="news">
                            <div class="news-left" ref="copy">
                                <div class="CargoInformation">
                                    <div class="key">货物信息：</div>
                                    <div
                                        class="value"
                                    >{{news.on?news.on+"、":""}}{{news.dw?news.dw+"吨":""}}{{news.dv!=0?news.dv+"方":""}}</div>
                                </div>
                                <div class="VehicleDemand">
                                    <div class="key">期望运费：</div>
                                    <div class="value">{{news.qyf?news.qyf:""}}</div>
                                </div>
                                <div class="other">
                                    <div class="key">装货时间：</div>
                                    <div
                                        class="value"
                                    >{{news.zdy?news.zdy+"，":""}} {{news.zte?news.zte:""}}</div>
                                </div>
                            </div>
                        </div>
                        <div class="text-title2">报价</div> -->
                        <div class="from">
                            <el-form
                                ref="form"
                                :model="form"
                                :rules="rules"
                                label-width="80px"
                                class="from-nav"
                            >
                                <el-form-item label="市场价格" required>
                                    <!-- <el-input v-model="form.price" style="width:230px;"></el-input> -->
                                    <el-input-number
                                        v-model.trim="form.price"
                                        controls-position="right"
                                        :min="0"
                                        :max="99999"
                                        style="width:230px;"
                                        :step="50"
                                        :maxlength="8"
                                    ></el-input-number>
                                </el-form-item>

                                <el-form-item label="司机名称" prop="name">
                                    <el-input
                                        v-model.trim="form.name"
                                        style="width:230px;"
                                        :minlength="2"
                                        :maxlength="16"
                                    ></el-input>
                                </el-form-item>
                                <el-form-item label="司机电话" prop="phone">
                                    <el-input
                                        v-model="form.phone"
                                        style="width:230px;"
                                        :minlength="11"
                                        :maxlength="11"
                                        oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
                                    ></el-input>
                                </el-form-item>
                            </el-form>
                            <div class="vehicleType">
                                <div class="title">车型：</div>
                                <div class="block">
                                    <el-checkbox-group
                                        v-model="checkboxGroup3"
                                        size="mini"
                                        :max="1"
                                    >
                                        <el-checkbox-button
                                            :label="item"
                                            v-for="(item,index) in truckType"
                                            :key="index"
                                            style="margin-right:10px; margin-bottom: 5px;"
                                            border
                                        >{{item}}</el-checkbox-button>
                                    </el-checkbox-group>
                                </div>
                            </div>
                            <div class="conductor">
                                <div class="title">车长：</div>
                                <div class="block">
                                    <el-checkbox-group
                                        v-model="checkboxGroup4"
                                        size="mini"
                                        :max="1"
                                    >
                                        <el-checkbox-button
                                            :label="item"
                                            v-for="(item,index) in conductor"
                                            :key="index"
                                            style="margin-right:10px; margin-bottom: 5px;"
                                            class="checkbox"
                                            border
                                        >{{item}}</el-checkbox-button>
                                    </el-checkbox-group>
                                </div>
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
const ruckList = ['平板', '高栏', '中栏', '低栏', '高低板', '厢车', '自卸', '保温', '冷藏', '危险品', '集装箱', '特种'];
const conductorList = [
    '4.2米',
    '5.0米',
    '6.2米',
    '6.8米',
    '7.2米',
    '7.7米',
    '7.8米',
    '8.2米',
    '8.7米',
    '9.6米',
    '12.5米',
    '13.0米',
    '13.5米',
    '14.6米',
    '15.0米',
    '16.5米',
    '17.5米'
];
export default {
    data() {
        return {
            editVisible: false,
            checked: false,
            But: false,
            form: {
                price: '',
                name: '',
                phone: '',
                ct: '',
                cl: ''
            },
            head: {
                start: '',
                end: '',
                dis: ''
            },
            news: {
                dw: '',
                dv: '',
                on: '',
                qyf: '',
                zdy: '',
                zte: ''
            },
            conductor: conductorList,
            truckType: ruckList,
            checkboxGroup3: [],
            checkboxGroup4: [],
            rules: {
                price: [
                    { required: true, message: '请输入报价金额', trigger: 'blur' },
                    { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请输入司机手机号码', trigger: 'blur' },
                    { min: 11, max: 11, message: '长度为11位数字字符', trigger: 'blur' }
                ],
                name: [
                    { required: true, message: '请输入司机名称', trigger: 'blur' },
                    { min: 2, max: 16, message: '长度为2到16位字符', trigger: 'blur' }
                ]
            },
            conductor1: [
                '4.2米',
                '5.0米',
                '6.2米',
                '6.8米',
                '7.2米',
                '7.7米',
                '7.8米',
                '8.2米',
                '8.7米',
                '9.6米',
                '12.5米',
                '13.0米',
                '14.6米',
                '15.0米',
                '16.5米',
                '17.5米',
                '13.5米'
            ]
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
            this.news.dw = this.popupData.dw;
            this.news.dv = this.popupData.dv;
            this.news.on = this.popupData.on;
            this.news.qyf = this.popupData.qyf;
            this.news.zdy = this.popupData.zdy;
            this.news.zte = this.popupData.zte;
            this.But = false;

            // this.loginData = JSON.parse(this.$store.state.Login.loginData);
            // console.log(this.popupData);
        },
        //取消事件
        cancel() {
            this.editVisible = false;
            this.form = {
                price: '',
                name: '',
                phone: '',
                ct: '',
                cl: ''
            };
        },
        onSubmit() {
            console.log('submit!');
        },
        //确定事件
        async determine() {
            this.But = true;
            let action = this.$store.state.Login.url + '/20024';
            if (this.form.price <= 0) {
                this.open2('报价不能为空');
                this.But = true;
                setTimeout(() => {
                    this.But = false;
                }, 1000);
                return;
            }
            if (this.form.name.length == 0) {
                this.open2('司机名称不能为空');
                this.But = true;
                setTimeout(() => {
                    this.But = false;
                }, 1000);
                return;
            }
            if (this.form.phone.length == 0) {
                this.open2('司机电话不能为空');
                this.But = true;
                setTimeout(() => {
                    this.But = false;
                }, 1000);
                return;
            }
            if (this.checkboxGroup3.length == 0) {
                this.open2('车型不能为空');
                this.But = true;
                setTimeout(() => {
                    this.But = false;
                }, 1000);
                return;
            }
            if (this.checkboxGroup4.length == 0) {
                this.open2('车长不能为空');
                this.But = true;
                setTimeout(() => {
                    this.But = false;
                }, 1000);
                return;
            } else {
                let ct = '';
                this.truckType.map((item, index) => {
                    if (item == this.checkboxGroup3[0]) {
                        ct = index + 1;
                    }
                });
                let cl = '';
                this.conductor1.map((item, index) => {
                    if (item == this.checkboxGroup4[0]) {
                        cl = index + 1;
                    }
                });
                let bd = {
                    tid: this.popupData.tid,
                    oid: this.popupData.oid,
                    qyf: this.form.price,
                    ne: this.form.name,
                    ph: this.form.phone,
                    ct: ct,
                    cl: cl
                };
                var hd = {
                    pi: 20024,
                    si: this.popupData.si,
                    sq: 9
                };
                let res = await request('/20024', hd, bd);
                let rid = res.data.hd.rid;
                if (rid >= 0) {
                    this.cancel();
                    this.open1('报价成功');
                    this.$emit('refresh');
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
            // this.But = false;

            // setTimeout(() => {
            //     this.But = false;
            // }, 1000);
            // return;
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
        height: 600px;

        .el-main {
            flex: 1;
            padding: 0;
            padding-bottom: 10px;
            position: relative;
            display: flex;
            flex-direction: column;
            .head {
                height: 120px;
                padding-bottom: 0;
                width: 100%;
                margin: auto;
                background: #009dd9;
                display: flex;
                justify-content: space-between;
                align-items: center;
                position: absolute;
                top: 0;
                left: 0;
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
            .nav {
                margin-top: 120px;
                overflow: scroll;
                flex: 1;
                .text-title1 {
                    font-size: 18px;
                    line-height: 40px;
                    color: rgba($color: #000000, $alpha: 0.5);
                    text-align: center;
                }
                .text-title2 {
                    font-size: 18px;
                    line-height: 40px;
                    color: rgba($color: #000000, $alpha: 0.5);
                    text-align: center;
                }
                .news {
                    height: 122px;
                    display: flex;
                    justify-content: space-between;
                    border: 1px solid #ccc;
                    border-radius: 3px;
                    box-shadow: 0 1px 0 0 #ccc;
                    padding: 10px 16%;
                    box-sizing: border-box;
                    width: 90%;
                    border-radius: 5px;
                    margin: auto;
                    margin-bottom: 20px;

                    .news-left {
                        height: 100%;
                        flex: 1;
                        display: flex;
                        flex-direction: column;
                        margin-left: 10px;
                        justify-content: space-around;
                        box-sizing: border-box;
                        .CargoInformation,
                        .VehicleDemand,
                        .other {
                            display: flex;

                            .key {
                                color: rgba($color: #000000, $alpha: 0.5);
                                width: 70px;
                            }
                            .value {
                                font-size: 16px;
                                color: #000;
                                overflow: hidden;
                                text-overflow: ellipsis;
                                display: -webkit-box;
                                -webkit-line-clamp: 1;
                                -webkit-box-orient: vertical;
                            }
                        }
                    }
                    .news-right {
                        height: 100%;
                        width: 270px;
                        border-left: 1px solid #ccc;
                        display: flex;
                        position: relative;

                        .cargoPicture {
                            flex: 1;
                            display: flex;
                            justify-content: space-around;

                            img {
                                width: 100px;
                                height: 100px;
                            }
                        }
                        .butCopy {
                            position: absolute;
                            top: 70px;
                            left: -58px;
                        }
                    }
                }
                .from {
                    width: 90%;
                    border-radius: 5px;
                    margin: auto;
                    // border: 1px solid #ccc;
                    display: flex;
                    flex-direction: column;
                    .from-nav {
                        display: flex;
                        flex-direction: column;
                        padding: 10px 22%;
                        // margin-top: 10px;
                        // /deep/.el-form-item__label {
                        //     width: 70px !important;
                        //     text-align: left;
                        // }
                        // /deep/ .el-form-item__content {
                        //     margin-left: 80px !important;
                        // }
                        // /deep/.el-form-item .el-form-item--small {
                        //     text-align: center;
                        // }
                        // /deep/.el-col-11 {
                        //     width: 100% !important;
                        // }
                        /deep/.el-form-item--small {
                            margin-bottom: 20px !important;
                            height: 32px !important;
                        }
                    }
                    .vehicleType,
                    .conductor {
                        display: flex;
                        flex-wrap: wrap;
                        margin-bottom: 10px;
                        position: relative;
                        .title {
                            width: 50px;
                            text-align: right;
                            line-height: 32px;
                            span {
                                padding-top: 3px;
                                color: red;
                            }
                        }
                        .block {
                            flex: 1;

                            .el-checkbox-group {
                                width: 100%;

                                /deep/ .el-checkbox-button--mini .is-checked {
                                    margin-bottom: 5px;
                                    border: 1px solid #dcdfe6 !important;
                                }

                                /deep/.el-checkbox-button__inner {
                                    width: 70px;
                                    // border-left-color: #409eff !important;
                                }
                                /deep/.el-checkbox-button .el-checkbox-button__inner {
                                    border: 1px solid #dcdfe6;
                                    border-radius: 0px 0 0 0px;
                                    -webkit-box-shadow: none !important;
                                    box-shadow: none !important;
                                    // border-left-color: #409eff !important;
                                }
                            }
                        }
                        .tipa {
                            font-size: 12px;
                            color: rgba($color: #000000, $alpha: 0.5);
                            position: absolute;
                            right: 30px;
                            bottom: 10px;
                        }
                    }
                }
            }
            .nav::-webkit-scrollbar {
                display: none;
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

