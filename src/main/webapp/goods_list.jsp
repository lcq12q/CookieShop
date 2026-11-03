<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/10/27
  Time: 09:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<title>商品列表</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/simpleCart.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="js/cart.js?v=20261103b"></script>
</head>
<body>
<!--header-->
<%@include file="head.jsp"%>
<!--//header-->
<!--products-->
<div class="products">
	<div class="container">
		<h2>
			<c:if test="${empty param.typeid}">全部系列</c:if>
			<c:if test="${!empty param.typeid}">${param.typeid}${typeName}</c:if>
		</h2>
		<div class="col-md-12 product-model-sec">
			<c:forEach var="goods" items="${p.list}">
			<div class="product-grid">
				<a href="goods_detail?id=${goods.id}">
					<div class="more-product"><span> </span></div>
					<div class="product-img b-link-stripe b-animate-go  thickbox">
						<img src="${pageContext.request.contextPath}${goods.cover}" class="img-responsive" alt="${goods.name}" width="240" height="240">
						<div class="b-wrapper">
							<h4 class="b-animate b-from-left  b-delay03">
								<button href="goods_detail?id=${goods.id}">查看详情</button>
							</h4>
						</div>
					</div>
				</a>
				<div class="product-info simpleCart_shelfItem">
					<div class="product-info-cust prt_name">
						<h4>${goods.name}</h4>
						<span class="item_price">¥ ${goods.price}</span>
						<input type="button" class="item_add items" value="加入购物车" onclick="buy(${goods.id})">
						<div class="clearfix"> </div>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>
		<div style='text-align:center;' id="pageContainer">
			<c:choose>
				<c:when test="${not empty param.typeid}">
					<%-- 按分类查看：分页链接保留 typeid --%>
					<jsp:include page="page.jsp">
						<jsp:param name="url" value="${pageContext.request.contextPath}/goods_list"/>
						<jsp:param name="param" value="&typeid=${param.typeid}"/>
					</jsp:include>
				</c:when>
				<c:otherwise>
					<%-- 全部商品：分页链接不带额外参数 --%>
					<jsp:include page="page.jsp">
						<jsp:param name="url" value="${pageContext.request.contextPath}/goods_list"/>
						<jsp:param name="param" value=""/>
					</jsp:include>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<!--//products-->
<!--footer-->
<%@include file="foot.jsp"%>
<!--//footer-->
</body>
</html>
