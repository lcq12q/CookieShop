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
 * 查询当前会话购物车的商品总数量（供头部角标刷新使用）。
 *
 * <p>返回纯文本数字，例如 {@code 3}；购物车为空或不存在时返回 {@code 0}。</p>
 *
 * <p>把它单独拆成一个接口的好处：前端只要拿到“权威数量”，
 * 就不用再去解析 goods_buy / goods_lessen / goods_delete 各自的响应格式，
 * 任何一个操作结束后调用一次即可把角标校准回正确值。</p>
 */
@WebServlet("/cart_count")
public class CartCountServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// 角标是实时数据，禁止浏览器缓存
		res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		res.setContentType("text/plain;charset=UTF-8");

		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(false);
		Cart cart = session == null ? null : (Cart) session.getAttribute("cart");
		out.write(String.valueOf(cart == null ? 0 : cart.getTotalCount()));
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doGet(req, res);
	}
}
