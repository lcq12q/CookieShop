<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<title>下单成功</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
</head>
<body>
<%@include file="head.jsp"%>

<div class="container" style="padding:70px 0;text-align:center;">
	<h2>🎉 下单成功！</h2>
	<p style="margin-top:20px;">订单编号：<b>#${orderId}</b></p>
	<p>订单金额：<b style="color:#e74c3c;">¥ ${order.total}</b></p>
	<p>共 <b>${order.amount}</b> 件商品，我们会尽快为你发货～</p>
	<p style="margin-top:30px;">
		<a class="btn btn-primary" href="index.jsp">继续购物</a>
		<a class="btn btn-default" href="order_list">查看我的订单</a>
	</p>
</div>

<%@include file="foot.jsp"%>
</body>
</html>
