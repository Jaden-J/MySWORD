<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>MySWORD Project Cards</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="description" content="MySWORD Project Cards Page">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/jqwidgets/jqx.base.css">
    <link rel="stylesheet" type="text/css" href="css/jqwidgets/jqx.${mysword_theme}.css">
    <script type="text/javascript" src="js/main.js"></script>
    <script type="text/javascript" src="js/jquery/jquery.js"></script>
    <script type="text/javascript" src="js/jqwidgets/jqx-all.js"></script>
    <script type="text/javascript">
        var theme = "${mysword_theme}";
        var records;
        $(document).ready(function(){
            $('#print').jqxButton({ width: 70, theme: theme}).on('click', function (event) {
                printCards();
            });
            $('#selectAll').jqxButton({ width: 70, theme: theme}).on('click', function (event) {
                $("#listBox").jqxListBox('checkAll');
            });
            $('#remove').jqxButton({ width: 70, theme: theme}).on('click', function (event) {
                var items = $("#listBox").jqxListBox('getCheckedItems');
                for(var i=0; i<items.length; i++) {
                    $("#listBox").jqxListBox('removeItem', items[i]);
                }
            });
            var source = {
                url:"project/ProjectList!listByWeek",
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
            $("#listBox").jqxListBox({ allowDrop: true, allowDrag: true, source: dataAdapter, displayMember: 'GSD', valueMember:'itemId', checkboxes:true, width: 130, height: 600, theme: theme});
        });

        function printCards() {
            $('#printArea').html('');
            var items = $("#listBox").jqxListBox('getCheckedItems');
            for(var i=0; i<items.length; i++) {
                for(var j=0; j<records.length; j++) {
                    if (items[i].value == records[j].itemId) {
                        var newDiv = $('<div>');
                        if(records[j].region == "APAC") {
                            newDiv.css('background-color', '#808080');
                        }
                        newDiv.addClass('projectCard').html("[" + (i+1) + "][${weekStr}]" + records[j].scriptId + "/#" + records[j].GSD + ": " + records[j].projectName + "<br/>Con: " + records[j].coordinator +
                                ", Effort: " + records[j].estimateEffort + ", OnQA:" + records[j].liveOnTest + ", OnPROD: " + records[j].liveOnProd);
                        $('#cardArea').append(newDiv);
                    }
                }
            }
            var newWindow = window.open("Print Window", "_blank");
            newWindow.document.write($('#printArea').html());
            newWindow.document.close();
            newWindow.print();
            newWindow.close();
        }
    </script>
</head>
<body>
<table>
    <tr>
        <td style="vertical-align: top;">
            <button id="print">Print</button>&nbsp;
            <button id="selectAll">Select All</button>&nbsp;
            <button id="remove">Remove</button>
            <div id="listBox"></div>
        </td>
        <td style="vertical-align: top;">
            <div id="printArea">
                <style type="text/css">
                    .projectCard {
                        width: 320px;
                        height: 270px;
                        border: solid black thin;
                        margin: 5px;
                        display: inline-block;
                        font-family: Arial;
                        font-size: 25px;
                        font-weight: bold;
                        overflow: hidden;
                        word-break:break-all;
                    }
                </style>
                <div style="width: 1200px">
                    <div id="cardArea"></div>
                </div>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
