<%@ page import="xyz.lcq12q.bean.Goods" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/9/11
  Time: 08:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
	<title>商品列表</title>
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
<%@include file="head.jsp"%>
<!--banner-->
<div class="banner">
	<div class="container">
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators" id="olnum">
				<c:forEach var="goods" items="${scrollGoods}" varStatus="status">
				<li data-target="#carousel-example-generic" data-slide-to="${status.index}"<c:if test="${status.index == 0}"> class="active"</c:if>></li>
				</c:forEach>
			</ol>
			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox" id="lunbotu" style="width: 1242px; height: 432px;">
				<c:forEach var="goods" items="${scrollGoods}" varStatus="status">
				<div class="item <c:if test="${status.index == 0}">active</c:if>">
					<h2 class="hdng">
						<a href="goods_detail?id=${goods.id}">${goods.name}</a><span></span>
					</h2>
					<p>今日精选推荐</p>
					<a class="banner_a" href="javascript:;" onclick="buy(${goods.id})">立刻购买</a>
					<div class="banner-text">
						<a href="goods_detail?id=${goods.id}">
							<img src="${pageContext.request.contextPath}${goods.cover}" alt="${goods.name}" width="350" height="350">
						</a>
					</div>
				</div>
				</c:forEach>
			</div>
			<!-- Controls -->
		</div>
	</div>
</div>
<!--//banner-->
<div class="subscribe2"></div>
<!--gallery-->
<div class="gallery">
	<div class="container">
		<div class="alert alert-danger">热销推荐</div>
		<div class="gallery-grids">

			<c:forEach var="goods" items="${hotGoods}">
			<div class="col-md-4 gallery-grid glry-two">
				<a href="goods_detail?id=${goods.id}">
					<img src="${pageContext.request.contextPath}${goods.cover}" class="img-responsive" alt="${goods.name}" width="350" height="350"/>
				</a>
				<div class="gallery-info galrr-info-two">
					<p>
						<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
						<a href="goods_detail?id=${goods.id}">查看详情</a>
					</p>
					<a class="shop" href="javascript:;" onclick="buy(${goods.id})">立刻购买</a>
					<div class="clearfix"></div>
				</div>
				<div class="galy-info">
					<p>${goods.type.name} > ${goods.name}</p>
					<div class="galry">
						<div class="prices">
							<h5 class="item_price">¥ ${goods.price}</h5>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>
		<div class="clearfix"></div>
		<div class="alert alert-info">新品推荐</div>
		<div class="gallery-grids">
			<c:forEach var="goods" items="${anewGoods}">
			<div class="col-md-3 gallery-grid ">
				<a href="goods_detail?id=${goods.id}">
					<img src="${pageContext.request.contextPath}${goods.cover}" class="img-responsive" alt="${goods.name}"/>
				</a>
				<div class="gallery-info">
					<p>
						<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
						<a href="goods_detail?id=${goods.id}">查看详情</a>
					</p>
					<a class="shop" href="javascript:;" onclick="buy(${goods.id})">立刻购买</a>
					<div class="clearfix"></div>
				</div>
				<div class="galy-info">
					<p>${goods.type.name} > ${goods.name}</p>
					<div class="galry">
						<div class="prices">
							<h5 class="item_price">¥ ${goods.price}</h5>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>
</div>
<!--//gallery-->
<!--subscribe-->
<div class="subscribe"></div>
<!--//subscribe-->
<%@include file="foot.jsp"%>
</body>
</html>
