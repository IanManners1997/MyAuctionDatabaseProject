import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

public class Driver {

	private static Scanner scan = new Scanner(System.in);
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement prepStatement;
	private static ResultSet resultSet;
	private static MyAuction myAuction = new MyAuction();

	public static void main(String args[]) {

		String oracleUsername = "username";
		String oraclePassword = "password";
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
			//SET USER AND PASSWORD HERE
			connection = DriverManager.getConnection(url, oracleUsername, oraclePassword);
		} catch (Exception e) {
			System.out.println("Error connecting to database: " + e.toString());
		}

		//Database is normally initialized in main, this is a helper function to do it
		//without calling main.
		myAuction.initForTesting();

		testNewAdmin();

		testNewCustomer();

		testUpdateSysDate();

		testProductStatisticsAll();

		testProductStatisticsSpecific();

		testBrowseProductsHighBid();

		testBrowseProductsProductName();

		testSearchForProducts();

		testPutProductForAuction();

		testBidOnProducts();

		testSellProductsSell();

		testSellProductsWithdraw();

	}

	private static void testNewAdmin() {
		try {
			System.out.println("\nTest adding a new admin.");
			String query = "select * from Administrator";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Login: " + resultSet.getString(1) + " Pass: " + resultSet.getString(2) +
						" Name: " + resultSet.getString(3) + " Address: " + resultSet.getString(4) + " Email: " 
						+ resultSet.getString(5));
			}
			resultSet.close();

			myAuction.newCustomerRegistration();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Login: " + resultSet.getString(1) + " Pass: " + resultSet.getString(2) +
						" Name: " + resultSet.getString(3) + " Address: " + resultSet.getString(4) + " Email: " 
						+ resultSet.getString(5));
			}
			resultSet.close();
			System.out.println("Finished testing adding new admin.");
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	private static void testNewCustomer() {
		try {
			System.out.println("\nTest adding a new customer.");
			String query = "select * from Customer";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Login: " + resultSet.getString(1) + " Pass: " + resultSet.getString(2) +
						" Name: " + resultSet.getString(3) + " Address: " + resultSet.getString(4) + " Email: " 
						+ resultSet.getString(5));
			}
			resultSet.close();

			myAuction.newCustomerRegistration();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Login: " + resultSet.getString(1) + " Pass: " + resultSet.getString(2) +
						" Name: " + resultSet.getString(3) + " Address: " + resultSet.getString(4) + " Email: " 
						+ resultSet.getString(5));
			}
			resultSet.close();
			System.out.println("Finished testing adding new customer.");
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	private static void testUpdateSysDate() {
		try {
			System.out.println("\nTest updating sys date.");
			String query = "select * from ourSysDATE";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()) {
				System.out.println("Date: " + resultSet.getDate(1));
			}
			resultSet.close();

			myAuction.updateSystemDate();
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			if(resultSet.next()) {
				System.out.println("Date: " + resultSet.getDate(1));
			}
			resultSet.close();
			System.out.println("Finishing test of update sys date.");
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	private static void testProductStatisticsAll() {
		System.out.println("\nTest retrieving product statistics for all products.");
		
		try {
			myAuction.productStatistics();
			System.out.println("Finished test for product statistics for all products.");
		}catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

		
	}

	private static void testProductStatisticsSpecific() {
		System.out.println("\nTest retrieving product statistics for specific customer.");
		try{
			myAuction.productStatistics();
			System.out.println("Finished test for product statistics for specific customer.");
		}catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	private static void testBrowseProductsHighBid() {
		System.out.println("\nTest browsing products choosing the sort by highest bid.");
		try{
			myAuction.browseProducts();
		System.out.println("Finished test for browsing products by highest bid.");
		}catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
	}

	private static void testBrowseProductsProductName() {
		System.out.println("\nTest browsing products choosing the sort by product name.");
		try{
			myAuction.browseProducts();
		System.out.println("Finished test for browsing products by product name.");
		}catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
	}

	private static void testSearchForProducts() {
		System.out.println("\nTest searching for products by keywords (lower case).");
		try{
			myAuction.searchForProducts();
		System.out.println("Finished test for searching for products by keyword.");
		}catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	private static void testPutProductForAuction() {
		System.out.println("\nTest putting products up for auction.");
		String query = "select * from Product";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Auction ID: " + resultSet.getInt(1) + " Name " + resultSet.getString(2) +
						" Desc: " + resultSet.getString(3) + " Seller: " + resultSet.getString(4) + " StartDate: " 
						+ resultSet.getDate(5) + " MinPrice: " + resultSet.getInt(6) + " # Days: " + 
						resultSet.getInt(7) + " Status: " + resultSet.getString(8));
			}
			resultSet.close();

			myAuction.putProductForAuction();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Auction ID: " + resultSet.getInt(1) + " Name " + resultSet.getString(2) +
						" Desc: " + resultSet.getString(3) + " Seller: " + resultSet.getString(4) + " StartDate: " 
						+ resultSet.getDate(5) + " MinPrice: " + resultSet.getInt(6) + " # Days: " + 
						resultSet.getInt(7) + " Status: " + resultSet.getString(8));
			}
			resultSet.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
		System.out.println("Finished test of putting products up for auction.");
	}

	private static void testBidOnProducts() {
		System.out.println("\nTest bidding on products.");
		try{
			String query = "select * from Bidlog";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Bidsn: " + resultSet.getInt(1) + " Auction ID: " + resultSet.getInt(2)
						+ " Bidder: " + resultSet.getString(3) + " Bid Time: " + resultSet.getDate(4)
						+ " Amount: " + resultSet.getInt(5));
			}
			resultSet.close();

			myAuction.bidOnProducts();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Bidsn: " + resultSet.getInt(1) + " Auction ID: " + resultSet.getInt(2)
						+ " Bidder: " + resultSet.getString(3) + " Bid Time: " + resultSet.getDate(4)
						+ " Amount: " + resultSet.getInt(5));
			}
			resultSet.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		System.out.println("Finished testing bidding on products.");
	}

	private static void testSellProductsSell() {
		System.out.println("\nTest sell on products and hit sell.");
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Auction ID: " + resultSet.getInt(1) + " Name " + resultSet.getString(2) +
						" Desc: " + resultSet.getString(3) + " Seller: " + resultSet.getString(4) + " StartDate: " 
						+ resultSet.getDate(5) + " MinPrice: " + resultSet.getInt(6) + " # Days: " + 
						resultSet.getInt(7) + " Status: " + resultSet.getString(8) + " Buyer " 
						+ resultSet.getString(9) + " Sell Date: " + resultSet.getDate(10) + " Amount: "
						+ resultSet.getInt(11));
			}
			resultSet.close();

			myAuction.sellProduct();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Auction ID: " + resultSet.getInt(1) + " Name " + resultSet.getString(2) +
						" Desc: " + resultSet.getString(3) + " Seller: " + resultSet.getString(4) + " StartDate: " 
						+ resultSet.getDate(5) + " MinPrice: " + resultSet.getInt(6) + " # Days: " + 
						resultSet.getInt(7) + " Status: " + resultSet.getString(8) + " Buyer " 
						+ resultSet.getString(9) + " Sell Date: " + resultSet.getDate(10) + " Amount: "
						+ resultSet.getInt(11));
			}
			resultSet.close();
			System.out.println("Finished testing selling products.")
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	private static void testSellProductsWithdraw() {
		System.out.println("\nTest sell on products and withdraw.");
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Auction ID: " + resultSet.getInt(1) + " Name " + resultSet.getString(2) +
						" Desc: " + resultSet.getString(3) + " Seller: " + resultSet.getString(4) + " StartDate: " 
						+ resultSet.getDate(5) + " MinPrice: " + resultSet.getInt(6) + " # Days: " + 
						resultSet.getInt(7) + " Status: " + resultSet.getString(8) + " Buyer " 
						+ resultSet.getString(9) + " Sell Date: " + resultSet.getDate(10) + " Amount: "
						+ resultSet.getInt(11));
			}
			resultSet.close();

			myAuction.sellProduct();

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Auction ID: " + resultSet.getInt(1) + " Name " + resultSet.getString(2) +
						" Desc: " + resultSet.getString(3) + " Seller: " + resultSet.getString(4) + " StartDate: " 
						+ resultSet.getDate(5) + " MinPrice: " + resultSet.getInt(6) + " # Days: " + 
						resultSet.getInt(7) + " Status: " + resultSet.getString(8) + " Buyer " 
						+ resultSet.getString(9) + " Sell Date: " + resultSet.getDate(10) + " Amount: "
						+ resultSet.getInt(11));
			}
			resultSet.close();
			System.out.println("Finished testing selling products and withdrawing.")
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

}
