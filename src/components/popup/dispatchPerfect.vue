<template>
    <div class="perfect">
        <el-dialog
            title="修改地址"
            :visible.sync="editVisible"
            width="700px"
            class="dialog"
            :close-on-click-modal="false"
        >
            <el-container>
                <el-main>
                    <div class="information">
                        <div class="news-noe">
                            <div class="title">
                                <div class="left">- 发货信息</div>
                            </div>
                            <div class="nav">
                                <el-form
                                    :model="form1"
                                    ref="dynamicValidateForm"
                                    label-width="100px"
                                    class="demo-dynamic"
                                >
                                    <el-form-item label="起运地" required>
                                        <el-input
                                            v-model="form1.start"
                                            :disabled="true"
                                            style="width:400px;"
                                            :maxlength="64"
                                        ></el-input>
                                    </el-form-item>
                                    <el-form-item
                                        v-for="(domain, index) in form1.domains"
                                        :label="'详细地址' "
                                        :key="domain.key"
                                        :prop="'domains.' + index + '.value'"
                                    >
                                        <el-input
                                            v-model="domain.value"
                                            style="width:400px;"
                                            :maxlength="64"
                                        ></el-input>
                                        <el-button
                                            @click="startAdd"
                                            size="mini"
                                            type="success"
                                            style=" margin-left: 10px;"
                                            :disabled="disabled"
                                        >增加</el-button>
                                        <el-button
                                            @click="startRemove"
                                            size="mini"
                                            type="success"
                                        >删除</el-button>
                                    </el-form-item>
                                    <el-form-item v-if="startRoute" label="途径地">
                                        <el-input
                                            v-model="form1.route"
                                            style="width:400px;"
                                            :maxlength="64"
                                        ></el-input>
                                    </el-form-item>

                                    <div class="line"></div>
                                    <div class="nav-center1">
                                        <el-form-item label="发货人">
                                            <el-input
                                                v-model="start.name"
                                                placeholder="发货人"
                                                :disabled="true"
                                            ></el-input>
                                        </el-form-item>
                                        <el-form-item label="联系电话">
                                            <el-input
                                                v-model="start.phone"
                                                placeholder="联系电话"
                                                :disabled="true"
                                            ></el-input>
                                        </el-form-item>
                                    </div>
                                    <!-- <div class="nav-center2">

                                    </div>-->
                                    <el-form-item label="发货企业">
                                        <el-input
                                            v-model="start.enterprise"
                                            style="width:400px;"
                                            :disabled="true"
                                        ></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                        <div class="news-noe">
                            <div class="title">
                                <div class="left">- 收货信息</div>
                            </div>
                            <div class="nav">
                                <el-form
                                    :model="form2"
                                    ref="dynamicValidateForm"
                                    label-width="100px"
                                    class="demo-dynamic"
                                >
                                    <el-form-item label="目的地">
                                        <el-input
                                            v-model="form2.end"
                                            :disabled="true"
                                            style="width:400px;"
                                            :maxlength="64"
                                        ></el-input>
                                    </el-form-item>
                                    <el-form-item
                                        v-for="(domain, index) in form2.domains"
                                        :label="'详细地址' "
                                        :key="domain.key"
                                        :prop="'domains.' + index + '.value'"
                                    >
                                        <el-input
                                            v-model="domain.value"
                                            style="width:400px;"
                                            :maxlength="64"
                                        ></el-input>
                                        <el-button
                                            @click="endAdd"
                                            size="mini"
                                            type="success"
                                            style=" margin-left: 10px;"
                                            :disabled="disabled"
                                        >增加</el-button>
                                        <el-button @click="endRemove" size="mini" type="success">删除</el-button>
                                    </el-form-item>
                                    <el-form-item v-if="endRoute" label="途径地">
                                        <el-input
                                            v-model="form2.route"
                                            style="width:400px;"
                                            :maxlength="64"
                                        ></el-input>
                                    </el-form-item>

                                    <div class="line"></div>
                                    <div class="nav-center1">
                                        <el-form-item label="收货人">
                                            <el-input
                                                v-model="end.name"
                                                placeholder="收货人"
                                                :minlength="2"
                                                :maxlength="16"
                                            ></el-input>
                                        </el-form-item>
                                        <el-form-item label="联系电话">
                                            <el-input
                                                v-model="end.phone"
                                                placeholder="联系电话"
                                                :maxlength="12"
                                                oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
                                            ></el-input>
                                        </el-form-item>
                                    </div>
                                    <!-- <div class="nav-center2">

                                    </div>-->
                                    <el-form-item label="收货企业">
                                        <el-input
                                            v-model="end.enterprise"
                                            style="width:400px;"
                                            :maxlength="32"
                                        ></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                    </div>
                </el-main>
                <el-footer style="height:50px;">
                    <el-button size="small" @click="determine" :disabled="But">提交</el-button>
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
            form1: {
                domains: [
                    {
                        value: ''
                    }
                ],
                start: '',
                route: ''
            },
            form2: {
                domains: [
                    {
                        value: ''
                    }
                ],
                end: '',
                route: ''
            },
            start: {
                name: '',
                phone: '',
                enterprise: ''
            },
            end: {
                name: '',
                phone: '',
                enterprise: ''
            },
            disabled: false,
            delet: false,
            perfectData: {},
            startRoute: false,
            endRoute: false,

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

            this.form1.start = this.orderData.zct;
            this.form2.end = this.orderData.xct;
            this.start.name = this.orderData.tne;
            this.start.phone = this.orderData.tph;
            this.start.enterprise = this.orderData.fcom;
            this.end.name = this.orderData.shr;
            this.end.phone = this.orderData.shrp;
            this.end.enterprise = this.orderData.scom;
            this.form1.domains[0].value = this.orderData.zad;
            this.form2.domains[0].value = this.orderData.xad;
            this.form1.route = this.orderData.zadp;
            this.form2.route = this.orderData.xadp;
            if (this.form1.route) {
                this.startRoute = true;
            } else {
                this.startRoute = false;
            }
            if (this.form2.route) {
                this.endRoute = true;
            } else {
                this.endRoute = false;
            }
            // console.log(this.orderData);
            // console.log(this.newsData);

            // this.head.end = this.orderData.xct;
            // this.head.dis = this.orderData.dis;
            // this.head.start = this.orderData.zct;
        },
        //取消事件
        cancel() {
            this.editVisible = false;
        },
        //确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/20047';

            let bd = {
                tid: this.loginData.tid,
                wid: this.orderData.wid,
                zid: this.orderData.zid,
                zad: this.form1.domains[0].value,
                zadp: this.form1.route,
                xid: this.orderData.xid,
                xad: this.form2.domains[0].value,
                xadp: this.form2.route,
                fcom: this.start.enterprise,
                fhr: this.start.name,
                fhrp: this.start.phone,
                shr: this.end.name,
                shrp: this.end.phone,
                scom: this.end.enterprise
            };
            var hd = {
                pi: 20047,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/20047', hd, bd);
            let rid = res.data.hd.rid;

            if (rid >= 0) {
                this.cancel();
                this.open1('提交成功');
                this.$emit('refresh');
            } else {
                this.open2(res.data.hd.rmsg);
            }

            this.But = true;
            setTimeout(() => {
                this.But = false;
            }, 1000);
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert('submit!');
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        startAdd() {
            this.startRoute = true;
        },
        startRemove() {
            this.startRoute = false;
        },
        endAdd() {
            this.endRoute = true;
        },
        endRemove() {
            this.endRoute = false;
        },
        open1(text) {
            this.$message({
                message: text,
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
            padding: 0 10px;
            padding-bottom: 10px;
        }

        .el-footer {
            text-align: center;
            border-top: 1px solid #aaaaaa;
            background: #f8f8f8;
            line-height: 50px;
        }
    }

    .information {
        .news-noe {
            margin-top: 8px;
        }

        .news-noe {
            // padding: 10px 0px 0px 10px;
            padding: 0 0 0 0;

            .title {
                border-bottom: 2px solid #0476ce;

                .left {
                    font-size: 16px;
                    line-height: 24px;
                    border-radius: 12px 0 12px 0;
                    background: #0476ce;
                    color: #fff;
                    width: 100px;
                    text-align: center;
                }
            }

            .nav {
                /deep/.el-form-item--mini.el-form-item,
                .el-form-item--small.el-form-item {
                    margin-top: 10px;
                    margin-bottom: 0;
                }
                .line {
                    width: 90%;
                    margin: auto;
                    border: 0.5px solid rgba($color: #ccc, $alpha: 0.5);
                    margin-top: 15px;
                    margin-bottom: 5px;
                }
                .nav-center1 {
                    display: flex;
                    justify-content: center;
                    padding-right: 180px;

                    // .line-center {
                    //     margin-top: 10px;
                    //     display: flex;
                    //     align-items: center;

                    //     .name {
                    //         width: 120px;
                    //         text-align: right;
                    //         margin-right: 8px;
                    //     }
                    // }
                }
            }
        }
    }

    .el-main::-webkit-scrollbar {
        display: none;
    }
}
</style>

