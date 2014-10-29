<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Knowledge Share Home</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
    <script type="text/javascript" src="../js/jquery/jquery.js"></script>
    <script type="text/javascript" src="../js/main.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
    <style type="text/css">
        img {
            width: 17px;
            height: 17px;
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">
        var theme = "${mysword_theme}";
        var scheduleType;
        var scheduleId;
        var tableSelectionIdex;
        $(document).ready(function () {
            $('#window').jqxWindow({width: 670, height: 570, isModal: true, autoOpen: false, theme: theme});
            $('#window').on('close', function (event){initWindowData()});
            $('#schedule_Type').jqxDropDownList({ source: ['Repeat','Onetime','Complex'], selectedIndex: 0, width: 100, height: 25, theme: theme});
            $("#schedule_Name").jqxInput({ placeHolder: "Schedule name.", width: 500, height: 25, theme: theme });
            $("#schedule_Service").jqxInput({ placeHolder: "Package.folder:function.", width: 500, height: 25, theme: theme });
            $('#multiple').jqxCheckBox({width:30});
            $('#multiple').jqxCheckBox('val', false);
            $('#staticMethod').jqxCheckBox({width:30});
            $('#staticMethod').jqxCheckBox('val', false);
            $("#interval").jqxInput({ placeHolder: "Interval in seconds.", width: 500, height: 25, theme: theme });
            $("#run_Time").jqxInput({ placeHolder: "Run time 'yyyy-MM-dd HH:mm:ss", width: 500, height: 25, theme: theme });
            $("#month").jqxListBox({ source: ['JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC'], multiple: true, width: 100, height: 280, theme: theme });
            $("#day_of_month").jqxListBox({ multiple: true, width: 100, height: 280, theme: theme });
            $("#hour").jqxListBox({ multiple: true, width: 100, height: 280, theme: theme });
            $("#minute").jqxListBox({ multiple: true, width: 100, height: 280, theme: theme });
            for(var i=0; i<60; i++) {
                if(i>0 && i<=31) {
                    $("#day_of_month").jqxListBox('addItem', i);
                }
                if(i<24) {
                    $("#hour").jqxListBox('addItem', i);
                }
                $("#minute").jqxListBox('addItem', i);
            }
            $('#monday').jqxCheckBox({width:30});
            $('#monday').jqxCheckBox('val', false);
            $('#tuesday').jqxCheckBox({width:30});
            $('#tuesday').jqxCheckBox('val', false);
            $('#wednesday').jqxCheckBox({width:30});
            $('#wednesday').jqxCheckBox('val', false);
            $('#thursday').jqxCheckBox({width:30});
            $('#thursday').jqxCheckBox('val', false);
            $('#friday').jqxCheckBox({width:30});
            $('#friday').jqxCheckBox('val', false);
            $('#saturday').jqxCheckBox({width:30});
            $('#saturday').jqxCheckBox('val', false);
            $('#sunday').jqxCheckBox({width:30});
            $('#sunday').jqxCheckBox('val', false);
            $('#save, #cancel, #new').jqxButton({width: 70, height: 27, theme: theme}).on('click', function(event){
               switch(this.id){
                   case 'save':
                       var url;
                       if(this.value == "Save") {
                           url="Schedule!updateSchedule";
                       } else if(this.value == "New") {
                           url="Schedule!createSchedule";
                       }
                       var day_of_week = new Array();
                       if($('#monday').jqxCheckBox('val')){
                           day_of_week.push('MON');
                       }
                       if($('#tuesday').jqxCheckBox('val')){
                           day_of_week.push('TUE');
                       }
                       if($('#wednesday').jqxCheckBox('val')){
                           day_of_week.push('WED');
                       }
                       if($('#thursday').jqxCheckBox('val')){
                           day_of_week.push('THU');
                       }
                       if($('#friday').jqxCheckBox('val')){
                           day_of_week.push('FRI');
                       }
                       if($('#saturday').jqxCheckBox('val')){
                           day_of_week.push('SAT');
                       }
                       if($('#sunday').jqxCheckBox('val')){
                           day_of_week.push('SUN');
                       }
                       $.ajax({async: false, type: "post", url: url, dataType: "json",
                           data: {
                               'scheduleList[0].scheduleId': scheduleId,
                               'scheduleList[0].schedule_Type': $('#schedule_Type').jqxDropDownList('val'),
                               'scheduleList[0].schedule_Name': $("#schedule_Name").jqxInput('val'),
                               'scheduleList[0].schedule_Service': $("#schedule_Service").jqxInput('val'),
                               'scheduleList[0].active': false,
                               'scheduleList[0].multiple': $('#multiple').jqxCheckBox('val'),
                               'scheduleList[0].staticMethod': $('#staticMethod').jqxCheckBox('val'),
                               'scheduleList[0].schedule_Interval': $("#interval").jqxInput('val'),
                               'scheduleList[0].run_Time': $("#run_Time").jqxInput('val'),
                               'scheduleList[0].month_of_year': $("#month").jqxListBox('val'),
                               'scheduleList[0].day_of_month': $("#day_of_month").jqxListBox('val'),
                               'scheduleList[0].hour_of_day': $("#hour").jqxListBox('val'),
                               'scheduleList[0].minute_of_hour': $("#minute").jqxListBox('val'),
                               'scheduleList[0].day_of_week': day_of_week.join(",")
                           },
                           complete: function (response, returnType) {
                               if (response.responseJSON == null || response.responseJSON == "") {
                                   post("/${mysword_path}/error", "_self", {error: response.responseText});
                               } else {
                                   var divId;
                                   if(response.responseJSON.scheduleList[0].schedule_Type == "Repeat") {
                                       divId="repeatSchedule";
                                   } else if(response.responseJSON.scheduleList[0].schedule_Type == "Onetime") {
                                       divId="onetimeSchedule";
                                   } else if(response.responseJSON.scheduleList[0].schedule_Type == "Complex") {
                                       divId="complexSchedule";
                                   }
                                   if (this.url == "Schedule!updateSchedule") {
                                       if(scheduleType == response.responseJSON.scheduleList[0].schedule_Type) {
                                           $("#" + divId).jqxDataTable('updateRow', tableSelectionIdex, response.responseJSON.scheduleList[0]);
                                       } else {
                                           $("#" + divId).jqxDataTable('addRow', response.responseJSON.scheduleList[0].scheduleId, response.responseJSON.scheduleList[0]);
                                           $("#" + divId).jqxDataTable('height', null);
                                           if(scheduleType == "Repeat") {
                                               divId="repeatSchedule";
                                           } else if(scheduleType == "Onetime") {
                                               divId="onetimeSchedule";
                                           } else if(scheduleType == "Complex") {
                                               divId="complexSchedule";
                                           }
                                           $("#" + divId).jqxDataTable('deleteRow', parseInt(tableSelectionIdex));
                                           if ($("#" + divId).jqxDataTable('getRows').length == 0) {
                                               $("#" + divId).jqxDataTable('height', 70);
                                           }
                                       }
                                   } else if (this.url == "Schedule!createSchedule") {
                                       $("#" + divId).jqxDataTable('addRow', response.responseJSON.scheduleList[0].scheduleId, response.responseJSON.scheduleList[0]);
                                       $("#" + divId).jqxDataTable('height', null);
                                   }
                               }
                           }
                       });
                       $('#window').jqxWindow('close');
//                       initWindowData();
                       resetWindowHeight();
                       break;
                   case 'cancel':
                       $('#window').jqxWindow('close');
//                       initWindowData();
                       break;
                   case 'new':
                       $('#save').val('New');
                       $('#window').jqxWindow('open');
                       break;
               }
            });
            var scheduleList;
            $.ajax({async: false, type: "post", url: "Schedule!listSchedules", dataType: "json",
                complete: function (response, returnType) {
                    if(response.responseJSON == null || response.responseJSON == "") {
                        post("/${mysword_path}/error", "_self", {error: response.responseText});
                    } else {
                        scheduleList = response.responseJSON.scheduleList;
                    }
                }
            });
            showRepeatSchedule(scheduleList);
            showOnetimeSchedule(scheduleList);
            showComplexSchedule(scheduleList);
            resetWindowHeight();
        });
//        function resetWindowHeight(){
//            if(window.frameElement.height > 600) {
//                window.frameElement.height = $('body').height() + 20;
//            } else {
//                window.frameElement.height = 600;
//            }
//        }
        function showRepeatSchedule(scheduleList) {
            var data = new Array();
            for(var i=0; i<scheduleList.length; i++) {
                if(scheduleList[i].schedule_Type == "Repeat") {
                    data.push(scheduleList[i]);
                }
            }
            var source = { localdata: data, datatype: "array", id: 'scheduleId'};
            var dataAdapter = new $.jqx.dataAdapter(source);
            var height = 70;
            if(data.length > 0) {
                height = null;
            }
            $("#repeatSchedule").jqxDataTable({ height: height, source: dataAdapter, columnsResize: true, enableBrowserSelection: true, altrows: true, theme: theme,
                columns: [
                    { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                        return row + 1;
                    }},
                    { text: 'Action', dataField: 'active', width: 50, cellsRenderer: function (row, column, value, rowData) {
                        var resultDiv = "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(\"repeatSchedule\",\"" + row + "\")' />";
                        if(value) {
                            resultDiv += "<img src='../image/operations/lightbulb_on.png' title='Suspend this item.' onclick='switchItem(\"repeatSchedule\",\"" + row + "\","+!value+")' />";
                        } else {
                            resultDiv += "<img src='../image/operations/lightbulb_off.png' title='Active this item.' onclick='switchItem(\"repeatSchedule\",\"" + row + "\","+!value+")' />";
                        }
                        return resultDiv
                    }},
                    { text: 'Schedule Id', dataField: 'scheduleId', hidden: true, width: 150 },
                    { text: 'Schedule Name', dataField: 'schedule_Name', width: 230 },
                    { text: 'Service', dataField: 'schedule_Service', width: 350 },
                    { text: 'Type', dataField: 'schedule_Type', hidden: true, width: 70 },
                    { text: 'Multiple', dataField: 'multiple', hidden: true, width: 70 },
                    { text: 'Running', dataField: 'running', width: 70 },
                    { text: 'Static', dataField: 'staticMethod', hidden: true, width: 70 },
                    { text: 'Interval', dataField: 'schedule_Interval', width: 70 },
                    { text: 'Thread', dataField: 'thread', width: 70 },
                    { text: 'Last Run Date', dataField: 'lastrundt', hidden: true, width: 160 },
                    { text: 'Next Run Date', dataField: 'nextRun', width: 160 },
                    { text: 'Last Edit By', dataField: 'lasteditby', hidden: true, width: 100 },
                    { text: 'Last Edit Date', dataField: 'lasteditdt', hidden: true, width: 160 }
                ]
            });
            $("#repeatSchedule").on('rowDoubleClick', function (event){
                $('#save').val('Save');
                scheduleType = event.args.row.schedule_Type;
                tableSelectionIdex = event.args.index;
                initWindowData(event.args.row);
                $('#window').jqxWindow('open');
            });
            resetWindowHeight();
        }
        function showOnetimeSchedule(scheduleList) {
            var data = new Array();
            for(var i=0; i<scheduleList.length; i++) {
                if(scheduleList[i].schedule_Type == "Onetime") {
                    data.push(scheduleList[i]);
                }
            }
            var source = { localdata: data, datatype: "array",id: 'scheduleId'};
            var dataAdapter = new $.jqx.dataAdapter(source);
            var height = 70;
            if(data.length > 0) {
                height = null;
            }
            $("#onetimeSchedule").jqxDataTable({ height: height, source: dataAdapter, columnsResize: true, enableBrowserSelection: true, altrows: true, theme: theme,
                columns: [
                    { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                        return row + 1;
                    }},
                    { text: 'Action', dataField: 'active', width: 50, cellsRenderer: function (row, column, value, rowData) {
                        var resultDiv = "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(\"onetimeSchedule\",\"" + row + "\")' />";
                        if(value) {
                            resultDiv += "<img src='../image/operations/lightbulb_on.png' title='Suspend this item.' onclick='switchItem(\"onetimeSchedule\",\"" + row + "\","+!value+")' />";
                        } else {
                            resultDiv += "<img src='../image/operations/lightbulb_off.png' title='Active this item.' onclick='switchItem(\"onetimeSchedule\",\"" + row + "\","+!value+")' />";
                        }
                        return resultDiv
                    }},
                    { text: 'Schedule Id', dataField: 'scheduleId', hidden: true, width: 150 },
                    { text: 'Schedule Name', dataField: 'schedule_Name', width: 230 },
                    { text: 'Service', dataField: 'schedule_Service', width: 350 },
                    { text: 'Type', dataField: 'schedule_Type', hidden: true, width: 70 },
                    { text: 'Multiple', dataField: 'multiple', hidden: true, width: 70 },
                    { text: 'Running', dataField: 'running', width: 70 },
                    { text: 'Static', dataField: 'staticMethod', hidden: true, width: 70 },
                    { text: 'Thread', dataField: 'thread', hidden: true, width: 70 },
                    { text: 'Run Time', dataField: 'run_Time', width: 160 },
                    { text: 'Last Run Date', dataField: 'lastrundt', hidden: true, width: 160 },
                    { text: 'Next Run Date', dataField: 'nextRun', width: 160 },
                    { text: 'Last Edit By', dataField: 'lasteditby', hidden: true, width: 100 },
                    { text: 'Last Edit Date', dataField: 'lasteditdt', hidden: true, width: 160 }
                ]
            });
            $("#onetimeSchedule").on('rowDoubleClick', function (event){
                $('#save').val('Save');
                scheduleType = event.args.row.schedule_Type;
                tableSelectionIdex = event.args.index;
                initWindowData(event.args.row);
                $('#window').jqxWindow('open');
            });
            resetWindowHeight();
        }
        function showComplexSchedule(scheduleList) {
            var data = new Array();
            for(var i=0; i<scheduleList.length; i++) {
                if(scheduleList[i].schedule_Type == "Complex") {
                    data.push(scheduleList[i]);
                }
            }
            var source = { localdata: data, datatype: "array",id: 'scheduleId'};
            var dataAdapter = new $.jqx.dataAdapter(source);
            var height = 70;
            if(data.length > 0) {
                height = null;
            }
            $("#complexSchedule").jqxDataTable({ height: height, source: dataAdapter, columnsResize: true, enableBrowserSelection: true, altrows: true, theme: theme,
                columns: [
                    { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                        return row + 1;
                    }},
                    { text: 'Action', dataField: 'active', width: 50, cellsRenderer: function (row, column, value, rowData) {
                        var resultDiv = "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(\"complexSchedule\",\"" + row + "\")' />";
                        if(value) {
                            resultDiv += "<img src='../image/operations/lightbulb_on.png' title='Suspend this item.' onclick='switchItem(\"complexSchedule\",\"" + row + "\","+!value+")' />";
                        } else {
                            resultDiv += "<img src='../image/operations/lightbulb_off.png' title='Active this item.' onclick='switchItem(\"complexSchedule\",\"" + row + "\","+!value+")' />";
                        }
                        return resultDiv
                    }},
                    { text: 'Schedule Id', dataField: 'scheduleId', hidden: true, width: 150 },
                    { text: 'Schedule Name', dataField: 'schedule_Name', width: 230 },
                    { text: 'Service', dataField: 'schedule_Service', width: 350 },
                    { text: 'Type', dataField: 'schedule_Type', hidden: true, width: 70 },
                    { text: 'Multiple', dataField: 'multiple', hidden: true, width: 70 },
                    { text: 'Running', dataField: 'running', width: 70 },
                    { text: 'Static', dataField: 'staticMethod', hidden: true, width: 70 },
                    { text: 'Thread', dataField: 'thread', width: 70 },
                    { text: 'Month', dataField: 'month_of_year', width: 70 },
                    { text: 'Date', dataField: 'day_of_month', width: 70 },
                    { text: 'Hour', dataField: 'hour_of_day', width: 70 },
                    { text: 'Minute', dataField: 'minute_of_hour', width: 70 },
                    { text: 'Week Day', dataField: 'day_of_week', width: 70 },
                    { text: 'Last Run Date', dataField: 'lastrundt', hidden: true, width: 160 },
                    { text: 'Next Run Date', dataField: 'nextRun', width: 160 },
                    { text: 'Last Edit By', dataField: 'lasteditby', hidden: true, width: 100 },
                    { text: 'Last Edit Date', dataField: 'lasteditdt', hidden: true, width: 160 }
                ]
            });
            $("#complexSchedule").on('rowDoubleClick', function (event){
                $('#save').val('Save');
                scheduleType = event.args.row.schedule_Type;
                tableSelectionIdex = event.args.index;
                initWindowData(event.args.row);
                $('#window').jqxWindow('open');
            });
            resetWindowHeight();
        }
        function switchItem(id, rowIndex, value) {
            tableSelectionIdex = rowIndex;
            $('#'+id).jqxDataTable('clearSelection');
            $('#'+id).jqxDataTable('selectRow', parseInt(rowIndex));
            var data = $('#'+id).jqxDataTable('getSelection');
            if(confirm('Do you want to suspend this item?')) {
                $.ajax({async: false, type: "post", url: "Schedule!switchSchedule", dataType: "json",
                    data: {
                        'scheduleList[0].scheduleId': data[0].scheduleId,
                        'scheduleList[0].schedule_Type': data[0].schedule_Type,
                        'scheduleList[0].schedule_Name': data[0].schedule_Name,
                        'scheduleList[0].schedule_Service': data[0].schedule_Service,
                        'scheduleList[0].active': value,
                        'scheduleList[0].multiple': data[0].multiple,
                        'scheduleList[0].staticMethod': data[0].staticMethod,
                        'scheduleList[0].schedule_Interval': data[0].schedule_Interval,
                        'scheduleList[0].run_Time': data[0].run_Time,
                        'scheduleList[0].month_of_year': data[0].month_of_year,
                        'scheduleList[0].day_of_month': data[0].day_of_month,
                        'scheduleList[0].hour_of_day': data[0].hour_of_day,
                        'scheduleList[0].minute_of_hour': data[0].minute_of_hour,
                        'scheduleList[0].day_of_week': data[0].day_of_week
                    },
                    complete: function (response, returnType) {
                        if (response.responseJSON == null || response.responseJSON == "") {
                            post("/${mysword_path}/error", "_self", {error: response.responseText});
                        } else {
                            $("#" + id).jqxDataTable('updateRow', parseInt(rowIndex), response.responseJSON.scheduleList[0]);
//                            $("#" + id).jqxDataTable('setCellValue', parseInt(rowIndex), 'active', value);
                        }
                    }
                });
            }
        }
        function deleteItem(id, rowIndex) {
            tableSelectionIdex = rowIndex;
            $('#'+id).jqxDataTable('clearSelection');
            $('#'+id).jqxDataTable('selectRow', parseInt(rowIndex));
            var data = $('#'+id).jqxDataTable('getSelection');
            if(confirm('Do you want to delete this item?')) {
                $.ajax({async: false, type: "post", url: "Schedule!deleteSchedule", dataType: "json", data: {'scheduleList[0].scheduleId': data[0].scheduleId},
                    complete: function (response, returnType) {
                        if (response.responseJSON == null || response.responseJSON == "") {
                            post("/${mysword_path}/error", "_self", {error: response.responseText});
                        } else {
                            $("#" + id).jqxDataTable('deleteRow', parseInt(rowIndex));
                            if($("#" + id).jqxDataTable('getRows').length == 0) {
                                $("#" + id).jqxDataTable('height', 70);
                            }
                        }
                    }
                });
            }
        }
        function initWindowData(rowData) {
            if(rowData != null) {
                scheduleId = rowData.scheduleId;
                $('#window').jqxWindow('open');
                $('#schedule_Type').jqxDropDownList('val',rowData.schedule_Type);
                $("#schedule_Name").jqxInput('val',rowData.schedule_Name);
                $("#schedule_Service").jqxInput('val',rowData.schedule_Service);
                $('#multiple').jqxCheckBox('val', rowData.multiple);
                $('#staticMethod').jqxCheckBox('val', rowData.staticMethod);
                $("#interval").jqxInput('val',rowData.schedule_Interval);
                $("#run_Time").jqxInput('val',rowData.run_Time);
                if(rowData.month_of_year != null) {
                    var monthArray = rowData.month_of_year.replace(/(\*|\?)/g, "").split(',');
                    for(var i=0; i<monthArray.length; i++) {
                        $("#month").jqxListBox('selectItem',monthArray[i]);
                    }
                }
                if(rowData.day_of_month != null) {
                    var dayArray = rowData.day_of_month.replace(/(\*|\?)/g, "").split(',');
                    for(var i=0; i<dayArray.length; i++) {
                        $("#day_of_month").jqxListBox('selectItem',parseInt(dayArray[i]));
                    }
                }
                if(rowData.hour_of_day != null) {
                    var hourArray = rowData.hour_of_day.replace(/(\*|\?)/g, "").split(',');
                    for(var i=0; i<hourArray.length; i++) {
                        $("#hour").jqxListBox('selectItem',parseInt(hourArray[i]));
                    }
                }
                if(rowData.minute_of_hour != null) {
                    var minuteArray = rowData.minute_of_hour.replace(/(\*|\?)/g, "").split(',');
                    for(var i=0; i<minuteArray.length; i++) {
                        $("#minute").jqxListBox('selectItem',parseInt(minuteArray[i]));
                    }
                }
                if(rowData.day_of_week != null) {
                    var weekArray = rowData.day_of_week.replace(/(\*|\?)/g, "").split(',');
                    for(var i=0; i<weekArray.length; i++) {
                        switch(weekArray[i]) {
                            case "MON":
                                $('#monday').jqxCheckBox('val',true);
                                break;
                            case "TUE":
                                $('#tuesday').jqxCheckBox('val',true);
                                break;
                            case "WED":
                                $('#wednesday').jqxCheckBox('val',true);
                                break;
                            case "THU":
                                $('#thursday').jqxCheckBox('val',true);
                                break;
                            case "FRI":
                                $('#friday').jqxCheckBox('val',true);
                                break;
                            case "SAT":
                                $('#saturday').jqxCheckBox('val',true);
                                break;
                            case "SUN":
                                $('#sunday').jqxCheckBox('val',true);
                                break;
                        }
                    }
                }
            } else {
                scheduleId = "";
                $('#schedule_Type').jqxDropDownList('val','Repeat');
                $("#schedule_Name").jqxInput('val','');
                $("#schedule_Service").jqxInput('val','');
                $('#multiple').jqxCheckBox('val', false);
                $('#staticMethod').jqxCheckBox('val', false);
                $("#interval").jqxInput('val','');
                $("#run_Time").jqxInput('val','');
                $("#month").jqxListBox('clearSelection');
                $("#day_of_month").jqxListBox('clearSelection');
                $("#hour").jqxListBox('clearSelection');
                $("#minute").jqxListBox('clearSelection');
                $('#monday').jqxCheckBox('val', false);
                $('#tuesday').jqxCheckBox('val', false);
                $('#wednesday').jqxCheckBox('val', false);
                $('#thursday').jqxCheckBox('val', false);
                $('#friday').jqxCheckBox('val', false);
                $('#saturday').jqxCheckBox('val', false);
                $('#sunday').jqxCheckBox('val', false);
            }
        }
    </script>
</head>
<body>
<div id="window">
    <div>Schedule Detail Window</div>
    <div>
        <table>
            <tr>
                <th>Type</th>
                <td colspan="7"><div id="schedule_Type"/></td>
            </tr>
            <tr>
                <th>Name</th>
                <td colspan="7"><input id="schedule_Name"/></td>
            </tr>
            <tr>
                <th>Service</th>
                <td colspan="7"><input id="schedule_Service"/></td>
            </tr>
            <tr>
                <th>Multiple</th>
                <td><div id="multiple"/></td>
                <th>Static</th>
                <td><div id="staticMethod"/></td>
                <th></th>
                <td></td>
            </tr>
            <tr>
                <th>Interval</th>
                <td colspan="7"><input id="interval"/></td>
            </tr>
            <tr>
                <th>Runtime</th>
                <td colspan="7"><input id="run_Time"/></td>
            </tr>
            <tr>
                <th>Month</th>
                <td><div id="month"/></td>
                <th>Day</th>
                <td><div id="day_of_month"/></td>
                <th>Hour</th>
                <td><div id="hour"/></td>
                <th>Minute</th>
                <td><div id="minute"/></td>
            </tr>
                <th>Week</th>
                <td colspan="7">
                    <table>
                        <tr>
                            <th>MON</th>
                            <td><div id="monday"/></td>
                            <th>TUE</th>
                            <td><div id="tuesday"/></td>
                            <th>WED</th>
                            <td><div id="wednesday"/></td>
                            <th>THU</th>
                            <td><div id="thursday"/></td>
                            <th>FRI</th>
                            <td><div id="friday"/></td>
                            <th>SAT</th>
                            <td><div id="saturday"/></td>
                            <th>SUN</th>
                            <td><div id="sunday"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <button id="save">Save</button>&nbsp;
                    <button id="cancel">Cancel</button>
                </td>
            </tr>
        </table>
    </div>
</div>
<div><button id="new">New</button></div>
<h1>Repeat Schedule</h1>
<div id="repeatSchedule"></div>
<h1>Onetime Schedule</h1>
<div id="onetimeSchedule"></div>
<h1>Complex Schedule</h1>
<div id="complexSchedule"></div>
</body>
</html>