<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>MySWORD Home</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="MySWORD home page">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/jqwidgets/jqx.base.css">
		<link rel="stylesheet" type="text/css" href="css/jqwidgets/jqx.${mysword_theme}.css">
        <style type="text/css">
            #userDiv{
                font-size: 12px;
            }
            #userDiv ul{
                display: inline-block;
                margin: 0px 0px 0px 0px;
                padding: 0px 0px 0px 0px;
                list-style-type: circle;
            }
            #userDiv li{
                display: inline-block;
                margin: 0px 5px 0px 5px;
            }
        </style>
		<script type="text/javascript" src="js/jquery/jquery.js"></script>
		<script type="text/javascript" src="js/jqwidgets/jqx-all.js"></script>
		<script type="text/javascript" src="js/jqwidgets/globalization/globalize.js"></script>
		<script type="text/javascript">
            var theme = "${mysword_theme}";
			var navigatorItems;
            var preIds = new Array();
            $(document).ready(function () {
                var source =
                {
					url: 'Navigator!navigatorDetail',
					type: 'POST',
                    datatype: "json",
                    datafields: [
                        { name: 'id' },
                        { name: 'parentId' },
                        { name: 'label' },
                        { name: 'value' },
                        { name: 'container' },
                        { name: 'html' },
                        { name: 'subMenuWidth' }
                    ],
	                id: 'id',
                    root: 'navigator',
					async: false
                };
                var dataAdapter = new $.jqx.dataAdapter(source);
                dataAdapter.dataBind();
                navigatorItems = dataAdapter.records;
                var records = dataAdapter.getRecordsHierarchy('id', 'parentId', 'items');
                $('#navigator').jqxMenu({source: records, width: 700, height: 30, showTopLevelArrows: true, theme: theme});
                $("#navigator").on('itemclick', function (event) {
               		for(var i=0; i<navigatorItems.length; i++) {
           				if(navigatorItems[i].id == event.args.id) {
                            if (navigatorItems[i].value != "") {
                                for(var k=0; k<navigatorItems.length; k++) {
                                    if(navigatorItems[k].id == -1) {
                                        $('#'+navigatorItems[k].id).css('background-color', '').css('text-shadow', 'rgb(240, 240, 240) 0px 1px 0px');
                                    }
                                    $('#'+navigatorItems[k].id).css('background-color', '');
                                }
                                for(var j=0; j<navigatorItems.length; j++) {
                                    if(navigatorItems[j].id == navigatorItems[i].parentId || navigatorItems[j].id == navigatorItems[i].id) {
                                        $('#'+navigatorItems[j].id).css('background-color', '#ADD8E6');
                                        $('#'+navigatorItems[j].id).css('text-shadow', 'rgb(240, 240, 240) 0px 0px 0px');
                                        $('#'+navigatorItems[i].id).css('background-color', '#ADD8E6');
                                        $('#'+navigatorItems[i].id).css('text-shadow', 'rgb(240, 240, 240) 0px 0px 0px');
                                        break;
                                    }
                                }
                                window.open(navigatorItems[i].value, navigatorItems[i].container);
                                return;
                            }
               			}
               		}
                });
            });
        </script>
	</head>
	<body style="width: 1350px">
		<table style="border-collapse:collapse;">
			<tbody>
				<tr>
					<td style="vertical-align:bottom"><div id='navigator'></div></td>
                    <td>
                        <div id="userDiv">
                            Username:&nbsp;<a href="system/Profile!showProfile" target="main">${mysword_username}</a>&nbsp;(${mysword_region})
                            <br/>
                            Roles:
                            <ul>
                                <li>${mysword_sysadmin?"◆SysAdmin":""}</li>
                                <li>${mysword_developer?"◆Developer":""}</li>
                                <li>${mysword_support?"◆Support":""}</li>
                                <li>${mysword_knowledge_sharing_admin?"◆KS Admin":""}</li>
                                <li>${mysword_knowledge_sharing_user?"◆KS User":""}</li>
                            </ul>
                        </div>
                    </td>
				</tr>
			</tbody>
		</table>
        <iframe id="main" width="100%" frameborder="0" name="main" scrolling="no" src="Home!home"></iframe>
	</body>
</html>
