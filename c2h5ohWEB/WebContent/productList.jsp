<%@page import="com.ProductBean"%>
<%@page import="java.util.List"%>
<%@page import="c2h5oh.jpa.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Product List</title>
	</head>	
	<body>		
<form action="c2h5ohServlet">			
		Product List <br>
	<table border="1">
	
	<%
	String action = request.getParameter("action");
	List<Product> productList = null;
	if(action != null){
		if(action.equals("remove")){
			System.out.println("GPEL action is not null: " + action);
			String idStr = request.getParameter("id");
			System.out.println("GPEL id: " + idStr);
			ProductBean productBean = (ProductBean)session.getAttribute("productBean");
			productBean.remove(Long.parseLong(idStr));
			productList = productBean.listProducts();			
		}
	} else {
		productList = (List<Product>)request.getAttribute("productList");
	}
	System.out.println("GPEL jsp productList size: " + productList.size());
	for ( int i =0; i < productList.size() ; i++)
	{    
		Product product = productList.get(i);
	%>
	
	<tr >
	<td width="100px"> <%=product.getId()%></td>
	<td width="100px"> <%=product.getName()%></td>
	<td width="100px"><%=product.getPrice()%> </td>
	<td width="100px"><a href="product.jsp?action=edit&id=<%=product.getId()%>"><input  type="button" value="Edit" name="edit"/></a> </td>
	<td width="100px"><a href="productList.jsp?action=remove&id=<%=product.getId()%>"><input  type="button" value="Remove" name="remove"/></a> </td>
	</tr>
	
	<% 	
	}
	request.setAttribute("productList", productList);
	%>
	
	<a href="product.jsp?action=add"><input  type="button" value="Add" name="add"/></a> 
	</table>
						
</form>	

	</body>	
</html>