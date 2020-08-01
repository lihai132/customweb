<template>
    <div class="map">
        <div id="container"></div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            show: true,
            start: '广州',
            center: '武汉',
            end: '北京'
        };
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            // var that = this;
            // window.onload = function() {
            //     var map = new AMap.Map('container', {
            //         resizeEnable: true,
            //         center: [114.02919, 30.58203], //地图中心点
            //         zoom: 6 //地图显示的缩放级别
            //     });

            //     var driving = new AMap.Driving({
            //         map: map
            //     });

            //     driving.search(
            //         [
            //             { keyword: that.start, city: that.start },
            //             { keyword: that.center, city: that.center },
            //             { keyword: that.end, city: that.end }
            //         ],
            //         function(status, result) {
            //             // result 即是对应的驾车导航信息，相关数据结构文档请参考  https://lbs.amap.com/api/javascript-api/reference/route-search#m_DrivingResult
            //             if (status === 'complete') {
            //                 log.success('绘制驾车路线完成');
            //             } else {
            //                 log.error('获取驾车数据失败：' + result);
            //             }
            //         }
            //     );

            //     var marker = new AMap.Marker({
            //         position: map.getCenter(),
            //         icon: require('../../assets/img/che.png'),
            //         offset: new AMap.Pixel(-13, -30)
            //     });

            //     marker.setMap(map);

            //     marker.setLabel({
            //         offset: new AMap.Pixel(0, 10), //设置文本标注偏移量
            //         content: "<div class='info' style='width:60px;font-size: 12px;line-height: 12px;'>武汉</div>", //设置文本标注内容
            //         direction: 'right' //设置文本标注方位
            //     });
            // };
        }
    }
};
</script>
<style scoped>
html,
body,
#container {
    width: 600px;
    height: 600px;
    font-size: 12px;
    line-height: 20px;
}
#panel {
    position: fixed;
    background-color: white;
    max-height: 90%;
    overflow-y: auto;
    top: 10px;
    right: 10px;
    width: 280px;
}
#panel .amap-call {
    background-color: #009cf9;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
}
#panel .amap-lib-driving {
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;
    overflow: hidden;
}
.amap-icon img {
    width: 32px;
    height: 32px;
}

.amap-icon img {
    width: 25px;
    height: 34px;
}

.amap-marker-label {
    border: 0;
    background-color: transparent;
}

.info {
    position: relative;
    top: 0;
    right: 0;
    width: 60px;
}
</style>