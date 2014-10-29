<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>User List Home</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<link rel="stylesheet" type="text/css" href="../css/form.css">
		<link rel="stylesheet" type="text/css" href="../css/table.css">
		<link rel="stylesheet" type="text/css" href="../css/administrator/user_list.css">
		<script type="text/javascript" src="../js/administrator/user_list.js"></script>
	</head>
	<body>
		<div style="width: 900px;">
		<s:form id="actionForm" theme="simple" method="post" cssClass="myForm">
			<s:hidden name="username" />
			<s:hidden name="actionType" />
			<s:submit action="Profile" name="actionType" value="New" />
		</s:form>
		<table class="myTable">
			<tbody>
				<tr>
					<th>No</th>
					<th>Name</th>
					<th>SIMS</th>
					<th>Image</th>
					<th>Department</th>
					<th>Email</th>
					<th>Roles</th>
					<th>Lastedit By</th>
					<th>Lastedit DT</th>
					<th>Del.</th>
				</tr>
				<s:iterator value="userList" status="status">
					<tr>
						<td><s:property value="#status.index + 1" /></td>
						<td><span class="contentLink" onclick="submitForm('View', '<s:property value="username"/>')"><s:property value="username" /></span></td>
						<td><s:property value="sims" /></td>
						<td><s:property value="img" /></td>
						<td><s:property value="department" /></td>
						<td><s:property value="email" /></td>
						<td>
							<ul>
							<s:iterator value="roles">
								<li><s:property value="role_name"/></li>
							</s:iterator>
							</ul>
						</td>
						<td><s:property value="lasteditby" /></td>
						<td><s:property value="lasteditdt" /></td>
						<td>
									<img src="../image/operations/delete.png"
										title="Delete this user." class="actionImg"
										onclick="submitForm('Delete', '<s:property value="sims"/>')" />
								</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		</div>
	</body>
</html>