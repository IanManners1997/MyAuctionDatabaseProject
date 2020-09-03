/**
 * Phase 2 Submission
 */

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class MyAuction {

	private static Scanner scan = new Scanner(System.in);
	private static Connection connection;
	private static Statement statement;
	private static PreparedStatement prepStatement;
	private static ResultSet resultSet;
	private static String query;
	private static int userType;
	private static String user;
	private static String username;
	private static String password;
	private static String name;
	private static String address;
	private static String email;
	private static String date;
	private static String hours;
	private static String category;
	private static String keyword1;
	private static String keyword2;
	private static int menuChoice;
	private static int num_of_day;
	private static int months;
	private static String description;
	private static int amount;

	public static void main(String args[]) {

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
			//SET USER AND PASSWORD HERE
			connection = DriverManager.getConnection(url, "username", "password");
		} catch (Exception e) {
			System.out.println("Error connecting to database: " + e.toString());
		}

		System.out.println("\nWelcome to the auction.");
		System.out.println("Are you an adminstrator or customer?");
		System.out.println("Please enter 1 for adminstrator or 2 for customer.\n");

		userType = scan.nextInt();
		switch(userType) {
			case 1: loginAdmin(); break;
			case 2: loginCustomer(); break;
			default: 
				System.out.println("Sorry, please try again using 1 for administrator and 2 for customer.");
				break;
		}

		try {
			connection.close();
		} catch (Exception e) {
			System.out.println("Error closing database: " + e.toString());
		}

	}

	static void initForTesting() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
			//SET USER AND PASSWORD HERE
			connection = DriverManager.getConnection(url, "mfs35", "4153613");
		} catch (Exception e) {
			System.out.println("Error connecting to database: " + e.toString());
		}
		username = "testUser";
	}

	static void loginAdmin() {
		System.out.println("\nPlease enter your username followed by your password.");
		
		boolean running = true;
		while(running) {
			System.out.print("User: ");
			username = scan.next();
			System.out.print("Pass: ");
			password = scan.next();
			
			//set the user for the remainder of program to use
			user = username;

			try {
				query = "SELECT Count(login) FROM Administrator where login = ? and password = ?";
				prepStatement = connection.prepareStatement(query);
				prepStatement.setString(1, username);
				prepStatement.setString(2, password);
				resultSet = prepStatement.executeQuery();
				if(resultSet.next()) {
					if(resultSet.getInt(1) == 1) {
						running = false;
						displayAdminMenu();
					} else {
						System.out.println("Invalid username or password.");
					}
				}
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Error with database: " + e.toString());
			}
		}
	}

	private static void loginCustomer() {
		System.out.println("\nPlease enter your username followed by your password.");
		
		boolean running = true;
		while(running) {
			System.out.print("User: ");
			username = scan.next();
			System.out.print("Pass: ");
			password = scan.next();
			
			//set the user for the remainder of the program to user
			user = username;

			try {
				query = "SELECT Count(login) FROM Customer where login = ? and password = ?";
				prepStatement = connection.prepareStatement(query);
				prepStatement.setString(1, username);
				prepStatement.setString(2, password);
				resultSet = prepStatement.executeQuery();
				if(resultSet.next()) {
					if(resultSet.getInt(1) == 1) {
						running = false;
						displayCustomerMenu();
					} else {
						System.out.println("Invalid username or password.");
					}
				}
				resultSet.close();
			} catch (Exception e) {
				System.out.println("Error with database: " + e.toString());
			}
		}
	}

	private static void displayAdminMenu() {
		System.out.println("\nWelcome " + username);
		System.out.println("Please choose a selection using the number on the left.\n");
		System.out.println("(1) New Customer Registration");
		System.out.println("(2) Update System Date");
		System.out.println("(3) Product Statistics");
		System.out.println("(4) Other Statistics\n");

		menuChoice = scan.nextInt();
		switch(menuChoice) {
			case 1: newCustomerRegistration(); break;
			case 2: updateSystemDate(); break;
			case 3: productStatistics(); break;
			case 4: otherStatistics(); break;
			default:
				System.out.println("Sorry that was not a valid choice.");
				break;
		}
	}

	private static void displayCustomerMenu() throws Exception {
		System.out.println("\nWelcome " + username);
		System.out.println("Please choose a selection using the number on the left.\n");
		System.out.println("(1) Browse Products");
		System.out.println("(2) Search For Products");
		System.out.println("(3) Put Product Up For Auction");
		System.out.println("(4) Bid On Products");
		System.out.println("(5) Sell/Withdraw Product Under Auction");
		System.out.println("(6) Get Suggestions\n");

		menuChoice = scan.nextInt();
		switch(menuChoice) {
			case 1: browseProducts(); break;
			case 2: searchForProducts(); break;
			case 3: putProductForAuction(); break;
			case 4: bidOnProducts(); break;
			case 5: sellProduct(); break;
			case 6: getSuggestion(); break;
			default:
				System.out.println("Sorry that was not a valid choice.");
				break;
		}
	}

	static void newCustomerRegistration() {
		System.out.println("\nTo register a new customer please follow the prompts.");
		System.out.print("New Customer Username: ");
		username = scan.next();
		System.out.print("New Customer Password: ");
		password = scan.next();
		//Clears input for nextline, otherwise nextline skips
		scan.nextLine();
		System.out.print("New Customer Name: ");
		name = scan.nextLine();
		System.out.print("New Customer Address: ");
		address = scan.nextLine();
		System.out.print("New Customer Email: ");
		email = scan.nextLine();
		System.out.print("Type 1 for the user being an admin, or 2 for the user being a customer.");
		menuChoice = scan.nextInt();

		if(menuChoice == 1) {
			query = "insert into Administrator values (?, ?, ?, ?, ?)";
		} else {
			query = "insert into Customer values (?, ?, ?, ?, ?)";
		}

		try {
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, username);
			prepStatement.setString(2, password);
			prepStatement.setString(3, name);
			prepStatement.setString(4, address);
			prepStatement.setString(5, email);
			prepStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error with database: " + e.toString());
		}

		System.out.println("\nNew user created.");

	}

	static void updateSystemDate() {
		System.out.println("\nPlease enter the date in format dd-mon-yyyy hh:mi:ss");
		System.out.print("Date: ");
		
		date = scan.next();
		hours = scan.next();
		try{
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
			java.sql.Date date_reg = new java.sql.Date (df.parse(date + " " + hours).getTime());

			query = "update ourSysDATE set c_date = ?";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setDate(1, date_reg);
			prepStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error with database: " + e.toString());
		}

		System.out.println("\nDate updated.");
	}

	static void productStatistics() {
		System.out.println("\nFor the report, would you like to display all products or products from a specific customer?");
		System.out.println("(1) All Products");
		System.out.println("(2) Specific Customer's Products");

		try {
			menuChoice = scan.nextInt();
			if(menuChoice == 1) {
				query = "select name, status, amount, buyer from product";
				prepStatement = connection.prepareStatement(query);
				resultSet = prepStatement.executeQuery();
			} else {
				System.out.println("Please enter the customer whose products you'd like to see.");
				scan.nextLine(); //clears out nextline after a nextInt
				username = scan.nextLine();
				query = "select name, status, amount, buyer from product where seller = ?";
				prepStatement = connection.prepareStatement(query);
				prepStatement.setString(1, username);
				resultSet = prepStatement.executeQuery();
			}
			while(resultSet.next()) {
				System.out.println("Name: " + resultSet.getString(1) + " Status: " + resultSet.getString(2)
						+ " Amount: " + resultSet.getInt(3) + " Buyer/Bidder: " + resultSet.getString(4));
			}
		} catch (Exception e) {
			System.out.println("Error with database: " + e.toString());
		}
	}

	static void otherStatistics() {
		System.out.println("\nThe system supports various statistics.");
		System.out.println("Please choose the stats you want to retrieve.");
		System.out.println("(1) Highest Volume Categories (no sub categories)");
		System.out.println("(2) Highest Volume Categories (no parent categories)");
		System.out.println("(3) Most Active Bidders");
		System.out.println("(4) Most Active Buyers\n");

		menuChoice = scan.nextInt();
		switch(menuChoice) {
			case 1: highestVolumeNoSub(); break;
			case 2: highestVolumeNoParent(); break;
			case 3: mostActiveBidders(); break;
			case 4: mostActiveBuyers(); break;
			default:
				System.out.println("Sorry that was not a valid choice.");
				break;
		}
	}

	static void browseProducts() {
		boolean running = true;

		System.out.println("\nPlease choose from the following categories to browse.");
		System.out.println("To choose, type out the full name of the category and press enter.\n");

		try {
			query = "select * from category where parent_category is null";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			int i = 1;
			while(resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
			scan.nextLine(); //Clears out scanner for upcoming nextline
			while(running) {
				menuChoice = 0;
				category = scan.nextLine();
				//Check if this is a leaf node
				query = "select Count(name) from Category where parent_category = ?";
				prepStatement = connection.prepareStatement(query);
				prepStatement.setString(1, category);
				resultSet = prepStatement.executeQuery();
				resultSet.next();
				//If it is a leaf node, see how customer wants to see results, then display them
				if(resultSet.getInt(1) == 0) { 
					System.out.println("How would you like to see these items?");
					System.out.println("\n(1) By Highest Bid");
					System.out.println("(2) By Product Name (A-Z)");

					menuChoice = scan.nextInt();
					if(menuChoice == 1) {
						query = "select p.auction_id, name, start_date, amount from product p, belongsto b " +  
								"where p.status = 'under auction' and p.auction_id = b.auction_id and b.category = ? " + 
								"order by p.amount desc";
					} else {
						query = "select p.auction_id, name, start_date, amount from product p, belongsto b " + 
								"where p.status = 'under auction' and p.auction_id = b.auction_id and b.category = ? " + 
								"order by p.name asc";
					}
					running = false;
				}else {
					query = "select name from category where parent_category = ?";
				}
				prepStatement = connection.prepareStatement(query);
				prepStatement.setString(1, category);
				resultSet = prepStatement.executeQuery();
				System.out.print("\n"); //Looks nicer with this
				while(resultSet.next()) {
					if(menuChoice == 0) {
						System.out.println(resultSet.getString(1));
					} else {
						System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) 
							+ " " + resultSet.getString(3) + " " + resultSet.getString(4));
					}
				}
				
			}
			resultSet.close();
		} catch (Exception e) {
			System.out.println("Error with database: " + e.toString());
		}

	}

	static void searchForProducts() {
		System.out.println("Please enter the first keyword you'd like to use in the search.");
		keyword1 = scan.next();
		System.out.println("Please enter the second keyword to search with.");
		keyword2 = scan.next();

		//Append % to the strings on each side
		keyword1 = "%" + keyword1 + "%";
		keyword2 = "%" + keyword2 + "%";

		try {
			query = "select * from product where lower(description) like ? and lower(description) like ?";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, keyword1);
			prepStatement.setString(2, keyword2);
			resultSet = prepStatement.executeQuery();
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3)
						+ " " + resultSet.getString(4) + " " + resultSet.getDate(5) + " " + resultSet.getInt(6)
						+ " " + resultSet.getInt(7) + " " + resultSet.getString(8) + " " + resultSet.getString(9) 
						+ " " + resultSet.getDate(10) + " " + resultSet.getInt(11));
			}
		} catch (Exception e) {
			System.out.println("Error with database: " + e.toString());
		}
	}

	static void putProductForAuction() {
		scan.nextLine(); //empty scanner
		System.out.println("\nPlease enter product name.");
		name = scan.nextLine();
		System.out.println("Please enter the description of the product.");
		description = scan.nextLine();
		System.out.println("Please enter the categories.");
		category = scan.next();
		System.out.println("Please enter the minimum auction price.");
		amount = scan.nextInt();
		System.out.println("Please enter the number of days for auction.");
		num_of_day = scan.nextInt();
		System.out.println("\nPlease enter the date in format dd-mon-yyyy hh:mi:ss");
		
		date = scan.next();
		hours = scan.next();

		try {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
			java.sql.Date date_reg = new java.sql.Date (df.parse(date + " " + hours).getTime());

			query = "call proc_putProduct(?, ?, ?, ?, ?, ?, ?)";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, name);
			prepStatement.setString(2, description);
			prepStatement.setString(3, user);
			prepStatement.setString(4, category);
			prepStatement.setInt(5, amount);
			prepStatement.setInt(6, num_of_day);
			prepStatement.setDate(7, date_reg);
			prepStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error with database: " + e.toString());
		}

	}

	static void bidOnProducts() throws Exception
	{
		int auction_id = -1;
		int bid = -1;
		scan.nextLine(); //Empty out scanner
		System.out.println("What is the auction_id of the product you wish to bid on?");

		try
		{
			auction_id = Integer.parseInt(scan.nextLine());
		}
		catch(NumberFormatException e)
		{
			System.out.println("That auction_id is not valid. Please enter a different auction_id.");
			bidOnProducts();
			return;
		}

		query = "SELECT * FROM PRODUCT WHERE auction_id = ?";
		prepStatement = connection.prepareStatement(query);
		prepStatement.setInt(1, auction_id);
		resultSet = prepStatement.executeQuery();

		if (!resultSet.next())
		{
			System.out.println("Please enter a valid auction_id.");
			bidOnProducts();
			return;
		}

		System.out.print("How much would you like to bid on this item? (whole number)\n$");

		try
		{
			bid = Integer.parseInt(scan.nextLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Please enter a valid amount");
			bidOnProducts();
			return;
		}

		connection.setAutoCommit(false);

		query = "LOCK TABLE PRODUCT IN EXCLUSIVE MODE";
		statement = connection.createStatement();
		statement.execute(query);

		query = "SELECT amount FROM PRODUCT WHERE auction_id = ?";
		prepStatement = connection.prepareStatement(query);
		prepStatement.setInt(1, auction_id);
		resultSet = prepStatement.executeQuery();
		resultSet.next();
		int amount = resultSet.getInt(1);

		if (bid <= amount)
		{
			System.out.println("Your bid not higher than the current high bidder.");
			connection.commit();
			connection.setAutoCommit(true);
			return;
		}

		query = "SELECT MAX(bidsn) FROM BIDLOG";
		resultSet = statement.executeQuery(query);

		int bidsnum = -1;

		if (resultSet.next())
		{
			bidsnum = resultSet.getInt(1) + 1;
		}

		query = "SELECT MAX(c_date) FROM ourSysDate";
		resultSet = statement.executeQuery(query);
		resultSet.next();
		Date bid_date = resultSet.getDate(1);

		query = "INSERT INTO BIDLOG VALUES(?, ?, ?, ?, ?)";
		prepStatement = connection.prepareStatement(query);

		prepStatement.setInt(1, bidsnum);
		prepStatement.setInt(2, auction_id);
		prepStatement.setString(3, username);
		prepStatement.setDate(4, bid_date);
		prepStatement.setInt(5, bid);

		prepStatement.executeUpdate();

		System.out.println("Your bid was successful.");
		connection.commit();
		connection.setAutoCommit(true);
		return;
}

	static void sellProduct() throws Exception
	{
		System.out.println("Enter the auction_id of the product auction you would like to kill.");

		int auction_id = -1;

		try
		{
			auction_id = Integer.parseInt(scan.nextLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Please enter a valid auction_id");
			sellProduct();
			return;
		}

		query = "SELECT * FROM PRODUCT WHERE auction_id = ?";
		prepStatement = connection.prepareStatement(query);
		prepStatement.setInt(1, auction_id);
		resultSet = prepStatement.executeQuery();

		if (!resultSet.next())
		{
			System.out.println("Please enter a valid auction_id");
			sellProduct();
			return;
		}

		if (!resultSet.getString(8).equals("under auction"))
		{
			System.out.println("This product has already completed. Please try a new product");
			sellProduct();
			return;
		}

		if (!resultSet.getString(4).equals(username))
		{
			System.out.println("This is not your Product.");
			sellProduct();
			return;
		}

		query = "SELECT COUNT(*) FROM BIDLOG WHERE auction_id = ?";
		prepStatement = connection.prepareStatement(query);
		prepStatement.setInt(1, auction_id);
		resultSet = prepStatement.executeQuery();
		resultSet.next();
		int numBids = resultSet.getInt(1);

		if (numBids == 0)
		{
			System.out.println("You have not recieved any bids. Removing product now");
			query = "UPDATE PRODUCT SET status = 'withdrawn' WHERE auction_id = ?";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, auction_id);
			prepStatement.executeUpdate();
			return;
		}
		else if (numBids == 1)
		{
			query = "SELECT amount FROM PRODUCT WHERE auction_id = ?";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, auction_id);
			resultSet = prepStatement.executeQuery();
			resultSet.next();
			int amount = resultSet.getInt(1);

			System.out.println("Would you like to list this product for $" + amount + " or remove this product?");
			System.out.println("q: Sell");
			System.out.println("w: Withdraw");

			char option = scan.nextLine().toLowerCase().charAt(0);

			switch (option)
			{
				case 'q':
					query = "SELECT bidder FROM BIDLOG WHERE auction_id = ? AND amount = ?";
					prepStatement = connection.prepareStatement(query);
					prepStatement.setInt(1, auction_id);
					prepStatement.setInt(2, amount);
					resultSet = prepStatement.executeQuery();
					resultSet.next();
					String bidder = resultSet.getString(1);

					
					query = "SELECT MAX(c_date) FROM ourSysDate";
					resultSet = statement.executeQuery(query);
					resultSet.next();
					Date sell_date = resultSet.getDate(1);

					
					query = "UPDATE PRODUCT SET bidder = ?, sell_date = ?, status = 'sold', amount = ? WHERE auction_id = ?";
					prepStatement = connection.prepareStatement(query);
					prepStatement.setString(1, bidder);
					prepStatement.setDate(2, sell_date);
					prepStatement.setInt(3, amount);
					prepStatement.setInt(4, auction_id);
					statement.executeUpdate(query);

					System.out.println("Product sold for $" + amount + " to " + bidder);
					break;
				case 'w':
					query = "UPDATE PRODUCT SET status = 'withdrawn' WHERE auction_id = ?";
					prepStatement = connection.prepareStatement(query);
					prepStatement.setInt(1, auction_id);
					prepStatement.executeUpdate();

					System.out.println("Product removed");
					break;
			}
		}
		else
		{
			query = "select amount from (select amount from bidlog where auction_id = ? order by amount desc) where amount < (select MAX(amount) from bidlog where auction_id = ?) and rownum = 1";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, auction_id);
			prepStatement.setInt(2, auction_id);
			resultSet = prepStatement.executeQuery();
			resultSet.next();
			int amount = resultSet.getInt(1);

			System.out.println("Would you like to sell this Product for $" + amount + " or remove this product?");
			System.out.println("q: Sell");
			System.out.println("w: Withdraw");

			char option = scan.nextLine().toLowerCase().charAt(0);

			switch (option)
			{
				case 'q':
					query = "SELECT bidder FROM BIDLOG WHERE auction_id = ? AND amount = ?";
					prepStatement = connection.prepareStatement(query);
					prepStatement.setInt(1, auction_id);
					prepStatement.setInt(2, amount);
					resultSet = prepStatement.executeQuery();
					resultSet.next();
					String bidder = resultSet.getString(1);

					query = "SELECT MAX(c_date) FROM ourSysDate";
					resultSet = statement.executeQuery(query);
					resultSet.next();
					Date sell_date = resultSet.getDate(1);

					query = "UPDATE PRODUCTSET bidder = ?, sell_date = ?, status = 'sold',amount = ? WHERE auction_id = ?";
					prepStatement = connection.prepareStatement(query);
					prepStatement.setString(1, bidder);
					prepStatement.setDate(2, sell_date);
					prepStatement.setInt(3, amount);
					prepStatement.setInt(4, auction_id);
					prepStatement.executeUpdate();

					System.out.println("Product sold for $" + amount + " to " + bidder);
					break;
				case 'w':
					query = "UPDATE PRODUCT SET status = 'withdrawn' WHERE auction_id = ?";
					prepStatement = connection.prepareStatement(query);
					prepStatement.setInt(1, auction_id);
					prepStatement.executeUpdate();
					System.out.println("Product Removed");
					break;
			}
		}
	}


	static void getSuggestion() throws Exception
	{
		ArrayList<Integer> prevAuc = new ArrayList<Integer>();
		//I realized that we could turn this into a single query but I didnt have enough time to fix the function and test it
		//but it could be done by making one query as follows:"SELECT auction_id, name, description, amount, count(DISTINCT bidder) as friendNum FROM BidLog NATURAL JOIN Product WHERE bidder IN (SELECT bidder FROM BidLog WHERE auction_id IN(SELECT auction_id FROM BidLog WHERE bidder = ?) AND bidder <> ?)GROUP BY auction_id, name, description, amount ORDER BY friendNum DESC");

		query = "SELECT auction_id FROM BIDLOG WHERE bidder = ?";
		prepStatement = connection.prepareStatement(query);
		prepStatement.setString(1, username);
		resultSet = prepStatement.executeQuery();

		while (resultSet.next())
		{
			prevAuc.add(resultSet.getInt(1));
		}

		ArrayList<String> bidderList = new ArrayList<String>();

		for (Integer i : prevAuc)
		{
			//This is not user input, it is data taken from a resultSet.
			//No need to use a prepStatement
			query = "SELECT bidder FROM BIDLOG WHERE auction_id = " + i.intValue();
			resultSet = statement.executeQuery(query);

			while (resultSet.next())
			{
				String bidder = resultSet.getString(1);
				if (!bidder.equals(username) && !bidderList.contains(bidder))
				{
					bidderList.add(bidder);
				}
			}
		}

		if (bidderList.size() == 0)
		{
			System.out.println("Sorry, we could not find any suggestions. Please enter again");
			return;
		}

		String bidders = "bidder = '" + bidderList.get(0) + "' ";
		for (String s : bidderList)
		{
			if (bidderList.get(0).equals(s))
			{
				continue;
			}

			bidders += "OR bidder = '" + s + "' ";
		}

		//Same here, no user input, just taken from resultSet.
		//PrepStatement not necessary.
		query = "SELECT name, COUNT(name) as Desirability FROM BIDLOG JOIN PRODUCT ON BIDLOG.auction_id = PRODUCT.auction_id WHERE " + bidders + " GROUP BY name ORDER BY Desirability DESC";
		resultSet = statement.executeQuery(query);

		System.out.println("Suggested for you");

		while(resultSet.next())
		{
			System.out.println(resultSet.getString(1));
		}

		return;
	}

	static void highestVolumeNoSub() {
		System.out.println("How many months should we look back?");
		months = scan.nextInt();
		System.out.println("How many most highest volume categories should be listed?");
		menuChoice = scan.nextInt();
		try{
			query = "select name, Product_Count(name, ?) from category where name not in " +
					"(select parent_category from category where parent_category is not null) " +
					"order by Product_Count(name, ?) desc";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, months);
			prepStatement.setInt(2, months);
			resultSet = prepStatement.executeQuery();
			for(int i = 0; i < menuChoice; i++)
			{
				if(resultSet.next())
				{
					System.out.println("Category: " + resultSet.getString(1) + " Amount: " + resultSet.getInt(2));

				}else{
					System.out.println("No more results found.");
					break;
				}
			} 
 		} catch (Exception e) {
 			System.out.println("Error with database: " + e.toString());
 		}
	}

	static void highestVolumeNoParent() {
		System.out.println("How many months should we look back?");
		months = scan.nextInt();
		System.out.println("How many most highest volume categories should be listed?");
		menuChoice = scan.nextInt();
		try{
			query = "select name, Product_Count(name, ?) from Category WHERE parent_category is NULL order by Product_Count(name, ?) desc";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, months);
			prepStatement.setInt(2, months);
			resultSet = prepStatement.executeQuery();
			for(int i = 0; i < menuChoice; i++)
			{
				if(resultSet.next())
				{
					System.out.println("Category: " + resultSet.getString(1) + " Amount: " + resultSet.getInt(2));

				}else{
					System.out.println("No more results found.");
					break;
				}
			} 
 		} catch (Exception e) {
 			System.out.println("Error with database: " + e.toString());
 		}
	}

	static void mostActiveBidders() {
 		System.out.println("Code most active bidders here.");
 		System.out.println("How many months should we look back?");
 		months = scan.nextInt();
 		System.out.println("How many most active bidders should be listed?");
 		menuChoice = scan.nextInt();
 		try {
 			query = "select login, Bid_Count(login, ?) from customer order by Bid_Count(login, ?) desc";
 			prepStatement = connection.prepareStatement(query);
 			prepStatement.setInt(1, months);
 			prepStatement.setInt(2, months);
 			resultSet = prepStatement.executeQuery();
 			for(int i = 0; i < menuChoice; i++) {
 				if(resultSet.next()) {
 					System.out.println("User: " + resultSet.getString(1) + " Bids: " + resultSet.getInt(2));
 				} else {
 					break;
 				}
 			}
 		} catch (Exception e) {
 			System.out.println("Error with database: " + e.toString());
 		}
 	}

 	static void mostActiveBuyers() {
		System.out.println("How many most active buyers should be listed?");
		menuChoice = scan.nextInt();
		try {
			query = "select login, Bid_Count(login, ?) from customer order by Bid_Count(login, ?) desc";
			query = "select login, Buying_Amount(login, ?) from customer order by Buying_Amount(login, ?) desc";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, months);
			prepStatement.setInt(2, months);
			resultSet = prepStatement.executeQuery();
			for(int i = 0; i < menuChoice; i++) {
				resultSet.next();
				System.out.println("User: " + resultSet.getString(1) + " Bids: " + resultSet.getInt(2));
				if(resultSet.next()) {
					System.out.println("User: " + resultSet.getString(1) + " Amount: " + resultSet.getInt(2));
				} else {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error with database: " + e.toString());
		}
	}

}
