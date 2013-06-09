<%@page import="c2h5oh.beans.UserInfoBean"%>
<%@page import="c2h5oh.beans.roles.Role"%>
<%@page import="c2h5oh.util.JspUtils"%>
<%@page import="c2h5oh.util.Constants"%>
<%@page import="c2h5oh.jpa.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>

<%!UserInfoBean userInfo;%>
<%
	userInfo = (UserInfoBean) ((HttpSession) session)
			.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
%>
<!--  insert header  -->
<%
	if (userInfo != null) {
		response.sendRedirect(request.getContextPath()
				+ Constants.MAIN_PAGE);
	} else {
	}
%>


<body>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<form class="form-horizontal" method="post" action="userLogin">
			<%
				String message = (String) request
						.getAttribute(Constants.MESSAGE_REQUEST_ATTR_NAME);
				if (message != null) {
			%>
			<span style="color: red;"><%=JspUtils.escapeForHTML(message)%></span>
			<%
				}
			%>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="false">×</button>
				<h3 id="H1">Влез!</h3>
			</div>
			<div class="modal-body">
				<div class="control-group">
					<label class="control-label" for="inputNickname">Потребителско
						име</label>
					<div class="controls">
						<input type="text" id="Text1" placeholder="username"
							name="username" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="pass">Парола</label>
					<div class="controls">
						<input type="password" id="pass" placeholder="password"
							name="password" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<label class="checkbox"> <input type="checkbox"
							name="rememberMe" />Запомни ме! <input type='hidden'
							name='statementId' value=''>
						</label>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<label>Роля: </label><select name="role">
					<option value="<%=Role.WAITER.toString()%>"><%=Role.WAITER.toString()%></option>
					<option value="<%=Role.DIRECTOR.toString()%>"><%=Role.DIRECTOR.toString()%></option>
					<option value="<%=Role.BARTENDER.toString()%>"><%=Role.BARTENDER.toString()%></option>
				</select> <input type="submit" class="btn btn-primary" value="Влез!" />
			</div>
		</form>
	</div>
</body>
</html>