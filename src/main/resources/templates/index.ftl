
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>客户管理系统</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="/static/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="/static/scripts/miniui/miniui.js" type="text/javascript"></script>
    <link href="/static/scripts/miniui/themes/icons.css" rel="stylesheet" type="text/css"/>
    <link href="/static/scripts/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css"/>
    <link href="/static/scripts/miniui/themes/jqueryui-smoothness/skin.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/static/js/cookie_util.js"></script>
    <style type="text/css">

        body{
            margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
        }
        .header
        {
            background:url(/static/img/header.gif) repeat-x 0 -1px;
        }

    </style>
</head>
<body>

<!--Layout-->
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div class="header" region="north" height="76" showSplit="false" showHeader="false">
        <h1 style="margin:0;padding:21px;cursor:default;font-weight: normal"><img src="/static/img/logo2.png" style="width: 48px;height: 23px;margin-right: 2px;"/>客户管理系统</h1>
        <div style="position:absolute;top:18px;right:10px;">
            <#--<a class="mini-button mini-button-iconTop" iconCls="icon-user" href='#'plain="true" showheader="true" >${user.roles}</a>-->
            <a class="mini-button mini-button-iconTop" iconCls="icon-user" href='javascript:showUserInfo()'plain="true" showheader="true" id="user" title="用户登录">${user.name?if_exists}</a>
            <a class="mini-button mini-button-iconTop" iconCls="icon-tip" href='javascript:toIndex()'  plain="true" title="返回首页" >首页</a>
            <a class="mini-button mini-button-iconTop" iconCls="icon-lock" href='javascript:setPWD()'  plain="true" title="设置密码">设置</a>
            <a class="mini-button mini-button-iconTop" iconCls="icon-close" href='javascript:toLogin()'  plain="true" title="退出登陆">退出</a>
        </div>
    </div>
    <div title="south" region="south" showSplit="false" showHeader="false" height="30" >
    </div>
    <div title="center" region="center" bodyStyle="overflow:hidden;">
        <!--Splitter-->
        <div class="mini-splitter" style="width:100%;height:100%;" borderStyle="border:0;">
            <div size="180" maxSize="250" minSize="100" showCollapseButton="true">
                <!--OutlookTree-->
                <div id="leftTree" class="mini-outlooktree"  onnodeselect="onNodeSelect" textField="text" idField="id" parentField="pid" showTreeIcon="true">

                </div>
            </div>
            <div showCollapseButton="false">
                <!--Tabs-->
                <div id="mainTabs" class="mini-tabs bg-toolbar" activeIndex="0" style="width:100%;height:100%;" bodyStyle="border:0;background:white;" contextMenu="#tabsMenu" onactivechanged="onTabsActiveChanged" plain="false">
                    <div title="首页" url="/toWelcome" name="first" title="Tab1" >
                    </div>
                </div>
                <ul id="tabsMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen" style="width: 100px;">
                    <li onclick="closeTab">关闭当前页</li>
                    <li onclick="closeAllButFirst">关闭其他</li>
                    <li onclick="closeAll">关闭所有</li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    mini.parse();
    var tree = mini.get("leftTree");
    var tabs = mini.get("mainTabs");

    function showTab(node) {
        var id = "tab$" + node.id;
        var tab = tabs.getTab(id);
        if (!tab) {
            tab = {};
            tab._nodeid = node.id;
            tab.name = id;
            tab.title = node.text;
            tab.showCloseButton = true;

            //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
            //tab.url = mini_JSPath + "../../docs/api/" + node.id + ".html";
            tab.url =node.url;
            tabs.addTab(tab);
        }
        tabs.activeTab(tab);
    }
    var currentTab = null;

    function onBeforeOpen(e) {
        currentTab = tabs.getTabByEvent(e.htmlEvent);
        if (!currentTab) {
            e.cancel = true;
        }
    }

    ///////////////////////////
    function closeTab() {
        if(currentTab.name=="first"){
            return;
        }
        tabs.removeTab(currentTab);
    }
    /*function closeAllBut() {
        tabs.removeAll(currentTab);
    }*/
    function closeAll() {
        var but=[];
        but.push(tabs.getTab("first"));
        tabs.removeAll(but);
    }
    function closeAllButFirst() {
        var but = [currentTab];
        but.push(tabs.getTab("first"));
        tabs.removeAll(but);
    }

    function onNodeSelect(e) {

        var node = e.node;
        var isLeaf = e.isLeaf;

        if (isLeaf) {
            showTab(node);
        }
    }

    function onClick(e) {
        var text = this.getText();
        alert(text);
    }
    function onQuickClick(e) {
        tree.expandPath("datagrid");
        tree.selectNode("datagrid");
    }

    function onTabsActiveChanged(e) {
        var tabs = e.sender;
        var tab = tabs.getActiveTab();
        if (tab && tab._nodeid) {

            var node = tree.getNode(tab._nodeid);
            if (node && !tree.isSelectedNode(node)) {
                tree.selectNode(node);
            }
        }
    }

    $(function () {
        $.ajax({
            url: "/menu/getMenu",
            async: true,
            type: "get",
            dataType: "json",
            success: function (list) {
               tree.loadList(list,"id","pid");
            }
        });

    })

    function toLogin() {
        window.location.href='/toLogin';
    }
    function toIndex() {
        window.location.href='/toIndex';
    }
    function setPWD() {
        mini.open({
            targetWindow: window,
            url: "/setPWD",
            title: "设置密码",
            width: 300,
            height: 240,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action: "edit",id:getCookie("userId")};
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                grid.reload();
            }
        });
    }

    function showUserInfo() {
        mini.open({
            url: "/user/addUI",
            title: "查看用户", width:600, height: 410,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = { action: "edit", id:getCookie("userId") };
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                grid.reload();
            }
        });
    }
    /*$(function () {
        $('#user').html(getCookie("loginName"));
    })*/
</script>
</html>
