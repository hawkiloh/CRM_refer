
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <script type="text/javascript" src="${request.contextPath}/static/js/treesort.js"></script>
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
<div class="mini-toolbar" style="padding:0px;border-radius: 4px 4px 0px 0px;border-bottom:0;">
    <a class="mini-button" iconCls="icon-add" plain="true"  onclick="newRow()">新增</a>
</div>
<div id="treegrid1" class="mini-treegrid" style="width:100%;height:460px;"
     showTreeIcon="true"
     treeColumn="taskname" idField="id" parentField="pid" resultAsTree="false" url="/organization/getAll"
     expandOnLoad="true"
>
    <div property="columns">
        <div field="code" width="80" align="center" headerAlign="center">编号</div>
        <div name="taskname" field="name" width="160" headerAlign="center">部门名称</div>
        <div field="seq" width="60" align="center" headerAlign="center">排序</div>
        <div field="iconCls" width="100" align="center" headerAlign="center" renderer="parseIcon">图标</div>
        <div field="createTime" width="120" align="center" headerAlign="center">创建时间</div>
        <div field="address" name="address" align="center" width="160" headerAlign="center">地址</div>
        <div name="action" width="140" headerAlign="center" align="center" renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    mini.parse();
    var tree = mini.get("treegrid1");
    tree.load();

    function onActionRenderer(e) {
        var grid = e.sender;
        var record = e.record;
        var id = record.id;
        var rowIndex = e.rowIndex;

        var s = ' <a  class="op-a icon-edit" href="javascript:editRow(\'' + id + '\')">编辑</a>'
                + ' <a  class="op-a icon-remove" href="javascript:delRow(\'' + id + '\')">删除</a>';
        return s;
    }
    function parseIcon(e) {
        var icon=e.value;
        return "<a class='"+icon+"' style='width: 115px;display: inline-block;color: #20a98a;' >"+icon+"</a>";
    }
    function newRow() {
        mini.open({
            targetWindow: window,
            url: "/organization/addUI",
            title: "新增部门",
            async: true,
            width: 600,
            height: 410,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action: "new" };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                tree.reload();
            }
        });
    }

    function editRow(row) {
        if(row){
            mini.open({
                url: "/organization/addUI",
                title: "编辑部门",
                width:600,
                height: 410,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: row };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                   tree.reload();
                }
            });

        }
    }

    function delRow(row) {
        if (row) {
            mini.confirm("确定删除该部门？","删除部门",
                    function (action) {
                        if (action == "ok") {
                            tree.loading("删除中，请稍后......");
                            $.ajax({
                                url: "/organization/remove?id=" + row,
                                success: function (text) {
                                    if(text.state==0){
                                        tree.reload();
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
</script>