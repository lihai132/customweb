<template>
    <div class="cargoPhoto">
        <el-dialog
            title="指派调度"
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
                    <div class="search">
                        <el-form
                            :model="form"
                            :rules="rules"
                            ref="ruleForm"
                            class="form"
                            style="margin-bottom:15px"
                        >
                            <el-form-item prop="input" style="width:360px;margin:auto">
                                <el-input
                                    v-model="form.input"
                                    placeholder="请输入内容"
                                    style="width:290px"
                                    :minlength="2"
                                    :maxlength="16"
                                ></el-input>

                                <el-button
                                    type="success"
                                    plain
                                    @click="getSearch"
                                    :disabled="But1"
                                    style="margin-left:10px"
                                >搜索</el-button>
                            </el-form-item>
                        </el-form>
                        <!-- <el-input v-model="input" placeholder="请输入姓名或手机号码" style="width:200px"></el-input>
                        <el-button type="success" plain @click="getSearch" :disabled="But1">搜索</el-button>-->
                    </div>
                    <div class="tabs searchTable" v-if="show2">
                        <el-table
                            :data="searchTable"
                            border
                            class="table"
                            ref="multipleTable"
                            header-cell-class-name="table-header"
                            highlight-current-row
                            :show-header="false"
                        >
                            <el-table-column prop="type" label="类型" width="80" align="center"></el-table-column>
                            <el-table-column prop="dne" label="名称" width="80" align="center"></el-table-column>
                            <el-table-column prop="dph" label="联系方式"></el-table-column>
                        </el-table>

                        <div class="service" v-if="show3">
                            <div class="title">服务费：</div>
                            <div class="num">
                                <el-input v-model="price" placeholder="请输入"></el-input>
                            </div>
                            <div class="text">元</div>
                        </div>
                    </div>
                    <el-tabs
                        v-model="activeName"
                        @tab-click="handleClick"
                        class="tabs"
                        v-if="show1"
                    >
                        <el-tab-pane label="调度" name="first">
                            <div class="passerby">
                                <el-table
                                    :data="dispatcher"
                                    border
                                    class="table"
                                    ref="multipleTable"
                                    header-cell-class-name="table-header"
                                    highlight-current-row
                                    @row-click="tableClick"
                                    align="center"
                                    :show-overflow-tooltip="true"
                                >
                                    <el-table-column width="60" align="center">
                                        <template slot-scope="scope">
                                            <el-checkbox v-model="scope.row.checked"></el-checkbox>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                        prop="dne"
                                        label="名称"
                                        width="80"
                                        align="center"
                                        :show-overflow-tooltip="true"
                                    ></el-table-column>
                                    <el-table-column prop="dph" label="联系方式" align="center"></el-table-column>
                                </el-table>
                            </div>
                        </el-tab-pane>
                        <!-- <el-tab-pane label="经纪人" name="second">
                            <el-table
                                :data="agent"
                                border
                                class="table"
                                ref="multipleTable"
                                header-cell-class-name="table-header"
                                highlight-current-row
                                height="355"
                                @row-click="tableClick"
                            >
                                <el-table-column width="60" align="center">
                                    <template slot-scope="scope">
                                        <el-checkbox v-model="scope.row.checked"></el-checkbox>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    prop="dne"
                                    label="名称"
                                    width="80"
                                    align="center"
                                    :show-overflow-tooltip="true"
                                ></el-table-column>
                                <el-table-column
                                    prop="dph"
                                    label="联系方式"
                                    align="center"
                                    :show-overflow-tooltip="true"
                                ></el-table-column>
                            </el-table>
                        </el-tab-pane>-->
                    </el-tabs>
                </el-main>
                <el-footer style="height:50px;">
                    <el-button size="small" style="width:100px;" @click="assign" :disabled="But2">指派</el-button>
                    <el-button size="small" @click="cancel" style="width:100px;">取消</el-button>
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
            But1: false,
            But2: false,
            show1: true,
            show2: false,
            show3: false,
            head: {
                start: '',
                end: '',
                dis: ''
            },
            popupData: {},
            sk: '',
            loginData: {},
            activeName: 'first',
            dialogVisible: false,
            dispatcher: [],
            agent: [],
            form: {
                input: ''
            },

            popupData: {},
            searchTable: [],
            price: 0,
            row: {},
            searchType: 3,
            rules: {
                input: [
                    { required: true, message: '请输入姓名或者手机号码', trigger: 'blur' },
                    { min: 2, max: 11, message: '长度在 2 到 16 个字符', trigger: 'blur' }
                ]
            }
        };
    },
    created() {},
    methods: {
        //点击弹窗事件
        receipt(index, row) {
            this.editVisible = true;
            this.popupData = this.$store.state.Popup.popup2;
            this.sk = this.$store.state.Login.key;
            this.loginData = this.$store.state.Login.CustomerData;
            this.head.start = this.popupData.start;
            this.head.end = this.popupData.end;
            this.head.dis = this.popupData.dis;
            this.getData1();
            this.getData2();
        },
        //请求调度员数据的方法
        async getData1() {
            let action = this.$store.state.Login.url + '/30064';
            let bd = {
                tid: this.popupData.tid,
                te: 3,
                ss: '',
                pg: 1,
                sz: 200
            };
            let hd = {
                pi: 30064,
                si: this.popupData.si,
                sq: 9
            };

            let res = await request('/30064', hd, bd);
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
                let tableList = data.olst;
                tableList.forEach(item => {
                    item.checked = false;

                    if (item.te == 3) {
                        item.type = '调度员';
                    }
                    if (item.te == 5) {
                        item.type = '经纪人';
                    }
                });
                this.dispatcher = tableList;
                console.log(this.dispatcher);
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //请求经纪人数据的方法
        async getData2() {
            let action = this.$store.state.Login.url + '/30064';
            let bd = {
                tid: this.popupData.tid,
                te: 5,
                ss: '',
                pg: 1,
                sz: 200
            };
            let hd = {
                pi: 30064,
                si: this.popupData.si,
                sq: 9
            };

            let res = await request('/30064', hd, bd);
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
                let tableList = data.olst;
                tableList.forEach(item => {
                    item.checked = false;
                    if (item.te == 3) {
                        item.type = '调度员';
                    }
                    if (item.te == 5) {
                        item.type = '经纪人';
                    }
                });
                this.agent = tableList;
                console.log(this.agent);
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        //搜索方法
        async getSearch() {
            let action = this.$store.state.Login.url + '/30064';
            let bd = {
                tid: this.popupData.tid,
                te: this.searchType,
                ss: this.form.input,
                pg: 1,
                sz: 200
            };
            let hd = {
                pi: 30064,
                si: this.popupData.si,
                sq: 9
            };

            let res = await request('/30064', hd, bd);
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

                let tableList = data.olst;

                if (tableList.length == 0) {
                    if (this.searchType == 3) {
                        this.dispatcher = [];
                    }
                    if (this.searchType == 5) {
                        this.agent = [];
                    }
                } else {
                    if (this.searchType == 3) {
                        tableList.forEach(item => {
                            item.checked = false;
                            item.type = '调度员';
                        });
                        this.dispatcher = tableList;
                    }
                    if (this.searchType == 5) {
                        item.checked = false;
                        tableList.forEach(item => {
                            item.type = '经纪人';
                        });
                        this.agent = tableList;
                    }
                }
            } else {
                this.open2(res.data.hd.rmsg);
            }

            this.But1 = true;
            setTimeout(() => {
                this.But1 = false;
            }, 1000);
        },
        //取消事件
        cancel() {
            this.editVisible = false;
            this.row = {};
            this.form.input = '';
        },
        //确定事件
        async assign() {
            let action = this.$store.state.Login.url + '/20038';
            if (this.searchTable.length == 0 && JSON.stringify(this.row) == '{}') {
                this.open2('请选择调度');
            }
            if (this.searchTable.length > 0) {
                this.row = {};
                let bd = {
                    tid: this.popupData.tid,
                    oid: this.popupData.oid,
                    td: this.searchTable[0].did,
                    cch: this.price
                };
                let hd = {
                    pi: 20038,
                    si: this.popupData.si,
                    sq: 9
                };

                let res = await request('/20038', hd, bd);
                let rid = res.data.hd.rid;

                if (rid >= 0) {
                    this.cancel();
                    this.open1('指派成功');
                    this.$emit('refresh');
                    this.row = {};
                    this.searchTable = [];
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
            if (this.searchTable.length == 0 && JSON.stringify(this.row) != '{}') {
                let bd = {
                    tid: this.popupData.tid,
                    oid: this.popupData.oid,
                    td: this.row.did,
                    cch: this.price
                };
                let hd = {
                    pi: 20038,
                    si: this.popupData.si,
                    sq: 9
                };

                let res = await request('/20038', hd, bd);
                let rid = res.data.hd.rid;

                if (rid >= 0) {
                    this.cancel();
                    this.open1('指派成功');
                    this.$emit('refresh');
                    this.row = {};
                    this.searchTable = [];
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
            if (this.searchTable.length > 0 && JSON.stringify(this.row) != '{}') {
                let bd = {
                    tid: this.popupData.tid,
                    oid: this.popupData.oid,
                    td: this.searchTable[0].did,
                    cch: this.price
                };
                let hd = {
                    pi: 20038,
                    si: this.popupData.si,
                    sq: 9
                };

                let res = await request('/20038', hd, bd);
                let rid = res.data.hd.rid;

                if (rid >= 0) {
                    this.cancel();
                    this.open1('指派成功');
                    this.$emit('refresh');
                    this.row = {};
                    this.searchTable = [];
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
            this.But2 = true;
            setTimeout(() => {
                this.But2 = false;
            }, 1000);
        },
        handleClick(tab, event) {
            // console.log(tab.name);
            if (tab.name == 'first') {
                this.searchType = 3;
            }
            if (tab.name == 'second') {
                this.searchType = 5;
            }
        },
        //表格点击事件
        tableClick(row) {
            this.row = row;
            if (this.searchType == 3) {
                this.dispatcher.forEach(item => {
                    if (item.did == row.did) {
                        item.checked = true;
                    } else {
                        item.checked = false;
                    }
                });
            }
            if (this.searchType == 5) {
                this.agent.forEach(item => {
                    if (item.did == row.did) {
                        item.checked = true;
                    } else {
                        item.checked = false;
                    }
                });
            }
        },
        open1(text) {
            this.$message({
                message: text,
                type: 'success',
                duration: 900
            });
        },
        open2(text) {
            // this.$message.error(text);
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

    /deep/.el-table__empty-block {
        min-height: 61px !important;
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
        height: 665px;

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
            .searchTable {
                padding-top: 10px;
                .service {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    margin-right: 73px;
                    margin-top: 10px;
                    .text {
                        margin-left: 8px;
                    }
                }
            }
            .tabs {
                //    padding: 0 40%;
                /deep/ .el-tabs__nav-scroll {
                    overflow: hidden;
                    padding: 0 48%;
                }
                .table {
                    width: 90%;
                    margin: auto;
                }
                /deep/.el-tabs__content {
                    height: 358px;
                    // overflow: scroll;
                    padding-bottom: 10px;
                    /deep/.el-tabs__content::-webkit-scrollbar {
                        display: none;
                    }
                    // /deep/.el-table__body-wrapper {
                    //     overflow: scroll;
                    // }
                    // /deep/.el-table__body-wrapper::-webkit-scrollbar {
                    //     display: none;
                    // }
                }
            }
            .search {
                text-align: center;
                margin-top: 15px;
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

