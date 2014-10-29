<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Base64 Tool</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${sessionScope.mysword_theme}.css">
        <script type="text/javascript" src="../js/main.js"></script>
		<script type="text/javascript" src="../js/jquery/jquery.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
		<style type="text/css">
			textarea {
				width: 1200px;
				height: 240px;
			}
			.running_img {
				width: 20px;
				height: 20px;
				display: none;
				float: left;
			}
		</style>
		<script type="text/javascript">
			var theme = "${sessionScope.mysword_theme}";
			$(document).ready(function () {
                $('#inputContent').jqxTooltip({ content: 'If you want to encode the special characters, e.g. Chinese, German... Please use switch on UTF-8.', position: 'mouse', name:'tooltips', theme: theme });
				$('#utf8').jqxSwitchButton({ height: 27, width: 81, theme: theme, checked: false, theme: theme });
				$('#utf8').jqxTooltip({ content: 'Encode or Decode with encoding UTF-8.', position: 'mouse', name:'tooltips', theme: theme });
				$('#encode, #decode').jqxButton({width: 70, height: 30, theme: theme}).on('click', function(elem){
                    $("#running").css("display", "block");
                    $('#encode, #decode').jqxButton({disabled: true});
                    $('#utf8').jqxSwitchButton({disabled: true});
					var url = "";
					if(elem.target.id == "encode") {
						url = "Base64!encode";
					} else if(elem.target.id == "decode") {
						url = "Base64!decode";
					} else {
						return;
					};
					$.ajax({
						type : 'post',
						url : url,
						data : {
							'inputContent': $('#inputContent').val(),
                            'utf8': $('#utf8').jqxSwitchButton('val')
						},
						dataType: 'json',
						complete: function(response, returnType) {
                            if(response.responseJSON == null || response.responseJSON == "") {
                                post("/${sessionScope.mysword_path}/error", "_self", {error: response.responseText});
                            } else {
								$('#outputContent').val(response.responseJSON.outputContent);
					        }
							$("#running").css("display", "none");
							$('#encode, #decode').jqxButton({disabled: false});
                            $('#utf8').jqxSwitchButton({disabled: false});
						}
					});
				});
			})
		</script>
	</head>
	<body>
		<table>
			<tbody>
				<tr>
					<td colspan="5"><textarea id="inputContent"></textarea></td>
				</tr>
				<tr>
					<td style="width: 70px">
						<input type="button" id="encode" value="Encode"/>
                    </td>
                    <td style="width: 70px">
						<input type="button" id="decode" value="Decode"/>
                    </td>
                    <td style="width: 95px; padding-left: 10px;">
                        UTF-8 Encoding:
                    </td>
                    <td style="width: 70px;">
						<div id="utf8"></div>
                    </td>
                    <td>
						<img class="running_img" id="running" src="../image/operations/spinner.gif"/>
					</td>
				</tr>
				<tr>
					<td colspan="5"><textarea id="outputContent" wrap="off"></textarea></td>
				</tr>
			</tbody>
		</table>
	</body>
</html>