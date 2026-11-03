<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/9/11
  Time: 08:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<title>购物车</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="js/cart.js?v=20261103a"></script>
</head>
<body>
	<!--header-->
<!--header-->
<%@include file="head.jsp"%>
<!--//header-->
	<!--//header-->
	<!--cart-items-->
	<div class="cart-items">
		<div class="container">
			<h2>我的购物车</h2>
			<c:forEach var="item" items="${cart.items}">
			<div class="cart-header col-md-6">
                <div class="cart-sec simpleCart_shelfItem">
                    <div class="cart-item cyc">
                        <a href="/goods_detail?id=${item.goods.id}">
                            <img src="${pageContext.request.contextPath}${item.goods.cover}" class="img-responsive">
                        </a>
                    </div>
                    <div class="cart-item-info">
                        <h3><a href="/goods_detail?id=${item.goods.id}">${item.goods.name}</a></h3>
                        <h3><span>单价: ¥ ${item.goods.price}</span></h3>
                        <h3><span>数量: ${item.quantity}</span></h3>
                        <a class="btn btn-info" href="javascript:buy(${item.goods.id});">增加</a>
                        <a class="btn btn-warning" href="javascript:lessen(${item.goods.id});">减少</a>
                        <a class="btn btn-danger" href="javascript:deletes(${item.goods.id});">删除</a>
                    </div>
                    <div class="clearfix"></div>
                </div>
			</div>
			</c:forEach>
			<div class="cart-header col-md-12">
				<hr>
				<h3>订单总金额: ¥ ${cart.totalPrice}</h3>
				<a class="btn btn-success btn-lg" style="margin-left:74%" href="order_submit">提交订单</a>
			</div>
		</div>
	</div>
	<!--//cart-items-->
	<!--footer-->
<!--footer-->
<%@include file="foot.jsp"%>
<!--//footer-->
	<!--//footer-->
</body>
</html>