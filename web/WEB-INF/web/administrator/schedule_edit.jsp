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
	<style type="text/css">
		.myForm select {
			height: 100px;
			min-width: 25px;
		}
		.myForm option {
			min-width: 25px;
		}
		table {
			font-size: 12px;
		}
		th {
			width: 70px;
			text-align: right;
		}
	</style>
  </head>
  <body>
  	<div>
  		
  							<s:property value="dayOfMonth"/>
  		<s:form theme="simple" method="post" cssClass="myForm">
  			<s:hidden name="schedule.scheduleId" />
  			<table>
  				<tbody>
  					<tr>
  						<th>Name:</th>
  						<td><s:textfield name="schedule.schedule_Name" /></td>
  						<td>The schedule name.</td>
  					</tr>
  					<tr>
  						<th>Schedule:</th>
  						<td><s:textfield name="schedule.schedule_Service" /></td>
  						<td>Package.Service_Name:Method_Name. e.g. test.Test:test.</td>
  					</tr>
  					<tr>
  						<th>Run Multiple:</th>
  						<td>
  							<s:radio list="{'true', 'false'}" name="schedule.multiple" />
  						</td>
  						<td>
  							 True: run when meet the schedule.<br/>False: run after previous schedule.
  						</td>
  					</tr>
  					<tr>
  						<th>Active:</th>
  						<td>
  							<s:radio list="{'true','false'}" name="schedule.active" />
  						</td>
  						<td>
  							True: activate the schedule.<br/>False: deactivate the schedule.
  						</td>
  					</tr>
  				</tbody>
  			</table>
  			<hr/>
  			<p><s:radio list="{'OneTime'}" name="schedule.schedule_Type" /></p>
  			<table>
  				<tbody>
  					<tr>
  						<th>Datetime:</th>
  						<td><s:textfield name="schedule.run_Time" /></td>
  						<td>Format: yyyy-MM-dd HH:mm:ss</td>
  					</tr>
  				</tbody>
  			</table>
  			<hr/>
  			<p><s:radio list="{'Repeat'}" name="schedule.schedule_Type"/></p>
  			<table>
  				<tbody>
  					<tr>
  						<th>Interval:</th>
  						<td><s:textfield name="schedule.interval" /></td>
  						<td>Please input the seconds.</td>
  					</tr>
  				</tbody>
  			</table>
  			<hr/>
  			<p><s:radio list="{'Complex'}" name="schedule.schedule_Type" /></p>
  			<table>
  				<tbody>
  					<tr>
  						<th rowspan="2">Date Time:</th>
  						<td>Month</td>
  						<td>Day</td>
  						<td>Hour</td>
  						<td>Minute</td>
  						<td>Day of week</td>
  					</tr>
  					<tr>
  						<td>
  							<s:select list="{'JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC'}" name="month" value="month.split(',')" multiple="true"/>
  						</td>
  						<td>
  							<s:select list='{"1","2","3","4","5","6","7","8","9","10","11","12"}' name="dayOfMonth" multiple="true"/>
  						</td>
  						<td>
  							<s:select list='{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"}' name="hour" multiple="true" />
  						</td>
  						<td>
  							<s:select list='{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"}' name="minute" multiple="true" />
  						</td>
  						<td>
  							<s:select list="{'MON','TUE','WED','THU','FRI','SAT','SUN'}" name="dayOfWeek" multiple="true" />
  						</td>
  					</tr>
  				</tbody>
  			</table>
  			<hr/>
  			<s:if test='"View".equals(actionType)'>
	  			<s:submit name="actionType" value="Update"/>
	  			<s:submit name="actionType" value="Delete"/>
  			</s:if>
  			<s:else>
	  			<s:submit name="actionType" value="New"/>
  			</s:else>
  			<s:submit action="Schedule" value="Cancel"/>
  			<s:submit type="reset" value="Reset"/>
  		</s:form>
  	</div>
  </body>
</html>
