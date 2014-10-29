<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>MySWORD Access List Page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="MySWORD Access List Page">
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
                font-family: "DB Office",Arial;
            }

            #windowTable tr {
                line-height: 30px;
            }

            #windowTable th {
                font-family: "DB Office",Arial;
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
                $('#window').jqxWindow({width: 500, height: 200, isModal: true, autoOpen: false, theme: theme});
                $('#window').on('close', function(event){ clearWindowData(); });
                $('#mac').jqxInput({width: 300, height: 25, disabled:true, theme: theme});
                $('#username').jqxInput({width: 300, height: 25, theme: theme});
                $('#description').jqxInput({width: 300, height: 25, theme: theme});
                $('#windowTable').jqxValidator({
                    rules: [
                        { input: '#username', message: 'Username is required!', action: 'keyup, blur', rule: 'required' },
                        { input: '#mac', message: 'Mac is required!', action: 'keyup, blur', rule: 'required' }
                    ]
                });
                $('#save').jqxButton({ width: 70, height: 27, theme: theme}).on('click', function (event) {
                    var valid = $('#windowTable').jqxValidator('validate');
                    if (!valid) {
                        return;
                    }
                    var url;
                    if (this.value == "New") {
                        url = "Access!addMac";
                    } else if (this.value == "Save") {
                        url = "Access!updateMac";
                    }
                    $.ajax({async: false, type: "post", url: url, dataType: "json",
                        data: {
                            'macList[0].username': $('#username').jqxInput('val'),
                            'macList[0].mac': $('#mac').jqxInput('val'),
                            'macList[0].description': $('#description').jqxInput('val')
                        },
                        complete: function (response, returnType) {
                            if (response.responseJSON == null || response.responseJSON == "") {
                                post("/${mysword_path}/error", "_self", {error: response.responseText});
                            } else {
                                if (this.url == "Access!addMac") {
                                    $("#macTable").jqxDataTable('addRow', response.responseJSON.macList[0].mac, response.responseJSON.macList[0]);
                                    $('#window').jqxWindow('close');
                                } else if (this.url == "Access!updateMac") {
                                    $("#macTable").jqxDataTable('updateRow', tableSelectionIdex, response.responseJSON.macList[0]);
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
                    $('#mac').jqxInput('disabled', false);
                    $('#save').jqxButton('val', 'New');
                    $('#window').jqxWindow('title', 'Null');
                    $('#window').jqxWindow('open');
                });
                var source = {
                    url: 'Access!listMacs',
                    async: false,
                    datatype: "json",
                    datafields: [
                        { name: 'mac', type: "string" },
                        { name: 'username', type: "string" },
                        { name: 'description', type: "string" },
                        { name: 'lasteditby', type: "string" },
                        { name: 'lasteditdt', type: "string" }
                    ],
                    id: 'mac'
                }
                var dataAdapter = new $.jqx.dataAdapter(source, {
                    loadError: function (xhr, status, error) {
                        post("/${mysword_path}/error", "_self", {error: xhr.responseText});
                    }
                });
                $("#macTable").jqxDataTable({ source: dataAdapter, theme: theme, altrows: true, enableBrowserSelection: true, columnsResize: true, sortable: true, selectionMode: 'customer',
                    columns: [
                        { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                            return row + 1;
                        }},
                        { text: 'Action', dataField: 'action', width: 50, cellsRenderer: function (row, column, value, rowData) {
                            return "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(" + row + ")' />&nbsp;"
                                    + "<img src='../image/operations/pencil.png' title='Edit this item.' onclick='editItem(" + row + ")' />";
                        }},
                        { text: 'mac', dataField: 'mac', width: 180 },
                        { text: 'Username', dataField: 'username', width: 80 },
                        { text: 'Description', dataField: 'description', width: 220 },
                        { text: 'Last Edit', dataField: 'lasteditby', width: 120 },
                        { text: 'Edit Date', dataField: 'lasteditdt', width: 200 }
                    ]
                });
                resetWindowHeight();
	        });
            function deleteItem(rowIndex) {
                $("#macTable").jqxDataTable('clearSelection');
                tableSelectionIdex = rowIndex;
                $("#macTable").jqxDataTable('selectRow', rowIndex);
                var data = $("#macTable").jqxDataTable('getSelection');
                if(!confirm("Do you really want to delete " + data[0].mac)) {
                    return;
                }
                $.ajax({async: false, type: "post", url: "Access!deleteMac", dataType: "json",
                    data: {'macList[0].mac': data[0].mac},
                    complete: function (response, returnType) {
                        if(response.responseJSON == null || response.responseJSON == "") {
                            post("/${mysword_path}/error", "_self", {error: response.responseText});
                        } else {
                            $("#macTable").jqxDataTable('deleteRow', rowIndex);
                        }
                    }
                })
                resetWindowHeight();
            }
            function editItem(rowIndex) {
                $('#mac').jqxInput('disabled', true);
                tableSelectionIdex = rowIndex;
                $("#macTable").jqxDataTable('clearSelection');
                $("#macTable").jqxDataTable('selectRow', rowIndex);
                var data = $("#macTable").jqxDataTable('getSelection');
                $('#save').jqxButton('val', 'Save');
                $('#window').jqxWindow({ title: data[0].mac });
                $('#mac').val(data[0].mac);
                $('#username').val(data[0].username);
                $('#description').val(data[0].description);
                $('#window').jqxWindow('open');
            }
            function clearWindowData() {
                $('#mac').val('');
                $('#username').val('');
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
        <div id="macTable"></div>
        <div id="window">
            <div></div>
            <div>
                <table id="windowTable">
                    <tr>
                        <th class="mandatory">Mac</th>
                        <td><input id="mac" /></td>
                    </tr>
                    <tr>
                        <th class="mandatory">Username</th>
                        <td><input id="username" /></td>
                    </tr>
                    <tr>
                        <th>Description</th>
                        <td><input id="description" /></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center"><button id="save">Save</button>&nbsp;&nbsp;&nbsp;<button id="cancel">Cancel</button></td>
                    </tr>
                </table>
            </div>
        </div>
	</body>
</html>