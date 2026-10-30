package xyz.lcq12q.controller.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.lcq12q.bean.User;
import xyz.lcq12q.service.IUserService;
import xyz.lcq12q.service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet("/admin/user_get")
public class UserGetServlet extends HttpServlet {
	private IUserService userService = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json;charset=UTF-8");
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			User user = userService.findById(id);
			if (user != null) {
				res.getWriter().print("{\"code\":0, \"data\":{\"username\":\"" + user.getUsername() + "\",\"email\":\"" + user.getEmail() + "\"}}");
			} else {
				res.getWriter().print("{\"code\":1, \"msg\":\"用户不存在\"}");
			}
		} catch (Exception e) {
			res.getWriter().print("{\"code\":1, \"msg\":\"系统错误\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doGet(req, res);
	}
}