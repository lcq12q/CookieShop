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
 * 从服务端拉取购物车权威数量并刷新角标。
 * 不依赖任何业务接口的响应格式，因此无论加购/减购/删除返回什么，都能把数字校准正确。
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
 * 服务端成功返回 ok:总数量，失败返回 fail；即使返回格式变了也不影响角标刷新。
 */
function buy(goodsid) {
	$.post("goods_buy", {goodsid: goodsid}, function (data) {
		data = $.trim(data || "")
		if (data === "fail") {
			msg("库存不足,请购买其他商品!")
			return
		}
		syncCartBadge(function () {
			msg("添加到购物车!")
		})
	}).fail(function () {
		// 兼容老版本服务端（写完 ok 又 sendRedirect 导致 404）：仍然同步一次角标
		syncCartBadge(function () {
			msg("添加到购物车!")
		})
	})
}

/**
 * 购物车数量减少
 */
function lessen(goodsid) {
	$.post("goods_lessen", {goodsid: goodsid}, function () {
		syncCartBadge(function () {
			msg("操作成功!")
		})
	})
}

/**
 * 购物车删除
 */
function deletes(goodsid) {
	$.post("goods_delete", {goodsid: goodsid}, function () {
		syncCartBadge(function () {
			msg("删除成功!")
		})
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
