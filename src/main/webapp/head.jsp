<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/10/13
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="header">
	<div class="container">
		<nav class="navbar navbar-default" role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<h1 class="navbar-brand"><a href="index"></a></h1>
			</div>
			<!--navbar-header-->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="nav">
					<li><a href="index" class="active">首页</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle " data-toggle="dropdown">商品分类<b class="caret"></b></a>
						<ul class="dropdown-menu multi-column columns-2">
							<li>
								<div class="row">
									<div class="col-sm-12">
										<h4>商品分类</h4>
										<ul class="multi-column-dropdown">
											<li>
												<a class="list" href="goods_list">全部系列</a>
											</li>
											<c:forEach var="type" items="${applicationScope.types}">
											<li>
												<a class="list" href="goods_list?typeid=${type.id}">${type.name}</a>
											</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</li>
						</ul>
					</li>
					<li>
						<a href="goodsrecommend_list?type=2" >热销</a>
					</li>
					<li>
						<a href="goodsrecommend_list?type=3" >新品</a>
					</li>
					<c:if test="${empty sessionScope.loginUser}">
					<li>
						<a href="user_register.jsp" >注册</a>
					</li>
					<li>
						<a href="user_login.jsp" >登录</a>
					</li>
					</c:if>
					<c:if test="${not empty sessionScope.loginUser}">
					<li>
						<a href="order_list" >我的订单</a>
					</li>
					<li>
						<a href="user_center.jsp" class="active">个人中心</a>
					</li>
					<li>
						<a href="user_logout" >退出</a>
					</li>
					</c:if>
					<c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.isAdmin}">
					<li>
						<a href="admin/index.jsp" target="_blank">后台管理</a>
					</li>
					</c:if>
				</ul>
				<!--/.navbar-collapse-->
			</div>
			<!--//navbar-header-->
		</nav>
		<div class="header-info">
			<div class="header-right search-box">
				<a href="javascript:;"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
				<div class="search">
					<form class="navbar-form" action="goods_search" onsubmit="return false" role="search">
						<input type="text" class="form-control" name="keyword" id="searchInput">
						<button type="submit" class="btn btn-default " aria-label="Left Align" id="searchBtn">搜索</button>
					</form>
				</div>
			</div>

			<div class="header-right cart">
				<a href="goods_cart.jsp.htm">
					<span class="glyphicon glyphicon-shopping-cart " aria-hidden="true"><span class="card_num">0</span></span>
				</a>
			</div>
			<div class="clearfix"> </div>
		</div>
		<div class="clearfix"> </div>
	</div>
</div>
<script>
	let nav = $("#nav a").children("a").addClass("active")
	// 搜索触发逻辑（直接在当前页面发起异步请求）
	function doSearch() {
		let keyword = $("#searchInput").val().trim()
		if (keyword !== "") {
			window.location.href = "goods_search?keyword=" + encodeURIComponent(keyword)
		} else {
			alert("请输入搜索关键词！")
		}
	}

	$("#searchBtn").click(function() {
		doSearch()
	})

	$("#searchInput").keypress(function(e) {
		if (e.which === 13) {
			doSearch()
			return false
		}
	})
</script>