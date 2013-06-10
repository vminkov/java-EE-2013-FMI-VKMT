<%@page import="c2h5oh.util.Constants"%>
<%@page import="c2h5oh.beans.UserInfoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Оборот</title>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<%
		UserInfoBean user = (UserInfoBean) session
				.getAttribute(Constants.USER_INFO_SESSION_ATTR_NAME);
	%>
	<%
		if (user != null) {
	%>
	<div id="enterForm" class="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false">×</button>
			<h3 id="H1">Преглед на оборота</h3>
		</div>
		<div class="modal-body">
			<div class="control-group">
				<div class="controls">
					<label>От: </label> <input type="date" id="fromDate"
						name="fromDate" />
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label>До: </label> <input type="date" id="toDate" name="toDate" />
				</div>
			</div>
			<div id="tableWrapper">
				<table class="table table-bordered" id="table">
					<tbody>

					</tbody>
				</table>
			</div>

		</div>
		<div class="modal-footer">
			<a href="index.jsp"><button class="btn">Начало</button></a>
		</div>
	</div>
	<script>
			function fillProducts(data){
				var sum = 0;
				var str = "";
				str += "<tr>";
				$.each(data, function (key, value){
					str += "<td>";
					str += key;
					str += "</td>";
				});
				str += "<td>Общо</td></tr>";
				str += "<tr>";
				$.each(data, function (key, value){
					str += "<td>";
					var subtotal = value.count * value.price;
					sum += subtotal;
					str += value.count + " (" + subtotal + " лв.)";
					str += "</td>";
				});
				str += "<td>" + sum + " лв.</td></tr>";
 				$('#table > tbody:last').append(str);
 				
			}
			
			function updateTable(data){
				$("#table").find("tr").remove();
				fillProducts(data);
			}
			function getUpdates(){
				if(dates.from && dates.to){
					$.ajax({url: "rest/oborot", method: "POST", data : {"user" : user, "fromDate": dates.from, "toDate": dates.to}})
						.success(function(data){
							console.log(data);
							updateTable(data);
						});
				}
			}
			
			var dates = {};
			$("#fromDate").change(function(){
				dates.from = $("#fromDate").val();
				getUpdates();
			});
			$("#toDate").change(function(){
				dates.to = $("#toDate").val();
				getUpdates();
			});

			var user = {};
			user.username = "<%=user.getUsername()%>";
			user.token = "<%=user.getPasswordHash()%>";
	</script>
	<%
		} else {
	%>

	<h1>Този панел е видим само за директори!</h1>
	<%
		}
	%>

</body>
</html>