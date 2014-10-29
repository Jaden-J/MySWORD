<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>How To Files</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="MySWORD How To page">
		<link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" type="text/css" href="../css/knowledge_base.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
		<script type="text/javascript" src="../js/jquery/jquery.js"></script>
        <script type="text/javascript" src="../js/main.js"></script>
        <script type="text/javascript" src="../js/knowledge_base.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
		<script type="text/javascript">
            var theme = "${mysword_theme}";
            $(document).ready(function () {
                showHowTo(theme);
                resetWindowHeight();
            });
        </script>
	</head>
	<body>
		<div id="dataTable"></div>
	</body>
</html>