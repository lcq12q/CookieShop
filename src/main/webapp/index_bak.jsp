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
	<script type="text/javascript" src="js/cart.js"></script>
</head>
<body>
<%@include file="head.jsp"%>
<!--banner-->
<%
	List<Goods> scrollGoods = (List<Goods>) request.getAttribute("scrollGoods");
	int scrollSize = (scrollGoods == null) ? 0 : scrollGoods.size();
%>
<div class="banner">
	<div class="container">
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators" id="olnum">
				<%
					for (int i = 0; i < scrollSize; i++) {
				%>
				<li data-target="#carousel-example-generic" data-slide-to="<%=i%>"<%=(i == 0 ? " class=\"active\"" : "")%>></li>
				<% } %>
			</ol>
			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox" id="lunbotu" style="width: 1242px; height: 432px;">
				<%
				for (int i = 0; i < scrollGoods.size(); i++) {
					Goods goods = scrollGoods.get(i);
				%>
				<div class="item <%=(i == 0 ? "active" : "")%>">
					<h2 class="hdng">
						<a href="goods_detail?id=<%=goods.getId()%>"><%=goods.getName()%></a><span></span>
					</h2>
					<p>今日精选推荐</p>
					<a class="banner_a" href="javascript:;" onclick="buy(<%=goods.getId()%>)">立刻购买</a>
					<div class="banner-text">
						<a href="goods_detail?id=<%=goods.getId()%>">
							<img src="<%= request.getContextPath() %>/<%=goods.getCover()%>" alt="<%=goods.getName()%>" width="350" height="350">
						</a>
					</div>
				</div>
				<% } %>
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
			<%
				List<Goods> hotGoods = (List<Goods>) request.getAttribute("hotGoods");
				if (hotGoods != null) {
					for (Goods goods : hotGoods) {
			%>
			<div class="col-md-4 gallery-grid glry-two">
				<a href="goods_detail?id=<%=goods.getId()%>">
					<img src="<%= request.getContextPath() %>/<%=goods.getCover()%>" class="img-responsive" alt="<%=goods.getName()%>" width="350" height="350"/>
				</a>
				<div class="gallery-info galrr-info-two">
					<p>
						<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
						<a href="goods_detail?id=<%=goods.getId()%>">查看详情</a>
					</p>
					<a class="shop" href="javascript:;" onclick="buy(<%=goods.getId()%>)">立刻购买</a>
					<div class="clearfix"></div>
				</div>
				<div class="galy-info">
					<p><%=goods.getType().getName()%> > <%=goods.getName()%></p>
					<div class="galry">
						<div class="prices">
							<h5 class="item_price">¥ <%=goods.getPrice()%></h5>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			<%
					}
				}
			%>
		</div>
		<div class="clearfix"></div>
		<div class="alert alert-info">新品推荐</div>
		<div class="gallery-grids">
			<%
				List<Goods> anewGoods = (List<Goods>) request.getAttribute("anewGoods");
				if (anewGoods != null) {
					for (Goods goods : anewGoods) {
			%>
			<div class="col-md-3 gallery-grid ">
				<a href="goods_detail?id=<%=goods.getId()%>">
					<img src="<%= request.getContextPath() %>/<%=goods.getCover()%>" class="img-responsive" alt="<%=goods.getName()%>"/>
				</a>
				<div class="gallery-info">
					<p>
						<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
						<a href="goods_detail?id=<%=goods.getId()%>">查看详情</a>
					</p>
					<a class="shop" href="javascript:;" onclick="buy(<%=goods.getId()%>)">立刻购买</a>
					<div class="clearfix"></div>
				</div>
				<div class="galy-info">
					<p><%=goods.getType().getName()%> > <%=goods.getName()%></p>
					<div class="galry">
						<div class="prices">
							<h5 class="item_price">¥ <%=goods.getPrice()%></h5>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			<%
					}
				}
			%>
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
