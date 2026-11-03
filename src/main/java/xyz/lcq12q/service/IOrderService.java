package xyz.lcq12q.service;

import xyz.lcq12q.bean.Order;

import java.util.List;

public interface IOrderService {
	// 添加订单
	int addOrder(Order order);

	// 查询我的订单信息
	List<Order> queryOrderByUser(int userId);
}
