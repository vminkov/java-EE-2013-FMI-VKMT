<%@page import="c2h5oh.beans.UserInfoBean"%>
<%@page import="c2h5oh.util.Constants"%>
<%@page import="c2h5oh.jpa.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GlassFish JSP Page</title>
</head>
<body>
	<h1>Hello World!</h1>
	<%
		UserInfoBean user = (UserInfoBean) session
				.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
	%>
	<%
		if (user != null) {
	%>
	<%=user.getUsername()%>
	<%
		}
	%>
	<a href="createUser.jsp">Създай потребител</a>
	<br>
	<a href="ListUsersServlet">Виж потребителите</a>
</body>
</html>
