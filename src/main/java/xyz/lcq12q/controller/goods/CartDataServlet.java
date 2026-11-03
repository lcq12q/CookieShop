package xyz.lcq12q.controller.goods;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.utils.Cart;
import xyz.lcq12q.utils.CartItem;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 返回当前会话购物车的全量数据（供页面局部刷新使用）。
 *
 * <p>返回 JSON，例如：</p>
 * <pre>{"totalCount":3,"totalPrice":59.0,"items":[{"goodsId":5,"quantity":2}]}</pre>
 *
 * <p>前端 {@code refreshCart()} 拿到这份权威数据后，会同步：顶部角标、每个商品行的
 * “数量”、订单总金额，并在某商品被删空时移除对应行。这样无论加购/减购/删除，
 * 页面都不需要整页刷新即可保持一致。</p>
 */
@WebServlet("/cart_data")
public class CartDataServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// 实时数据，禁止缓存
		res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		res.setContentType("application/json;charset=UTF-8");

		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(false);
		Cart cart = session == null ? null : (Cart) session.getAttribute("cart");
		if (cart == null) {
			out.write("{\"totalCount\":0,\"totalPrice\":0,\"items\":[]}");
			out.flush();
			return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{\"totalCount\":").append(cart.getTotalCount())
		  .append(",\"totalPrice\":").append(cart.getTotalPrice())
		  .append(",\"items\":[");

		List<CartItem> items = cart.getItems();
		for (int i = 0; i < items.size(); i++) {
			CartItem it = items.get(i);
			if (i > 0) {
				sb.append(",");
			}
			sb.append("{\"goodsId\":").append(it.getGoods().getId())
			  .append(",\"quantity\":").append(it.getQuantity()).append("}");
		}
		sb.append("]}");
		out.write(sb.toString());
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doGet(req, res);
	}
}
