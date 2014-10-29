<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Map Tool</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
        <script type="text/javascript" src="../js/main.js"></script>
		<script type="text/javascript" src="../js/jquery/jquery.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
        <style type="text/css">
            #development, #integration, #test, #production {
                width: 100%;
                font-family: "DB Office",Arial;
                border-collapse: collapse;
                border-spacing: 0px;
            }
            #development td,
            #integration td,
            #test td,
            #production td {
                vertical-align: top;
                border-top: solid thin #000000;
                border-bottom: solid thin #000000;
            }
            #development td:nth-child(3),
            #integration td:nth-child(3),
            #test td:nth-child(3),
            #production td:nth-child(3) {
                width: 140px;
            }
            .running_img {
                width: 20px;
                height: 20px;
                display: none;
            }
        </style>
		<script type="text/javascript">
			var theme="${mysword_theme}";
            var actionType="";
            var reversionIndex=-1;
			$(document).ready(function () {
                $('#mapName').jqxInput({ placeHolder: 'Map name.', width: 200, height: 25, theme: theme });
                $('#description').jqxInput({ placeHolder: 'Description, it should be the same as the description in archive file.', width: 600, height: 25, theme: theme });
                $('#instance').jqxDropDownList({ source: [{label:'DEV', value:'dev'}, {label:'INT', value:'int'}, {label:'TEST', value:'test'}, {label:'PROD', value:'prod'}, {label:'VIP', value:'vip'}, {label:'SAG', value:'sag'}], displayMember:'label', valueMember:'value', selectedIndex: 0,
                    width: 60, height: 25,dropDownHeight: 155,theme:theme });
                $('#checkIn, #rollout, #getmap, #version, #clear').jqxButton({width: 70, height:25, theme: theme}).on('click', function(event) {
                    var url="";
                    if(this.id == "checkIn") {
                        url="MapTool!checkInMap";
                        if($('#mapName').val()=="" || $('#description').val()=="") {
                            addLog("Map name and description should not be empty!", "error");
                            return;
                        }
                    } else if(this.id == "rollout") {
                        url="MapTool!rolloutMap";
                        if($('#mapName').val()=="") {
                            addLog("Map name should not be empty!", "error");
                            return;
                        }
                        if(reversionIndex==-1){
                            addLog("Please choose the revision you want to deploy!", "error");
                            return;
                        }
                    } else if(this.id == "getmap") {
                        url="MapTool!runGetmap";
                        if($('#mapName').val()=="") {
                            addLog("Map name should not be empty!", "error");
                            return;
                        }
                    } else if(this.id == "version") {
                        url="MapTool!listRevisions";
                        if($('#mapName').val()=="") {
                            addLog("Map name should not be empty!", "error");
                            return;
                        }
                        resetRevisionLogs();
                    } else if(this.id=="clear") {
                        $('#logPanel').html("");
                        return;
                    } else {
                        return;
                    }
                    $('#checkIn, #rollout, #getmap, #version, #clear').jqxButton({disabled: true});
                    $("#running").css("display", "inline-block");
                    $.ajax({
                        type : 'post',
                        url : url,
                        data : {
                            'mapName': $('#mapName').val(),
                            'description': $('#description').val(),
                            'instance': $('#instance').val(),
                            'versionIndex': reversionIndex
                        },
                        dataType: 'json',
                        complete: function(response, returnType) {
                            if(response.responseJSON == null || response.responseJSON == "") {
                                <%--post("/${sessionScope.mysword_path}/error", "_self", {error: response.responseText});--%>
                                addLog(response.responseText, 'error');
                            } else {
                                addLog(response.responseJSON.logs);
                                if(this.url=="MapTool!listRevisions"){
                                    var dev=response.responseJSON.revisions.DEV;
                                    if(dev!=null||dev!="") {
                                        for (var i = 0; i < dev.length; i++) {
                                            $("#development").append($("<tr>").append($("<td>").append($("<div id='version" + (i + 1) + "'>"))).append($("<td>").html(dev[i].revision)).append($("<td>").html(dev[i].date)).append($("<td>").html(dev[i].message)));
                                            $("#version" + (i + 1)).jqxRadioButton({ width: 25, groupName: 'revision'});
                                            $("#version" + (i + 1)).on('checked', function (event) {
                                                reversionIndex = parseInt(this.id.replace("version", ""));
                                            });
                                        }
                                    }
                                    var int=response.responseJSON.revisions.INT;
                                    if(int!=null||int!="") {
                                        for (var i = 0; i < int.length; i++) {
                                            $("#integration").append($("<tr>").append($("<td>").html(i + 1)).append($("<td>").html(int[i].revision)).append($("<td>").html(int[i].date)).append($("<td>").html(int[i].message)));
                                        }
                                    }
                                    var test=response.responseJSON.revisions.TEST;
                                    if(test!=null||test!=""){
                                        for(var i=0;i<test.length;i++){
                                            $("#test").append($("<tr>").append($("<td>").html(i+1)).append($("<td>").html(test[i].revision)).append($("<td>").html(test[i].date)).append($("<td>").html(test[i].message)));
                                        }
                                    }
                                    var prod=response.responseJSON.revisions.PROD;
                                    if(prod!=null||prod!="") {
                                        for (var i = 0; i < prod.length; i++) {
                                            $("#production").append($("<tr>").append($("<td>").html(i + 1)).append($("<td>").html(prod[i].revision)).append($("<td>").html(prod[i].date)).append($("<td>").html(prod[i].message)));
                                        }
                                    }
                                }
                            }
                            $('#checkIn, #rollout, #getmap, #version, #clear').jqxButton({disabled: false});
                            $("#running").css("display", "none");
                            resetWindowHeight();
                        }
                    });
                });
                $('#versionList').jqxTabs({width: 1200, height:300, theme: theme});
                $('#logWindow').jqxExpander({width: 1200, showArrow: false, toggleMode: 'none', theme: theme});
                resetWindowHeight();
			})

            function addLog(message, type) {
                var date = new Date();
                var divClass = "info";
                if(type == "error") {
                    divClass="error";
                }
                $('#logPanel').html("<div class='" + divClass + "'>["+date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"] " + message + "</div><hr/>" + $('#logPanel').html());
                resetWindowHeight();
            }

            function resetRevisionLogs(){
                $('#development,#integration,#test,#production').html("<colgroup style='width:30px'></colgroup><colgroup style='width:60px'></colgroup><colgroup style='width:140px'></colgroup><tr><th>No.</th><th>Revision</th><th>Date</th><th>Description</th></tr>");
            }
		</script>
	</head>
	<body>
        <table id='actionTable'>
            <tr>
                <td style="font-weight: bold">Map Name:<input id="mapName" /></td>
                <td><div id="instance" /></td>
                <td style="font-weight: bold">Description:<input id="description" /></td>
            </tr>
            <tr>
                <td colspan="3">
                    <button id="checkIn">CheckIn</button>
                    <button id="version">Revision</button>
                    <button id="rollout">Rollout</button>
                    <button id="getmap">Get Map</button>
                    <button id="clear">Clear</button>
                    <img id="running" class="running_img" src="../image/operations/spinner.gif"/>
                </td>
            </tr>
        </table>
        <div id="versionList">
            <ul>
                <li>Development</li>
                <li>Integration</li>
                <li>Test</li>
                <li>Production</li>
            </ul>
            <div>
                <dev style="width:1000px;height:300px; overflow:scroll;">
                    <table id="development"></table>
                </dev>
            </div>
            <div>
                <table id="integration"></table>
            </div>
            <div>
                <table id="test"></table>
            </div>
            <div>
                <table id="production"></table>
            </div>
        </div>
        <br/>
        <div id="logWindow">
            <div>LOG</div>
            <div><pre id="logPanel" style="padding: 2px"></pre></div>
        </div>
	</body>
</html>