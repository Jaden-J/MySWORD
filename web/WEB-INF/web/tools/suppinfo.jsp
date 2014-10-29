<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Support Information</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
        <script type="text/javascript" src="../js/main.js"></script>
		<script type="text/javascript" src="../js/jquery/jquery.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
        <style type="text/css">
            th {
                border:#000000 thin solid;
            }
        </style>
		<script type="text/javascript">
			var theme = "${mysword_theme}";
			$(document).ready(function () {
//                $('#description').jqxInput({ placeHolder: 'Description, it should be the same as the description in archive file.', width: 600, height: 25, theme: theme });
//                $('#instance').jqxDropDownList({ source: [{label:'DEV', value:'dev'}, {label:'INT', value:'int'}, {label:'TEST', value:'test'}, {label:'PROD', value:'prod'}], displayMember:'label', valueMember:'value', selectedIndex: 0,
//                    width: 60, height: 25,dropDownHeight: 95,theme:theme });
//                $('#checkIn, #rollout, #getmap, #version, #clear').jqxButton({width: 70, height:25});
                $('#suppInfoTab').jqxTabs({width: 1200, theme: theme});
                $('#mapName').jqxInput({ placeHolder: 'Map name.', width: 200, height: 25, theme: theme });
                $('#specials').jqxEditor({ height: "400px", width: 800 });
//                $('#logWindow').jqxExpander({width: 1200, showArrow: false, toggleMode: 'none', theme: theme});
                window.frameElement.height = $('body').height() + 20;
			})
		</script>
	</head>
	<body>
        <div id="suppInfoTab">
            <ul>
                <li>New Map</li>
                <li>Map Change</li>
                <li>New Routing Process</li>
                <li>New Mapping Process</li>
                <li>Process Change</li>
                <li>Routing Change</li>
                <li>Agreement Change</li>
            </ul>
            <div>
                <textarea id="specials">
                </textarea>
                <%--<table>--%>
                    <%--<tr>--%>
                        <%--<th>Map name</th>--%>
                        <td><input id="mapName" /></td>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<th>Specials</th>--%>
                        <%--<td><textarea id="specials"></textarea></td>--%>
                    <%--</tr>--%>
                <%--</table>--%>
            </div>
            <div>
                <%--<table>--%>
                    <%--<tr>--%>
                        <%--<th>Map name</th>--%>
                        <%--<td><input id="mapName" /></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<th>Specials</th>--%>
                        <%--<td><textarea id="specials"></textarea></td>--%>
                    <%--</tr>--%>
                <%--</table>--%>
            </div>
            <div></div>
            <div></div>
            <div></div>
            <div></div>
            <div></div>
        </div>
	</body>
</html>