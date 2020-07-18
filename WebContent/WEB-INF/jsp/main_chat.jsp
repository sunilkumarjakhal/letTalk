<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
<title>Chatting</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main.css" />


<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
</head>
<body>

	<div id="welcome-page">
		<div class="welcome-page-container">
			<h1 class="title">Welcome - To join the chat group enter your
				name</h1>
			<form id="welcomeForm" name="welcomeForm">
				<div class="form-group">
					<input type="text" id="name" placeholder="name"
						class="form-control" />
				</div>
				<div class="form-group">
					<button type="submit" onclass="accent username-submit">Lets
						Begin</button>
				</div>
			</form>
		</div>
	</div>


	<div id="dialogue-page" class="hidden">
		<div class="dialogue-container">
			<div class="dialogue-header">
				<h2>JavaInUse Chat Application</h2>
			</div>
			<ul id="messageList">

			</ul>
			<form id="dialogueForm" name="dialogueForm" nameForm="dialogueForm">
				<div class="form-group">
					<div class="input-group clearfix">
						<input type="text" id="chatMessage"
							placeholder="Enter a message...." autocomplete="off"
							class="form-control" />
						<button type="submit" class="glyphicon glyphicon-share-alt">Send</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>