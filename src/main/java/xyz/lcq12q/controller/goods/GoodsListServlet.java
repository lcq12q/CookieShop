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

@WebServlet("/goods_list")
public class GoodsListServlet extends HttpServlet {
	private final IGoodsService goodsService = new GoodsServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		// 安全解析 typeid：为空或非数字时视为“全部商品”
		Integer typeid = null;
		String stypeid = req.getParameter("typeid");
		if (stypeid != null && !stypeid.trim().isEmpty()) {
			try {
				typeid = Integer.parseInt(stypeid.trim());
			} catch (NumberFormatException e) {
				typeid = null;
			}
		}

		// 安全解析 pageNumber，默认第 1 页
		int pageNumber = 1;
		String spageNumber = req.getParameter("pageNumber");
		if (spageNumber != null && !spageNumber.trim().isEmpty()) {
			try {
				pageNumber = Integer.parseInt(spageNumber.trim());
			} catch (NumberFormatException e) {
				pageNumber = 1;
			}
		}
		int pageSize = 8;

		Page<Goods> page;
		if (typeid != null) {
			page = goodsService.queryGoodsByTypeId(typeid, pageNumber, pageSize);
			req.setAttribute("typeid", typeid);
			// 取首条商品的类型名作为标题
			if (!page.getList().isEmpty() && page.getList().get(0).getType() != null) {
				req.setAttribute("typeName", page.getList().get(0).getType().getName());
			} else {
				req.setAttribute("typeName", "该系列暂无商品");
			}
		} else {
			page = goodsService.queryAll(pageNumber, pageSize);
		}

		req.setAttribute("p", page);

		// 获取对应商品

		req.getRequestDispatcher("/goods_list.jsp").forward(req, res);
	}
}
