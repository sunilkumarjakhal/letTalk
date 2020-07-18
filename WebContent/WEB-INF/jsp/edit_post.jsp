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

			<div id="editPostDiv" style="padding: 1% 0 2% 0;">
				<b>Edit Your post....</b>
				<form:form id="editPostForm" modelAttribute="editPostData"
					action="editPostProcess" method="post">
					<table>
				<tr>
				<td>Post : </td>
					<td><form:textarea path="postDetail" id="postDetail" name="postDetail"
						rows="5" cols="30" value="${editPostData.postDetail}"/></td>
					
					</tr>
										<tr>
						<td><form:label path="privacy">Privacy : </form:label></td>

						<td><form:radiobutton path="privacy" value="Public" id="privacy"
								name="privacy"
								checked="${editPostData.privacy == 'Public' ? 'checked' : '' }" />Public <form:radiobutton
								path="privacy" value="Friend" id="privacy" name="privacy"
								checked="${editPostData.privacy == 'Friend' ? 'checked' : '' }" />Friend
								<form:radiobutton
								path="privacy" value="None" id="privacy" name="privacy"
								checked="${editPostData.privacy == 'None' ? 'checked' : '' }" />None
								</td>

					</tr>
				
					<form:hidden path="dateTime" value="${editPostData.dateTime}"/>
				<tr>
					<td><input type="submit" class="button" value="Edit Post"></td></tr>
					</table>
				</form:form>

			</div>
		</div>
	</div>

</body>
</html>