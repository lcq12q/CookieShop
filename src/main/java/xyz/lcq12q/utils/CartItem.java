package xyz.lcq12q.utils;

import xyz.lcq12q.bean.Goods;

public class CartItem {
	private Goods goods;    // 商品对象信息
	private int quantity;   // 商品数量

	/**
	 * 构造方法
	 * @param goods 商品对象信息
	 * @param quantity 商品数量
	 */
	public CartItem(Goods goods, int quantity) {
		this.goods = goods;
		this.quantity = quantity;
	}

	/**
	 * 计算当前购物项的总金额
	 * @return 当前购物项的总金额
	 */
	public double getTotalPrice() {
		return goods.getPrice() * quantity;
	}

	/**
	 * 计算当前购物项的总积分
	 * @return 当前购物项的总积分
	 */
	public int getTotalPoints() {
		return (int) (goods.getPrice() * quantity);
	}

	/**
	 * 获取商品对象信息
	 * @return 商品对象信息
	 */
	public Goods getGoods() {
		return goods;
	}

	/**
	 * 设置商品对象信息
	 * @param goods 商品对象信息
	 */
	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	/**
	 * 获取商品数量
	 * @return 商品数量
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * 设置商品数量
	 * @param quantity 商品数量
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 重写toString方法，返回购物项的字符串表示
	 * @return 购物项的字符串表示
	 */
	@Override
	public String toString() {
		return "CartItem{" +
				"goods=" + goods +
				", quantity=" + quantity +
				'}';
	}
}
