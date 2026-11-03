<!DOCTYPE html>
<!-- saved from url=(0049)http://localhost:8080/CookieShop2403/order_submit -->
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户登录</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link type="text/css" rel="stylesheet" href="./order_submit_files/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="./order_submit_files/style.css">
	<script type="text/javascript" src="./order_submit_files/jquery.min.js"></script>
	<script type="text/javascript" src="./order_submit_files/bootstrap.min.js"></script>
	<script type="text/javascript" src="./order_submit_files/simpleCart.min.js"></script>
</head>
<body>
<!--header-->
<%@include file="head.jsp"%>
<!--//header-->
<div class="cart-items">
	<div class="container">
		<h2>确认收货信息</h2>
		<form class="form-horizontal" action="order_confirm" method="post" id="payform">
			<div class="row">
				<label class="control-label col-md-1">收货人</label>
				<div class="col-md-6">
					<input type="text" class="form-control" name="name" value="管理员" style="height:auto;padding:10px;" placeholder="输入收货人" required="required"><br>
				</div>
			</div>
			<div class="row">
				<label class="control-label col-md-1">收货电话</label>
				<div class="col-md-6">
					<input type="text" class="form-control" name="phone" value="13873244259" style="height:auto;padding:10px;" placeholder="输入收货电话" required="required"><br>
				</div>
			</div>
			<div class="row">
				<label class="control-label col-md-1">收货地址</label>
				<div class="col-md-6">
					<input type="text" class="form-control" name="address" value="中国" style="height:auto;padding:10px;" placeholder="输入收货地址" required="required"><br>
				</div>
			</div>
			<br><hr><br>
			<h2>选择支付方式</h2>
			<h3>支付金额: 38.0</h3><br><br>
			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<label>
					<div class="thumbnail">
						<input type="radio" name="paytype" value="1" checked="checked">
						<img src="./order_submit_files/wechat.jpg" alt="微信支付">
					</div>
				</label>
			</div>
			<div class="col-sm-6 col-md-4 col-lg-3 ">
				<label>
					<div class="thumbnail">
						<input type="radio" name="paytype" value="2">
						<img src="./order_submit_files/alipay.jpg" alt="支付宝支付">
					</div>
				</label>
			</div>
			<div class="clearfix"> </div>
			<div class="register-but text-center">
				<input type="submit" value="确认订单">
				<div class="clearfix"> </div>
			</div>
		</form>
	</div>
</div>
<!--footer-->
<%@include file="foot.jsp"%>
<!--//footer-->
</body>