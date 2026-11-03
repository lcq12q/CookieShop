package xyz.lcq12q.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
	private int id;
	private double total;     // 订单总金额
	private int amount;         // 订单总数量
	private int status;         // 订单状态 1:未付款 2:已付款 3:配送中 4:已完成
	private int paytype;        // 支付方式 1:支付宝 2:微信支付
	private String name;          // 收货人姓名
	private String phone;         // 收货人电话
	private String address;       // 收货人地址
	private LocalDateTime datetime; // 下单时间

	private User user;        // 用户对象 1对1
	private List<OrderItem> orderItems = new ArrayList<>(); // 订单项列表 1对多

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
