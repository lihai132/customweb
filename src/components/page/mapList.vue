<template>
    <div class="mapList">
        <div class="routes" v-if="list1">
            <div class="map-list" v-for="(item,index) in listData" :key="index">
                <div class="list-left"></div>
                <div class="list-right">
                    <div class="datatime">{{item.datatime}}</div>
                    <div class="time">{{item.time}}</div>
                    <el-tooltip class="item" effect="dark" :content="item.add" placement="top">
                        <div class="address">{{item.add}}</div>
                    </el-tooltip>
                </div>
                <div class="line" ref="line"></div>
            </div>
        </div>
        <div class="none" v-if="list2">暂无数据</div>
    </div>
</template>
<script>
import encryptUtil from '../../assets/js/encryptUtil.js';
import request from '../../assets/js/request.js';
export default {
    data() {
        return {
            loginData: {},
            orderData: {},
            newsData: {},
            list1: true,
            list2: false,
            listData: [],
            sk: '',
            LogingType: ''
        };
    },
    created() {
        this.LogingType = this.$store.state.Login.LogingType;
        this.sk = this.$store.state.Login.key;
        if (this.LogingType == 3) {
            this.loginData = this.$store.state.Login.DispatchData;
            this.show1 = false;
            this.show2 = false;
        }
        if (this.LogingType == 4) {
            this.loginData = this.$store.state.Login.CustomerData;
            this.show1 = true;
            this.show2 = true;
        }

        this.orderData = this.$store.state.Order.orderData;
        this.newsData = this.$store.state.Order.newsData;
        this.getData();
        // console.log(this.orderData);
    },
    methods: {
        async getData() {
            let action = this.$store.state.Login.url + '/30043';
            // this.loginData = this.$store.state.Login.CustomerData;

            var bd = {
                tid: this.loginData.tid,
                wid: this.orderData.wid,
                pg: 1,
                sz: 1000
            };
            var hd = {
                pi: 30043,
                si: this.loginData.si,
                sq: 9
            };
            let res = await request('/30043', hd, bd);
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
                    this.list1 = false;
                    this.list2 = true;
                } else {
                    list.forEach(item => {
                        let arr = item.ctm.split(' ');
                        item.datatime = arr[0];
                        item.time = arr[1];
                    });
                    this.listData = list;
                    // this.line();
                }
            } else {
                this.open2(res.data.hd.rmsg);
            }
        },
        line() {
            let arr = this.$refs.line;
            console.log(arr);
        },
        open1() {
            this.$message({
                message: '请求成功',
                type: 'success',
                duration: 900
            });
        },
        open2(text) {
            // this.$message.error('提交失败：' + text);
            this.$message({
                message: text,
                type: 'error',
                duration: 900
            });
        }
    },
    mounted() {
        // let arr = this.$refs.line;
        // console.log(arr);
    }
    // watch:{
    //     'formLabelAlign.freight': function(newVal){

    //     }
    // }
};
</script>
<style lang="scss" scoped>
.mapList {
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    padding: 25px 25px;
    .routes {
        .map-list {
            display: flex;
            align-items: center;
            position: relative;
            margin-bottom: 18px;

            // height: ;
            .list-left {
                width: 14.8px;
                height: 10px;
                border-radius: 50%;
                background: #d9d9d9;
                margin-bottom: 3px;
            }
            .list-right {
                display: flex;
                // justify-content: space-between;
                .datatime {
                    width: 82px;
                    margin-right: 15px;
                    margin-left: 15px;
                }
                .time {
                    width: 63px;
                    margin-right: 15px;
                }
                .address {
                    flex: 1;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;
                    -webkit-line-clamp: 1;
                    -webkit-box-orient: vertical;
                }
                // flex-wrap: wrap;
            }
            .line {
                height: 20px;
                // margin-top: 1px;
                // margin-bottom: 4px;
                border-left: 1px solid #d9d9d9;
                margin-left: 4.5px;
                position: absolute;
                top: 18.5px;
                left: 0;
            }
        }
        .map-list:last-child {
            .line {
                display: none;
            }
        }
    }
    .none {
        width: 100%;
        height: 100%;
        text-align: center;
        line-height: 100%;
        font-size: 20px;
        font-weight: 550;
        color: rgba($color: #ccc, $alpha: 0.5);
    }
}
</style>