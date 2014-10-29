<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>MySWORD Database List Page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="MySWORD Database List Page">
		<link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
        <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
        <script type="text/javascript" src="../js/main.js"></script>
        <script type="text/javascript" src="../js/jquery/jquery.js"></script>
        <script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
        <script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
        <style type="text/css">
            img {
                width: 17px;
                height: 17px;
                cursor: pointer;
            }

            #windowTable {
                margin-left: 20px;
            }

            #windowTable tr {
                line-height: 30px;
            }

            #windowTable th {
                text-align: right;
                padding-right: 4px;
            }

            .mandatory:before {
                color: red;
                content: "*";
            }

            #windowTable th:after {
                content: ":";
            }
        </style>
		<script type="text/javascript">
            var theme = "${mysword_theme}";
            var tableSelectionIdex = 0;
	        $(document).ready(function () {
                $('#window').jqxWindow({width: 500, height: 450, isModal: true, autoOpen: false, theme: theme});
                $('#window').on('close', function(event){ clearWindowData(); });
                $('#databaseName').jqxInput({ placeHolder: "Please input the database name.", width: 300, height: 25, disabled:true, theme: theme});
                $('#databaseType').jqxDropDownList({ source: ['SQL Server', 'Oracle', 'MySQL', 'SQLite'], width: 150, height: 25, dropDownHeight: 105, theme: theme});
                $('#driverClass').jqxDropDownList({ source: ['com.microsoft.sqlserver.jdbc.SQLServerDriver', 'oracle.jdbc.driver.OracleDriver', 'com.mysql.jdbc.Driver', 'org.sqlite.JDBC'], width: 150,
                    height:25, dropDownHeight:105, dropDownWidth:320, theme:theme});
                $('#databaseURL').jqxInput({ width: 300, height: 25, theme: theme});
                $('#databaseURL').jqxTooltip({ position:'mouse',theme:theme,
                    content:'<table style="text-align:left"><tr><td>SQL Server:</td><td>jdbc:sqlserver://&lt;hostname&gt;:&lt;port&gt;;databaseName=&lt;schema&gt;</td></tr><tr><td>Oracle:</td><td></td></tr><tr><td>MySQL:</td><td>jdbc:mysql://&lt;hostname&gt;:&lt;port&gt;/&lt;schema&gt;</td></tr><tr><td>SQLite:</td><td>jdbc:sqlite:&lt;URI&gt;</td></tr></table>'
                });
                $('#username').jqxInput({width: 300, height: 25, theme: theme});
                $('#password').jqxInput({width: 300, height: 25, theme: theme});
                $('#timeout').jqxInput({width: 300, height: 25, theme: theme});
                $('#maxThread').jqxInput({width: 300, height: 25, theme: theme});
                $('#description').jqxInput({ placeHolder: "Please input the description.", width: 300, height: 90, theme: theme});
                $('#save').jqxButton({ width: 70, height: 27, theme: theme}).on('click', function (event) {
                    var valid = $('#windowTable').jqxValidator('validate');
                    if (!valid) {
                        return;
                    }
                    var url;
                    if (this.value == "New") {
                        url = "Database!addDatabase";
                    } else if (this.value == "Save") {
                        url = "Database!updateDatabase";
                    }
                    $.ajax({async: false, type: "post", url: url, dataType: "json",
                        data: {
                            'databaseList[0].databaseName': $('#databaseName').jqxInput('val'),
                            'databaseList[0].databaseType': $('#databaseType').jqxDropDownList('val'),
                            'databaseList[0].driverClass': $('#driverClass').jqxDropDownList('val'),
                            'databaseList[0].databaseURL': $('#databaseURL').jqxInput('val'),
                            'databaseList[0].username': $('#username').jqxInput('val'),
                            'databaseList[0].password': $('#password').jqxInput('val'),
                            'databaseList[0].timeout': $('#timeout').jqxInput('val'),
                            'databaseList[0].maxThread': $('#maxThread').jqxInput('val'),
                            'databaseList[0].description': $('#description').val()
                        },
                        complete: function (response, returnType) {
                            if (response.responseJSON == null || response.responseJSON == "") {
                                post("/${mysword_path}/error", "_self", {error: response.responseText});
                            } else {
                                if (this.url == "Database!addDatabase") {
                                    $("#databaseTable").jqxDataTable('addRow', response.responseJSON.databaseList[0].databaseName, response.responseJSON.databaseList[0]);
                                    $('#window').jqxWindow('close');
                                } else if (this.url == "Database!updateDatabase") {
                                    $("#databaseTable").jqxDataTable('updateRow', tableSelectionIdex, response.responseJSON.databaseList[0]);
                                    $('#window').jqxWindow('close');
                                }
                            }
                            clearWindowData();
                            resetWindowHeight();
                        }
                    })
                });
                $('#cancel').jqxButton({ width: 70, height: 27, theme: theme}).on('click', function () {
                    clearWindowData();
                    $('#window').jqxWindow('close');
                });
                $('#new').jqxButton({ width: 70, height: 27, theme: theme}).on('click', function () {
                    $('#databaseName').jqxInput('disabled', false);
                    $('#save').jqxButton('val', 'New');
                    $('#window').jqxWindow('title', 'Null');
                    $('#window').jqxWindow('open');
                });
                $('#windowTable').jqxValidator({
                    rules: [
                        { input: '#databaseName', message: 'Database name is required!', action: 'keyup, blur', rule: 'required' },
                        { input: '#driverClass', message: 'Driver class is required!', action: 'keyup, blur', rule: function(){
                            if($('#driverClass').val() == "") {
                                return false;
                            } else {
                                return true;
                            }
                        }},
                        { input: '#databaseURL', message: 'Database URL is required!', action: 'keyup, blur', rule: 'required' }
                    ]
                });
                var source = {
                    url: 'Database!listDatabases',
                    async: false,
                    datatype: "json",
                    datafields: [
                        { name: 'databaseName', type: "string" },
                        { name: 'databaseType', type: "string" },
                        { name: 'driverClass', type: "string" },
                        { name: 'databaseURL', type: "string" },
                        { name: 'username', type: "string" },
                        { name: 'password', type: "string" },
                        { name: 'description', type: "string" },
                        { name: 'timeout', type: "number" },
                        { name: 'maxThread', type: "number" },
                        { name: 'currentThread', type: "number" },
                        { name: 'lasteditby', type: "string" },
                        { name: 'lasteditdt', type: "string" }
                    ],
                    id: 'databaseName'
                }
                var dataAdapter = new $.jqx.dataAdapter(source, {
                    loadError: function (xhr, status, error) {
                        post("/${mysword_path}/error", "_self", {error: xhr.responseText});
                    }
                });
                $("#databaseTable").jqxDataTable({ source: dataAdapter, theme: theme, altrows: true, enableBrowserSelection: true, columnsResize: true, sortable: true, selectionMode: 'customer',
                    columns: [
                        { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                            return row + 1;
                        }},
                        { text: 'Action', dataField: 'action', width: 50, cellsRenderer: function (row, column, value, rowData) {
                            return "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(" + row + ")' />&nbsp;"
                                    + "<img src='../image/operations/pencil.png' title='Edit this item.' onclick='editItem(" + row + ")' />";
                        }},
                        { text: 'Database Name', dataField: 'databaseName', width: 180 },
                        { text: 'Database URL', dataField: 'databaseURL', hidden:true, width: 200 },
                        { text: 'Username', dataField: 'username', hidden:true, width: 100 },
                        { text: 'Password', dataField: 'password', hidden:true, width: 100 },
                        { text: 'Timeout', dataField: 'timeout', width: 80 },
                        { text: 'Max', dataField: 'maxThread', width: 50 },
                        { text: 'Used', dataField: 'currentThread', width: 50 },
                        { text: 'Description', dataField: 'description', width: 300 },
                        { text: 'Last Edit', dataField: 'lasteditby', width: 110 },
                        { text: 'Edit Date', dataField: 'lasteditdt', width: 170 }
                    ]
                });
                resetWindowHeight();
	        });
            function deleteItem(rowIndex) {
                $("#databaseTable").jqxDataTable('clearSelection');
                tableSelectionIdex = rowIndex;
                $("#databaseTable").jqxDataTable('selectRow', rowIndex);
                var data = $("#databaseTable").jqxDataTable('getSelection');
                if(!confirm("Do you really want to delete " + data[0].databaseName)) {
                    return;
                }
                $.ajax({async: false, type: "post", url: "Database!deleteDatabase", dataType: "json",
                    data: {'databaseList[0].databaseName': data[0].databaseName},
                    complete: function (response, returnType) {
                        if(response.responseJSON == null || response.responseJSON == "") {
                            post("/${mysword_path}/error", "_self", {error: response.responseText});
                        } else {
                            $("#databaseTable").jqxDataTable('deleteRow', rowIndex);
                        }
                    }
                })
            }
            function editItem(rowIndex) {
                $('#databaseName').jqxInput('disabled', true);
                tableSelectionIdex = rowIndex;
                $("#databaseTable").jqxDataTable('clearSelection');
                $("#databaseTable").jqxDataTable('selectRow', rowIndex);
                var data = $("#databaseTable").jqxDataTable('getSelection');
                $('#save').jqxButton('val', 'Save');
                $('#window').jqxWindow({ title: data[0].databaseName });
                $('#databaseName').val(data[0].databaseName);
                $('#databaseType').val(data[0].databaseType);
                $('#driverClass').val(data[0].driverClass);
                $('#databaseURL').val(data[0].databaseURL);
                $('#username').val(data[0].username);
                $('#password').val(data[0].password);
                $('#timeout').val(data[0].timeout);
                $('#maxThread').val(data[0].maxThread);
                $('#currentThread').val(data[0].currentThread);
                $('#description').val(data[0].description);
                $('#window').jqxWindow('open');
            }
            function clearWindowData() {
                $('#databaseName').val('');
                $('#databaseType').jqxDropDownList('selectIndex', -1);
                $('#driverClass').jqxDropDownList('selectIndex', -1);
                $('#databaseURL').val('');
                $('#username').val('');
                $('#password').val('');
                $('#timeout').val('');
                $('#maxThread').val('');
                $('#currentThread').val('');
                $('#description').val('');
            }
//            function resetWindowHeight(){
//                if ($('body').height() < 600) {
//                    window.frameElement.height = 600;
//                } else {
//                    window.frameElement.height = $('body').height() + 20;
//                }
//            }
	    </script>
	</head>
	<body>
        <button id="new">New</button>
        <br/>
        <br/>
        <div id="databaseTable"></div>
        <div id="window">
            <div></div>
            <div>
                <table id="windowTable">
                    <tr>
                        <th class="mandatory">Database Name</th>
                        <td><input id="databaseName" /></td>
                    </tr>
                    <tr>
                        <th>Database Type</th>
                        <td><div id="databaseType" /></td>
                    </tr>
                    <tr>
                        <th class="mandatory">Driver Class</th>
                        <td><div id="driverClass" /></td>
                    </tr>
                    <tr>
                        <th class="mandatory">Database URL</th>
                        <td><input id="databaseURL" /></td>
                    </tr>
                    <tr>
                        <th>Username</th>
                        <td><input id="username" /></td>
                    </tr>
                    <tr>
                        <th>Password</th>
                        <td><input id="password" /></td>
                    </tr>
                    <tr>
                        <th>Timeout</th>
                        <td><input id="timeout" /></td>
                    </tr>
                    <tr>
                        <th>Max. Thread</th>
                        <td><input id="maxThread" /></td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td><textarea id="description" wrap="off"></textarea></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center"><button id="save">Save</button>&nbsp;&nbsp;&nbsp;<button id="cancel">Cancel</button></td>
                    </tr>
                </table>
            </div>
        </div>
	</body>
</html>