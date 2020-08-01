<template>
<!-- 参考行情 -->
    <div>
        <div class="top">
            <div class="start">
                <div class="title">起运地</div>
                <div class="block">
                    <el-col :span="20">
                        <el-autocomplete
                            class="inline-input"
                            v-model="state1"
                            :fetch-suggestions="querySearch"
                            placeholder="请输入内容"
                            :trigger-on-focus="false"
                            @select="startChang"
                        ></el-autocomplete>
                    </el-col>
                </div>
            </div>
            <div class="end">
                <div class="title">目的地</div>
                <div class="block">
                    <el-col :span="20">
                        <el-autocomplete
                            class="inline-input"
                            v-model="state2"
                            :fetch-suggestions="querySearch"
                            placeholder="请输入内容"
                            :trigger-on-focus="false"
                            @select="endChang"
                        ></el-autocomplete>
                    </el-col>
                </div>
            </div>
            <div class="but">
                <el-button type="success" @click="getData">查询</el-button>
                <el-button type="success" @click="getData">刷新</el-button>
            </div>
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
                :header-cell-style="{background:'#eef1f6',color:'#606266'}"
            >
                <el-table-column prop="zid" label="起运地" width="220"></el-table-column>
                <el-table-column prop="xid" label="目的地" width="220"></el-table-column>
                <el-table-column prop="on" label="货物名称" width="150"></el-table-column>
                <el-table-column prop="qt" label="成交价" width="120"></el-table-column>
                <el-table-column prop="dis" label="距离(km)" width="120"></el-table-column>
                <el-table-column prop="dw" label="重量(吨)" width="120"></el-table-column>
                <el-table-column prop="dv" label="体积(方)" width="120"></el-table-column>
                <el-table-column prop="te" label="运输类型"></el-table-column>
                <el-table-column prop="ct" label="车型"></el-table-column>
                <el-table-column prop="cl" label="车长"></el-table-column>
                <el-table-column prop="ctm" label="成交时间" width="200"></el-table-column>
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
            query: {
                address: '',
                name: '',
                pageIndex: 1,
                pageSize: 10
            },
            tableData: [],
            options: [],
            loginData: {},
            radio: '1',

            state1: '',
            state2: '',
            restaurants: [],
            startId: '',
            endId: ''
        };
    },
    created() {
        this.getData();
    },
    methods: {
        //获取行情数据
        getData() {
            let action = `${this.$store.state.Login.url}/30041`;
            this.loginData = JSON.parse(this.$store.state.Login.loginData);

            var bd = {
                tid: this.loginData.tid,
                zid: this.startId,
                xid: this.endId,
                pg: 1,
                sz: 100
            };

            this.$axios
                .post(action, {
                    hd: {
                        pi: 30041,
                        si: this.loginData.si,
                        sq: 8
                    },
                    bd: bd
                })
                .then(res => {
                    let data = JSON.parse(res.data.bd);
                    let tableData = data.olst;
                    this.startProvinceChange(tableData);
                })
                .catch(error => {
                    console.log(error);
                });
        },

        startProvinceChange(tableData) {
            let action = `${this.$store.state.Login.url}/31009`;
            this.loginData = JSON.parse(this.$store.state.Login.loginData);

            var storage = window.localStorage;
            let AddressList = storage.AddressList;

            if (AddressList) {
                this.restaurants = JSON.parse(AddressList);

                tableData.forEach(item => {
                    this.restaurants.forEach(e => {
                        if (item.zid === e.id) {
                            item.zid = e.value;
                        }
                        if (item.xid === e.id) {
                            item.xid = e.value;
                        }
                    });
                });
                this.tableData = tableData;
            } else {
                var bd = {
                    tid: this.loginData.tid,
                    vn: '20190101'
                };
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 31009,
                            sq: 8,
                            si: this.loginData.si
                        },
                        bd: bd
                    })
                    .then(res => {
                        let assetsData = JSON.parse(res.data.bd);

                        let assetsList = assetsData.olst;
                        assetsList.map(item => {
                            this.restaurants.push({ value: item.mne, address: item.ne, id: item.id });
                        });
                        storage.AddressList = JSON.stringify(this.restaurants);
                        tableData.forEach(item => {
                            this.restaurants.forEach(e => {
                                if (item.zid === e.id) {
                                    item.zid = e.value;
                                }
                                if (item.xid === e.id) {
                                    item.xid = e.value;
                                }
                            });
                        });
                        this.tableData = tableData;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        },

        querySearch(queryString, cb) {
            var restaurants = this.restaurants;
            var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
        },

        createStateFilter(queryString) {
            return houseNumber => {
                return houseNumber.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1;
            };
        },

        loadAll() {
            return this.restaurants;
        },
        startChang(item) {
            this.startId = item.id;
        },
        endChang(item) {
            this.endId = item.id;
        },
        reset() {
            this.state1 = '';
            this.state2 = '';
        }
    },
    mounted() {
        this.restaurants = this.loadAll();
    }
};
</script>

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
    .block {
        display: flex;
    }
    .type {
        display: flex;
        align-items: center;
        padding-bottom: 10px;
        .title {
            margin-right: 10px;
        }
    }
    .start {
        display: flex;
        align-items: center;
        padding-bottom: 10px;
        .title {
            margin-right: 10px;
        }
    }
    .end {
        display: flex;
        align-items: center;
        padding-bottom: 10px;
        .title {
            margin-right: 10px;
        }
    }
    .el-button {
        margin-left: 20px;
    }
}
</style>
