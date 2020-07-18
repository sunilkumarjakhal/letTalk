<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>My Profile</title>
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

			</table>

			<table>

				<tr>

					<td>${data.username}</td>
					<c:if test="${not empty data.imageUrl}">
						<!-- <td><img
					src='<c:url value="H:/sunil fr/Workspace/LetsTalk/WebContent/resources/FileStorage/sunil.jpg"></c:url>' height="10px"/></td>-->
						<td><img
							src='<c:url value="https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-260nw-1048185397.jpg"></c:url>'
							width="100px" height="100px" /></td>


					</c:if>
					<c:if test="${empty data.imageUrl}">
						<td><img
							src='<c:url value="${pageContext.request.contextPath}/resources/FileStorage/dummy_man.png"></c:url>' /></td>
					</c:if>
				</tr>


				<c:if test="${not empty data.firstName}">
					<tr>
						<td>First Name :</td>
						<td>${data.firstName}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.lastName}">
					<tr>
						<td>Last Name :</td>
						<td>${data.lastName}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.emailId}">
					<tr>
						<td>Email Id :</td>
						<td>${data.emailId}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.mobileNo}">
					<tr>
						<td>Mobile No. :</td>
						<td>${data.mobileNo}</td>
					</tr>
				</c:if>

				<c:if test="${not empty data.gender}">
					<tr>
						<td>Gender :</td>
						<td>${data.gender}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.country}">
					<tr>
						<td>Country :</td>
						<td>${data.country}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.state}">
					<tr>
						<td>State :</td>
						<td>${data.state}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.city}">
					<tr>
						<td>City :</td>
						<td>${data.city}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.address}">
					<tr>
						<td>Address :</td>
						<td>${data.address}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.dob}">
					<tr>
						<td>D.O.B. :</td>
						<td>${data.dob}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.hobby}">
					<tr>
						<td>Hobbies :</td>
						<td>${data.hobby}</td>
					</tr>
				</c:if>
				<c:if test="${not empty data.aboutYou}">
					<tr>
						<td>About You :</td>
						<td>${data.aboutYou}</td>
					</tr>
				</c:if>




			</table>
			<table>
				<tr>
					<td><c:choose>
							<c:when test="${ isMyFriend }">
								<a href="unFriendFromProfile?queryName=${data.username}">UnFriend</a>
							</c:when>
							<c:otherwise>
								<a href="addFriendFromProfile?queryName=${data.username}">Add
									Friend</a>
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td><c:choose>
							<c:when test="${ isBlocked }">
								<a href="unBlockUser?queryName=${data.username}">Un Block</a>
							</c:when>
							<c:otherwise>
								<a href="blockUser?queryName=${data.username}">Block</a>
							</c:otherwise>
						</c:choose></td>
				</tr>

			</table>

		</div>
	</div>
</body>

</html>