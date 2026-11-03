package xyz.lcq12q.controller.goods;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.service.IGoodsService;
import xyz.lcq12q.service.impl.GoodsServiceImpl;
import xyz.lcq12q.utils.Cart;
import xyz.lcq12q.utils.CartItem;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 加入购物车（供页面 AJAX 调用）。
 *
 * <p>约定：本 Servlet 只返回纯文本，不跳转页面。</p>
 * <ul>
 *   <li>成功：{@code ok:总数量}，例如 {@code ok:3}，前端据此直接刷新头部角标；</li>
 *   <li>失败：{@code fail}（库存已满 / 商品不存在 / 参数非法）。</li>
 * </ul>
 *
 * <p>注意：早期版本在 {@code out.write("ok")} 之后又调用了
 * {@code res.sendRedirect("cart.jsp")}，这会让响应变成 302 跳转
 * （且 cart.jsp 并不存在），前端拿到的是 404 页面而不是 "ok"，
 * 于是 {$.post} 的成功回调永远不执行，购物车角标也就不会更新，
 * 只能手动刷新页面才看得到新数量。</p>
 */
@WebServlet("/goods_buy")
public class GoodsBuyServlet extends HttpServlet {

	private IGoodsService goodsService = new GoodsServiceImpl();   // 创建GoodsService层对象

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = res.getWriter();

		// 1. 商品ID校验
		int id;
		try {
			id = Integer.parseInt(req.getParameter("goodsid").trim());
		} catch (Exception e) {
			out.write("fail");
			return;
		}

		// 2. 获取（或创建）会话中的购物车
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		// 3. 商品存在性校验
		Goods goods = goodsService.queryById(id);
		if (goods == null) {
			out.write("fail");
			return;
		}

		// 4. 库存校验：购物车里已有数量不能超过库存
		if (goods.getStock() <= cart.findQuantityById(id)) {
			out.write("fail");
			return;
		}

		// 5. 加入购物车，并把最新总数量回传给前端
		cart.addItem(new CartItem(goods, 1)); // 默认购买数量为1
		out.write("ok:" + cart.getTotalCount());
		out.flush();
	}
}
