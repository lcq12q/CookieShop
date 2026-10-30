package xyz.lcq12q.controller.goods;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.service.IGoodsService;
import xyz.lcq12q.service.impl.GoodsServiceImpl;

import java.io.IOException;

@WebServlet("/goods_detail")
public class GoodDetailServlet extends HttpServlet {
	private final IGoodsService goodsService = new GoodsServiceImpl(); // 注入service层对象
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 获取请求参数商品id
		String sid = req.getParameter("id");
		// 验证数据有效性
		if (sid == null || sid.isEmpty() || "".equals(sid)) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "商品ID不能为空");
			return;
		}
		int id = 0;
		try {
			id = Integer.parseInt(sid);
		} catch (NumberFormatException e) {
			// 跳转到首页
			res.sendRedirect(req.getContextPath() + "/index.jsp");
			return;
		}
		// 调用service层方法查询商品详情
		Goods goods = (Goods) goodsService.queryById(id);
		req.setAttribute("goods", goods);
		// 转发请求到goods_detail.jsp页面
		req.getRequestDispatcher("goods_detail.jsp").forward(req, res);
	}
}