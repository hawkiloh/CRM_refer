
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增或编辑用户</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <style type="text/css">
        html, body
        {
            font-size:12px;
            padding:0;
            margin:0;
            border:0;
            height:100%;
            overflow:hidden;
        }
        table tr{
            height: 38px;
        }
        .mini-listbox-items tr{
            height:28px;
        }
    </style>
</head>
<body>

<form id="form1" method="post">
    <input name="id" class="mini-hidden" />
    <div style="padding-left:11px;padding-bottom:5px;">
        <table style="table-layout:fixed;">
            <tr>
                <td style="width:70px;">登录名</td>
                <td style="width:150px;">
                    <input name="loginName" class="mini-textbox" required="true"  emptyText="请输入登录名"/>
                </td>
                <td style="width:70px;">姓名</td>
                <td style="width:150px;">
                    <input name="name" class="mini-textbox" required="true"  emptyText="请输入姓名"/>
                </td>
            </tr>
            <tr>
                <td >密码</td>
                <td >
                    <input name="password" class="mini-textbox" emptyText="请输入密码" />
                </td>
                <td >性别</td>
                <td >
                    <input name="sex" emptyText="请选择性别" class="mini-combobox" valueField="id" textField="name" data="sex" required="true" />
                </td>
            </tr>
            <tr>
                <td >年龄</td>
                <td >
                    <input name="age" emptyText="请输入年龄" class="mini-textbox" minValue="0" maxValue="100" value="25" required="true"   />
                </td>
                <td >用户类型</td>
                <td >
                    <input name="userType" emptyText="请选择用户类型" class="mini-combobox" valueField="id" textField="name" data="type" required="true"   />
                </td>
            </tr>
            <tr>
                <td >部门</td>
                <td >
                    <input id="organizationId" name="organizationId" class="mini-treeselect" url="/organization/getAll" multiSelect="false" showClose="true" oncloseclick="onCloseClick"
                           textField="name" valueField="id" parentField="pid" checkRecursive="true" emptyText="请选择部门"/>
                </td>
                <td >角色</td>
                <td >
                    <input name="roleIds" emptyText="请选择角色" class="mini-combobox" url="/role/getAll" textField="name" valueField="id" showNullItem="true" multiSelect="true"/>
                </td>
            </tr>
            <tr>
                <td >电话</td>
                <td>
                    <input name="phone"  emptyText="请输入电话" class="mini-textbox" vtype="rangeLength:11，11" required="true"/>
                </td>
                <td >状态</td>
                <td >
                    <input name="status" emptyText="请选择状态" class="mini-combobox" valueField="id" textField="name" data="statu" required="true"   />
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <span style="color:red">注：密码不填写则默认不修改</span>
                </td>
            </tr>
        </table>
    </div>
    <div style="text-align: center; padding: 6px;position: absolute;bottom: 0px;background: #eee;width: 100%;">
        <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
        <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
    </div>
</form>
<script type="text/javascript">
    var sex=[{id:0,name:'男'},{id:1,name:'女'}];
    var type=[{id:0,name:'管理员'},{id:1,name:'用户'}];
    var statu=[{id:0,name:'启动'},{id:1,name:'停用'}];
    mini.parse();

    var form = new mini.Form("form1");

    function SaveData() {
        var o = form.getData();
        form.validate();
        if (form.isValid() == false) return;
        $.ajax({
            url: "/user/save",
            type: 'post',
            data: o,
            cache: false,
            success: function (text) {
                if(text.state==0){
                    mini.alert("保存成功");
                    CloseWindow("save");
                }else{
                    mini.alert("保存失败",text.message);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                mini.alert(jqXHR.responseText);
                CloseWindow();
            }
        });
    }

    //标准方法接口定义
    function SetData(data) {
        if (data.action == "edit") {
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = mini.clone(data);
            $.ajax({
                url: "/user/findById?id=" + data.id,
                cache: false,
                success: function (text) {
                    var o = mini.decode(text.data);
                    form.setData(o);
                    form.setChanged(false);

                    /*onDeptChanged();
                     mini.getbyName("position").setValue(o.position);*/
                }
            });
        }
    }

    function GetData() {
        var o = form.getData();
        return o;
    }
    //
    function CloseWindow(action) {
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
    //
    function onOk(e) {
        SaveData();
    }
    //
    function onCancel(e) {
        CloseWindow("cancel");
    }
    //
    function onCloseClick() {
        var organizationId = mini.getbyName("organizationId");
        organizationId.setValue("");
    }

</script>
</body>
</html>
