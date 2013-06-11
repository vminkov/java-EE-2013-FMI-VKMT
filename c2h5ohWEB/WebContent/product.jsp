<%@page import="c2h5oh.beans.roles.Role"%>
<%@page import="c2h5oh.util.JspUtils"%>
<%@page import="c2h5oh.util.Constants"%>
<%@page import="c2h5oh.beans.UserInfoBean"%>
<%@page import="c2h5oh.jpa.Product"%>
<%@page import="c2h5oh.beans.ProductBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<title>Products</title>
</head>
<body>
	<%
		UserInfoBean user = (UserInfoBean) session
				.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
	%>
	<%
		if (user != null && user.getRole().equals(Role.DIRECTOR)) {
	%>
	<%
		String action = request.getParameter("action");
			action = JspUtils.escapeForHTML(action);

			ProductBean productBean = (ProductBean) session
					.getAttribute("productBean");
			Product product = null;
			String name = "";
			String price = "";
			String idStr = "";
			if (action.equals("edit")) {
				idStr = request.getParameter("id");
				idStr = JspUtils.escapeForHTML(idStr);
				Long id = Long.parseLong(idStr);
				product = productBean.getProduct(id);
				name = product.getName();
				price = product.getPrice().toPlainString();
			}
	%>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false">×</button>
			<h3 id="H1">
				Създайте продукт, г-н/г-жо,
				<%=user.getName()%>!
			</h3>
		</div>

		<form action="product" method="post">
			<p class="control-group form-inline">
				<%
					if (action.equals("edit")) {
				%>
				<input type="hidden" name="id" size="20px" value="<%=idStr%>" />
				<%
					}
				%>
			</p>

			<div class="control-group">
				<label> Име</label> <input type="text" name="name" value="<%=name%>" />
			</div>

			<div class="control-group">
				<label>Цена</label> <input type="number" name="price"
					value="<%=price%>" />
			</div>
			<div class="control-group">
				<input type="submit" class="btn btn-primary" value="Готово" />
			</div>
		</form>
		<div class="modal-footer">
			<a href="index.jsp"><button class="btn">Начало</button></a>
		</div>
	</div>
	<%
		}
	%>
</body>
</html>