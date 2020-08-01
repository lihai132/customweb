<template>
    <div class="customerList">
        <el-dialog
            title="选择客户"
            :visible.sync="dialogFormVisible"
            width="650px"
            class="listDialog"
            :close-on-click-modal="false"
            :before-close="handleClose"
        >
            <div style="display:flex;">
                <el-input placeholder="请输入客户电话或者姓名" v-model="phone" clearable style="width:300px;"></el-input>
                <el-button type="success" @click="getData" style="margin-left:30px;">查询</el-button>
            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                height="600"
                :fit="true"
                highlight-current-row
                @current-change="rowCurrentChange"
                :header-cell-style="{background:'#ff0000',color:'#606266'}"
            >
                <el-table-column type="index" width="50"></el-table-column>
                <el-table-column prop="ne" label="名称" width="100" show-overflow-tooltip></el-table-column>
                <el-table-column prop="ph" label="电话" width="120"></el-table-column>
                <el-table-column prop="com" label="企业名称" min-width="200" show-overflow-tooltip></el-table-column>
                <el-table-column prop="st" label="客户状态" width="110" :formatter="stateFormat"></el-table-column>
                <!-- <el-table-column prop="ctm" label="创建时间" width show-overflow-tooltip></el-table-column> -->
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelAdd">取 消</el-button>
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
            dialogFormVisible: false,
            phone: '',
            tableData: [],
            loginData: '',
            sk: '',
            currentRow: null,
            selectRow: {}
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.CustomerData;
    },
    components: {},
    methods: {
        open() {
            this.dialogFormVisible = true;
            this.getData();
        },
        handleClose() {
            this.cancelAdd();
        },
        cancelAdd() {
            this.dialogFormVisible = false;
        },
        //获取我的客户数据
        async getData() {
            let action = `${this.$store.state.Login.url}/30079`;
            // this.loginData = this.$store.state.Login.CustomerData;

            var bd = {
                tid: this.loginData.tid,
                ss: this.phone,
                im: 1,
                pg: 1,
                sz: 500
            };
            let hd = {
                pi: 30079,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30079', hd, bd);
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
                this.tableData = data.olst;
                this.totalNumber = data.pg.tn;
            } else {
                this.$message({
                    message: res.data.hd.rmsg,
                    type: 'error',
                    duration: 900
                });
            }
            // console.log(this.loginData.tid, this.phone,this.loginData.si);
            // this.$axios
            //     .post(action, {
            //         hd: {
            //             pi: 30079,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         let rid = res.data.hd.rid;
            //         if (rid >= 0) {
            //             let data = JSON.parse(res.data.bd);
            //             this.tableData = data.olst;
            //             // this.totalNumber = data.pg.tn;
            //             // console.log(res.data.bd);
            //         } else {
            //             this.open2(res.data.hd.rmsg);
            //         }
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
        },
        saveEdit() {
            if (!this.currentRow) {
                this.$alert('请选择客户！', '提示', {
                    confirmButtonText: '确定',
                    callback: function() {}
                });
                return;
            }
            this.selectRow = this.currentRow;
            this.$emit('setRowData', this.selectRow);
            // console.log(this.currentRow);
            this.dialogFormVisible = false;
        },
        //选中
        rowCurrentChange(val) {
            this.currentRow = val;
        },
        stateFormat(row) {
            if (row.st == 3) {
                return '正常';
            } else {
                return '已登记';
            }
        }
    }
};
</script>

<style scoped lang="scss">
.customerList {
    .listDialog {
        .table {
            margin-top: 25px;
            /deep/ .el-table__body tr.current-row > td {
                background-color: #d8e5f4;
            }
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
        /deep/ .el-dialog__body {
            padding: 25px 15px;
        }
        /deep/ .el-dialog__footer {
            padding: 12px 20px;
            background-color: #f8f8f8;
        }
    }
}
</style>
