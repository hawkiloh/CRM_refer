
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户列表</title>
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
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="195" showCollapseButton="true">
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">
            <input class="mini-textbox" width="120" emptyText="请输入姓名" id="key" onenter="onKeyEnter"/>
            <a class="mini-button" iconCls="icon-search" plain="true" onclick="search()">查找</a>
        </div>
        <div class="mini-fit">
            <ul id="tree1" class="mini-tree" style="width:100%;"
                showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false" expandOnLoad="true"

            >
            </ul>
        </div>
    </div>
    <div showCollapseButton="true">
        <div class="mini-toolbar" style="padding:2px;border-top:0;border-left:0;border-right:0;">

            <a class="mini-button" iconCls="icon-add" plain="true" onclick="newRow()">新增</a>
            <span class="separator"></span>
            <a class="mini-button" iconCls="icon-edit" plain="true" onclick="editRow()">修改</a>
            <span class="separator"></span>
            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="delRow()">删除</a>
            <span class="separator"></span>
            <a class="mini-button" iconCls="icon-collapse" plain="true" onclick="showData()">展示全部</a>
            <span class="separator"></span>
            <a class="mini-button" iconCls="icon-filter" plain="true" onclick="clearFilter()">清除过滤</a>
        </div>
        <div id="grid1" class="mini-datagrid" style="width:100%;height:460px;"  allowResize="true" url="/user/findAll"
             idField="id" emptyText="当前数据为空，<a class='op-a' href='javascript:newRow()'>增加一条</a>" showEmptyText="true"
        >
            <div property="columns">
                <div type="indexcolumn" field="id" align="center" headerAlign="center">编号</div>
                <div name="taskname" field="loginName" width="80" align="center" headerAlign="center">登录名</div>
                <div  name="name" field="name" width="80" align="center" headerAlign="center">姓名</div>
                <div  name="organizationName" field="organizationName" width="100" align="center" headerAlign="center">所属部门</div>
                <div field="createTime" width="100" align="center" headerAlign="center" dateFormat="yyyy-MM-dd">创建时间</div>
                <div  name="sex" field="sex" width="80" align="center" headerAlign="center" renderer="onGenderRenderer">性别</div>
                <div  name="age" field="age" width="80" align="center" headerAlign="center">年龄</div>
                <div field="phone" width="100" align="center" headerAlign="center">电话</div>
                <div field="roles" width="120" align="center" headerAlign="center">角色</div>
                <div field="userType" width="80" align="center" headerAlign="center" renderer="onTypesRenderer">用户类型</div>
                <div field="status" width="60" align="center" headerAlign="center" renderer="onStatusRenderer">状态</div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    mini.parse();

    var tree = mini.get("tree1");
    $(function () {
        $.ajax({
            url: "/organization/getAll",
            async: true,
            type: "get",
            dataType: "json",
            success: function (list) {
                tree.loadList(list,"id","pid");
            }
        });
    })
    var grid = mini.get("grid1");
    grid.load();


    tree.on("nodeselect", function (e) {
        if (e.isLeaf) {
            grid.load({organizationId: e.node.id });
        } /*else {
            grid.setData([]);
            grid.setTotalCount(0);
        }*/
    });
    //查看全部
    function showData() {
        grid.load();
    }

    //条件查询
    function search() {
        var key = mini.get("key").getValue();
        grid.load({name:key});
    }

    //回车键搜索
    function onKeyEnter(e) {
        search();
    }

    function clearFilter() {
        filter.clearAllFilter();
    }
    //过滤处理
    var filter = new HeaderFilter(grid, {
        columns: [
            { name: 'name' },
            { name: "age" },
            { name: "organizationName" },
            {name:"sex"}
        ],
        callback: function (column, filtered) {
        }
    });

    //处理性别表示
    var Genders = [{ id: 0, text: '男' }, { id: 1, text: '女'}];
    function onGenderRenderer(e) {
        for (var i = 0, l = Genders.length; i < l; i++) {
            var g = Genders[i];
            if (g.id == e.value) return g.text;
        }
        return "";
    }
    //处理用户类型表示
    var Types = [
            { id: 0, text: "<a style='color:#ffffff;background: #ed5565;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>管理员</a>" },
            { id: 1, text: "<a style='color:#ffffff;background: #8bc34a;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>用户</a>"}
    ];
    function onTypesRenderer(e) {
        for (var i = 0, l = Types.length; i < l; i++) {
            var g = Types[i];
            if (g.id == e.value) return g.text;
        }
        return "";
    }
    //处理状态表示
    var Status = [
            { id: 0, text: "<a style='color:#ffffff;background: #795548;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>启动</a>" },
            { id: 1, text: "<a style='color:#ffffff;background: #2196f3;padding: 4px 11px;font-weight: 600;border-radius: 3px;'>停用</a>"}
    ];
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
            url: "/user/addUI",
            title: "新增用户",
            width: 600,
            height: 410,
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

    function editRow() {
        var rows = grid.getSelecteds();
        if(rows.length==1){
            mini.open({
                url: "/user/addUI",
                title: "编辑用户", width:600, height: 410,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id:rows[0].id };
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }else{
            mini.alert("请选择一条数据编辑");
        }
    }

    function delRow() {
        var rows = grid.getSelecteds();
        if (rows.length>0) {
            mini.confirm("确定删除该用户？","删除用户",
                    function (action) {
                        if (action == "ok") {
                            grid.loading("删除中，请稍后......");
                            $.ajax({
                                url: "/user/remove?id=" + rows[0].id,
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
        }else{
            mini.alert("请选择删除用户");
        }
    }

</script>
</body>
</html>