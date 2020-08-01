<template>
    <div class="deliverGood">
        <el-dialog
            title="发货"
            :visible.sync="dialogFormVisible"
            width="650px"
            class="goodDialog"
            :close-on-click-modal="false"
            :before-close="handleClose"
        >
            <el-form :model="form" label-width="80px" size="mini">
                <div class="title _first">
                    <div class="left">- 地址信息</div>
                </div>
                <div class="row">
                    <el-form-item label="起运地" prop="start" style="width:350px;">
                        <el-col :span="50">
                            <el-autocomplete
                                class="inline-input"
                                v-model="form.start"
                                :fetch-suggestions="querySearch"
                                placeholder
                                :trigger-on-focus="false"
                                @select="startChang"
                                style="width:260px;"
                            ></el-autocomplete>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="发货人" prop="consignor" style="width:250px;">
                        <el-input v-model="form.consignor" disabled></el-input>
                    </el-form-item>
                </div>
                <el-form-item label="详细地址" prop="startAddress" style="width:600px;">
                    <el-input v-model="form.startAddress"></el-input>
                </el-form-item>
                <div class="row">
                    <el-form-item label="目的地" prop="end" style="width:350px;">
                        <el-col :span="50">
                            <el-autocomplete
                                class="inline-input"
                                v-model="form.end"
                                :fetch-suggestions="querySearch"
                                placeholder
                                :trigger-on-focus="false"
                                @select="endChang"
                                style="width:260px;"
                            ></el-autocomplete>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="收货人" prop="Receipt" style="width:250px;">
                        <el-input v-model="form.Receipt"></el-input>
                    </el-form-item>
                </div>
                <el-form-item label="详细地址" prop="startAddress" style="width:600px;">
                    <el-input v-model="form.endAddress"></el-input>
                </el-form-item>
                <el-form-item label="距离" style="width:600px;">
                    <el-input v-model="form.dis" disabled style=" font-weight: bold;color:#000;">
                        <i
                            slot="suffix"
                            style="font-style:normal;font-weight: bold;"
                            class="metre"
                        >公里</i>
                    </el-input>
                </el-form-item>
                <div class="title">
                    <div class="left">- 货物信息</div>
                </div>
                <div class="row">
                    <el-form-item label="货物名称" prop="goodsName" style="width:280px;">
                        <el-input v-model="form.goodsName"></el-input>
                    </el-form-item>
                    <el-form-item label="装货日期" style="width:300px;margin-left:40px">
                        <el-date-picker
                            v-model="form.time"
                            type="date"
                            placeholder="选择日期"
                            value-format="yyyy-MM-dd"
                            style="width:200px;"
                        ></el-date-picker>
                    </el-form-item>
                </div>
                <div class="row">
                    <el-form-item label="重量" style="width:280px;">
                        <!-- <el-input-number
                            v-model="form.dw"
                            controls-position="right"
                            :min="0"
                            :max="99999"
                            style="width:99%;"
                        ></el-input-number>-->
                        <el-col :span="11">
                            <el-input-number
                                v-model="form.dw1"
                                controls-position="right"
                                :min="1"
                                :max="9999"
                                style="width:90px;"
                                :precision="2"
                            ></el-input-number>
                        </el-col>
                        <el-col class="line" :span="2" style="text-align: center;">-</el-col>
                        <el-col :span="11">
                            <el-input-number
                                v-model="form.dw2"
                                controls-position="right"
                                :min="1"
                                :max="9999"
                                style="width:90px;"
                                :precision="2"
                            ></el-input-number>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="体积" style="width:280px;margin-left:40px">
                        <!-- <el-input-number
                            v-model="form.dv"
                            controls-position="right"
                            :min="0"
                            :max="99999"
                            style="width:99%;"
                        ></el-input-number> -->
                        <el-col :span="11">
                            <el-input-number
                                v-model="form.dv1"
                                controls-position="right"
                                :min="0"
                                :max="9999"
                                style="width:90px;"
                                :precision="2"
                            ></el-input-number>
                        </el-col>
                        <el-col class="line" :span="2" style="text-align: center;">-</el-col>
                        <el-col :span="11">
                            <el-input-number
                                v-model="form.dv2"
                                controls-position="right"
                                :min="0"
                                :max="9999"
                                style="width:90px;"
                                :precision="2"
                            ></el-input-number>
                        </el-col>
                    </el-form-item>
                </div>
                <el-form-item label="司机运费" style="width:280px;">
                    <el-input-number
                        v-model="form.price"
                        controls-position="right"
                        :min="0"
                        :max="99999"
                        style="width:99%;"
                    ></el-input-number>
                </el-form-item>
                <div class="title">
                    <div class="left">- 车辆信息</div>
                </div>
                <div class="row" style="height:30px;">
                    <el-form-item label="车型" prop="ct" style="width:300px;">
                        <el-select v-model="form.ct" placeholder="请选择">
                            <el-option
                                v-for="item in car_types"
                                :key="item.value"
                                :label="item.label"
                                :value="item.label"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="车长" prop="cl" style="width:300px;margin-left:20px;">
                        <el-select v-model="form.cl" placeholder="请选择">
                            <el-option
                                v-for="item in car_long"
                                :key="item.value"
                                :label="item.label"
                                :value="item.label"
                            ></el-option>
                        </el-select>
                    </el-form-item>
                </div>
            </el-form>
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
    props: ['getcusData'],
    data() {
        return {
            dialogFormVisible: false,
            customData: this.getcusData || { com: '', id: '' },
            form: {
                dw1:'',
                dw2:'',
                dv1:'',
                dv2:'',
                com: '',
                cid: '',
                startId: '',
                start: '',
                endId: '',
                end: '',
                startAddress: '',
                endAddress: '',
                consignor: '',
                Receipt: '',
                dis: '',
                goodsName: '',
                time: '',
                dw: '',
                dv: '',
                price: '',
                ct: '',
                cl: '',
                aa: ''
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
                    label: '低栏'
                },
                {
                    value: '5',
                    label: '高低板'
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
                    value: '18',
                    label: '13.5米'
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
                }
            ],
            rowData: {},
            restaurants: [],
            loginData: {},
            sk: ''
        };
    },
    watch: {
        getcusData: {
            handler(newValue, oldValue) {
                this.customData = newValue;
            },
            deep: true
        }
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.CustomerData;
    },
    components: {},
    methods: {
        open(row) {
            // console.log(row.__rowNum__);

            this.rowData = row;
            this.loginData = this.$store.state.Login.CustomerData;
            if (!this.rowData.起运地) {
                this.$alert('请根据模板格式导入数据！', '提示', {
                    confirmButtonText: '确定',
                    callback: (action) => {}
                });
                return;
            }
            this.dialogFormVisible = true;

            this.form.start = '';
            this.form.end = '';
            this.form.startAddress = '';
            this.form.endAddress = '';
            this.form.Receipt = '';
            this.form.startId = '';
            this.form.dis = '';
            this.form.consignor = this.customData.ne;
            this.form.goodsName = row.货物名称;
            this.form.time = row.装货日期;
            if (String(row.重量).indexOf('-',0) != -1) {
                this.form.dw1 = String(row.重量).split('-')[0];
                this.form.dw2 = String(row.重量).split('-')[1];
            }else{
                this.form.dw1 = String(row.重量);
                this.form.dw2 = String(row.重量);
            }
            if (String(row.体积).indexOf('-',0) != -1) {
                this.form.dv1 = String(row.体积).split('-')[0];
                this.form.dv2 = String(row.体积).split('-')[1];
            }else{
                this.form.dv1 = String(row.体积);
                this.form.dv2 = String(row.体积);
            }
            // this.form.dw = row.重量;
            // this.form.dv = row.体积;

            this.form.price = row.期望运费;
            this.form.ct = row.车型;
            this.form.cl = row.车长;

            this.startProvinceChange();
        },
        againOpen(row) {
            this.rowData = row;
            this.loginData = this.$store.state.Login.CustomerData;
            // if (!this.rowData.起运地) {
            //     this.$alert('请根据模板格式导入数据！', '提示', {
            //         confirmButtonText: '确定',
            //         callback: action => {}
            //     });
            //     return;
            // }
            this.dialogFormVisible = true;
            this.form.com = row.fcom;
            this.form.cid = row.tid;
            this.form.start = row.zct;
            this.form.end = row.xct;
            this.form.startAddress = '';
            this.form.endAddress = '';
            this.form.Receipt = row.shr;
            this.form.startId = row.zid;
            this.form.endId = row.xid;
            this.form.dis = row.dis;
            this.form.consignor = row.fhr;
            this.form.goodsName = row.on;

            if (new Date(row.zdy).getTime() > new Date().getTime()) {
                this.form.time = row.zdy;
            }else{
                this.form.time = new Date;
            }

            this.form.price = row.qyf;
            this.form.ct = row.ct;
            this.form.cl = row.cl;

            if (row.dw.indexOf('-',0) != -1) {
                this.form.dw1 = row.dw.split('-')[0];
                this.form.dw2 = row.dw.split('-')[1];
            }else{
                this.form.dw1 = row.dw;
                this.form.dw2 = row.dw;
            }
            if (row.dv.indexOf('-',0) != -1) {
                this.form.dv1 = row.dv.split('-')[0];
                this.form.dv2 = row.dv.split('-')[1];
            }else{
                this.form.dv1 = row.dv;
                this.form.dv2 = row.dv;
            }


            // this.form.dw = row.dw;
            // this.form.dv = row.dv;


            // this.startProvinceChange();
        },
        handleClose() {
            this.cancelAdd();
        },
        cancelAdd() {
            this.dialogFormVisible = false;
        },
        //地址搜索
        async startProvinceChange() {
            var storage = window.localStorage;
            let AddressList = JSON.parse(storage.AddressList);
            this.restaurants = AddressList;
            // console.log(this.restaurants);

            this.restaurants.map((item) => {
                // console.log(item.address.indexOf('广州'));

                if (item.address.indexOf(this.rowData.起运地) != -1) {
                    this.form.startId = item.id;
                    this.form.start = item.value;
                    // console.log(item.address);
                    return;
                }
                if (item.address.indexOf(this.rowData.目的地) != -1) {
                    this.form.endId = item.id;
                    this.form.end = item.value;
                    // console.log(item.value);
                    return;
                }
            });
            // console.log(this.form.startId,this.form.endId);

            if (this.form.startId && this.form.endId) {
                let action = this.$store.state.Login.url + '/10016';
                var bd = {
                    tid: this.loginData.tid,
                    zid: this.form.startId,
                    xid: this.form.endId
                };
                let hd = {
                    pi: 10016,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/10016', hd, bd);
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
                    this.form.dis = Math.round(data.dis);
                    // this.tableData = data.olst;
                    // this.totalNumber = data.pg.tn;
                } else {
                    this.$message({
                        message: res.data.hd.rmsg,
                        type: 'error',
                        duration: 900
                    });
                }

                // console.log(bd);
                // this.$axios
                //     .post(action, {
                //         hd: {
                //             pi: 10016,
                //             si: this.loginData.si,
                //             sq: 9
                //         },
                //         bd: bd
                //     })
                //     .then(res => {
                //         let rid = res.data.hd.rid;
                //         if (rid >= 0) {
                //             let data = JSON.parse(res.data.bd);
                //             this.form.dis = data.dis;
                //             // console.log(this.dis);
                //         } else {
                //             this.open2(res.data.hd.rmsg);
                //         }
                //     })
                //     .catch(error => {
                //         console.log(error);
                //     });
            }
        },
        querySearch(queryString, cb) {
            var restaurants = this.restaurants;
            var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
            // console.log(queryString, cb);
        },
        createStateFilter(queryString) {
            return (houseNumber) => {
                return houseNumber.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1;
            };
        },
        async startChang(item) {
            this.form.startId = item.id + '';
            // console.log(this.loginData);
            if (this.form.startId.length > 0 && this.form.endId > 0) {
                let action = this.$store.state.Login.url + '/10016';
                var bd = {
                    zid: this.form.startId,
                    xid: this.form.endId,
                    tid: this.loginData.tid
                };
                let hd = {
                    pi: 10016,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/10016', hd, bd);
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
                    this.form.dis = Math.round(data.dis);
                    // this.tableData = data.olst;
                    // this.totalNumber = data.pg.tn;
                } else {
                    this.$message({
                        message: res.data.hd.rmsg,
                        type: 'error',
                        duration: 900
                    });
                }

                // console.log(bd);
                // this.$axios
                //     .post(action, {
                //         hd: {
                //             pi: 10016,
                //             si: this.loginData.si,
                //             sq: 9
                //         },
                //         bd: bd
                //     })
                //     .then(res => {
                //         let rid = res.data.hd.rid;
                //         if (rid >= 0) {
                //             let data = JSON.parse(res.data.bd);
                //             this.form.dis = data.dis;
                //         } else {
                //             this.open2(res.data.hd.rmsg);
                //         }
                //     })
                //     .catch(error => {
                //         console.log(error);
                //     });
            }
        },
        async endChang(item) {
            this.form.endId = item.id + '';
            // console.log(this.loginData.tid);
            if (this.form.startId.length > 0 && this.form.endId > 0) {
                let action = this.$store.state.Login.url + '/10016';

                var bd = {
                    tid: this.loginData.tid,
                    zid: this.form.startId,
                    xid: this.form.endId
                };
                let hd = {
                    pi: 10016,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/10016', hd, bd);
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
                    this.form.dis = Math.round(data.dis);
                    // this.tableData = data.olst;
                    // this.totalNumber = data.pg.tn;
                } else {
                    this.$message({
                        message: res.data.hd.rmsg,
                        type: 'error',
                        duration: 900
                    });
                }
                // console.log(bd);
                // this.$axios
                //     .post(action, {
                //         hd: {
                //             pi: 10016,
                //             si: this.loginData.si,
                //             sq: 9
                //         },
                //         bd: bd
                //     })
                //     .then(res => {
                //         let rid = res.data.hd.rid;
                //         if (rid >= 0) {
                //             let data = JSON.parse(res.data.bd);
                //             this.form.dis = data.dis;
                //         } else {
                //             this.open2(res.data.hd.rmsg);
                //         }
                //     })
                //     .catch(error => {
                //         console.log(error);
                //     });
            }
        },
        //发货
        async saveEdit() {
            // this.$emit('delData',this.rowData.__rowNum__);
            if (!this.form.startId || !this.form.endId) {
                this.$alert('起运地、目的地不能为空，请根据提示选择！', '提示', {
                    confirmButtonText: '确定',
                    callback: (action) => {}
                });
                return;
            }
            if (!this.form.consignor) {
                this.$alert('发货人不能为空', '提示', {
                    confirmButtonText: '确定',
                    callback: (action) => {}
                });
                return;
            }
            if (this.form.dw1 != this.form.dw2) {
                this.form.dw = `${this.form.dw1}-${this.form.dw2}`
            }else{
                this.form.dw = this.form.dw1;
            }
            if (this.form.dv1 != this.form.dv2) {
                this.form.dv = `${this.form.dv1}-${this.form.dv2}`
            }else{
                this.form.dv = this.form.dv1;
            }
            if (!this.form.goodsName || !this.form.time || !this.form.dw || !this.form.dv || !this.form.price) {
                this.$alert('货物信息不能为空', '提示', {
                    confirmButtonText: '确定',
                    callback: (action) => {}
                });
                return;
            }
            if (!this.form.ct || !this.form.cl) {
                this.$alert('车型、车长不能为空', '提示', {
                    confirmButtonText: '确定',
                    callback: (action) => {}
                });
                return;
            }
            this.dialogFormVisible = false;
            let action = this.$store.state.Login.url + '/20001';
            var bd = {
                tid: this.loginData.tid,
                on: this.form.goodsName,
                te: 1,
                zxte: 1,
                ct: this.form.ct,
                cl: this.form.cl,
                dw: this.form.dw,
                dv: this.form.dv,
                zdy: this.form.time,
                zte: '0:00-24:00',
                qyf: this.form.price,
                dis: this.form.dis,
                zid: this.form.startId,
                zad: this.form.startAddress,
                xid: this.form.endId,
                xad: this.form.endAddress,
                com: this.customData.com || this.form.com,
                cid: this.customData.id || this.form.cid,
                shr: this.form.Receipt,
                shrp: '',
                odc: '',
                dc: '',
                isin: 1,
                drne: '',
                drph: '',
                cn: ''
            };
            let hd = {
                pi: 20001,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/20001', hd, bd);
            // console.log(res);
            let rid = res.data.hd.rid;
            if (rid >= 0) {
                this.$message({
                    message: '发货成功',
                    type: 'success',
                    duration: 900
                });
                this.$emit('delData', this.rowData.__rowNum__);
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
            //             pi: 20001,
            //             si: this.loginData.si,
            //             sq: 9
            //         },
            //         bd: bd
            //     })
            //     .then(res => {
            //         let data = res.data;
            //         if (data.hd.rid >= 0) {
            //             this.$message({
            //                 message: '发货成功',
            //                 type: 'success',
            //                 duration: 900
            //             });
            //             this.$emit('delData', this.rowData.__rowNum__);
            //         } else {
            //             this.open2(res.data.hd.rmsg);
            //         }
            //     });
        },
        open2(text) {
            // this.$message.error(text);
            this.$message({
                message: text,
                type: 'error',
                duration: 900
            });
        }
    }
};
</script>

<style scoped lang="scss">
.deliverGood {
    .goodDialog {
        .title {
            border-bottom: 2px solid #409eff;
            margin: 20px 0;
            .left {
                font-size: 14px;
                line-height: 24px;
                border-radius: 12px 0 12px 0;
                background: #409eff;
                color: #fff;
                width: 150px;
                text-align: center;
            }
        }
        ._first {
            margin-top: 0;
        }
        .row {
            display: flex;
            .el-form-item {
                margin-bottom: 18px;
            }
            /deep/ .el-input-number.is-controls-right .el-input__inner{
                padding-left: 0;
                padding-right: 28px;
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
