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

@WebServlet("/admin/type_edit")
public class AdminTypeEditServlet extends HttpServlet {
	private final ITypeService typeService = new TypeServiceImpl();
	/**
	 * 重写doPost方法
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 *
	 * @param res an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 设置请求编码，防止中文乱码
		req.setCharacterEncoding("UTF-8");
		String sid = req.getParameter("id");
		// 验证名称为空
		String name = req.getParameter("name");
		if (name == null || name.isEmpty()) {
			req.setAttribute("msg", "名称为空");
			req.getRequestDispatcher("/admin/type_edit.jsp").forward(req, res);
			return;
		}
		int result = typeService.updateType(Integer.parseInt(sid),name);
		if (result == 1) {
			req.setAttribute("msg", "修改成功！");
			res.sendRedirect(req.getContextPath() + "/admin/type_list");
//			req.getRequestDispatcher("/admin/type_list").forward(req, res);
		} else if (result == -1) {
			req.setAttribute("msg", "类型名称重复！");
			req.getRequestDispatcher("/admin/type_edit.jsp").forward(req, res);
		} else {
			req.setAttribute("msg", "修改失败！");
			req.getRequestDispatcher("/admin/type_edit.jsp").forward(req, res);
		}
	}

	/**
	 * 重写doGet
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 *
	 * @param res an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("/admin/type_edit.jsp").forward(req, res);
	}
}
