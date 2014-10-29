<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>MySWORD Profile List Page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="MySWORD Profile List Page">
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
                $('#window').jqxWindow({width: 500, height: 600, isModal: true, autoOpen: false, theme: theme});
                $('#window').on('close', function(event){ clearWindowData(); });
                $('#hostname').jqxInput({width: 300, height: 25, theme: theme});
                $('#username').jqxInput({width: 300, height: 25, disabled: true, theme: theme});
                $('#sims').jqxInput({width: 300, height: 25, theme: theme});
                $('#sims_password').jqxPasswordInput({width: 300, height: 25, theme: theme});
                $('#sims_password1').jqxPasswordInput({width: 300, height: 25, theme: theme});
                $("#region").jqxDropDownList({ source: ['SSC', 'APAC', 'CORPORATE', 'US', 'EAST'], width: 150, height: 25, dropDownHeight: 130, theme: theme});
                $('#email').jqxInput({width: 300, height: 25, theme: theme});
                $('#extension').jqxInput({width: 300, height: 25, theme: theme});
                $('#userIndex').jqxInput({width: 300, height: 25, theme: theme});
                $("#department").jqxDropDownList({ source: ['Development','Support','Coordination','Framework','Operations'], width: 150, height: 25, dropDownHeight: 130, theme: theme});
                $("#theme").jqxDropDownList({ source: ['base', 'android', 'arctic', 'black', 'blackberry', 'bootstrap', 'classic', 'darkblue', 'energyblue', 'fresh', 'highcontrast', 'metro', 'metrodark', 'mobile', 'office', 'orange', 'shinyblack', 'summer', 'ui-darkness', 'ui-le-frog', 'ui-lightness', 'ui-overcast', 'ui-redmond', 'ui-smoothness', 'ui-start', 'ui-sunny', 'web', 'windowsphone'], selectedIndex: 0, width: 150, height: 25, dropDownHeight: 250, theme: theme});
                $('#sysAdmin').jqxCheckBox({ width: 25, theme: theme });
                $('#ksAdmin').jqxCheckBox({ width: 25, theme: theme });
                $('#ksUser').jqxCheckBox({ width: 25, theme: theme });
                $('#developer').jqxCheckBox({ width: 25, theme: theme });
                $('#support').jqxCheckBox({ width: 25, theme: theme });
                $('#windowTable').jqxValidator({
                    rules: [
                        { input: '#username', message: 'Username is required!', action: 'keyup, blur', rule: 'required' },
                        { input: '#email', message: 'Email format is wrong!', action: 'keyup, blur', rule: 'email' },
                        { input: '#sims_password', message: 'Password doesn\'t match', action: 'keyup, blur', rule: function(){
                            if($('#sims_password').val() == $('#sims_password1').val()) {
                                return true;
                            } else {
                                return false;
                            }
                        }},
                        { input: '#sims_password1', message: 'Password doesn\'t match', action: 'keyup, blur', rule: function(){
                            if($('#sims_password').val() == $('#sims_password1').val()) {
                                return true;
                            } else {
                                return false;
                            }
                        }}
                    ]
                });
                $('#save').jqxButton({ width: 70, height: 27, theme: theme}).on('click', function (event) {
                    var valid = $('#windowTable').jqxValidator('validate');
                    if (!valid) {
                        return;
                    }
                    var url;
                    if (this.value == "New") {
                        url = "Profile!addProfile";
                    } else if (this.value == "Save") {
                        url = "Profile!updateProfile";
                    }
                    $.ajax({async: false, type: "post", url: url, dataType: "json",
                        data: {
                            'userList[0].username': $('#username').jqxInput('val'),
                            'userList[0].hostname': $('#hostname').jqxInput('val'),
                            'userList[0].sims': $('#sims').jqxInput('val'),
                            'userList[0].sims_password': $('#sims_password').jqxPasswordInput('val'),
                            'userList[0].region': $("#region").jqxDropDownList('val'),
                            'userList[0].email': $('#email').jqxInput('val'),
                            'userList[0].extension': $('#extension').jqxInput('val'),
                            'userList[0].userIndex': $('#userIndex').jqxInput('val'),
                            'userList[0].department': $("#department").jqxDropDownList('val'),
                            'userList[0].theme': $("#theme").jqxDropDownList('val'),
                            'userList[0].sysadmin': $('#sysAdmin').jqxCheckBox('val'),
                            'userList[0].knowledge_sharing_admin': $('#ksAdmin').jqxCheckBox('val'),
                            'userList[0].knowledge_sharing_user': $('#ksUser').jqxCheckBox('val'),
                            'userList[0].developer': $('#developer').jqxCheckBox('val'),
                            'userList[0].support': $('#support').jqxCheckBox('val')
                        },
                        complete: function (response, returnType) {
                            if (response.responseJSON == null || response.responseJSON == "") {
                                post("/${mysword_path}/error", "_self", {error: response.responseText});
                            } else {
                                if (this.url == "Profile!addProfile") {
                                    $("#profile").jqxDataTable('addRow', response.responseJSON.userList[0].username, response.responseJSON.userList[0]);
                                    $('#window').jqxWindow('close');
                                } else if (this.url == "Profile!updateProfile") {
                                    $("#profile").jqxDataTable('updateRow', tableSelectionIdex, response.responseJSON.userList[0]);
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
                    $('#username').jqxInput('disabled', false);
                    $('#save').jqxButton('val', 'New');
                    $('#window').jqxWindow('title', 'Null');
                    $('#window').jqxWindow('open');
                });
                var source = {
                    url: 'Profile!listProfiles',
                    async: false,
                    datatype: "json",
                    datafields: [
                        { name: 'hostname', type: "string" },
                        { name: 'username', type: "string" },
                        { name: 'sims', type: "string" },
                        { name: 'sims_password', type: "string"},
                        { name: 'region', type: "string" },
                        { name: 'email', type: "string" },
                        { name: 'extension', type: "string" },
                        { name: 'userIndex', type: "string" },
                        { name: 'department', type: "string" },
                        { name: 'theme', type: "number" },
                        { name: 'sysadmin', type: "boolean" },
                        { name: 'knowledge_sharing_admin', type: "boolean" },
                        { name: 'knowledge_sharing_user', type: "boolean" },
                        { name: 'developer', type: "boolean" },
                        { name: 'support', type: "boolean"},
                        { name: 'lasteditdt', type: "string" },
                        { name: 'lasteditby', type: "string" }
                    ],
                    id: 'username'
                }
                var dataAdapter = new $.jqx.dataAdapter(source, {
                    loadError: function (xhr, status, error) {
                        post("/${mysword_path}/error", "_self", {error: xhr.responseText});
                    }
                });
                $("#profile").jqxDataTable({ source: dataAdapter, theme: theme, altrows: true, enableBrowserSelection: true, columnsResize: true, sortable: true, selectionMode: 'customer',
                    columns: [
                        { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                            return row + 1;
                        }},
                        { text: 'Action', dataField: 'action', width: 50, cellsRenderer: function (row, column, value, rowData) {
                            return "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(" + row + ")' />&nbsp;"
                                    + "<img src='../image/operations/pencil.png' title='Edit this item.' onclick='editItem(" + row + ")' />";
                        }},
                        { text: 'Hostname', dataField: 'hostname', hidden: true, width: 200 },
                        { text: 'Username', dataField: 'username', width: 80 },
                        { text: 'SIMS', dataField: 'sims', hidden: true, width: 100 },
                        { text: 'SIMS password', dataField: 'sims_password', hidden: true, width: 120 },
                        { text: 'Region', dataField: 'region', width: 90 },
                        { text: 'Email', dataField: 'email', width: 250 },
                        { text: 'Extension', dataField: 'extension', width: 140 },
                        { text: 'User Index', dataField: 'userIndex', hidden: true, width: 50 },
                        { text: 'Department', dataField: 'department', width: 100 },
                        { text: 'Theme', dataField: 'theme', width: 110 },
                        { text: 'Admin', dataField: 'sysadmin', cellsAlign: 'center', width: 60, cellsRenderer: function (row, column, value, rowData) {
                            if (value) {
                                return "<img src='../image/operations/correct.ico'/>";
                            } else {
                                return "<img src='../image/operations/pink-cross.ico'/>";
                            }
                        }},
                        { text: 'KS Adm.', dataField: 'knowledge_sharing_admin', cellsAlign: 'center', width: 60, cellsRenderer: function (row, column, value, rowData) {
                            if (value) {
                                return "<img src='../image/operations/correct.ico'/>";
                            } else {
                                return "<img src='../image/operations/pink-cross.ico'/>";
                            }
                        } },
                        { text: 'KS User', dataField: 'knowledge_sharing_user', cellsAlign: 'center', width: 60, cellsRenderer: function (row, column, value, rowData) {
                            if (value) {
                                return "<img src='../image/operations/correct.ico'/>";
                            } else {
                                return "<img src='../image/operations/pink-cross.ico'/>";
                            }
                        } },
                        { text: 'Dev.', dataField: 'developer', cellsAlign: 'center', width: 60, cellsRenderer: function (row, column, value, rowData) {
                            if (value) {
                                return "<img src='../image/operations/correct.ico'/>";
                            } else {
                                return "<img src='../image/operations/pink-cross.ico'/>";
                            }
                        } },
                        { text: 'Supp.', dataField: 'support', cellsAlign: 'center', width: 57, cellsRenderer: function (row, column, value, rowData) {
                            if (value) {
                                return "<img src='../image/operations/correct.ico'/>";
                            } else {
                                return "<img src='../image/operations/pink-cross.ico'/>";
                            }
                        } }
                    ]
                });
                resetWindowHeight();
	        });
            function deleteItem(rowIndex) {
                $("#profile").jqxDataTable('clearSelection');
                tableSelectionIdex = rowIndex;
                $("#profile").jqxDataTable('selectRow', rowIndex);
                var data = $("#profile").jqxDataTable('getSelection');
                if(!confirm("Do you really want to delete " + data[0].hostname)) {
                    return;
                }
                $.ajax({async: false, type: "post", url: "Profile!deleteProfile", dataType: "json",
                    data: {'userList[0].username': data[0].username},
                    complete: function (response, returnType) {
                        if(response.responseJSON == null || response.responseJSON == "") {
                            post("/${mysword_path}/error", "_self", {error: response.responseText});
                        } else {
                            $("#profile").jqxDataTable('deleteRow', rowIndex);
                        }
                    }
                })
            }
            function editItem(rowIndex) {
                $('#username').jqxInput('disabled', true);
                tableSelectionIdex = rowIndex;
                $("#profile").jqxDataTable('clearSelection');
                $("#profile").jqxDataTable('selectRow', rowIndex);
                var data = $("#profile").jqxDataTable('getSelection');
                $('#save').jqxButton('val', 'Save');
                $('#window').jqxWindow({ title: data[0].username });
                $('#hostname').val(data[0].hostname);
                $('#username').val(data[0].username);
                $('#sims').val(data[0].sims);
                $('#sims_password').val(data[0].sims_password);
                $('#sims_password1').val(data[0].sims_password);
                $("#region").val(data[0].region);
                $('#email').val(data[0].email);
                $('#extension').val(data[0].extension);
                $('#userIndex').val(data[0].userIndex);
                $("#department").val(data[0].department);
                $("#theme").val(data[0].theme);
                $('#sysAdmin').val(data[0].sysadmin);
                $('#ksAdmin').val(data[0].knowledge_sharing_admin);
                $('#ksUser').val(data[0].knowledge_sharing_user);
                $('#developer').val(data[0].developer);
                $('#support').val(data[0].support);
                $('#window').jqxWindow('open');
            }
            function clearWindowData() {
                $('#hostname').val('');
                $('#username').val('');
                $('#sims').val('');
                $('#sims_password').val('');
                $('#sims_password1').val('');
                $("#region").val('');
                $('#email').val('');
                $('#extension').val('');
                $('#userIndex').val('');
                $("#department").val('');
                $("#theme").val('web');
                $('#sysAdmin').val(false);
                $('#ksAdmin').val(false);
                $('#ksUser').val(false);
                $('#developer').val(false);
                $('#support').val(false);
            }
	    </script>
	</head>
	<body>
        <button id="new">New</button>
        <br/>
        <br/>
        <div id="profile"></div>
        <div id="window">
            <div></div>
            <div>
                <table id="windowTable">
                    <tr>
                        <th class="mandatory">Username</th>
                        <td colspan="3"><input id="username" /></td>
                    </tr>
                    <tr>
                        <th>Hostname</th>
                        <td colspan="3"><input id="hostname" /></td>
                    </tr>
                    <tr>
                        <th>SIMS</th>
                        <td colspan="3"><input id="sims" /></td>
                    </tr>
                    <tr>
                        <th>Password</th>
                        <td colspan="3"><input type="password" id="sims_password" /></td>
                    </tr>
                    <tr>
                        <th>Retry Password</th>
                        <td colspan="3"><input type="password" id="sims_password1" /></td>
                    </tr>
                    <tr>
                        <th>Region</th>
                        <td colspan="3"><div id="region"></div></td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td colspan="3"><input id="email" /></td>
                    </tr>
                    <tr>
                        <th>Extension</th>
                        <td colspan="3"><input id="extension" /></td>
                    </tr>
                    <tr>
                        <th>User Index</th>
                        <td colspan="3"><input id="userIndex" /></td>
                    </tr>
                    <tr>
                        <th>Department</th>
                        <td colspan="3"><div id="department"></div></td>
                    </tr>
                    <tr>
                        <th>Theme</th>
                        <td colspan="3"><div id="theme"></div></td>
                    </tr>
                    <tr>
                        <th>System Admin</th>
                        <td colspan="3"><div id="sysAdmin"></div></td>
                    </tr>
                    <tr>
                        <th>KS Admin</th>
                        <td><div id="ksAdmin"></div></td>
                        <th>KS User</th>
                        <td><div id="ksUser"></div></td>
                    </tr>
                    <tr>
                        <th>Developer</th>
                        <td><div id="developer"></div></td>
                        <th>Support</th>
                        <td><div id="support"></div></td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: center"><button id="save">Save</button>&nbsp;&nbsp;&nbsp;<button id="cancel">Cancel</button></td>
                    </tr>
                </table>
            </div>
        </div>
	</body>
</html>