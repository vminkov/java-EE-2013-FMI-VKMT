<%@page import="c2h5oh.util.Constants"%>
<%@page import="c2h5oh.beans.UserInfoBean"%>
<%@page import="c2h5oh.beans.roles.Role"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Create User</title>
</head>
<body>
	<%
		UserInfoBean user = (UserInfoBean) session
				.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
	%>
	<%
		if (user != null && user.getRole().equals(Role.DIRECTOR)) {
	%>
	<%=user.getRole().toString()%>

	<form method="post" action="CreateUserServlet">
		<label>Потр. име: </label><input type="text" name="username" /> <br>
		<label>Парола: </label><input type="password" name="password" /> <br>
		<label>Имейл: </label><input type="text" name="email" /> <br> <label>Роля:
		</label><select name="role">
			<option value="<%=Role.WAITER.toString()%>"><%=Role.WAITER.toString()%></option>
			<option value="<%=Role.DIRECTOR.toString()%>"><%=Role.DIRECTOR.toString()%></option>
			<option value="<%=Role.BARTENDER.toString()%>"><%=Role.BARTENDER.toString()%></option>
		</select>
		<input type="submit" value="Създай!"/>
	</form>
	<%
		} else {
	%>
	<h1>Не можеш да създаваш нови потребители !</h1>
	<%
		if (user != null) {
	%>
	<%=user.getRole()%>
	<%
		} else {
	%>
	user is null
	<%
		}
	%>
	<%
		}
	%>
</body>
</html>