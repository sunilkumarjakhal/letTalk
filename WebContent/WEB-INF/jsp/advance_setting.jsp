<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<%-- <link href="<c:url value="/resources/css/common.css" />" rel="stylesheet"> --%>
<title>Advance Setting</title>

</head>

<body>
	<div id="container">
		<div id="mainOuterDiv">
	<%@ include file="project_name_header.jsp"%>
			<table>


				<%@ include file="menu.jsp"%>



				<tr></tr>
				<tr></tr>

				<tr>

					<td><b>Advance Setting</b></td>
				</tr>
				<tr>
					<td>Post Privacy :</td>
					<td><a href="editPostPrivacy">Edit Privacy</a></td>
				</tr>
				
					<tr>
					<td>Activate/Deactivate Account :</td>
					
					
									<td><c:choose>
								<c:when test="${isAccountActivate }">
									<a href="editAccountActivation?id=0">Deactivate</a>
					</c:when>
								<c:otherwise>
								<a href="editAccountActivation?id=1">Activate</a>
					</c:otherwise>
					</c:choose>
							</td>
					
				
				
				</tr>
				<tr>


				</tr>

			</table>




		</div>
	</div>
</body>

</html>