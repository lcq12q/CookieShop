package xyz.lcq12q.service;

import xyz.lcq12q.bean.User;

import java.util.List;

public interface IUserService {
	/**
	 * 用户登录
	 * @param ue 用户名或邮箱
	 * @param password 密码
	 * @return 登录成功返回 User 对象，失败返回 null
	 */
	User login(String ue,String password);
	/**
	 * 用户注册
	 * @param user 用户实体
	 * @return 返回状态码：1-注册成功；-1-用户名或邮箱已存在；-2-必填项为空；0-数据库操作失败
	 */
	int register(User user);
	// 重置密码
	int resetPassword(int id, String password);
	// 通过id查找用户信息
	User findById(int id);
	// 修改用户信息
	int updateUserInfo(User user);
	// 删除用户信息
	int deleteUser(int id);
	// 查询所有信息
	List<User> findAll();

	int delete(int id);

	List<User> findByKeyword(String trim);
}
