
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <script src="${request.contextPath}/static/scripts/boot.js" type="text/javascript"></script>
    <script src="${request.contextPath}/static/scripts/thirdparty/webuploader/webuploader.min.js" type="text/javascript"></script>
    <link href="${request.contextPath}/static/scripts/thirdparty/webuploader/webuploader.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="height: 100%">
    <div class="mini-datagrid" id="grid1" style="width: 100%; height: 420px;" showpager="false" emptyText="尚未上传"
         alwaysShowEmptyText="true" showEmptyText="true" url="/file/findAll" showheader="true" idField="id"
         showtoolbar="true" title="<font style='font-weight:bold'>文件上传</font>">

        <div property="toolbar" style="height: 30px; line-height: 27px">
            <a class="mini-button" iconcls="icon-add" id="beginBtn" style="margin-left: 3px">选择文件上传</a>
            <a class="mini-button" iconcls="icon-remove" id="deleteBtn">删除选中文件</a>
        </div>
        <div property="columns">
            <div field="fileName"   width="150" align="center" headerAlign="center">文件名</div>
            <div field="fileType"   width="80"  align="center" headerAlign="center">类型</div>
            <div field="fileSize"   width="80"  align="center" headerAlign="center" renderer="bytesToSize">大小</div>
            <div field="uploadUser" width="80"  align="center" headerAlign="center">上传用户</div>
            <div field="uploadTime" width="80"  align="center" headerAlign="center" dateFormat="yyyy-MM-dd" renderer="parseDate">上传时间</div>
            <div field="fileState"  width="80"  align="center" headerAlign="center">状态</div>
        </div>
    </div>

    <div id="editWindow" class="mini-window"  style="width:410px;height: 150px;"
         showModal="true" allowResize="true" allowDrag="true" title="文件上传">

        <form id="form1" method="post" enctype="multipart/form-data" action="/file/upload">
            <div style="margin: 5px;">
                选择文件:<input type="file" id="file" name="file" width="120px">
                <br>
                <input type="submit" value="上传" style="width: 60px;padding: 3px;position: absolute;top: 41%;left: 64%;cursor: pointer">
            </div>
        </form>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get("grid1");
    grid.load();
    var editWindow = mini.get("editWindow");

    mini.get("beginBtn").on("click",function (e) {
        editWindow.show();
    })

    mini.get("deleteBtn").on("click", function (e) {debugger
        var row = grid.getSelected();
        //var record = e.record;
        //var id = record.id;
        if (!row) {
            mini.alert("请选择行！");
        } else {
            //grid.removeRow(row);
            mini.confirm("确定删除该文件？","删除文件",
                    function (action) {
                        if (action == "ok") {
                            grid.loading("删除中，请稍后......");
                            $.ajax({
                                url: "/file/remove?id=" + row.id,
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
    })

    function bytesToSize(e) {
        var bytes=e.value;
        if (bytes === 0) return '0 B';
        var k = 1024,
                sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
                i = Math.floor(Math.log(bytes) / Math.log(k));

        return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
    }

    function parseDate(e) {
        return parseDateToStr(e.value);
    }

    function parseDateToStr(data)
    {
        var now = new Date(data);

        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日

        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();           //秒

        var clock = year + "-";

        if(month < 10)
            clock += "0";

        clock += month + "-";

        if(day < 10)
            clock += "0";

        clock += day + " ";

        if(hh < 10)
            clock += "0";

        clock += hh + ":";
        if (mm < 10) clock += '0';
        clock += mm + ":";

        if (ss < 10) clock += '0';
        clock += ss;
        return(clock);
    }
</script>

