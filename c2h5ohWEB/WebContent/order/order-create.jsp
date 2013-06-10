<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>
<title>Поръчки</title>
<script>
	// jQuery insert/delete functionality
	$(function() {
		$("span.add").click(
				function() {
					// get the correct fieldset based on the current element
					var $fieldset = $(this).closest('fieldset');
					$('p', $fieldset).first().clone().insertBefore(
							$('p', $fieldset).last());
				});

		// use delegate here to avoid adding new 
		// handlers for new elements
		$('fieldset').delegate("span.remove", {
			'click' : function() {
				$(this).parent().remove();
			}
		});
	});
</script>
</head>

<body>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false">×</button>
			<h3 id="H1">Нова поръчка</h3>
		</div>
		<div class="modal-body">
			<form name="create-order" action="order" method="GET">
				<input type="hidden" name="action" value="create">
				<fieldset>
					<p class="control-group form-inline">
						<select name="products">
							<c:forEach var="product" items="${products}">
								<option value="${product.id}">${product.name}</option>
							</c:forEach>
						</select> <input type="number" name="quantities" class="input-mini" min="1"
							value="1" /> <span class="remove btn">Премахни</span>
					</p>
					<span class="add btn">Добави продукт</span>
				</fieldset>
				<br />
				<p>
					<input type="submit" name="submit" value="Създай!"
						class="btn btn-primary" />
				</p>
			</form>
		</div>
		<div class="modal-footer">
			<a href="index.jsp"><button class="btn">Начало</button></a>
		</div>
	</div>
</body>
</html>