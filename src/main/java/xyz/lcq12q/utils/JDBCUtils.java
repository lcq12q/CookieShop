package xyz.lcq12q.utils;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.*;

// 工具类：统一管理连接池与数据库连接
public class JDBCUtils {
	// 1. 声明并初始化 Tomcat JDBC 连接池
	private static DataSource dataSource;

	static {
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:mysql://127.0.0.1:3306/cookieshop?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&allowPublicKeyRetrieval=true");
		p.setDriverClassName("com.mysql.cj.jdbc.Driver");
		p.setUsername("root");
		p.setPassword("qualcomm1");
		p.setInitialSize(5);    // 初始化连接数
		p.setMaxActive(20);     // 最大连接数
		p.setMaxWait(10000);    // 超时等待时间

		dataSource = new DataSource();
		dataSource.setPoolProperties(p);
	}

	/**
	 * 获取连接：
	 * 虽然外表看起来是“原生 JDBC”的获取方式，
	 * 但底层已经是直接从 Tomcat JDBC 连接池中拿连接了！
	 */
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取数据源（给 QueryRunner 使用）
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}

	// 关闭连接（对于连接池而言，close() 代表将连接归还给池子）
	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close(); // 归还连接给池
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close(); // 归还连接给池
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static void main() {
		Connection conn = getConnection();
		System.out.println("测试获取连接成功：" + conn);
	}
}