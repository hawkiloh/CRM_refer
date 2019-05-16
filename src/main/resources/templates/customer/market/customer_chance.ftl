
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>营销机会管理</title>
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
            width:60px;
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
                    <a class="mini-button" iconCls="icon-filter" plain="true" onclick="showReview()">订单回访</a>
                    <a class="mini-button" iconCls="icon-filter" plain="true" onclick="clearFilter()">清除过滤</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-upgrade" plain="true" onclick="sendEmail()">发送订单回访邮件</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入客户名称" style="width:150px;" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:460px;"  allowResize="true" url="/market/findAll"
     idField="id" emptyText="当前数据为空，<a class='op-a' href='javascript:newRow()'>增加一条</a>" showEmptyText="true"
     allowRowSelect="true" multiSelect="true" allowUnselect="true" virtualColumns="true"
>
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"  align="center" headerAlign="center">编号</div>
        <div field="id" align="center" headerAlign="center" width="300">订单id</div>
        <div name="custName" field="custName" width="100" align="center"  headerAlign="center">客户名称</div>
        <div field="title" width="160" align="center" headerAlign="center">概要</div>
        <div name="contact" field="contact" width="100" align="center" headerAlign="center">联系人</div>
        <div field="contactTel" width="100" align="center" headerAlign="center">联系人电话</div>
        <div field="createDate" width="180" align="center" headerAlign="center" dateFormat="yyyy-MM-dd" renderer="parseDate">创建时间</div>
        <div name="status" field="status" width="100" align="center" headerAlign="center" renderer="parseStatus">状态</div>
        <div name="action" width="180" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    mini.parse();
    var  grid= mini.get("datagrid1");
    grid.load();

    function search() {
        var key = mini.get("key").getValue();
        grid.load({ custName: key });
    }

    function parseDate(e) {
        return parseDateToStr(e.value);
    }
    function parseStatus(e) {
        var status=e.value;
        if(status=="未分配"){
            return "<a style='color:#ffffff;background: #ed5565;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>未分配</a>"
        }else{
            return "<a style='color:#ffffff;background: #8bc34a;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>已分配</a>"
        }
    }
    function clearFilter() {
        filter.clearAllFilter();
        grid.load();
    }
    //过滤处理
    var filter = new HeaderFilter(grid, {
        columns: [
            { name: 'custName' },
            { name: 'contact' },
            { name: 'status' }
        ],
        callback: function (column, filtered) {
        }
    });
    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var id = record.id;
        var rowIndex = e.rowIndex;
        var s = ' <a  class="op-a icon-user" href="javascript:giveRow(\'' + id + '\')">指派</a>'
                + ' <a  class="op-a icon-edit" href="javascript:editRow(\'' + id + '\')">编辑</a>'
                + ' <a  class="op-a icon-remove" href="javascript:delRow(\'' + id + '\')">删除</a>';
        return s;
    }

    function newRow() {
        mini.open({
            targetWindow: window,
            url: "/market/addUI",
            title: "新增订单",
            async: true,
            width: 750,
            height: 400,
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

    function editRow(row) {
        if(row){
            mini.open({
                url: "/market/addUI",
                title: "编辑客户订单", width:600, height: 500,
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

    function delRow(row) {
        if (row) {
            mini.confirm("确定删除该数据？","删除数据",
                    function (action) {
                        if (action == "ok") {
                            grid.loading("删除中，请稍后......");
                            $.ajax({
                                url: "/market/remove?id=" + row,
                                success: function (text) {
                                    if(text.state==0){
                                        grid.reload();
                                        mini.alert("删除成功");
                                    }else{
                                        mini.alert("删除失败，"+text.message);
                                    }
                                },
                                error: function () {
                                }
                            });
                        }
                    }
            );
        }
    }
    function giveRow(row) {
        if(row){
            mini.open({
                url: "/market/giveUI",
                title: "分配指派人", width:600, height: 260,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: row };
                    iframe.contentWindow.setData(data);

                },
                ondestroy: function (action) {
                    grid.reload();
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

    //显示回访订单
    function showReview(){
        grid.load({
            showReview:true
        });
    }

    function sendEmail() {
        let rows=grid.getSelecteds();
        if(rows){
            $.ajax({
                type:'POST',
                url: "/market/sendEmail",
                data:JSON.stringify(rows),
                contentType: "application/json;charset=UTF-8",
                success: function (text) {
                    if(text.state==0){
                        mini.alert("成功发送"+text.data+"封邮件。");
                    }else{
                        mini.alert("发送失败，"+text.message);
                    }
                },
                error: function () {
                }
            });

        }
        alert("已提交系统处理！")
    }
</script>