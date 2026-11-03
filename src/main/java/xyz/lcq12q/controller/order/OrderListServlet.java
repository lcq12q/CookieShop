package xyz.lcq12q.controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.bean.Order;
import xyz.lcq12q.bean.User;
import xyz.lcq12q.service.IOrderService;
import xyz.lcq12q.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * 我的订单列表。
 *
 * <p>从会话中取出登录用户，查询其名下所有订单（按下单时间倒序），
 * 转发到订单列表页展示。未登录时跳转登录页。</p>
 */
@WebServlet("/order_list")
public class OrderListServlet extends HttpServlet {

	private IOrderService orderService = new OrderServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("loginUser");

		// 未登录先登录
		if (user == null) {
			res.sendRedirect(req.getContextPath() + "/user_login.jsp");
			return;
		}

		List<Order> orderList = orderService.queryOrderByUser(user.getId());
		req.setAttribute("orderList", orderList);
		req.getRequestDispatcher("/order_list.jsp").forward(req, res);
	}
}
