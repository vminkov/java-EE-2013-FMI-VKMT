<%@page import="c2h5oh.jpa.Product"%>
<%@page import="c2h5oh.beans.ProductBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
	pageEncoding="ISO-8859-1"%>
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
		
		<title>Products</title>
	</head>	
	<body>		
	
	<%
	String action = request.getParameter("action");
	ProductBean productBean = (ProductBean)session.getAttribute("productBean");
	Product product = null;
	String name = "";
	String price = "";
	String idStr = "";
	if(action.equals("edit")){
		idStr = request.getParameter("id");
		Long id = Long.parseLong(idStr);
		product = productBean.getProduct(id);
		name = product.getName();
		price = product.getPrice().toPlainString();
	} 
	
	%>
		<form action="product" method="post">
		<p class="control-group form-inline">			
			 <%
			 if(action.equals("edit")) {
			 %>
			<input type="hidden" name="id"size="20px" value="<%=idStr%>"/>
			 <% } %>
			 <label> Name</label>
			<input type="text" name="name" value="<%=name%>"/>
			<br/>
			<label>Price</label>
			<input type="text" name="price" value="<%=price%>"/>
			<input type="submit" class="btn" value="submit"/>
			</p>
		</form>
	</body>	
</html>