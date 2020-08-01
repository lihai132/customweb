<template>
    <div>
        
        <div class="top">
            <div class="search">
                <div class="title">按手机号查询</div>
                <el-input v-model="phone" placeholder="请输入手机号码"></el-input>
            </div>
            <div class="but">
                <el-button type="success" @click="getData">查询</el-button>
                <el-button type="success" @click="reData">重置</el-button>
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
                :header-cell-style="{background:'#ff0000',color:'#606266'}"
            >
                <el-table-column prop="did" label="司机编号" width="120"></el-table-column>
                <el-table-column prop="dne" label="司机名称" width="150"></el-table-column>
                <el-table-column prop="dph" label="司机电话" width="150"></el-table-column>
                <el-table-column prop="cn" label="车牌号" width="120"></el-table-column>
                <el-table-column prop="ct" label="车型" width="120"></el-table-column>
                <el-table-column prop="cl" label="车长" width="120"></el-table-column>
                <el-table-column prop="didc" label="司机身份证" width="250"></el-table-column>
                <el-table-column prop="cidc" label="车辆识别代码"></el-table-column>
                <el-table-column prop="ctm" label="创建时间"></el-table-column>
                <el-table-column
                    
                    label="操作"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="handleClick(scope.row)" type="text" size="small">派车</el-button>
                        <el-button type="text" size="small" @click="handleDetails(scope.row)">详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 编辑弹出框 -->
        <el-dialog title="司机详情" :visible.sync="editVisible" width="33%">
            <el-form ref="form" :model="form" label-width="85px" size="mini" inline>
                <!-- <div class="driver_info">- - - - - 司机信息 - - - - -</div> -->
                <el-form-item label="姓名">
                    <el-input v-model="form.name" :disabled="disabled" style="width: 100%;"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="form.phone" maxlength="11" :disabled="disabled" style="width: 100%;"></el-input>
                </el-form-item>
                <el-form-item label="身份证号" style="display:block;">
                    <el-input v-model="form.userID" :disabled="disabled" style="width:37.5em"></el-input>
                </el-form-item>
                <!-- <div class="vehicle_info">- - - - - 车辆信息 - - - - -</div> -->
                <el-form-item label="车牌号">
                    <el-input v-model="form.numberPlate" :disabled="disabled" style="width: 100%;"></el-input>
                </el-form-item>
                <span>(例：粤B88888)</span>
                <el-form-item label="车型">
                    <el-select v-model="form.car_type" :disabled="disabled" placeholder="请选择" style="width: 12.75em">
                        <el-option
                            v-for="item in car_types"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车长">
                    <el-select v-model="form.car_length" :disabled="disabled" placeholder="请选择" style="width: 12.75em;">
                        <el-option
                            v-for="item in car_long"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车辆识别码" style="display:block;">
                    <el-input v-model="form.Identifier" :disabled="disabled" style="width:37.5em"></el-input>
                </el-form-item>
                <el-form-item label="车辆描述" style="display:block;">
                    <el-input type="textarea" v-model="form.desc" :disabled="disabled" style="width:37.5em"></el-input>
                </el-form-item>

                <el-tabs type="border-card" style="width:38em;margin-left:8px;">
                    <el-tab-pane label="驾驶证">

                        <el-image v-if="disabled" :src="form.src_jpc"></el-image>

                    </el-tab-pane>
                    <el-tab-pane label="行驶证">

                        <el-image v-if="disabled" :src="form.src_xpc"></el-image>

                    </el-tab-pane>
                    <el-tab-pane label="身份证">

                    </el-tab-pane>
                    <el-tab-pane label="车辆照片">
 
                    </el-tab-pane>
                </el-tabs>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">确 定</el-button>
                <!-- <el-button type="primary" @click="saveEdit" v-if="newAddBtn">新 增</el-button> -->
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
export default {
    data() {
        return {
            phone: '',
            tableData: [],
            editVisible: false,
            disabled: true,
            form: {
                name: '',
                phone: '',
                userID: '',
                numberPlate: '',
                Identifier: '',
                car_type: '',
                car_length: '',
                jpc: '',
                xpc: '',
                src_jpc: '',
                src_xpc: ''
            },
            car_types: [
                {
                    value: '1',
                    label: '平板'
                },
                {
                    value: '2',
                    label: '高栏'
                },
                {
                    value: '3',
                    label: '中栏'
                },
                {
                    value: '4',
                    label: '底栏'
                },
                {
                    value: '6',
                    label: '厢式'
                },
                {
                    value: '7',
                    label: '自卸'
                },
                {
                    value: '8',
                    label: '保温'
                },
                {
                    value: '9',
                    label: '冷藏'
                },
                {
                    value: '10',
                    label: '危险品'
                },
                {
                    value: '11',
                    label: '集装箱'
                },
                {
                    value: '12',
                    label: '特种'
                },
                {
                    value: '13',
                    label: '其他'
                }
            ],
            car_long: [
                {
                    value: '1',
                    label: '4.2米'
                },
                {
                    value: '2',
                    label: '5.0米'
                },
                {
                    value: '3',
                    label: '6.2米'
                },
                {
                    value: '4',
                    label: '6.8米'
                },
                {
                    value: '5',
                    label: '7.2米'
                },
                {
                    value: '6',
                    label: '7.7米'
                },
                {
                    value: '7',
                    label: '7.8米'
                },
                {
                    value: '8',
                    label: '8.2米'
                },
                {
                    value: '9',
                    label: '8.7米'
                },
                {
                    value: '10',
                    label: '9.6米'
                },
                {
                    value: '11',
                    label: '12.5米'
                },
                {
                    value: '12',
                    label: '13.0米'
                },
                {
                    value: '13',
                    label: '14.6米'
                },
                {
                    value: '14',
                    label: '15.0米'
                },
                {
                    value: '15',
                    label: '16.5米'
                },
                {
                    value: '16',
                    label: '17.5米'
                },
                {
                    value: '17',
                    label: '其他'
                }
            ]
        };
    },
    created() {
        this.getData();
    },
    methods: {
        //获取平台司机数据
        getData() {
            let action = 'http://192.168.0.66:9010/30023';
            this.loginData = JSON.parse(this.$store.state.Login.loginData);

            var bd = {
                tid: this.loginData.tid,
                dph: this.phone,
                did: '',
                cs: 1,
                pg: 1,
                sz: 10
            };
            // console.log(this.loginData.tid, this.phone,this.loginData.si);
            this.$axios
                .post(action, {
                    hd: {
                        pi: 30023,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let data = JSON.parse(res.data.bd);
                    this.tableData = data.olst;
                    // console.log(tableData);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        reData(){
            this.phone = '';
        },
        // 详情
        handleDetails(row){
            // console.log(row);
            this.disabled = true;
            // this.newAddBtn = false;
            this.editVisible = true;
            this.form.name = row.dne;
            this.form.phone = row.dph;
            this.form.userID = row.didc;
            this.form.numberPlate = row.cn;
            this.form.Identifier = row.cidc;
            this.car_types.forEach(element => {
                for (const key in element) {
                    if(key === 'value'){
                       if(element[key] == row.ct){
                          this.form.car_type = element.label;
                           
                       }
                    }
                }
            });
            this.car_long.forEach(element => {
                for (const key in element) {
                    if(key === 'value'){
                       if(element[key] == row.cl){
                          this.form.car_length = element.label;
                           
                       }
                    }
                }
            });
            var loginData = JSON.parse(this.$store.state.Login.loginData);
            this.form.src_jpc = `${loginData.murl}${row.jpc}`;
            this.form.src_xpc = `${loginData.murl}${row.xpc}`;

        }
    }
};
</script>

<style>
/* .el-table--small td {
    padding: 2px 0;
} */
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
