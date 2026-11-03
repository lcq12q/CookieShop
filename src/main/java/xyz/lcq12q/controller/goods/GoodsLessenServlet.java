package xyz.lcq12q.controller.goods;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.utils.Cart;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 购物车数量 -1（供页面 AJAX 调用）。
 *
 * <p>约定：只返回纯文本 {@code ok} 或 {@code fail}；本 Servlet 不跳转页面，
 * 由前端拿到回调后自行刷新购物车区域（refreshCart）。</p>
 *
 * <p>说明：早期前端 {@code lessen()} 调用的是 {@code goods_lessen}，但项目里
 * 一直缺少对应的 Servlet，导致请求 404、成功回调永不执行、数量无法更新。</p>
 */
@WebServlet("/goods_lessen")
public class GoodsLessenServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = res.getWriter();

		int id;
		try {
			id = Integer.parseInt(req.getParameter("goodsid").trim());
		} catch (Exception e) {
			out.write("fail");
			return;
		}

		HttpSession session = req.getSession(false);
		Cart cart = session == null ? null : (Cart) session.getAttribute("cart");
		if (cart == null) {
			out.write("fail");
			return;
		}

		cart.decreaseItem(id);   // 数量>1 则 -1；数量为 1 则直接移除该商品
		out.write("ok");
		out.flush();
	}
}
