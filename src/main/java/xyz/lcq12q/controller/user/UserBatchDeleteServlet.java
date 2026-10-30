package xyz.lcq12q.controller.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.bean.User;
import xyz.lcq12q.service.IUserService;
import xyz.lcq12q.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet("/admin/user_batch_delete")
public class UserBatchDeleteServlet extends HttpServlet {
	private IUserService userService = new UserServiceImpl();   // 创建UserService层对象

	/**
	 * 重写doGet
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 *
	 * @param res an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 *
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
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
			// 2. 获取前端传过来的以逗号拼接的 ids 字符串（例如 "1,2,3"）
			String idsStr = req.getParameter("ids");
			if (idsStr == null || idsStr.trim().isEmpty()) {
				res.getWriter().print("{\"code\":1,\"msg\":\"未选择要删除的数据\"}");
				return;
			}

			String[] idArr = idsStr.split(",");
			boolean allSuccess = true;

			// 3. 循环调用删除方法（如果你在 Service 里写了批量删除方法，也可以直接调用批量接口）
			for (String idStr : idArr) {
				int id = Integer.parseInt(idStr);
				// 假设你的 IUserService 中有 delete(int id) 方法（对应你前面写的单行删除）
				int result = userService.delete(id);
				if (result <= 0) {
					allSuccess = false;
				}
			}

			if (allSuccess) {
				res.getWriter().print("{\"code\":0,\"msg\":\"删除成功\"}");
			} else {
				res.getWriter().print("{\"code\":1,\"msg\":\"部分或全部删除失败\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();    // 在控制台打印详细错误方便排查
			// 发生异常时返回合法的 LayUI 错误结构，而不是让页面崩溃
			String errorJson = "{\"code\":1,\"msg\":\"服务器内部错误: " + e.getMessage() + "\",\"count\":0,\"data\":[]}";
			res.getWriter().write(errorJson);
		}
	}

	/**
	 * 重写doPost()
	 * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 *
	 * @param res an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 *
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doGet(req, res);
	}
}