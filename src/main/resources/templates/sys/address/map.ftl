
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
        #allmap{height:100%;width:100%;}
        #r-result{width:100%; font-size:14px;}
    </style>
    <link rel="stylesheet" href="${request.contextPath}/static/scripts/baidugis/map.css"/>
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=hTbz2yG9qhphm2yR6xBABXow"></script>
    <script type=text/javascript src="${request.contextPath}/static/scripts/baidugis/GpsConvert.js"></script>
    <title>城市名定位</title>
</head>
<body>
<div id="allmap"></div>
<div id="r-result" style=" position: fixed;top: 10px;left: 79%;background: #ffffffe0;padding: 8px 23px;width: 216px;border-radius: 3px;">
    <input id="cityName" type="text" style="width:140px; margin-right:10px;padding: 3px;color: red;" placeholder="请输入城市名"/>
    <input type="button" value="查询" onclick="theLocation()" />
</div>
</body>
</html>
<script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(113.371398,23.137445);
    map.centerAndZoom(point,14);
    var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP]});
    var overView = new BMap.OverviewMapControl();
    var overViewOpen = new BMap.OverviewMapControl({isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
    map.addControl(mapType1);  //添加地图类型
    map.addControl(overView);
    map.addControl(overViewOpen);
    var navigationControl = new BMap.NavigationControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        enableGeolocation: true
    });
    var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
    map.addControl(navigationControl);//添加比例尺工具
    map.addControl(top_left_control);//添加比例尺

    map.enableScrollWheelZoom();//能够缩放，滚动
    map.enableContinuousZoom();
    var pointArray={
        id:'1',
        longitude:113.371398,
        latitude:23.137445,
        country:'天河区'
    };
    drawPoint(pointArray, "公司地址");


    function theLocation(){
        var city = document.getElementById("cityName").value;
        if(city != ""){
            map.centerAndZoom(city,14);      // 用城市名设置地图中心点
        }
    }

    function drawPoint(pointArray, websiteName) {
        var point = new BMap.Point(pointArray.longitude, pointArray.latitude);
        var marker = new BMap.Marker(point);  // 创建标注
        map.addOverlay(marker);
        var label = new BMap.Label(websiteName, {offset: new BMap.Size(20, 0)});
        marker.setLabel(label);

        var opts = {
            width: 190,     // 信息窗口宽度
            height: 100,     // 信息窗口高度
            title: "<span style='font-size: 16px;font-weight: 600;'>某某科技股份公司</span>"  // 信息窗口标题
        };
        var originPoint=GPS.bd_wgs(parseFloat(pointArray.latitude), parseFloat(pointArray.longitude));

        var windows="<div style='font-size: 14px;line-height: 24px;margin-top: 9px;'>" +
                "<i style='display: none'>"+pointArray.id+"</i>" +
                "经度:" + originPoint.lng + "<br>" +
                "纬度:" + originPoint.lat + "<br>" +
                "所属县区:" + (pointArray.country == undefined?"":pointArray.country)+"</div>";

        var infoWindow = new BMap.InfoWindow(windows, opts);  // 创建信息窗口对象

        marker.addEventListener("click", function () {
            map.openInfoWindow(infoWindow, point); //开启信息窗口
        });
    }
</script>
