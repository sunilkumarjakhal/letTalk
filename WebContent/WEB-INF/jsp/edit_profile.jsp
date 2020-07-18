<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Edit Profile</title>


<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<script>
	$(document).ready(
			function() {
				var lastCountry = $('#country').val();
				if (lastCountry != "NONE") {
					getStateData(lastCountry)
				}

				var lastState = $('#state').val();
				if (lastState != "NONE") {
					getCityData(lastState)
				}

				$('#dob').datetimepicker({
					//format : 'Y-m-d H:i',
					format : 'Y-m-d',
					step : 15,
					minDate : ge_today_date(new Date()),
					timepicker : false,
					minDate : '-1970/01/10',//yesterday is minimum date(for today use 0 or -1970/01/01)
					maxDate : '+1970/01/10',//tomorrow is maximum date calendar
					yearStart : 2019,

				});

				function ge_today_date(date) {
					var day = date.getDate();
					var month = date.getMonth() + 1;
					var year = date.getFullYear().toString().slice(2);
					return day + '-' + month + '-' + year;
				}
				var startDate;
				$('#startDate').datetimepicker({
					format : 'Y-m-d H:i',
					onChangeDateTime : function(dp, $input) {
						startDate = $("#startDate").val();
					}
				});

				$('#endDate').datetimepicker({
					format : 'Y-m-d H:i',
					onClose : function(current_time, $input) {
						var endDate = $("#endDate").val();
						if (startDate > endDate) {
							alert('Please select correct date');
							return false;
						}
					}
				});

				$("#country").on('change', function() {
					var country = $('#country').val();
					getStateData(country)
				});

				$("#state").on('change', function() {
					var state = $('#state').val();
					getCityData(state)
				});

				function getStateData(country) {

					if (state === "NONE") {

						$('#state').empty();
						$('#state').append(
								'<option value="NONE">' + "---select---"
										+ '</option>');
					} else {
						$.ajax({
							url : 'getStateFromCountry?country=' + country,
							type : 'GET',
							//  data : { state: $('#country').val()},
							success : function(data) {

								console.log("data ", data)
								$('#state').empty(); //remove all child nodes

								var StateList = JSON.parse(data);

								$.each(StateList, function(key, val) {

									if (typeof (key == 'string')) {
										$('#state').append(
												'<option value="' + key+ '">'
														+ val + '</option>');
									}

								});
								//   for(var i = 0; i < data.length; i++){
								// 	  if (typeof (data[i].id == 'string')) {
								// 		$('#state').append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
								// 		  }
								// }

							},
							error : function(xhr, status, error) {
								alert("failure")
								console.log(xhr.responseText)

							}
						});
					}
				}

				function getCityData(state) {

					if (state === "NONE") {

						$('#city').empty();
						$('#city').append(
								'<option value="NONE">' + "---select---"
										+ '</option>');
					} else {
						$.ajax({
							url : 'getCityFromState?state=' + state,
							type : 'GET',
							//  data : { state: $('#country').val()},
							success : function(data) {

								console.log("data ", data)
								$('#city').empty(); //remove all child nodes

								var StateList = JSON.parse(data);

								$.each(StateList, function(key, val) {

									if (typeof (key == 'string')) {
										$('#city').append(
												'<option value="' + key+ '">'
														+ val + '</option>');
									}

								});
								//   for(var i = 0; i < data.length; i++){
								// 	  if (typeof (data[i].id == 'string')) {
								// 		$('#state').append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
								// 		  }
								// }

							},
							error : function(xhr, status, error) {
								alert("failure")
								console.log(xhr.responseText)

							}
						});
					}
				}

			});
</script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
</head>



<body>
	<div id="container">
		<div id="mainOuterDiv">
	<%@ include file="project_name_header.jsp"%>
			<table >

				<%@ include file="menu.jsp"%>

				<tr></tr>
				<tr></tr>

				<tr>

					<td>Welcome <b>${firstname}</b></td>
				</tr>
			</table>

			<form:form id="editProfile" modelAttribute="profileDataPojo"
				action="editProfileProcess" method="post"
				enctype="multipart/form-data">

				<table>

					<tr>

						<td><form:label path="username">Username</form:label></td>

						<td><form:input path="username" name="username" id="username"
								value="${data.username }" disabled="true" /></td>

						<td style="color: red; font-style: italic;"><form:errors
								path="username" />
					</tr>



					<tr>

						<td><form:label path="firstName">FirstName</form:label></td>

						<td><form:input path="firstName" name="firstName"
								id="firstName" value="${data.firstName }" /></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="firstName" />
					</tr>

					<tr>

						<td><form:label path="lastName">LastName</form:label></td>

						<td><form:input path="lastName" name="lastName" id="lastName"
								value="${data.lastName }" /></td>

						<td style="color: red; font-style: italic;"><form:errors
								path="lastName" /></td>
					</tr>

					<tr>

						<td><form:label path="emailId">Email</form:label></td>

						<td><form:input path="emailId" name="emailId" id="emailId"
								value="${data.emailId }" /></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="emailId" />
					</tr>

					<%-- 			<tr>

				<td><form:label path="address">Address</form:label></td>

				<td><form:input path="address" name="address" id="address" />

				</td>
				<td style="color: red; font-style: italic;"><form:errors
						path="address" />
			</tr> --%>

					<tr>

						<td><form:label path="mobileNo">Phone</form:label></td>

						<td><form:input path="mobileNo" name="mobileNo" id="mobileNo"
								value="${data.mobileNo }" /></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="mobileNo" />
					</tr>

					<tr>
						<td><form:label path="dob">D.O.B.</form:label></td>

						<td><form:input class='input-group date' path="dob"
								name="dob" id="dob" value="${data.dob }" /></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="dob" />
					</tr>


					<tr>

						<td><form:label path="country">Country</form:label></td>

						<td><form:select path="country" id="country" name="country">
								<form:option value="NONE" label="--- Select ---" />
								<%-- <form:options items='${countryList}' /> --%>
								<form:options items="${countryList}" />
							</form:select></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="country" /></td>
					</tr>


					<tr>

						<td><form:label path="state">State</form:label></td>

						<td><form:select  path="state" id="state" name="state"
								value="${data.state }">
								<form:option value="NONE" label="--- Select ---" />

							</form:select></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="state" />
					</tr>

					<tr>

						<td><form:label path="city">City</form:label></td>

						<td><form:select path="city" id="city" name="city"
								value="${data.city }">
								<form:option value="NONE" label="--- Select ---" />

							</form:select></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="city" />
					</tr>

					<tr>
						<td><form:label path="address">Address</form:label></td>
						<td><form:textarea id="address" name="address" path="address"
								rows="3" cols="30" value="${data.address }" /></td>

						<td style="color: red; font-style: italic;"><form:errors
								path="address" />
					</tr>

					<tr>
						<td><form:label path="gender">Gender</form:label></td>

						<td><form:radiobutton path="gender" value="Male" id="gender"
								name="gender"
								checked="${data.gender == 'Male' ? 'checked' : '' }" />Male <form:radiobutton
								path="gender" value="Female" id="gender" name="gender"
								checked="${data.gender == 'Female' ? 'checked' : '' }" />Female</td>

					</tr>

					<tr>
						<td><form:label path="hobby">Hobbies</form:label></td>
						<td><c:choose>
								<c:when test="${  fn:contains( data.hobby, 'Sport' ) }">
									<form:checkbox path="hobby" value="Sport" checked="checked" />Sport
					</c:when>
								<c:otherwise>
									<form:checkbox path="hobby" value="Sport" />Sport
					</c:otherwise>
							</c:choose> <c:choose>
								<c:when test="${  fn:contains( data.hobby, 'Reading' ) }">
									<form:checkbox path="hobby" value="Reading" checked="checked" />Reading
					</c:when>
								<c:otherwise>
									<form:checkbox path="hobby" value="Reading" />Reading
					</c:otherwise>
							</c:choose> <c:choose>
								<c:when test="${  fn:contains( data.hobby, 'Singing' ) }">
									<form:checkbox path="hobby" value="Singing" checked="checked" />Singing
					</c:when>
								<c:otherwise>
									<form:checkbox path="hobby" value="Singing" />Singing
					</c:otherwise>
							</c:choose></td>

						<%-- 	<td><form:checkbox path="hobby" value="Sport" />Sport <form:checkbox
						path="hobby" value="Reading" />Reading <form:checkbox
						path="hobby" value="Singing" />Singing</td> --%>


					</tr>

					<tr>
						<td><form:label path="aboutYou">About You</form:label></td>
						<td><form:textarea path="aboutYou" rows="3" cols="30"
								value="${data.aboutYou }" /></td>


					</tr>

					<tr>
						<td><form:label path="file">Upload Image</form:label></td>
						<td><input type="file" name="file" id="file" name="file" /></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="file" />
					</tr>
					<tr>

						<td></td>

						<td><form:button id="submit" class="button" name="sybmit">Submit</form:button>

						</td>

					</tr>

					<tr></tr>



				</table>

			</form:form>
		</div>
	</div>
</body>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<link
	href="${pageContext.request.contextPath}/resources/css/jquery.datetimepicker.min.css"
	rel="stylesheet">
<!--   <script src="<c:url value="/resources/js/jquery.datetimepicker.js" />"></script>-->
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.js"></script>




</html>