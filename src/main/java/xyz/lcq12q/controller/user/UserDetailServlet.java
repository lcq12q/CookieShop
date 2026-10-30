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
import java.util.List;

@WebServlet("/admin/user_detail")
public class UserDetailServlet extends HttpServlet {
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
			String idStr = req.getParameter("id");
			if (idStr != null && !idStr.isEmpty()) {
				int id = Integer.parseInt(idStr);
				User user = userService.findById(id); // 假设你的 Service 层有根据ID查询的方法

				if (user != null) {
					// 注意这里返回 code: 0，与前端的 res.code === 0 对应
					// 为了让前端 form.val 能正确读取，把 user 转成 JSON 格式输出
					String json = "{\"code\":0,\"msg\":\"\",\"data\":{" +
							"\"id\":" + user.getId() + "," +
							"\"username\":\"" + (user.getUsername() != null ? user.getUsername() : "") + "\"," +
							"\"email\":\"" + (user.getEmail() != null ? user.getEmail() : "") + "\"," +
							"\"name\":\"" + (user.getName() != null ? user.getName() : "") + "\"," +
							"\"phone\":\"" + (user.getPhone() != null ? user.getPhone() : "") + "\"," +
							"\"address\":\"" + (user.getAddress() != null ? user.getAddress() : "") + "\"" +
							"}}";
					res.getWriter().print(json);
				} else {
					res.getWriter().print("{\"code\":1,\"msg\":\"用户不存在\",\"data\":null}");
				}
			} else {
				res.getWriter().print("{\"code\":1,\"msg\":\"请求参数ID为空\",\"data\":null}");
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