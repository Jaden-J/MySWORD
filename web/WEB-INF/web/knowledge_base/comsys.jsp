<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>MySWORD Comsys Page</title>
		<meta http-equiv="description" content="MySWORD Comsys page">
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
                showComsys("dev", theme);
                showComsys("int", theme);
                showComsys("test", theme);
                showComsys("prod", theme);
                showComsys("sag", theme);
                showComsys("vip", theme);
	        });
	    </script>
	</head>
	<body>
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
        <div>
            <h1>SAG</h1>
            <div id="sag"></div>
        </div>
        <div>
            <h1>VIP</h1>
            <div id="vip"></div>
        </div>
	</body>
</html>