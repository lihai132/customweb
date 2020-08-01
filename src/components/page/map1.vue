<template>
    <div class="map1">
        <div id="container1"></div>
    </div>
</template>
<script>
// import 'https://a.amap.com/jsapi_demos/static/demo-center/js/demoutils.js';
// import 'https://cache.amap.com/lbs/static/addToolbar.js';
export default {
    data() {
        return {};
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            // var that = this;
            // window.onload = function() {
            //     var map = new AMap.Map('container1', {
            //         zoom: 12,
            //         center: [116.4, 39.92],
            //         resizeEnable: true
            //     });

            //     var viaIcon = new AMap.Icon({
            //         // 图标尺寸
            //         size: new AMap.Size(26, 26),
            //         // 图标的取图地址
            //         image: require('../../assets/img/che.png'),
            //         // 图标所用图片大小
            //         imageSize: new AMap.Size(26, 26)
            //     });

            //     var startIcon = new AMap.Icon({
            //         // 图标尺寸
            //         size: new AMap.Size(19, 31),
            //         // 图标的取图地址
            //         image: 'https://webapi.amap.com/theme/v1.3/markers/n/start.png',
            //         // 图标所用图片大小
            //         imageSize: new AMap.Size(19, 31)
            //     });

            //     var endIcon = new AMap.Icon({
            //         // 图标尺寸
            //         size: new AMap.Size(19, 31),
            //         // 图标的取图地址
            //         image: 'https://webapi.amap.com/theme/v1.3/markers/n/end.png',
            //         // 图标所用图片大小
            //         imageSize: new AMap.Size(19, 31)
            //     });

            //     var drivingOption = {
            //         policy: AMap.DrivingPolicy.LEAST_TIME, // 其它policy参数请参考 https://lbs.amap.com/api/javascript-api/reference/route-search#m_DrivingPolicy
            //         ferry: 1, // 是否可以使用轮渡
            //         map: map,
            //         hideMarkers: true, // 设置隐藏路径规划的起始点图标
            //         autoFitView: true
            //     };

            //     // 构造路线导航类
            //     var driving = new AMap.Driving(drivingOption);
            //     // 根据起终点经纬度规划驾车导航路线
            //     driving.search(
            //         new AMap.LngLat(113.746262, 23.046237),
            //         new AMap.LngLat(117.120098, 36.6512),
            //         {
            //             waypoints: [new AMap.LngLat(114.305215, 30.592935)]
            //         },
            //         function(status, result) {
            //             if (status === 'complete') {
            //                 console.log('绘制驾车路线完成');
            //             } else {
            //                 console.log('获取驾车数据失败：' + result);
            //             }
            //         }
            //     );

            //     var capitals = [
            //         {
            //             center: [113.746262, 23.046237]
            //         },
            //         {
            //             center: [114.305215, 30.592935]
            //         },
            //         {
            //             center: [117.120098, 36.6512]
            //         }
            //     ];

            //     var facilities = [];

            //     var Startmarker = new AMap.Marker({
            //         position: new AMap.LngLat(capitals[0].center[0], capitals[0].center[1]),
            //         offset: new AMap.Pixel(-10, -25),
            //         icon: startIcon
            //     });

            //     var Viamarker = new AMap.Marker({
            //         position: new AMap.LngLat(capitals[1].center[0], capitals[1].center[1]),
            //         offset: new AMap.Pixel(-15, -20),
            //         icon: viaIcon
            //     });

            //     var Endmarker = new AMap.Marker({
            //         position: new AMap.LngLat(capitals[2].center[0], capitals[2].center[1]),
            //         offset: new AMap.Pixel(-10, -25),
            //         icon: endIcon
            //     });
            //     facilities.push(Startmarker, Viamarker, Endmarker);

            //     map.add(facilities);

            //     // 设置label标签
            //     // label默认蓝框白底左上角显示，样式className为：amap-marker-label
            //     Viamarker.setLabel({
            //         offset: new AMap.Pixel(5, 0), //设置文本标注偏移量
            //         content: "<div class='info'>武汉</div>", //设置文本标注内容
            //         direction: 'right' //设置文本标注方位
            //     });
            // };
        }
    }
};
</script>
<style leng="scss" scoped>
#container1 {
    height: 353px;
    width: 400px;
}

.info {
    position: relative;
    top: 0;
    right: 0;
    /* width: 60px; */
    border: none;
}
.amap-marker-label {
    border: 0;
    background-color: transparent;
}

.amap-icon img {
    z-index: 10000;
}
</style>