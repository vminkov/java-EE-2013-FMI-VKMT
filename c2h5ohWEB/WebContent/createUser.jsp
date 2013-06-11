<%@page import="c2h5oh.util.Constants"%>
<%@page import="c2h5oh.beans.UserInfoBean"%>
<%@page import="c2h5oh.beans.roles.Role"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Create User</title>

<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<%
		UserInfoBean user = (UserInfoBean) session
				.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
	%>
	<%
		if (user != null && user.getRole().equals(Role.DIRECTOR)) {
	%>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<form class="form-horizontal" method="post" action="CreateUserServlet">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="false">×</button>
				<h3 id="H1">Създай потребител!</h3>
			</div>
			<div class="control-group">
				<div class="controls">
					<label>Имейл: </label><input type="email" name="email" />
				</div>
			<div class="control-group">
				<div class="controls">
					<label>Адрес: </label><input type="text" name="address" />
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label>Заплата: </label><input type="text" name="salary" />
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label>Потр. име: </label><input type="text" name="username" />
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label>Парола: </label><input type="password" name="password" />
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label>Роля:</label> <select name="role">
						<option value="<%=Role.WAITER.toString()%>">Сервитьор</option>
						<option value="<%=Role.DIRECTOR.toString()%>">Директор</option>
						<option value="<%=Role.BARTENDER.toString()%>">Барман</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<input class="btn btn-primary" type="submit" value="Създай!" />
				</div>
			</div>
			
			<div class="modal-footer">
				<a href="index.jsp"><button class="btn" >Начало</button></a>
			</div>
		</form>
	</div>
	<%
		} else {
	%>
	<h1>Не можеш да създаваш нови потребители !</h1>
	<%
		}
	%>
</body>
</html>