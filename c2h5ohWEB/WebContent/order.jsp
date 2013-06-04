<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Нова Поръчка</title>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script>
	$(function() {
		$("span.add").click(function() {
			// get the correct fieldset based on the current element
			var $fieldset = $(this).closest('fieldset');
			$('p', $fieldset).first().clone()
					.insertBefore($('p', $fieldset).last());
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

<form name="create-order" action="order" method="POST">
	<h2>Нова поръчка</h2>
	<fieldset>
		<p class="control-group form-inline">
			<select name="products">
				<c:forEach var="product" items="${products}">
					<option value="${product.id}">${product.name}</option>
				</c:forEach>
			</select> 
			<input type="number" name="quantities" class="input-mini" min="1" value="1" /> 
			<span class="remove btn">Премахни</span>
		</p>
		<span class="add btn">Добави продукт</span>
	</fieldset>
	<p>
		<input type="submit" name="submit" value="Създай" class="btn btn-primary" />
	</p>
</form>

</body>
</html>