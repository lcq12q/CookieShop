package xyz.lcq12q.service.impl;

import xyz.lcq12q.bean.User;
import xyz.lcq12q.dao.IUserDao;
import xyz.lcq12q.dao.impl.UserDaoimpl;
import xyz.lcq12q.service.IUserService;

import java.util.List;
import java.util.Scanner;

public class UserServiceImpl implements IUserService {
	IUserDao userDao = new UserDaoimpl();

	/// @param ue
	/// @param password
	/// @return
	@Override
	public User login(String ue, String password) {
		User user = null;

		// 按照用户名和密码查询用户
		user = userDao.selectByUsernameAndPassword(ue, password);
		if (user != null) {
			return user;
		}

		// 按照邮箱和密码查询用户
		user = userDao.selectByEmailAndPassword(ue, password);
		if (user != null) {
			return user;
		}
		return user;
	}

	/// @param user 用户实体
	/// @return 0:失败；1注册成功；-1：用户名或邮箱已存在
	@Override
	public int register(User user) {
		String username = user.getUsername();
		String email = user.getEmail();
		String password = user.getPassword();
		String name = user.getName();
		String phone = user.getPhone();
		String address = user.getAddress();

		// 非空校验
		if (username == null || username.trim().isEmpty()) return -2;
		if (email == null || email.trim().isEmpty()) return -2;
		if (password == null || password.trim().isEmpty()) return -2;

		// 查询用户名和邮箱是否存在
		User existUser = userDao.selectByUsernameOrEmail(user.getUsername(), user.getEmail());
		if (existUser != null) {
			// 用户名或邮箱已被占用
			return -1;
		}

		// 执行插入
		int rows = userDao.add(user);
		return rows;
	}

	/// @param id
	/// @param password
	/// @return
	@Override
	public int resetPassword(int id, String password) {
		// 基础校验
		if (password == null || password.trim().isEmpty()) {
			return 0; // 密码为空，修改失败
		}
		// 调用 DAO 层执行密码更新
		return userDao.updatePassword(id, password);
	}

	/// @param id
	/// @return
	@Override
	public User findById(int id) {
		return userDao.selectById(id);
	}

	/// @param user
	/// @return
	@Override
	public int updateUserInfo(User user) {
		// 调用 DAO 层执行更新操作
		return userDao.update(user);
	}

	/// @param id
	/// @return
	@Override
	public int deleteUser(int id) {
		return 0;
	}

	/// @return
	@Override
	public List<User> findAll() {
		return userDao.selectAll();
	}

	/// @param id
	/// @return
	@Override
	public int delete(int id) {
		return userDao.delete(id);
	}

	/// @param trim
	/// @return
	@Override
	public List<User> findByKeyword(String trim) {
		// 调用 DAO 层根据关键字进行模糊查询
		return userDao.selectByKeyword(trim);
	}

	void main() {
		Scanner sc = new Scanner(System.in);
		IUserService service = new UserServiceImpl();
		while (true) {
			System.out.println("\n===== 商城控制台登录注册 ====\n1：登录\n2：注册\n0：退出\n请输入选项：");
			int op =  sc.nextInt();
			sc.nextLine();
			if (op == 0) break;
			switch (op) {
				case 1:
					System.out.print("请输入用户名/邮箱：");
					String ue = sc.nextLine();
					System.out.print("请输入密码：");
					String pw = sc.nextLine();
					User login = service.login(ue,pw);
					if (login != null) {
						System.out.println("✅登录成功 " + login.getUsername());
					} else {
						System.out.println("❌账号密码错误");
					}
					break;
				case 2:
					System.out.print("请输入用户名：");
					String username = sc.nextLine();
					System.out.print("请输入邮箱：");
					String email = sc.nextLine();
					System.out.print("请输入密码：");
					String password = sc.nextLine();
					System.out.print("请输入收货人(非必填)：");
					String name = sc.nextLine();
					System.out.print("请输入收货电话(非必填)：");
					String phone = sc.nextLine();
					System.out.print("请输入收货地址(非必填)：");
					String address = sc.nextLine();
					User u = new  User();
					u.setUsername(username);
					u.setEmail(email);
					u.setPassword(password);
					u.setName(name);
					u.setPhone(phone);
					u.setAddress(address);
					int res = service.register(u);
					if(res == -2){
						System.out.println("❌用户名/邮箱/密码不能为空");
					}else if(res == -1){
						System.out.println("❌用户名或邮箱已占用");
					}else if(res == 1){
						System.out.println("✅注册成功");
					}else{
						System.out.println("❌数据库异常");
					}
			}
		}
	}
}