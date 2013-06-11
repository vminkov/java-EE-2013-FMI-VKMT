<%@page import="java.util.List"%>
<%@page import="c2h5oh.jpa.User"%>
<%@page import="c2h5oh.controller.UserManager"%>
<%@page import="javax.ejb.EJB"%>
<%@page import="c2h5oh.beans.roles.Role"%>
<%@page import="c2h5oh.util.Constants"%>
<%@page import="c2h5oh.beans.UserInfoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Прегледай потребителите</title>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<%
		List<User> users = (List<User>) request
				.getAttribute(Constants.ALL_USERS_REQUEST_ATTRIBUTE);

		UserInfoBean user = (UserInfoBean) session
				.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
	%>
	<%
		if (user != null && user.getRole().equals(Role.DIRECTOR)) {
	%>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false">×</button>
			<h3 id="H1">Прегледай потребителите</h3>
		</div>
		<div class="modal-body">
			<div class="control-group">
				<div class="controls">

					<table class="table table-bordered">
						<tr>
							<td>Потр. име</td>
							<td>Роля</td>
							<td>Имейл</td>
							<td>Заплата</td>
						</tr>
						<%
							for (User forUser : users) {
						%>
						<tr>
							<td><%=forUser.getUsername()%></td>
							<td><%=forUser.getEmployee().getRole()%></td>
							<td><%=forUser.getEmail()%></td>
							<td><%=forUser.getEmployee().getSalary()%></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
			</div>

			<div class="modal-footer">
				<a href="index.jsp"><button class="btn">Начало</button></a>
			</div>
		</div>

		<%
			} else {
		%>
		<h1>Не можеш да преглеждаш потребителите!</h1>
		<%
			}
		%>
	
</body>
</html>