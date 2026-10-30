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

@WebServlet("/admin/user_list")
public class UserListServlet extends HttpServlet {
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
			// 1. 获取前端传过来的搜索关键字
			String keyword = req.getParameter("keyword");

			// 2. 获取 Layui 传递的分页参数（默认 page=1, limit=10）
			int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
			int limit = req.getParameter("limit") != null ? Integer.parseInt(req.getParameter("limit")) : 10;
			// 2. 调用 Service 层方法（如果 keyword 不为空/不为null，则模糊查询；否则查全部）
			List<User> allUsers;
			if (keyword != null && !keyword.trim().isEmpty()) {
				keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
				allUsers = userService.findByKeyword(keyword.trim());
			} else {
				allUsers = userService.findAll();
			}

			int totalCount = allUsers != null ? allUsers.size() : 0;
			// 4. 对数据进行内存分页截取
			List<User> pageUsers;
			if (allUsers != null && !allUsers.isEmpty()) {
				int startIndex = (page - 1) * limit;
				if (startIndex > totalCount) {
					startIndex = 0;
				}
				int endIndex = Math.min(startIndex + limit, totalCount);
				pageUsers = allUsers.subList(startIndex, endIndex);
			} else {
				pageUsers = new java.util.ArrayList<>();
			}

			// 手动处理json对象数组
			// 按照 LayUI 标准格式拼接 JSON 对象
			StringBuilder json = new StringBuilder();
			json.append("{\"code\":0,\"msg\":\"\",\"count\":").append(totalCount).append(",\"data\":[");

			for (int i = 0; i < pageUsers.size(); i++) {
				User u = pageUsers.get(i);
				json.append("{");
				json.append("\"id\":").append(u.getId()).append(",");
				json.append("\"username\":\"").append(u.getUsername() != null ? u.getUsername() : "").append("\",");
				json.append("\"email\":\"").append(u.getEmail() != null ? u.getEmail() : "").append("\",");
				json.append("\"name\":\"").append(u.getName() != null ? u.getName() : "").append("\",");
				json.append("\"phone\":\"").append(u.getPhone() != null ? u.getPhone() : "").append("\",");
				json.append("\"address\":\"").append(u.getAddress() != null ? u.getAddress() : "").append("\",");
				json.append("\"isvalidate\":").append(u.getValidate() != null ? u.getValidate() : 0);
				json.append("}");
				if (i != pageUsers.size() - 1) {
					json.append(",");
				}
			}
			json.append("]}");
			res.getWriter().write(json.toString());
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