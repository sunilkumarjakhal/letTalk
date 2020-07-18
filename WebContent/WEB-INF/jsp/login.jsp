<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<title>Login</title>
<style>

</style>

</head>

<body>



	<div id="container">
		<div id="mainOuterDiv">
		<%@ include file="project_name_header.jsp"%>
		<h1>User Login...</h1>
			<form:form id="loginForm" modelAttribute="login"
				action="loginProcess" method="post">

				<table>

					<tr>

						<td><form:label path="username">Username: </form:label></td>

						<td><form:input path="username" name="username" id="username" />

						</td>

						<td style="color: red; font-style: italic;"><form:errors
								path="username" /></td>

					</tr>

					<tr>

						<td><form:label path="password">Password:</form:label></td>

						<td><form:password path="password" name="password"
								id="password" /></td>

						<td style="color: red; font-style: italic;"><form:errors
								path="password" /></td>

					</tr>

					<tr>

						<td></td>

						<td align="left"><form:button class="button" id="login" name="login">Login</form:button>

						</td>

					</tr>

					<tr></tr>
					<tr></tr>
					<tr></tr>
					<tr></tr>

					<tr>

						<td></td>

						<td>For registration <a href="register">click</a> here</td>

					</tr>

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