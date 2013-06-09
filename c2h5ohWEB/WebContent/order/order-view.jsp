<!-- order view fragment -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="order">
	<label>Номер:</label> 
	<span>${order.id}</span>
	
	<label>Продукти:</label>
	<ul>
	<c:forEach var="orderItem" items="${order.orderItem}"> 
		<li>
			${orderItem.product.name} x ${orderItem.quantity}
		</li>	
	</c:forEach>
	</ul>
</div>