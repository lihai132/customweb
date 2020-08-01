<template>
    <div class="head">
        <el-header style="height:184px">
            <div class="head">
                <div class="bg-img">
                    <div class="title">
                        <div class="name">客服订单</div>
                    </div>
                    <div class="right">
                        <div class="id">
                            ID:
                            <span>123456789</span>
                        </div>
                        <div class="ct">
                            CT:
                            <span>2020-5-01-12:02:00</span>
                        </div>
                    </div>
                </div>
                <div class="nav">
                    <div class="start">
                        <div class="start-address">上海</div>
                        <div class="DetailedAddress">闵行区莲花南路184号</div>
                    </div>
                    <div class="center1">
                        <div class="title">昌南大道北5街11巷33号</div>
                    </div>
                    <div class="distance">
                        <span>1500</span>公里
                    </div>
                    <div class="center2">昌南大道北5街11巷33号</div>
                    <div class="end">
                        <div class="start-address">广州</div>
                        <div class="DetailedAddress">闵行区莲花南路184号</div>
                    </div>
                </div>
            </div>
        </el-header>
    </div>
</template>
<style lang="scss" scoped>
.el-header {
    width: 100%;
    height: 178px;
    background-image: url(../../assets/img/bg.png);
    background-size: contain;
    background-repeat: no-repeat;
    padding: 0;

    margin-left: 10px;
    margin-right: 10px;
    .head {
        height: 178px;
        margin-bottom: 20xp;

        .bg-img {
            display: flex;
            .title {
                width: 77px;
                height: 74px;
                background-image: url(../../assets/img/loading.png);
                background-repeat: no-repeat;
                color: #fff;
                .name {
                    transform: rotate(-44deg);
                    text-align: left;
                    font-size: 12px;
                    line-height: 25px;
                    width: 80px;
                    height: 30px;
                }
            }
            .right {
                flex: 1;
                display: flex;
                height: 30px;
                justify-content: space-between;
                padding: 0 30px 0 0px;
                .id,
                .ct {
                    color: #fff;
                    font-size: 16px;
                    line-height: 30px;
                }
            }
        }
        .nav {
            display: flex;
            position: relative;
            padding: 0 26px;
            justify-content: space-between;
            .start {
                color: #fff;
                text-align: center;
                margin-top: 26px;
                .start-address {
                    font-size: 20px;
                    margin-bottom: 7px;
                }
            }
            .end {
                color: #fff;
                text-align: center;
                margin-top: 26px;
                margin-right: 25px;
                .start-address {
                    font-size: 20px;
                    margin-bottom: 7px;
                }
            }
            .distance {
                padding-top: 44px;
                color: #fff;
                font-size: 22px;
            }
            .center1 {
                box-sizing: border-box;
                width: 111px;
                overflow: hidden;
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 1;
                overflow: hidden;
                height: 26px;
                padding: 0 5px;

                font-size: 14px;
                line-height: 26px;
                color: #fff;
                position: absolute;
                top: -20px;
                left: 179px;
            }
            .center2 {
                box-sizing: border-box;
                width: 111px;
                overflow: hidden;
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 1;
                overflow: hidden;
                height: 26px;
                padding: 0 5px;

                font-size: 14px;
                line-height: 26px;
                color: #fff;
                position: absolute;
                top: -20px;
                left: 453px;
            }
        }
    }
}
</style>