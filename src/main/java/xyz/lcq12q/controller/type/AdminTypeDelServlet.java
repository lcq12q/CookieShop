package xyz.lcq12q.controller.type;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.service.ITypeService;
import xyz.lcq12q.service.impl.TypeServiceImpl;

import java.io.IOException;

@WebServlet("/admin/type_delete")
public class AdminTypeDelServlet extends HttpServlet {
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
		res.setContentType("application/json;charset=UTF-8");
		// 1. 获取 Session，参数为 false 表示如果不存在则返回 null，不自动创建
		HttpSession session = req.getSession(false);
		// 2. 校验 Session 中是否存在登录用户（假设登录时存入的 key 是 "loginUser"）
		if (session == null || session.getAttribute("loginUser") == null) {
			// 返回 401 未登录状态码的 JSON
			// 返回 code 401，同时 data 为空数组，防止表格解析崩溃
			String unauthorizedJson = "{\"code\":401,\"msg\":\"未登录或Session已过期，请重新登录\",\"count\":0,\"data\":[]}";
			res.getWriter().print(unauthorizedJson);
			return;
		}
		try {
			// 获取表单提交的参数
			String id = req.getParameter("id");
			if (id == null || id.trim().isEmpty()) {
				res.getWriter().print("{\"code\":1,\"msg\":\"类型ID不能为空\"}");
				return;
			}

			int result = typeService.deleteType(Integer.parseInt(id));
			if (result > 0) {
				res.getWriter().print("{\"code\":0,\"msg\":\"删除成功\"}");
			} else if (result == -1) {
				res.getWriter().print("{\"code\":1,\"msg\":\"类型名称已存在\"}");
			} else {
				res.getWriter().print("{\"code\":1,\"msg\":\"删除失败\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().print("{\"code\":1,\"msg\":\"服务器内部错误\"}");
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
		// 防止直接通过GET访问时报错，重定向回列表页
		res.sendRedirect(req.getContextPath() + "/admin/type_list");
	}
}
