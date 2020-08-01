<template>
    <div class="cargoPhoto">
        <el-dialog
            title="接单"
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
                        <div class="news-left" ref="copy">
                            <div class="box">
                                <div class="title">货物信息</div>
                                <div class="box-center">
                                    <div class="CargoInformation">
                                        <div class="key">货物信息：</div>
                                        <div
                                            class="value"
                                        >{{news.dw?news.dw+"吨":""}}{{news.dv?news.dv+"方，":""}}{{news.on?news.on:""}}{{news.zdy?"，"+news.zdy:""}} {{news.zte?news.zte:""}}</div>
                                    </div>
                                    <div class="CargoInformation">
                                        <div class="key">期望运费：</div>
                                        <div class="value">{{news.qyf?news.qyf+"元":""}}</div>
                                    </div>
                                    <div class="CargoInformation">
                                        <div class="key">装货时间：</div>
                                        <div
                                            class="value"
                                        >{{news.zdy?news.zdy+" ":""}}{{news.zte?news.zte:""}}</div>
                                    </div>
                                </div>
                            </div>

                            <div class="box">
                                <div class="title">用车需求</div>
                                <div class="box-center1">
                                    <div class="CargoInformation">
                                        <div class="key">用车需求：</div>
                                        <div
                                            class="value"
                                        >{{news.te?news.te+",":""}}{{news.ct?news.ct+",":""}}{{news.cl?news.cl:""}}</div>
                                    </div>
                                    <div class="CargoInformation">
                                        <div class="key">其他需求：</div>
                                        <div class="value">{{news.odc?news.odc:""}}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cargoPicture">
                            <img
                                :src="item"
                                preview="1"
                                preview-textss
                                alt
                                v-for="(item,index) in cargoPicture"
                                :key="index"
                            />
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
            But: false,
            textarea: '',
            editVisible: false,
            popupData: {},
            loginData: {},
            head: {
                start: '',
                end: '',
                dis: ''
            },
            checked: false,
            cargoPicture: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            news: {
                dw: '',
                dv: '',
                on: '',
                qyf: '',
                zdy: '',
                zte: '',
                ct: '',
                cl: '',
                zxte: '',
                zdy: '',
                zte: '',
                te: '',
                odc: '',
                pic: ''
            }
        };
    },
    created() {},
    methods: {
        //点击弹窗事件
        receipt(index, row) {
            this.editVisible = true;
            this.loginData = this.$store.state.Login.DispatchData;
            this.popupData = this.$store.state.Popup.popup2;
            this.head.start = this.popupData.data.zct;
            this.head.end = this.popupData.data.xct;
            this.head.dis = this.popupData.data.dis;
            console.log(this.popupData);
            this.news.dw = this.popupData.data.dw;
            this.news.dv = this.popupData.data.dv;
            this.news.on = this.popupData.data.on;
            this.news.qyf = this.popupData.data.qyf;
            this.news.zte = this.popupData.data.zte;
            this.news.ct = this.popupData.data.ct;
            this.news.cl = this.popupData.data.cl;
            this.news.zxte = this.popupData.data.zxte;
            this.news.zdy = this.popupData.data.zdy;
            this.news.zte = this.popupData.data.zte;
            this.news.te = this.popupData.data.te == 1 ? '整车' : '配货';
            this.news.odc = this.popupData.data.odc;
            this.news.pic = this.popupData.data.pic;
            if (this.popupData.data.pic) {
                let num = this.popupData.data.pic.indexOf(',');
                if (num == -1) {
                    this.cargoPicture = [this.popupData.data.pic.indexOf];
                } else {
                    let arr = this.popupData.data.pic.split(',');
                    this.cargoPicture = [this.loginData.murl + arr[0], this.loginData.murl + arr[1]];
                }
            } else {
                this.cargoPicture = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            }
        },
        //取消事件
        cancel() {
            this.editVisible = false;
        },
        //确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/20029';
            this.loginData = this.$store.state.Login.DispatchData;

            var bd = {
                tid: this.loginData.tid,
                oid: this.popupData.oid
            };
            var hd = {
                pi: 20029,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/20029', hd, bd);
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                this.editVisible = false;
                this.$emit('getData');
                this.open1('接单成功');
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
                display: flex;
                flex-direction: column;
                align-items: center;
                .news-left {
                    width: 100%;
                    .box {
                        width: 100%;
                        .title {
                            font-size: 18px;
                            line-height: 40px;
                            font-weight: 550;
                            color: rgba($color: #000000, $alpha: 0.5);
                            text-align: center;
                        }
                        .box-center,
                        .box-center1 {
                            box-sizing: border-box;
                            height: 100px;
                            width: 90%;
                            margin: auto;
                            border: 1px solid rgba($color: #ccc, $alpha: 0.5);
                            display: flex;
                            flex-direction: column;
                            justify-content: space-around;
                            align-items: left;
                        }
                        .box-center1 {
                            height: 70px;
                        }
                    }
                }
                .CargoInformation {
                    margin-left: 100px;
                    display: flex;

                    .key {
                        color: rgba($color: #000000, $alpha: 0.5);
                    }
                    .value {
                        font-size: 16px;
                        color: #000;
                    }
                }
                .cargoPicture {
                    width: 100%;
                    flex: 1;
                    display: flex;
                    justify-content: center;
                    padding-top: 13px;

                    img {
                        width: 150px;
                        height: 150px;
                        margin-right: 60px;
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

