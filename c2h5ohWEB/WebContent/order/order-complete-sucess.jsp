<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<title>Поръчка</title>
</head>
<body>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false">×</button>
			<h3 id="H1">Завършване на поръчка</h3>
		</div>
		<div class="modal-body">
			<h2>Поръчка с номер ${order.id} на цена ${order.totalPrice} лв.
				е завършена успешно.</h2>
		</div>
		<div class="modal-footer">
			<a href="index.jsp"><button class="btn">Начало</button></a>
		</div>
	</div>
</body>
</html>