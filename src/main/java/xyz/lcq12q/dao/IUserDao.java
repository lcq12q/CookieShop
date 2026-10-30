package xyz.lcq12q.dao;

import xyz.lcq12q.bean.User;

import java.util.List;

public interface IUserDao {
	// 按用户名和密码查询用户信息
	User selectByUsernameAndPassword(String username,String password);
	// 按email和密码查询用户信息
	User selectByEmailAndPassword(String email,String password);

	// 按emeil查询用户信息
	User selectByEmail(String email);
	// 按用户名查询用户信息
	User selectByUsername(String username);
	// 添加用户
	int add(User user);
	// 查询所有用户
	List<User> selectAll();

	User selectByUsernameOrEmail(String username, String email);

	int update(User user);

	User selectById(int id);
	// 更新密码
	int updatePassword(int id, String password);
	// 删除用户
	int delete(int id);
	// 更改用户状态
	int updateValidate(int id,int validate);

	List<User> selectByKeyword(String trim);
}
