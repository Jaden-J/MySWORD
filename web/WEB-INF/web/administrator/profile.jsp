<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Profile Home</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<link rel="stylesheet" type="text/css" href="../css/form.css">
	</head>
	<body>
		<div style="width: 900px;">
			<s:form method="post" cssClass="myForm" theme="simple"
				enctype="multipart/form-data">
				<s:hidden name="user.img" />
				<table>
					<tbody>
						<tr>
							<th>
								<label for="username">
									Name:
								</label>
							</th>
							<td>
								<s:textfield id="username" name="user.username" />
							</td>
						</tr>
						<tr>
							<th>
								<label for="password">
									Password:
								</label>
							</th>
							<td>
								<s:textfield id="password" name="user.password"/>
							</td>
						</tr>
						<tr>
							<th>
								<label for="sims">
									SIMS:
								</label>
							</th>
							<td>
								<s:textfield id="sims" name="user.sims" />
							</td>
						</tr>
						<s:if test="user != null">
							<tr>
								<th>
									<label for="editBy">
										Lastedit By:
									</label>
								</th>
								<td>
									${user.lasteditby}
									<s:hidden name="user.lasteditby" />
								</td>
							</tr>
						</s:if>
						<s:if test="user != null">
							<tr>
								<th>
									<label for="editDt">
										Lastedit DT:
									</label>
								</th>
								<td>
									${user.lasteditdt}
									<s:hidden name="user.lasteditdt" />
								</td>
							</tr>
						</s:if>
						<tr>
							<th>
								<label for="photo">
									Change Photo:
								</label>
							</th>
							<td>
								<s:file id="photo" name="uploadFile" />
								You Photo is shown below.
							</td>
						</tr>
						<tr>
							<th>
								<label for="department">
									Department:
								</label>
							</th>
							<td>
								<s:textfield id="department" name="user.department" />
							</td>
						</tr>
						<tr>
							<th>
								<label for="email">
									Email:
								</label>
							</th>
							<td>
								<s:textfield id="email" name="user.email" />
							</td>
						</tr>
						<tr>
							<th>
								<label for="server_Number">server_Number:</label>
							</th>
							<td><s:textfield id="server_Number" name="user.server_Number" /></td>
						</tr>
						<tr>
							<th>
								<label for="server_Username">server_Username:</label>
							</th>
							<td><s:textfield id="server_Username" name="user.server_Username" /></td>
						</tr>
						<tr>
							<th>
								<label for="server_Password">server_Password:</label>
							</th>
							<td><s:textfield id="server_Password" name="user.server_Password" /></td>
						</tr>
						<tr>
							<th>
								Role List:
							</th>
							<td>
								<s:if test="#session.admin">
									<ul style="list-style: none; margin: 0px; padding: 0px;">
										<s:iterator
											value="#{'admin',knowledgebase, 'member', 'guest', 'tomcat', 'manager', 'manager-gui'}"
											id="temp_role">
											<li>
												<input type="checkbox" name="user.roles.role_name"
													value="<s:property value="key" />"
													id="<s:property value="key" />"
													<s:iterator value="user.roles.{role_name}" id="temp_role_name">
														<s:if test="%{#temp_role_name == #temp_role.key}">
															checked="checked"
														</s:if>
													</s:iterator> />
												<label for="<s:property value="key" />">
													<s:property value="key" />
												</label>
											</li>
										</s:iterator>
									</ul>
								</s:if>
								<s:else>
									<ul>
										<s:iterator value="user.roles">
											<li>
												${role_name}
											</li>
										</s:iterator>
									</ul>
								</s:else>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: right">
							<s:if test="user == null">
								<s:submit name="actionType" value="New" />
							</s:if>
							<s:else>
								<s:submit name="actionType" value="Update" />
							</s:else>
								<s:submit type="reset" value="Reset" />
							</td>
						</tr>
					</tbody>
				</table>
			</s:form>
			<img src="../image/user/${user.img}"
				style="width: 200px; height: 200px;" />
		</div>
	</body>
</html>