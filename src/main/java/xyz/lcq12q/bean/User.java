package xyz.lcq12q.bean;

public class User {
	private int id;
	private String username;
	private String email;
	private String password;
	private String name;
	private String phone;
	private String address;

	private boolean admin;
	private Integer validate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isAdmin() {
		return admin;
	}

	/**
	 * 便于 EL 表达式使用 ${user.isAdmin} 时能解析到属性 isAdmin。
	 * 注意：JavaBeans 规范下 isAdmin() 对应的属性名是 admin，
	 * 若需要属性名为 isAdmin，则必须提供 getIsAdmin()。
	 */
	public boolean getIsAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Integer getValidate() {
		return validate;
	}

	public void setValidate(Integer validate) {
		this.validate = validate;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", admin=" + admin +
				", validate='" + validate + '\'' +
				'}';
	}
}