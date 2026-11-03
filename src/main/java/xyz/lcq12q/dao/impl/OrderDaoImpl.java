package xyz.lcq12q.dao.impl;

import xyz.lcq12q.bean.Order;
import xyz.lcq12q.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单表（orders）数据访问实现。
 * 建表语句参考 cookieshop.orders：
 *   id, total, amount, status, paytype, name, phone, address, datetime, user_id
 */
public class OrderDaoImpl implements IOrderDao {

	@Override
	public int addOrder(Order order) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int orderId = -1;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "INSERT INTO orders(total, amount, status, paytype, name, phone, address, datetime, user_id) "
					+ "VALUES(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, order.getTotal());
			ps.setInt(2, order.getAmount());
			ps.setInt(3, order.getStatus());
			ps.setInt(4, order.getPaytype());
			ps.setString(5, order.getName());
			ps.setString(6, order.getPhone());
			ps.setString(7, order.getAddress());
			if (order.getDatetime() != null) {
				ps.setTimestamp(8, java.sql.Timestamp.valueOf(order.getDatetime()));
			} else {
				ps.setTimestamp(8, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
			}
			ps.setInt(9, order.getUser() != null ? order.getUser().getId() : 0);
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
				order.setId(orderId); // 回填自增主键，供订单项关联
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return orderId;
	}

	@Override
	public List<Order> queryOrderByUser(int userId) {
		List<Order> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT * FROM orders WHERE user_id=? ORDER BY datetime DESC";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Order o = new Order();
				o.setId(rs.getInt("id"));
				o.setTotal(rs.getDouble("total"));
				o.setAmount(rs.getInt("amount"));
				o.setStatus(rs.getInt("status"));
				o.setPaytype(rs.getInt("paytype"));
				o.setName(rs.getString("name"));
				o.setPhone(rs.getString("phone"));
				o.setAddress(rs.getString("address"));
				java.sql.Timestamp ts = rs.getTimestamp("datetime");
				if (ts != null) {
					o.setDatetime(ts.toLocalDateTime());
				}
				list.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return list;
	}
}
