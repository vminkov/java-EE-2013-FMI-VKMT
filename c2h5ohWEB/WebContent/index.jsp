<%@page import="c2h5oh.beans.roles.Role"%>
<%@page import="c2h5oh.beans.UserInfoBean"%>
<%@page import="c2h5oh.util.Constants"%>
<%@page import="c2h5oh.jpa.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Начало</title>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
</head>
<body>
	<%
		UserInfoBean user = (UserInfoBean) session
				.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
	%>
	<%
		if (user != null) {
	%>
		<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="false">×</button>
				<h3 id="H1">Здравей, <%=user.getName() %>!</h3>
			</div>
			<div class="modal-body">
			<% if(Role.DIRECTOR.equals(user.getRole())) {%>
				<div class="control-group">
					<a href="createUser.jsp"><button class="btn">Създай потребител</button></a>
				</div>
				<div class="control-group">
					<a href="ListUsersServlet"><button class="btn">Виж потребителите</button></a>
				</div>
				<div class="control-group">
					<a href="product"><button class="btn">Прегледай продуктите</button></a>
				</div>
				<div class="control-group">
					<a href="oborot.jsp"><button class="btn">Оборот</button></a>
				</div>
				<%} else if(Role.WAITER.equals(user.getRole())) { %>
				
				<div class="control-group">
					<a href="order?action=create-form"><button class="btn">Създаване на поръчка</button></a>
				</div>
				<div class="control-group">
					<a href="order?action=complete-form"><button class="btn">Завършване на поръчка</button></a>
				</div>
				<%} else if(Role.BARTENDER.equals(user.getRole())){ %>
				
				<div class="control-group">
					<a href="order?action=accept-form"><button class="btn">Приемане на поръчка</button></a>
				</div>
				<div class="control-group">
					<a href="order?action=pending-form"><button class="btn">Обработка на поръчка</button></a>
				</div>
				<%} %>
			</div>
			<div class="modal-footer">
				<a href="logout.jsp"><button class="btn btn-primary">Излез</button></a>
			</div>
	</div>
	<%
		}
	%>
</body>
</html>
