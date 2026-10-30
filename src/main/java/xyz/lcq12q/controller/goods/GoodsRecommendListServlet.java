package xyz.lcq12q.controller.goods;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.service.IGoodsService;
import xyz.lcq12q.service.impl.GoodsServiceImpl;
import xyz.lcq12q.utils.Page;

import java.io.IOException;

@WebServlet("/goodsrecommend_list")
public class GoodsRecommendListServlet extends HttpServlet {
	private final IGoodsService goodsService = new GoodsServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		// 1、安全获取 type 参数（防止为空字符串或 null 导致 500 异常）
		int type = 2; // 默认值（可根据你的业务调整，比如默认热销类型）
		String stype = req.getParameter("type");
		if (stype != null && !stype.trim().isEmpty()) {
			try {
				type = Integer.parseInt(stype.trim());
			} catch (NumberFormatException e) {
				type = 2;
			}
		}

		// 2、安全获取 pageNumber 参数
		int pageNumber = 1;
		String spageNumber = req.getParameter("pageNumber");
		if (spageNumber != null && !spageNumber.trim().isEmpty()) {
			try {
				pageNumber = Integer.parseInt(spageNumber.trim());
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		}
		int pageSize = 8;  // 每页显示8条数据

		// 3、调用service层方法，获取推荐商品列表
		Page<Goods> page = goodsService.queryByRecommendType(type, pageNumber, pageSize);

		// 4、将推荐商品列表和 type 存入 request 域中
		req.setAttribute("p", page);
		req.setAttribute("type", type); // 确保 JSP 页面能拿到正确的 type 值

		// 5、转发到JSP页面
		req.getRequestDispatcher("/goodsrecommend_list.jsp").forward(req, res);
	}
}