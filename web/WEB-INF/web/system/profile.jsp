<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>MySWORD Profile List Page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="MySWORD Profile List Page">
		<link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.base.css">
        <link rel="stylesheet" type="text/css" href="../css/jqwidgets/jqx.${mysword_theme}.css">
        <script type="text/javascript" src="../js/main.js"></script>
        <script type="text/javascript" src="../js/jquery/jquery.js"></script>
        <script type="text/javascript" src="../js/jqwidgets/jqx-all.js"></script>
        <script type="text/javascript" src="../js/jqwidgets/globalization/globalize.js"></script>
        <style type="text/css">
            img {
                width: 17px;
                height: 17px;
                cursor: pointer;
            }

            #windowTable {
                margin-left: 20px;
            }

            #windowTable tr {
                line-height: 30px;
            }

            #windowTable th {
                text-align: right;
                padding-right: 4px;
            }

            .mandatory:before {
                color: red;
                content: "*";
            }

            #windowTable th:after {
                content: ":";
            }
        </style>
		<script type="text/javascript">
            var theme = "${mysword_theme}";
            var tableSelectionIdex = 0;
	        $(document).ready(function () {
                var profile;
                $.ajax({async: false, type: "post", url: "Profile!myProfile", dataType: "json",
                    complete: function (response, returnType) {
                        if (response.responseJSON == null || response.responseJSON == "") {
                            post("/${mysword_path}/error", "_self", {error: response.responseText});
                        } else {
                            profile = response.responseJSON.userList[0];
                        }
                    }
                })
                $('#username').jqxInput({width: 300, height: 25, disabled: true, theme: theme});
                $('#username').val(profile.username);
                $('#hostname').jqxInput({width: 300, height: 25, theme: theme});
                $('#hostname').val(profile.hostname);
                $('#sims').jqxInput({width: 300, height: 25, theme: theme});
                $('#sims').val(profile.sims);
                $('#sims_password').jqxPasswordInput({width: 300, height: 25, theme: theme});
                $('#sims_password').val(profile.sims_password);
                $('#sims_password1').jqxPasswordInput({width: 300, height: 25, theme: theme});
                $('#sims_password1').val(profile.sims_password);
                $("#region").jqxDropDownList({ source: ['SSC', 'APAC', 'CORPORATE', 'US', 'EAST'], width: 150, height: 25, dropDownHeight: 105, theme: theme});
                $("#region").jqxDropDownList('val', profile.region);
                $('#email').jqxInput({width: 300, height: 25, theme: theme});
                $('#email').val(profile.email);
                $('#extension').jqxInput({width: 300, height: 25, theme: theme});
                $('#extension').val(profile.extension);
                $('#userIndex').jqxInput({width: 300, height: 25, theme: theme});
                $('#userIndex').val(profile.userIndex);
                $("#department").jqxDropDownList({ source: ['Development','Support','Coordination','Framework','Operations'], width: 150, height: 25, dropDownHeight: 130, theme: theme});
                $("#department").jqxDropDownList('val', profile.department);
                $("#theme").jqxDropDownList({ source: ['base', 'android', 'arctic', 'black', 'blackberry', 'bootstrap', 'classic', 'darkblue', 'energyblue', 'fresh', 'highcontrast', 'metro', 'metrodark', 'mobile', 'office', 'orange', 'shinyblack', 'summer', 'ui-darkness', 'ui-le-frog', 'ui-lightness', 'ui-overcast', 'ui-redmond', 'ui-smoothness', 'ui-start', 'ui-sunny', 'web', 'windowsphone'], selectedIndex: 0, width: 150, height: 25, dropDownHeight: 250, theme: theme});
                $("#theme").jqxDropDownList('val',profile.theme);
                $('#sysAdmin').jqxCheckBox({ width: 25, disabled: true, theme: theme });
                $("#sysAdmin").jqxCheckBox('val', profile.sysadmin);
                $('#ksAdmin').jqxCheckBox({ width: 25, disabled: true, theme: theme });
                $("#ksAdmin").jqxCheckBox('val', profile.knowledge_sharing_admin);
                $('#ksUser').jqxCheckBox({ width: 25, disabled: true, theme: theme });
                $("#ksUser").jqxCheckBox('val', profile.knowledge_sharing_user);
                $('#developer').jqxCheckBox({ width: 25, disabled: true, theme: theme });
                $("#developer").jqxCheckBox('val', profile.developer);
                $('#support').jqxCheckBox({ width: 25, disabled: true, theme: theme });
                $("#support").jqxCheckBox('val', profile.support);
                $('#windowTable').jqxValidator({
                    rules: [
                        { input: '#username', message: 'Username is required!', action: 'keyup, blur', rule: 'required' },
                        { input: '#email', message: 'Email format is wrong!', action: 'keyup, blur', rule: 'email' },
                        { input: '#sims_password', message: 'Password doesn\'t match', action: 'keyup, blur', rule: function(){
                            if($('#sims_password').val() == $('#sims_password1').val()) {
                                return true;
                            } else {
                                return false;
                            }
                        }},
                        { input: '#sims_password1', message: 'Password doesn\'t match', action: 'keyup, blur', rule: function(){
                            if($('#sims_password').val() == $('#sims_password1').val()) {
                                return true;
                            } else {
                                return false;
                            }
                        }}
                    ]
                });
                $('#save').jqxButton({ width: 70, height: 27, theme: theme}).on('click', function (event) {
                    var valid = $('#windowTable').jqxValidator('validate');
                    if (!valid) {
                        return;
                    }
                    $.ajax({async: false, type: "post", url: "Profile!updateProfile", dataType: "json",
                        data: {
                            'userList[0].hostname': $('#hostname').jqxInput('val'),
                            'userList[0].username': $('#username').jqxInput('val'),
                            'userList[0].sims': $('#sims').jqxInput('val'),
                            'userList[0].sims_password': $('#sims_password').jqxPasswordInput('val'),
                            'userList[0].region': $("#region").jqxDropDownList('val'),
                            'userList[0].email': $('#email').jqxInput('val'),
                            'userList[0].extension': $('#extension').jqxInput('val'),
                            'userList[0].userIndex': $('#userIndex').jqxInput('val'),
                            'userList[0].department': $("#department").jqxDropDownList('val'),
                            'userList[0].theme': $("#theme").jqxDropDownList('val'),
                            'userList[0].sysadmin': $('#sysAdmin').jqxCheckBox('val'),
                            'userList[0].knowledge_sharing_admin': $('#ksAdmin').jqxCheckBox('val'),
                            'userList[0].knowledge_sharing_user': $('#ksUser').jqxCheckBox('val'),
                            'userList[0].developer': $('#developer').jqxCheckBox('val'),
                            'userList[0].support': $('#support').jqxCheckBox('val')
                        },
                        complete: function (response, returnType) {
                            if (response.responseJSON == null || response.responseJSON == "") {
                                post("/${mysword_path}/error", "_self", {error: response.responseText});
                            } else {
                                window.location.reload();
                            }
                        }
                    })
                });
                $('#cancel').jqxButton({ width: 70, height: 27, theme: theme}).on('click', function () {
                    window.location.reload();
                });
                resetWindowHeight();
	        });
	    </script>
	</head>
	<body>
        <table id="windowTable">
            <tr>
                <th class="mandatory">Username</th>
                <td><input id="username" /></td>
            </tr>
            <tr>
                <th>Hostname</th>
                <td><input id="hostname" /></td>
            </tr>
            <tr>
                <th>SIMS</th>
                <td><input id="sims" /></td>
            </tr>
            <tr>
                <th>Password</th>
                <td><input type="password" id="sims_password" /></td>
            </tr>
            <tr>
                <th>Retry Password</th>
                <td><input type="password" id="sims_password1" /></td>
            </tr>
            <tr>
                <th>Region</th>
                <td><div id="region" ></div></td>
            </tr>
            <tr>
                <th>Email</th>
                <td><input id="email" /></td>
            </tr>
            <tr>
                <th>Extension</th>
                <td><input id="extension" /></td>
            </tr>
            <tr>
                <th>User Index</th>
                <td><input id="userIndex" /></td>
            </tr>
            <tr>
                <th>Department</th>
                <td><div id="department"></div></td>
            </tr>
            <tr>
                <th>Theme</th>
                <td><div id="theme"></div></td>
            </tr>
            <tr>
                <th>System Admin</th>
                <td><div id="sysAdmin"></div></td>
            </tr>
            <tr>
                <th>KS Admin</th>
                <td><div id="ksAdmin"></div></td>
            </tr>
            <tr>
                <th>KS User</th>
                <td><div id="ksUser"></div></td>
            </tr>
            <tr>
                <th>Developer</th>
                <td><div id="developer"></div></td>
            </tr>
            <tr>
                <th>Support</th>
                <td><div id="support"></div></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center"><button id="save">Save</button>&nbsp;&nbsp;&nbsp;<button id="cancel">Cancel</button></td>
            </tr>
        </table>
	</body>
</html>