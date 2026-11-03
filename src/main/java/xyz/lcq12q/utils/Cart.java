package xyz.lcq12q.utils;

import xyz.lcq12q.bean.Goods;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<CartItem> items = new ArrayList<>();    // 购物车中的购物项项列表
	private double totalPrice = 0;  // 购物车所有购物项总金额
	private int totalCount = 0;     // 购物车所有购物项总数量

	public List<CartItem> getItems() {
		return items;
	}

	/**
	 * 设置购物车中的购物项列表
	 * @param items 购物项列表
	 */
	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	/**
	 * 获取购物车中所有购物项的总金额
	 * @return 购物车中所有购物项的总金额
	 */
	public double getTotalPrice() {
		totalPrice = 0; // 重置总金额
		for (CartItem item : items) {
			totalPrice += item.getGoods().getPrice() * item.getQuantity();
		}
		return totalPrice;
	}

	/**
	 * 设置购物车中所有购物项的总金额
	 * @param totalPrice 购物车中所有购物项的总金额
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 获取购物车中所有购物项的总数量
	 * @return 购物车中所有购物项的总数量
	 */
	public int getTotalCount() {
		totalCount = 0; // 重置总数量
		// 计算购物车中所有购物项的总数量
		for (CartItem item : items) {
			totalCount += item.getQuantity();
		}
		return totalCount;
	}

	/**
	 * 设置购物车中所有购物项的总数量
	 * @param totalCount 购物车中所有购物项的总数量
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 添加购物项到购物车
	 * @param item 购物项对象
	 */
	public void addItem(CartItem item) {
		if (item == null || item.getGoods() == null) {
			throw new NullPointerException("购物项或商品对象不能为空");
		}
		// 检查购物车中是否已经存在该商品
		for (CartItem i : items) {
			if (i.getGoods().getId() == item.getGoods().getId()) {
				// 如果商品已存在，增加数量
				i.setQuantity(i.getQuantity() + item.getQuantity());    // 购物项数量+1
				return;
			}
		}
		// 如果商品不存在，创建新的购物项并添加到购物车
		items.add(item);
	}

	/**
	 * 从购物车中移除指定商品的购物项
	 * @param goodsId 商品ID
	 */
	public void removeItem(int goodsId) {
		for (CartItem item : items) {
			if (item.getGoods().getId() == goodsId) {
				items.remove(item);
				return;
			}
		}
	}

	/**
	 * 减少购物车中指定商品的购物项数量 -1
	 * @param goodsId 商品ID
	 */
	public void decreaseItem(int goodsId) {
		for (CartItem item : items) {
			if (item.getGoods().getId() == goodsId) {
				int quantity = item.getQuantity();
				if (quantity == 0) {
					items.remove(item);
				} else if (quantity > 1) {
					item.setQuantity(quantity - 1);
				} else {
					items.remove(item);
				}
				return;
			}
		}
	}

	/**
	 * 查找指定商品id在购物车中的购买数量
	 * @param goodsId 商品ID
	 * @return 购买数量
	 */
	public int findQuantityById(int goodsId) {
		for (CartItem item : items) {
			if (item.getGoods().getId() == goodsId) {
				return item.getQuantity();
			}
		}
		return 0;
	}
}
