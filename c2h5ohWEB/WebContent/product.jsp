<%@page import="c2h5oh.jpa.Product"%>
<%@page import="com.ProductBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title> My first JSP   </title>
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
		System.out.println("GPEL product edit jsp id:" + idStr);
		Long id = Long.parseLong(idStr);
		product = productBean.getProduct(id);
		name = product.getName();
		price = product.getPrice();
	} 
	System.out.println("GPEL action: " + action);
	
	%>
		<form action="c2h5ohServlet" method="post">			
			 Please enter a color <br>
			 <%
			 if(action.equals("edit")){
			 %>			 
			<input type="hidden" name="id"size="20px" value="<%=idStr%>">
			 <% }
			 %>
			<input type="text" name="name"size="20px" value="<%=name%>">
			<input type="text" name="price"size="20px" value="<%=price%>">
			<input type="submit" value="submit">
									
		</form>	
	</body>	
</html>