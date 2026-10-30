<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/10/20
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="index.jsp">蛋糕店后台</a>
		</div>
		<div>
			<ul class="nav navbar-nav">
				<li>
					<a href="order_list" target="mainFrame">订单管理</a>
				</li>
				<li>
					<a href="member-list" target="mainFrame">客户管理</a>
				</li>
				<li>
					<a href="goods_list" target="mainFrame">商品管理</a>
				</li>
				<li>
					<a href="type_list" target="mainFrame">类目管理</a>
				</li>
				<li>
					<a href="../user_logout" onclick="return confirm('确定要退出吗？');">退出</a>
				</li>
			</ul>
		</div>
	</div>
</nav>