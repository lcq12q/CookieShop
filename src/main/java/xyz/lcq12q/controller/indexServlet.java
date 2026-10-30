package xyz.lcq12q.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.bean.Type;
import xyz.lcq12q.service.IGoodsService;
import xyz.lcq12q.service.ITypeService;
import xyz.lcq12q.service.impl.GoodsServiceImpl;
import xyz.lcq12q.service.impl.TypeServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class indexServlet extends HttpServlet {
	private IGoodsService goodsService = new GoodsServiceImpl();    //
	private ITypeService typeService = new TypeServiceImpl(); // 声明类型服务接口

	/**
	 * doGet方法处理GET请求
	 * @param req HttpServletRequest对象，包含客户端请求信息
	 * @param res HttpServletResponse对象，包含响应信息
	 * @throws IOException 如果发生输入输出异常
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 获取所有滚动推荐
		List<Goods> scrollGoods = goodsService.queryAllScrollGoods();
		// 获取所有热门推荐
		List<Goods> hotGoods = goodsService.queryAllHotGoods();
		// 获取所有最新推荐
		List<Goods> anewGoods = goodsService.queryAllAnewGoods();

		// 💡 调试打印：查看控制台输出的数据条数
//		System.out.println(">>> 轮播商品数量: " + (scrollGoods != null ? scrollGoods.size() : "null"));
//		System.out.println(">>> 热销商品数量: " + (hotGoods != null ? hotGoods.size() : "null"));
//		System.out.println(">>> 新品商品数量: " + (anewGoods != null ? anewGoods.size() : "null"));

		// 将查询结果存入请求属性中
		req.setAttribute("scrollGoods", scrollGoods);
		req.setAttribute("hotGoods", hotGoods);
		req.setAttribute("anewGoods", anewGoods);

		// 将商品类型放入application域中
		ServletContext context = getServletContext();   // 获取application域对象
		List<Type> types = (List<Type>)context.getAttribute("types");
		if (types == null) {
			types = typeService.findAllType();
			context.setAttribute("types", types);
		}

		// 转发请求到index.jsp页面
		req.getRequestDispatcher("index.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}
}
