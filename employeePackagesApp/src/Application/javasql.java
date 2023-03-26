package Application;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Entities.Customer;
import Entities.Packages;
import Entities.User;

public class javasql {
	
	public static boolean checkLogin(String userName, String password) throws ClassNotFoundException, SQLException {
		boolean login = false;
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();

		ResultSet rs=stmt.executeQuery("SELECT * from user INNER JOIN employee ON user.Username=employee.Username "
				+ "WHERE user.username='"+userName+"'"); 
		if(!rs.next())
			return login;
		if(password.equals(rs.getString(2))) 
			login = true;
		con.close();
		
		
		
		return login;
		
	};
	public static void payment(Packages pack) throws Exception{
	float value = 14*(pack.getWeight())+15;

	float insurance = 5*(pack.getWeight())+15*pack.getFragile()+10*pack.getLiquid()+10*pack.getChemical();
	float price = value + insurance;
	float fines=0;
	if(pack.getStatus().toLowerCase().equals("lost"))
		fines = (float) (insurance+0.05*value);
	if(pack.getStatus().toLowerCase().equals("damaged"))
		fines = (float) (insurance+0.05*value);
	float total = price-fines;
	
	Class.forName("com.mysql.cj.jdbc.Driver");

	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
	PreparedStatement prepared = con.prepareStatement("INSERT INTO payment VALUES(?,?,?,?,?)");
	prepared.setNString(1, pack.getPackageNo());
	prepared.setFloat(2, value);
	prepared.setFloat(3, insurance);
	prepared.setFloat(4, fines);
	prepared.setInt(5, 0);
	prepared.executeUpdate();
	con.close();
	
	}
	public static void paymentEdit(Packages pack) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  


		Statement stmt=con.createStatement();
		stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=0");
		String sql=("DELETE FROM payment WHERE PackageNo='"+pack.getPackageNo()+"'");  
		stmt.executeUpdate(sql);
		stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=0");
		con.close();
		payment(pack);
	}
	
	public static boolean addPack(Packages pack) throws ClassNotFoundException, SQLException {
		
		boolean insertion = false;
		String packageNo = pack.getPackageNo();
		int weight = pack.getWeight();
		int width = pack.getWidth();
		int height = pack.getHeight();
		int depth = pack.getDepth();
		String deliveryDate  = pack.getDeliveryDate();
		String status = pack.getStatus();
		int fragile= pack.getFragile();
		String matierial= pack.getMatierial();
		int chemical = pack.getChemical();
		String riskType= pack.getRiskType();
		int liquid= pack.getLiquid();
		int volume = pack.getVolume();
		String destinationID = pack.getDestinationID();
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
		PreparedStatement prepared = con.prepareStatement("INSERT INTO package VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		prepared.setNString(1, packageNo);
		prepared.setInt(2, weight);
		prepared.setInt(3, width);
		prepared.setInt(4, height);
		prepared.setInt(5, depth);
		prepared.setNString(6, deliveryDate);
		prepared.setNString(7, status);
		prepared.setInt(8, fragile);
		prepared.setNString(9, matierial);
		prepared.setInt(10, chemical);
		prepared.setNString(11, riskType);
		prepared.setInt(12, liquid);
		prepared.setInt(13, volume);
		prepared.setNString(14, destinationID);
		try {
			prepared.executeUpdate();
			con.close();
			insertion = true;
		}catch (SQLException e){
			System.out.println("Error at insertion");
		}

		return insertion;
		
	}
	public static ArrayList<String> getLocationIDs() throws ClassNotFoundException, SQLException{
		ArrayList<String> list = new ArrayList<String>();
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();

		ResultSet rs=stmt.executeQuery("select * from location"); 
		while(rs.next()) 
			list.add(rs.getString(1));
		
		con.close();
		
		return list;
	}
	public static ArrayList<String> getPackageIDs() throws ClassNotFoundException, SQLException{
		ArrayList<String> list = new ArrayList<String>();
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();

		ResultSet rs=stmt.executeQuery("select PackageNo from package"); 
		while(rs.next()) 
			list.add(rs.getString("PackageNo"));
		
		con.close();
		
		return list;
	}
	public static Packages getPackage(String id){
		ArrayList<String> list = new ArrayList<String>();
		Packages pack;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

			Statement stmt=con.createStatement();

			ResultSet rs=stmt.executeQuery("SELECT * FROM package "
					+ "WHERE PackageNo='"+id+"'"); 
			rs.next();
			String packageNo = rs.getString(1);
			int weight = rs.getInt(2);
			int width = rs.getInt(3);
			int height = rs.getInt(4);
			int depth = rs.getInt(5);
			String deliveryDate  = rs.getString(6);
			String status = rs.getString(7);
			int fragile= rs.getInt(8);
			String matierial= rs.getString(9);
			int chemical = rs.getInt(10);
			String riskType= rs.getString(11);
			int liquid= rs.getInt(12);
			int volume = rs.getInt(13);
			String destinationID = rs.getString(14);	
			
			pack = new Packages(packageNo, weight, width, height, depth, deliveryDate, status, fragile, matierial, chemical, riskType, liquid, volume, destinationID);
			con.close();
			return pack;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println();;
		}

		return null;
		

	}
	
	public static boolean editPackage(String id, Packages pack) throws Exception {
		boolean insertion=false;
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();
		stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=0");
		String sql=("DELETE FROM package WHERE PackageNo='"+id+"'");  
		stmt.executeUpdate(sql);  
		stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=0");
		con.close();
		
		
		return addPack(pack);
	}
	public static void deletePackage(String id) throws Exception {
		boolean deletion=false;
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();
		String sql=("DELETE FROM package WHERE PackageNo='"+id+"'");  
		stmt.executeUpdate(sql);

	}
	//Customers============================
	
	public static boolean addCustomer(Customer customer) throws Exception{
		boolean insertion = false;
		
    	String ID = customer.getID();
    	String address = customer.getAddress();
    	String userName = customer.getUserName();
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
		PreparedStatement prepared = con.prepareStatement("INSERT INTO customer VALUES(?,?,?)");
		prepared.setNString(1, userName);
		prepared.setNString(2, address);
		prepared.setNString(3, ID);
		try {
			prepared.executeUpdate();
			con.close();
			insertion = true;
		}catch (SQLException e){
			System.out.println("Error at insertion");
		}
		return insertion;
	}
	public static boolean addUser(User u) throws Exception{
		boolean insertion = false;
		
    	String userName = u.getUserName();
    	String password = u.getPassWord();
    	String email = u.getEmail();
    	String Fname = u.getfName();
    	String Lname = u.getlName();
    	String phone = u.getPhone();
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  
		PreparedStatement prepared = con.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?)");
		prepared.setNString(1, userName);
		prepared.setNString(2, password);
		prepared.setNString(3, email);
		prepared.setNString(4, Fname);
		prepared.setNString(5, Lname);
		prepared.setNString(6, phone);
		try {
			prepared.executeUpdate();
			con.close();
			insertion = true;
		}catch (SQLException e){
			System.out.println("Error at insertion");
		}
		return insertion;
	}
	public static Customer getCustomers(String id) {
		Customer c=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

			Statement stmt=con.createStatement();

			ResultSet rs=stmt.executeQuery("select * from customer INNER JOIN user on user.Username=customer.Username AND Customer_ID='"+id+"'"); 
			rs.next();
	    	String userName = rs.getString(1);
	    	String address = rs.getString(2);
	    	String ID = rs.getString(3);
	    	String password =rs.getString(5);
	    	String email = rs.getString(6);
	    	String Fname = rs.getString(7);
	    	String Lname = rs.getString(8);
	    	String phone = rs.getString(9);
	    	c = new Customer(userName, address, ID);
	    	c.setPassWord(password);
	    	c.setEmail(email);
	    	c.setfName(Fname);
	    	c.setlName(Lname);
	    	c.setPhone(phone);
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println();;
		}

		
		return c;
		
	}
	public static ArrayList<String> getCustomerUsername() {
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

			Statement stmt=con.createStatement();

			ResultSet rs=stmt.executeQuery("select Username from customer "); 
			while(rs.next()) 
				list.add(rs.getString(1));
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println();;
		}

		
		return list;
		
	}
	public static ArrayList<String> getCustomerIDs() throws ClassNotFoundException, SQLException{
		ArrayList<String> list = new ArrayList<String>();
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();

		ResultSet rs=stmt.executeQuery("select Customer_ID from customer"); 
		while(rs.next()) 
			list.add(rs.getString("Customer_ID"));
		
		con.close();
		
		return list;
	}
	//edit
	public static boolean editCustomer(String id, Customer c) throws Exception {

		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();
		String sql=("DELETE FROM customer WHERE Customer_ID='"+id+"'");  
		stmt.executeUpdate(sql);  
		
		
		
		return addCustomer(c);
	}
	public static boolean editUser(String id, User u) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();
		stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=0");
		String sql=("DELETE FROM user WHERE user.Username='"+id+"'");
		stmt.executeUpdate(sql);  
		stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=1");
		con.close();
		
		
		return addUser(u);
	}
//delete ==============
	
	public static void deleteUser(String id) throws Exception {
		boolean deletion=false;
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();
		stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=0");
		String sql=("DELETE FROM user WHERE user.Username='"+id+"'");
		stmt.executeUpdate(sql);  
		stmt.executeUpdate("SET GLOBAL FOREIGN_KEY_CHECKS=1");
		con.close();

	}
	public static void deleteCustomer(String id) throws Exception {
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  


		Statement stmt=con.createStatement();
		String sql=("DELETE FROM customer WHERE Customer_ID='"+id+"'");  
		stmt.executeUpdate(sql);  

	}
	//generate report page==================================================
	public static String generateReport(String city, int f, int c, int l, String from, String to, String username, int delay, int lost, int delivered, int transit, int dmg) throws Exception{
		String sql="";
		String result ="";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select LocationId from location "
				+ "WHERE City='"+city+"'");
		rs.next();
		String ID = rs.getString(1);
		String ships = ("SELECT packageNo FROM package "
				+ "WHERE deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND DestinationID='"+ID+"' ");

		sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND payment.confirmed='1'");
		rs=stmt.executeQuery(sql); 
		rs.next();
		result+= "Number of confirmed payments: "+rs.getInt("count")+"\n";
		sql=("Select package.PackageNo FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
				+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND payment.confirmed='1'");
		rs=stmt.executeQuery(sql); 
		while(rs.next())
			result+="Package: "+rs.getString(1)+"\n";
		result+="============================================\n";
		
		
		sql=("Select PackageNo,Status FROM package "
				+ "WHERE Status='delayed' OR Status='delivered' OR Status='lost'");
		rs=stmt.executeQuery(sql); 
		while(rs.next())
			result+= "Package "+rs.getInt(1)+" "+rs.getString(2)+"\n";
		result+="===================Packages type=========================\n";
		
		
		sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
				+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.LiquidFlag='0' AND package.FragileFlag='0' AND package.ChemicalFlag='0'");
		rs=stmt.executeQuery(sql); 
		rs.next();
		result+= "Number of Regular packages: "+rs.getInt("count")+"\n";	
		
		if(f==1) {
			ships+="AND FragileFlag='1' ";
			sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.FragileFlag='1'");
			rs=stmt.executeQuery(sql); 
			rs.next();
			result+= "Number of Fragile packages: "+rs.getInt("count")+"\n";
		}
		if(c==1) {
			ships+="AND ChemicalFlag='1' ";
			sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.ChemicalFlag='1'");
			rs=stmt.executeQuery(sql); 
			rs.next();
			result+= "Number of Chemical packages: "+rs.getInt("count")+"\n";
		}
		if(l==1) {
			ships+="AND LiquidFlag='1' ";
			sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.LiquidFlag='1'");
			rs=stmt.executeQuery(sql); 
			rs.next();
			result+= "Number of Liquid packages: "+rs.getInt("count")+"\n";

		}
		if(lost==1) {
			ships+="AND Status='lost' ";
			sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.Status='lost'");
			rs=stmt.executeQuery(sql); 
			rs.next();
			result+= "Number of lost packages: "+rs.getInt("count")+"\n";
		}if(delay==1) {
			ships+="AND Status='delayed' ";
			sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.Status='delayed'");
			rs=stmt.executeQuery(sql); 
			rs.next();
			result+= "Number of delayed packages: "+rs.getInt("count")+"\n";
		}
		if(delivered==1) {
			ships+="AND Status='delivered' ";
			sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.Status='delivered'");
			rs=stmt.executeQuery(sql); 
			rs.next();
			result+= "Number of delivered packages: "+rs.getInt("count")+"\n";
		}
		if(transit==1) {
			ships+="AND Status='transit' ";
			sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.Status='transit'");
			rs=stmt.executeQuery(sql); 
			rs.next();
			result+= "Number of in transit packages: "+rs.getInt("count")+"\n";
		}
		if(dmg==1) {
			ships+="AND Status='damaged' ";
			sql=("Select COUNT(*) AS count FROM package INNER JOIN payment On package.PackageNo=payment.PackageNo "
					+ "WHERE package.deliveryDate BETWEEN '"+from+"' AND '"+to+"' AND package.Status='damaged'");
			rs=stmt.executeQuery(sql); 
			rs.next();
			result+= "Number of damaged packages: "+rs.getInt("count")+"\n";
		}
		result+="====================Customer========================\n";
		result+="Packages for customer "+username+"\n";
		sql=("Select * FROM ship "
				+ "WHERE From_Username='"+username+"' OR to_Username='"+username+"'");
		rs=stmt.executeQuery(sql); 
		while(rs.next())
			result+="-Package "+rs.getString(3)+"\n";
		result+="==================== "+city +" ========================\n";
		rs=stmt.executeQuery(ships); 
		while(rs.next())
			result+="-Package "+rs.getString(1)+"\n";
		return result;
	}
	public static ArrayList<String> getCities() throws ClassNotFoundException, SQLException{
		ArrayList<String> list = new ArrayList<String>();
		
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/packagessystemdb","root","");  

		Statement stmt=con.createStatement();

		ResultSet rs=stmt.executeQuery("select DISTINCT city from location"); 
		while(rs.next()) 
			list.add(rs.getString(1));
		
		con.close();
		
		return list;
	}
	
	
}
