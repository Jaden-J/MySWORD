<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Knowledge Share Home</title>
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link rel="stylesheet" type="text/css" href="../css/knowledge_sharing.css">
    <script type="text/javascript" src="../js/jquery/jquery.js"></script>
    <script type="text/javascript" src="../js/main.js"></script>
    <script type="text/javascript" src="../js/knowledge_sharing.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
    <script type="text/javascript">
        var theme = "${mysword_theme}";
        var filter = {share_Id: "", subject: "", content: "", approval: "", poster: "", startTime: "", endTime: ""};
        $(document).ready(function () {
            initialPage(${mysword_knowledge_sharing_admin});
            initialPosterList();
            showKnowledgeSharingList(${mysword_knowledge_sharing_admin});
        });
    </script>
</head>
<body>
<table>
    <tbody>
    <tr>
        <td class="label">Share ID</td>
        <td><input type="text" id="shareId" style="padding-left: 2px;" name="knowledge.share_Id"/></td>
        <td class="label">Subject</td>
        <td><input type="text" id="subject" style="padding-left: 2px;" name="knowledge.subject"/></td>
        <td class="label">Content</td>
        <td><input type="text" id="content" style="padding-left: 2px;" name="knowledge.content"/></td>
        <td class="label">Start Time</td>
        <td>
            <div id="startTime"></div>
        </td>
    </tr>
    <tr>
        <td class="label">Post By</td>
        <td>
            <div id="poster"></div>
        </td>
        <td class="label">Approval</td>
        <td>
            <div id="approval"></div>
        </td>
        <td></td>
        <td></td>
        <td class="label">End Time</td>
        <td>
            <div id="endTime"></div>
        </td>
    </tr>
    <tr>
        <td colspan="8" style="text-align: right">
            <input type="submit" id="search" value="Search"/>
            <img id="loading" style="display: none" src="../image/operations/loading.gif"/>
        </td>
    </tr>
    </tbody>
</table>
<hr/>
<br/>
<div id="table"></div>
<div id="itemTable"></div>
</body>
</html>