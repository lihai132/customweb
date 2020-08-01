<template>
    <div class="app-container">
        <upload-excel-component
            :on-success="handleSuccess"
            :before-upload="beforeUpload"
            @setCustomData="getCustomData"
        />
        <el-table
            :data="tableData"
            border
            style="width: 100%;margin-top:20px;"
        >
            <el-table-column v-for="item of tableHeader" :key="item" :prop="item" :label="item" />
            <el-table-column label="操作" width>
                <template slot-scope="scope">
                    <el-button @click="submit(scope.row)" type="success" size="small">发货</el-button>
                </template>
            </el-table-column>
        </el-table>

        <deliver-goods ref="dGoods" :getcusData="customData" @delData="delData"></deliver-goods>
    </div>
</template>

<script>
import UploadExcelComponent from '../UploadExcel/index.vue';
import deliverGoods from '../popup/deliverGoods.vue';
export default {
    name: 'UploadExcel',
    components: { UploadExcelComponent, deliverGoods },
    data() {
        return {
            tableData: [],
            tableHeader: [],
            customData: ''
        };
    },
    methods: {
        beforeUpload(file) {
            const isLt1M = file.size / 1024 / 1024 < 1;

            if (isLt1M) {
                return true;
            }

            this.$message({
                message: '请不要上传大于1m的文件',
                type: 'warning'
            });
            return false;
        },
        handleSuccess({ results, header }) {
            this.tableData = results;
            this.tableHeader = header;
            // console.log(results);
        },
        getCustomData(arg) {
            this.customData = arg;
        },
        submit(row) {
            this.$refs.dGoods.open(row);
            // console.log(this.tableData);
        },
        delData(arg){
            this.tableData.forEach((element,index) => {
                if (element.__rowNum__ == arg) {
                    this.tableData.splice(index,1);
                }
            });
                             
        }
    }
};
</script>
