
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>菜单列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <script type="text/javascript" src="${request.contextPath}/static/js/treesort.js"></script>
</head>
<body style="margin: 5px">
<div class="mini-toolbar" style="padding:0px;border-radius: 4px 4px 0px 0px;border-bottom:0;">
    <table style="width:100%;">
        <tr>
            <td style="width:100%;">
                <a class="mini-button" iconCls="icon-add" plain="true"  onclick="add()">新增</a>
                <span class="separator"></span>
                <a class="mini-button" iconCls="icon-edit" plain="true"  onclick="edit()">修改</a>
                <span class="separator"></span>
                <a class="mini-button" iconCls="icon-remove" plain="true"  onclick="remove()">删除</a>
                <span class="separator"></span>
                <a class="mini-button" iconCls="icon-reload" plain="true" onclick="reflash()">刷新</a>
            </td>
        </tr>
    </table>
</div>

<div id="treegrid1" class="mini-treegrid" style="width:100%;height:460px;"
     showTreeIcon="true"
     treeColumn="taskname" idField="id" parentField="pid" resultAsTree="false" showCheckBox="true"
     expandOnLoad="true" checkRecursive="false"
>
    <div property="columns">
        <div type="indexcolumn" field="id" align="center" headerAlign="center">编号</div>
        <div name="taskname" field="name" width="100" headerAlign="center">资源名称</div>
        <div field="url" width="80" align="center" headerAlign="center">资源路径</div>
        <div field="seq" width="60" align="center" headerAlign="center">排序</div>
        <div field="iconCls" width="80" align="center" headerAlign="center" renderer="parseIcon">图标</div>
        <div name="resourceType" field="resourceType" width="80" align="center" headerAlign="center" renderer="parseType">资源类型</div>
        <div field="status" width="80" align="center" headerAlign="center" renderer="parseStatus">状态</div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    var tree = mini.get("treegrid1");
    $(function () {
        loadDataList();
    })
    function loadDataList() {
        $.ajax({
            url: "/menu/getMenuList",
            async: true,
            type: "get",
            dataType: "json",
            success: function (res) {
                var data=res.rows;
                if(data.length>0){
                    tree.loadData(data);
                    /* var sorter = new TreeSort(tree);
                     sorter.sort("seq", "desc");*/
                }
            }
        });
    }

    function reflash() {
        loadDataList();
    }

    function add() {
        mini.open({
            targetWindow: window,
            url: "/resource/addUI",
            title: "新增菜单",
            width: 600,
            height: 430,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action: "new" };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                loadDataList();
            }
        });
    }

    function getCheckedNodes() {
        var tree = mini.get("treegrid1");
        var value = tree.getValue();
        return value;
    }
    function edit() {
        var row=getCheckedNodes();
        if(!row || row.split(",").length>1){
            mini.alert("请选中一条记录");
            return;
        }else{
            mini.open({
                url: "/resource/addUI",
                title: "编辑菜单",
                width: 600,
                height:430,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: row };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    loadDataList();
                }
            });

        }

    }
    function remove() {
        var row=getCheckedNodes();
        //mini.alert(row);
        if (row) {
            mini.confirm("确定删除选中菜单及其子菜单？","删除菜单",
                    function (action) {
                        if (action == "ok") {
                            //tree.loading("操作中，请稍后......");
                            $.ajax({
                                url: "/menu/delete?ids=" +row,
                                success: function (text) {
                                    if(text.state==0){
                                        mini.alert("删除成功");
                                        loadDataList();
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
        } else {
            mini.alert("请选中一条记录");
        }
    }

    function parseIcon(e) {
        var icon=e.value;
        return "<a class='"+icon+"' style='width: 115px;display: inline-block;color: #20a98a;' >"+icon+"</a>";
    }
    //#1c84c6
    function parseType(e) {
        var type=e.value;
        if(type=="菜单"){
            return "<a style='color:#ffffff;background: #ed5565;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>菜单</a>"
        }else{
            return "<a style='color:#ffffff;background: #8bc34a;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>按钮</a>"
        }
    }
    //#FF5722
    function parseStatus(e) {
        var status=e.value;
        if(status=="启动"){
            return "<a style='color:#ffffff;background: #795548;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>启动</a>"
        }else{
            return "<a style='color:#ffffff;background: #2196f3;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>停用</a>"
        }
    }
</script>