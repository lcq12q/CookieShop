package xyz.lcq12q.dao.impl;

import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.bean.OrderItem;
import xyz.lcq12q.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单项表（orderitem）数据访问实现。
 * 建表语句参考 cookieshop.orderitem：
 *   id, price, amount, goods_id, order_id
 */
public class OrderItemDaoImpl implements IOrderItemDao {

	/**
	 * 添加订单项。
	 * @param item
	 * @return 订单项id
	 */
	@Override
	public int addOrderItem(OrderItem item) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = -1;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "INSERT INTO orderitem(price, amount, goods_id, order_id) VALUES(?,?,?,?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, item.getPrice());
			ps.setInt(2, item.getAmount());
			ps.setInt(3, item.getGoods() != null ? item.getGoods().getId() : 0);
			ps.setInt(4, item.getOrder() != null ? item.getOrder().getId() : 0);
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
				item.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return id;
	}

	/**
	 * 查询某个订单下的所有订单项。
	 * orderitem 表只存了 goods_id，需要 LEFT JOIN goods 表把商品名称、封面补齐，
	 * 否则订单列表页无法展示商品信息。
	 * @param orderId
	 * @return list
	 */
	@Override
	public List<OrderItem> queryItemsByOrderId(int orderId) {
		List<OrderItem> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT oi.id, oi.price, oi.amount, oi.goods_id, "
					+ "g.name AS goods_name, g.cover AS goods_cover "
					+ "FROM orderitem oi LEFT JOIN goods g ON oi.goods_id = g.id "
					+ "WHERE oi.order_id = ? ORDER BY oi.id";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderItem item = new OrderItem();
				item.setId(rs.getInt("id"));
				item.setPrice(rs.getDouble("price"));
				item.setAmount(rs.getInt("amount"));

				// 补齐关联商品信息（商品可能已被下架，故用 LEFT JOIN，取不到时忽略即可）
				Goods goods = new Goods();
				goods.setId(rs.getInt("goods_id"));
				goods.setName(rs.getString("goods_name"));
				goods.setCover(rs.getString("goods_cover"));
				item.setGoods(goods);

				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, ps, conn);
		}
		return list;
	}
}
