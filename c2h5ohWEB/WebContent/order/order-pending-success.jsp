<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>
<title>Поръчки</title>
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
			<h2>Поръчка с номер ${order.id} е изпълнена от барман.</h2>
		</div>
		<div class="modal-footer">
			<a href="index.jsp"><button class="btn">Начало</button></a>
		</div>
	</div>
</body>
</html>