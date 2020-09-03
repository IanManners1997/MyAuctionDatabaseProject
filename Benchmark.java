import java.util.*;
import java.sql.*;

public class Benchmark{
	public static Connection dbconnect;
	
	public static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException{
		try{
			String dbUser = "imm17", dbPass = "4038364";
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String dbURL = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
			dbconnect = DriverManager.getConnection(dbURL, dbUser, dbPass);
			start();
		}catch(SQLException e){
			System.out.println("Error connecting to database.  Machine Error: " + e.toString());
		}
		finally{
			dbconnect.close();
		}
	}
	
	public static void start() throws SQLException{
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.updateSystemDate();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.loginAdmin();
			myAuction.displayAdminMenu();
			
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.newCustomerRegistration();
			myAuction.loginCustomer();
			myAuction.displayCustomerMenu();
		}
		
		for(int i = 1; i < 6; i++){
			System.out.println("Running test number " + i);
			myAuction.putProductForAuction();
		}
		
		for(int i = 6; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.putProductForAuction();
		}
		
		for(int i = 1; i < 6; i++){
			System.out.println("Running test number " + i);
			myAuction.searchForProducts();
		}
		for(int i = 6; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.searchForProducts();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.browseProducts();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.productStatistics();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.otherStatistics();
		}
		
		for(int i = 1; i < 6; i++){
			System.out.println("Running test number " + i);
			myAuction.bidOnProducts();
		}
		
		for(int i = 6; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.bidOnProducts();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.sellProduct();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.getSuggestion();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.highestVolumeNoSub();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.highestVolumeNoParent();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.mostActiveBidders();
		}
		
		for(int i = 1; i < 11; i++){
			System.out.println("Running test number " + i);
			myAuction.mostActiveBuyers();
		}
	}
}