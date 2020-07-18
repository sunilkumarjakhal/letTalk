<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<title>Chat</title>

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

					<td>Lets Talk with friends</td>
				</tr>
			</table>

			</br> </br>



			<c:set var="contextPath" value="${pageContext.request.contextPath}" />

			<c:if test="${not empty allFriendList}">
				<ol>
					<c:forEach items="${allFriendList}" var="friend">
						<li><a href="${contextPath}/messages?fid=${friend.username}">
								${friend.firstName}</a></li>



					</c:forEach>
				</ol>
			</c:if>
		</div>
	</div>
</body>

</html>