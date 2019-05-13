

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>客户流失监控</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${request.contextPath}/static/scripts/miniui/filter/HeaderFilter.css"/>
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <script type="text/javascript" src="${request.contextPath}/static/scripts/miniui/filter/HeaderFilter.js"></script>
    <style type="text/css">
        body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
        }
        .op-a{
            width:87px;
            display: inline-block;
            color: #20a98a;
        }
        .op-a:hover{
            color: #2f4f4f;
        }
    </style>
</head>
<body style="margin: 5px">
<div style="width:100%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="newRow()" plain="true" tooltip="增加">增加</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-filter" plain="true" onclick="showReview()">今天流失客户回访</a>
                    <a class="mini-button" iconCls="icon-filter" plain="true" onclick="clearFilter()">清除过滤</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入客户名称" style="width:150px;" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:460px;"  allowResize="true" url="/customerDrains/findAll"
       idField="id" emptyText="当前数据为空，<a class='op-a' href='javascript:newRow()'>增加一条</a>" showEmptyText="true"
>
    <div property="columns">
        <div type="indexcolumn" field="id" align="center" headerAlign="center">编号</div>
        <div name="customerName" field="customerName" width="120"  align="center" headerAlign="center">客户名称</div>
        <div field="reason" width="100" align="center" headerAlign="center">流失原因</div>
        <div field="drainDate" width="120" align="center" headerAlign="center" renderer="parseDate" dateFormat="yyyy-MM-dd">流失时间</div>
        <div field="delay" width="100" align="center" headerAlign="center">暂缓流失计划</div>
        <div name="status" field="status" width="100"  align="center" headerAlign="center" renderer="parseStatus">流失状态</div>
        <div field="lastOrderDate" width="100"  align="center" headerAlign="center" renderer="parseDate" dateFormat="yyyy-MM-dd">最后订单时间</div>
        <div name="action" width="180" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
    function search() {
        var key = mini.get("key").getValue();
        grid.load({ customerName: key });
    }
    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var id = record.id;
        var rowIndex = e.rowIndex;

        var s = ' <a  class="op-a icon-nowait" href="javascript:waitRow(\'' + id + '\')">暂缓流失</a>'
                + ' <a  class="op-a icon-ok" href="javascript:okRow(\'' + id + '\')">确认流失</a>';
        return s;
    }
    function parseStatus(e) {
        var status=e.value;
        if(status==="流失预警"){
            return "<a style='color:#ffffff;background: #ff5722;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>流失预警</a>";
        }else if(status==="确认流失"){
            return "<a style='color:#ffffff;background: #4caf50;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>确认流失</a>";
        }else{
            return "<a style='color:#ffffff;background: #cddc39;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>暂缓流失</a>";
        }
    }
    function clearFilter() {
        filter.clearAllFilter();
    }
    //过滤处理
    var filter = new HeaderFilter(grid, {
        columns: [
            { name: 'customerName' },
            { name: 'status' }
        ],
        callback: function (column, filtered) {
        }
    });
    function okRow(row) {
        if(row){
            mini.open({
                url: "/customerDrains/addUI",
                title: "确认流失", width:600, height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: row };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });

        }
    }

    function waitRow(row) {
        if (row) {
            $.ajax({
                url: "/customerDrains/waitDrains?id=" + row,
                success: function (text) {
                    if(text.state==0){
                        grid.reload();
                    }else{
                        mini.alert("执行暂缓失败，"+text.message);
                    }
                }
            });
        }
    }
    function parseDate(e) {
        return parseDateToStr(e.value);
    }
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


    function newRow() {
        mini.open({
            targetWindow: window,
            url: "/customerDrains/addUI",
            title: "新增客户流失信息",
            async: true,
            width: 600,
            height: 500,
            showMaxButton:true,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action: "new" };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                grid.reload();
            }
        });
    }
</script>