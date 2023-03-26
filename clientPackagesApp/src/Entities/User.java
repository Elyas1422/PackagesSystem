package Entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
	//This class is to get all user info by sending query to the database.
	//it send one query to get all info of a user.
	private String Fname;
	private String Lname;
	private String Email;
	private String Username;
	private String Phone;
	Statement stmt;
	ResultSet rs;
	public User(String Username) throws ClassNotFoundException, SQLException{
		this.Username=Username;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
		stmt=con.createStatement();
		rs = stmt.executeQuery("SELECT *"
				+ " FROM user "
				+"WHERE username='"+Username+"'");
		rs.next();
	}
	public String getFname() {
		try {
			rs = stmt.executeQuery("SELECT Fname"
					+ " FROM user "
					+"WHERE username='"+Username+"'");
			rs.next();
			return rs.getString("Fname");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return "first";
		}
	}
	public void setUser(String password,String fname,String Lname,String Email,String Phone,String address) {
		try {
			stmt.executeUpdate("UPDATE user SET Password='"+password+"'"
					+ " ,Email='"+Email+"',Fname='"+fname+"',Lname='"+Lname+"'"
					+ " ,Phone='"+Phone+"' WHERE Username='"+this.Username+"'");
			stmt.executeUpdate("UPDATE customer SET Address='"+address+"'"
					+ " WHERE Username='"+this.Username+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getLname(){
		try {
			rs = stmt.executeQuery("SELECT Lname"
					+ " FROM user "
					+"WHERE username='"+Username+"'");
			rs.next();
			return rs.getString("Lname");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "last";
		}
	}
	public String getEmail() {
		try {
			rs = stmt.executeQuery("SELECT Email"
					+ " FROM user "
					+"WHERE username='"+Username+"'");
			rs.next();
			return rs.getString("Email");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return "Email";
		}
	}
	public String getUsername() {
		return Username;
		
	}
	public String getPassword() {
		try {
			rs = stmt.executeQuery("SELECT Password"
					+ " FROM user "
					+"WHERE username='"+Username+"'");
			rs.next();
			return rs.getString("Password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return "pass";
		}
	}
	public String getPhone() {
		try {
			rs = stmt.executeQuery("SELECT Phone"
					+ " FROM user "
					+"WHERE username='"+Username+"'");
			rs.next();
			return rs.getString("Phone");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return "phone number";
		}
	}
	public String getAddress() {
		try {
			ResultSet AddressRs = stmt.executeQuery("SELECT *"
					+ " FROM customer "
					+"WHERE username='"+Username+"'");
			AddressRs.next();
			return AddressRs.getString("Address");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
}
