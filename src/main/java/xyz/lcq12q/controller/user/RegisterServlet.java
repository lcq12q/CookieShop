package xyz.lcq12q.controller.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.lcq12q.bean.User;
import xyz.lcq12q.service.IUserService;
import xyz.lcq12q.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet("/user_register")
public class RegisterServlet extends HttpServlet {
	private IUserService userService = new UserServiceImpl();   // 创建UserService层对象
	// 重写doPost()
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// 1. 设置请求编码（必须在所有获取参数操作之前）
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");

		// 1.获取请求参数
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");

		// 2.验证数据的有效性(服务器端验证)
		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty() || email == null) {
			// 使用 req.getContextPath() 动态获取项目根路径，防止重定向报 404
			res.getWriter().print("-2");    // 返回-2，用于表示数据不为空
//			res.sendRedirect("user_register.jsp.htm?msg=fail");
			return;
		}
		// 3.调用service层的方法，完成注册功能
		// 业务的有效性验证，验证用户名是否重复，验证email是否重复
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setName(name);
		user.setPhone(phone);
		user.setAddress(address);

		int result = userService.register(user);
		// 返回操作结果
		res.getWriter().print(result);
	}
}
