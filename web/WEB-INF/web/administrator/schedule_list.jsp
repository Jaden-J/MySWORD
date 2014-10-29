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
		<script type="text/javascript" src="../js/administrator/schedule.js"></script>
		<style type="text/css">
.myForm select {
	height: 100px;
	min-width: 25px;
}

.myTable th, .myTable td{
	font-size: 14px;
}

.myTable th {
	height: 27px;
}

.myForm option {
	min-width: 25px;
}
.actionImg {
	cursor: pointer;
	width: 20px;
	height: 20px;
}
</style>
	</head>
	<body>
		<div style="width: 1100px;">
			<s:form method="post" theme="simple" id="actionForm" cssClass="myForm">
				<s:hidden name="actionType" />
				<s:hidden name="scheduleId" />
				<s:submit type="button" onclick="editSchedule('Add', '')" value="Add" />
			</s:form>
			<hr/>
			<h2>
				Repeat Schedule:
			</h2>
			<table class="myTable">
				<tbody>
					<tr>
						<th style="min-width: 100px">
							Schedule Name
						</th>
						<th style="min-width: 200px">
							Schedule Service
						</th>
						<th style="min-width: 50px">
							Active
						</th>
						<th style="min-width: 60px">
							Running
						</th>
						<th style="min-width: 50px">
							Thread
						</th>
						<th style="min-width: 100px">
							Interval
						</th>
						<th style="min-width: 100px">
							Next Run
						</th>
						<th style="min-width: 50px">
							Edit
						</th>
					</tr>
					<s:iterator value="scheduleList">
						<s:if test='"Repeat".equals(value.schedule_Type)'>
						<tr>
							<td>
								<s:property value="value.schedule_Name" />
							</td>
							<td>
								<s:property value="value.schedule_Service" />
							</td>
							<td>
								<s:property value="value.active" />
							</td>
							<td>
								<s:property value="value.running" />
							</td>
							<td>
								<s:property value="value.thread" />
							</td>
							<td>
								<s:property value="value.interval" />
							</td>
							<td>
								<s:property value="value.nextRun" />
							</td>
							<td>
								<img class="actionImg" title="Edit this schedule item."
									onclick="editSchedule('View', '<s:property value="value.scheduleId" />')"
									src="../image/operations/pencil.png" />
								<s:if test='value.active'>
									<img class="actionImg" title="Suspend this schedule item."
										onclick="editSchedule('Suspend', '<s:property value="value.scheduleId" />')"
										src="../image/operations/lightbulb_on.png" />
								</s:if>
								<s:else>
									<img class="actionImg" title="Active this schedule item."
										onclick="editSchedule('Active', '<s:property value="value.scheduleId" />')"
										src="../image/operations/lightbulb_off.png" />
								</s:else>
								<img class="actionImg" title="Delete this schedule item."
									onclick="editSchedule('Delete', '<s:property value="value.scheduleId" />')"
									src="../image/operations/close.png" />
							</td>
						</tr>
						</s:if>
					</s:iterator>
				</tbody>
			</table>
			<hr />
			<h2>
				One Time Schedule:
			</h2>
			<table class="myTable">
				<tbody>
					<tr>
						<th style="min-width: 100px">
							Schedule Name
						</th>
						<th style="min-width: 200px">
							Schedule Service
						</th>
						<th style="min-width: 50px">
							Active
						</th>
						<th style="min-width: 60px">
							Running
						</th>
						<th style="min-width: 50px">
							Thread
						</th>
						<th style="min-width: 120px">
							Run Time
						</th>
						<th style="min-width: 120px">
							Next Run
						</th>
						<th style="min-width: 50px">
							Edit
						</th>
					</tr>
					<s:iterator value="scheduleList">
						<s:if test='"OneTime".equals(value.schedule_Type)'>
						<tr>
							<td>
								<s:property value="value.schedule_Name" />
							</td>
							<td>
								<s:property value="value.schedule_Service" />
							</td>
							<td>
								<s:property value="value.active" />
							</td>
							<td>
								<s:property value="value.running" />
							</td>
							<td>
								<s:property value="value.thread" />
							</td>
							<td>
								<s:property value="value.run_Time" />
							</td>
							<td>
								<s:property value="value.nextRun" />
							</td>
							<td>
								<img class="actionImg" title="Edit this schedule item."
									onclick="editSchedule('View', '<s:property value="value.scheduleId" />')"
									src="../image/operations/pencil.png" />
								<s:if test='value.active'>
									<img class="actionImg" title="Suspend this schedule item."
										onclick="editSchedule('Suspend', '<s:property value="value.scheduleId" />')"
										src="../image/operations/lightbulb_on.png" />
								</s:if>
								<s:else>
									<img class="actionImg" title="Active this schedule item."
										onclick="editSchedule('Active', '<s:property value="value.scheduleId" />')"
										src="../image/operations/lightbulb_off.png" />
								</s:else>
								<img class="actionImg" title="Delete this schedule item."
									onclick="editSchedule('Delete', '<s:property value="value.scheduleId" />')"
									src="../image/operations/close.png" />
							</td>
						</tr>
						</s:if>
					</s:iterator>
				</tbody>
			</table>
			<hr />
			<h2>
				Complex Schedule:
			</h2>
			<table class="myTable">
				<tbody>
					<tr>
						<th style="min-width: 100px">
							Schedule Name
						</th>
						<th style="min-width: 200px">
							Schedule Service
						</th>
						<th style="min-width: 50px">
							Active
						</th>
						<th style="min-width: 60px">
							Running
						</th>
						<th style="min-width: 50px">
							Thread
						</th>
						<th style="min-width: 50px">
							Month
						</th>
						<th style="min-width: 50px; maz-width: 120px">
							Date
						</th>
						<th style="min-width: 50px; max-width: 120px">
							Hour
						</th>
						<th style="min-width: 50px; max-width: 120px">
							Minute
						</th>
						<th style="min-width: 50px">
							Week Day
						</th>
						<th style="min-width: 120px">
							Next Run
						</th>
						<th style="min-width: 50px">
							Edit
						</th>
					</tr>
					<s:iterator value="scheduleList">
						<s:if test='"Complex".equals(value.schedule_Type)'>
						<tr>
							<td>
								<s:property value="value.schedule_Name" />
							</td>
							<td>
								<s:property value="value.schedule_Service" />
							</td>
							<td>
								<s:property value="value.active" />
							</td>
							<td>
								<s:property value="value.running" />
							</td>
							<td>
								<s:property value="value.thread" />
							</td>
							<td>
								<s:if test='!"?".equals(value.month) && !"*".equals(value.month)'>
									<s:property value="value.month"/>
								</s:if>
							</td>
							<td>
								<s:if test='!"?".equals(value.day_of_month) && !"*".equals(value.day_of_month)'>
									<s:property value="value.day_of_month"/>
								</s:if>
							</td>
							<td>
								<s:if test='!"?".equals(value.hour) && !"*".equals(value.hour)'>
									<s:property value="value.hour"/>
								</s:if>
							</td>
							<td>
								<s:if test='!"?".equals(value.minute) && !"*".equals(value.minute)'>
									<s:property value="value.minute"/>
								</s:if>
							</td>
							<td>
								<s:if test='!"?".equals(value.day_of_week) && !"*".equals(value.day_of_week)'>
									<s:property value="value.day_of_week"/>
								</s:if>
							</td>
							<td>
								<s:property value="value.nextRun" />
							</td>
							<td>
								<img class="actionImg" title="Edit this schedule item."
									onclick="editSchedule('View', '<s:property value="value.scheduleId" />')"
									src="../image/operations/pencil.png" />
								<s:if test='value.active'>
									<img class="actionImg" title="Suspend this schedule item."
										onclick="editSchedule('Suspend', '<s:property value="value.scheduleId" />')"
										src="../image/operations/lightbulb_on.png" />
								</s:if>
								<s:else>
									<img class="actionImg" title="Active this schedule item."
										onclick="editSchedule('Active', '<s:property value="value.scheduleId" />')"
										src="../image/operations/lightbulb_off.png" />
								</s:else>
								<img class="actionImg" title="Delete this schedule item."
									onclick="editSchedule('Delete', '<s:property value="value.scheduleId" />')"
									src="../image/operations/close.png" />
							</td>
						</tr>
						</s:if>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</body>
</html>
