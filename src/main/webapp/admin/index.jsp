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
	<meta http-equiv=Content-Type content='text/html; charset=UTF-8'>
	<title>后台管理</title>
	<link rel="stylesheet" href="../css/bootstrap.css"/>
	<style>
		.content-iframe {
			width: 100%;
			height: calc(100vh - 80px);
			border: none;
			display: block;
		}
		body {
			margin: 0;
			padding: 0;
			overflow-x: hidden;
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<%@include file="nav.jsp"%>
<%--	<iframe name="mainFrame" id="mainFrame" src="index.jsp" class="content-iframe" title="主内容区"></iframe>--%>
	<div class="alert alert-success" role="alert">恭喜你! 登录成功了</div>
</div>
</body>
</html>