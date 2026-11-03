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
	<title>商品详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/flexslider.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.flexslider.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="js/cart.js?v=20261103d"></script>
	<script>
		$(function() {
			$('.flexslider').flexslider({
				animation: "slide",
				controlNav: "thumbnails"
			})
		})
	</script>
</head>
<body>
	<!--header-->
<!--header-->
	<%@include file="head.jsp"%>
<!--//header-->
	<!--//header-->
	<!--//single-page-->
	<div class="single">
		<div class="container">
			<div class="single-grids">
				<div class="col-md-4 single-grid">
					<div class="flexslider">
						<ul class="slides">
							<!-- 💡 动态加载商品封面图 -->
							<li data-thumb="${pageContext.request.contextPath}${goods.cover}">
								<div class="thumb-image">
									<img src="${pageContext.request.contextPath}${goods.cover}" data-imagezoom="true" class="img-responsive">
								</div>
							</li>
							<c:if test="${not empty goods.image1}">
							<li data-thumb="${pageContext.request.contextPath}${goods.image1}">
								<div class="thumb-image">
								<img src="${pageContext.request.contextPath}${goods.image1}" data-imagezoom="true" class="img-responsive" alt="${goods.name}">
								</div>
							</li>
							</c:if>
						</ul>
					</div>
				</div>	
				<div class="col-md-4 single-grid simpleCart_shelfItem">		
					<h3>${goods.name}</h3>
					<div class="tag">
						<p>分类 : <a href="goods_list?typeid=${goods.type.id}">${goods.type.name}</a></p>
					</div>
					<p>${goods.intro}</p>
					<div class="galry">
						<div class="prices">
							<h5 class="item_price">¥ ${goods.price}</h5>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="btn_form">
						<a href="javascript:;" class="add-cart item_add" onclick="buy(${goods.id})">加入购物车</a>
					</div>
				</div>
				<div class="col-md-4 single-grid1">
					<!-- <h2>商品分类</h2> -->
					<ul>
                        <li><a  href="goods_list">全部系列</a></li>
						<c:forEach var="type" items="${applicationScope.types}">
							<li><a href="goods_list?typeid=${type.id}">${type.name}</a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
	<!--related-products--><!-- 
	<div class="related-products">
		<div class="container">
			<h3>猜你喜欢</h3>
			<div class="product-model-sec single-product-grids">
				<div class="product-grid single-product">
					<a href="single.html">
					<div class="more-product"><span> </span></div>						
					<div class="product-img b-link-stripe b-animate-go  thickbox">
						<img src="images/m1.png" class="img-responsive" alt="">
						<div class="b-wrapper">
						<h4 class="b-animate b-from-left  b-delay03">							
						<button>View</button>
						</h4>
						</div>
					</div>
					</a>					
					<div class="product-info simpleCart_shelfItem">
						<div class="product-info-cust prt_name">
							<h4>Product #1</h4>								
							<span class="item_price">$2000</span>
							<div class="ofr">
							  <p class="pric1"><del>$2300</del></p>
							  <p class="disc">[15% Off]</p>
							</div>
							<div class="clearfix"> </div>
						</div>												
					</div>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
 -->	<!--related-products-->
	<!--footer-->
<!--footer-->
	<%@include file="foot.jsp"%>
<!--//footer-->
	<!--//footer-->
</body>
</html>