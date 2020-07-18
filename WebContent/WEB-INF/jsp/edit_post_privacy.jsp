<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
</head>
<body>

	<div id="container">
		<div id="mainOuterDiv">
			<%@ include file="project_name_header.jsp"%>
			<table>

				<%@ include file="menu.jsp"%>

				<tr></tr>
				<tr></tr>
			</table>
			<div id="myDetail" style="padding: 2% 0 1% 0;">
				Hello <b>${firstname}</b>

			</div>

			<div id="editPostPrivacyDiv" style="padding: 1% 0 2% 0;">

				<form:form id="seePost" modelAttribute="editPostPrivacyData"
					action="editPostPrivacyProcess" method="post">
					<table>
						<tr>
							<td><b>Edit Your post Privacy....</b></td>
						</tr>
						<tr>
							<td><form:radiobutton path="privacy" value="Public"
									id="privacy" name="privacy"
									checked="${postPrivacy == 'Public' ? 'checked' : '' }" />Everyone
								<form:radiobutton path="privacy" value="Friend" id="privacy"
									name="privacy"
									checked="${postPrivacy == 'Friend' ? 'checked' : '' }" />Friend<form:radiobutton
									path="privacy" value="None" id="privacy" name="privacy"
									checked="${postPrivacy == 'None' ? 'checked' : '' }" />None
							<td>
						</tr>
						<tr>
							<td><input type="submit" class="button" value="Edit" /></td>
						</tr>
					</table>
				</form:form>

			</div>

		</div>
	</div>
</body>
</html>