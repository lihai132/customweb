<template>
    <div class="container">
        <el-date-picker
            ref="daterange_"
            v-model="date"
            type="daterange"
            v-show="isType == 'daterange'"
            align="center"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small"
            :picker-options="pickerOptionsType"
            class="date-class"
        ></el-date-picker>

        <!-- <el-date-picker
            ref="weekrange_"
            v-model="date"
            type="week"
            v-show="isType == 'weekrange'"
            align="center"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small"
            :picker-options="pickerOptionsType"
            format="yyyy 第 WW 周"
            placeholder="选择周"
        ></el-date-picker> -->

        <el-date-picker
            ref="weekrange_"
            v-model="date"
            type="daterange"
            v-show="isType == 'weekrange'"
            align="center"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small"
            :picker-options="pickerOptionsType"
            class="date-class"
            
            format="yyyy 第 WW 周"
        ></el-date-picker>

        <el-date-picker
            ref="monthrange_"
            v-model="date"
            type="monthrange"
            v-show="isType == 'monthrange'"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始月份"
            end-placeholder="结束月份"
            size="small"
            :picker-options="pickerOptionsType"
            class="date-class"
        ></el-date-picker>
        <el-date-picker
            ref="yearrange_"
            v-model="date"
            type="year"
            v-show="isType == 'yearrange'"
            align="center"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            size="small"
            :picker-options="pickerOptionsType"
            class="date-class"
        ></el-date-picker>
    </div>
</template>

<script>
export default {
    data() {
        var that = this;
        return {
            date: '',
            format: '',
            isType: 'daterange',
            get pickerOptionsType() {
                // var that = this;
                return {
                    shortcuts: [
                        {
                            text: '日',
                            onClick(picker) {
                                // console.log(this, picker);
                                that.isType = 'daterange';
                                // console.log(that.$refs.saveDateInput.$el.children[1]);

                                // that.getFocus('saveDateInput')
                                // setTimeout(()=>{
                                //     console.log(that.$refs);
                                // },1000)
                                // console.log(that.$refs);

                                that.$refs.daterange_.focus();
                                // that.$refs.weekrange_.$el.remove()
                                //                                 setTimeout(()=>{
                                // that.$refs.saveDateInput.$el.children[1].focus();
                                //                                 },100)
                                // that.$refs.saveDateInput.$el.children[1].focus();
                                // that.$refs.monthrange_.blur();
                            }
                        },
                        {
                            text: '周',
                            onClick(picker) {
                                console.log(that.date);
                                
                                that.isType = 'weekrange';
                                that.$refs.weekrange_.focus();
                                setTimeout(() => {
                                    that.$refs.weekrange_.$el.children[0].focus();
                                }, 100);
                            }
                        },
                        {
                            text: '月',
                            onClick(picker) {
                                // console.log(that.$refs.monthrange_);
                                that.isType = 'monthrange';
                                that.$refs.monthrange_.focus();

                                // that.$refs.saveDateInput.$el.children[1].focus();
                                // that.$refs.saveDateInput.blur();
                                // that.$refs.monthrange_.$el.children[0].focus()
                                // this.focus();
                            }
                        },
                        // {
                        //     text: '年',
                        //     onClick(picker) {
                        //         // console.log(that.$refs.yearrange_.$el.children[0]);
                        //         that.isType = 'yearrange';
                        //         that.$refs.yearrange_.focus();
                        //         setTimeout(() => {
                        //             that.$refs.yearrange_.$el.children[0].focus();
                        //         }, 100);
                        //         that.$refs.yearrange_.$el.children[0].focus();
                        //     }
                        // }
                    ]
                };
            }

            // format: {
            // get() {
            //     // console.log(this.date);

            //     // let beginYearWeek = common.getYearAndWeek(date[0], date[1]);
            //     // let endYearWeek = common.getYearAndWeek()
            //     // return '2019.52周2020.32周';
            // }
            // }
        };
    },
    created() {},
    components: {},
    methods: {
        getFocus(name) {
            this.$refs.name.focus();
        }
    },
    computed: {}
};
</script>

<style scoped lang="scss">
</style>
