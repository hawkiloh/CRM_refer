
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增资源</title>
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
                <td style="width:70px;">资源名称：</td>
                <td style="width:150px;">
                    <input name="name" class="mini-textbox" required="true"  emptyText="请输入资源名称"/>
                </td>
                <td style="width:70px;">资源类型：</td>
                <td style="width:150px;">
                    <input name="resourceType" emptyText="请选择资源类型" required="true"  class="mini-combobox" valueField="id" textField="name" data="resourceTypes"/>
                </td>
            </tr>
            <tr>
                <td >资源路径：</td>
                <td >
                    <input name="url" class="mini-textbox"  emptyText="请输入资源路径"/>
                </td>
                <td >打开方式：</td>
                <td >
                    <input name="openMode" emptyText="请选择打开方式" class="mini-combobox" valueField="id" textField="name" data="openModes"/>
                </td>
            </tr>

            <tr>
                <td >排序：</td>
                <td >
                    <input id="seq" name="seq" emptyText="请输入排序" class="mini-spinner" minValue="0" maxValue="200" value="5" required="true"   />
                </td>
                <td >菜单图标：</td>
                <td >
                    <input name="icon" class="mini-textbox" emptyText="请输入菜单图标"/>
                </td>
            </tr>
            <tr>
                <td >状态：</td>
                <td >
                    <input name="status" emptyText="请选择状态" class="mini-combobox" valueField="id" textField="name" data="statuz" required="true" />
                </td>
                <td >是否打开：</td>
                <td >
                    <input name="opened"  class="mini-combobox" valueField="id" textField="name" data="openeds" required="true" />
                </td>
            </tr>
            <tr>
                <td >上级资源：</td>
                <td >
                    <input name="pid" id="pid"  class="mini-treeselect" url="/menu/getMenu" multiSelect="false"
                           textField="text" emptyText="请选择上级资源" valueField="id" parentField="pid" checkRecursive="true"/>
                </td>
                <td ><a class="mini-button" onclick="isClear" style="width:43px;">清空</a></td>
                <td >
                </td>
            </tr>
        </table>
    </div>
    <div style="text-align: center; padding: 6px;position: absolute;bottom: 0px;background: #eee;width: 100%;">
        <a class="mini-button"  onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
        <a class="mini-button"  onclick="onCancel" style="width:60px;">取消</a>
    </div>
</form>
<script type="text/javascript">
    var resourceTypes=[{id:0,name:'菜单'},{id:1,name:'按钮'}];
    var openModes=[{id:'ajax',name:'ajax'},{id:'iframe',name:'iframe'},{id:'',name:'空'}];
    var openeds=[{id:0,name:'打开'},{id:1,name:'关闭'}];
    var statuz=[{id:0,name:'启动'},{id:1,name:'停用'}];
    mini.parse();

    var form = new mini.Form("form1");

    function SaveData() {
        var o = form.getData();
        form.validate();
        if (form.isValid() == false) return;
        $.ajax({
            url: "/menu/save",
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
                url: "/menu/findById?id=" + data.id,
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
    function isClear() {
        var pid = mini.getbyName("pid");
        pid.setValue("");
    }
</script>
</body>
</html>
