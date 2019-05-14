

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增或编辑客户信息</title>
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
                <td style="width:70px;">客户名称：</td>
                <td style="width:150px;">
                    <input name="name" class="mini-textbox" required="true"  emptyText="请输入客户名称"/>
                </td>
                <td >地区：</td>
                <td >
                    <input name="region" class="mini-textbox" required="true"  emptyText="请输入地区" />
                </td>
            </tr>
            <tr>
                <td >联系电话：</td>
                <td >
                <input name="tel" class="mini-textbox"  required="true" emptyText="请输入联系电话"/>
                </td>
                <td >传真：</td>
                <td >
                    <input name="fax" class="mini-textbox" required="true" emptyText="请输入传真" />
                </td>
            </tr>
            <tr>
                <td >客户等级：</td>
                <td >
                    <input id="level" name="level" class="mini-combobox" required="true"
                           valueField="id" textField="name" data="levels"  emptyText="请选择客户等级"/>
                </td>
                <td >客户满意度：</td>
                <td >
                    <input id="satify" name="satify" class="mini-combobox" required="true"
                           valueField="id" textField="name" data="satifies" emptyText="请选择客户满意度"/>
                </td>
            </tr>
            <tr>
                <td >客户经理：</td>
                <td >
                    <input id="managerId" name="managerId" class="mini-combobox" required="true"
                           valueField="id" textField="text" url="/user/findAllManager" emptyText="请选择客户经理"/>
                </td>
                <td >客户信用度：</td>
                <td >
                    <input name="credit" emptyText="请选择客户信用度" class="mini-combobox" required="true"  valueField="id" textField="name" data="credits" />

                </td>

            </tr>
            <tr>
                <td >出生日期：</td>
                <td >
                    <input id="birthday" name="birthday" class="mini-datepicker" format="yyyy-MM-dd" required="true" showTime="false"
                            emptyText="请输入客户出生日期" vtype="date"/>
                </td>
                <td >邮箱：</td>
                <td >
                    <input name="email" emptyText="请输入客户邮箱" class="mini-textbox" required="true" vtype="email" />

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
    var levels=[{id:'大客户',name:'大客户'},{id:'合作伙伴',name:'合作伙伴'},{id:'普通客户',name:'普通客户'},{id:'战略合作伙伴',name:'战略合作伙伴'}];
    var satifies=[{id:'一星',name:'一星'},{id:'二星',name:'二星'},{id:'三星',name:'三星'},{id:'四星',name:'四星'},{id:'五星',name:'五星'}];
    var credits=[{id:'一星',name:'一星'},{id:'二星',name:'二星'},{id:'三星',name:'三星'},{id:'四星',name:'四星'},{id:'五星',name:'五星'}];
  
    mini.parse();

    var form = new mini.Form("form1");
    function SaveData() {
        var o = form.getData();
        form.validate();
        if (form.isValid() == false) {
            // mini.alert("验证失败！");
            return;
        }
        $.ajax({
            url: "/customer/save",
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
                url: "/customer/findById?id=" + data.id,
                cache: false,
                success: function (text) {
                    let d=text.data;
                    d.birthday=new Date(d.birthday);
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
