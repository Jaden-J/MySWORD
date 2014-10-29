<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Project List</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="description" content="MySWORD Project List Page">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
    <script type="text/javascript" src="../js/main.js"></script>
    <script type="text/javascript" src="../js/jquery/jquery.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
    <style type="text/css">
        img {
            width: 20px;
            height: 20px;
            cursor: pointer;;
        }

        .type {
            margin-left: 10px;
            margin-top: 5px;
            font-size: 20px;
            font-style: italic;
            font-weight: bold;
            color: #6699FF;
        }

        th {
            font-size: 14px;
        }

        th:after {
            content: ":";
        }
    </style>
    <script type="text/javascript">
        var theme = "${mysword_theme}";
        var selection = 0;
        var itemId;
        var lockId;
        var region;
        var item_Type;
        $(document).ready(function () {
            var weekStr = '${weekStr}';
            $('#window').jqxWindow({width: 600, height: 360, isModal: true, autoOpen: false, position: { x: 300, y: 400 }, theme: theme});
            $('#window').on('close', function (event){initWindowData()});
            $('#detailPanel').jqxExpander({width: 570, showArrow: true, expanded: false, theme: theme});
            $('#detailPanel').on('expanded', function () {$('#window').jqxWindow({height: 490})});
            $('#detailPanel').on('collapsed', function () {$('#window').jqxWindow({height: 360})});
            $('#weekList').jqxDropDownList({ source: [], selectedIndex: 0, width: 100, height: 25, theme: theme});
            $('#report, #add, #refresh, #export, #print, #upload, #save, #cancel, #sync').jqxButton({width: 70, height: 27, theme: theme});
            $("#scriptId").jqxInput({ width: 110, height: 25, theme: theme });
            $("#gsd").jqxInput({ width: 110, height: 25, theme: theme });
            $("#projectName").jqxInput({ width: 435, height: 25, theme: theme });
            $('#category').jqxDropDownList({ source: ['Change Request/Update','New Request','Admin/Maintenance'], selectedIndex: 0, width: 200, dropDownHeight: 80, height: 25, theme: theme});
            $("#customer").jqxInput({ width: 110, height: 25, theme: theme });
            $("#coordinator").jqxInput({ source: ['AD','AS','BR','BS','CD','CF','DH','DS','KG','MH','KK','RY'], width: 110, height: 25, theme: theme });
            $("#developer").jqxInput({ source: ['AK','BJ','JZ','HY','LL','ML','MT','TZ','SW','VX'], width: 110, height: 25, theme: theme });
            $("#docLink").jqxInput({ width: 435, height: 25, theme: theme });
            $("#liveOnTest").jqxInput({ width: 110, height: 25, theme: theme });
            $("#liveOnProd").jqxInput({ width: 110, height: 25, theme: theme });
            $('#priority').jqxDropDownList({ source: ['Low','Medium','High'], selectedIndex: 0, width: 110, dropDownHeight: 80, height: 25, theme: theme});
            $("#estimateEffort").jqxInput({ width: 110, height: 25, theme: theme });
            $("#realEffort").jqxInput({ width: 110, height: 25, theme: theme });
            $("#pendingEffort").jqxInput({ width: 110, height: 25, theme: theme });
            $("#restEffort").jqxInput({ width: 110, height: 25, theme: theme });
            $("#underEstimatedEffort").jqxInput({ width: 110, height: 25, theme: theme });
            $('#report, #add, #export, #refresh, #print, #upload, #save, #cancel, #sync').on('click', function(event){
                switch(this.id) {
                    case "report":
                        break;
                    case "refresh":
                        window.location.reload();
                    case "add":
                        $('#save').val('New');
                        $('#window').jqxWindow({ title: 'Add Project' });
                        $('#window').jqxWindow('move', getX(event), getY(event));
                        $('#window').jqxWindow('open');
                        break;
                    case "save":
                        var temp_data = {
                            itemId: itemId,
                            lockId: lockId,
                            week: $('#weekList').jqxDropDownList('val'),
                            scriptId: $("#scriptId").val(),
                            GSD: $("#gsd").val(),
                            projectName: $("#projectName").val(),
                            docLink: $("#docLink").val(),
                            region: $("#region").jqxDropDownList('val'),
                            item_Type: $("#item_Type").jqxDropDownList('val'),
                            coordinator: $("#coordinator").val(),
                            developer: $("#developer").val(),
                            liveOnTest: $("#liveOnTest").val(),
                            liveOnProd: $("#liveOnProd").val(),
                            category: $("#category").jqxDropDownList('val'),
                            customer: $("#customer").val(),
                            priority: $("#priority").val(),
                            estimateEffort: $("#estimateEffort").val(),
                            realEffort: $("#realEffort").val(),
                            pendingEffort: $("#pendingEffort").val(),
                            restEffort: $("#restEffort").val(),
                            underEstimatedEffort: $("#underEstimatedEffort").val(),
                            item_Comment: $("#item_Comment").val()
                        }
                        if (temp_data.estimateEffort == null || temp_data.estimateEffort == "") {
                            temp_data.estimateEffort = 0;
                        }
                        if(temp_data.GSD == "" && temp_data.projectName == "") {
                            alert("GSD# and Project Name cannot be empty at the same time!");
                            return;
                        }
                        if(this.value == "New") {
                            for(var i=0;i<$('#tabs').jqxTabs('length'); i++){
                                var text = $('#tabs').jqxTabs('getTitleAt', i);
                                if(text ==  $("#region").jqxDropDownList('val')){
                                    $('#tabs').jqxTabs('select', i);
                                    break;
                                }
                            }
                            if(!existId($("#region").jqxDropDownList('val') + $("#item_Type").jqxDropDownList('val'))) {
                                alert("There is no region'"+$("#region").jqxDropDownList('val')+"' and type '"+$("#item_Type").jqxDropDownList('val')+"' exist!");
                                return;
                            }
                            $("#" + $("#region").jqxDropDownList('val') + $("#item_Type").jqxDropDownList('val')).jqxDataTable('addRow', null, temp_data);
                            $("#" + $("#region").jqxDropDownList('val') + $("#item_Type").jqxDropDownList('val')).jqxDataTable('height', null);
                        } else if(this.value == "Save") {
                            if(region == temp_data.region && item_Type == temp_data.item_Type) {
                                $("#" + $("#region").jqxDropDownList('val') + $("#item_Type").jqxDropDownList('val')).jqxDataTable('updateRow', selection, temp_data);
                                $("#" + $("#region").jqxDropDownList('val') + $("#item_Type").jqxDropDownList('val')).jqxDataTable('height', null);
                            } else {
                                if(!existId($("#region").jqxDropDownList('val') + $("#item_Type").jqxDropDownList('val'))) {
                                    alert("There is no region'"+$("#region").jqxDropDownList('val')+"' and type '"+$("#item_Type").jqxDropDownList('val')+"' exist!");
                                    return;
                                }
                                $("#" + region + item_Type).jqxDataTable('deleteRow', selection);
                                if($("#" + region + item_Type).jqxDataTable('getRows')==null || $("#" + region + item_Type).jqxDataTable('getRows').length==0) {
                                    $("#" + region + item_Type).jqxDataTable('height', 90);
                                }
                                for(var i=0;i<$('#tabs').jqxTabs('length'); i++){
                                    var text = $('#tabs').jqxTabs('getTitleAt', i);
                                    if(text ==  $("#region").jqxDropDownList('val')){
                                        $('#tabs').jqxTabs('select', i);
                                        break;
                                    }
                                }
                                $("#" + $("#region").jqxDropDownList('val') + $("#item_Type").jqxDropDownList('val')).jqxDataTable('addRow', null, temp_data);
                                $("#" + $("#region").jqxDropDownList('val') + $("#item_Type").jqxDropDownList('val')).jqxDataTable('height', null);
                            }
                        }
                        $('#window').jqxWindow('close');
                        break;
                    case "cancel":
                        $('#window').jqxWindow('close');
                        break;
                    case "sync":
                        window.open("ProjectList!syncProjectListFile?weekStr="+$('#weekList').jqxDropDownList('val'), "_blank");
                        break;
                    case "export":
                        window.open("ProjectList!generateProjectListFile?weekStr="+$('#weekList').jqxDropDownList('val'), "_blank");
                        break;
                    case "print":
                        window.open("ProjectList!printProjectCards?weekStr="+$('#weekList').jqxDropDownList('val'), "_blank");
                        break;
                    case "upload":
                        $('#projectFile').click();
                        break;
                }
                resetWindowHeight();
            });
            $('#projectFile').on('change', function(event){
                $('#fileForm').submit();
            });
            var projectInfo = null;
            $.ajax({
                type: 'post',
                url: 'ProjectList!projectInfo',
                data: {'weekStr': weekStr},
                dataType: 'json',
                async: false,
                complete: function (response, returnType) {
                    if (response.responseJSON == null || response.responseJSON == "") {
                        post("/${sessionScope.mysword_path}/error", "_self", {error: response.responseText});
                    } else if (returnType == "success") {
                        projectInfo = response.responseJSON;
                    }
                }
            });
            $("#region").jqxDropDownList({ source: projectInfo.projectTypes.regions, selectedIndex: 0, width: 110, height: 25, dropDownHeight: 130, theme: theme });
            $("#item_Type").jqxDropDownList({ source: projectInfo.projectTypes.types, selectedIndex: 0, width: 110, height: 25, dropDownHeight: 105, theme: theme });
            $("#item_Comment").jqxInput({ placeHolder: "Please input comment.", width: 435, height: 60, theme: theme });
            //Fill the week list dropdown list. From the week of last year to current week.
            var myDate = new Date();
            for (var i = 1; i <= projectInfo.lastYearWeekCount; i++) {
                $("#weekList").jqxDropDownList('addItem', myDate.getFullYear() - 1 + "-" + i);
            }
            for (var i = 1; i <= projectInfo.thisYearWeekCount; i++) {
                $("#weekList").jqxDropDownList('addItem', myDate.getFullYear() + "-" + i);
            }
            $("#weekList").jqxDropDownList('selectItem', weekStr);
            $('#weekList').on('change', function(event){
                window.open("ProjectList?weekStr="+event.args.item.value, "_self");
            });
            //Generate the tooltips.
            $("#item_Type").jqxTooltip({ content: "Please choose the type.", position: 'mouse', name: 'tooltip', theme: theme});
            $("#export").jqxTooltip({ content: "Download the weekly excel file.", position: 'mouse', name: 'tooltip', theme: theme});
            $("#sync").jqxTooltip({ content: "Save the project items to Project Status file.", position: 'mouse', name: 'tooltip', theme: theme});
            $("#upload").jqxTooltip({ content: "Upload weekly project list file.", position: 'mouse', name: 'tooltip', theme: theme});
            $("#add").jqxTooltip({ content: "Click this button to add a new project item.", position: 'mouse', name: 'tooltip', theme: theme});
            $("#weekList").jqxTooltip({ content: 'Tables will refresh the data accroding to the week number you choose.', position: 'mouse', name: 'tooltip', theme: theme});
            $("#report").jqxTooltip({ content: "Click this button to view the current week's report.", position: 'mouse', name: 'tooltip', theme: theme});
            $("#region").jqxTooltip({ content: "Please choose the region.", position: 'mouse', name: 'tooltip', theme: theme});
            //Generete the project list tables.
            if(projectInfo.projectStructures.length > 0){
                $("body").append("<div id='tabs'><ul id='header'></ul>");
            }
            for (var i = 0; i < projectInfo.projectStructures.length; i++) {
                $("#header").append($("<li>").html(projectInfo.projectStructures[i].region));
                $("#tabs").append($("<div id='"+projectInfo.projectStructures[i].region+"'>"));
                for (var j = 0; j < projectInfo.projectStructures[i].types.length; j++) {
                    $("#"+projectInfo.projectStructures[i].region).append($("<div>").html(projectInfo.projectStructures[i].types[j]).addClass("type"));
                    $("#"+projectInfo.projectStructures[i].region).append($('<div id="' + projectInfo.projectStructures[i].region + projectInfo.projectStructures[i].types[j] + '">'));
                    var data = new Array();
                    for (var k = 0; k < projectInfo.projectList.length; k++) {
                        if (projectInfo.projectStructures[i].region == projectInfo.projectList[k].region && projectInfo.projectStructures[i].types[j] == projectInfo.projectList[k].item_Type) {
                            data.push(projectInfo.projectList[k]);
                        }
                    }
                    var tableHeight = null;
                    if (data.length == 0) {
                        tableHeight = 90;
                    }
                    var source = {
                        localdata: data,
                        datatype: "array",
                        datafields: [
                            { name: 'itemId', type: "string" },
                            { name: 'lockId', type: "string" },
                            { name: 'week', type: "string"},
                            { name: 'region', type: "string" },
                            { name: 'item_Type', type: "string" },
                            { name: 'scriptId', type: "string" },
                            { name: 'GSD', type: "string" },
                            { name: 'projectName', type: "string" },
                            { name: 'customer', type: "string" },
                            { name: 'coordinator', type: "string" },
                            { name: 'developer', type: "string" },
                            { name: 'category', type: "string" },
                            { name: 'priority', type: "string" },
                            { name: 'liveOnTest', type: "string" },
                            { name: 'liveOnProd', type: "string" },
                            { name: 'docLink', type: "string" },
                            { name: 'estimateEffort', type: "number" },
                            { name: 'realEffort', type: "number" },
                            { name: 'restEffort', type: "number" },
                            { name: 'pendingEffort', type: "number" },
                            { name: 'underEstimatedEffort', type: "number" },
                            { name: 'item_Comment', type: "string" },
                            { name: 'lastEditDT', type: "string" },
                            { name: 'lastEditBy', type: "string" }
                        ],
                        id: 'itemId',
                        addRow: function (rowID, rowData, position, commit) {
                            $.ajax({
                                type: 'post',
                                url: 'ProjectList!addProject',
                                data: {
                                    'projectList[0].itemId': rowData.itemId,
                                    'projectList[0].lockId': rowData.lockId,
                                    'projectList[0].week': rowData.week,
                                    'projectList[0].region': rowData.region,
                                    'projectList[0].item_Type': rowData.item_Type,
                                    'projectList[0].scriptId': rowData.scriptId,
                                    'projectList[0].GSD': rowData.GSD,
                                    'projectList[0].projectName': rowData.projectName,
                                    'projectList[0].coordinator': rowData.coordinator,
                                    'projectList[0].developer': rowData.developer,
                                    'projectList[0].category': rowData.category,
                                    'projectList[0].priority': rowData.priority,
                                    'projectList[0].liveOnTest': rowData.liveOnTest,
                                    'projectList[0].liveOnProd': rowData.liveOnProd,
                                    'projectList[0].docLink': rowData.docLink,
                                    'projectList[0].customer': rowData.customer,
                                    'projectList[0].estimateEffort': rowData.estimateEffort,
                                    'projectList[0].realEffort': rowData.realEffort,
                                    'projectList[0].pendingEffort': rowData.pendingEffort,
                                    'projectList[0].restEffort': rowData.restEffort,
                                    'projectList[0].underEstimatedEffort': rowData.underEstimatedEffort,
                                    'projectList[0].item_Comment': rowData.item_Comment
                                },
                                dataType: 'json',
                                async: false,
                                complete: function (response, returnType) {
                                    if (response.responseJSON == null || response.responseJSON == "") {
                                        commit(false);
                                        post("/${sessionScope.mysword_path}/error", "_self", {error: response.responseText});
                                    } else {
                                        var temp_data = response.responseJSON.projectList[0];
                                        rowData.lockId = temp_data.lockId;
                                        commit(true);
                                    }
                                }
                            });
                        },
                        updateRow: function (rowID, rowData, commit) {
                            $.ajax({
                                type: 'post',
                                url: 'ProjectList!updateProject',
                                data: {
                                    'projectList[0].itemId': rowData.itemId,
                                    'projectList[0].lockId': rowData.lockId,
                                    'projectList[0].week': rowData.week,
                                    'projectList[0].region': rowData.region,
                                    'projectList[0].item_Type': rowData.item_Type,
                                    'projectList[0].scriptId': rowData.scriptId,
                                    'projectList[0].GSD': rowData.GSD,
                                    'projectList[0].projectName': rowData.projectName,
                                    'projectList[0].customer': rowData.customer,
                                    'projectList[0].coordinator': rowData.coordinator,
                                    'projectList[0].developer': rowData.developer,
                                    'projectList[0].category': rowData.category,
                                    'projectList[0].priority': rowData.priority,
                                    'projectList[0].liveOnTest': rowData.liveOnTest,
                                    'projectList[0].liveOnProd': rowData.liveOnProd,
                                    'projectList[0].docLink': rowData.docLink,
                                    'projectList[0].estimateEffort': rowData.estimateEffort,
                                    'projectList[0].realEffort': rowData.realEffort,
                                    'projectList[0].pendingEffort': rowData.pendingEffort,
                                    'projectList[0].restEffort': rowData.restEffort,
                                    'projectList[0].underEstimatedEffort': rowData.underEstimatedEffort,
                                    'projectList[0].item_Comment': rowData.item_Comment
                                },
                                dataType: 'json',
                                async: false,
                                complete: function (response, returnType) {
                                    if (response.responseJSON == null || response.responseJSON == "") {
                                        post("/${sessionScope.mysword_path}/error", "_self", {error: response.responseText});
                                    } else {
                                        if(response.responseJSON.projectList == null) {
                                            if(confirm("This item has been deleted or updated by other users, do you want to reload?")) {
                                                window.location.reload();
                                            } else {
                                                commit(false);
                                            }
                                        } else if(response.responseJSON.projectList.length > 0) {
                                            var temp_data = response.responseJSON.projectList[0];
                                            rowData.lockId = temp_data.lockId;
                                            commit(true);
                                        }
                                    }
                                }
                            });
                        },
                        deleteRow: function (rowID, commit) {
                            $.ajax({
                                type: 'post',
                                url: 'ProjectList!deleteProject',
                                data: {
                                    'projectList[0].itemId': rowID
                                },
                                dataType: 'json',
                                async: false,
                                complete: function (response, returnType) {
                                    if (response.responseJSON == null || response.responseJSON == "") {
                                        commit(false);
                                        post("/${sessionScope.mysword_path}/error", "_self", {error: response.responseText});
                                    } else {
                                        commit(true);
                                    }
                                }
                            });
                        }
                    };
                    var dataAdapter = new $.jqx.dataAdapter(source);
                    $("#" + projectInfo.projectStructures[i].region + projectInfo.projectStructures[i].types[j]).jqxDataTable({ height: tableHeight, source: dataAdapter, theme: theme, selectionMode: "singleRow",
                        columnsResize: true, sortable: true, showAggregates: true,aggregatesHeight:25, enableBrowserSelection: true,
                        columns: [
                            { text: 'WK#', dataField: 'week', width: 40, hidden: true},
                            { text: 'ItemId', dataField: 'itemId', width: 100, hidden: true },
                            { text: 'LockId', dataField: 'lockId', width: 100, hidden: true },
                            { text: 'Type', dataField: 'item_Type', width: 120, hidden: true },
                            { text: 'Region', dataField: 'region', width: 100, hidden: true },
                            { text: 'Customer', dataField: 'customer', width: 100, hidden: true },
                            { text: 'coordinator', dataField: "coordinator", width: 100, hidden: true },
                            { text: 'category', dataField: "category", width: 100, hidden: true },
                            { text: 'priority', dataField: "priority", width: 100, hidden: true },
                            { text: 'liveOnTest', dataField: "liveOnTest", width: 100, hidden: true },
                            { text: 'liveOnProd', dataField: "liveOnProd", width: 100, hidden: true },
                            { text: 'docLink', dataField: "docLink", width: 100, hidden: true },
                            { dataField: 'rowNumber', editable: false, width: 30, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>No</div>";}, cellsRenderer: function (row, column, value, rowData) {
                                rowData.rowNumber = row;
                                return row+1;
                            }},
                            { dataField: 'GSD', width: 100, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>GSD#</div>";} },
                            { dataField: 'scriptId', width: 100, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>SCRIPT ID</div>";} },
                            { dataField: 'projectName', width: 380, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>ProjectName</div>";} },
                            { dataField: 'estimateEffort', width: 60, aggregates: ['sum'], cellsalign: 'center', renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Estimate</div>";},
                                cellsRenderer: function (row, column, value, rowData){
                                    return formatFloat(value);
                                },
                                aggregatesRenderer: function(aggregates){
                                    if(aggregates.sum!=null){
                                        return "<div style='padding: 2px 0px 0px 2px'>"+formatFloat(aggregates.sum)+"</div>";
                                    }else{
                                        return "";
                                    }
                                }
                            },
                            { dataField: 'realEffort', width: 60, aggregates: ['sum'], cellsalign: 'center', renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Real</div>";},
                                cellsRenderer: function (row, column, value, rowData){
                                    return formatFloat(value);
                                },
                                aggregatesRenderer: function(aggregates){
                                    if(aggregates.sum!=null){
                                        return "<div style='padding: 2px 0px 0px 2px'>"+formatFloat(aggregates.sum)+"</div>";
                                    }else{
                                        return "";
                                    }
                                }
                            },
                            { dataField: 'developer', width: 80, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Developer</div>";} },
                            { dataField: 'restEffort', width: 60, aggregates: ['sum'], cellsalign: 'center', renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Rest</div>";},
                                cellsRenderer: function (row, column, value, rowData){
                                    return formatFloat(value);
                                },
                                aggregatesRenderer: function(aggregates){
                                    if(aggregates.sum!=null){
                                        return "<div style='padding: 2px 0px 0px 2px'>"+formatFloat(aggregates.sum)+"</div>";
                                    }else{
                                        return "";
                                    }
                                }
                            },
                            { dataField: 'pendingEffort', width: 60, aggregates: ['sum'], cellsalign: 'center', renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Pending</div>";},
                                cellsRenderer: function (row, column, value, rowData){
                                    return formatFloat(value);
                                },
                                aggregatesRenderer: function(aggregates){
                                    if(aggregates.sum!=null){
                                        return "<div style='padding: 2px 0px 0px 2px'>"+formatFloat(aggregates.sum)+"</div>";
                                    }else{
                                        return "";
                                    }
                                }
                            },
                            { dataField: 'underEstimatedEffort', width: 60, aggregates: ['sum'], cellsalign: 'center', renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Under</div>";},
                                cellsRenderer: function (row, column, value, rowData){
                                    return formatFloat(value);
                                },
                                aggregatesRenderer: function(aggregates){
                                    if(aggregates.sum!=null){
                                        return "<div style='padding: 2px 0px 0px 2px'>"+formatFloat(aggregates.sum)+"</div>";
                                    }else{
                                        return "";
                                    }
                                }
                            },
                            { dataField: 'item_Comment', width: 310, renderer: function(){return "<div style='margin: 6px 0px 0px 3px; font-weight: bold'>Comment</div>";} },
                            { text:'', dataField: 'delete', editable: false, width: 30, cellsRenderer: function (row, column, value, rowData) {
                                return "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(\"" + rowData.region + rowData.item_Type + "\",\"" + row + "\")' />";
                            }}
                            ]
                        });
                        $("#" + projectInfo.projectStructures[i].region + projectInfo.projectStructures[i].types[j]).on('rowDoubleClick', function (event){
                            $('#save').val('Save');
                            initWindowData(event.args.row);
                            $('#window').jqxWindow({ title: 'Edit Project' });
                            $('#window').jqxWindow('move', event.currentTarget.offsetLeft+200, event.currentTarget.offsetTop);
                            $('#window').jqxWindow('open');
                        });
                }
            }
            if(projectInfo.projectStructures.length > 0){
                $('#tabs').jqxTabs({});
            }
            resetWindowHeight();
        });
        function deleteItem(id, rowIndex) {
            if(!confirm('Do you really want to delete this item?')) {
                return;
            }
            $("#" + id).jqxDataTable('deleteRow', parseInt(rowIndex));
            if($("#" + id).jqxDataTable('getRows').length == 0) {
                $("#" + id).jqxDataTable('height', 90);
            }
        }
        function formatFloat(value){
            var parts = (value+"").split(".");
            if(parts.length==1) {
                if(parts[0] == ""){
                    return "";
                } else {
                    return parts[0] + ".00";
                }
            } else if(parts.length==2) {
                for(var i=parts[1].length; i<2; i++) {
                    parts[1] = parts[1]+"0";
                }
                return parts[0]+"."+parts[1];
            }
        }
        function initWindowData(rowData) {
            $('#detailPanel').jqxExpander('collapse');
            if(rowData != null) {
                selection = rowData.rowNumber;
                itemId = rowData.itemId;
                lockId = rowData.lockId;
                region = rowData.region;
                item_Type = rowData.item_Type;
                $("#gsd").val(rowData.GSD);
                $("#scriptId").val(rowData.scriptId);
                $("#projectName").val(rowData.projectName);
                $("#docLink").val(rowData.docLink);
                $("#region").jqxDropDownList('val',rowData.region);
                $("#item_Type").jqxDropDownList('val',rowData.item_Type);
                $("#coordinator").val(rowData.coordinator);
                $("#developer").val(rowData.developer);
                $("#liveOnTest").val(rowData.liveOnTest);
                $("#liveOnProd").val(rowData.liveOnProd);
                $("#category").jqxDropDownList('val',rowData.category);
                $("#customer").val(rowData.customer);
                $("#priority").val(rowData.priority);
                $("#estimateEffort").val(rowData.estimateEffort);
                $("#realEffort").val(rowData.realEffort);
                $("#pendingEffort").val(rowData.pendingEffort);
                $("#restEffort").val(rowData.restEffort);
                $("#underEstimatedEffort").val(rowData.underEstimatedEffort);
                $("#item_Comment").val(rowData.item_Comment);
            } else {
                $("#gsd").val('');
                $("#scriptId").val('');
                $("#projectName").val('');
                $("#docLink").val('');
                $("#region").jqxDropDownList('val', 'CORPORATE');
                $("#item_Type").jqxDropDownList('val', 'Planned');
                $("#coordinator").val('');
                $("#developer").val('');
                $("#liveOnTest").val('');
                $("#liveOnProd").val('');
                $("#category").jqxDropDownList('val', '');
                $("#customer").val('');
                $("#priority").val('');
                $("#estimateEffort").val(0);
                $("#realEffort").val(0);
                $("#pendingEffort").val(0);
                $("#restEffort").val(0);
                $("#underEstimatedEffort").val(0);
                $("#item_Comment").val('');
            }
        }
    </script>
</head>
<body>
    <table>
        <tbody>
        <tr>
            <th>Week</th>
            <td>
                <div id="weekList"></div>
            </td>
            <td><input type="button" id="refresh" value="Refresh"/></td>
            <td><input type="button" id="add" value="Add"/></td>
            <td>
                <input type="button" id="upload" value="Upload"/>
                <form id="fileForm" method="POST" action="ProjectList!uploadProjectFile" enctype="multipart/form-data">
                    <input type="file" id="projectFile" name="projectFile" style="display: none"/>
                    <input type="hidden" name="weekStr" value="${weekStr}"/>
                </form>
            </td>
            <td><input type="button" id="print" value="Print"/></td>
            <td><input type="button" id="export" value="Export"/></td>
            <td><input type="button" id="sync" value="Sync"/></td>
            <td><input type="button" id="report" value="Report"/></td>
        </tr>
        </tbody>
    </table>
    <div id="window">
        <div>Add Project</div>
        <div>
            <table>
                <colgroup style="width: 80px"></colgroup>
                <colgroup style="width: 200px"></colgroup>
                <colgroup style="width: 100px"></colgroup>
                <colgroup style="width: 150px"></colgroup>
                <tr>
                    <th>Region</th>
                    <td><div id="region" /></td>
                    <th>Type</th>
                    <td><div id="item_Type" /></td>
                </tr>
                <tr>
                    <th>GSD</th>
                    <td><input id="gsd" /></td>
                    <th>SCRIPT ID</th>
                    <td><input id="scriptId" /></td>
                </tr>
                <tr>
                    <th>Prj. Name</th>
                    <td colspan="3"><input id="projectName" /></td>
                </tr>
                <tr>
                    <th>Est. Effort</th>
                    <td><input id="estimateEffort" /></td>
                    <th>Developer</th>
                    <td><input type="text" id="developer" /></td>
                </tr>
                <tr>
                    <th>Real Effort</th>
                    <td><input id="realEffort" /></td>
                    <th>Pending Effort</th>
                    <td><input id="pendingEffort" /></td>
                </tr>
                <tr>
                    <th>Rest Effort</th>
                    <td><input id="restEffort" /></td>
                    <th>Under Est. Eff.</th>
                    <td><input id="underEstimatedEffort" /></td>
                </tr>
                <tr>
                    <th>Comment</th>
                    <td colspan="3"><textarea id="item_Comment"></textarea></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div id="detailPanel">
                            <div>Details</div>
                            <div>
                                <table>
                                    <colgroup style="width: 100px"></colgroup>
                                    <colgroup style="width: 220px"></colgroup>
                                    <colgroup style="width: 100px"></colgroup>
                                    <colgroup style="width: 120px"></colgroup>
                                    <tr>
                                        <th>DocLink</th>
                                        <td colspan="3"><input id="docLink" /></td>
                                    </tr>
                                    <tr>
                                        <th>Live On Test</th>
                                        <td><input id="liveOnTest" /></td>
                                        <th>Live On Prod</th>
                                        <td><input id="liveOnProd" /></td>
                                    </tr>
                                    <tr>
                                        <th>Category</th>
                                        <td><div id="category" /></td>
                                        <th>Priority</th>
                                        <td><div id="priority" /></td>
                                    </tr>
                                    <tr>
                                        <th>Coordinator</th>
                                        <td><input id="coordinator" /></td>
                                        <th>Customer</th>
                                        <td><input id="customer" /></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" style="text-align: center">
                        <button id="save">Save</button>&nbsp;&nbsp;
                        <button id="cancel">Cancel</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
