<template>
    <div class="addCustom">
        <el-dialog
            :title="cTitle"
            :visible.sync="dialogVisible"
            width="628px"
            class="a_cus"
            :close-on-click-modal="false"
        >
            <el-form :model="form" label-width="120px" size="mini">
                <el-form-item
                    label="客户名称"
                    prop="ne"
                    :rules="[
                        { required: true, message: '客户名称不能为空'}
                    ]"
                    :show-message="isShow"
                >
                    <el-input v-model="form.ne" maxlength="16" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item
                    label="客户电话"
                    prop="ph"
                    :rules="[
                            { required: true, message: '客户电话不能为空'}
                        ]"
                    :show-message="isShow"
                >
                    <el-input
                        v-model="form.ph"
                        maxlength="11"
                        :disabled="isDisabled"
                        autocomplete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="企业名称" prop="com">
                    <el-input v-model="form.com" maxlength="32"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import request from '../../assets/js/request.js';
import encryptUtil from '../../assets/js/encryptUtil.js';
export default {
    data() {
        return {
            cTitle: '添加客户',
            dialogVisible: false,
            isDisabled: false,
            isShow: false,
            form: {
                ne: '',
                ph: '',
                com: '',
                idc: '',
                idpc: ''
            },
            loginData: '',
            sk: ''
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.CustomerData;
    },
    components: {},
    methods: {
        a_open() {
            this.isDisabled = false;
            this.dialogVisible = true;
            this.cTitle = '添加客户';
            for (var item in this.form) {
                this.form[item] = '';
            }
        },
        async saveEdit() {
            // console.log(this.form);
            if (!this.form.ne || !this.form.ph) {
                this.$alert('客户名称、客户电话不能为空！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (!this.form.ne.trim() || !this.form.ph.trim()) {
                this.$alert('客户名称、客户电话不能为空！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (this.form.ne.length < 2) {
                this.$alert('客户名称长度（2-16位中文，字母，数字）！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (this.form.ph.length != 11) {
                this.$alert('请输入正确的电话号码！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            if (!/^1[3|4|5|7|8]\d{9}$/.test(this.form.ph)) {
                this.$alert('请输入正确的电话号码！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }

            this.dialogVisible = false;

            let action = `${this.$store.state.Login.url}/40022`;
            // this.loginData = this.$store.state.Login.CustomerData;
            var bd = {
                tid: this.loginData.tid,
                ne: this.form.ne,
                ph: this.form.ph,
                idc: this.form.idc,
                idpc: this.form.idpc,
                com: this.form.com
            };
            let hd = {
                pi: 40022,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/40022', hd, bd);
            // console.log(res);
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
                if (data.st == 1) {
                    this.$alert(`已在${data.mcd}运营商注册`, '提示', {
                        confirmButtonText: '确定',
                        callback: action => {}
                    });
                }
                this.$emit('getData');
            } else {
                this.$message({
                    message: res.data.hd.rmsg,
                    type: 'error',
                    duration: 900
                });
            }
            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 40022,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         if (res.data.hd.rid >= 0) {
            //             // for (var item in this.form) {
            //             //     this.form[item] = '';
            //             // }

            //             if (JSON.parse(res.data.bd).st == 1) {
            //                 this.$alert(`已在${JSON.parse(res.data.bd).mcd}运营商注册`, '提示', {
            //                     confirmButtonText: '确定',
            //                     callback: action => {}
            //                 });
            //             }
            //             this.$emit('getData');
            //             // console.log(res.data);
            //         } else {
            //             this.$message({
            //                 type: 'info',
            //                 message: res.data.hd.rmsg
            //             });
            //             // for (var item in this.form) {
            //             //     this.form[item] = '';
            //             // }
            //         }
            //     });
        },
        changeInfo(row) {
            this.cTitle = '修改客户';
            this.isDisabled = true;
            this.dialogVisible = true;
            // console.log(row);
            // this.form = row;
            this.form.ne = row.ne;
            this.form.ph = row.ph;
            this.form.com = row.com;
        },
        cancelAdd() {
            for (var item in this.form) {
                this.form[item] = '';
            }
            this.dialogVisible = false;
        },
        //XX
        handleClose() {
            this.cancelAdd();
        }
    }
};
</script>

<style scoped lang="scss">
.addCustom {
    .a_cus {
        .title {
            border-bottom: 2px solid #409eff;
            margin: 20px 0;
            .left {
                font-size: 14px;
                line-height: 24px;
                border-radius: 12px 0 12px 0;
                background: #409eff;
                color: #fff;
                width: 100px;
                text-align: center;
            }
        }
        /deep/ .el-form-item--mini.el-form-item,
        .el-form-item--small.el-form-item {
            margin-right: 25px;
            margin-bottom: 15px;
            margin-top: 25px;
        }
        /deep/ .el-dialog__header {
            padding: 6px 10px;
            background: #409eff;
            .el-dialog__title {
                color: #fff;
                font-size: 14px;
            }
            .el-dialog__headerbtn .el-dialog__close {
                color: #fff;
            }
            .el-dialog__headerbtn {
                top: 10px;
            }
        }
        /deep/ .el-input__inner {
            width: 178px;
        }

        /deep/ .el-dialog__body {
            padding: 25px 15px;
            padding-left: 120px;
        }
        /deep/ .el-dialog__footer {
            padding: 12px 20px;
            background-color: #f8f8f8;
        }
    }
}
</style>
