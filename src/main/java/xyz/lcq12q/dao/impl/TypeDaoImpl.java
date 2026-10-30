package xyz.lcq12q.dao.impl;

import xyz.lcq12q.bean.Type;
import xyz.lcq12q.dao.ITypeDao;
import xyz.lcq12q.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeDaoImpl implements ITypeDao {

	/**
	 * 添加商品类型
	 * @param name
	 * @return
	 */
	@Override
	public int addType(String name) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "INSERT INTO type (name) VALUES (?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(null,ps,conn);
		}
		return result;
	}

	/**
	 * 删除商品类型
	 * @param id
	 * @return
	 */
	@Override
	public int deleteType(int id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "DELETE FROM type WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(null,ps,conn);
		}
		return result;
	}

	/**
	 * 更新商品类型
	 * @param id
	 * @param name
	 * @return
	 */
	@Override
	public int updateType(int id, String name) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "UPDATE type SET name = ? WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(null,ps,conn);
		}
		return result;
	}

	/**
	 * 根据商品类型名称查询商品类型。
	 * @param name 商品类型的名称
	 * @return 查询到的商品类型；如果不存在，则返回 {@code null}
	 */
	@Override
	public Type findByName(String name) {
		Type type = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT * FROM type WHERE name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, null);
		}
		return type;
	}

	/**
	 * 查询所有商品类型。
	 * @return 商品类型列表
	 */
	@Override
	public List<Type> findAllType() {
		List<Type> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT * FROM type";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Type type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
				list.add(type); // 把对象添加到集合中
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, null);
		}
		return list;
	}

	/**
	 * 根据商品类型 ID 查询商品类型。
	 * @param id 商品类型的 ID
	 * @return 查询到的商品类型；如果不存在，则返回 {@code null}
	 */
	@Override
	public Type findById(int id) {
		Type type = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT * FROM type WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				type = new Type();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, null);
		}
		return type;
	}
}
