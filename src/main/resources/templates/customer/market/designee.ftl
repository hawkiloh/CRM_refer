

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>指派人选取</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
</head>
<body>
<input type="button" value="保存" onclick="saveData()" style="width:55px;"/>
<table >
    <tr>
        <td >
            <div id="listbox1" class="mini-listbox" style="width:150px;height:150px;"
                 textField="text" valueField="id" showCheckBox="true" multiSelect="false"
                 url="/user/findSalesAll"  value="">

            </div>
        </td>
        <td style="width:120px;text-align:center;">
            <input type="button" value=">" onclick="add()" style="width:40px;"/><br />
            <input type="button" value=">>" onclick="addAll()" style="width:40px;"/><br />
            <input type="button" value="&lt;&lt;" onclick="removeAll()" style="width:40px;"/><br />
            <input type="button" value="&lt;" onclick="removes()" style="width:40px;"/><br />

        </td>
        <td>
            <div id="listbox2" class="mini-listbox" style="width:250px;height:150px;"
                 showCheckBox="true" multiSelect="true"
            >
                <div property="columns">
                    <div header="ID" field="id"></div>
                    <div header="指派人" field="text"></div>
                </div>
            </div>
        </td>
        <td style="width:50px;text-align:center;vertical-align:bottom">
            <input type="button" value="上移" onclick="upItem()" style="width:55px;"/>
            <input type="button" value="下移" onclick="downItem()" style="width:55px;"/>

        </td>
    </tr>

</table>

<script type="text/javascript">
    var chanceId;
    mini.parse();
    var listbox1 = mini.get("listbox1");
    var listbox2 = mini.get("listbox2");

    function add() {
       var items = listbox1.getSelecteds();
        listbox1.removeItems(items);
        listbox2.addItems(items);
    }
    function addAll() {
        var items = listbox1.getData();
        listbox1.removeItems(items);
        listbox2.addItems(items);
    }
    function removes() {
        var items = listbox2.getSelecteds();
        listbox2.removeItems(items);
        listbox1.addItems(items);
    }
    function removeAll() {
        var items = listbox2.getData();
        listbox2.removeItems(items);
        listbox1.addItems(items);
    }
    function upItem() {
        var items = listbox2.getSelecteds();
        for (var i = 0, l = items.length; i < l; i++) {
            var item = items[i];
            var index = listbox2.indexOf(item);
            listbox2.moveItem(item, index-1);
        }
    }
    function downItem() {
        var items = listbox2.getSelecteds();
        for (var i = items.length-1; i >=0; i--) {
            var item = items[i];
            var index = listbox2.indexOf(item);
            listbox2.moveItem(item, index + 1);
        }
    }
    function setData(e) {
        chanceId=e.id;
        $.ajax({
            url: "/market/findUserId",
            async:false,
            data:{id:chanceId},
            success: function (text) {
                mini.get("listbox1").setValue(text.data);
                add();
            }
        });
    }
    /*$(function () {
        $.ajax({
            url: "/market/findUserId",
            async:false,
            data:{id:chanceId},
            success: function (text) {
                mini.get("listbox1").setValue(text);
                add();
            }
        });
    })*/
    function saveData() {
        var data = listbox2.getData();
        var json = mini.encode(data);
        if(data.length==1) {
            $.ajax({
                url: "/market/save",
                data:{designee:data[0].text,id:chanceId},
                success: function (text) {
                    CloseWindow("save");
                },
                error: function () {
                }
            });
        }else{
            mini.alert("请选择一位指派人");
        }
    }

    function CloseWindow(action) {
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
</script>

</body>
</html>
