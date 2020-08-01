<template>
    <div class="goods">
        <div class="left">
            <div class="nav1">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">选择客户</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form3"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                        class="form"
                    >
                        <el-form-item label="选择客户" prop="search">
                            <el-input
                                v-model="form3.search"
                                placeholder="请输入内容"
                                style="width:300px"
                                :minlength="2"
                                :maxlength="16"
                            ></el-input>
                            <el-button
                                type="primary"
                                style="margin-left:13px;"
                                @click="search()"
                                :disabled="searchBut"
                            >查找</el-button>
                            <el-button type="primary" @click="addCustom">添加</el-button>
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
                    >
                        <el-table-column width="60" align="center">
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.checked"></el-checkbox>
                            </template>
                        </el-table-column>

                        <el-table-column
                            prop="ne"
                            label="客户名称"
                            :show-overflow-tooltip="true"
                            align="center"
                            width="100"
                        ></el-table-column>
                        <el-table-column
                            prop="ph"
                            label="客户电话"
                            :show-overflow-tooltip="true"
                            align="center"
                        ></el-table-column>
                        <el-table-column
                            prop="com"
                            label="企业名称"
                            :show-overflow-tooltip="true"
                            align="center"
                        ></el-table-column>
                        <el-table-column
                            prop="st"
                            label="客户状态"
                            :show-overflow-tooltip="true"
                            align="center"
                        ></el-table-column>
                        <!-- <el-table-column
                            prop="ctm"
                            label="创建时间"
                            :show-overflow-tooltip="true"
                            align="center"
                        ></el-table-column>-->
                    </el-table>
                </div>
            </div>
            <div class="nav2">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">地址信息</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form1"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                        class="form"
                    >
                        <div class="row">
                            <el-form-item label="起运地" prop="start">
                                <el-autocomplete
                                    class="inline-input"
                                    v-model="form1.start"
                                    :fetch-suggestions="querySearch"
                                    placeholder="省-市-区"
                                    :trigger-on-focus="false"
                                    @select="startChang"
                                    style="width:220px;"
                                >
                                    <template slot-scope="{item}">
                                        <span :title="item.value">{{item.value}}</span>
                                    </template>
                                </el-autocomplete>
                                <!-- <el-input
                                    v-model="form1.startAddress"
                                    style="width:300px;margin-left:20px;"
                                    placeholder="请输入起运地详细地址"
                                ></el-input>-->
                            </el-form-item>
                            <!-- <el-form-item prop="startAddress" style="margin-left:20px;"> -->
                            <el-input
                                v-model="form1.startAddress"
                                style="width:330px;margin-left:20px;"
                                placeholder="详细地址"
                                :maxlength="64"
                            ></el-input>
                            <!-- </el-form-item> -->
                        </div>
                        <!-- <el-form-item label="起运地" prop="start">
                            <el-autocomplete
                                class="inline-input"
                                v-model="form1.start"
                                :fetch-suggestions="querySearch"
                                placeholder="请输入内容"
                                :trigger-on-focus="false"
                                @select="startChang"
                                style="width:220px;"
                            ></el-autocomplete>
                        </el-form-item>-->
                        <!-- <el-form-item label="详细地址" prop="startAddress">
                            <el-input v-model="form1.startAddress" style="width:450px"></el-input>
                        </el-form-item>-->
                        <div class="row">
                            <el-form-item label="目的地" prop="end">
                                <el-autocomplete
                                    class="inline-input"
                                    v-model="form1.end"
                                    :fetch-suggestions="querySearch"
                                    placeholder="省-市-区"
                                    :trigger-on-focus="false"
                                    @select="endChang"
                                    style="width:220px;"
                                >
                                    <template slot-scope="{item}">
                                        <span :title="item.value">{{item.value}}</span>
                                    </template>
                                </el-autocomplete>
                                <!-- <el-input
                                    v-model="form1.endAddress"
                                    style="width:300px;margin-left:20px;"
                                    placeholder="请输入目的地详细地址"
                                ></el-input>-->
                            </el-form-item>
                            <!-- <el-form-item prop="endAddress" style="margin-left:20px;"> -->
                            <el-input
                                v-model="form1.endAddress"
                                style="width:330px;margin-left:20px;"
                                placeholder="详细地址"
                                :maxlength="64"
                            ></el-input>
                            <!-- </el-form-item> -->
                        </div>
                        <!-- <el-form-item label="目的地" prop="end">
                            <el-autocomplete
                                class="inline-input"
                                v-model="form1.end"
                                :fetch-suggestions="querySearch"
                                placeholder="请输入内容"
                                :trigger-on-focus="false"
                                @select="endChang"
                                style="width:220px;"
                            ></el-autocomplete>
                        </el-form-item>
                        <el-form-item label="详细地址" prop="endAddress">
                            <el-input v-model="form1.endAddress" style="width:450px"></el-input>
                        </el-form-item>-->
                        <!-- <div class="dis">
                            <div class="text">公里</div>
                            <div class="num">{{form1.dis}}</div>
                        </div>-->
                        <el-form-item label="距离">
                            <el-input v-model="form1.dis" style="width:220px" :disabled="true">
                                <i slot="suffix" style="font-style:normal" class="metre">公里</i>
                            </el-input>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
            <div class="nav3">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">货物信息</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form2"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                        class="form"
                    >
                        <div class="row">
                            <!-- <span class="tipe">*</span> -->
                            <el-form-item label="货物名称" prop="goodsName">
                                <el-input
                                    v-model="form2.goodsName"
                                    style="width:220px"
                                    :maxlength="8"
                                    :minlength="2"
                                ></el-input>
                            </el-form-item>
                            <el-form-item label="期望运费" required style="margin-left:30px;">
                                <el-input-number
                                    v-model="form2.price"
                                    controls-position="right"
                                    :min="0"
                                    :max="999999"
                                    style="width:230px;"
                                    :step="50"
                                    :maxlength="8"
                                    :precision="0"
                                ></el-input-number>
                            </el-form-item>
                        </div>
                        <div class="row">
                            <el-form-item label="货物重量" required>
                                <el-col :span="9" prop="weight">
                                    <el-input-number
                                        v-model="form2.weight1"
                                        controls-position="right"
                                        :min="1"
                                        :max="9999"
                                        style="width:100px;"
                                        :precision="2"
                                    ></el-input-number>
                                </el-col>
                                <el-col
                                    class="line"
                                    :span="3"
                                    style="text-align: right;margin-right:5px;"
                                >-</el-col>
                                <!-- <span>~</span> -->
                                <el-col :span="9">
                                    <el-input-number
                                        v-model="form2.weight2"
                                        controls-position="right"
                                        :min="1"
                                        :max="9999"
                                        style="width:100px;"
                                        prop="weight"
                                        :precision="2"
                                    ></el-input-number>
                                </el-col>吨
                            </el-form-item>
                            <el-form-item label="货物体积" style="margin-left:26px;">
                                <el-col :span="9">
                                    <el-input-number
                                        v-model="form2.volume1"
                                        controls-position="right"
                                        :min="0"
                                        :max="9999"
                                        style="width:107px;"
                                        :precision="2"
                                    ></el-input-number>
                                </el-col>
                                <el-col
                                    class="line"
                                    :span="3"
                                    style="text-align: right;margin-right:5px;"
                                >-</el-col>
                                <el-col :span="9">
                                    <el-input-number
                                        v-model="form2.volume2"
                                        controls-position="right"
                                        :min="0"
                                        :max="9999"
                                        style="width:107px;"
                                        :precision="2"
                                    ></el-input-number>
                                </el-col>方
                            </el-form-item>
                        </div>
                        <div class="row">
                            <span class="tipe">*</span>
                            <el-form-item label="装货日期">
                                <el-date-picker
                                    v-model="form2.datatime"
                                    type="date"
                                    placeholder="选择日期"
                                    value-format="yyyy-MM-dd"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="详细时间" style="margin-left:30px;">
                                <el-select
                                    v-model="form2.time"
                                    placeholder="请选择"
                                    style="width:230px;"
                                >
                                    <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.label"
                                    ></el-option>
                                </el-select>
                            </el-form-item>
                        </div>
                    </el-form>
                </div>
            </div>
            <!-- <div class="nav3">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">其他要求</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form3"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                        class="form"
                    >
                        <el-form-item label="常规要求">
                            <el-checkbox-group v-model="otherList" size="mini">
                                <el-checkbox-button
                                    :label="item"
                                    v-for="(item,index) in other"
                                    :key="index"
                                    style="margin-right:10px; margin-bottom: 5px;"
                                >{{item}}</el-checkbox-button>
                            </el-checkbox-group>
                        </el-form-item>

                        <el-form-item label="备注">
                            <el-input
                                type="textarea"
                                :autosize="{ minRows: 6, maxRows: 9}"
                                placeholder="请输入内容"
                                v-model="form2.textarea"
                                maxlength="64"
                            ></el-input>
                        </el-form-item>
                    </el-form>
                </div>
            </div>-->
        </div>
        <div class="right">
            <div class="nav1">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">用车需求</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form3"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                        class="form"
                    >
                        <el-form-item label="运输类型" prop="transportType">
                            <el-radio-group v-model="form3.transportType" size="mini" :max="1">
                                <el-radio-button label="整车" style="margin-right:10px;"></el-radio-button>
                                <el-radio-button label=" 配货 " style="margin-right:20px;"></el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="装卸方式" prop="pretendType">
                            <el-radio-group v-model="form3.pretendType">
                                <el-radio-button
                                    :label="item"
                                    v-for="(item,index) in dischargeCargo"
                                    :key="index"
                                    style="margin-right:10px;"
                                ></el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="车型" class="fixed1">
                            <el-checkbox-group
                                v-model="form3.ct"
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
                        </el-form-item>
                        <el-form-item label="车长" class="fixed2">
                            <el-checkbox-group v-model="form3.cl" size="mini" :max="3">
                                <el-checkbox-button
                                    :label="item"
                                    v-for="(item,index) in conductor"
                                    :key="index"
                                    style="margin-right:10px; margin-bottom: 5px;"
                                >{{item}}</el-checkbox-button>
                            </el-checkbox-group>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
            <div class="nav2">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">其他要求</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form3"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                        class="form"
                    >
                        <el-form-item label="常规要求">
                            <el-checkbox-group v-model="otherList" size="mini">
                                <el-checkbox-button
                                    :label="item"
                                    v-for="(item,index) in other"
                                    :key="index"
                                    style="margin-right:10px; margin-bottom: 5px;"
                                >{{item}}</el-checkbox-button>
                            </el-checkbox-group>
                        </el-form-item>

                        <el-form-item label="备注">
                            <el-input
                                type="textarea"
                                :autosize="{ minRows: 6, maxRows: 9}"
                                placeholder="请输入内容"
                                v-model="form2.textarea"
                                maxlength="64"
                            ></el-input>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
            <!-- <div class="nav2">
                <div class="title">
                    <div class="title-left"></div>
                    <div class="text">选择客户</div>
                </div>
                <div class="center">
                    <el-form
                        :model="form3"
                        :rules="rules"
                        ref="ruleForm"
                        label-width="80px"
                        class="form"
                    >
                        <el-form-item label="选择客户">
                            <el-input
                                v-model="form3.search"
                                placeholder="请输入内容"
                                style="width:300px"
                            ></el-input>
                            <el-button type="primary" style="margin-left:13px;">查找</el-button>
                            <el-button type="primary">添加</el-button>
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
                    >
                        <el-table-column width="60" align="center">
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.checked"></el-checkbox>
                            </template>
                        </el-table-column>

                        <el-table-column
                            prop="ne"
                            label="客户名称"
                            :show-overflow-tooltip="true"
                            align="center"
                            width="100"
                        ></el-table-column>
                        <el-table-column
                            prop="ph"
                            label="客户电话"
                            :show-overflow-tooltip="true"
                            align="center"
                            width="130"
                        ></el-table-column>
                        <el-table-column
                            prop="com"
                            label="企业名称"
                            :show-overflow-tooltip="true"
                            align="center"
                            width="100"
                        ></el-table-column>
                        <el-table-column
                            prop="st"
                            label="客户状态"
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
            </div>-->
            <div class="but">
                <el-button type="primary" style="width:120px" @click="place" :disabled="placeBut">发货</el-button>
            </div>
        </div>
        <addCustom ref="addCustom" @getData="getData"></addCustom>
    </div>
</template>
<script>
import addCustom from '../popup/addCustom.vue';
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
export default {
    data() {
        return {
            searchBut: false,
            placeBut: false,
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
                    { min: 1, max: 8, message: '长度在 1 到 8 个字符', trigger: 'blur' }
                ],
                weight: [{ required: true, message: '请输入重量', trigger: 'blur' }],
                time: [{ required: true }],
                transportType: [{ required: true }],
                pretendType: [{ required: true }],
                customer: [{ required: true }],
                price: [
                    { required: false, message: '请输入运费', trigger: 'blur' },
                    { min: 0, max: 7, message: '长度在 0 到 7 个字符', trigger: 'blur' }
                ],
                name: [
                    { required: false, message: '请输入司机名称', trigger: 'blur' },
                    { min: 0, max: 16, message: '长度在 0 到 16 个字符', trigger: 'blur' }
                ],
                search: [
                    { required: true, message: '请输入客户名称', trigger: 'blur' },
                    { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
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
            form1: {
                start: '',
                startId: '',
                startAddress: '',
                end: '',
                endId: '',
                endAddress: '',
                dis: 0,
                cid: '',
                com: ''
            },
            form2: {
                goodsName: '',
                startAddress: '',
                end: '',
                endAddress: '',
                weight1: '',
                weight2: '',
                volume1: '',
                volume2: '',
                datatime: '',
                time: '',
                textarea: '',
                price: ''
            },
            form3: {
                transportType: '整车',
                pretendType: '一装一卸',
                ct: [],
                cl: [],
                search: ''
            },
            customerData: [],
            customer: {},
            loginData: {},
            sk: '',
            customerList: [],
            otherList: [],
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
            dischargeCargo: ['一装一卸', '一装两卸', '两装一卸', '两装两卸'],
            truckType: ['平板', '高栏', '中栏', '低栏', '高低板', '厢式', '自卸', '保温', '冷藏', '危险品', '集装箱', '特种'],
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
            other: ['三不超', '禁止配货', '含油卡', '购买保险', '全程高速', '需开票', '需回单', '需雨布', '需压车', '有禁区']
        };
    },
    created() {
        this.loginData = this.$store.state.Login.CustomerData;
        this.sk = this.$store.state.Login.key;
        this.search();
        this.startProvinceChange();
    },
    methods: {
        //地址方法
        startProvinceChange() {
            var storage = window.localStorage;
            let AddressList = JSON.parse(storage.goodAddress);
            this.restaurants = AddressList;
            // console.log(AddressList);
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
        // loadAll1() {
        //     return this.restaurants;
        // },
        async startChang(item) {
            this.form1.startId = item.id + '';
            console.log(item);
            if (this.form1.startId.length > 0 && this.form1.endId.length > 0) {
                let action = this.$store.state.Login.url + '/10016';

                var bd = {
                    tid: this.loginData.tid,
                    zid: this.form1.startId,
                    xid: this.form1.endId
                };
                var hd = {
                    pi: 10016,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/10016', hd, bd);
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
                    data.dis = Math.round(data.dis);
                    if (data.dis < 10) {
                        data.dis = 10;
                    }
                    this.form1.dis = data.dis;
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
        },
        async endChang(item) {
            this.form1.endId = item.id + '';
            console.log(item);
            if (this.form1.startId.length > 0 && this.form1.endId.length > 0) {
                let action = this.$store.state.Login.url + '/10016';
                // this.loginData = this.$store.state.Login.CustomerData;
                var bd = {
                    tid: this.loginData.tid,
                    zid: this.form1.startId,
                    xid: this.form1.endId
                };
                var hd = {
                    pi: 10016,
                    si: this.loginData.si,
                    sq: 9
                };
                let res = await request('/10016', hd, bd);
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
                    data.dis = Math.round(data.dis);
                    if (data.dis < 10) {
                        data.dis = 10;
                    }
                    this.form1.dis = data.dis;
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }
        },
        loadAll() {
            return this.restaurants;
        },
        //客户搜索
        async search() {
            let action = `${this.$store.state.Login.url}/30079`;

            var bd = {
                tid: this.loginData.tid,
                im: 1,
                ss: this.form3.search,
                pg: 1,
                sz: 200
            };
            var hd = {
                pi: 30079,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30079', hd, bd);
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

                if (list.length == 0) {
                    // this.open2('所查询客户为空，请添加');
                    this.customerData = [];
                } else {
                    list.forEach((item) => {
                        item.checked = false;
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
                }
            } else {
                this.open2(res.data.hd.rmsg);
            }

            this.searchBut = true;
            setTimeout(() => {
                this.searchBut = false;
            }, 1000);
        },
        getData() {
            this.search();
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
            this.form1.cid = row.id;
            this.form1.com = row.com;
            console.log(row);
            console.log(this.form1.com, this.form1.cid);
        },
        //发货
        async place() {
            this.placeBut = true;
            let action = this.$store.state.Login.url + '/20001';

            //数据校验
            if (this.form1.startId.length == 0) {
                this.open2('发货地址不能为空');
                this.placeBut = true;
                setTimeout(() => {
                    this.placeBut = false;
                }, 1000);
                return;
            }
            if (this.form1.endId.length == 0) {
                this.open2('收货地址不能为空');
                this.placeBut = true;
                setTimeout(() => {
                    this.placeBut = false;
                }, 1000);
                return;
            }
            if (this.form2.goodsName == false) {
                this.open2('货物名称不能为空');
                this.placeBut = true;
                setTimeout(() => {
                    this.placeBut = false;
                }, 1000);
                return;
            }
            if (this.form2.weight1 == false || this.form2.weight1 <= 0 || this.form2.weight1 == undefined) {
                this.open2('货物重量为一个范围数组');
                this.placeBut = true;
                setTimeout(() => {
                    this.placeBut = false;
                }, 1000);
                return;
            }
            if (this.form2.weight2 == false || this.form2.weight2 <= 0 || this.form2.weight2 == undefined) {
                this.open2('货物重量为一个范围数组');
                this.placeBut = true;
                setTimeout(() => {
                    this.placeBut = false;
                }, 1000);
                return;
            }
            if (this.form2.volume2 > 0) {
                if (this.form2.volume1 <= 0) {
                    this.open2('货物体积最小值不能为0');
                    this.placeBut = true;
                    setTimeout(() => {
                        this.placeBut = false;
                    }, 1000);
                    return;
                }
            }
            if (this.form2.price == false) {
                this.open2('期望运费不能为空');
                this.placeBut = true;
                setTimeout(() => {
                    this.placeBut = false;
                }, 1000);
                return;
            }
            if (this.form1.cid == false) {
                this.open2('请指定客户');
                this.placeBut = true;
                setTimeout(() => {
                    this.placeBut = false;
                }, 1000);
                return;
            }
            if (this.form2.datatime.length == 0) {
                this.open2('装货日期不能为空');
                this.placeBut = true;
                setTimeout(() => {
                    this.placeBut = false;
                }, 1000);
                return;
            } else {
                let zxte;
                if (this.form3.pretendType == '一装一卸') {
                    zxte = 1;
                }
                if (this.form3.pretendType == '一装两卸') {
                    zxte = 2;
                }
                if (this.form3.pretendType == '两装一卸') {
                    zxte = 3;
                }
                if (this.form3.pretendType == '两装两卸') {
                    zxte = 4;
                }
                let cl = '';
                if (this.form3.cl.length == 0) {
                    cl = '';
                }
                if (this.form3.cl.length > 0 && this.form3.cl.length == 1) {
                    cl = this.form3.cl[0];
                    console.log(cl);
                }
                if (this.form3.cl.length >= 2) {
                    this.form3.cl.map((item) => {
                        cl += item + ',';
                    });
                    cl = cl.substring(0, cl.length - 1);
                }

                let ct = '';
                if (this.form3.ct.length == 0) {
                    ct = '';
                }
                if (this.form3.ct.length > 0 && this.form3.ct.length == 1) {
                    ct = this.form3.ct[0];
                    console.log(ct);
                }
                if (this.form3.ct.length >= 2) {
                    this.form3.ct.map((item) => {
                        ct += item + ',';
                    });
                    ct = ct.substring(0, ct.length - 1);
                }

                let odc = '';
                if (this.otherList.length == 0) {
                    odc = '';
                }
                if (this.otherList.length > 0 && this.otherList.length == 1) {
                    odc = this.otherList[0];
                }
                if (this.otherList.length >= 2) {
                    this.otherList.map((item) => {
                        odc += item + ',';
                    });
                    odc = odc.substring(0, odc.length - 1);
                }
                let weight = '';
                if (this.form2.weight1 == this.form2.weight2) {
                    weight = this.form2.weight2;
                } else {
                    weight = this.form2.weight1 + '-' + this.form2.weight2;
                }

                let volume = '';
                if (this.form2.volume1 == this.form2.volume2) {
                    volume = this.form2.volume2;
                } else {
                    volume = this.form2.volume1 + '-' + this.form2.volume2;
                }

                // console.log(this.form2.weight1, this.form2.weight2);
                // console.log(this.form2.volume1, this.form2.volume2);
                var bd = {
                    tid: this.loginData.tid,
                    on: this.form2.goodsName,
                    te: this.form3.transportType == '整车' ? 1 : 2,

                    zxte: zxte,
                    ct: ct,
                    cl: cl,
                    dw: weight,
                    dv: volume,
                    zdy: this.form2.datatime,
                    zte: this.form2.time,
                    qyf: this.form2.price,
                    dis: this.form1.dis,
                    zid: this.form1.startId,
                    zad: this.form1.startAddress,
                    xid: this.form1.endId,
                    xad: this.form1.endAddress,
                    com: this.form1.com,
                    cid: this.form1.cid,
                    shr: '',
                    shrp: '',
                    odc: odc,
                    dc: this.form2.textarea,

                    isin: 0
                };
                var hd = {
                    pi: 20001,
                    si: this.loginData.si,
                    sq: 9
                };

                let res = await request('/20001', hd, bd);
                let rid = res.data.hd.rid;
                if (rid >= 0) {
                    this.open1('发货成功');
                    this.$router.push('/MyOrder');
                } else {
                    this.open2(res.data.hd.rmsg);
                }
            }

            this.placeBut = false;
            // setTimeout(() => {
            //     this.placeBut = false;
            // }, 1000);
        },

        addCustom() {
            this.$refs.addCustom.a_open();
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
    components: {
        addCustom
    },
    watch: {
        'form2.weight1': function (newVal) {
            if (newVal > this.form2.weight2) {
                this.form2.weight2 = newVal;
            }
        },
        'form2.weight2': function (newVal) {
            if (newVal < this.form2.weight1) {
                this.form2.weight1 = newVal;
            }
        },

        'form2.volume1': function (newVal) {
            if (newVal > this.form2.volume2) {
                this.form2.volume2 = newVal;
            }
        },
        'form2.volume2': function (newVal) {
            if (newVal < this.form2.volume1) {
                this.form2.volume1 = newVal;
            }
        }
    }
};
</script>
<style lang="scss">
.el-radio-button__orig-radio:checked + .el-radio-button__inner {
    width: 80px;
}
// .goods {
//     .left {
//         .center {
//             .row {
//                 /deep/.el-autocomplete-suggestion,
//                 /deep/.el-popper {
//                     width: 302px !important;
//                 }
//             }
//         }
//     }
// }

.el-radio-button:first-child .el-radio-button__inner {
    border-left: none !important;
    border-radius: 4px 0 0 4px;
    -webkit-box-shadow: none !important;
    box-shadow: none !important;
}
.el-radio-button {
    // border-left: none;
    border-left: 1px solid #ccc;
}

.el-radio-button__inner {
    width: 80px;
    // border: none !important;
    border-radius: 0px !important;
}

.el-checkbox-button__inner {
    width: 80px;
    // border: none !important;
    border-radius: 0px !important;
    border-left: 1px solid #ccc;
}
</style>
<style lang="scss" scoped>
* {
    box-sizing: border-box;
}

.table /deep/.cell {
    // line-height: 14px !important;
}
/deep/textarea {
    resize: none !important;
    height: 150px !important;
    width: 545px;
}
/deep/.is-checked {
    background: #409eff;
}

.goods {
    display: flex;
    width: 100%;
    // height: 100%;
    .left {
        width: 700px;
        display: flex;
        flex-direction: column;
        padding-right: 40px;
        .nav1,
        .nav2,
        .nav3 {
            height: 30%;
            width: 100%;

            padding-top: 20px;
            padding-left: 10px;

            .title {
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
                .form {
                    margin-top: 22px;
                    position: relative;
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
                    /deep/.el-table__body-wrapper {
                        height: 199px;
                        overflow: scroll;
                        // overflow-x: hidden;
                    }
                    /deep/.el-table__body-wrapper::-webkit-scrollbar {
                        display: none;
                    }
                }
            }
        }
        .nav1 {
            height: 382px;
        }
        .nav2 {
            height: 230px;
            width: 100%;
            // background: rgba($color: red, $alpha: 0.4);
            padding-top: 20px;
            padding-left: 10px;
        }
        .nav3 {
            height: 30%;
            width: 100%;
            // background: rgba($color: red, $alpha: 0.4);
            padding-top: 20px;
            padding-left: 10px;
        }
    }
    .right {
        width: 700px;
        display: flex;
        flex-direction: column;
        padding-left: 40px;
        .nav1,
        .nav2 {
            height: 40%;
            width: 100%;
            padding-top: 20px;
            padding-left: 10px;
            .title {
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
                .form {
                    margin-top: 22px;
                    position: relative;
                    height: 52px;
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
                        .el-form-item {
                            margin-bottom: 18px;
                        }
                    }
                    .fixed1 {
                        height: 72px;
                    }
                    .fixed2 {
                        height: 108px;
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
            height: 381px;
        }
        .nav2 {
            height: 352px;
            width: 100%;
            .search {
                margin-top: 20px;
            }
        }
        .but {
            text-align: center;
        }
    }
}
</style>