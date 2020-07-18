<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Messages</title>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<script>
	$(document).ready(
			function() {

				$("#sendButton").click(function() {
					debugger
					//	myFunction();

					window.location.assign('addMessages?message=' + message)

				});

				function myFunction() {
					debugger;
					var message = $('#message').val();
					alert(message)
					$.ajax({
						url : 'addMessages?message=' + message,
						type : 'GET',
						success : function(data) {

							console.log("data ", data)
							$('#msgList').empty(); //remove all child nodes

							var messageList = JSON.parse(data);

							var listDiv = document.getElementById('msgList');
							var ul = document.createElement('ul');

							for (var i = 0; i < messageList.length; ++i) {
								var li = document.createElement('li');
								li.innerHTML = messageList[i].name + " : "
										+ messageList[i].message + " : "
										+ messageList[i].dateTime; // Use innerHTML to set the text
								ul.appendChild(li);

							}
							listDiv.appendChild(ul);
						},
						error : function(xhr, status, error) {
							debugger
							//alert("failure")
							console.log(xhr.responseText)

						}

					});

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
			<table>


				<%@ include file="menu.jsp"%>
				<tr></tr>
				<tr></tr>

				<tr>

					<td><b>Your Chat</b></td>
				</tr>
						<tr>

				<td><a href="downloadExcel?name=${username}-${fid}">Excel download</a></td>
					<td><a href="downloadPDF?name=${username}-${fid}">PDF download</a></td>
				</tr>
			</table>


			<div id=messageBox">

				<c:if test="${not empty allMessageList}">
				
						<c:forEach items="${allMessageList}" var="message">
							<%-- <li>${message.name}<span>&#58;</span> ${message.message} <span>&#58;</span>
								${message.dateTime}
							</li> --%>

							<c:choose>
								<c:when test="${username == message.name }">
								
										<div  style=" float:right;  clear:both;display:inline-block; background-color: silver; color: black;  margin-top: 5px; padding:5px; border-radius: 25px 25px 0px 25px;">${message.message}
											<span>&#58;</span> ${message.dateTime}
										</div>
								<br>
								</c:when>
								<c:otherwise>
									<div style="display:inline-block; background-color: silver; color: black; margin-top: 5px; padding:5px; border-radius: 25px 25px 25px 0px;">${message.message}
											<span>&#58;</span> ${message.dateTime}
										</div>
										<br>
								</c:otherwise>
							</c:choose>

						</c:forEach>
				
				</c:if>
				<c:if test="${empty allMessageList}">
					<b>Start Conversation</b>
				</c:if>
				<div id="sendMessage">
					<form:form id="sendMessageDiv" modelAttribute="sendMessagePojo"
						action="addMessages" method="post">
						<form:input path="message" id="message" name="message"
							placeholder="Type a message..." />

						<button id="sendButton" class="button">Send</button>
					</form:form>


				</div>
			</div>
		</div>
	</div>
</body>

</html>