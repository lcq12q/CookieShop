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

@WebServlet("/admin/user_add")
public class UserAddServlet extends HttpServlet {
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
			// 获取表单提交的参数
			String idStr = req.getParameter("id");
			String username = req.getParameter("username");
			String email = req.getParameter("email");
			String name = req.getParameter("name");
			String phone = req.getParameter("phone");
			String address = req.getParameter("address");
			String password = req.getParameter("password");

			User user = new User();
			// 💡 核心修复：如果是添加操作，idStr 可能为空或 "false"，不要强行 parseInt 报错
			if (idStr != null && !idStr.isEmpty() && !"false".equals(idStr)) {
				try {
					user.setId(Integer.parseInt(idStr));
				} catch (NumberFormatException e) {
					// 忽略非法的数字格式
				}
			}

			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			user.setName(name);
			user.setPhone(phone);
			user.setAddress(address);

			// 调用写好的 register 方法（它自带非空校验和重名校验）
			int result = userService.register(user);

			if (result == 1) {
				res.getWriter().print("{\"code\":0,\"msg\":\"添加成功\"}");
			} else if (result == -1) {
				res.getWriter().print("{\"code\":1,\"msg\":\"用户名或邮箱已被占用\"}");
			} else if (result == -2) {
				res.getWriter().print("{\"code\":1,\"msg\":\"必填项不能为空\"}");
			} else {
				res.getWriter().print("{\"code\":1,\"msg\":\"添加失败\"}");
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