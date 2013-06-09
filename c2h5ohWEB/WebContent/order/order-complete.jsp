<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp" %>
<title>Поръчки</title>
</head>
<body>
<h1>Завършване на поръчка</h1>
<h2>Незавършени поръчки:</h2>

<c:forEach var="order" items="${orders}">
<form action="order" method="GET">
	<input type="hidden" name="action" value="accept"/>
	<input type="hidden" name="orderId" value="${order.id}"/>
	<%@include file="order-view.jsp" %>
	<input type="submit" class="btn" id="${order.id}" value="Завърши!"/>
</form>
</c:forEach>

<%@include file="../footer.jsp" %>

</body>
</html>