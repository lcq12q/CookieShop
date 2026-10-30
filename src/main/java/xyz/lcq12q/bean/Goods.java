package xyz.lcq12q.bean;

public class Goods {
	private int id;
	private String name;
	private String cover;   // 商品封面图片
	private String image1;
	private String image2;
	private double price;
	private String intro;   // 商品简介
	private int stock;

	private boolean anew;   // 是否新品
	private boolean hot;    // 是否热销
	private boolean scoll;  // 是否滚动推荐

	private Type type;  // 商品类型

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isAnew() {
		return anew;
	}

	public void setAnew(boolean anew) {
		this.anew = anew;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public boolean isScoll() {
		return scoll;
	}

	public void setScoll(boolean scoll) {
		this.scoll = scoll;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 重写 toString 方法，返回商品对象的字符串表示
	 * @return
	 */
	@Override
	public String toString() {
		return "Goods{" +
			"id=" + id +
			", name='" + name + '\'' +
			", cover='" + cover + '\'' +
			", image1='" + image1 + '\'' +
			", image2='" + image2 + '\'' +
			", price=" + price +
			", intro='" + intro + '\'' +
			", stock=" + stock +
			", anew=" + anew +
			", hot=" + hot +
			", scoll=" + scoll +
			", type=" + type +
			'}';
	}
}
