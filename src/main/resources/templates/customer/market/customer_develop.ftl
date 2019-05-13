
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>客户开发计划</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${request.contextPath}/static/scripts/miniui/filter/HeaderFilter.css"/>
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <script type="text/javascript" src="${request.contextPath}/static/js/cookie_util.js"></script>
    <script type="text/javascript" src="${request.contextPath}/static/scripts/miniui/filter/HeaderFilter.js"></script>
    <style type="text/css">
        body{
            margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
        }
        .op-a{
            width:82px;
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
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:460px;"  allowResize="true" url="/market/findAllPlan"
     idField="id" emptyText="当前数据为空，<a class='op-a' href='javascript:newRow()'>增加一条</a>" showEmptyText="true"
>
    <div property="columns">
        <div type="indexcolumn" field="id" align="center" headerAlign="center">编号</div>
        <div name="custName" field="custName" width="80" align="center"  headerAlign="center">客户名称</div>
        <div name="contact" field="contact" width="80" align="center" headerAlign="center">联系人</div>
        <div field="contactTel" width="100" align="center" headerAlign="center">联系人电话</div>
        <div field="toDo" width="140" align="center" headerAlign="center">计划内容</div>
        <div field="date" width="120" align="center" headerAlign="center" dateFormat="yyyy-MM-dd" renderer="parseDate">计划实施时间</div>
        <div name="result" field="result" width="80" align="center" headerAlign="center" renderer="parseResult">结果</div>
        <div name="action" width="200" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    mini.parse();
    var  grid= mini.get("datagrid1");
    grid.load({designee:getCookie("loginName")});
    console.log(getCookie("loginName"));
    function search() {
        var key = mini.get("key").getValue();
        grid.load({ custName: key });
    }

    function parseDate(e) {
        return parseDateToStr(e.value);
    }
    function parseResult(e) {
        var result=e.value;
        if(result=="开发失败"){
            return "<a style='color:#ffffff;background: #ed5565;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>开发失败</a>";
        }else if(result=="开发成功"){
            return "<a style='color:#ffffff;background: #8bc34a;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>开发成功</a>";
        }else{
            return "<a style='color:#ffffff;background: #f38f3c;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>开发中</a>";
        }
    }
    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var id = record.id;
        var result=record.result;
        var rowIndex = e.rowIndex;

        var s = ' <a  class="op-a icon-goto" href="javascript:openRow(\'' + id + '\',\''+result+'\')">执行开发</a>'
                + ' <a  class="op-a icon-ok" href="javascript:verRow(\'' + id + '\',\'开发成功\',\''+result+'\')">开发成功</a>'
                + ' <a  class="op-a icon-no" href="javascript:verRow(\'' + id + '\',\'开发失败\',\''+result+'\')">开发失败</a>';
        return s;
    }
    function clearFilter() {
        filter.clearAllFilter();
    }
    //过滤处理
    var filter = new HeaderFilter(grid, {
        columns: [
            { name: 'custName' },
            { name: "contact" },
            { name: "result" }
        ],
        callback: function (column, filtered) {
        }
    });

    function openRow(row,result) {
        if(result=="开发中"){
            mini.alert("不能重复开发");
            return;
        }
        if (row) {
            mini.open({
                targetWindow: window,
                url: "/market/exploitUI",
                title: "执行开发",
                async: true,
                width: 600,
                height: 360,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit",id:row };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }

    }
    function verRow(row,obj,result) {
        if(result=="未开发"){
            mini.alert("暂未开发");
            return;
        }
        if (row) {
            $.ajax({
                url: "/market/verRow?id=" + row+"&op="+obj,
                success: function (text) {
                    if(text.state==0){
                        grid.reload();
                        mini.alert("操作成功");
                    }else{
                        mini.alert("操作失败，"+text.message);
                    }
                },
                error: function () {
                }
            });
        }

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
</script>