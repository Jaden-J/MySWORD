<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>MySWORD Project Cards</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="description" content="MySWORD Project Cards Page">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
    <script type="text/javascript" src="../js/main.js"></script>
    <script type="text/javascript" src="../js/jquery/jquery.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
    <style type="text/css">
        .projectCard {
            width: 350px;
            height: 300px;
            border: solid black medium;
            margin: 5px;
            padding: 2px;
            display: inline-block;
            font-family: "DB Office",Arial;
            font-size: 25px;
            font-weight: bold;
            overflow: hidden;
            word-break:break-all;
        }
    </style>
    <script type="text/javascript">
        var theme = "${mysword_theme}";
        var records;
        $(document).ready(function(){
            $('#print').jqxButton({ width: 80, height: 27, theme: theme}).on('click', function (event) {
                printCards();
            });
            $('#selectAll').jqxButton({ width: 80, height: 27, theme: theme}).on('click', function (event) {
                $("#listBox").jqxListBox('checkAll');
            });
            $('#unselectAll').jqxButton({ width: 80, height: 27, theme: theme}).on('click', function (event) {
                $("#listBox").jqxListBox('uncheckAll');
            });
            var source = {
                url:"ProjectList!listByWeek",
                type:"post",
                data: {weekStr:'${weekStr}'},//"${weekStr}",
                async: false,
                datatype: "json",
                datafields: [
                    { name: 'itemId', type: "string" },
                    { name: 'lockId', type: "string" },
                    { name: 'week', type: "string"},
                    { name: 'region', type: "string" },
                    { name: 'type', type: "string" },
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
                    { name: 'maps', type: "string" },
                    { name: 'estimateEffort', type: "number" },
                    { name: 'realEffort', type: "number" },
                    { name: 'restEffort', type: "number" },
                    { name: 'pendingEffort', type: "number" },
                    { name: 'underEstimatedEffort', type: "number" },
                    { name: 'comment', type: "string" },
                    { name: 'lastEditDT', type: "string" },
                    { name: 'lastEditBy', type: "string" }
                ],
                id: 'itemId'
            };
            var dataAdapter = new $.jqx.dataAdapter(source, {
                autoBind: true,
                loadError: function (xhr, status, error) {
                    post("/${mysword_path}/error", "_self", {error: xhr.responseText});
                }
            });
            records = dataAdapter.records;
            $("#listBox").jqxListBox({ allowDrop: true, allowDrag: true, source: dataAdapter, displayMember: 'scriptId', valueMember:'itemId', checkboxes:true, width: 250, height: 600, theme: theme,
                renderer: function (index, label, value) {
                    for(var i=0; i<records.length; i++) {
                        if(records[i].itemId == value) {
                            if(records[i].region == "APAC"){
                                return "<div style='background-color:#C7C7C7'>"+records[i].scriptId+"/#"+records[i].GSD+"</div>";
                            } else {
                                return records[i].scriptId+"/#"+records[i].GSD;
                            }
                        }
                    }
                }
            });
            $("#listBox").on('dragEnd', function (event) {
                $("#listBox").jqxListBox('checkItem', event.args.value );
            });
        });

        function printCards() {
            $('#cardArea').html('');
            var items = $("#listBox").jqxListBox('getCheckedItems');
            var data = new Array();
            for(var i=0; i<items.length; i++) {
                data[items.length-1-i] = items[i].value;
                for(var j=0; j<records.length; j++) {
                    if (items[i].value == records[j].itemId) {
                        var newDiv = $('<div>');
                        if(records[j].region == "APAC") {
                            newDiv.css('background-color', '#C7C7C7');
                            newDiv.css('color', '#000000');
                        }
                        newDiv.addClass('projectCard').html("[" + (i+1) + "][W${fn:substring(weekStr, 5, 7)}]" + records[j].scriptId + "/#" + records[j].GSD + ": " + records[j].projectName + "<br/>Con: " + records[j].coordinator +
                                ", Effort: "+records[j].estimateEffort+", Maps: "+records[j].maps+", OnQA:" + records[j].liveOnTest + ", OnPROD: " + records[j].liveOnProd);
                        $('#cardArea').append(newDiv);
                    }
                }
            }
            post("ProjectList!generateProjectCardFile", "_blank", {"weekStr": "${weekStr}","itemIds":data.join(";")});
        }
    </script>
</head>
<body>
<table>
    <tr>
        <td style="vertical-align: top;width: 250px">
            <button id="print">Print</button>
            <button id="selectAll">SelectAll</button>
            <button id="unselectAll">UnselectAll</button>
            <div id="listBox"></div>
        </td>
        <td style="vertical-align: top;">
            <div id="printArea">
                <div style="width: 800px">
                    <div id="cardArea"></div>
                </div>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
