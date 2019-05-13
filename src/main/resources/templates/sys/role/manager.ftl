

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>角色列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
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
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入角色名" style="width:150px;" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="datagrid1" class="mini-datagrid" style="width:100%;height:460px;"  allowResize="true" url="/role/getRoleList"
       idField="id" emptyText="当前数据为空，<a class='op-a' href='javascript:newRow()'>增加一条</a>" showEmptyText="true"
>
    <div property="columns">
        <div type="indexcolumn" field="id" align="center" headerAlign="center">编号</div>
        <div name="taskname" field="name" width="80" align="center" headerAlign="center">角色名</div>
        <div field="description" width="100" align="center" headerAlign="center">描述</div>
        <div field="seq" width="60" align="center" headerAlign="center">排序</div>
        <div field="status" width="60" align="center" headerAlign="center" renderer="onStatusRenderer">状态</div>
        <div name="action" width="130" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
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
        grid.load({ name: key });
    }
    //回车键搜索
    function onKeyEnter(e) {
        search();
    }
    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var id = record.id;
        var rowIndex = e.rowIndex;

        var s = ' <a  class="op-a icon-edit" href="javascript:editRow(\'' + id + '\')">编辑</a>'
                + ' <a  class="op-a icon-remove" href="javascript:delRow(\'' + id + '\')">删除</a>'
                + ' <a  class="op-a icon-lock" href="javascript:selRow(\'' + id + '\')">授权</a>';
        return s;
    }
    //处理状态表示
    var Status = [{ id: 0, text: "<a style='color:#ffffff;background: #795548;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>启动</a>" }, { id: 1, text: "<a style='color:#ffffff;background: #2196f3;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>停用</a>"}];
    function onStatusRenderer(e) {
        for (var i = 0, l = Status.length; i < l; i++) {
            var g = Status[i];
            if (g.id == e.value) return g.text;
        }
        return "";
    }

    function newRow() {
        mini.open({
            targetWindow: window,
            url: "/role/addUI",
            title: "新增角色",
            width: 300,
            height: 260,
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
                url: "/role/addUI",
                title: "编辑角色", width:300, height: 260,
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

    function cancelRow() {
        grid.reload();
        editWindow.hide();
    }

    function delRow(row) {
        if (row) {
            mini.confirm("确定删除该角色？","删除角色",
                    function (action) {
                        if (action == "ok") {
                            grid.loading("删除中，请稍后......");
                            $.ajax({
                                url: "/role/remove?id=" + row,
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

    function selRow(row) {
        if(row){
            mini.open({
                url: "/role/rightUI",
                title: "编辑权限", width:740, height: 560,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: row };
                    iframe.contentWindow.SetRight(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });

        }
    }
</script>