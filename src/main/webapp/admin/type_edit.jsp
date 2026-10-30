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
	<title>类目编辑</title>
	<meta charset="utf-8"/>
	<link rel="stylesheet" href="css/bootstrap.css"/>
</head>
<body>
<div class="container-fluid">
	<%@include file="nav.jsp"%>
	<br><br>
	<!--msg不为空则显示-->
	<c:if test="${not empty msg}">
		<div class="alert alert-danger">${msg}</div>
	</c:if>
	<form class="form-horizontal" action="type_edit" method="post">
		<input type="hidden" name="id" value="${param.id}">
		<div class="form-group">
			<label for="input_name" class="col-sm-1 control-label">类目名称</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name" name="name" value="${param.name}" required="required">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="btn btn-success">提交修改</button>
			</div>
		</div>
	</form>
	<span style="color:red;"></span>
</div>
</body>
</html>