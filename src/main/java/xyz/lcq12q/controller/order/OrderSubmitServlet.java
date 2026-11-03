package xyz.lcq12q.controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.bean.Order;
import xyz.lcq12q.bean.OrderItem;
import xyz.lcq12q.bean.User;
import xyz.lcq12q.service.IOrderService;
import xyz.lcq12q.service.impl.OrderServiceImpl;
import xyz.lcq12q.utils.Cart;
import xyz.lcq12q.utils.CartItem;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 提交订单：将当前会话中的购物车结算成一张订单。
 *
 * <p>流程：校验登录 → 校验购物车非空 → 组装 Order/OrderItem →
 * 调用 {@code orderService.addOrder}（落 orders / orderitem 并扣减库存）→
 * 清空会话购物车 → 转发到结算成功页。</p>
 *
 * <p>若用户未登录则跳转到登录页；若购物车为空则回到购物车页。</p>
 */
@WebServlet("/order_submit")
public class OrderSubmitServlet extends HttpServlet {

	private IOrderService orderService = new OrderServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("loginUser");
		Cart cart = (Cart) session.getAttribute("cart");

		// 1. 必须登录才能下单（订单表使用 user_id 外键）
		if (user == null) {
			res.sendRedirect(req.getContextPath() + "/user_login.jsp");
			return;
		}
		// 2. 购物车为空无法下单
		if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
			res.sendRedirect(req.getContextPath() + "/goods_cart.jsp");
			return;
		}

		// 3. 组装订单主表
		Order order = new Order();
		order.setTotal(cart.getTotalPrice());
		order.setAmount(cart.getTotalCount());
		order.setStatus(1);                       // 1:未付款
		order.setPaytype(1);                      // 1:支付宝（默认）
		order.setName(user.getName());
		order.setPhone(user.getPhone());
		order.setAddress(user.getAddress());
		order.setDatetime(LocalDateTime.now());
		order.setUser(user);

		// 4. 组装订单项
		List<OrderItem> items = new ArrayList<>();
		for (CartItem ci : cart.getItems()) {
			OrderItem oi = new OrderItem();
			Goods g = ci.getGoods();
			oi.setGoods(g);
			oi.setAmount(ci.getQuantity());
			oi.setPrice(g.getPrice());
			items.add(oi);
		}
		order.setOrderItems(items);

		// 5. 落库（内部会逐条写入订单项并扣减库存）
		int orderId = orderService.addOrder(order);

		// 6. 成功则清空购物车并展示结果
		if (orderId > 0) {
			session.removeAttribute("cart");
			req.setAttribute("order", order);
			req.setAttribute("orderId", orderId);
			req.getRequestDispatcher("/order_success.jsp").forward(req, res);
		} else {
			req.setAttribute("errorMsg", "下单失败，请稍后再试");
			req.getRequestDispatcher("/goods_cart.jsp").forward(req, res);
		}
	}
}
