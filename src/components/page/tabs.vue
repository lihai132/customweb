<template>
    <div class="new">
        <div class="container">
            <el-tabs v-model="message" class="tabs">
                <el-tab-pane :label="`交易信息`" name="first">
                    <el-form ref="form" :model="form" label-width="0px">
                        <el-form-item label>
                            <el-date-picker
                                v-model="form.time1"
                                type="date"
                                placeholder="选择日期"
                                value-format="MM/dd"
                            ></el-date-picker>
                            <el-input
                                v-model="form.search1"
                                style="width:220px;margin-left:20px;margin-right:20px;"
                                placeholder="请输入需要搜索的内容"
                            ></el-input>
                            <el-button type="primary" size="small">搜索</el-button>
                        </el-form-item>
                        <!-- <el-form-item label="">
                            <el-input v-model="form.time" style="width:220px;"></el-input>
                            <el-button type="primary" size="small">搜索</el-button>
                        </el-form-item>-->
                    </el-form>
                    <el-table :data="unread" style="width: 100%">
                        <!-- <el-table-column>
                            <template slot-scope="scope">
                                <span class="message-title">{{scope.row.title}}</span>
                            </template>
                        </el-table-column>-->
                        <el-table-column label="序号" prop="ctt.index" width="50" align="center"></el-table-column>
                        <el-table-column
                            label="消息类型"
                            prop="ctt.newsType"
                            width="180"
                            align="center"
                        ></el-table-column>
                        <el-table-column label="订单/运单号" prop="ctt.cd" width="180" align="center"></el-table-column>
                        <el-table-column label="操作类型" prop="ctt.opt" width="180" align="center"></el-table-column>

                        <el-table-column label="状态更新时间" prop="ctt.utm" align="center"></el-table-column>
                        <el-table-column label="状态" prop="ctt.rf" width="180" align="center">
                            <template></template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane :label="`系统公告`" name="second">
                    <el-form ref="form" :model="form" label-width="0px">
                        <el-form-item label>
                            <el-date-picker
                                v-model="form.time2"
                                type="date"
                                placeholder="选择日期"
                                value-format="MM/dd"
                            ></el-date-picker>
                            <el-input
                                v-model="form.search2"
                                style="width:220px;margin-left:20px;margin-right:20px;"
                                placeholder="请输入需要搜索的内容"
                            ></el-input>
                            <el-button type="primary" size="small">搜索</el-button>
                        </el-form-item>
                        <!-- <el-form-item label="">
                            <el-input v-model="form.time" style="width:220px;"></el-input>
                            <el-button type="primary" size="small">搜索</el-button>
                        </el-form-item>-->
                    </el-form>
                    <template v-if="message === 'second'">
                        <el-table :data="read" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span class="message-title">{{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="150"></el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
export default {
    name: 'tabs',
    data() {
        return {
            message: 'first',
            showHeader: false,
            unread: [
                // {
                //     date: '2018-04-19 20:00:00',
                //     title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
                // },
                // {
                //     date: '2018-04-19 21:00:00',
                //     title: '今晚12点整发大红包，先到先得'
                // }
            ],
            read: [
                // {
                //     date: '2018-04-19 20:00:00',
                //     title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
                // }
            ],
            recycle: [
                {
                    date: '2018-04-19 20:00:00',
                    title: '【系统通知】该系统将于今晚凌晨2点到5点进行升级维护'
                }
            ],
            form: {
                time1: '',
                search1: '',
                time2: '',
                search2: ''
            },
            newsList: [],
            page: '',
            LogingType: '',
            loginData: {},
            sk: '',
            url: '',
            idStr: []
        };
    },
    mounted() {
        this.sk = this.$store.state.Login.key;
        this.page = this.$store.state.newsList.page;
        this.LogingType = this.$store.state.Login.LogingType;

        if (this.LogingType == 3) {
            this.loginData = this.$store.state.Login.DispatchData;
        }
        if (this.LogingType == 4) {
            this.loginData = this.$store.state.Login.CustomerData;
        }
        this.getList();
        // this.getList();
        // 31011   40024
    },

    methods: {
        handleRead(index) {
            const item = this.unread.splice(index, 1);
            console.log(item);
            this.read = item.concat(this.read);
        },
        handleDel(index) {
            const item = this.read.splice(index, 1);
            this.recycle = item.concat(this.recycle);
        },
        handleRestore(index) {
            const item = this.recycle.splice(index, 1);
            this.read = item.concat(this.read);
        },

        async getList() {
            let action = this.$store.state.Login.url + '/31011';
            let bd = {
                tid: this.loginData.tid,
                bte: '',
                ete: '',
                pg: 1,
                sz: 200
            };
            let hd = {
                pi: 31011,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/31011', hd, bd);
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
                let tableData = data.olst;
                let list = [];
                console.log(tableData[0]);
                tableData.forEach((item, index) => {
                    item.ctt = JSON.parse(item.ctt);
                    if (item.pid == 50001) {
                        item.ctt.cd = item.ctt.ocd;
                        if (item.ctt.opt == 1) {
                            item.ctt.opt = '新增订单';
                        }
                        if (item.ctt.opt == 2) {
                            item.ctt.opt = '客服接单';
                        }

                        if (item.ctt.opt == 3) {
                            item.ctt.opt = '指派调度';
                        }
                        if (item.ctt.opt == 4) {
                            item.ctt.opt = '撤销指派';
                        }
                        if (item.ctt.opt == 5) {
                            item.ctt.opt = '调度接单';
                        }
                        if (item.ctt.opt == 6) {
                            item.ctt.opt = '调度派车';
                        }
                        if (item.ctt.opt == 7) {
                            item.ctt.opt = '派车完成';
                        }
                        if (item.ctt.opt == 8) {
                            item.ctt.opt = '撤销订单';
                        }
                    }
                    if (item.pid == 50002) {
                        item.ctt.cd = item.ctt.wcd;
                        if (item.ctt.opt == 1) {
                            item.ctt.opt = '调度派车';
                        }
                        if (item.ctt.opt == 2) {
                            item.ctt.opt = '签约运单';
                        }

                        if (item.ctt.opt == 3) {
                            item.ctt.opt = '签约完成';
                        }
                        if (item.ctt.opt == 4) {
                            item.ctt.opt = '运费支付申请';
                        }
                        if (item.ctt.opt == 5) {
                            item.ctt.opt = '运费支付完成';
                        }
                        if (item.ctt.opt == 6) {
                            item.ctt.opt = '确认收货';
                        }
                        if (item.ctt.opt == 7) {
                            item.ctt.opt = '关闭运单';
                        }
                        if (item.ctt.opt == 8) {
                            item.ctt.opt = '撤销运单';
                        }
                    }
                    item.ctt.id = item.id;
                    item.ctt.index = index + 1;
                    item.ctt.tid = item.tid;
                    item.ctt.te = item.te;
                    item.ctt.newsType = item.pid == 50001 ? '订单信息' : '运单信息';
                    item.ctt.pid = item.pid;
                    item.ctt.ctm = item.ctm;
                    if (item.rf == 0) {
                        this.idStr.push(item.id);
                    }
                    item.ctt.rf = item.rf == 0 ? '未读' : '已读';

                    list.push(item.ctt);
                });

                this.unread = tableData;
                if (this.idStr.length > 0) {
                    setTimeout(() => {
                        this.readNews();
                    }, 3000);
                }
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        async readNews() {
            let action = this.$store.state.Login.url + '/40024';
            let nid = '';
            this.idStr.map((item) => {
                nid += item + ',';
            });
            nid = nid.substring(0, nid.length - 1);
            // console.log(mid)
            let bd = {
                tid: this.loginData.tid,
                nid: nid
            };
            let hd = {
                pi: 40024,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/40024', hd, bd);
            let rid = res.data.hd.rid;

            if (rid >= 0) {
                // let data = {};
                // if (this.loginData.em == 0) {
                //     data = JSON.parse(res.data.bd);
                // }
                // if (this.loginData.em == 1) {
                //     let decryptData = encryptUtil.decrypt(res.data.bd, this.sk);
                //     data = JSON.parse(decryptData);
                // }
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        open1(text) {
            this.$message({
                showClose: true,
                message: text,
                type: 'success',
                duration: 900
            });
        },
        open2(text) {
            this.$message({
                showClose: true,
                message: text,
                type: 'error',
                duration: 900
            });
        }
    },
    // updated() {
    //     this.getList();
    //     return;
    // },
    computed: {
        unreadNum() {
            return this.unread.length;
        }
    },
    watch: {
        '$store.state.newsList.time': function (newVal) {
            // console.log(111);
            // this.unread = this.$store.state.newsList.List;
            this.getList();
        }
    }
};
</script>

<style lang="scss" scoped>
.message-title {
    cursor: pointer;
}
.handle-row {
    margin-top: 30px;
}
.new {
    height: 100%;
    .container {
        height: 90%;
        .tabs {
            /deep/ .el-tabs__header {
                /deep/.el-tabs__nav-wrap {
                    .el-tabs__nav-scroll {
                        padding: 0 42%;
                    }
                }
            }
        }
    }
}
</style>

