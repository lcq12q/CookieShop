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
 * 从购物车移除某商品（供页面 AJAX 调用）。
 *
 * <p>约定：只返回纯文本 {@code ok} 或 {@code fail}；不跳转页面。</p>
 *
 * <p>说明：早期前端 {@code deletes()} 调用的是 {@code goods_delete}，但项目里
 * 一直缺少对应的 Servlet，导致请求 404、成功回调永不执行、删除无效。</p>
 */
@WebServlet("/goods_delete")
public class GoodsDeleteServlet extends HttpServlet {

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

		cart.removeItem(id);
		out.write("ok");
		out.flush();
	}
}
