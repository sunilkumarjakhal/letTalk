<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Welcome</title>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js">
	
</script>
<link href="${pageContext.request.contextPath}/resources/css/common.css" rel="stylesheet" >
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
			<%
				String username = (String) request.getSession().getAttribute("letsTalk_username");
			%>
			<div id="createPost" style="padding: 1% 0 2% 0;">
				<b>Create Your post....</b>
				<form:form id="createPost" modelAttribute="postData"
					action="addPost" method="post">
					<form:textarea path="postDetail" id="postDetail" name="postDetail"
						rows="5" cols="30" />
					<br>
					<br>
					<input type="submit" class="button" value="Create Post">
				</form:form>

			</div>



			<div id="allUserList"></div>

			<div id="allPost">
				<c:if test="${not empty postedDataPojoList}">
					<c:forEach items="${postedDataPojoList}" var="postedDataPojo">
						<div id="post"
							style="padding: 1% 0 2% 0; border-top: 3px solid black;">
							<a href="friendProfile?queryName=${postedDataPojo.username}">${postedDataPojo.username}</a><br> ${postedDataPojo.postDetail}<br>
							${postedDataPojo.dateTime}<br>
							<c:if test="${postedDataPojo.myPost}">
								<a href="editPost?query=${postedDataPojo.dateTime}">Edit</a>
								<a href="deletePost?query=${postedDataPojo.dateTime}">Delete</a>
							</c:if>
						</div>

					</c:forEach>
				</c:if>

			</div>
		</div>
	</div>
</body>

</html>