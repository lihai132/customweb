<template>
    <div class="goods">
        <div class="left">
            <div class="nav1">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">选择司机</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form3"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                        class="form"
                    >
                        <el-form-item label="选择司机">
                            <el-input
                                v-model="form3.search"
                                placeholder="请输入内容"
                                style="width:300px"
                                :maxlength="16"
                            ></el-input>
                            <el-button
                                type="primary"
                                style="margin-left:13px;"
                                @click="search()"
                                :disabled="button"
                            >查找</el-button>
                            <el-button type="primary" @click="addDriver">新增</el-button>
                        </el-form-item>
                    </el-form>
                    <el-table
                        :data="customerData"
                        border
                        class="table"
                        ref="multipleTable"
                        header-cell-class-name="table-header"
                        highlight-current-row
                        @row-click="tableClick"
                        @cell-click="getOpen"
                    >
                        <el-table-column width="60" align="center">
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.checked"></el-checkbox>
                            </template>
                        </el-table-column>

                        <el-table-column
                            prop="ne"
                            label="司机名称"
                            :show-overflow-tooltip="true"
                            align="center"
                            width="100"
                        ></el-table-column>
                        <el-table-column
                            prop="ph"
                            label="司机电话"
                            :show-overflow-tooltip="true"
                            align="center"
                            width="130"
                        ></el-table-column>
                        <el-table-column
                            prop="cn"
                            label="车牌号"
                            :show-overflow-tooltip="true"
                            align="center"
                            width="100"
                        >
                            <template slot-scope="scope">
                                <span
                                    style="color:#409EFF;text-decoration:underline"
                                >{{scope.row.cn}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="st"
                            label="司机状态"
                            :show-overflow-tooltip="true"
                            align="center"
                            width="100"
                        ></el-table-column>
                        <el-table-column
                            prop="ctm"
                            label="创建时间"
                            :show-overflow-tooltip="true"
                            align="center"
                        ></el-table-column>
                    </el-table>
                </div>
            </div>
        </div>
        <div class="right">
            <div class="nav1">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">运单信息</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form2"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="100px"
                        class="form"
                    >
                        <el-form-item required label="装货日期">
                            <el-date-picker
                                v-model="form2.startTime"
                                type="date"
                                placeholder="选择日期"
                                value-format="yyyy-MM-dd"
                                style="width:130px;"
                            ></el-date-picker>
                            <el-time-select
                                v-model="form2.startSpecific "
                                :picker-options="{
                                 start: '08:30',
                                 step: '00:15',
                                 end: '18:30'
                                }"
                                placeholder="选择时间"
                                style="width:100px;"
                            ></el-time-select>
                        </el-form-item>
                        <el-form-item label="卸货日期">
                            <el-date-picker
                                v-model="form2.endTime"
                                type="date"
                                placeholder="选择日期"
                                value-format="yyyy-MM-dd"
                                style="width:130px;"
                            ></el-date-picker>
                            <el-time-select
                                v-model="form2.endSpecific "
                                :picker-options="{
                                 start: '08:30',
                                 step: '00:15',
                                 end: '18:30'
                                }"
                                placeholder="选择时间"
                                style="width:100px;"
                            ></el-time-select>
                        </el-form-item>

                        <el-form-item label="货物重量" required style="width:323px;">
                            <el-input-number
                                v-model="form2.weight"
                                controls-position="right"
                                :min="0"
                                :max="9999"
                                style="width:215px;"
                            ></el-input-number>
                        </el-form-item>
                        <el-form-item label="货物体积" style="width:323px;">
                            <el-input-number
                                v-model="form2.volume"
                                controls-position="right"
                                :min="0"
                                :max="9999"
                                style="width:215px;"
                            ></el-input-number>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
            <div class="nav2">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">司机费用</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form2"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="100px"
                        class="form"
                    >
                        <div class="row">
                            <!-- <span class="tipe">*</span> -->
                            <el-form-item label="司机服务费" required>
                                <el-input-number
                                    v-model="form2.service"
                                    controls-position="right"
                                    :min="0"
                                    :max="99999"
                                    style="width:215px;"
                                    :step="50"
                                    :maxlength="8"
                                ></el-input-number>
                            </el-form-item>
                        </div>

                        <el-form-item required label="司机运费">
                            <el-input-number
                                v-model="form2.freight"
                                controls-position="right"
                                :min="0"
                                :max="999999"
                                style="width:215px;"
                                :step="50"
                                :maxlength="8"
                                @change="freChange"
                            ></el-input-number>
                        </el-form-item>
                        <el-form-item label="回单押金">
                            <el-input-number
                                v-model="form2.deposit"
                                controls-position="right"
                                :min="0"
                                :max="depMax"
                                style="width:215px;"
                                :step="50"
                                :maxlength="8"
                                @change="depChange"
                            ></el-input-number>
                        </el-form-item>
                        <el-form-item label="油卡费">
                            <el-input-number
                                v-model="form2.oilCard"
                                controls-position="right"
                                :min="0"
                                :max="oilMax"
                                style="width:215px;"
                                :step="50"
                                :maxlength="8"
                                @change="oilChange"
                            ></el-input-number>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
            <div class="nav3">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">货主运费</div>
                    <div class="check">
                        <el-checkbox
                            v-model="form3.check"
                            style="margin-left:20px;"
                        >开具增值税发票(当前税率{{popupData.txr}})</el-checkbox>
                    </div>
                </div>
                <div class="center">
                    <el-form
                        :model="form2"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="100px"
                        class="form"
                    >
                        <div class="row">
                            <!-- <span class="tipe">*</span> -->
                            <el-form-item label="佣金">
                                <el-input-number
                                    v-model="form3.commission"
                                    controls-position="right"
                                    :min="0"
                                    :max="99999"
                                    style="width:215px;"
                                    :step="50"
                                    :maxlength="8"
                                ></el-input-number>
                            </el-form-item>
                        </div>

                        <el-form-item label="税费">
                            <el-input-number
                                v-model="form3.insurance"
                                controls-position="right"
                                :min="0"
                                :max="99999"
                                style="width:215px;"
                                :step="50"
                                :maxlength="8"
                                :disabled="true"
                            ></el-input-number>
                        </el-form-item>
                        <el-form-item required label="货主运费">
                            <el-input-number
                                v-model="form3.totaLfreight"
                                controls-position="right"
                                :min="0"
                                :max="999999"
                                style="width:215px;"
                                :step="50"
                                :maxlength="8"
                                :disabled="true"
                            ></el-input-number>
                        </el-form-item>
                    </el-form>
                </div>
            </div>

            <div class="but">
                <el-button type="primary" style="width:120px;margin-left:30px;" @click="goBack">返回</el-button>
                <el-button
                    type="primary"
                    style="width:120px;"
                    @click="assign"
                    :disabled="button1"
                >派车</el-button>
            </div>
        </div>
        <div class="headTitle">合约派车</div>
        <driverDetails ref="driverDetails"></driverDetails>
        <addDriverNew ref="addDriverNew" @getData="getData"></addDriverNew>
    </div>
</template>
<script>
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
import driverDetails from '../popup/driverDetails.vue';
import addDriverNew from '../popup/addDriverNew.vue';
export default {
    data() {
        return {
            depMax: 99999,
            oilMax: 99999,
            checked: '', // 如果使用单选框,定义一个model值
            // currentSelectItem: {}, // 当前选中的值
            templateRadio: '',
            restaurants: [],
            rules: {
                start: [
                    { required: true, message: '请输入起运地', trigger: 'blur' },
                    { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
                ],
                end: [
                    { required: true, message: '请输入目的地地', trigger: 'blur' },
                    { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
                ],
                consignor: [
                    { required: true, message: '请输入起运地', trigger: 'blur' },
                    { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
                ],
                startAddress: [
                    { required: false, message: '请输入起运地详细地址', trigger: 'blur' },
                    { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
                ],
                endAddress: [
                    { required: false, message: '请输入目的地详细地址', trigger: 'blur' },
                    { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
                ],
                goodsName: [
                    { required: true, message: '请输入货物名称', trigger: 'blur' },
                    { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
                ],
                weight: [{ required: true, message: '请输入重量', trigger: 'blur' }],
                time: [{ required: true }],
                transportType: [{ required: true }],
                pretendType: [{ required: true }],
                customer: [{ required: true }],
                price: [
                    // { required: true, message: '请输入运费', trigger: 'blur' },
                    { min: 1, max: 8, message: '长度在 1 到 8 个字符', trigger: 'blur' }
                ],
                name: [
                    { required: false, message: '请输入司机名称', trigger: 'blur' },
                    { min: 0, max: 16, message: '长度在 0 到 16 个字符', trigger: 'blur' }
                ],
                phone: [
                    { required: false, message: '请输入司机号码', trigger: 'blur' },
                    { min: 11, max: 11, message: '长度为 11 位数字字符', trigger: 'blur' }
                ],
                vehicle: [
                    { required: false, message: '请输入车牌号', trigger: 'blur' },
                    { min: 7, max: 7, message: '长度为 7 位字符', trigger: 'blur' }
                ],
                ct: [{ required: true, message: '请选择车型', trigger: 'blur' }],
                cl: [{ required: true, message: '请选择车长', trigger: 'blur' }]
            },

            form2: {
                startTime: '',
                startSpecific: '',
                endTime: '',
                endSpecific: '',
                weight: '',
                volume: '',
                service: 0,
                freight: '',
                deposit: '',
                oilCard: '',
                totaLfreight: ''
            },
            form3: {
                search: '',
                did: '',
                commission: '',
                insurance: 0,
                totaLfreight: '',
                check: false
            },
            customerData: [],
            customer: {},
            loginData: {},
            sk: '',
            popupData: {},
            customerList: [],
            truckType: ['平板', '高栏', '中栏', '低栏', '高低板', '厢车', '自卸', '保温', '冷藏', '危险品', '集装箱', '特种'],
            conductor: [
                '4.2米',
                '5.0米',
                '6.2米',
                '6.8米',
                '7.2米',
                '7.7米',
                '7.8米',
                '8.2米',
                '8.7米',
                '9.6米',
                '12.5米',
                '13.0米',
                '13.5米',
                '14.6米',
                '15.0米',
                '16.5米',
                '17.5米'
            ],
            disabled1: false,
            disabled2: false,
            button: false,
            button1: false
        };
    },
    created() {
        this.sk = this.$store.state.Login.key;
        this.loginData = this.$store.state.Login.DispatchData;
        this.popupData = this.$store.state.Popup.popup2;
        // console.log(this.popupData);
        this.form2.freight = this.popupData.qyf;
        this.form2.startTime = this.popupData.zdy;
        if (this.popupData.dw.indexOf('-') == -1) {
            this.form2.weight = this.popupData.dw;
        } else {
            let dwArr = this.popupData.dw.split('-');
            this.form2.weight = dwArr[1];
        }
        if (this.popupData.dv.length > 0) {
            if (this.popupData.dv.indexOf('-') == -1) {
                this.form2.volume = this.popupData.dv;
            } else {
                let dwArr = this.popupData.dv.split('-');
                this.form2.volume = dwArr[1];
            }
        }

        console.log(this.popupData);
        this.search();
    },
    methods: {
        //司机搜索
        async search() {
            let action = `${this.$store.state.Login.url}/30073`;

            var bd = {
                tid: this.loginData.tid,
                im: 3,
                ss: this.form3.search,
                pg: 1,
                sz: 100
            };
            let hd = {
                pi: 30073,
                si: this.loginData.si,
                sq: 9
            };

            let res = await request('/30073', hd, bd);
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
                let list = data.olst;
                // console.log(data);
                if (list.length == 0) {
                    this.open2('所查询司机为空，请添加');
                    // this.rightShow = false;
                    // this.formShow = true;
                } else {
                    // list.map((item, index) => {
                    //     item.value = item.ne + ' ' + item.ph;
                    // });
                    list.forEach((item, index) => {
                        item.checked = false;
                        item.id = index;
                        if (item.st == 1) {
                            item.st = '已开户';
                        }
                        if (item.st == 2) {
                            item.st = '待签约';
                        }
                        if (item.st == 3) {
                            item.st = '正常';
                        }
                        if (item.st == 4) {
                            item.st = '冻结';
                        }
                        if (item.st == 5) {
                            item.st = '已注销';
                        }
                        if (item.st == 6) {
                            item.st = '已登记';
                        }
                    });
                    this.customerData = list;
                    // console.log(list)
                    // this.tabelShow = true;
                }
            } else {
                this.open2(res.data.hd.rmsg);
            }

            this.button = true;
            setTimeout(() => {
                this.button = false;
            }, 1000);
        },
        handleSelectionChange(row) {
            // el-radio单选框,不需要这一步
            this.customerData.forEach((item) => {
                // 排他,每次选择时把其他选项都清除
                if (item.id !== row.id) {
                    item.checked = false;
                }
                if (item.id == row.id) {
                    item.checked = true;
                }
            });

            this.customer = row;
            // console.log(row.checked);
            // row.checked = true;
        },
        tableClick(row) {
            // this.handleSelectionChange(row)
            this.customerData.forEach((item) => {
                if (item.id == row.id) {
                    item.checked = true;
                } else {
                    item.checked = false;
                }
            });
            this.form3.did = row.tid;
            // console.log(row)
        },
        //派车
        async assign() {
            this.button1 = true;
            let action = this.$store.state.Login.url + '/20019';
            // console.log(this.did.length);
            if (this.form3.did == false) {
                this.open2('请先确定派车司机');
                this.button1 = true;
                setTimeout(() => {
                    this.button1 = false;
                }, 1000);
                return;
            }
            if (this.form2.startTime == false ) {
                this.open2('装货日期为必填项');
                this.button2 = true;
                setTimeout(() => {
                    this.button2 = false;
                }, 1000);

                return;
            }
            if (this.form2.startSpecific == false) {
                this.open2('装货时间为必填项');
               this.button1 = true;
                setTimeout(() => {
                    this.button1 = false;
                }, 1000);
                return;
            }
            if (this.form2.weight == false) {
                this.open2('重量为必填项');
                this.button1 = true;
                setTimeout(() => {
                    this.button1 = false;
                }, 1000);

                return;
            }
            if (this.form2.service == undefined || this.form2.service.length == 0) {
                this.open2('司服务费不能为空');
                this.button1 = true;
                setTimeout(() => {
                    this.button1 = false;
                }, 1000);
                return;
            }
            if (this.form2.freight == false) {
                this.open2('期望运费不能为空');
                this.button1 = true;
                setTimeout(() => {
                    this.button1 = false;
                }, 1000);
                return;
            } else {
                let bd = {
                    tid: this.loginData.tid,
                    oid: this.popupData.oid,
                    did: this.form3.did,
                    wt: 2,
                    zte: (this.form2.startTime + ' ' + this.form2.startSpecific).trim(),
                    xte: (this.form2.endTime + ' ' + this.form2.endSpecific).trim(),
                    dw: this.form2.weight,
                    dv: this.form2.volume,
                    yf: this.form3.totaLfreight,
                    dyf: this.form2.freight,
                    ins: 0,
                    oil: this.form2.oilCard,
                    isin: this.form3.check ? 1 : 0,
                    tax: this.form3.insurance,
                    hdyf: this.form2.deposit,
                    dch: this.form2.service,
                    ishd: 0,
                    isds: 0,
                    cch: this.form3.commission,
                    dc: ''
                };
                console.log(bd)
                var hd = {
                    pi: 20019,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/20019', hd, bd);
                let rid = res.data.hd.rid;
                if (rid >= 0) {
                    this.open1('派车成功');
                    this.goBack();
                } else {
                    this.open2(res.data.hd.rmsg);
                }

                setTimeout(() => {
                    this.button1 = false;
                }, 1000);
            }
        },
        //返回
        goBack() {
            this.$emit('goBack');
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
        },
        getOpen(row, cell) {
            if (cell.label == '车牌号') {
                // this.handleDetails(row);
                // console.log(row);

                this.$refs.driverDetails.d_open(row);
                this.$refs.driverDetails.getData();
            } else {
                return;
            }
            // console.log(row,cell.label);
        },
        //添加司机
        addDriver() {
            this.$refs.addDriverNew.a_open();
        },
        getData() {
            this.search();
        },
        change1(num) {
            // console.log(111);
            // if (num > this.form2.freight) {
            //     this.form2.deposit = this.form2.freight;
            // }
            // if (num + this.form2.oilCard >= this.form2.freight) {
            //     num + this.form2.oilCard == this.form2.freight;
            //     this.form2.deposit = this.form2.freight - this.form2.oilCard;
            // }
        },
        change2(num) {
            // console.log(222);
            // if (num + this.form2.deposit >= this.form2.freight) {
            //     num + this.form2.deposit == this.form2.freight;
            //     this.form2.oilCard = this.form2.freight - this.form2.deposit;
            // }
        },
        freChange(value) {
            this.depMax = this.form2.freight - this.form2.oilCard;
            this.oilMax = this.form2.freight - this.form2.deposit;
        },
        depChange(value) {
            this.depMax = this.form2.freight - this.form2.oilCard;
            this.oilMax = this.form2.freight - this.form2.deposit;
        },
        oilChange(value) {
            this.depMax = this.form2.freight - this.form2.oilCard;
            this.oilMax = this.form2.freight - this.form2.deposit;
        }
    },
    watch: {
        'form2.freight': function (newVal) {
            this.form3.totaLfreight = newVal * 1 + this.form3.commission * 1;
            if (this.form3.check == true) {
                this.form3.insurance = (newVal * 1 + this.form3.commission * 1) * this.popupData.txr * 1;
                this.form3.insurance = Math.round(this.form3.insurance * 100) / 100;
                this.form3.totaLfreight = newVal * 1 + this.form3.commission * 1 + this.form3.insurance * 1;
                this.form3.totaLfreight = this.form3.totaLfreight.toFixed(2);
                // console.log(this.form3.totaLfreight);
                this.form3.totaLfreight = Math.round(this.form3.totaLfreight * 100) / 100;
            }
            if (this.form3.check == false) {
                this.form3.insurance = 0;
                this.form3.totaLfreight = newVal * 1 + this.form3.commission * 1;
            }

            this.form3.totaLfreight = this.form3.totaLfreight.toFixed(2);
        },
        'form3.commission': function (newVal) {
            this.form3.totaLfreight = this.form2.freight * 1 + newVal * 1;
            if (this.form3.check == true) {
                this.form3.insurance = (newVal * 1 + this.form2.freight * 1) * this.popupData.txr * 1;
                this.form3.insurance = Math.round(this.form3.insurance * 100) / 100;
                this.form3.insurance = this.form3.insurance.toFixed(2);
                this.form3.totaLfreight = newVal * 1 + this.form2.freight * 1 + this.form3.insurance * 1;
                this.form3.totaLfreight = this.form3.totaLfreight.toFixed(2);
                // console.log(this.form3.totaLfreight);
                this.form3.totaLfreight = Math.round(this.form3.totaLfreight * 100) / 100;
            }
            if (this.form3.check == false) {
                this.form3.insurance = 0;
                this.form3.totaLfreight = this.form2.freight * 1 + newVal * 1;
            }
            this.form3.totaLfreight = this.form3.totaLfreight.toFixed(2);
        },
        // 'form3.insurance': function(newVal) {
        //     this.form3.totaLfreight = this.form2.freight * 1 + this.form3.commission * 1 + newVal;
        //     if (this.form3.check == true) {
        //         this.form3.totaLfreight = this.form3.totaLfreight / (1 - this.popupData.txr * 1);
        //         // this.form3.totaLfreight = this.form3.totaLfreight.toFixed(2);
        //         console.log(this.form3.totaLfreight);
        //         this.form3.totaLfreight = Math.round(this.form3.totaLfreight * 100) / 100;
        //     }
        //     if (this.form3.check == false) {
        //         this.form3.totaLfreight = this.form2.freight * 1 + this.form3.commission * 1 + newVal;
        //     }
        //     this.form3.totaLfreight = this.form3.totaLfreight.toFixed(2);
        // },
        'form3.check': function (newVal) {
            if (newVal == true) {
                this.form3.insurance = (this.form2.freight * 1 + this.form3.commission * 1) * this.popupData.txr * 1;
                this.form3.insurance = Math.round(this.form3.insurance * 100) / 100;
                // console.log(1 - this.popupData.txr);
                this.form3.totaLfreight = this.form2.freight * 1 + this.form3.commission + this.form3.insurance;
                this.form3.totaLfreight = this.form3.totaLfreight.toFixed(2);
                console.log(this.form2.freight);
                console.log(this.form3.commission);
                console.log(this.form3.insurance);
                this.form3.totaLfreight = Math.round(this.form3.totaLfreight * 100) / 100;
            }
            if (newVal == false) {
                this.form3.insurance = 0;
                this.form3.totaLfreight = this.form2.freight * 1 + this.form3.commission * 1;
            }
        }
        // 'form2.deposit': function(newVal) {
        //     // let total = newVal + this.form2.oilCard;
        //     console.log(newVal);
        //     if (newVal + this.form2.oilCard >= this.form2.freight) {
        //         console.log(this.form2.oilCard);
        //         // this.disabled1 = true;
        //         // total = this.form2.freight;
        //         this.form2.deposit = this.form2.freight - this.form2.oilCard;
        //         // this.form2.deposit = total - this.form2.oilCard;
        //     }
        // },
        // 'form2.oilCard': function(newVal) {
        //     let total = newVal + this.form2.deposit;
        //     if (total >= this.form2.freight) {
        //         // this.disabled2 = true;
        //         total = this.form2.freight;
        //         this.form2.oilCard = total - this.form2.deposit;
        //     }
        // }
    },
    components: {
        driverDetails,
        addDriverNew
    }
};
</script>
<style lang="scss">
.el-radio-button__orig-radio:checked + .el-radio-button__inner {
    width: 80px;
}
.el-radio-button {
    border: 1px solid #dcdfe6 !important ;
    background: #409eff;
}
.el-checkbox-button {
    border: 1px solid #dcdfe6 !important ;
}
// .is-checked {
//     background: #409eff;
// }
.el-radio-button__inner {
    width: 80px;
    border: none !important;
    border-radius: 0px !important;
}

.el-checkbox-button__inner {
    width: 80px;
    border: none !important;
    border-radius: 0px !important;
}
</style>
<style lang="scss" scoped>
* {
    box-sizing: border-box;
}
.table /deep/.cell {
    line-height: 16px !important;
    padding: 6px 0;
}
/deep/textarea {
    resize: none !important;
    height: 150px !important;
    width: 545px;
}

/deep/.el-input--smal {
    font-size: 16px !important;
    font-weight: 600 !important;
}

.goods {
    display: flex;
    width: 100%;
    // height: 100%;
    padding-top: 50px;
    position: relative;
    .left {
        width: 700px;
        display: flex;
        flex-direction: column;
        padding-right: 40px;
        .nav1 {
            height: 90%;
            width: 100%;
            padding-top: 20px;
            padding-left: 10px;
            display: flex;
            flex-direction: column;

            .title {
                height: 34px;
                display: flex;
                align-items: center;
                padding-bottom: 10px;
                border-bottom: 1px solid #ccc;
                .title-left {
                    width: 5px;
                    height: 18px;
                    background: #409eff;
                    margin-right: 10px;
                }
                .text {
                    font-size: 18px;
                }
            }
            .center {
                // position: relative;
                display: flex;
                flex-direction: column;
                flex: 1;
                .form {
                    margin-top: 22px;
                    position: relative;
                    height: 34px;
                    .dis {
                        position: absolute;
                        left: 600px;
                        top: 50%;
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        .text {
                            font-size: 20px;
                            text-align: center;
                        }
                        .num {
                            font-size: 20px;
                            text-align: center;
                        }
                    }
                    .row {
                        display: flex;
                        position: relative;
                        .el-form-item {
                            margin-bottom: 18px;
                        }
                        .tipe {
                            position: absolute;
                            top: 10px;
                            color: #f56c6c;
                        }
                    }
                }
                .table {
                    margin-top: 20px;
                    flex: 1;
                    /deep/.el-table__body-wrapper {
                        height: 650px;
                        overflow: scroll;
                        // overflow-x: hidden;
                    }
                    /deep/.el-table__body-wrapper::-webkit-scrollbar {
                        display: none;
                    }
                }
            }
        }
    }
    .right {
        width: 700px;
        // display: flex;
        // flex-direction: column;
        padding-left: 40px;
        /deeo/.el-input--small {
            font-size: 16px !important;
            font-weight: 600 !important;
        }
        .nav1,
        .nav2,
        .nav3 {
            width: 100%;
            padding-top: 10px;
            padding-left: 10px;
            .title {
                display: flex;
                align-items: center;
                padding-bottom: 10px;
                border-bottom: 1px solid #ccc;
                // padding-left: 100px;
                .title-left {
                    width: 5px;
                    height: 18px;
                    background: #409eff;
                    margin-right: 10px;
                }
                .text {
                    font-size: 18px;
                }
            }
            .center {
                // position: relative;
                padding-left: 140px;
                display: flex;
                flex-direction: column;
                .form {
                    margin-top: 22px;
                    position: relative;
                    height: 52px;
                    .el-input-number {
                        /deep/.el-input--smal {
                            font-size: 16px !important;
                            font-weight: 600 !important;
                        }
                    }
                    .dis {
                        position: absolute;
                        left: 600px;
                        top: 40%;
                        display: flex;
                        flex-direction: column;
                        justify-content: center;
                        .text {
                            font-size: 20px;
                            text-align: center;
                        }
                        .num {
                            font-size: 20px;
                            text-align: center;
                        }
                    }
                    .row {
                        display: flex;
                        position: relative;
                        .el-form-item {
                            margin-bottom: 18px;
                        }
                        .tipe {
                            position: absolute;
                            top: 10px;
                            color: #f56c6c;
                            left: 8px;
                        }
                    }
                }
                // .table {
                //     flex: 1;
                //     /deep/.el-table__body-wrapper {
                //         height: 400px;
                //         overflow: scroll;
                //     }
                //     /deep/.el-table__body-wrapper::-webkit-scrollbar {
                //         display: none;
                //     }
                // }
            }
        }

        .nav1 {
            height: 265px;
            width: 100%;
            .search {
                margin-top: 20px;
            }
        }
        .nav2 {
            height: 265px;
            .title {
                align-items: center;
                .check {
                    height: 23px;
                    line-height: 23px;
                    margin-top: 6px;
                }
            }
        }
        .nav3 {
            height: 249px;
            .title {
                align-items: center;
                .check {
                    height: 23px;
                    line-height: 23px;
                    margin-top: 6px;
                }
            }
        }

        .but {
            width: 660px;
            text-align: center;

            /deep/.el-button--small,
            /deep/.el-button--small.is-round {
                padding: 12px 15px;
            }
        }
    }
    .headTitle {
        position: absolute;
        left: 660px;
        top: 10px;
        font-size: 24px;
        font-weight: 550;
        color: #409eff;
    }
}
</style>