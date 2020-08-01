<template>
    <div class="map1">
        <div id="container1"></div>
    </div>
</template>
<script>
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
            window.onload = function() {
                var map = new AMap.Map('container', {
                    zoom: 4
                });
                AMapUI.load(['ui/misc/PathSimplifier', 'lib/$'], function(PathSimplifier, $) {
                    if (!PathSimplifier.supportCanvas) {
                        alert('当前环境不支持 Canvas！');
                        return;
                    }
                    var defaultRenderOptions = {
                        renderAllPointsIfNumberBelow: -1, //绘制路线节点，如不需要可设置为-1
                        pathLineStyle: {
                            //路线样式设置
                            lineWidth: 3,
                            strokeStyle: '#FFEC7A',
                            borderWidth: 1,
                            borderStyle: '#eeeeee',
                            dirArrowStyle: false
                        },
                        startPointStyle: {
                            //起点
                            radius: 4,
                            fillStyle: '#109618',
                            lineWidth: 1,
                            strokeStyle: '#eeeeee'
                        },
                        endPointStyle: {
                            //终点
                            radius: 4,
                            fillStyle: '#dc3912',
                            lineWidth: 1,
                            strokeStyle: '#eeeeee'
                        }
                    };
                    var pathSimplifierIns = new PathSimplifier({
                        zIndex: 100,
                        //autoSetFitView:false,
                        map: map, //所属的地图实例

                        getPath: function(pathData, pathIndex) {
                            return pathData.path;
                        },
                        getHoverTitle: function(pathData, pathIndex, pointIndex) {
                            if (pointIndex >= 0) {
                                //point
                                return pathData.name + '，点：' + pointIndex + '/' + pathData.path.length;
                            }

                            return pathData.name + '，点数量' + pathData.path.length;
                        },
                        renderOptions: defaultRenderOptions
                    });

                    window.pathSimplifierIns = pathSimplifierIns;
                    //设置数据
                    pathSimplifierIns.setData([
                        {
                            name: '路线0',
                            path: [
                                [116.405289, 39.904987],
                                [113.964458, 40.54664],
                                [111.47836, 41.135964],
                                [108.949297, 41.670904],
                                [106.380111, 42.149509],
                                [103.774185, 42.56996],
                                [101.135432, 42.930601],
                                [98.46826, 43.229964],
                                [95.777529, 43.466798],
                                [93.068486, 43.64009],
                                [90.34669, 43.749086],
                                [87.61792, 43.793308]
                            ]
                        }
                    ]);

                    //对第一条线路（即索引 0）创建一个巡航器
                    var navg1 = pathSimplifierIns.createPathNavigator(0, {
                        loop: false, //循环播放
                        speed: 1000000, //巡航速度，单位千米/小时
                        pathNavigatorStyle: {
                            width: 44,
                            height: 54,
                            //使用图片
                            content: PathSimplifier.Render.Canvas.getImageContent(imgUrl, onload, onerror),
                            strokeStyle: null,
                            fillStyle: null,
                            //经过路径的样式
                            pathLinePassedStyle: {
                                lineWidth: 5,
                                strokeStyle: '#FFDC04'
                            }
                        }
                    });
                    navg1.start();
                });
            };
        }
    }
};
</script>
<style leng="scss" scoped>
#container1 {
    height: 600px;
    width: 600px;
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