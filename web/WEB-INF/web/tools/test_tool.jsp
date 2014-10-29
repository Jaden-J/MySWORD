<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Test Tool</title>
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
		<link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${sessionScope.mysword_theme}.css">
        <link rel="stylesheet" type="text/css" href="../css/main.css">
        <script type="text/javascript" src="../js/main.js"></script>
		<script type="text/javascript" src="../js/jquery/jquery.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
		<script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
		<link rel="stylesheet" href="../css/jquery/jquery-ui.css" type="text/css" />
		<link rel="stylesheet" href="../css/plupload/css/jquery.ui.plupload.css" type="text/css" />
		<script type="text/javascript" src="../js/jquery/jquery-ui.js"></script>
		<script type="text/javascript" src="../js/plupload/plupload.full.min.js"></script>
		<script type="text/javascript" src="../js/plupload/jquery.ui.plupload.min.js"></script>
		<style type="text/css">
			textarea {
				margin-left: 2px;
				padding: 2px;
				border-style: solid;
			    border-width: 1px;
			    font-size: 13px;
			    margin: 0;
			    background-clip: padding-box;
			    border-color: #8E8E97;
			    outline: medium none;
			    font-style: normal;
			    border-radius: 3px;
			}
			.checkboxDiv {
				display: inline-block;
				width: 320px;
			}
			.filenameDiv {
				display: inline-block;
				width: 300px;
				text-overflow: ellipsis;
				-o-text-overflow: ellipsis;
				overflow: hidden;
			}
			.error {
				color: red;
			}
			.info, .error{
				margin-top: 5px;
				margin-bottom: 5px;
			}
			.running_img {
				width: 20px;
				height: 20px;
				display: none;
				float: left;
			}
		</style>
		<script type="text/javascript">
			var theme = '${sessionScope.mysword_theme}';
			var instance = [
                {html: "Comsys", value: "dev-agr-1", title: "INTERNALGW", group: "Development" },
                {html: "Comsys2", value: "dev-agr-2", title: "INTERD0002(UTF-8)", group: "Development" },
                {html: "Comsys3", value: "dev-agr-3", title: "INTERD0003", group: "Development" },
                {html: "Comsys0002", value: "int-agr-1", title: "INTERI0002", group: "INT" },
                {html: "Comsystest", value: "int-agr-2", title: "COMINT01", group: "INT" },
                {html: "Comsys", value: "test-agr-1", title: "INTERNALGW", group: "TEST" },
                {html: "Comsys2", value: "test-agr-2", title: "INTERD0002(UTF-8)", group: "TEST" },
                {html: "Comsys3", value: "test-agr-3", title: "INTERD0003", group: "TEST" },
                {html: "Comsys", value: "prod-agr-1", title: "INTERNALGW", group: "Production" },
                {html: "Comsys2", value: "prod-agr-2", title: "INTERP0002", group: "Production" },
                {html: "Comsys2", value: "prod-agr-2", title: "INTERP0002", group: "Production" },
                {html: "Comsys3", value: "prod-agr-3", title: "INTERP0003", group: "Production" },
                {html: "Comsys4", value: "prod-agr-4", title: "INTERP0004(UTF-8)", group: "Production" },
                {html: "Comsys5", value: "prod-agr-5", title: "INTERP0005", group: "Production" },
                {html: "Comsys6", value: "prod-agr-6", title: "INTERP0006", group: "Production" },
                {html: "Comsys2001", value: "sag-agr-1", title: "INTERP2001", group: "SAG" },
                {html: "Comsys2002", value: "sag-agr-2", title: "INTERP2002", group: "SAG" },
                {html: "Comsys1001", value: "vip-agr-1", title: "INTERP1001(UTF-8)", group: "VIP" },
                {html: "Comsys1002", value: "vip-agr-2", title: "INTERP1002(UTF-8)", group: "VIP" }
			];
			$(document).ready(function () {
                $('#window').jqxWindow({width: 500, height: 600, isModal: true, autoOpen: false, theme: theme});
				$('#uploadFileTab').jqxTabs({width: 1200, theme: theme});
				$('#filename, #keyId, #uploadPath, #subject, #srcString, #dstString').jqxInput({ width: 200, height: 25, theme: theme });
				$('#newline').jqxDropDownList({ source: ['LF', 'CR', 'CRLF', 'No'], selectedIndex: 0, width: 60, height: 25, dropDownHeight: 105, theme: theme });
				$('#encoding').jqxDropDownList({ source: ['ANSI/ASCII', 'UTF-8', 'UTF-8 NO BOM', 'Unicode'], selectedIndex: 0, width: 135, height: 25, dropDownHeight: 105, theme: theme });
				$('#keyId').on('change', function (obj){if(obj.currentTarget.value.length >= 10){getFTPFiles(obj.currentTarget.value);}});
				$('#instance').jqxDropDownList({ source: instance, displayMember: 'html', valueMember: 'value', selectedIndex: 0, width: 130, height: 25, dropDownHeight: 250, theme: theme });
				$('#instance').on('select', function (event) {$('#instanceDetail').html(event.args.item.group + '  ' + event.args.item.title);});
				$('#logWindow').jqxExpander({width: 1200, showArrow: false, toggleMode: 'none', theme: theme});
				$('#email, #create, #demo, #upload, #clear, #logs, #run, #reload, #replace, #refresh').jqxButton({ width: 70, height: 25, theme: theme }).on('click', function(elem){
					if(elem.target.id == "clear") {
						$('#logPanel').html("");
						return;
					}
					ftpToolAction(elem.target.id);
				});
	            $("#uploader").plupload({
			        runtimes: 'html5,flash,silverlight,html4',
			        url: "../TestTool!uploadFile",
			        max_file_size : '50mb',
			        chunk_size: '1mb',
			 		file_data_name: 'uploadFile',
			 		multipart_params: {filename: "", keyId: "", uploadPath: ""},
			        resize : {
			            width : 100,
			            height : 100,
			            quality : 90,
			            crop: true
			        },
			        rename: true,
			        sortable: true,
			        dragdrop: true,
			        views: {
			            list: true,
			            thumbs: true,
			            active: 'thumbs'
			        },
			        flash_swf_url: 'http://rawgithub.com/moxiecode/moxie/master/bin/flash/Moxie.cdn.swf',
			        silverlight_xap_url: 'http://rawgithub.com/moxiecode/moxie/master/bin/silverlight/Moxie.cdn.xap',
			        init: {
			        	BeforeUpload: function(up, file){
			        		this.settings.multipart_params.filename=file.name;
			        		this.settings.multipart_params.keyId=$('#keyId').val().substr(0,10);
			        		this.settings.multipart_params.uploadPath=$('#uploadPath').val();
			        		if($('#keyId').val() == "" && $('#uploadPath').val() == "") {
			        			addLog('Please input the ProcessID or Upload Path!', 'error');
			        			this.stop();
			        		}
			        	},
			        	FileUploaded: function(up, file, info) {
			        		addLog($.parseJSON(info.response).resultString.replace(/\\r\\n|\\n/g, "\n").replace(/\\\//g, "\\").replace(/^"|"$/g, ""), "info");
			            },
			            Error: function(up, args) {
			                addLog(args.response, "error");
			            },
			            UploadComplete: function() {
			        		//getFTPFiles();
			            }
			        }
			    });
			    $('#filename').jqxTooltip({ content: 'The filename that will be stored in FTP server, please follow the filename rule in windows!', position: 'mouse', theme: theme });
				$('#newline').jqxTooltip({ content: '<div style="text-align:left">Line separator:<br/>CR → Mac: \\r<br/>LF → Unix: \\n<br/>CRLF → Dos: \\r\\n</div>', position: 'mouse', theme: theme });
				$('#encoding').jqxTooltip({ content: '<div style="text-align:left">The encoding you will use to upload the file. "NO BOM(Byte Order Mark)":encoding with no signature,<br/>we will not write the \\ufeff to the beginning of the file.</div>', position: 'mouse', theme: theme });
				$('#keyId').jqxTooltip({ content: 'Please input the processId or AgreementId.', position: 'mouse', theme: theme });
				$('#instance').jqxTooltip({ content: 'Comsys instance id, seperated by DEV/INT/TEST/PROD/SAG/VIP', position: 'mouse', theme: theme });
				$('#uploadPath').jqxTooltip({ content: 'Any path after login using account "customer"', position: 'mouse', theme: theme });
				$('#email').jqxTooltip({ content: '<div style="text-align:left">Send the file to DEV using email detection. The subject will be choosed as below:<br/>1.Manually input subject.<br/>2.ProcessID.</div>', position: 'mouse', theme: theme });
				$('#create').jqxTooltip({ content: '<div style="text-align:left">Craete the folders as below:<br/>dev\/&lt;Customer Code&gt;\/inbound\/&lt;ProcessId&gt;<br/>dev\/&lt;Customer Code&gt;\/inbound\/&lt;ProcessId&gt;/testfiles<br/>dev/&lt;Customer Code&gt;/outbound/&lt;ProcessId&gt;</div>', position: 'mouse', theme: theme });
				$('#demo').jqxTooltip({ content: 'Send the file to DEV using Receive Demo detection.', position: 'mouse', theme: theme });
				$('#upload').jqxTooltip({ content: '<div style="text-align:left">Upload the file to Masstest FTP by sequence as below:<br/>1.Manually input Upload Path.<br/>2.dev\/&lt;Customer Code&gt;\/inbound\/&lt;ProcessId&gt;<br/>3.dev\/&lt;Customer Code&gt;\/inbound\/&lt;ProcessId, 5, 10&gt;</div>', position: 'mouse', theme: theme });
				$('#run').jqxTooltip({ content: 'Run agreement. Please choose the right Instance.', position: 'mouse', theme: theme });
	            $('#reload').jqxTooltip({ content: 'Reload agreement. Please choose the right Instance.', position: 'mouse', theme: theme });
	            $('#refresh').jqxTooltip({ content: 'Force the system to refresh the "File In Masstest" tab.', position: 'mouse', theme: theme });
	            $('#clear').jqxTooltip({ content: 'Clear the LOG panel.', position: 'mouse', theme: theme });
	           	$('#replace').jqxTooltip({ content: 'Replace the string in content.', position: 'mouse', theme: theme });
	           	$('#srcString').jqxTooltip({ content: 'The string you want to be replaced.\\n as the newline.', position: 'mouse', theme: theme });
	           	$('#dstString').jqxTooltip({ content: 'The string you want to replace.\\n as the newline.', position: 'mouse', theme: theme });
	           	$('#subject').jqxTooltip({ content: 'Input the email subject. This field will be used when you are using email detection.', position: 'mouse', theme: theme });
			});
			
			function ftpToolAction(type) {
                var reg=/.{10}(_inbound|_outbound)/;
                var reg_instance=/^(prod|sag|vip)/;
				if((type == "create" || type == "demo") && $("#keyId").val().length < 10) {
					addLog('Please input the correct ProcessID!', 'error');
					return;
				} else if (type == "upload" && $("#keyId").val().length < 10 && $("#uploadPath").val() == "") {
					addLog('Please input the correct ProcessID or specify the Upload Path!', 'error');
					return;
				} else if (type == "email" && $("#keyId").val().length < 10 && $("#subject").val() == "") {
					addLog('Please input the correct ProcessID or specify the Subject!', 'error');
					return;
				} else if ((type == "run" || type == "reload") && !reg.test($('#keyId').val())) {
                    addLog("KeyID must end with '_inbound' or '_outbound' and has 10 chars before '_'.", "error");
                    return;
                }
                if((type == "run" || type == "reload") && reg_instance.test($('#instance').jqxDropDownList('val'))) {
                    $('#window').jqxWindow('open');
                    if(!confirm('Do you really want to run/reload the PROD/SAG/VIP agreement?')) {
                        return;
                    }
                }
				if( type=='run' ) {
					url = 'TestTool!runAgreement';
				} else if( type=='reload' ) {
					url = 'TestTool!reloadAgreement';
				} else if( type=='create' ){
					url = 'TestTool!createFolder';
				} else if( type=='demo' ){
					url = 'TestTool!receiveDemo';
				} else if( type=='upload' ){
					url = 'TestTool!uploadFile';
				} else if( type=='email' ){
					url = 'TestTool!emailDetection';
				} else if( type=='refresh' ){
					getFTPFiles();
					return;
				} else if( type=='replace' ){
					if($('#srcString').val() == "") {
						addLog("Please input the replace string!!!");
						return;
					}
                    $.ajax({
                        type : 'post',
                        url : 'TestTool!replaceString',
                        data : {
                            'srcString': $('#srcString').val(),
                            'dstString': $('#dstString').val(),
                            'content': $('#content').val()
                        },
                        dataType: 'json',
                        complete: function(response, returnType) {
                            if(response.responseJSON == null || response.responseJSON == "") {
                                addLog(response.responseText.replace(/\\r\\n|\\n/g, "\n").replace(/\\\//g, "\\").replace(/^"|"$/g, ""), "error");
                            } else {
                                addLog('Replace string finished!');
                                $('#content').val(response.responseJSON.content);
                            }
                        }
                    });
					return;
				} else {
					return;
				}
				$('#email, #create, #demo, #upload, #clear, #logs, #run, #reload, #replace, #refresh').jqxButton({disabled: true});
				$('#keyId').jqxInput({disabled: true});
				$("#running").css("display", "inline-block");
				var fileListObj = $("input[name='ftpFileList']");
				var fileArray = new Array();
				if(fileListObj != "" || fileListObj != null) {
					for(var i=0; i<fileListObj.length; i++) {
						if(fileListObj[i].checked) {
							fileArray.push({'filename': fileListObj[i].value});
						}
					}
				}
				$.ajax({
					type : 'post',
					url : url,
					data : {
						'keyId': $('#keyId').val(),
						'instance': $('#instance').jqxDropDownList('val'),
						'uploadPath': $('#uploadPath').val(),
						'encoding': $('#encoding').jqxDropDownList('val'),
						'subject': $('#subject').val(),
						'ftpFileList': fileArray,
						'filename': $('#filename').val(),
						'newline': $('#newline').jqxDropDownList('val'),
						'content': $('#content').val()
					},
					dataType: 'json',
					beforeSend: function(XHR) {
						this.data = this.data.replace(/\%5Bfilename\%5D/g, ".filename");
					},
					complete: function(response, returnType) {
						if(response.responseJSON == null || response.responseJSON == "") {
							addLog(response.responseText.replace(/\\r\\n|\\n/g, "\n").replace(/\\\//g, "\\").replace(/^"|"$/g, ""), "error");
						} else {
							addLog(response.responseJSON.resultString.replace(/\\r\\n|\\n/g, "\n").replace(/\\\//g, "\\").replace(/^"|"$/g, ""));
				        	if($('#filename').val()!="" && !isFileExisting($('#filename').val())) {
				        		$('#filenamePanel').append($('<div>').addClass('checkboxDiv').append($('<input type="checkbox" name="ftpFileList"/>').val($('#filename').val())).append($('<div>').addClass('filenameDiv').html($('#filename').val())));
				        	}
				        }
						$("#running").css("display", "none");
						$('#email, #create, #demo, #upload, #clear, #logs, #run, #reload, #replace, #refresh').jqxButton({disabled: false});
						$('#keyId').jqxInput({disabled: false});
					}
				});
			}
			
			function isFileExisting(filename) {
				var obj = $('input[value="' + filename + '"]');
				if(obj != null && $('input[value="' + filename + '"]').attr('name') == "ftpFileList") {
					return true;
				} else {
					return false;
				}
			}
			
			function getFTPFiles() {
				if($("#keyId").val() == "" || $("#keyId").val().length < 10) {
					addLog('Please input the correct KeyID!', 'error');
					return;
				}
				$("#gettingFiles").css("display", "inline-block");
				$.ajax({
					type : 'post',
					url : 'TestTool!ftpFileList',
					data : {
						'keyId' : $("#keyId").val()
					},
					dataType : 'json',
					complete: function(response, returnType) {
						if(response.responseJSON == null || response.responseJSON == "") {
							addLog(response.responseText.replace(/\\r\\n|\\n/g, "\n").replace(/\\\//g, "\\").replace(/^"|"$/g, ""));
						} else {
							addLog(response.responseJSON.resultString.replace(/\\r\\n|\\n/g, "\n").replace(/\\\//g, "\\").replace(/^"|"$/g, ""));
							$('#filenamePanel').html("");
							var fileList = response.responseJSON.ftpFileList;
							for(var i=0; i<fileList.length; i++) {
								$('#filenamePanel').append($('<div>').addClass('checkboxDiv').append($('<input type="checkbox" name="ftpFileList"/>').val(fileList[i].filename)).append($('<div>').addClass('filenameDiv').html(fileList[i].filename)));
							}
						}
						$("#gettingFiles").css("display", "none");
					}
				});
			}
			
			function addLog(message, type) {
				var date = new Date();
				var divClass = "info";
				if(type == "error") {
					divClass="error";
				}
				$('#logPanel').html("<div class='" + divClass + "'>["+date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"] " + message + "</div><hr/>" + $('#logPanel').html());
                resetWindowHeight();
			}
		</script>
	</head>
	<body>
        <div id="window">
            <div>Confirmation</div>
            <div>
                Please confirm the below information before system run:
                <table>
                    <tr>
                        <th>Agreement Name</th>
                        <td><div id="agreementIdDiv"></div></td>
                    </tr>
                    <tr>
                        <th>Instance</th>
                        <td><div id="instanceDiv"></div></td>
                    </tr>
                    <tr>
                        <th>System</th>
                        <td><div id="systemDiv"></div></td>
                    </tr>
                </table>
            </div>
        </div>
		<table>
			<tbody>
				<tr>
					<th width=50>KeyID</th>
					<td width=100><input type="text" id="keyId" name="keyId" style="padding-left: 2px;"/></td>
					<th width=60>Instance</th>
					<td width=100><div id="instance"></div></td>
					<td><div id="instanceDetail">Development INTERNALGW</div></td>
					<th>Upload Path</th>
					<td><input type="text" id="uploadPath" style="padding-left: 2px;"/></td>
					<th>Email Subject</th>
					<td><input type="text" id="subject" style="padding-left: 2px;"/></td>
				</tr>
			</tbody>
		</table>
		<div id="uploadFileTab">
			<ul>
				<li>Manually Input</li>
				<li>Local File</li>
				<li>File In Masstest<img id="gettingFiles" class="running_img" src="../image/operations/spinner.gif"/></li>
			</ul>
			<div>
				<table>
					<tbody>
						<tr>
							<th>Filename</th>
							<td><input id="filename" name="filename" style="padding-left: 2px;"/></td>
							<th>Newline</th>
							<td><div id="newline"></div></td>
							<th>Encoding</th>
							<td><div id="encoding"></div></td>
							<td><input id="srcString" name="srcString" style="padding-left: 2px;"/>&gt;&gt;<input id="dstString" name="dstString" style="padding-left: 2px;"/></td>
							<td><input type="button" id="replace" value="Replace" /></td>
						</tr>
					</tbody>
				</table>
				<textarea id="content" style="width: 1190px; height: 260px" wrap="off"></textarea>
			</div>
			<div>
				<div id="uploader">
					<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
				</div>
			</div>
			<div>
				<div id="filenamePanel" style="height: 300px"></div>
			</div>
		</div>
		<table>
			<tbody>
				<tr>
					<td>
						<input type="button" id="email" value="Email" />
					</td>
					<td>
						<input type="button" id="create" value="Create" />
					</td>
					<td>
						<input type="button" id="demo" value="Demo" />
					</td>
					<td>
						<input type="button" id="upload" value="Upload" />
					</td>
					<td>
						<input type="button" id="run" value="Run" />
					</td>
					<td>
						<input type="button" id="reload" value="Reload" />
					</td>
					<td>
						<input type="button" id="refresh" value="Refresh" />
					</td>
					<td>
						<input type="button" id="clear" value="Clear" />
					</td>
					<td>
						<span><img id="running" class="running_img" src="../image/operations/spinner.gif"/></span>
					</td>
				</tr>
			</tbody>
		</table>
		<hr/>
		<div id="logWindow">
			<div>LOG</div>
			<div><pre id="logPanel" style="padding: 2px"></pre></div>
		</div>
	</body>
</html>