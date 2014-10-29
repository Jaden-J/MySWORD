<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Knowledge Sharing Result</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <script type="text/javascript" src="../js/main.js"></script>
</head>
<body>
<h1>You request has been processed successfully!</h1>
<table>
    <tbody>
        <tr>
            <th>Share ID</th>
            <td>${share_Id}</td>
        </tr>
        <tr>
            <th>Subject</th>
            <td>${knowledgeList[0].subject}</td>
        </tr>
        <tr>
            <th>Poster</th>
            <td>${knowledgeList[0].poster}</td>
        </tr>
    </tbody>
</table>
</body>
</html>