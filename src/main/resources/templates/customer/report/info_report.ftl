
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>客户信息分析</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <style type="text/css">
        .b{
            height:540px;width:98%;
            display: inline-block;
            margin: 1%;
        }
    </style>
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <script type="text/javascript" src="${request.contextPath}/static/js/echarts.js"></script>

</head>
<body>
<div id="win1" class="mini-window" title="客户信息分析" style="width:100%;height:100%;"
     showToolbar="false" showFooter="true" showModal="true" allowResize="true">

    <span>请选择：</span>
    <input type="button" value="客户等级分析" onclick="showChartWindow(this)" />
    <input type="button" value="客户满意度分析" onclick="showChartWindow(this)" />
    <input type="button" value="客户信用度分析" onclick="showChartWindow(this)" />
    <br/>

    <div class="ct b" id="chart1"></div>
</div>
</body>
</html>


<script type="text/javascript">
    mini.parse();
    var data1=[],
            data2=[],
            data3=[];
    function CountCustomer() {
        $.ajax({
            url: "/report/CountCustomer",
            type: 'get',
            async: false,
            cache: false,
            success: function (count) {
                if(count.length>0) {
                    data1=count[0];
                    data2 = count[1];
                    data3 = count[2];

                }
            }
        });
    }

    CountCustomer();


    function buildChart(e) {
        var chart1 = echarts.init(document.getElementById('chart1'));
        if(!e || e.value=="客户等级分析") {
            chart1.setOption(option1);
        }else if(e.value=="客户满意度分析") {
            chart1.setOption(option2);
        }else if(e.value=="客户信用度分析") {
            chart1.setOption(option3);
        }
    }
    function showChartWindow(e) {
        var win = mini.get("win1");
        win.show();
        buildChart(e);
    }

    var option1 = {
        title: {
            text: '客户等级信息分析',
            subtext: '',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['大客户', '合作伙伴', '普通客户', '战略合作伙伴']
        },
        series: [
            {
                name: '客户等级',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: data1,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    var option2 = {
        title: {
            text: '客户满意度分析',
            subtext: '',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['一星', '二星', '三星', '四星','五星']
        },
        series: [
            {
                name: '客户满意度',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: data2,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    var option3 = {
        title: {
            text: '客户信用度分析',
            subtext: '',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['一星', '二星', '三星', '四星','五星']
        },
        series: [
            {
                name: '客户信用度',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: data3,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    showChartWindow();

</script>