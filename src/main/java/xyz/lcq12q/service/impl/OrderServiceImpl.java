package xyz.lcq12q.service.impl;

import xyz.lcq12q.bean.Goods;
import xyz.lcq12q.bean.Order;
import xyz.lcq12q.bean.OrderItem;
import xyz.lcq12q.dao.IGoodsDao;
import xyz.lcq12q.dao.impl.IOrderDao;
import xyz.lcq12q.dao.impl.IOrderItemDao;
import xyz.lcq12q.dao.impl.GoodsDaoImpl;
import xyz.lcq12q.dao.impl.OrderDaoImpl;
import xyz.lcq12q.dao.impl.OrderItemDaoImpl;
import xyz.lcq12q.service.IOrderService;

import java.util.List;

public class OrderServiceImpl implements IOrderService {
	private IOrderDao orderDao = new OrderDaoImpl();
	private IGoodsDao goodsDao = new GoodsDaoImpl();
	private IOrderItemDao orderItemDao = new OrderItemDaoImpl();

	@Override
	public int addOrder(Order order) {
		// 1. 添加订单
		int orderId = orderDao.addOrder(order);
		if (orderId <= 0) {
			return -1; // 添加订单失败
		}

		// 2. 添加订单项
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem item : orderItems) {
			item.setOrder(order); // 设置订单项的所属订单
			item.setPrice(item.getGoods().getPrice()); // 设置订单项的单价
			int result = orderItemDao.addOrderItem(item);
			if (result <= 0) {
				return -1; // 添加订单项失败
			}
			// 3. 更新商品库存
			Goods goods = item.getGoods();
			goods.setStock(goods.getStock() - item.getAmount());
			goodsDao.updateGoods(goods);
		}

		return orderId;
	}

	@Override
	public List<Order> queryOrderByUser(int userId) {
		List<Order> orders = orderDao.queryOrderByUser(userId);
		// 为每个订单装载订单明细，便于列表页直接展示商品信息
		for (Order order : orders) {
			order.setOrderItems(orderItemDao.queryItemsByOrderId(order.getId()));
		}
		return orders;
	}
}
