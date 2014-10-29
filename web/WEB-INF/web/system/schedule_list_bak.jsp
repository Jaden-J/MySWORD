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
        $(document).ready(function () {
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
        });

        function showRepeatSchedule(scheduleList) {
            var data = new Array();
            for(var i=0; i<scheduleList.length; i++) {
                if(scheduleList[i].schedule_Type == "Repeat") {
                    data.push(scheduleList[i]);
                }
            }
            var source = { localdata: data, datatype: "array",
//                datafields: [
//                    { name: 'scheduleId', type: 'string' },
//                    { name: 'schedule_Name', type: 'string' },
//                    { name: 'schedule_Service', type: 'string' },
//                    { name: 'schedule_Type', type: 'string' },
//                    { name: 'active', type: 'boolean' },
//                    { name: 'multiple', type: 'boolean' },
//                    { name: 'running', type: 'boolean' },
//                    { name: 'staticMethod', type: 'boolean' },
//                    { name: 'interval', type: 'number' },
//                    { name: 'thread', type: 'number'},
//                    { name: 'run_Time', type: 'string'},
//                    { name: 'month', type: 'string' },
//                    { name: 'day_of_month', type: 'string' },
//                    { name: 'hour', type: 'string' },
//                    { name: 'minute', type: 'string' },
//                    { name: 'lastrundt', type: 'string' },
//                    { name: 'nextRun', type: 'string' },
//                    { name: 'lasteditby', type: 'string' },
//                    { name: 'lasteditdt', type: 'string' }
//                ],
                id: 'scheduleId'
            };
            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadError: function (xhr, status, error) {
                    post("/${mysword_path}/error", "_self", {error: xhr.responseText});
                }
            });
            var height = 70;
            if(data.length > 0) {
                height = null;
            }
            $("#repeatSchedule").jqxDataTable({ height: height, source: dataAdapter, columnsResize: true, enableBrowserSelection: true, altrows: true, theme: theme,
                columns: [
                    { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                        return row + 1;
                    }},
                    { text: 'Action', dataField: 'action', width: 50, cellsRenderer: function (row, column, value, rowData) {
                        return "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(" + row + ")' />&nbsp;"
                                + "<img src='../image/operations/pencil.png' title='Edit this item.' onclick='editItem(" + row + ")' />";
                    }},
                    { text: 'Schedule Id', dataField: 'scheduleId', hidden: true, width: 150 },
                    { text: 'Schedule Name', dataField: 'schedule_Name', width: 200 },
                    { text: 'Service', dataField: 'schedule_Service', width: 350 },
                    { text: 'Type', dataField: 'schedule_Type', hidden: true, width: 70 },
                    { text: 'Multiple', dataField: 'multiple', width: 70 },
                    { text: 'Running', dataField: 'running', width: 70 },
                    { text: 'Static', dataField: 'staticMethod', width: 70 },
                    { text: 'Interval', dataField: 'interval', width: 70 },
                    { text: 'Thread', dataField: 'thread', width: 70 },
                    { text: 'Last Run Date', dataField: 'lastrundt', width: 160 },
                    { text: 'Next Run Date', dataField: 'nextRun', width: 160 },
                    { text: 'Last Edit By', dataField: 'lasteditby', width: 100 },
                    { text: 'Last Edit Date', dataField: 'lasteditdt', width: 160 }
                ]
            });
        }

        function showOnetimeSchedule(scheduleList) {
            var data = new Array();
            for(var i=0; i<scheduleList.length; i++) {
                if(scheduleList[i].schedule_Type == "Onetime") {
                    data.push(scheduleList[i]);
                }
            }
            var source = { localdata: data, datatype: "array",
                datafields: [
                    { name: 'scheduleId', type: 'string' },
                    { name: 'schedule_Name', type: 'string' },
                    { name: 'schedule_Service', type: 'string' },
                    { name: 'schedule_Type', type: 'string' },
                    { name: 'active', type: 'boolean' },
                    { name: 'multiple', type: 'boolean' },
                    { name: 'running', type: 'boolean' },
                    { name: 'staticMethod', type: 'boolean' },
                    { name: 'interval', type: 'number' },
                    { name: 'thread', type: 'number'},
                    { name: 'run_Time', type: 'string'},
                    { name: 'month', type: 'string' },
                    { name: 'day_of_month', type: 'string' },
                    { name: 'hour', type: 'string' },
                    { name: 'minute', type: 'string' },
                    { name: 'lastrundt', type: 'string' },
                    { name: 'nextRun', type: 'string' },
                    { name: 'lasteditby', type: 'string' },
                    { name: 'lasteditdt', type: 'string' }
                ],
                id: 'scheduleId'
            };
            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadError: function (xhr, status, error) {
                    post("/${mysword_path}/error", "_self", {error: xhr.responseText});
                }
            });
            var height = 70;
            if(data.length > 0) {
                height = null;
            }
            $("#onetimeSchedule").jqxDataTable({ height: height, source: dataAdapter, columnsResize: true, enableBrowserSelection: true, altrows: true, theme: theme,
                columns: [
                    { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                        return row + 1;
                    }},
                    { text: 'Action', dataField: 'action', width: 50, cellsRenderer: function (row, column, value, rowData) {
                        return "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(" + row + ")' />&nbsp;"
                                + "<img src='../image/operations/pencil.png' title='Edit this item.' onclick='editItem(" + row + ")' />";
                    }},
                    { text: 'Schedule Id', dataField: 'scheduleId', hidden: false, width: 150 },
                    { text: 'Schedule Name', dataField: 'schedule_Name', width: 200 },
                    { text: 'Service', dataField: 'schedule_Service', width: 350 },
                    { text: 'Type', dataField: 'schedule_Type', width: 70 },
                    { text: 'Multiple', dataField: 'multiple', width: 70 },
                    { text: 'Running', dataField: 'running', width: 70 },
                    { text: 'Static', dataField: 'staticMethod', width: 70 },
                    { text: 'Thread', dataField: 'thread', width: 70 },
                    { text: 'Run Time', dataField: 'run_Time', width: 160 },
                    { text: 'Last Run Date', dataField: 'lastrundt', width: 160 },
                    { text: 'Next Run Date', dataField: 'nextRun', width: 160 },
                    { text: 'Last Edit By', dataField: 'lasteditby', width: 100 },
                    { text: 'Last Edit Date', dataField: 'lasteditdt', width: 160 }
                ]
            });
        }

        function showComplexSchedule(scheduleList) {
            var data = new Array();
            for(var i=0; i<scheduleList.length; i++) {
                if(scheduleList[i].schedule_Type == "Complex") {
                    data.push(scheduleList[i]);
                }
            }
            var source = { localdata: data, datatype: "array",
                datafields: [
                    { name: 'scheduleId', type: 'string' },
                    { name: 'schedule_Name', type: 'string' },
                    { name: 'schedule_Service', type: 'string' },
                    { name: 'schedule_Type', type: 'string' },
                    { name: 'active', type: 'boolean' },
                    { name: 'multiple', type: 'boolean' },
                    { name: 'running', type: 'boolean' },
                    { name: 'staticMethod', type: 'boolean' },
                    { name: 'interval', type: 'number' },
                    { name: 'thread', type: 'number'},
                    { name: 'run_Time', type: 'string'},
                    { name: 'month', type: 'string' },
                    { name: 'day_of_month', type: 'string' },
                    { name: 'hour', type: 'string' },
                    { name: 'minute', type: 'string' },
                    { name: 'lastrundt', type: 'string' },
                    { name: 'nextRun', type: 'string' },
                    { name: 'lasteditby', type: 'string' },
                    { name: 'lasteditdt', type: 'string' }
                ],
                id: 'scheduleId'
            };
            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadError: function (xhr, status, error) {
                    post("/${mysword_path}/error", "_self", {error: xhr.responseText});
                }
            });
            var height = 70;
            if(data.length > 0) {
                height = null;
            }
            $("#complexSchedule").jqxDataTable({ height: height, source: dataAdapter, columnsResize: true, enableBrowserSelection: true, altrows: true, theme: theme,
                columns: [
                    { text: 'No.', dataField: 'index',  width: 30, cellsRenderer: function (row, column, value, rowData) {
                        return row + 1;
                    }},
                    { text: 'Action', dataField: 'action', width: 50, cellsRenderer: function (row, column, value, rowData) {
                        return "<img src='../image/operations/red-cross.png' title='Delete this item.' onclick='deleteItem(" + row + ")' />&nbsp;"
                                + "<img src='../image/operations/pencil.png' title='Edit this item.' onclick='editItem(" + row + ")' />";
                    }},
                    { text: 'No', dataField: 'rowIndex', editable: false, width: 20, cellsRenderer: function (row, column, value, rowData) { rowData.index = row+1; return row+1; }},
                    { text: 'Schedule Id', dataField: 'scheduleId', hidden: false, width: 150 },
                    { text: 'Schedule Name', dataField: 'schedule_Name', width: 200 },
                    { text: 'Service', dataField: 'schedule_Service', width: 350 },
                    { text: 'Type', dataField: 'schedule_Type', width: 70 },
                    { text: 'Multiple', dataField: 'multiple', width: 70 },
                    { text: 'Running', dataField: 'running', width: 70 },
                    { text: 'Static', dataField: 'staticMethod', width: 70 },
                    { text: 'Thread', dataField: 'thread', width: 70 },
                    { text: 'Month', dataField: 'month', width: 120 },
                    { text: 'Date', dataField: 'day_of_month', width: 120 },
                    { text: 'Hour', dataField: 'hour', width: 120 },
                    { text: 'Minute', dataField: 'minute', width: 120 },
                    { text: 'Last Run Date', dataField: 'lastrundt', width: 160 },
                    { text: 'Next Run Date', dataField: 'nextRun', width: 160 },
                    { text: 'Last Edit By', dataField: 'lasteditby', width: 100 },
                    { text: 'Last Edit Date', dataField: 'lasteditdt', width: 160 }
                ]
            });
        }

    </script>
</head>
<body>
<h1>Repeat Schedule</h1>
<div id="repeatSchedule"></div>
<h1>Onetime Schedule</h1>
<div id="onetimeSchedule"></div>
<h1>Complex Schedule</h1>
<div id="complexSchedule"></div>
</body>
</html>