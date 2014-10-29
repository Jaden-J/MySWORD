<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Server Information</title>
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="MySWORD Knowledge Base page">
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
		<script type="text/javascript" src="../js/jquery/jquery.js"></script>
        <script type="text/javascript" src="../js/main.js"></script>
        <script type="text/javascript" src="../js/knowledge_base.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
		<script type="text/javascript">
			var theme = '${mysword_theme}';
	        $(document).ready(function () {
                showServer("dev", theme);
                showServer("int", theme);
                showServer("test", theme);
                showServer("prod", theme);
	        });
	    </script>
	</head>
	<body>
        <div style="width:1200px;">
        <div>
            <h1>Development</h1>
            <div id="dev"></div>
        </div>
        <div>
            <h1>Test</h1>
            <div id="test"></div>
        </div>
        <div>
            <h1>Integration</h1>
            <div id="int"></div>
        </div>
        <div>
            <h1>Production</h1>
            <div id="prod"></div>
        </div>
        </div>
	</body>
</html>