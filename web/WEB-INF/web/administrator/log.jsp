<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>MySWORD Schedule</title>
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<link rel="stylesheet" type="text/css" href="../css/form.css">
		<link rel="stylesheet" type="text/css" href="../css/table.css">
		<script type="text/javascript" src="../js/jquery/jquery.js"></script>
		<script type="text/javascript" src="../js/jquery/jquery.validate.js"></script>
		<script type="text/javascript" src="../js/jquery/common.js"></script>
		<script type="text/javascript" src="../js/administrator/log.js"></script>
		<style type="text/css">
.myTable th, .myTable td{
	font-size: 14px;
}

.myTable th {
	height: 27px;
}

.myForm select {
	min-width: 40px;
}

.myForm option {
	min-width: 40px;
}

.editTable th {
	background-color: #F5F5FF;
}

.className {
	display: none;
}
</style>
<script type="text/javascript">

</script>
	</head>
	<body>
			<s:form method="post" theme="simple" id="actionForm" cssClass="myForm">
				<table class="editTable">
					<tbody>
						<tr>
							<th><label for="startTime">Start Time:</label></th>
							<td colspan="5"><s:textfield id="startTime" name="startTime"/>&nbsp;yyyy-MM-dd HH:mm:ss</td>
						</tr>
						<tr>
							<th><label for="endTime">End Time:</label></th>
							<td colspan="5"><s:textfield id="endTime" name="endTime"/>&nbsp;yyyy-MM-dd HH:mm:ss</td>
						</tr>
						<tr>
							<th><label for="message">Message:</label></th>
							<td colspan="5"><s:textfield id="message" name="message"/></td>
						</tr>
						<tr>
							<th><label for="user">User:</label></th>
							<td><s:select list="userList.{sims}" name="sims" /></td>
							<th><label for="level">Level:</label></th>
							<td><s:select list="{'All','DEBUG','INFO','ERROR','FATAL'}" name="level" /></td>
							<th><label for="pageSize">Page Size:</label></th>
							<td><s:select list="{'50','100','200', 'All'}" name="pageSize" /></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: left">
								<s:submit value="Search" />
								<input type="Reset" value="Reset" />
							</td>
						</tr>
					</tbody>
				</table>
			</s:form>
			<hr/>
			<s:form theme="simple" method="post">
				<s:hidden name="startTime" />
				<s:hidden name="endTime" />
				<s:hidden name="message" />
				<s:hidden name="user" />
				<s:hidden name="level" />
				<s:hidden name="pageSize" />
				<div style="width: 200px;">
					Go to:
					<select name="pageNo">
						<% 
							int pageNo = (Integer)request.getAttribute("pageNo");
							for(int i=1; i<=(Integer)request.getAttribute("totalPage"); i++) {
						%>
								<option value="<%= i %>" onclick="this.form.submit()"
								<%if(pageNo==i){%> selected="selected" <%}%>><%= i %></option>
						<%
							}
						%>
					</select>
					<a onclick="showDetail()" style="text-decoration:underline;cursor:pointer;color:blue">Detail</a>
				</div>
			</s:form>
			<table class="myTable">
				<tbody>
					<tr>
						<th style="min-width: 30px">
							No.
						</th>
						<th style="min-width: 170px">
							Log Time
						</th>
						<th style="min-width: 55px">
							Level
						</th>
						<th class="className">
							Class Name
						</th>
						<th style="min-width: 400px">
							Message
						</th>
					</tr>
					<s:iterator value="logList" status="sta">
						<tr>
							<td>
								<s:property value="#sta.index + 1" />
							</td>
							<td>
								<s:property value="logTime" />
							</td>
							<td>
								<s:property value="level" />
							</td>
							<td class="className">
								<s:property value="className" />.<s:property value="method" />(<s:property value='className.substring(className.lastIndexOf(".")+1)' />.java:<s:property value="lineNo" />)
							</td>
							<td>
								<s:property value="message" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
	</body>
</html>
