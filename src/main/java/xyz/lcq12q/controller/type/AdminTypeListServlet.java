package xyz.lcq12q.controller.type;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.lcq12q.bean.Type;
import xyz.lcq12q.service.ITypeService;
import xyz.lcq12q.service.impl.TypeServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/type_list")
public class AdminTypeListServlet extends HttpServlet {
	private final ITypeService typeService = new TypeServiceImpl();
	// 重写doGet方法
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 处理请求，查询类型列表数据
		List<Type> typeList = typeService.findAllType();
		// 将查询结果保存到请求属性中
		req.setAttribute("typeList", typeList);
		// 转发到JSP页面进行展示
		try {
			req.getRequestDispatcher("/admin/type_list.jsp").forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req,res);
	}
}
