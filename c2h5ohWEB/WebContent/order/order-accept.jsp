<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>
<title>Поръчки</title>
<title>Приемане на поръчка</title>
</head>
<body>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false">×</button>
			<h3 id="H1">Приемане на поръчка</h3>
		</div>
		<div class="modal-body">
			<h1>Неприети поръчки:</h1>

			<c:forEach var="order" items="${orders}">
				<form action="order" method="GET">
					<input type="hidden" name="action" value="accept" /> <input
						type="hidden" name="orderId" value="${order.id}" />
					<%@include file="order-view.jsp"%>
					<input type="submit" class="btn" id="${order.id}" value="Приеми!" />
				</form>
			</c:forEach>
		</div>
		<div class="modal-footer">
			<a href="index.jsp"><button class="btn">Начало</button></a>
		</div>
	</div>
</body>
</html>