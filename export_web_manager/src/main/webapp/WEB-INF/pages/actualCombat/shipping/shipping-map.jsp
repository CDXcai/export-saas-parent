<%--
  Created by IntelliJ IDEA.
  User: 11720
  Date: 2020/2/11
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <style type="text/css">
        body, html, #allmap {
            width: 100%;
            height: 100%;
            overflow: hidden;
            margin: 0;
            font-family: "微软雅黑";
        }
        #frameContent{
            height: 700px;
        }
    </style>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=xY6gSY0UebFECECXBU6jpMqpI3bwbzGK"></script>

    <title>路线</title>
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <div id="allmap"></div>
</div>
</body>
</html>
<script type="text/javascript">

    // 百度地图API功能
    var map = new BMap.Map("allmap");

    // 获取坐标
    var $startPointLng = "";
    var $startPointLat = "";
    var $endPointLng = "";
    var $nedPointLat = "";
    /////////////////////////////////////////////////////////////////////
    var StartLocalSearch = new BMap.LocalSearch(map);

    StartLocalSearch.setSearchCompleteCallback(function (searchResult) {
        var poi = searchResult.getPoi(0);
        $startPointLng = poi.point.lng;
        $startPointLat = poi.point.lat;
    });

    StartLocalSearch.search("${startAddress}");


    var endLocalSearch = new BMap.LocalSearch(map);

    endLocalSearch.setSearchCompleteCallback(function (searchResult) {
        var poi = searchResult.getPoi(0);
        $endPointLng = poi.point.lng;
        $nedPointLat = poi.point.lat;
    });

    endLocalSearch.search("${endAddress}");

    $(function () {
        setTimeout(function () {


            map.centerAndZoom(new BMap.Point(116.404, 39.915), 15);

            var myP1 = new BMap.Point($startPointLng, $startPointLat);    //起点
            var myP2 = new BMap.Point($endPointLng, $nedPointLat);    //终点
            var myIcon = new BMap.Icon("http://lbsyun.baidu.com/img/Mario.png", new BMap.Size(32, 70), {    //小车图片
                //offset: new BMap.Size(0, -5),    //相当于CSS精灵
                imageOffset: new BMap.Size(0, 0)    //图片的偏移量。为了是图片底部中心对准坐标点。
            });
            var driving2 = new BMap.DrivingRoute(map, {renderOptions: {map: map, autoViewport: true}});    //驾车实例
            driving2.search(myP1, myP2);    //显示一条公交线路

            window.run = function () {
                var driving = new BMap.DrivingRoute(map);    //驾车实例
                driving.search(myP1, myP2);
                driving.setSearchCompleteCallback(function () {
                    var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组
                    var paths = pts.length;    //获得有几个点

                    var carMk = new BMap.Marker(pts[0], {icon: myIcon});
                    map.addOverlay(carMk);
                    i = 0;

                    function resetMkPoint(i) {
                        carMk.setPosition(pts[i]);
                        if (i < paths) {
                            setTimeout(function () {
                                i++;
                                resetMkPoint(i);
                            }, 100);
                        }
                    }

                    setTimeout(function () {
                        resetMkPoint(5);
                    }, 100)

                });
            }

            setTimeout(function () {
                run();
            }, 1500);
        }, 1000);
    })


    /////////////////////////////////////////////////////////////////////


    // map.centerAndZoom(new BMap.Point(116.404, 39.915), 15);
    //
    // var myP1 = new BMap.Point(110.380967,39.913285);    //起点
    // var myP2 = new BMap.Point(116.424374,39.914668);    //终点
    // var myIcon = new BMap.Icon("http://lbsyun.baidu.com/img/Mario.png", new BMap.Size(32, 70), {    //小车图片
    //     //offset: new BMap.Size(0, -5),    //相当于CSS精灵
    //     imageOffset: new BMap.Size(0, 0)    //图片的偏移量。为了是图片底部中心对准坐标点。
    // });
    // var driving2 = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});    //驾车实例
    // driving2.search(myP1, myP2);    //显示一条公交线路
    //
    // window.run = function (){
    //     var driving = new BMap.DrivingRoute(map);    //驾车实例
    //     driving.search(myP1, myP2);
    //     driving.setSearchCompleteCallback(function(){
    //         var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组
    //         var paths = pts.length;    //获得有几个点
    //
    //         var carMk = new BMap.Marker(pts[0],{icon:myIcon});
    //         map.addOverlay(carMk);
    //         i=0;
    //         function resetMkPoint(i){
    //             carMk.setPosition(pts[i]);
    //             if(i < paths){
    //                 setTimeout(function(){
    //                     i++;
    //                     resetMkPoint(i);
    //                 },100);
    //             }
    //         }
    //         setTimeout(function(){
    //             resetMkPoint(5);
    //         },100)
    //
    //     });
    // }
    //
    // setTimeout(function(){
    //     run();
    // },1500);
</script>


<!-- 根据地址找到坐标 -->
<!-- http://api.map.baidu.com/geocoding/v3/?address=北京市海淀区上地十街10号&output=json&ak=xY6gSY0UebFECECXBU6jpMqpI3bwbzGK&callback=showLocation -->

<!-- http://api.map.baidu.com/geocoding/v3/?address=北京南站&output=json&ak=xY6gSY0UebFECECXBU6jpMqpI3bwbzGK&callback=showLocation -->

