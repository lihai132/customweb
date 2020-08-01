<template>
    <div>
        <input
            ref="excel-upload-input"
            class="excel-upload-input"
            type="file"
            accept=".xlsx, .xls"
            @change="handleClick"
        />
        <div class="drop" @drop="handleDrop" @dragover="handleDragover" @dragenter="handleDragover">
            <div class="customInfo" v-if="customerData">
                <span>客户名称：{{customerData.ne}}</span>
                <span>客户电话：{{customerData.ph}}</span>
                <span v-if="customerData.com">企业名称：{{customerData.com}}</span>
            </div>
            <el-button
                :loading="loading"
                style="margin-left:16px;font-size:14px;"
                size="mini"
                type
                @click="getCustomer"
            >{{customerData ? '切换客户' : '选择客户' }}</el-button>
            <el-button
                :loading="loading"
                style="margin-left:16px;font-size:14px;"
                size="mini"
                type="primary"
                @click="handleUpload"
            >批量导入</el-button>
            <el-button
                :loading="loading"
                style="margin-left:16px;margin-right:20px;font-size:14px;"
                size="mini"
                type
                @click="handleDownload"
            >下载模板</el-button>
        </div>
        <cusromer-list ref="cusList" @setRowData="getRowData"></cusromer-list>
    </div>
</template>

<script>
import XLSX from 'xlsx';
import { fetchData } from '../../api/index';
import AESUTIL from '../../assets/js/encryption.js';
import AESUTILNormal from '../../assets/js/hs_common.js';
import cusromerList from '../popup/customerList.vue';
export default {
    props: {
        beforeUpload: Function, // eslint-disable-line
        onSuccess: Function // eslint-disable-line
    },
    data() {
        return {
            loading: false,
            excelData: {
                header: null,
                results: null
            },
            customerData: ''
        };
    },
    components: { cusromerList },
    methods: {
        getCustomer() {
            this.$refs.cusList.open();
        },
        getRowData(arg) {
            this.customerData = arg;
        },
        generateData({ header, results }) {
            this.excelData.header = header;
            this.excelData.results = results;
            this.onSuccess && this.onSuccess(this.excelData);
        },
        handleDrop(e) {
            e.stopPropagation();
            e.preventDefault();
            if (this.loading) return;
            const files = e.dataTransfer.files;
            if (files.length !== 1) {
                this.$message.error('Only support uploading one file!');
                return;
            }
            const rawFile = files[0]; // only use files[0]

            if (!this.isExcel(rawFile)) {
                this.$message.error('Only supports upload .xlsx, .xls, .csv suffix files');
                return false;
            }
            this.upload(rawFile);
            e.stopPropagation();
            e.preventDefault();
        },
        handleDragover(e) {
            e.stopPropagation();
            e.preventDefault();
            e.dataTransfer.dropEffect = 'copy';
        },
        handleUpload() {
            if (!this.customerData) {
                this.$alert('请先选择客户！', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {}
                });
                return;
            }
            // console.log(this.customerData);
            this.$emit('setCustomData', this.customerData);
            this.$refs['excel-upload-input'].click();
        },
        handleClick(e) {
            const files = e.target.files;
            const rawFile = files[0]; // only use files[0]
            if (!rawFile) return;
            this.upload(rawFile);
        },
        upload(rawFile) {
            this.$refs['excel-upload-input'].value = null; // fix can't select the same excel

            if (!this.beforeUpload) {
                this.readerData(rawFile);
                return;
            }
            const before = this.beforeUpload(rawFile);
            if (before) {
                this.readerData(rawFile);
            }
        },
        readerData(rawFile) {
            this.loading = true;
            return new Promise((resolve, reject) => {
                const reader = new FileReader();
                reader.onload = e => {
                    const data = e.target.result;
                    const workbook = XLSX.read(data, { type: 'array', cellDates: true });
                    const firstSheetName = workbook.SheetNames[0];
                    const worksheet = workbook.Sheets[firstSheetName];
                    const header = this.getHeaderRow(worksheet);
                    const results = XLSX.utils.sheet_to_json(worksheet);
                    if (!results[0].装货日期) {
                        this.$alert('请根据模板格式导入数据！', '提示', {
                            confirmButtonText: '确定',
                            callback: action => {}
                        });
                        this.loading = false;
                        return;
                    }
                    results.map((item, index) => {
                        var d = new Date(item.装货日期);
                        // var d = item.装货日期;
                        // console.log(d);
                        d.setTime(d.getTime() + 0.5 * 24 * 3600 * 1000);
                        // console.log(d);
                        // console.log(`${d.getFullYear()}-${d.getMonth()+1}-${d.getDate()}`);
                        // item.装货日期 = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
                        item.装货日期 = `${d.getFullYear()}-${d.getMonth() + 1}-${d.getDate()}`;
                        // item.装货日期 = this.formatDate(item.装货日期);
                    });

                    this.generateData({ header, results });
                    this.loading = false;
                    resolve();
                };
                reader.readAsArrayBuffer(rawFile);
            });
        },
        getHeaderRow(sheet) {
            const headers = [];
            const range = XLSX.utils.decode_range(sheet['!ref']);
            let C;
            const R = range.s.r;
            /* start in the first row */
            for (C = range.s.c; C <= range.e.c; ++C) {
                /* walk every column in the range */
                const cell = sheet[XLSX.utils.encode_cell({ c: C, r: R })];
                /* find the cell in the first row */
                let hdr = 'UNKNOWN ' + C; // <-- replace with your desired default
                if (cell && cell.t) hdr = XLSX.utils.format_cell(cell);
                headers.push(hdr);
            }
            return headers;
        },
        isExcel(file) {
            return /\.(xlsx|xls|csv)$/.test(file.name);
        },
        //下载模板
        handleDownload() {
            this.downloadLoading = true;
            import('../vendor/Export2Excel').then(excel => {
                const tHeader = ['序号', '装货日期', '起运地', '目的地', '货物名称', '期望运费', '重量', '体积', '车型', '车长', '备注'];
                const filterVal = ['id', 'zte', 'zct', 'xct', 'on', 'qyf', 'dw', 'dv', 'ct', 'cl', 'dsc'];
                const list = [
                    {
                        id: 1,
                        zte: '2020-03-08',
                        zct: '广东 广州',
                        xct: '湖南 长沙',
                        on: '设备',
                        qyf: '5000',
                        dw: '30',
                        dv: '55',
                        ct: '平板',
                        cl: '13.0米',
                        dsc: ''
                    }
                ];
                const data = this.formatJson(filterVal, list);
                excel.export_json_to_excel({
                    header: tHeader,
                    data,
                    filename: '订单模板',
                    autoWidth: true,
                    bookType: 'xlsx'
                });
                this.downloadLoading = false;
            });
        },
        formatJson(filterVal, jsonData) {
            return jsonData.map(v =>
                filterVal.map(j => {
                    if (j === 'timestamp') {
                        // return parseTime(v[j])
                        return v[j];
                    } else {
                        return v[j];
                    }
                })
            );
        },
        //日期格式
        formatDate(numb, format = '-') {
            // let time = new Date((numb - 1) * 24 * 3600000 + 1);
            // // let time = new Date((numb - 1) * 24 * 3600000 - 2209017943000);
            // // let time = new Date(1900,0,(numb - 1) * 24 * 3600000 + 1);
            // time.setYear(time.getFullYear() - 70);
            // let year = time.getFullYear();
            // let month = time.getMonth() + 1 + '';
            // let date = time.getDate() + '';
            // if(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)){
            //     date = date - 1;
            // }
            // if (format && format.length === 1) {
            //     return year + format + month + format + date;
            // }
            // return year + (month < 10 ? '0' + month : month) + (date < 10 ? '0' + date : date);

            let time = new Date((numb - 1) * 24 * 3600000 + 1);
            time.setYear(time.getFullYear() - 70);
            let year = time.getFullYear() + '';
            let month = time.getMonth() + 1 + '';
            let date = time.getDate() + '';
            return year + '-' + month + '-' + date;
        }
    }
};
</script>

<style scoped lang="scss">
.excel-upload-input {
    display: none;
    z-index: -9999;
}
.drop {
    border: 2px dashed #bbb;
    width: 100%;
    height: 100px;
    line-height: 100px;
    margin: 0 auto;
    font-size: 24px;
    border-radius: 5px;
    text-align: right;
    color: #bbb;
    position: relative;
}
.customInfo {
    position: absolute;
    left: 0;
    width: 55%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: inline-block;
    color: #000;
    span {
        margin: 0 10px;
        font-size: 16px;
    }
}
</style>
