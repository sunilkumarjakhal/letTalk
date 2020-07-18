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

				<tr>

					<td>Welcome <b>${username}</b></td>
				</tr>
			</table>

			<form:form id="searchFriendForm" modelAttribute="friendsList"
				action="" method="post">


				<ol>
					<c:forEach items="${friendsList}" var="friend">


						<li>
							<div id="mainDiv1">
								<div id="mainDiv2">
									<a href="friendProfile?queryName=${friend.username}">${friend.firstName}
										${friend.lastName}</a>
								</div>
								<div id="mainDiv3"></div>

								<c:choose>
									<c:when test="${ friend.friend }">
										<a
											href="unFriend?friendName=${friend.username}&queryName=${search_fr}">UnFriend</a>
									</c:when>
									<c:otherwise>
										<a
											href="addFriend?friendName=${friend.username}&queryName=${search_fr}">Add
											Friend</a>
									</c:otherwise>
								</c:choose>


							</div>
						</li>



					</c:forEach>
				</ol>




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