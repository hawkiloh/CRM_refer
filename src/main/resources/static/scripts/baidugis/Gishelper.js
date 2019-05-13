function RAD(LON_LAT) {
    return LON_LAT * Math.PI / 180;
}

function Rc() {
    return 6378137;
}

function Rj() {
    return 6356725;
}

function EC(m_Latitude) {
    return Rj() + (Rc() - Rj()) * (90 - m_Latitude) / 90;
}

function ED(m_Latitude) {
    return EC(m_Latitude) * Math.cos(RAD(m_Latitude));
}

// 获取经纬度
function EOffsetBearing(lng, lat, distance, angle) {
    var Lon2;
    var Lat2;
    var dx;
    var dy;
    var BJD;
    var BWD;
    var Ed1;
    var Ec1;
    var m_RadLo1;
    var m_RadLa1;
    m_RadLo1 = RAD(lng);
    m_RadLa1 = RAD(lat);
    angle = angle % 360;
    Ed1 = ED(lat);
    Ec1 = EC(lat);
    dx = distance * 1 * Math.sin(angle * Math.PI / 180);
    dy = distance * 1 * Math.cos(angle * Math.PI / 180);
    BJD = (dx / Ed1 + m_RadLo1) * 180 / Math.PI;
    BWD = (dy / Ec1 + m_RadLa1) * 180 / Math.PI;
    Lon2 = BJD;
    Lat2 = BWD;
    return new BMap.Point(Lon2, Lat2);
}

function Sector0(point2,// 起始点
                 radius,// 半径
                 sDegree,// 起始角度
                 eDegree,// 结束角度
                 strokeColour,// 边颜色
                 strokeWeight,// 边粗细
                 strokepacity,// 边透明度
                 fillColour,// 填充色
                 fillOpacity,// 填充色透明度
                 zoom) {
    var points = [];
    var multiple = 1;
    if (zoom == 19) {
        multiple = 0.5;
    } else if (zoom == 18) {
        multiple = 1;
    } else if (zoom == 17) {
        multiple = 2;
    } else if (zoom == 16) {
        multiple = 4;
    } else if (zoom == 15) {
        multiple = 8;
    } else if (zoom == 14) {
        multiple = 15;
    } else if (zoom == 13) {
        multiple = 30;
    } else if (zoom == 12) {
        multiple = 60;
    } else if (zoom == 11) {
        multiple = 120;
    } else if (zoom == 10) {
        multiple = 220;
    }
    eDegree = eDegree + sDegree;
    points.push(point2);
    points.push(EOffsetBearing(point2.lng, point2.lat, radius * multiple, sDegree));
    points.push(EOffsetBearing(point2.lng, point2.lat, radius * multiple, eDegree));
    points.push(point2);

    var polygon = new BMap.Polygon(points, {
        strokeColor: strokeColour,
        strokeWeight: strokeWeight,
        strokeOpacity: strokepacity,
        fillColor: fillColour,
        fillOpacity: fillOpacity
    });

    return polygon;
}

function Sector1(point2,// 起始点
                 radius,// 半径
                 sDegree,// 起始角度
                 eDegree,// 结束角度
                 strokeColour,// 边颜色
                 strokeWeight,// 边粗细
                 strokepacity,// 边透明度
                 fillColour,// 填充色
                 fillOpacity,// 填充色透明度
                 opts) {
    var points = [];

    var step = 1;
    if (eDegree <= 30) {
        step = (eDegree / 10) || 10;
    } else {
        step = (eDegree / 15) || 15;
    }

    eDegree = eDegree + sDegree;
    points.push(point2);
    for (var i = sDegree; i <= eDegree; i += step) {
        points.push(EOffsetBearing(point2.lng, point2.lat, radius, i));
    }
    points.push(point2);

    var polygon = new BMap.Polygon(points, {
        strokeColor: strokeColour,
        strokeWeight: strokeWeight,
        strokeOpacity: strokepacity,
        fillColor: fillColour,
        fillOpacity: fillOpacity
    });

    return polygon;
}

function Sector2(point2,// 起始点
                 radius,// 半径
                 strokeColour,// 边颜色
                 strokeWeight,// 边粗细
                 strokepacity,// 边透明度
                 fillColour,// 填充色
                 fillOpacity,// 填充色透明度
                 opts) {
    var points = [];

    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 120));
    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 240));
    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 360));
    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 120));

    var polygon = new BMap.Polygon(points, {
        strokeColor: strokeColour,
        strokeWeight: strokeWeight,
        strokeOpacity: strokepacity,
        fillColor: fillColour,
        fillOpacity: fillOpacity
    });

    return polygon;
}

function Sector3(point2,// 起始点
                 radius,// 半径
                 strokeColour,// 边颜色
                 strokeWeight,// 边粗细
                 strokepacity,// 边透明度
                 fillColour,// 填充色
                 fillOpacity,// 填充色透明度
                 opts) {
    var points = [];

    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 45));
    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 135));
    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 225));
    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 315));
    points.push(EOffsetBearing(point2.lng, point2.lat, radius, 45));

    var polygon = new BMap.Polygon(points, {
        strokeColor: strokeColour,
        strokeWeight: strokeWeight,
        strokeOpacity: strokepacity,
        fillColor: fillColour,
        fillOpacity: fillOpacity
    });

    return polygon;
}