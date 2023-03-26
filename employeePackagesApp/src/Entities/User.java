package Entities;

public class User {

	private String userName;
	private String passWord;
	private String email;
	private String fName;
	private String lName;
	private String phone;
	public User() {

	}
	public User(String userName, String passWord, String email, String fName, String lName, String phone) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.email = email;
		this.fName = fName;
		this.lName = lName;
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public String getEmail() {
		return email;
	}
	public String getfName() {
		return fName;
	}
	public String getlName() {
		return lName;
	}
	public String getPhone() {
		return phone;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
