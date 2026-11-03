package xyz.lcq12q.bean;

public class OrderItem {
	private int id;
	private double price;       // 单价
	private int amount;         // 数量
//	private String goodsName;   // 商品名称
	private Goods goods;        // 商品对象 1对1
	private Order order;        // 订单对象 1对多

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderItem{" +
				"id=" + id +
				", price=" + price +
				", amount=" + amount +
				", goods=" + goods +
				", order=" + order +
				'}';
	}
}
