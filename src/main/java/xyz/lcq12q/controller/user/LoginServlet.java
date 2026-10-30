package xyz.lcq12q.controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.bean.User;
import xyz.lcq12q.service.IUserService;
import xyz.lcq12q.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet("/user_login")
public class LoginServlet extends HttpServlet {
	private IUserService userService = new UserServiceImpl();   // 创建UserService层对象
	// 重写doPost()
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// 1. 设置请求编码（必须在所有获取参数操作之前）
		req.setCharacterEncoding("utf-8");
		res.setContentType("application/json;charset=utf-8");
		// 1.获取请求参数
		String ue = req.getParameter("ue");
		String password = req.getParameter("password");

		// 2.验证数据的有效性(服务器端验证)
		if (ue == null || ue.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			// 使用 req.getContextPath() 动态获取项目根路径，防止重定向报 404
//			res.getWriter().print("-1");
			res.getWriter().print("{\"code\":-1,\"msg\":\"用户名或密码不能为空\"}");
//			req.setAttribute("msg","用户名和密码不能为空");
//			req.getRequestDispatcher("/user_login.jsp").forward(req,res);
			return;
		}
		// 3.调用service层的方法，完成登录功能
		User user = userService.login(ue, password);

		// 根据登录结果响应前端
		if (user != null) {
			// 登录成功，将用户信息存入Session
			HttpSession session = req.getSession();
			session.setAttribute("loginUser", user);
//			if (user.isAdmin()) {
//				req.getRequestDispatcher("/admin/index.jsp").forward(req, res);
//			} else {
//				req.getRequestDispatcher("/index.htm").forward(req, res);
//			}
			boolean isAdmin = user.isAdmin();

			String json = "{\"code\":1,\"msg\":\"登录成功\",\"isAdmin\":" + isAdmin + "}";
			res.getWriter().print(json);
//			res.getWriter().print("1"); // 1表示登录成功
		} else {
//			req.setAttribute("msg","用户名和密码不能为空");
//			req.getRequestDispatcher("/user_login.jsp").forward(req,res);
//			res.getWriter().print("0"); // 0表示账号密码错误
			res.getWriter().print("{\"code\":0,\"msg\":\"账号或密码错误\"}");
		}
	}
}
