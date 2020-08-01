<template>
    <div class="cargoPhoto">
        <el-dialog
            title="派车"
            :visible.sync="editVisible"
            width="800px"
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
                    <div class="nav">
                        <div class="left">
                            <div class="search">
                                <el-input
                                    v-model="input"
                                    placeholder="请输入姓名或手机号码"
                                    style="width:200px"
                                    :maxlength="11"
                                ></el-input>
                                <el-button
                                    class="el-but"
                                    type="success"
                                    @click="search"
                                    :disabled="button1"
                                >搜索</el-button>
                            </div>
                            <div class="show1" v-if="show1">
                                <el-table
                                    :data="driverList"
                                    border
                                    class="table"
                                    ref="multipleTable"
                                    header-cell-class-name="table-header"
                                    highlight-current-row
                                    :show-header="false"
                                    @row-click="leftClick"
                                >
                                    <el-table-column
                                        prop="cn"
                                        width="100"
                                        align="center"
                                        :show-overflow-tooltip="true"
                                    ></el-table-column>
                                    <el-table-column
                                        prop="ne"
                                        width="100"
                                        align="center"
                                        :show-overflow-tooltip="true"
                                    ></el-table-column>

                                    <el-table-column
                                        prop="ph"
                                        align="center"
                                        :show-overflow-tooltip="true"
                                    ></el-table-column>
                                </el-table>
                            </div>
                            <div class="cartImg" v-if="show1">
                                <img
                                    :src="item"
                                    preview="1"
                                    preview-textss
                                    alt
                                    v-for="(item,index) in cargoPicture"
                                    :key="index"
                                />
                            </div>
                            <div class="show2" v-if="show2">
                                <div class="container">
                                    <!-- 文件上传得主要功能部分 -->
                                    <el-upload
                                        action
                                        ref="driveFile"
                                        :auto-upload="false"
                                        list-type="picture-card"
                                        :on-preview="handlePictureCardPreview"
                                        :on-change="getDrive"
                                    >
                                        <i class="el-icon-plus"></i>
                                    </el-upload>
                                </div>
                                <div class="container">
                                    <!-- 文件上传得主要功能部分 -->
                                    <el-upload
                                        action
                                        ref="driveFile"
                                        :auto-upload="false"
                                        list-type="picture-card"
                                        :on-preview="handlePictureCardPreview"
                                        :on-change="getVehicle"
                                    >
                                        <i class="el-icon-plus"></i>
                                    </el-upload>
                                </div>
                            </div>
                            <div class="cartType" v-if="show2">
                                <el-select
                                    v-model="form.car_type"
                                    placeholder="请选择"
                                    @change="change1"
                                >
                                    <el-option
                                        v-for="item in car_types"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                    ></el-option>
                                </el-select>
                                <el-select
                                    v-model="form.car_length"
                                    placeholder="请选择"
                                    @change="change2"
                                >
                                    <el-option
                                        v-for="item in car_long"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                    ></el-option>
                                </el-select>
                            </div>
                            <div class="edit" v-if="show2">
                                <el-button
                                    size="small"
                                    style="width:100px;"
                                    @click="addDrive"
                                    :disabled="button2"
                                >添加</el-button>
                            </div>
                        </div>
                        <div class="right">
                            <el-tabs class="tabs" v-model="activeName" @tab-click="handleClick">
                                <el-tab-pane class="first" label="普通派车" name="first">
                                    <div class="time">
                                        <div class="title">运单信息</div>
                                        <el-form
                                            :label-position="labelPosition"
                                            label-width="80px"
                                            :model="ordinaryForm"
                                        >
                                            <el-form-item label="装货时间" :rules="[{ required: true}]">
                                                <el-date-picker
                                                    v-model="ordinaryForm.startTime"
                                                    type="date"
                                                    placeholder="选择日期"
                                                    value-format="yyyy-MM-dd"
                                                    :disabled="order1.checked1"
                                                ></el-date-picker>
                                            </el-form-item>
                                            <el-form-item label="卸货时间">
                                                <el-date-picker
                                                    v-model="ordinaryForm.endTime"
                                                    type="date"
                                                    placeholder="选择日期"
                                                    value-format="yyyy-MM-dd"
                                                ></el-date-picker>
                                            </el-form-item>
                                            <el-form-item label="装货重量" :rules="[{ required: true}]">
                                                <el-input-number
                                                    v-model="ordinaryForm.dw"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="99999"
                                                ></el-input-number>&nbsp; 吨
                                            </el-form-item>
                                            <el-form-item label="装货体积">
                                                <el-input-number
                                                    v-model="ordinaryForm.dv"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                ></el-input-number>&nbsp; 方
                                            </el-form-item>
                                        </el-form>
                                    </div>

                                    <div class="price">
                                        <div class="title">司机费用</div>
                                        <el-form
                                            :label-position="labelPosition"
                                            label-width="80px"
                                            :model="formLabelAlign"
                                        >
                                            <el-form-item
                                                label="司机服务费"
                                                :rules="[{ required: true}]"
                                            >
                                                <el-input-number
                                                    v-model="ordinaryForm.service"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="9999999"
                                                ></el-input-number>
                                            </el-form-item>
                                            <el-form-item label="司机运费" :rules="[{ required: true}]">
                                                <el-input-number
                                                    v-model="ordinaryForm.freight"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="9999999"
                                                ></el-input-number>
                                            </el-form-item>
                                        </el-form>
                                    </div>
                                </el-tab-pane>
                                <el-tab-pane class="second" label="合同派车" name="second">
                                    <div class="time">
                                        <div class="title">运单信息</div>
                                        <el-form
                                            :label-position="labelPosition"
                                            label-width="80px"
                                            :model="formLabelAlign"
                                        >
                                            <el-form-item label="装货时间" :rules="[{ required: true}]">
                                                <el-date-picker
                                                    v-model="formLabelAlign.startTime"
                                                    type="date"
                                                    placeholder="选择日期"
                                                    value-format="yyyy-MM-dd"
                                                    :disabled="order1.checked1"
                                                ></el-date-picker>
                                            </el-form-item>
                                            <el-form-item label="卸货时间">
                                                <el-date-picker
                                                    v-model="formLabelAlign.endTime"
                                                    type="date"
                                                    placeholder="选择日期"
                                                    value-format="yyyy-MM-dd"
                                                ></el-date-picker>
                                            </el-form-item>
                                            <el-form-item label="装货重量" :rules="[{ required: true}]">
                                                <el-input-number
                                                    v-model="formLabelAlign.dw"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="99999"
                                                ></el-input-number>&nbsp; 吨
                                            </el-form-item>
                                            <el-form-item label="装货体积">
                                                <el-input-number
                                                    v-model="formLabelAlign.dv"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="99999"
                                                ></el-input-number>&nbsp; 方
                                            </el-form-item>
                                        </el-form>
                                    </div>

                                    <div class="price">
                                        <div class="title">司机费用</div>
                                        <el-form
                                            :label-position="labelPosition"
                                            label-width="80px"
                                            :model="formLabelAlign"
                                        >
                                            <el-form-item
                                                label="司机服务费"
                                                :rules="[{ required: true}]"
                                            >
                                                <el-input-number
                                                    v-model="formLabelAlign.service"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="9999999"
                                                ></el-input-number>
                                            </el-form-item>
                                            <el-form-item label="司机运费" :rules="[{ required: true}]">
                                                <el-input-number
                                                    v-model="formLabelAlign.freight"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="9999999"
                                                ></el-input-number>
                                            </el-form-item>

                                            <el-form-item label="回单押金">
                                                <el-input-number
                                                    v-model="formLabelAlign.deposit"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="9999999"
                                                ></el-input-number>
                                            </el-form-item>
                                            <el-form-item label="油卡费">
                                                <el-input-number
                                                    v-model="formLabelAlign.oilCard"
                                                    controls-position="right"
                                                    :step="50"
                                                    style="width:220px;"
                                                    :min="0"
                                                    :max="9999999"
                                                ></el-input-number>
                                            </el-form-item>
                                        </el-form>
                                    </div>
                                    <div class="priceAll">
                                        <div class="title">总运费</div>
                                        <div class="form">
                                            <el-form
                                                :label-position="labelPosition"
                                                label-width="80px"
                                                :model="formLabelAlign"
                                            >
                                                <el-form-item label="佣金">
                                                    <el-input-number
                                                        v-model="formLabelAlign.customerService"
                                                        controls-position="right"
                                                        :step="50"
                                                        style="width:220px;"
                                                        :min="0"
                                                        :max="9999999"
                                                    ></el-input-number>
                                                </el-form-item>
                                                <el-form-item label="保险费">
                                                    <el-input-number
                                                        v-model="formLabelAlign.insurance"
                                                        controls-position="right"
                                                        :step="50"
                                                        style="width:220px;"
                                                        :min="0"
                                                        :max="9999999"
                                                    ></el-input-number>
                                                </el-form-item>
                                                <el-form-item label="货主运费">
                                                    <el-input
                                                        v-model="formLabelAlign.totalPrice"
                                                        style="width:220px;"
                                                        :min="0"
                                                        :max="9999999"
                                                    ></el-input>
                                                </el-form-item>
                                                <el-form-item label>
                                                    <el-checkbox
                                                        style="margin-left:20px;"
                                                        v-model="order1.checked1"
                                                    >开具增值税发票(当前税率{{formLabelAlign.txr}})</el-checkbox>
                                                </el-form-item>
                                            </el-form>
                                        </div>
                                    </div>
                                </el-tab-pane>
                            </el-tabs>
                        </div>
                    </div>
                </el-main>
                <el-footer style="height:50px;">
                    <el-button
                        size="small"
                        style="width:100px;"
                        @click="assign"
                        :disabled="button3"
                    >指派</el-button>
                    <el-button size="small" @click="cancel" style="width:100px;">取消</el-button>
                </el-footer>
            </el-container>
        </el-dialog>
    </div>
</template>
<script>
export default {
    data() {
        return {
            button1: false,
            button2: false,
            button3: false,
            editVisible: false,
            show1: false,
            show2: false,
            other: false,
            priceNum: false,
            imgShow: false,
            zkText1: '展开',
            zkText2: '展开',
            _token: '',
            head: {
                start: '',
                end: '',
                dis: ''
            },
            popupData: {},
            activeName: 'first',
            dialogVisible: false,

            input: '',
            popupData: {},

            price: 0,
            row: {},
            cargoPicture: [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')],
            labelPosition: 'right',
            ordinaryForm: {
                startTime: '',
                endTime: '',
                dw: '',
                dv: '',
                service: '',
                freight: ''
            },
            formLabelAlign: {
                startTime: '',
                endTime: '',
                dw: '',
                dv: '',
                service: '',
                freight: '',
                deposit: 0,
                oilCard: 0,
                totalPrice: '',
                customerService: '',
                insurance: 0,
                price: '',
                txr: ''
            },
            order1: {
                checked1: false,
                checked2: true
            },

            activeName: 'first',
            loginData: {},
            cart: {
                cartNum: '',
                name: '',
                phone: ''
            },
            form: {
                ne: '',
                phone: '',
                userID: '',
                numberPlate: '',
                Identifier: '',
                car_type: '',
                car_length: '',
                jpc: '',
                xpc: '',
                src_jpc: '',
                src_xpc: '',
                cartType: '',
                cartLength: ''
            },
            judge1: false,
            judge2: false,
            did: '',
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
            ],
            driverList: []
        };
    },
    created() {
        this.getToken();
    },
    methods: {
        //点击弹窗事件
        receipt(index, row) {
            this.editVisible = true;
            this.popupData = this.$store.state.Popup.popup2;
            console.log(this.popupData);
            this.head.start = this.popupData.start;
            this.head.end = this.popupData.end;
            this.head.dis = this.popupData.dis;
            this.formLabelAlign.startTime = this.popupData.zdy;

            this.formLabelAlign.freight = this.popupData.qyf;
            this.formLabelAlign.txr = this.popupData.txr;
            this.ordinaryForm.startTime = this.popupData.zdy;
            if (this.popupData.dw.indexOf('-') == -1) {
                this.ordinaryForm.dw = this.popupData.dw;
                this.ordinaryForm.dv = this.popupData.dv;
                this.formLabelAlign.dw = this.popupData.dw;
                this.formLabelAlign.dv = this.popupData.dv;
            } else {
                let dwArr = this.popupData.dw.split('-');
                this.ordinaryForm.dw = dwArr[1];
                let dvArr = this.popupData.dv.split('-');
                this.ordinaryForm.dv = dvArr[1];
                this.formLabelAlign.dw = dwArr[1];
                this.formLabelAlign.dv = dvArr[1];
            }

            this.ordinaryForm.freight = this.popupData.qyf;
            this.search();
        },
        //获取百度识图的token
        getToken() {
            var API_Key = 'xfL8VlHVjY1ubdZU8tpyhzoN';
            var Secret_Key = '4u8FDsdEUE2PPazEOSvyrfLZvmapPpky';
            var url = 'https://aip.baidubce.com/oauth/2.0/token';
            this.$axios
                .get(`/baidu_token/oauth/2.0/token?grant_type=client_credentials&client_id=${API_Key}&client_secret=${Secret_Key}`)
                .then(res => {
                    this._token = res.data.access_token;
                });
        },
        //取消事件
        cancel() {
            this.editVisible = false;
            this.row = {};
            this.cargoPicture = [require('../../assets/img/frame.png'), require('../../assets/img/frame.png')];
            this.searchTable = [];
            this.judge1 = false;
            this.judge2 = false;
            this.show1 = false;
            this.show2 = false;
            this.input = '';
            this.imgShow = false;
            this.formLabelAlign = {
                startTime: '',
                endTime: '',
                dw: '',
                dv: '',
                price: ''
            };
            this.form = {
                ne: '',
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
            };
            this.order1 = {
                Shipper: '',

                card: '',
                checked1: false,
                checked2: false,
                cash: '',
                money: ''
            };
            this.did = '';
        },
        //确定事件
        assign() {
            let action = this.$store.state.Login.url + '/20019';
            console.log(this.did.length);
            if (this.activeName == 'first') {
                if (this.did.length == 0) {
                    this.open2('请先确定派车司机');

                    this.button3 = true;
                    setTimeout(() => {
                        this.button3 = false;
                    }, 1000);
                    return;
                }
                if (this.ordinaryForm.dw.length == 0) {
                    this.open2('重量为必填项');
                    this.button3 = true;
                    setTimeout(() => {
                        this.button3 = false;
                    }, 1000);
                    return;
                }
                if (this.ordinaryForm.freight.length == 0) {
                    this.open2('司机运费不能为空');
                    this.button3 = true;
                    setTimeout(() => {
                        this.button3 = false;
                    }, 1000);
                    return;
                }
                if (this.ordinaryForm.service.length == 0) {
                    this.open2('司机服务费不能为空');
                    this.button3 = true;
                    setTimeout(() => {
                        this.button3 = false;
                    }, 1000);
                    return;
                } else {
                    let bd = {
                        tid: this.popupData.tid,
                        oid: this.popupData.oid,
                        did: this.did,
                        wt: 1,
                        zte: this.ordinaryForm.startTime,
                        xte: this.ordinaryForm.endTime,
                        dw: this.ordinaryForm.dw,
                        dv: this.ordinaryForm.dv,
                        yf: this.ordinaryForm.freight,
                        dyf: this.ordinaryForm.freight,
                        ins: 0,
                        oil: 0,
                        isin: 0,
                        tax: 0,
                        hdyf: 0,
                        dch: this.ordinaryForm.service,
                        ishd: 0,
                        isds: 0,
                        cch: 0,
                        dc: ''
                    };
                    this.$axios
                        .post(action, {
                            hd: {
                                pi: 20019,
                                si: this.popupData.si,
                                sq: 9
                            },
                            bd: bd
                        })
                        .then(res => {
                            // console.log(res);

                            let rid = res.data.hd.rid;
                            if (rid >= 0) {
                                this.open1('派车成功');
                                this.cancel();
                                this.$emit('getData');
                            } else {
                                this.open2(res.data.hd.rmsg);
                            }
                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            }
            if (this.activeName == 'second') {
                if (this.did.length == 0) {
                    this.open2('请先确定派车司机');

                    this.button3 = true;
                    setTimeout(() => {
                        this.button3 = false;
                    }, 1000);
                    return;
                }
                if (this.formLabelAlign.dw.length == 0) {
                    this.open2('重量为必填项');
                    this.button3 = true;
                    setTimeout(() => {
                        this.button3 = false;
                    }, 1000);
                    return;
                }
                if (this.formLabelAlign.freight <= 0) {
                    this.open2('司机运费不能为空');
                    this.button3 = true;
                    setTimeout(() => {
                        this.button3 = false;
                    }, 1000);
                    return;
                }
                if (this.formLabelAlign.service <= 0) {
                    this.open2('司机服务费不能为空');
                    this.button3 = true;
                    setTimeout(() => {
                        this.button3 = false;
                    }, 1000);
                    return;
                } else {
                    // if (this.order1.checked1 == false) {
                    // }
                    // if (this.order1.checked1 == true) {
                    //     this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice / (1 - this.formLabelAlign.txr * 1);
                    //     this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice.toFixed(2);
                    // }
                    let bd = {
                        tid: this.popupData.tid,
                        oid: this.popupData.oid,
                        did: this.did,
                        wt: 2,
                        zte: this.formLabelAlign.startTime,
                        xte: this.formLabelAlign.endTime,
                        dw: this.formLabelAlign.dw,
                        dv: this.formLabelAlign.dv,
                        yf: this.formLabelAlign.totalPrice,
                        dyf: this.formLabelAlign.freight,
                        ins: this.formLabelAlign.insurance,
                        oil: this.formLabelAlign.oilCard,
                        isin: this.order1.checked1 ? 1 : 0,
                        tax: 0,
                        hdyf: this.formLabelAlign.deposit,
                        dch: this.formLabelAlign.service,
                        ishd: 0,
                        isds: 0,
                        cch: this.formLabelAlign.customerService,
                        dc: ''
                    };
                    this.$axios
                        .post(action, {
                            hd: {
                                pi: 20019,
                                si: this.popupData.si,
                                sq: 9
                            },
                            bd: bd
                        })
                        .then(res => {
                            // console.log(res);
                            let rid = res.data.hd.rid;
                            if (rid >= 0) {
                                this.open1('派车成功');
                                this.cancel();
                                this.$emit('getData');
                                this.formLabelAlign = {
                                    startTime: '',
                                    endTime: '',
                                    dw: '',
                                    dv: '',
                                    service: '',
                                    freight: '',
                                    deposit: 0,
                                    oilCard: 0,
                                    totalPrice: '',
                                    customerService: '',
                                    insurance: 0,
                                    price: '',
                                    txr: ''
                                };
                            } else {
                                this.open2(res.data.hd.rmsg);
                            }
                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            }

            this.button3 = true;
            setTimeout(() => {
                this.button3 = false;
            }, 1000);
        },
        //司机搜索
        search() {
            let action = this.$store.state.Login.url + '/30073';
            this.loginData = this.$store.state.Login.DispatchData;
            var bd = {
                tid: this.popupData.tid,
                im: 0,
                ss: this.input,
                pg: 1,
                sz: 100
            };

            this.$axios
                .post(action, {
                    hd: {
                        pi: 30073,
                        si: this.popupData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    let rid = res.data.hd.rid;

                    if (rid >= 0) {
                        let data = JSON.parse(res.data.bd);
                        let tableData = data.olst;
                        // console.log(tableData);
                        if (tableData.length == 0) {
                            this.open1('此司机不是本平台司机，请先添加');
                            this.show2 = true;
                            this.show1 = false;
                        } else {
                            this.show1 = true;
                            this.show2 = false;
                            this.judge1 = true;
                            this.judge2 = false;

                            this.driverList = tableData;
                            // console.log(this.driverList);
                        }
                    } else {
                        this.open2(res.data.hd.rmsg);
                    }
                })
                .catch(error => {
                    console.log(error);
                });

            this.button1 = true;
            setTimeout(() => {
                this.button1 = false;
            }, 1000);
        },
        //点击某行
        leftClick(row, event, column) {
            this.did = row.tid;
            this.cart.cartNum = row.cn;
            this.cart.name = row.dne;
            this.cart.phone = row.dph;
            console.log(row);
            if (row.xpc && row.jpc) {
                this.cargoPicture = [this.loginData.murl + row.xpc, this.loginData.murl + row.jpc];
                this.imgShow = true;
            }
            if (row.xpc && row.jpc.length == 0) {
                this.cargoPicture = [this.loginData.murl + row.xpc];
                this.imgShow = true;
            }
            if (row.xpc.length == 0 && row.jpc) {
                this.cargoPicture = [this.loginData.murl + row.jpc];
                this.imgShow = true;
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
        },
        //base64方法
        getBase64(file) {
            return new Promise(function(resolve, reject) {
                let reader = new FileReader();
                let imgResult = '';
                reader.readAsDataURL(file);
                reader.onload = function() {
                    imgResult = reader.result;
                };
                reader.onerror = function(error) {
                    reject(error);
                };
                reader.onloadend = function() {
                    resolve(imgResult);
                };
            });
        },
        //驾驶证
        getVehicle(file, fileList) {
            // this.uploadDisabled2 = fileList.length >= 1;
            this.getBase64(file.raw).then(res => {
                this.form.xpc = res;
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/vehicle_license?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then(res => {
                    console.log(res);
                    this.form.numberPlate = res.data.words_result.号牌号码.words;
                    // this.form.Identifier = res.data.words_result.车辆识别代号.words;
                    console.log(this.form.numberPlate);
                    // // console.log(res.data.words_result.证号.words);
                });

                let action = `${this.$store.state.Login.url}/60001`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 7,
                    img: imgBase64,
                    pn: ''
                };
                // console.log(this.loginData.tid, this.phone,this.loginData.si);
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60001,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let data = JSON.parse(res.data.bd);
                        this.form.xpc = data.url;
                        console.log(data.url);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        //行驶证
        getDrive(file, fileList) {
            // this.uploadDisabled = fileList.length >= 1;

            this.getBase64(file.raw).then(res => {
                // this.form.jpc = res;
                var imgBase64 = res.replace(/^data:image\/\w+;base64,/, '');
                this.$axios({
                    url: `/baidu_token/rest/2.0/ocr/v1/driving_license?access_token=${this._token}`,
                    method: 'POST',
                    data: `image=${encodeURIComponent(imgBase64)}`,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then(res => {
                    console.log(res.data);

                    this.form.ne = res.data.words_result.姓名.words;
                    this.form.userID = res.data.words_result.证号.words;
                });

                let action = `${this.$store.state.Login.url}/60001`;
                this.loginData = this.$store.state.Login.DispatchData;
                var bd = {
                    tid: this.loginData.tid,
                    pte: 6,
                    img: imgBase64,
                    pn: ''
                };
                // console.log(this.loginData.tid, this.phone,this.loginData.si);
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 60001,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        // console.log(res);
                        let data = JSON.parse(res.data.bd);
                        this.form.jpc = data.url;
                        console.log(data.url);
                        // this.drive_fileList = fileList;
                        // console.log(fileList);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            });
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        //添加司机
        addDrive() {
            let action = this.$store.state.Login.url + '/40001';
            this.loginData = this.$store.state.Login.DispatchData;

            if (this.form.jpc.length == 0) {
                this.open2('请上传驾驶证');
                this.button2 = true;
                setTimeout(() => {
                    this.button2 = false;
                }, 1000);
                return;
            }
            if (this.form.xpc.length == 0) {
                this.open2('请上传行驶证');
                this.button2 = true;
                setTimeout(() => {
                    this.button2 = false;
                }, 1000);
                return;
            }
            if (this.form.cartType.length == 0) {
                this.open2('请选择车型');
                this.button2 = true;
                setTimeout(() => {
                    this.button2 = false;
                }, 1000);
                return;
            }
            if (this.form.cartLength.length == 0) {
                this.open2('请选择车长');
                this.button2 = true;
                setTimeout(() => {
                    this.button2 = false;
                }, 1000);
                return;
            } else {
                var bd = {
                    tid: this.popupData.tid,
                    ne: this.form.ne,
                    ph: this.input,
                    idc: this.form.userID,
                    cn: this.form.numberPlate,
                    xpc: this.form.xpc,
                    jpc: this.form.jpc,
                    dc: '',
                    ct: this.form.cartType,
                    cl: this.form.cartLength,
                    cpc: ''
                };

                this.$axios
                    .post(action, {
                        hd: {
                            pi: 40001,
                            si: this.popupData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        console.log(res);

                        let rid = res.data.hd.rid;
                        let data = JSON.parse(res.data.bd);
                        this.did = data.cid;
                        console.log(rid);
                        if (rid >= 0) {
                            this.open1('添加成功');
                            this.judge1 = false;
                            this.judge2 = true;
                            this.cart.cartNum = this.form.numberPlate;
                            this.cart.name = this.form.ne;
                            this.cart.phone = this.input;
                            this.cargoPicture = [this.loginData.murl + this.form.xpc, this.loginData.murl + this.form.jpc];
                            this.form.car_type = '';
                            this.form.car_length = '';
                            this.form.userID = '';

                            this.form.numberPlate = '';
                            this.form.xpc = '';
                            this.form.jpc = '';

                            this.show2 = false;
                            this.show1 = true;
                        } else {
                            this.open2(res.data.hd.rmsg);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
            this.button2 = true;
            setTimeout(() => {
                this.button2 = false;
            }, 1000);
        },
        change1(value) {
            this.form.cartType = value;
        },
        change2(value) {
            this.form.cartLength = value;
        },
        zk1() {
            if (this.zkText1 == '展开') {
                this.other = true;
                this.zkText1 = '收起';
            } else {
                this.other = false;
                this.zkText1 = '展开';
            }
        },
        zk2() {
            if (this.zkText2 == '展开') {
                this.priceNum = true;
                this.zkText2 = '收起';
            } else {
                this.priceNum = false;
                this.zkText2 = '展开';
            }
        },
        handleClick(tab, event) {
            if (tab.label == '普通派车') {
                this.formLabelAlign = {
                    startTime: this.popupData.zdy,
                    endTime: '',
                    dw: this.popupData.dw,
                    dv: this.popupData.dv,
                    service: '',
                    freight: this.popupData.qyf,
                    deposit: 0,
                    oilCard: 0,
                    totalPrice: '',
                    customerService: '',
                    insurance: 0,
                    price: ''
                };
            }
            if (tab.label == '合同派车') {
                this.ordinaryForm = {
                    startTime: this.popupData.zdy,
                    endTime: '',
                    dw: this.popupData.dw,
                    dv: this.popupData.dv,
                    service: '',
                    freight: this.popupData.qyf
                };
            }
            console.log(tab.label);
        }
    },
    mounted() {},
    watch: {
        'formLabelAlign.freight': function(newVal) {
            this.formLabelAlign.totalPrice = newVal * 1 + this.formLabelAlign.customerService * 1 + this.formLabelAlign.insurance * 1;
            if (this.order1.checked1 == true) {
                this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice / (1 - this.formLabelAlign.txr * 1);
                // this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice.toFixed(2);
                console.log(this.formLabelAlign.totalPrice);
                this.formLabelAlign.totalPrice = Math.round(this.formLabelAlign.totalPrice * 100) / 100;
            }
            if (this.order1.checked1 == false) {
                this.formLabelAlign.totalPrice =
                    this.formLabelAlign.freight * 1 + this.formLabelAlign.customerService * 1 + this.formLabelAlign.insurance * 1;
            }

            // this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice.toFixed(2);
        },
        'formLabelAlign.customerService': function(newVal) {
            this.formLabelAlign.totalPrice = this.formLabelAlign.freight * 1 + newVal * 1 + this.formLabelAlign.insurance * 1;
            if (this.order1.checked1 == true) {
                this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice / (1 - this.formLabelAlign.txr * 1);
                // this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice.toFixed(2);
                console.log(this.formLabelAlign.totalPrice);
                this.formLabelAlign.totalPrice = Math.round(this.formLabelAlign.totalPrice * 100) / 100;
            }
            if (this.order1.checked1 == false) {
                this.formLabelAlign.totalPrice =
                    this.formLabelAlign.freight * 1 + this.formLabelAlign.customerService * 1 + this.formLabelAlign.insurance * 1;
            }
            // this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice.toFixed(2);
        },
        'formLabelAlign.insurance': function(newVal) {
            this.formLabelAlign.totalPrice = this.formLabelAlign.freight * 1 + this.formLabelAlign.customerService * 1 + newVal;
            if (this.order1.checked1 == true) {
                this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice / (1 - this.formLabelAlign.txr * 1);
                // this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice.toFixed(2);
                console.log(this.formLabelAlign.totalPrice);
                this.formLabelAlign.totalPrice = Math.round(this.formLabelAlign.totalPrice * 100) / 100;
            }
            if (this.order1.checked1 == false) {
                this.formLabelAlign.totalPrice =
                    this.formLabelAlign.freight * 1 + this.formLabelAlign.customerService * 1 + this.formLabelAlign.insurance * 1;
            }
            // this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice.toFixed(2);
        },
        'order1.checked1': function(newVal) {
            console.log(newVal);
            if (newVal == true) {
                this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice / (1 - this.formLabelAlign.txr * 1);
                // this.formLabelAlign.totalPrice = this.formLabelAlign.totalPrice.toFixed(2);
                console.log(this.formLabelAlign.totalPrice);
                this.formLabelAlign.totalPrice = Math.round(this.formLabelAlign.totalPrice * 100) / 100;
            }
            if (newVal == false) {
                this.formLabelAlign.totalPrice =
                    this.formLabelAlign.freight * 1 + this.formLabelAlign.customerService * 1 + this.formLabelAlign.insurance * 1;
            }
        }
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
            font-size: 16px;
        }
        /deep/.el-dialog__title {
            color: #fff;
        }
        /deep/.el-dialog__headerbtn {
            top: 8px !important;
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
        height: 680px;
        /deep/.el-form-item__label {
            width: 100px !important;
        }

        .el-main {
            flex: 1;
            padding: 0;
            padding-bottom: 10px;
            .head {
                height: 110px;
                padding-bottom: 0;
                width: 100%;
                margin: auto;
                background: #009dd9;
                display: flex;
                justify-content: space-around;
                align-items: center;
                padding: 0 60px;
                box-sizing: border-box;
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
                        left: 47.2%;
                    }
                }
            }
            .nav {
                display: flex;
                height: 500px;
                width: 100%;
                box-sizing: border-box;
                .left {
                    width: 50%;
                    height: 100%;
                    padding: 10px 10px;
                    border-right: 1px solid #ccc;
                    .search {
                        text-align: center;
                        padding-bottom: 10px;
                        // border-bottom: 1px solid #ccc;
                        .el-but {
                            margin-left: 20px;
                        }
                    }
                    .information {
                        display: flex;
                        justify-content: center;
                        padding-top: 30px;
                        padding-bottom: 30px;
                        font-size: 16px;
                        line-height: 16px;
                        font-weight: 550;
                        .plate,
                        .name,
                        .phone {
                            margin-right: 10px;
                        }
                    }
                    .cartImg {
                        flex: 1;
                        display: flex;
                        justify-content: space-around;
                        margin-top: 10px;
                        padding-top: 10px;
                        border-top: 1px solid #ccc;

                        img {
                            width: 130px;
                            height: 130px;
                        }
                    }
                    .show1 {
                        height: 280px;
                        overflow: scroll;
                        border-bottom: 0.5px solid #ebeef5;
                        border-top: 0.5px solid #ebeef5;
                    }
                    .show1::-webkit-scrollbar {
                        display: none;
                    }
                    .show2 {
                        display: flex;
                        .container {
                            width: 148px;
                            height: 148px;
                            border: none;
                            overflow: hidden;
                            // box-sizing: border-box;
                            padding: 0;
                            margin-left: 35px;
                            margin-top: 35px;
                            /deep/.el-upload .el-upload--picture-card {
                                display: none;
                            }
                            /deep/.el-upload-list__item-preview {
                                display: none;
                            }
                            /deep/.el-upload-list__item-delete {
                                text-align: center;
                                margin-left: 0;
                            }
                        }
                    }
                    .edit {
                        text-align: center;
                        margin-top: 50px;
                    }
                    .cartType {
                        text-align: left;
                        margin-top: 10px;
                        margin-left: 33px;
                        .el-select {
                            margin-top: 10px;
                        }
                    }
                }
                .right {
                    width: 50%;
                    height: 100%;
                    padding: 0 15px;

                    .tabs {
                        /deep/.el-tabs__nav-scroll {
                            padding: 0 29.8%;
                        }
                        /deep/.el-tabs__header {
                            margin: 0;
                        }
                        .first {
                            height: 466px;
                            overflow: scroll;
                        }
                        .first::-webkit-scrollbar {
                            display: none;
                        }
                        .second {
                            height: 466px;
                            overflow: scroll;
                        }
                        .second::-webkit-scrollbar {
                            display: none;
                        }
                    }

                    .el-form-item {
                        margin-bottom: 10px;
                    }
                    .title {
                        font-size: 18px;
                        line-height: 18px;
                        color: rgba($color: #000000, $alpha: 0.5);
                        padding-top: 10px;
                        padding-bottom: 10px;
                        text-align: center;
                    }
                    .time {
                        padding-bottom: 10px;
                        border-bottom: 1px solid #ccc;
                        /deep/.el-form-item--mini.el-form-item,
                        .el-form-item--small.el-form-item {
                            margin-top: 10px;
                            margin-bottom: 0;
                        }
                    }
                    .weight {
                        display: flex;
                        align-items: center;
                        // margin-left: 9px;
                        line-height: 33px;
                        padding: 5px 0;
                        border-bottom: 1px solid #ccc;
                        /deep/.el-form-item--mini.el-form-item,
                        .el-form-item--small.el-form-item {
                            margin-top: 0px;
                            margin-bottom: 5px;
                        }
                        .s0 {
                            margin-top: 7px;
                        }
                        .s2 {
                            font-size: 12px;
                            color: rgba($color: #000000, $alpha: 0.5);
                            margin-left: 10px;
                        }
                    }
                    .price,
                    .other {
                        padding: 5px 0;

                        /deep/.el-form-item--mini.el-form-item,
                        .el-form-item--small.el-form-item {
                            margin-top: 0px;
                            margin-bottom: 5px;
                        }
                        .zk {
                            margin-left: 5px;
                        }
                        .check {
                            margin-left: 20px;
                        }
                    }
                    .other {
                        border: 1px solid #ccc;
                        border-radius: 5px;
                    }
                    .priceAll {
                        padding-top: 5px;
                        margin-top: 10px;
                        border-top: 1px solid #ccc;
                    }
                    .priceNum {
                        border: 1px solid #ccc;
                        border-radius: 5px;
                        margin-bottom: 10px;
                        /deep/ .el-tabs__nav-scroll {
                            overflow: hidden;
                            padding: 0 30%;
                        }
                        .tabs {
                            /deep/.el-tabs__header {
                                margin: 0 0 5px;
                            }
                        }
                        .form {
                            /deep/.el-form-item--mini.el-form-item,
                            .el-form-item--small.el-form-item {
                                margin-top: 5px;
                                margin-bottom: 0px;
                            }
                            /deep/.el-form-item__content {
                                margin-left: 100px !important;
                            }
                        }
                    }
                }
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

