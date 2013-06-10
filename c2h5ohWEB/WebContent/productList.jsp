<%@page import="c2h5oh.beans.ProductBean"%>
<%@page import="java.util.List"%>
<%@page import="c2h5oh.jpa.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<title>Product List</title>
</head>
<body>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false">×</button>
			<h3 id="H1">Прегледай продуктите</h3>
		</div>
		<div class="control-group">
			<div class="controls">


				<table class="table table-bordered">

					<%
						String action = request.getParameter("action");
						List<Product> productList = null;
						if (action != null) {
							if (action.equals("remove")) {
								String idStr = request.getParameter("id");
								ProductBean productBean = (ProductBean) session
										.getAttribute("productBean");
								productBean.remove(Long.parseLong(idStr));
								productList = productBean.listProducts();
							}
						} else {
							productList = (List<Product>) request
									.getAttribute("productList");
						}
						if (productList != null) {
							for (int i = 0; i < productList.size(); i++) {
								Product product = productList.get(i);
					%>

					<tr>
						<td width="100px"><%=product.getId()%></td>
						<td width="100px"><%=product.getName()%></td>
						<td width="100px"><%=product.getPrice()%></td>
						<td width="100px"><a
							href="product.jsp?action=edit&id=<%=product.getId()%>"><input
								type="button" class="btn" value="Редактирай" name="edit" /></a></td>
						<td width="100px"><a
							href="productList.jsp?action=remove&id=<%=product.getId()%>"><input
								type="button" class="btn" value="Изтрий" name="remove" /></a></td>
					</tr>

					<%
						}
							request.setAttribute("productList", productList);
						}
					%>
				</table>
				<div class="control-group">
					<a href="product.jsp?action=add"><input class="btn btn-primary" type="button"
						value="Добави" name="add" /></a>
				</div>
				<div class="control-group">
					<div class="modal-footer">
						<a href="index.jsp"><button class="btn">Начало</button></a>
					</div>
				</div>
			</div>
		</div>
</body>
</html>