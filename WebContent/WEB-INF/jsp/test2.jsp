<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
	<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<title>Register</title>
<style>



</style>
</head>

<body>
	<div id="container">
		<div id="mainOuterDiv">
			<%@ include file="project_name_header.jsp"%>
			<h1>User Registration...</h1>
		
			<form:form id="registerForm" modelAttribute="register"
				action="registerProcess2" method="post">

				<table>

					<tr>

						<td><form:label path="username">Username: </form:label></td>

						<td><form:input path="username" name="username" id="username" />

						</td>

						<td style="color: red; font-style: italic;"><form:errors
								path="username" /></td>

					</tr>

					<tr>

						<td><form:label path="firstName">First Name: </form:label></td>

						<td><form:input path="firstName" name="firstName"
								id="firstName" /></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="firstName" /></td>

					</tr>


					<tr>

						<td><form:label path="lastName">Last Name: </form:label></td>

						<td><form:input path="lastName" name="lastName" id="lastName" />

						</td>
						<td style="color: red; font-style: italic;"><form:errors
								path="lastName" /></td>

					</tr>

					<tr>

						<td><form:label path="password">Password:</form:label></td>

						<td><form:password path="password" name="password"
								id="password" /></td>

						<td style="color: red; font-style: italic;"><form:errors
								path="password" /></td>

					</tr>

					<tr>

						<td><form:label path="password">Confirm Password:</form:label>

						</td>

						<td><form:password path="cpassword" name="cpassword"
								id="cpassword" /></td>

						<td style="color: red; font-style: italic;"><form:errors
								path="cpassword" /></td>

					</tr>

					<tr>

						<td></td>

						<td align="left"><form:button class="button" id="register" name="register">Register</form:button>

						</td>

					</tr>

					<tr></tr>



				</table>

			</form:form>

			<table align="center">

				<tr>

					<td style="font-style: italic; color: red;">${message}</td>

				</tr>

			</table>
		</div>
	</div>
</body>

</html>