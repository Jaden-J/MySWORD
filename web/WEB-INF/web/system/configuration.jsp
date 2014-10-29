<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Knowledge Configuration Home</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
    <script type="text/javascript" src="../js/main.js"></script>
    <script type="text/javascript" src="../js/jquery/jquery.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
    <style type="text/css">
        td {
            vertical-align: bottom;
        }
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
        $(document).on('ready', function(){
            $('#window').jqxWindow({width: 500, height: 350, isModal: true, autoOpen: false, theme: theme});
            $('#window').on('close', function(event){ clearWindowData(); });
            $('#config_name').jqxInput({ placeHolder: "Please input the config name.", width: 300, height: 25, theme: theme});
            $('#config_value').jqxInput({ placeHolder: "Please input the config value", width: 300, height: 25, theme: theme});
            $("#config_type").jqxDropDownList({ source: ['SYSTEM', 'SERVER', 'KNOWLEDGE', 'EMAIL', 'CUSTOM'], selectedIndex: 3, width: 150, height: 25, dropDownHeight: 130, theme: theme});
            $('#config_req').jqxCheckBox({ width: 25, theme: theme });
            $('#config_req').on('change', function(event) {
                if(event.args.checked) {
                    $('#valueTh').attr('class', 'mandatory');
                } else {
                    $('#valueTh').attr('class', '');
                }
            });
            $('#config_desc').jqxInput({ placeHolder: "Please input the config description", width: 300, height: 90, theme: theme});
            $('#windowTable').jqxValidator({
                rules: [
                    { input: '#config_name', message: 'Config name is required!', action: 'keyup, blur', rule: 'required' },
                    { input: '#config_value', message: 'Config value is required!', action: 'keyup, blur', rule: function(){
                        var data = $('#config_value').val();
                        if($('#config_req').jqxCheckBox('val') && $('#config_value').val() == '') {
                            return false;
                        } else {
                            return true;
                        }
                    }}
                ]
            });
            $('#save').jqxButton({ width: 70, height:27, theme: theme}).on('click', function (event) {
                var valid = $('#windowTable').jqxValidator('validate');
                if (!valid) {
                    return;
                }
                var url;
                if (this.value == "New") {
                    url = "Config!addConfig";
                } else if (this.value == "Save") {
                    url = "Config!updateConfig";
                }
                $.ajax({async: false, type: "post", url: url, dataType: "json",
                    data: {
                        'configList[0].config_name': $('#config_name').jqxInput('val'),
                        'configList[0].config_value': $('#config_value').jqxInput('val'),
                        'configList[0].config_desc': $('#config_desc').val(),
                        'configList[0].config_type': $("#config_type").jqxDropDownList('val'),
                        'configList[0].config_req': $('#config_req').jqxCheckBox('val'),
                        'configList[0].config_desc': $('#config_desc').val()
                    },
                    complete: function (response, returnType) {
                        if (response.responseJSON == null || response.responseJSON == "") {
                            post("/${mysword_path}/error", "_self", {error: response.responseText});
                        } else {
                            if (this.url == "Config!addConfig") {
                                $("#configTable").jqxDataTable('addRow', response.responseJSON.configList[0].config_name, response.responseJSON.configList[0]);
                                $('#window').jqxWindow('close');
                            } else if (this.url == "Config!updateConfig") {
                                $("#configTable").jqxDataTable('updateRow', tableSelectionIdex, response.responseJSON.configList[0]);
                                $('#window').jqxWindow('close');
                            }
                        }
                        clearWindowData();
                        resetWindowHeight();
                    }
                })
            });
            $('#cancel').jqxButton({ width: 70, height:27, theme: theme}).on('click', function () {
                clearWindowData();
                $('#window').jqxWindow('close');
            });
            $('#new').jqxButton({ width: 70, height:27, theme: theme}).on('click', function () {
                $('#save').jqxButton('val', 'New');
                $('#window').jqxWindow('title', 'Null');
                $('#window').jqxWindow('open');
            });
            var source = {
                url: "Config!listConfigs",
                async: false,
                datatype: "json",
                datafields: [
                    { name: 'config_name', type: 'String' },
                    { name: 'config_value', type: 'String' },
                    { name: 'config_type', type: 'String' },
                    { name: 'config_desc', type: 'String' },
                    { name: 'config_req', type: 'Boolean' },
                    { name: 'lasteditby', type: 'String' },
                    { name: 'lasteditdt', type: 'String' }
                ],
                root: 'configList'
            };
            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadError: function (xhr, status, error) {
                    post("/${mysword_path}/error", "_self", {error: xhr.responseText});
                }
            });
            $('#configTable').jqxDataTable( { theme: theme, source: dataAdapter, enableBrowserSelection: true, altrows: true,
                columns: [
                    { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                        return row + 1;
                    }},
                    { text: 'Action', dataField: 'action', width: 50, cellsRenderer: function (row, column, value, rowData) {
                        return "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(" + row + ")' />&nbsp;"
                                + "<img src='../image/operations/pencil.png' title='Edit this item.' onclick='editItem(" + row + ")' />";
                    }},
                    { text: 'Info', dataField: 'info', cellsalign: 'center', width: 40, cellsRenderer: function (row, column, value, rowData) {
                        return "<img src='../image/operations/info.png' class='info' title='" + rowData.config_desc + "' onclick='deleteItem(" + row + ")' />";
                    }},
                    { text: 'Name', datafield: 'config_name', width: 250 },
                    { text: 'Value', datafield: 'config_value', width: 350 },
                    { text: 'Type', datafield: 'config_type', width: 100 },
                    { text: 'Req.', datafield: 'config_req', cellsalign: 'center', width: 40, cellsRenderer: function (row, column, value, rowData) {
                        if (value) {
                            return "<img src='../image/operations/correct.ico'/>";
                        } else {
                            return "<img src='../image/operations/pink-cross.ico'/>";
                        }
                    }},
                    { text: 'Description', datafield: 'config_desc', hidden: true, width: 300 },
                    { text: 'Edit By', datafield: 'lasteditby', width: 100 },
                    { text: 'Edit Date', datafield: 'lasteditdt', width: 180 }
                ]
            });
            resetWindowHeight();
        });

        function editItem(rowIndex) {
            $('#save').jqxButton('val', 'Save');
            $('#config_name').jqxInput('disabled', true);
            tableSelectionIdex = rowIndex;
            $("#configTable").jqxDataTable('clearSelection');
            $("#configTable").jqxDataTable('selectRow', rowIndex);
            var data = $("#configTable").jqxDataTable('getSelection');
            $('#window').jqxWindow('title', data[0].config_name);
            $('#window').jqxWindow('open');
            $('#config_name').jqxInput('val', data[0].config_name);
            $('#config_value').jqxInput('val', data[0].config_value);
            $("#config_type").jqxDropDownList('val', data[0].config_type);
            $('#config_req').jqxCheckBox('val', data[0].config_req);
            $('#config_desc').val(data[0].config_desc);
        }

        function deleteItem(rowIndex) {
            $('#config_name').jqxInput('disabled', true);
            tableSelectionIdex = rowIndex;
            $("#configTable").jqxDataTable('clearSelection');
            $("#configTable").jqxDataTable('selectRow', rowIndex);
            var data = $("#configTable").jqxDataTable('getSelection');
            if(!confirm("Do you really want to delete " + data[0].config_name)) {
                return;
            }
            $.ajax({async: false, type: "post", url: "Config!deleteConfig", dataType: "json",
                data: {
                    'configList[0].config_name': data[0].config_name
                },
                complete: function (response, returnType) {
                    if (response.responseJSON == null || response.responseJSON == "") {
                        post("/${mysword_path}/error", "_self", {error: response.responseText});
                    } else {
                        $("#configTable").jqxDataTable('deleteRow', rowIndex);
                    }
                    resetWindowHeight()
                }
            })
        }

        function clearWindowData() {
            $('#config_name').jqxInput('disabled', false);
            $('#config_name').val('');
            $('#config_value').val('');
            $('#config_type').jqxDropDownList('val', 'CUSTOM');
            $('#config_desc').val('');
            $("#config_req").val(false);
        }

//        function resetWindowHeight() {
//            if($('body').height()<600){
//                window.frameElement.height = 600;
//            } else {
//                window.frameElement.height = $('body').height() + 20;
//            }
//        }
    </script>
</head>
<body style="width: 1200px">
    <button id="new">New</button>
    <br/>
    <br/>
    <div id="configTable"></div>
    <div id="window">
        <div></div>
        <div>
            <table id="windowTable">
                <tr>
                    <th class="mandatory">Name</th>
                    <td><input id="config_name" /></td>
                </tr>
                <tr>
                    <th id="valueTh">Value</th>
                    <td><input id="config_value" /></td>
                </tr>
                <tr>
                    <th>Type</th>
                    <td><div id="config_type"></div></td>
                </tr>
                <tr>
                    <th>Required</th>
                    <td><div id="config_req"></div></td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td><textarea id="config_desc" wrap="off"></textarea></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center"><button id="save">Save</button>&nbsp;&nbsp;&nbsp;<button id="cancel">Cancel</button></td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>