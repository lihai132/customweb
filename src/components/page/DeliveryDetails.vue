<template>
    <div>
        <div class="top">
            <el-date-picker
                v-model="sTime"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions"
                :default-time="['00:00:00', '23:59:59']"
                value-format="yyyy-MM-dd"
                style="margin:0 0 0px 0px;"
            ></el-date-picker>
            <el-button type="success" style="margin:0 0 0px 20px;" @click="getData">查 询</el-button>

            <el-input
                v-model="value"
                placeholder="货物名称/起运地/目的地"
                style="margin:0 0 0px 20px; width:300px"
            ></el-input>
            <el-button type="success" style="margin:0 0 0px 20px;" @click="getData">查 询</el-button>
        </div>
        <div class="container">
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                height="600"
                :fit="true"
                :header-cell-style="{background:'#ff0000',color:'#606266'}"
            >
                <el-table-column prop="oid" label="编号" width="100"></el-table-column>
                <el-table-column prop="ocd" label="订单号" width="120"></el-table-column>
                <el-table-column prop="st" label="订单状态" width="120"></el-table-column>
                <el-table-column prop="zad" label="起运地" width="180"></el-table-column>
                <el-table-column prop="xad" label="目的地" width="180"></el-table-column>
                <el-table-column prop="on" label="货物名称" width="150"></el-table-column>
                <el-table-column prop="dw" label="重量体积" width="120"></el-table-column>
                <el-table-column prop="dis" label="距离" width="100"></el-table-column>
                <el-table-column prop="qyf" label="期望运费" width="100"></el-table-column>
                <el-table-column prop="zdy" label="装货日期" width="110"></el-table-column>
                <el-table-column prop="ctm" label="创建时间" width="170"></el-table-column>
                <el-table-column label="操作" width>
                    <template slot-scope="scope">
                        <el-button type="success" size="small">再来一单</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
export default {
    data() {
        return {
            pickerOptions: {
                shortcuts: [
                    {
                        text: '今天',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            // start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '昨天',
                        onClick(picker) {
                            // const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1);
                            picker.$emit('pick', [start, start]);
                        }
                    },
                    {
                        text: '最近七天',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    // {
                    //     text: '上周',
                    //     onClick(picker) {
                    //         const end = new Date();
                    //         const start = new Date();
                    //         start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    //         picker.$emit('pick', [start, end]);
                    //     }
                    // },
                    {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '上月',
                        onClick(picker) {
                            let date = new Date();
                            let year = date.getFullYear();
                            let month = date.getMonth();
                            const start = new Date(year, month - 1, '01');
                            let day = new Date(year, month, 0).getDate();
                            const end = new Date(year, month - 1, day);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '本月',
                        onClick(picker) {
                            let date = new Date();
                            let year = date.getFullYear();
                            let month = date.getMonth();
                            const start = new Date(year, month, '01');
                            const end = new Date();
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }
                ]
            },
            tableData: [],
            sTime: '',
            value: ''
            // downloadLoading: false
        };
    },
    created() {
        this.getData();
    },
    methods: {
        //发货明细
        getData() {
            if (!this.sTime) {
                this.sTime = '';
            }

            let action = 'http://192.168.0.66:9010/31010';
            this.loginData = JSON.parse(this.$store.state.Login.loginData);

            var bd = {
                tid: this.loginData.tid,
                bte: this.sTime[0] || '2019-04-01',
                ete: this.sTime[1] || '2020-05-01',
                pg: 1,
                sz: 100
            };
            // console.log(this.loginData.tid, this.phone,this.loginData.si);
            this.$axios
                .post(action, {
                    hd: {
                        pi: 31010,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let data = JSON.parse(res.data.bd);
                    this.tableData = data.olst;
                    // console.log(res.data);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }
};
</script>

<style>
/* .el-table--small td {
    padding: 2px 0;
} */
.el-date-range-picker.has-sidebar {
    left: 250px !important;
}
</style>

<style lang="scss" scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
.top {
    display: flex;
    padding-bottom: 10px;
    .search {
        display: flex;
        width: 300px;
        justify-content: space-between;
        align-items: center;
        margin-right: 30px;
        .el-input {
            width: 200px;
        }
    }
}
</style>