/**
 * 把角标数字改成指定值
 * @param {number|string} count 购物车最新商品总数量
 * @returns {boolean} 是否成功写入页面
 */
function updateCartBadge(count) {
	var n = parseInt(count, 10)
	if (isNaN(n)) {
		return false
	}
	var $num = $(".card_num")
	if (!$num.length) {
		return false
	}
	$num.text(n)
	// 轻轻闪一下，让用户注意到数字变了
	$num.stop(true, true).animate({opacity: 0.25}, 120).animate({opacity: 1}, 180)
	return true
}

/**
 * 解析 /cart_data 返回的 JSON，解析失败返回 null
 */
function parseCart(data) {
	try {
		return JSON.parse($.trim(data || "{}"))
	} catch (e) {
		return null
	}
}

/**
 * 把金额格式化为 0.00
 */
function formatMoney(n) {
	var v = parseFloat(n)
	if (isNaN(v)) {
		return "0.00"
	}
	return v.toFixed(2)
}

/**
 * 购物车为空时，在列表区显示提示
 */
function checkEmptyCart() {
	var $list = $("#cart-items-list")
	if ($list.length && $list.find(".cart-item-row").length === 0) {
		$list.html('<div class="empty-cart-tip"><h3>购物车空空如也，去 <a href="index.jsp">首页</a> 逛逛吧～</h3></div>')
	}
}

/**
 * 从服务端拉取购物车全量数据并刷新页面（角标 + 每行数量 + 总金额 + 已删行）。
 * 这是“实时更新”的核心：任何加购/减购/删除操作结束后都调用一次即可。
 * @param {function} [done] 刷新完成后的回调
 */
function refreshCart(done) {
	$.get("cart_data", {_: new Date().getTime()}, function (data) {
		var cart = parseCart(data)
		if (!cart) {
			syncCartBadge()   // 退化方案：至少把角标校准对
			if (typeof done === "function") {
				done()
			}
			return
		}
		// 1. 顶部角标
		updateCartBadge(cart.totalCount)

		// 2. 每行“数量” + 记录仍在购物车里的商品 id
		var presentIds = {}
		$.each(cart.items, function (i, it) {
			presentIds[it.goodsId] = true
			var $qty = $("#qty-" + it.goodsId)
			if ($qty.length) {
				$qty.text("数量: " + it.quantity)
			}
		})

		// 3. 已经被删空的行，淡出移除
		$(".cart-item-row").each(function () {
			var gid = $(this).data("goods")
			if (gid != null && gid !== "" && !presentIds[gid]) {
				$(this).fadeOut(150, function () {
					$(this).remove()
					checkEmptyCart()
				})
			}
		})

		// 4. 订单总金额
		var $amt = $("#cart-total-amount")
		if ($amt.length) {
			$amt.text(formatMoney(cart.totalPrice))
		}

		checkEmptyCart()
		if (typeof done === "function") {
			done()
		}
	}).fail(function () {
		// 即便数据接口异常，也尽量校准角标
		syncCartBadge()
		if (typeof done === "function") {
			done()
		}
	})
}

/**
 * 从服务端拉取购物车权威数量并刷新角标（轻量版，用于非购物车页）。
 * 不依赖任何业务接口的响应格式。
 * @param {function} [done] 刷新完成后的回调
 */
function syncCartBadge(done) {
	$.get("cart_count", {_: new Date().getTime()}, function (data) {
		updateCartBadge($.trim(data || ""))
		if (typeof done === "function") {
			done()
		}
	}).fail(function () {
		if (typeof done === "function") {
			done()
		}
	})
}

/**
 * 加入购物车
 * 服务端成功返回 ok:总数量，失败返回 fail
 */
function buy(goodsid) {
	$.post("goods_buy", {goodsid: goodsid}, function (data) {
		data = $.trim(data || "")
		if (data === "fail") {
			msg("库存不足,请购买其他商品!")
			return
		}
		refreshCart()
		msg("添加到购物车!")
	}).fail(function () {
		// 兼容老版本服务端（写完 ok 又 sendRedirect 导致 404）：仍然同步一次
		refreshCart()
		msg("添加到购物车!")
	})
}

/**
 * 购物车数量减少
 */
function lessen(goodsid) {
	$.post("goods_lessen", {goodsid: goodsid}, function () {
		refreshCart()
		msg("操作成功!")
	}).fail(function () {
		msg("减少失败,请稍后再试!")
	})
}

/**
 * 购物车删除
 */
function deletes(goodsid) {
	$.post("goods_delete", {goodsid: goodsid}, function () {
		refreshCart()
		msg("删除成功!")
	}).fail(function () {
		msg("删除失败,请稍后再试!")
	})
}

/**
 * 统一的轻提示：有 layer 就用 layer，没有就退回原生 alert
 * @param {string} text 提示文字
 * @param {function} [callback] 提示结束后执行（layer 才有）
 */
function msg(text, callback) {
	if (window.layer && typeof layer.msg === "function") {
		layer.msg(text, {time: 800}, function () {
			if (typeof callback === "function") {
				callback()
			}
		})
	} else {
		window.alert(text)
		if (typeof callback === "function") {
			callback()
		}
	}
}
