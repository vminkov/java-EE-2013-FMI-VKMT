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
</head>
<body>
	<%
		List<User> users = (List<User>) request.getAttribute(Constants.ALL_USERS_REQUEST_ATTRIBUTE);
		
		UserInfoBean user = (UserInfoBean) session
				.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
	%>
	<%
		if (user != null && user.getRole().equals(Role.DIRECTOR)) {
	%>
	<table>
		<tr>
			<td>
				Потр. име
			</td>
			<td>
				Роля
			</td>
			<td>
				Имейл
			</td>
		</tr>
		<% for(User forUser : users){ %>
		<tr>
			<td>
			<%=forUser.getUsername() %>
			</td>
			<td>
			<%=forUser.getEmployee().getRole() %>
			</td>
			<td>
			<%=forUser.getEmail() %>
			</td>
		</tr>
		<%} %>
	</table>
	<%
		}
	%>

</body>
</html>