<template>
    <el-container>
        <el-main>
            <div class="DeliverGoods">
                <div class="customer">
                    <div class="left">
                        <div class="icon">
                            <img src="../../assets/img/time.png" alt />
                        </div>
                        <div class="name">选择客户</div>
                    </div>
                    <div class="right" v-if="rightShow">
                        <div class="search">
                            <el-select
                                v-model="value1"
                                filterable
                                @change="customerChange"
                                placeholder="请选择"
                            >
                                <el-option
                                    v-for="item in customerList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                ></el-option>
                            </el-select>

                            <el-button
                                type="primary"
                                class="button1"
                                @click="add"
                                :disabled="button1"
                            >添加</el-button>
                            <span class="s1">(客户必填)</span>
                        </div>
                    </div>
                    <div class="addShow" v-if="formShow">
                        <el-form
                            ref="form"
                            :rules="rules"
                            :model="form"
                            label-width="80px"
                            class="form"
                        >
                            <el-form-item label="姓名" prop="name">
                                <el-input v-model="form.name"></el-input>
                            </el-form-item>
                            <el-form-item label="手机号码" prop="phone">
                                <el-input v-model="form.phone" :maxlength="11"></el-input>
                            </el-form-item>

                            <el-form-item label>
                                <el-button
                                    type="primary"
                                    style="width:120px"
                                    @click="addCustomer('form')"
                                    :disabled="addBut"
                                >添加</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
                <div class="addressModular">
                    <div class="left">
                        <div class="icon">
                            <img src="../../assets/img/address.png" alt />
                        </div>
                        <div class="name">地址信息</div>
                    </div>
                    <div class="right">
                        <div class="start">
                            <div class="title">
                                <span>*</span>起运地：
                            </div>
                            <div class="block">
                                <el-col :span="60">
                                    <el-autocomplete
                                        class="inline-input"
                                        v-model="start"
                                        :fetch-suggestions="querySearch"
                                        placeholder="请输入内容"
                                        :trigger-on-focus="false"
                                        @select="startChang"
                                        style="width:300px;"
                                    ></el-autocomplete>
                                </el-col>
                            </div>
                        </div>
                        <div class="end">
                            <div class="title">
                                <span>*</span>目的地：
                            </div>
                            <div class="block">
                                <el-col :span="60">
                                    <el-autocomplete
                                        class="inline-input"
                                        v-model="end"
                                        :fetch-suggestions="querySearch"
                                        placeholder="请输入内容"
                                        :trigger-on-focus="false"
                                        @select="endChang"
                                        style="width:300px;"
                                    ></el-autocomplete>
                                </el-col>
                            </div>
                        </div>
                        <div class="distance">
                            <div class="title">距离：</div>
                            <div class="block">
                                <el-input size="small" placeholder="请输入内容" v-model="distance"></el-input>
                            </div>
                            <span class="kilometre">公里</span>
                        </div>
                    </div>
                </div>
                <div class="goods">
                    <div class="left">
                        <div class="icon">
                            <img src="../../assets/img/hw.png" alt />
                        </div>
                        <div class="name">货物信息</div>
                    </div>
                    <div class="right">
                        <div class="name">
                            <div class="title">
                                <span>*</span>货物名称：
                            </div>
                            <div class="block" style="width:300px">
                                <el-input
                                    size="small"
                                    placeholder="请输入内容"
                                    v-model="name"
                                    maxlength="8"
                                    minlength="2"
                                ></el-input>
                            </div>
                        </div>
                        <div class="weight">
                            <div class="title">
                                <span>*</span>重量体积：
                            </div>
                            <div class="block">
                                <el-input
                                    size="small"
                                    placeholder="请输入内容"
                                    v-model="weight"
                                    style="width:130px"
                                    maxlength="5"
                                ></el-input>
                                <span class="company" style="margin-left: 5px;margin-right: 20px;">吨</span>
                                <el-input
                                    size="small"
                                    placeholder="请输入内容"
                                    v-model="square"
                                    style="width:130px"
                                    maxlength="5"
                                ></el-input>
                                <span class="company" style="margin-left:5px;">方</span>
                                <span class="tips" style="margin-left:5px;">(重量必填)</span>
                            </div>
                        </div>
                        <div class="price">
                            <div class="title">期望运费：</div>
                            <div class="block">
                                <el-input-number
                                    v-model="price"
                                    controls-position="right"
                                    :step="50"
                                    :min="0"
                                    :max="1000000"
                                ></el-input-number>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="time">
                    <div class="left">
                        <div class="icon">
                            <img src="../../assets/img/time.png" alt />
                        </div>
                        <div class="name">装货时间</div>
                    </div>
                    <div class="right">
                        <div class="loadingDate">
                            <div class="title">
                                <span>*</span>装货日期：
                            </div>
                            <div class="block">
                                <el-date-picker
                                    v-model="time"
                                    type="date"
                                    placeholder="选择日期"
                                    value-format="yyyy-MM-dd"
                                ></el-date-picker>
                            </div>
                        </div>
                        <div class="detailedTime">
                            <div class="title">详细时间：</div>
                            <div class="block" style="width:300px">
                                <el-select v-model="value" placeholder="请选择">
                                    <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.label"
                                    ></el-option>
                                </el-select>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="truck">
                    <div class="left">
                        <div class="icon">
                            <img src="../../assets/img/cart.png" alt />
                        </div>
                        <div class="name">用车需求</div>
                    </div>
                    <div class="right">
                        <div class="type">
                            <div class="title">运输类型：</div>
                            <div class="block">
                                <el-radio-group v-model="truckRadio1" size="mini" :max="1">
                                    <el-radio-button label="整车" style="margin-right:10px;"></el-radio-button>
                                    <el-radio-button label=" 配货 " style="margin-right:20px;"></el-radio-button>
                                </el-radio-group>
                            </div>
                        </div>
                        <div class="type">
                            <div class="title">装卸方式：</div>
                            <div class="block">
                                <el-radio-group v-model="truckRadio2">
                                    <el-radio-button
                                        :label="item"
                                        v-for="(item,index) in dischargeCargo"
                                        :key="index"
                                        style="margin-right:10px;"
                                    ></el-radio-button>
                                </el-radio-group>
                            </div>
                        </div>

                        <div class="vehicleType">
                            <div class="title">车型：</div>
                            <div class="block">
                                <el-checkbox-group
                                    v-model="checkboxGroup3"
                                    :border="false"
                                    size="mini"
                                    :max="3"
                                >
                                    <el-checkbox-button
                                        :label="item"
                                        v-for="(item,index) in truckType"
                                        :key="index"
                                        style="margin-right:10px; margin-bottom: 5px;"
                                        :border="false"
                                    >{{item}}</el-checkbox-button>
                                </el-checkbox-group>
                            </div>
                            <div class="tipa">(可三选)</div>
                        </div>
                        <div class="conductor">
                            <div class="title">车长：</div>
                            <div class="block">
                                <el-checkbox-group v-model="checkboxGroup4" size="mini" :max="3">
                                    <el-checkbox-button
                                        :label="item"
                                        v-for="(item,index) in conductor"
                                        :key="index"
                                        style="margin-right:10px; margin-bottom: 5px;"
                                    >{{item}}</el-checkbox-button>
                                </el-checkbox-group>
                            </div>
                            <div class="tipa">(可三选)</div>
                        </div>
                    </div>
                </div>
                <div class="other">
                    <div class="left">
                        <div class="icon">
                            <img src="../../assets/img/other.png" alt />
                        </div>
                        <div class="name">其他信息</div>
                    </div>
                    <div class="right">
                        <div class="conductor">
                            <div class="title">常规要求：</div>
                            <div class="block">
                                <el-checkbox-group v-model="checkboxGroup5" size="mini">
                                    <el-checkbox-button
                                        :label="item"
                                        v-for="(item,index) in other"
                                        :key="index"
                                        style="margin-right:10px; margin-bottom: 5px;"
                                    >{{item}}</el-checkbox-button>
                                </el-checkbox-group>
                            </div>
                            <div class="tipa">(可多选)</div>
                        </div>
                        <div class="Remarks">
                            <div class="title">备注：</div>
                            <div class="block">
                                <el-input
                                    type="textarea"
                                    :autosize="{ minRows: 6, maxRows: 12}"
                                    placeholder="请输入内容"
                                    v-model="textarea"
                                ></el-input>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </el-main>
        <el-footer>
            <div class="but">
                <el-button type="primary" style="width:120px" @click="place" :disabled="goodsBut">发货</el-button>
            </div>
        </el-footer>
    </el-container>
</template>
<script>
const dischargeList = ['一装一卸', '一装两卸', '两装一卸', '两装两卸'];
const ruckList = ['平板', '高栏', '中栏', '低栏', '高低板', '厢车', '自卸', '保温', '冷藏', '危险品', '集装箱', '特种'];
const conductorList = [
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
];
const otherList = [
    '三不超',
    '禁止配货',
    '购买保险',
    '全程高速',
    '需雨布',
    '需押车',
    '需开票',
    '需回单',
    '有禁区',
    '随时走',
    '全部现金',
    '含油卡'
];
export default {
    data() {
        return {
            options1: [],
            value1: '',
            goodsBut: false,
            button1: false,
            formShow: false,
            rightShow: true,
            tabelShow: false,
            customer: '',
            customerList: [],
            start: '',
            startId: '',
            end: '',
            endId: '',
            distance: '',
            name: '',
            weight: '',
            square: '',
            price: 100,
            input: '',
            cid: '',
            customerList: [],
            checked: false,
            time: '',
            detailedTime: '',
            options: [
                {
                    value: '选项1',
                    label: '00:00-24:00'
                },
                {
                    value: '选项2',
                    label: '00:00-06:00'
                },
                {
                    value: '选项3',
                    label: '06:00-12:00'
                },
                {
                    value: '选项4',
                    label: '12:00-18:00'
                },
                {
                    value: '选项5',
                    label: '18:00-24:00'
                }
            ],
            value: '',
            truckRadio1: '整车',
            truckRadio2: '一装一卸',
            dischargeCargo: dischargeList,
            checkboxGroup3: [],
            truckType: ruckList,
            conductor: conductorList,
            checkboxGroup4: [],
            other: otherList,
            checkboxGroup5: [],
            textarea: '',
            loginData: {},
            form: {
                name: '',
                phone: ''
            },
            addBut: false,
            rules: {
                name: [
                    { required: true, message: '请输入客户姓名', trigger: 'blur' },
                    { min: 2, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请输入手机号码', trigger: 'blur' },
                    { min: 11, max: 11, message: '长度为11位数字字符', trigger: 'blur' }
                ],
                idCart: [
                    { required: true, message: '请输入客户身份证号', trigger: 'blur' },
                    { min: 18, max: 18, message: '长度18位数字字符', trigger: 'blur' }
                ]
            }
        };
    },
    created() {
        this.startProvinceChange();

        this.loginData = this.$store.state.Login.CustomerData;
        this.search();
    },
    methods: {
        //客户
        customerChange(val) {
            this.customerList.map(item => {
                if (val == item.value) {
                    this.cid = item.id;
                }
            });
            console.log(this.cid);
        },

        //地址
        startProvinceChange() {
            var storage = window.localStorage;
            let AddressList = JSON.parse(storage.AddressList);
            this.restaurants = AddressList;
        },
        querySearch(queryString, cb) {
            var restaurants = this.restaurants;
            var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
            // console.log(queryString, cb);
        },
        createStateFilter(queryString) {
            return houseNumber => {
                return houseNumber.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1;
            };
        },
        // loadAll1() {
        //     return this.restaurants;
        // },
        startChang(item) {
            this.startId = item.id + '';

            if (this.startId.length > 0 && this.endId.length > 0) {
                let action = this.$store.state.Login.url + '/10016';

                var bd = {
                    zid: this.startId,
                    xid: this.endId
                };
                console.log(bd);
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 10016,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let rid = res.data.hd.rid;
                        if (rid >= 0) {
                            let data = JSON.parse(res.data.bd);
                            this.distance = data.dis;
                        } else {
                            this.open2(res.data.hd.rmsg);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        },
        endChang(item) {
            this.endId = item.id + '';

            if (this.startId.length > 0 && this.endId.length > 0) {
                let action = this.$store.state.Login.url + '/10016';
                this.loginData = this.$store.state.Login.CustomerData;
                var bd = {
                    tid: this.loginData.tid,
                    zid: this.startId,
                    xid: this.endId
                };
                console.log(bd);
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 10016,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        let rid = res.data.hd.rid;
                        if (rid >= 0) {
                            let data = JSON.parse(res.data.bd);
                            this.distance = data.dis;
                        } else {
                            this.open2(res.data.hd.rmsg);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        },
        loadAll() {
            return this.restaurants;
        },
        leftClick(row) {
            this.cid = row.id;
        },
        //客户搜索
        search() {
            let action = `${this.$store.state.Login.url}/30079`;

            var bd = {
                tid: this.loginData.tid,
                im: 1,
                ss: this.input,
                pg: 1,
                sz: 10
            };
            this.$axios
                .post(action, {
                    hd: {
                        pi: 30079,
                        si: this.loginData.si,
                        sq: 9
                    },
                    bd: bd
                })
                .then(res => {
                    // console.log(res);
                    let rid = res.data.hd.rid;
                    if (rid >= 0) {
                        let data = JSON.parse(res.data.bd);
                        let list = data.olst;
                        // console.log(list);
                        if (list.length == 0) {
                            this.open2('所查询客户为空，请添加');
                            this.rightShow = false;
                            this.formShow = true;
                        } else {
                            list.map((item, index) => {
                                item.value = item.ne + ' ' + item.ph;
                            });
                            this.customerList = list;
                            this.tabelShow = true;
                        }
                    } else {
                        this.open2(res.data.hd.rmsg);
                    }
                })
                .catch(error => {});

            this.button1 = true;
            setTimeout(() => {
                this.button1 = false;
            }, 1000);
        },

        //添加按钮
        add() {
            this.rightShow = false;
            this.formShow = true;
        },
        //添加客户
        addCustomer(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    let action = `${this.$store.state.Login.url}/40022`;
                    
                    var bd = {
                        tid: this.loginData.tid,
                        ne: this.form.name,
                        ph: this.form.phone,
                        idc: '',
                        idpc: '',
                        com: ''
                    };
                    this.$axios
                        .post(action, {
                            hd: {
                                pi: 40022,
                                si: this.loginData.si,
                                sq: 9
                            },
                            bd: bd
                        })
                        .then(res => {
                            // console.log(res);
                            let rid = res.data.hd.rid;
                            if (rid >= 0) {
                                this.open1('添加成功');
                                this.rightShow = true;
                                this.formShow = false;
                                this.value1 = this.form.name + ' ' + this.form.phone;
                                let bd = JSON.parse(res.data.bd);
                                this.cid = bd.cid;
                                console.log(this.cid);
                                this.search();
                            } else {
                                this.open2(res.data.hd.rmsg);
                            }
                        })
                        .catch(error => {});

                    this.button1 = true;
                    setTimeout(() => {
                        this.button1 = false;
                    }, 1000);
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        //发货
        place() {
            let action = this.$store.state.Login.url + '/20001';

            //数据校验
            if (this.startId.length == 0) {
                this.open2('发货地址不能为空');
                this.goodsBut = true;
                setTimeout(() => {
                    this.goodsBut = false;
                }, 1000);
                return;
            }
            if (this.endId.length == 0) {
                this.open2('收货地址不能为空');
                this.goodsBut = true;
                setTimeout(() => {
                    this.goodsBut = false;
                }, 1000);
                return;
            }
            if (this.name.length == 0) {
                this.open2('货物名称不能为空');
                this.goodsBut = true;
                setTimeout(() => {
                    this.goodsBut = false;
                }, 1000);
                return;
            }
            if (this.weight.length == 0) {
                this.open2('货物重量不能为空');
                this.goodsBut = true;
                setTimeout(() => {
                    this.goodsBut = false;
                }, 1000);
                return;
            }
            if (this.price.length == 0) {
                this.open2('期望运费不能为空');
                this.goodsBut = true;
                setTimeout(() => {
                    this.goodsBut = false;
                }, 1000);
                return;
            }
            if (this.cid.length == 0) {
                this.open2('请指定客户');
                this.goodsBut = true;
                setTimeout(() => {
                    this.goodsBut = false;
                }, 1000);
                return;
            }
            if (this.time.length == 0) {
                this.open2('装货日期不能为空');
                this.goodsBut = true;
                setTimeout(() => {
                    this.goodsBut = false;
                }, 1000);
                return;
            } else {
                let zxte;
                if (this.truckRadio2 == '一装一卸') {
                    zxte = 1;
                }
                if (this.truckRadio2 == '一装两卸') {
                    zxte = 2;
                }
                if (this.truckRadio2 == '两装一卸') {
                    zxte = 3;
                }
                if (this.truckRadio2 == '两装两卸') {
                    zxte = 4;
                }
                let cl = '';
                if (this.checkboxGroup4.length == 0) {
                    cl = '';
                }
                if (this.checkboxGroup4.length > 0 && this.checkboxGroup4.length == 1) {
                    cl = this.checkboxGroup4[0];
                    console.log(cl);
                }
                if (this.checkboxGroup4.length >= 2) {
                    this.checkboxGroup4.map(item => {
                        cl += item + ',';
                    });
                    cl = cl.substring(0, cl.length - 1);
                }

                let ct = '';
                if (this.checkboxGroup3.length == 0) {
                    ct = '';
                }
                if (this.checkboxGroup3.length > 0 && this.checkboxGroup4.length == 1) {
                    ct = this.checkboxGroup3[0];
                    console.log(ct);
                }
                if (this.checkboxGroup3.length >= 2) {
                    this.checkboxGroup3.map(item => {
                        ct += item + ',';
                    });
                    ct = ct.substring(0, ct.length - 1);
                }

                let odc = '';
                if (this.checkboxGroup5.length == 0) {
                    odc = '';
                }
                if (this.checkboxGroup5.length > 0 && this.checkboxGroup4.length == 1) {
                    odc = this.checkboxGroup5[0];
                }
                if (this.checkboxGroup5.length >= 2) {
                    this.checkboxGroup5.map(item => {
                        odc += item + ',';
                    });
                }
                odc = odc.substring(0, odc.length - 1);
                console.log(ct, cl);
                var bd = {
                    tid: this.loginData.tid,
                    on: this.name,
                    te: this.truckRadio1 == '整车' ? 1 : 2,
                    pte: this.radio == '平台代付' ? 1 : 2,
                    zxte: zxte,
                    ct: ct,
                    cl: cl,
                    dw: this.weight,
                    dv: this.square,
                    zdy: this.time,
                    zte: this.value,
                    qyf: this.price,
                    yft: '',
                    ismf: 1,
                    dis: this.distance,
                    zid: this.startId,
                    zad: '',
                    xid: this.endId,
                    xad: '',
                    com: '',
                    fhr: '',
                    fhrp: '',
                    shr: '',
                    shrp: '',
                    odc: odc,
                    dc: this.textarea,
                    cid: this.cid,
                    isin: this.checked ? 1 : 0
                };
                console.log(bd);
                this.$axios
                    .post(action, {
                        hd: {
                            pi: 20001,
                            si: this.loginData.si,
                            sq: 9
                        },
                        bd: bd
                    })
                    .then(res => {
                        // console.log(res);
                        let data = res.data;
                        // console.log(data);
                        if (data.hd.rid >= 0) {
                            this.open1('发货成功');
                            this.$router.push('/MyOrder');
                        } else {
                            this.open2(res.data.hd.rmsg);
                        }

                        this.goodsBut = true;
                        setTimeout(() => {
                            this.goodsBut = false;
                        }, 1000);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }

            //车长车型装卸方式
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
    background: #409eff;
}
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
.el-container {
    height: 800px;
    width: 750px;
    padding: 0 10px;
    border: 1px solid #ccc;
    margin: auto;
    .el-main {
        flex: 1;
        border-bottom: 1px solid #ccc;
    }
    .el-main::-webkit-scrollbar {
        display: none;
    }
    .el-footer {
        height: 60px;
        line-height: 60px;
        text-align: center;
        .but {
            text-align: center;
        }
    }
}
.DeliverGoods {
    margin: auto;

    .addressModular {
        display: flex;
        padding: 20px 0;
        border-bottom: 1px solid #ccc;

        .left {
            display: flex;

            justify-content: space-around;
            padding-right: 50px;
            margin-left: 10px;
            .name {
                line-height: 32px;
                margin-left: 10px;
            }
        }
        .right {
            .start {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
                // .block {
                //     width: 150px;
                // }
            }
            .end {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
            }
            .distance {
                display: flex;
                margin-bottom: 10px;
                line-height: 32px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                }
                .kilometre {
                    margin-left: 5px;
                }
            }
        }
    }
    .goods {
        display: flex;
        padding: 20px 0;
        border-bottom: 1px solid #ccc;

        .left {
            display: flex;

            justify-content: space-around;
            padding-right: 50px;
            margin-left: 10px;
            .name {
                line-height: 32px;
                margin-left: 10px;
            }
        }
        .right {
            width: 517px;
            .name {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
                .block {
                    display: flex;
                }
            }
            .weight {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
                .block {
                    .tips {
                        font-size: 12px;
                        color: rgba($color: #000000, $alpha: 0.5);
                        margin-left: 10px;
                    }
                }
            }
            .price {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
            }
            .type {
                display: flex;
                // margin-bottom: 10px;
                // flex-wrap: wrap;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    // font-size: 12px;
                }
                .block {
                    display: flex;
                    flex: 1;
                    align-items: center;
                    .el-radio-group {
                        display: flex;
                    }
                }
            }
            .el-checkbox {
                margin-left: 20px;
            }
        }
    }
    .time {
        display: flex;
        padding: 20px 0;
        border-bottom: 1px solid #ccc;

        .left {
            display: flex;

            justify-content: space-around;
            padding-right: 50px;
            margin-left: 10px;
            .name {
                line-height: 32px;
                margin-left: 10px;
            }
        }
        .right {
            .loadingDate {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
                .block {
                }
            }
            .detailedTime {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
            }
        }
    }
    .customer {
        display: flex;
        padding: 20px 0;
        border-bottom: 1px solid #ccc;

        .left {
            display: flex;

            justify-content: space-around;
            padding-right: 87px;
            margin-left: 10px;
            .name {
                line-height: 32px;
                margin-left: 10px;
            }
        }
        .right {
            .loadingDate {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
                .block {
                }
            }
            .detailedTime {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
            }
            .button1 {
                margin-left: 20px;
            }
            .table {
                margin-top: 10px;
            }
            .s1 {
                margin-left: 20px;
                font-size: 12px;
                color: rgba($color: #000000, $alpha: 0.5);
            }
        }
    }
    .truck,
    .other {
        display: flex;
        padding: 20px 0;
        border-bottom: 1px solid #ccc;
        // position: relative;

        .left {
            display: flex;

            justify-content: space-around;
            padding-right: 50px;
            margin-left: 10px;
            .name {
                line-height: 32px;
                margin-left: 10px;
            }
            .icon {
                img {
                    width: 32px;
                    height: 32px;
                }
            }
        }
        .right {
            width: 480px;

            .type {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
                .block {
                    .el-radio-group {
                        // /deep/.el-radio-button--mini .el-radio-button__inner {
                        //     border-left: 1 ;
                        // }
                        // /deep/ .el-radio-button--small {
                        //     /deep/span {
                        //         border-left: 1px solid #dcdfe6 !important;
                        //     }
                        // }
                    }
                }
            }

            .detailedTime {
                display: flex;
                margin-bottom: 10px;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
            }
            .vehicleType,
            .conductor,
            .Remarks {
                display: flex;
                flex-wrap: wrap;
                margin-bottom: 10px;
                position: relative;
                .title {
                    width: 100px;
                    text-align: right;
                    line-height: 32px;
                    span {
                        padding-top: 3px;
                        color: red;
                    }
                }
                .block {
                    flex: 1;
                    .el-radio-group {
                        width: 100%;
                        .el-radio-button {
                            margin-bottom: 5px;
                        }
                        .el-checkbox-button:first-child .el-checkbox-button__inner {
                            border-radius: none !important;
                        }
                        .el-checkbox-button .el-checkbox-button--mini .is-checked {
                            margin-bottom: 5px;
                        }
                    }
                    /deep/textarea {
                        resize: none !important;
                    }
                }
                .tipa {
                    font-size: 12px;
                    color: rgba($color: #000000, $alpha: 0.5);
                    position: absolute;
                    right: -30px;
                    bottom: 10px;
                }
                // /deep/.el-checkbox-button--mini .el-checkbox-button__inner {
                //     border: 1px solid #dcdfe6;
                // }
                /deep/.el-checkbox-button:first-child /deep/.el-checkbox-button__inner {
                    border-radius: none !important;
                }
            }
        }
    }
    .other {
        border: none;
    }
}
</style>