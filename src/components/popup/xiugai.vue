<template>
    <div class="cancel">
        <el-dialog
            title="修改密码"
            :visible.sync="editVisible"
            width="700px"
            class="dialog"
            :close-on-click-modal="false"
        >
            <el-container>
                <el-main>
                    <div class="input">
                        <el-form ref="form" :model="form" label-width="80px">
                            <el-form-item label="旧密码">
                                <el-input v-model="form.used" type="password" show-password></el-input>
                            </el-form-item>
                            <el-form-item label="新密码">
                                <el-input type="password" v-model="form.new" show-password></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="but">
                        <el-button type="primary" @click="determine">提交</el-button>
                    </div>
                </el-main>
                <!-- <el-footer style="height:50px;">
                    <el-button size="small" @click="determine">确定</el-button>
                    <el-button size="small" @click="cancel">取消</el-button>
                </el-footer>-->
            </el-container>
        </el-dialog>
    </div>
</template>
<script>
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
import CryptoJS from 'crypto-js';
import pwdmd5 from '../../assets/js/pwdmd5.js';

export default {
    data() {
        return {
            input: '',
            editVisible: false,
            popupData: {},
            form: {
                used: '',
                new: ''
            },
            loginData: {},
            LogingType: '',
            name: '',
            hexChar: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F']
        };
    },
    created() {
        this.LogingType = this.$store.state.Login.LogingType;
        this.name = this.$store.state.Login.name;
        if (this.LogingType == 3) {
            this.loginData = this.$store.state.Login.DispatchData;
            this.show1 = false;
            this.show2 = false;
        }
        if (this.LogingType == 4) {
            this.loginData = this.$store.state.Login.CustomerData;
            this.show1 = true;
            this.show2 = true;
        }
    },
    methods: {
        //点击弹窗事件
        receipt(index, row) {
            this.editVisible = true;
        },
        //取消事件
        cancel() {
            this.editVisible = false;
        },
        //确定事件
        async determine() {
            let action = this.$store.state.Login.url + '/10004';

            if (this.form.used.length == 0) {
                this.open3('旧密码不能为空');
            }
            if (this.form.new.length == 0) {
                this.open3('新密码不能为空');
            } else {
                var curTime = new Date().getTime();
                var oldPassword = pwdmd5(this.name + '_' + this.form.used);
                var newPassword = pwdmd5(this.name + '_' + this.form.new);
                oldPassword = oldPassword.toString();
                newPassword = newPassword.toString();

                console.log(oldPassword);
                console.log(newPassword);
                let bd = {
                    tid: this.loginData.tid,
                    pwdty: 2,
                    vt: 1,
                    sc: '',
                    opwd: oldPassword,
                    npwd: newPassword
                };
                var hd = {
                    pi: 10004,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/10004', hd, bd);
                let rid = res.data.hd.rid;
                if (rid >= 0) {
                    this.$emit('logout');
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
            this.$message.error('提交失败：' + text);
        },
        open3(text) {
            this.$message.error(text);
        },

        mounted() {}
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
        height: 200px;

        .el-main {
            flex: 1;
            padding: 0;
            padding-bottom: 10px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: space-around;
            padding: 20px 0;
            .title {
                font-size: 18px;
            }
        }

        .el-footer {
            text-align: right;
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

