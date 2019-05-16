
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>客户信息管理</title>
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
                    <a class="mini-button" iconCls="icon-download" plain="true" onclick="downCustomer()">下载客户信息</a>
                    <a class="mini-button" iconCls="icon-filter" plain="true" onclick="showBirthday()">今天生日客户</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-filter" plain="true" onclick="clearFilter()">清除过滤</a>
                    <a class="mini-button" iconCls="icon-upgrade" plain="true" onclick="sendEmail()">发送生日祝福</a>
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入客户名称" style="width:150px;" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:100%;height:460px;"  allowResize="true" url="/customer/findAll"
     idField="id" emptyText="当前数据为空，<a class='op-a' href='javascript:newRow()'>增加一条</a>" showEmptyText="true"
     allowRowSelect="true" multiSelect="true" allowUnselect="true"
>
    <div property="columns">
        <div type="checkcolumn"></div>
        <div type="indexcolumn"  align="center" headerAlign="center" width="50">编号</div>
        <div field="id" align="center" headerAlign="center" width="300">客户id</div>
        <div name="name" field="name" width="100" align="center"  headerAlign="center">客户名称</div>
        <div name="region" field="region" width="120" align="center" headerAlign="center">地区</div>
        <div field="tel" width="80" align="center" headerAlign="center">联系电话</div>
        <div field="fax" width="80" align="center" headerAlign="center">传真</div>
        <div name="level" field="level" width="80" align="center" headerAlign="center">客户等级</div>
        <div name="satify" field="satify" width="80" align="center" headerAlign="center">客户满意度</div>
        <div name="credit" field="credit" width="80" align="center" headerAlign="center">客户信用度</div>
        <div field="managerId" width="80" align="center" headerAlign="center" renderer="getManager">客户经理</div>
        <div name="state" field="state" width="70" align="center" headerAlign="center" renderer="parseStatus">状态</div>
        <div name="birthday" field="birthday" width="120" align="center" headerAlign="center" renderer="parseDate" dateFormat="yyyy-MM-dd">出生日期</div>
        <div name="email" field="email" width="120" align="center" headerAlign="center" renderer="">邮箱</div>
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
        grid.load({ name: key });
    }

    function parseDate(e) {
        return parseDateToStr(e.value);
    }


    function parseStatus(e) {
        var status=e.value;
        if(status=="启动"){
            return "<a style='color:#ffffff;background: #795548;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>启动</a>"
        }else{
            return "<a style='color:#ffffff;background: #2196f3;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>停用</a>"
        }
    }

    function clearFilter() {
        filter.clearAllFilter();
        grid.load();
    }
    //过滤处理
    var filter = new HeaderFilter(grid, {
        columns: [
            { name: 'name' },
            { name: 'region' },
            { name: 'level' },
            { name: 'satify' },
            { name: 'credit' },
            { name: 'state' }
        ],
        callback: function (column, filtered) {
        }
    });
    function getManager(e) {
        var arr=[];
        var id=e.value;
        $.ajax({
            url: "/user/findAllManager",
            async:false,
            cache: false,
            success: function (text) {
                if(text.length>0){
                    arr=text;
                }
            }
        });

        for (var i = 0, l = arr.length; i < l; i++) {
            var g = arr[i];
            if (g.id == e.value) return g.text;
        }
        return "";
    }

    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var id = record.id;
        var rowIndex = e.rowIndex;
        var s = '<a  class="op-a icon-edit" href="javascript:editRow(\'' + id + '\')">编辑</a>'
                + ' <a  class="op-a icon-remove" href="javascript:delRow(\'' + id + '\')">删除</a>';
        return s;
    }

    function newRow() {
        mini.open({
            targetWindow: window,
            url: "/customer/addUI",
            title: "新增客户信息",
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

    function editRow(row) {
        if(row){
            mini.open({
                url: "/customer/addUI",
                title: "编辑客户信息", width:600, height: 500,
                showMaxButton:true,
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
            mini.confirm("确定删除该客户？","删除客户",
                    function (action) {
                        if (action == "ok") {
                            grid.loading("删除中，请稍后......");
                            $.ajax({
                                url: "/customer/remove?id=" + row,
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

    /*    var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();           //秒*/

        var clock = year + "-";

        if(month < 10)
            clock += "0";

        clock += month + "-";

        if(day < 10)
            clock += "0";

        clock += day + " ";

       /* if(hh < 10)
            clock += "0";

        clock += hh + ":";
        if (mm < 10) clock += '0';
        clock += mm + ":";

        if (ss < 10) clock += '0';
        clock += ss;*/
        return(clock);
    }
    function downCustomer() {
        var form = $("<form>"); //定义一个form表单
        form.attr("style", "display:none");
        form.attr("target", "");
        form.attr("method", "get");
        form.attr("action", "/customer/exportExcel");
        var input1 = $("<input>");
        input1.attr("type", "hidden");
        input1.attr("name", "type");
        input1.attr("value", "2");
        $("body").append(form); //将表单放置在web中
        form.append(input1);

        form.submit(); //表单提交
    }


    //显示生日客户
    function showBirthday(){
        grid.load({
            showBirthday:true
        });
    }

    function sendEmail() {
        let rows=grid.getSelecteds();
        if(rows){
            $.ajax({
                type:'POST',
                url: "/customer/sendEmail",
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