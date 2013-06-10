<%@page import="c2h5oh.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
      ((HttpSession) request.getSession()).invalidate ();
	  response.sendRedirect(request.getContextPath() + Constants.LOGIN_PAGE);
%>

<head>
<title>Logout</title>
</head>
<body>
Log out successfully.  
</body>
</html>