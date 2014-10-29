<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Regular Tool</title>
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
    <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${sessionScope.mysword_theme}.css">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <script type="text/javascript" src="../js/jquery/jquery.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
    <script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
    <style type="text/css">
        #regData {
            width: 1000px;
            height: 250px;
            font-size: 20px;
        }

        #regResult {
            width: 1000px;
            font-size: 20px;
        }

        #regStr {
            width: 500px;
            height: 25px;
            font-size: 16px;
        }

        .matchResult {
            color: red;
            background-color: powderblue;
        }

        /*#regResult {*/
            /*height: 60px;*/
        /*}*/
    </style>
    <script type="text/javascript">
        var theme = '${sessionScope.mysword_theme}';
        $(document).ready(function () {
            $('#testBt').jqxButton({ width: 60, height: 30, theme: theme }).on('click', function(){regTest();});
            $('#regStr').jqxTooltip({ content: 'Test the regular expression.', position: 'mouse', theme: theme });
            $('#testBt').jqxTooltip({ content: 'Input the regular expression that you want to test!', position: 'mouse', theme: theme });
            $('#regData').jqxTooltip({ content: 'Input the data you want to use the regular expression test!', position: 'mouse', theme: theme });
            resetWindowHeight();
        })
        function regTest() {
            var regStr = $('#regStr').val();
            if (regStr == '') {
                $('#regResult').html(tagEncode($('#regData').val()).replace(/\n/g, '<br/>'));
                window.frameElement.height = $('body').height() + 20;
                return;
            }
            var regExp = new RegExp(regStr, 'g');
            var resultArray = new Array();
            var matchResult;
            var testData = $('#regData').val();
            while ((matchResult = regExp.exec(testData)) != null) {
                resultArray.push({
                    start: matchResult.index,
                    end: regExp.lastIndex,
                    value: testData.substring(matchResult.index,
                            regExp.lastIndex)
                });
            }
            var resultData = "";
            if (resultArray.length == 0) {
                resultData = tagEncode(testData);
            } else {
                for (var i = 0; i < resultArray.length; i++) {
                    resultData = resultData
                            + tagEncode(testData.substring((i == 0 ? 0 : resultArray[i
                                    - 1].end), resultArray[i].start))
                            + '<span class="matchResult">'
                            + tagEncode(resultArray[i].value)
                            + '</span>'
                            + ((i == resultArray.length - 1) ? testData
                                    .substr(resultArray[i].end) : '');
                }
            }
            $("#regResult").html(resultData.replace(/\n/g, '<br/>'));
            resetWindowHeight();
        }
        function tagEncode(str) {
            if (str == null || str == '') {
                return str;
            }
            str = str.replace(/</g, '&lt;');
            str = str.replace(/>/g, '&gt;');
            return str;
        }
    </script>
</head>
<body>
    <form>
        <table>
            <tr>
                <td><textarea id="regData" wrap="off"></textarea></td>
            </tr>
            <tr>
                <td>
                    <input id="regStr" type="text"/>&nbsp;
                    <input type="button" id="testBt" value="Test" />
                </td>
            </tr>
        </table>
    </form>
    <hr/>
    <pre id="regResult"></pre>
</body>
</html>