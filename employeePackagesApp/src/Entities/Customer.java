package Entities;

public class Customer extends User{
	private String ID;
	private String address;
	private String userName;
	public Customer(String userName, String address, String iD) {
		super();
		ID = iD;
		this.address = address;
		this.userName = userName;
	}
	


	private Customer(String userName, String passWord, String email, String fName, String lName, String phone) {
		super(userName, passWord, email, fName, lName, phone);
		// TODO Auto-generated constructor stub
	}

	public String getID() {
		return ID;
	}
	public String getAddress() {
		return address;
	}
	public String getUserName() {
		return userName;
	}



	@Override
	public String toString() {
		return "Customer [ID=" + ID + ", address=" + address + ", userName=" + userName + ", getPassWord()="
				+ getPassWord() + ", getEmail()=" + getEmail() + ", getfName()=" + getfName() + ", getlName()="
				+ getlName() + ", getPhone()=" + getPhone() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	} 
	
}
