package xyz.lcq12q.controller.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xyz.lcq12q.service.IUserService;
import xyz.lcq12q.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet("/admin/user_reset_password")
public class UserResetPasswordServlet extends HttpServlet {
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json;charset=UTF-8");

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			res.getWriter().print("{\"code\":401,\"msg\":\"未登录或Session已过期，请重新登录\"}");
			return;
		}

		try {
			String idStr = req.getParameter("id");
			String password = req.getParameter("password");

			if (idStr == null || password == null || password.trim().isEmpty()) {
				res.getWriter().print("{\"code\":1,\"msg\":\"参数不完整\"}");
				return;
			}

			int id = Integer.parseInt(idStr);
			int result = userService.resetPassword(id, password);

			if (result > 0) {
				res.getWriter().print("{\"code\":0,\"msg\":\"密码修改成功\"}");
			} else {
				res.getWriter().print("{\"code\":1,\"msg\":\"密码修改失败\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().print("{\"code\":1,\"msg\":\"服务器内部错误: " + e.getMessage() + "\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doGet(req, res);
	}
}