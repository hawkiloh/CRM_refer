
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>权限分配树</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />

    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>

    <style>
        .function-item
        {
            margin-left:5px;
            margin-right:5px;
        }
        .function-item input
        {
            vertical-align:bottom;
        }
    </style>

</head>
<body>
<input style="padding: 6px;margin: 5px;cursor: pointer" type="button" value="保存权限" onclick="getFuns()" />
<div id="treegrid1" class="mini-treegrid" style="width:700px;height:auto;"
     url="/menu/findRightList"
     treeColumn="name" idField="id" parentField="pid" resultAsTree="false"
     allowResize="true" expandOnLoad="true" showTreeIcon="true"
     allowSelect="false" allowCellSelect="false" enableHotTrack="false"
     ondrawcell="ondrawcell" showCheckBox="true"
>
    <div property="columns">
        <div type="indexcolumn"></div>
        <div name="name" field="name" width="180" >模块名称</div>
        <div field="functions" width="90%">权限</div>
    </div>
</div>


</body>
</html>
<script>
    var roleId;
    mini.parse();
    var tree = mini.get("treegrid1");

    function SetRight(data) {debugger
        roleId=data.id;
        tree.load({id:roleId});
        getMenuIds(data.id);
    }
    
    function getMenuIds(rId) {
        var url='/menu/getSelectedMenuIds?roleId='+rId;
        $.ajax({
            url: url,
            async:false,
            success: function (text) {
                tree.setValue(text);
            },
            error: function () {
            }
        });
    }

    function ondrawcell(e) {
        var tree = e.sender,
                record = e.record,
                column = e.column,
                field = e.field,
                id = record[tree.getIdField()],
                funs = record.functions;

        function createCheckboxs(funs) {
            if (!funs || funs.length<1) return "";
            var html = "";
            for (var i = 0, l = funs.length; i < l; i++) {
                var fn = funs[i];
                var clickFn = 'checkFunc(\'' + id + '\',\'' + fn.action + '\', this.checked)';
                var checked = fn.checked ? 'checked' : '';
                html += '<label class="function-item"><input onclick="' + clickFn + '" ' + checked + ' type="checkbox" name="'
                        + fn.action + '" hideFocus/>' + fn.name + '</label>';
            }
            return html;
        }

        if (field == 'functions') {
            e.cellHtml = createCheckboxs(funs);
        }
    }

    function getFuns() {
        var data = tree.getData();
        var menu=tree.getValue();
        var ids=[];
        for(var i=0;i<data.length;i++){
            var func=data[i].functions;
            if(func && func.length>0){
                for(var m=0;m<func.length;m++){
                    if(func[m].checked){
                        ids.push(func[m].id);
                    }
                }
            }
            var children=data[i].children;
            if(children && children.length>0){
                for(var j=0;j<children.length;j++){
                    var func1=children[j].functions;
                    if(func1 && func1.length>0){
                       for(var k=0;k<func1.length;k++){
                           if(func1[k].checked){
                               ids.push(func1[k].id);
                           }
                       }
                   }
               }
           }
        }
        if(menu.length>0){
            saveRightList(menu+","+ids);
        }else{
            saveRightList(ids);
        }
    }
    function saveRightList(ids) {
        $.ajax({
            url: "/role/saveRightList?ids=" + ids+"&roleId="+roleId,
            success: function (text) {
                if(text.state==0){debugger
                    tree.reload();
                    //mini.alert("删除成功");
                    CloseWindow("save");
                }else{
                    mini.alert("授权失败，"+text.message);
                }
            },
            error: function () {
            }
        });
    }

    function checkFunc(id, action, checked) {
        var record = tree.getRecord(id);
        if(!record) return;
        var funs = record.functions;
        if (!funs ||funs.length<1) return;
        function getAction(action) {
            for (var i = 0, l = funs.length; i < l; i++) {
                var o = funs[i];
                if (o.action == action) return o;
            }
        }
        var obj = getAction(action);
        if (!obj) return;
        obj.checked = checked;
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
