package Entities;

public class Employee extends User{
	private String userName;
	private String EmployeeID;
	
	public Employee(String userName, String employeeID) {
		super(employeeID, employeeID, employeeID, employeeID, employeeID, employeeID);
		this.userName = userName;
		EmployeeID = employeeID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}

}
