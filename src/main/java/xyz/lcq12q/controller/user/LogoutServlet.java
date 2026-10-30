package xyz.lcq12q.controller.user;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 用户退出登录。
 *
 * <p>合并了原先 LogoutServlet 与 UserLogoutServlet 两套实现：</p>
 * <ul>
 *   <li>同时支持 GET / POST（页面上 &lt;a href="user_logout"&gt; 走 GET，表单可走 POST）；</li>
 *   <li>先移除 loginUser，再销毁整个 session（双重保障，避免残留属性）；</li>
 *   <li>默认登出后跳转首页 index，传 ?to=login 则跳转登录页 user_login.jsp。</li>
 * </ul>
 *
 * <p>注意：一个 url-pattern 只能对应一个 servlet，不要再新建第二个映射 /user_logout 的类，
 * 否则 Tomcat 启动时会抛 IllegalArgumentException 导致整个应用部署失败。</p>
 */
@WebServlet("/user_logout")
public class LogoutServlet extends HttpServlet {
	/**
	 * 处理 GET 请求，执行登出逻辑。
	 * @param req 请求对象
	 * @param res 响应对象
	 * @throws IOException 重定向失败时抛出
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		logout(req, res);
	}

	/**
	 * 统一的登出逻辑。
	 * @param req 请求对象
	 * @param res 响应对象
	 * @throws IOException 重定向失败时抛出
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		logout(req, res);
	}

	/**
	 * 统一的登出逻辑。
	 *
	 * @param req 请求对象
	 * @param res 响应对象
	 * @throws IOException 重定向失败时抛出
	 */
	private void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// getSession(false)：没有会话时返回 null，避免登出动作反而新建一个空会话
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.removeAttribute("loginUser"); // 先移除登录用户信息
			session.invalidate();                 // 再销毁会话，清除所有属性
		}

		// 目标页面：默认回首页，传 ?to=login 则回登录页
		String to = req.getParameter("to");
		String target = "login".equalsIgnoreCase(to) ? "/user_login.jsp" : "/index";
		res.sendRedirect(req.getContextPath() + target);
	}
}
