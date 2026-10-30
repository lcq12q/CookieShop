package xyz.lcq12q.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import xyz.lcq12q.bean.User;
import xyz.lcq12q.dao.IUserDao;
import xyz.lcq12q.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoimpl implements IUserDao {
	// 创建 QueryRunner 对象，传入连接池数据源
	private QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

	/// @param username
	/// @param password
	/// 查询用户名和密码是否正确
	/// @return
	@Override
	public User selectByUsernameAndPassword(String username, String password) {
		User user = null;
		// 2
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from user where username=? and password=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));

				user.setAdmin(rs.getBoolean("isadmin"));
				user.setValidate(rs.getBoolean("isvalidate") ? 1 : 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		// 3.执行查询
		return user;
	}

	/// @param email
	/// @param password
	/// 查询邮箱和密码是否正确
	/// @return
	@Override
	public User selectByEmailAndPassword(String email, String password) {
		User user = null;
		// 2
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from user where email = ? and password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));

				user.setAdmin(rs.getBoolean("isadmin"));
				user.setValidate(rs.getBoolean("isvalidate") ? 1 : 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		// 3.执行查询
		return user;
	}

	/// @param email
	/// @return
	@Override
	public User selectByEmail(String email) {
		return null;
	}

	/// @param username
	/// @return
	@Override
	public User selectByUsername(String username) {
		return null;
	}

	/// @param user
	/// @return
	@Override
	public int add(User user) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "insert into user(username,password,name,email,phone,address,isadmin,isvalidate) values(?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getAddress());
			ps.setInt(7, 0);
			ps.setInt(8, 1);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(ps, conn);
		}
		return 0;
	}

	/**
	 * 查询全部用户信息
	 * @return
	 */
	@Override
	public List<User> selectAll() {
		List<User> users = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from user";
			ps = conn.prepareStatement(sql);
			// 执行查询并将结果赋值给 rs
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));
				user.setAdmin(rs.getBoolean("isadmin"));
				user.setValidate(rs.getBoolean("isvalidate") ? 1 : 0);
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return users;
	}

	/// @param username
	/// @param email
	// 查询用户名和密码是否存在
	/// @return
	@Override
	public User selectByUsernameOrEmail(String username, String email) {
		User user = null;
		// 2
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from user where username=? or email=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, email);

			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		// 3.执行查询
		return user;
	}

	/// @param user
	/// @return
	@Override
	public int update(User user) {
		String sql = "update user set name = ?, phone = ?, address = ?, email = ? where id = ?";
		try {
			// queryRunner.update 会自动处理 Connection 的获取和关闭（前提是构造时传入了 DataSource）
			return queryRunner.update(sql, user.getName(), user.getPhone(), user.getAddress(), user.getEmail(), user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/// @param id
	/// @return
	@Override
	public User selectById(int id) {
		// 示例：通过 SQL "SELECT * FROM user WHERE id = ?" 查询单条记录并封装为 User 对象
		// 具体取决于你项目里使用的持久层工具（如 DbUtils, MyBatis 或 原生 JDBC）
		String  sql = "select username, email, name, phone, address from user where id = ?";
		// 执行查询并返回 User 对象
		try {
			return queryRunner.query(sql,new BeanHandler<>(User.class),id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/// @param id
	/// @param password
	/// @return
	@Override
	public int updatePassword(int id, String password) {
		String sql = "update user set password = ? where id = ?";
		try {
			return queryRunner.update(sql, password, id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/// @param id
	/// @return
	@Override
	public int delete(int id) {
		String sql = "delete from user where id = ?";
		try {
			return queryRunner.update(sql,id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/// @param id
	/// @param validate
	/// @return
	@Override
	public int updateValidate(int id, int validate) {
		String sql = "update user set isvalidate = ? where id = ?";
		try {
			return queryRunner.update(sql,validate,id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/// @param trim
	/// @return
	@Override
	public List<User> selectByKeyword(String trim) {
		List<User> users = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			// 拼接模糊查询 SQL，同时匹配用户名、邮箱、收货人
			String sql = "SELECT * FROM user WHERE username LIKE ? OR email LIKE ? OR name LIKE ?";
			ps = conn.prepareStatement(sql);

			String wildCard = "%" + (trim != null ? trim : "") + "%";
			ps.setString(1, wildCard);
			ps.setString(2, wildCard);
			ps.setString(3, wildCard);

			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));
				user.setAdmin(rs.getBoolean("isadmin"));
				user.setValidate(rs.getBoolean("isvalidate") ? 1 : 0);
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return users;
	}

	static void main() {
		UserDaoimpl userDao = new UserDaoimpl();
		User user = userDao.selectByUsernameAndPassword("admin", "admin");
		System.out.println(user.toString());
	}
}
