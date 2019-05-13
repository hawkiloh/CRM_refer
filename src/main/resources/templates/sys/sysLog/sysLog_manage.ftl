
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>日志管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${request.contextPath}/static/scripts/miniui/filter/HeaderFilter.css"/>
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <script type="text/javascript" src="${request.contextPath}/static/scripts/miniui/filter/HeaderFilter.js"></script>
    <style type="text/css">
        body{
            margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
        }
    </style>
</head>
<body style="margin: 5px">
<div style="width:100%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-filter" plain="true" onclick="clearFilter()">清除过滤</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:460px;"  allowResize="true" url="/sysLog/findAll"
     idField="id" emptyText="当前数据为空，<a class='op-a' href='javascript:newRow()'>增加一条</a>" showEmptyText="true"
>
    <div property="columns">
        <div type="indexcolumn" field="id" align="center" headerAlign="center">编号</div>
        <div name="method" field="method" width="200" align="center"  headerAlign="center">方法名</div>
        <div field="params" width="200" align="center" headerAlign="center">参数</div>
        <div field="msg" width="100" align="center" headerAlign="center">操作</div>
        <div field="time" width="80" align="center" headerAlign="center">执行时长</div>
        <div field="userName" width="100" align="center" headerAlign="center">用户</div>
        <div field="ip" width="100" align="center" headerAlign="center">IP</div>
        <div field="createTime" width="100" align="center" headerAlign="center" dateFormat="yyyy-MM-dd" renderer="parseDate">时间</div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    mini.parse();
    var  grid= mini.get("datagrid1");
    grid.load();

   /* function search() {
        var key = mini.get("key").getValue();
        grid.load({ name: key });
    }*/

    function parseDate(e) {
        return parseDateToStr(e.value);
    }
    function clearFilter() {
        filter.clearAllFilter();
    }
    //过滤处理
    var filter = new HeaderFilter(grid, {
        columns: [
            { name: 'method' }
        ],
        callback: function (column, filtered) {
        }
    });


    function parseDateToStr(data)
    {
        var now = new Date(data);

        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日

        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();           //秒

        var clock = year + "-";

        if(month < 10)
            clock += "0";

        clock += month + "-";

        if(day < 10)
            clock += "0";

        clock += day + " ";

        if(hh < 10)
            clock += "0";

        clock += hh + ":";
        if (mm < 10) clock += '0';
        clock += mm + ":";

        if (ss < 10) clock += '0';
        clock += ss;
        return(clock);
    }
</script>