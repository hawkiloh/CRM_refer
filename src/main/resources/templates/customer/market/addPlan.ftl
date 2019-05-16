
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增或编辑开发计划</title>
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
            overflow:scroll;
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
                <#--<td style="width:70px;">客户名称：</td>
                <td style="width:150px;">
                    <input name="custName" class="mini-textbox" required="true"  emptyText="请输入客户名称"/>
                </td>-->
                <td style="width:70px;">订单id：</td>
                <td style="width:150px;">
                    <input name="chanceId" class="mini-textbox" required="true" width="300px" emptyText="请输入客户订单id"/>
                </td>
            </tr>
            <#--<tr>
                <td >联系人：</td>
                <td >
                    <input name="contact" class="mini-textbox" required="true" emptyText="请输入联系人" />
                </td>
                <td >联系人电话：</td>
                <td >
                    <input name="contactTel" class="mini-textbox"  required="true" emptyText="请输入联系人电话"/>
                </td>
            </tr>-->
            <tr>
                <td >计划内容：</td>
                <td colspan="3">
                    <input id="toDo" name="toDo" class="mini-textarea" width="300px" emptyText="请输入计划内容"/>
                </td>

            </tr>
            <tr>
                <td >计划实施时间：</td>
                <td>
                    <input id="date" name="date" class="mini-datepicker" required="true" emptyText="请选择计划实施时间"
                           format="yyyy-MM-dd HH:mm" showTime="true"/>
                </td>
                <td >结果：</td>
                <td >
                    <input id="result" name="result" class="mini-textbox" value="开发中" required="true" emptyText="请选择结果状态" readonly />
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
    mini.parse();

    var form = new mini.Form("form1");

    function SaveData() {
        var o = form.getData();
        form.validate();
        if (form.isValid() == false) return;
        $.ajax({
            url: "/market/savePlan",
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
        if (data.action === "new") {
            //跨页面传递的数据对象，克隆后才可以安全使用
            data = mini.clone(data);
            $.ajax({
                url: "/market/findPlanById?id=" + data.id,
                cache: false,
                success: function (text) {
                    let d=text.data;
                    d.createDate=new Date(d.createDate);
                    var o = mini.decode(text.data,true);
                    form.setData(o);
                    form.setChanged(false);
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
