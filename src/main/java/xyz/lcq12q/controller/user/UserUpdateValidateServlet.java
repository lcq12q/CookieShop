package xyz.lcq12q.controller.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.lcq12q.dao.IUserDao;
import xyz.lcq12q.dao.impl.UserDaoimpl;

import java.io.IOException;

@WebServlet("/admin/user_update_validate")
public class UserUpdateValidateServlet extends HttpServlet {
	private IUserDao userDao = new UserDaoimpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json;charset=UTF-8");
		try {
			String idStr = req.getParameter("id");
			String isvalidateStr = req.getParameter("isvalidate");
			// 打印到控制台，看看前端到底传了什么过来！
			System.out.println("收到更新状态请求 - id: " + idStr + ", isvalidate: " + isvalidateStr);
			int id = Integer.parseInt(req.getParameter("id"));

			int isvalidate;
			if ("true".equalsIgnoreCase(isvalidateStr) || "1".equalsIgnoreCase(isvalidateStr)) {
				isvalidate = 1;
			} else {
				isvalidate = 0;
			}

			// 调用 DAO 更新状态
			int result = userDao.updateValidate(id, isvalidate);

			if (result > 0) {
				res.getWriter().write("{\"code\":0,\"msg\":\"状态修改成功\"}");
			} else {
				res.getWriter().write("{\"code\":1,\"msg\":\"修改失败，未找到该用户\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().write("{\"code\":1,\"msg\":\"服务器异常: " + e.getMessage() + "\"}");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doPost(req, res);
	}
}