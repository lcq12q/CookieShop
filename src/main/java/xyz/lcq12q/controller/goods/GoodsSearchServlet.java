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

@WebServlet("/goods_search")
public class GoodsSearchServlet extends HttpServlet {
	private final IGoodsService goodsService = new GoodsServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String keyword = req.getParameter("keyword");
		if (keyword == null) {
			keyword = "";
		}

		int pageNumber = 1;
		String spageNumber = req.getParameter("pageNumber");
		if (spageNumber != null && !spageNumber.isEmpty()) {
			try {
				pageNumber = Integer.parseInt(spageNumber);
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		}
		int pageSize = 8;

		// 查询分页数据
		Page<Goods> page = goodsService.queryByKeyword(keyword, pageNumber, pageSize);

		// 将数据存入 request
		req.setAttribute("p", page);
		req.setAttribute("keyword", keyword);

		// 直接转发到你现有的商品列表页展示！
		req.getRequestDispatcher("/goodsrecommend_list.jsp").forward(req, res);
	}
}