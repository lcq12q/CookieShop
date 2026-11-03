package xyz.lcq12q.dao.impl;

import xyz.lcq12q.bean.OrderItem;

import java.util.List;

public interface IOrderItemDao {
	int addOrderItem(OrderItem orderItem);

	// 根据订单号查询该订单下的所有订单项（含商品名称、封面）
	List<OrderItem> queryItemsByOrderId(int orderId);
}
