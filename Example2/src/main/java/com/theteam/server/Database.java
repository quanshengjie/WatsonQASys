package com.theteam.server;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Database {
	private Connection c = null;
	private static boolean first = true;
	
	public Database()
	{
		first = false;
		if(first)
		{
			String sql;
			
			first = false;
			sql = "CREATE TABLE USERS " +
					"(fname		TEXT		NOT NULL," +
					" mname		TEXT		," +
					" lname		TEXT		NOT NULL," +
					" gender 	CHAR(10)	NOT NULL," +
					" email 	CHAR(50) PRIMARY KEY	NOT NULL," +
					" pwd		CHAR(50)	NOT NULL," +
					" month		INT			," +
					" day		INT			," +
					" year		INT			," +
					" university	CHAR(50)	," +
					" major		CHAR(50)	," +
					" gpascale	REAL		NOT NULL," +
					" cgpa		REAL		NOT NULL," +
					" mgpa		REAL		NOT NULL," +
					" program	REAL		NOT NULL)";
			createTable(sql);
			
			/*
			sql = "DROP TABLE QATable";
			dropTable(sql);
			*/
			sql = "CREATE TABLE QATable " +
	                "(email 	CHAR(50)      NOT NULL," +
	                " question           TEXT    NOT NULL, " + 
	                " answer            TEXT     NOT NULL)";
			createTable(sql);
			
			/*
			sql = "CREATE TABLE WebLink " + 
					"(id CHAR(1024) PRIMARY KEY     NOT NULL," +
	                " link            CHAR(1024)     NOT NULL)";
			createTable(sql);
			
			String urlList = "/home/yihan/Documents/CSE5914/WorkSpace/Example2/src/main/java/com/theteam/server/urlList.txt";
			try
			{
				FileReader fileReader = new FileReader(urlList);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while(bufferedReader.ready())
				{
					String line = bufferedReader.readLine();
					String elements[] = line.split(" ");
					sql = "INSERT INTO WebLink (id,link) " +
	                   "VALUES ('" + elements[0] + "' , '" + elements[2] + "' );"; 
					insert(sql);
				}
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
			*/
		}
	}
	
	private void connection()
	{
	    try 
	    {
	    	Class.forName("org.sqlite.JDBC");
	    	String databaseLocation = getClass().getResource("QA.sqilte").toString();
	    	System.out.println(databaseLocation);
	    	//String databaseLocation = "main/java/com/theteam/server/QA.sqlite";
	    	c = DriverManager.getConnection("jdbc:sqlite:" + databaseLocation);
	    	System.out.println("Opened database successfully");
	    } 
	    catch ( Exception e ) 
	    {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	}
	
	private void disconnection()
	{
		try 
		{
			c.close();
		    System.out.println("Close database successfully");
		} 
		catch ( Exception e ) 
		{
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	private void createTable(String sql)
	{
		connection();
	    Statement stmt = null;
	    try 
	    {
	    	stmt = c.createStatement();
	    	stmt.executeUpdate(sql);
	    	stmt.close();
	    	System.out.println("Table created successfully");
	    } 
	    catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	    disconnection();
	}
	
	private void dropTable(String sql)
	{
		connection();
	    Statement stmt = null;
	    try 
	    {
	    	stmt = c.createStatement();
	    	stmt.executeUpdate(sql);
	    	stmt.close();
	    	System.out.println("Table droppted successfully");
	    } 
	    catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	    disconnection();
	}
	
	public void insert(String sql)
	{
		connection();
		Statement stmt = null;
	    try 
	    {
	    	c.setAutoCommit(false);
	    	stmt = c.createStatement();
	    	stmt.executeUpdate(sql);
	    	stmt.close();
	    	c.commit();
	    	System.out.println("Records created successfully");
	    } 
	    catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	    disconnection();
	}
	
	public ArrayList<UserInformation> selectFromUsersTable(String sql)
	{
		connection();
	    Statement stmt = null;
	    ArrayList<UserInformation> users = new ArrayList<UserInformation>();
	    
	    try 
	    {
	    	c.setAutoCommit(false);
	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	
	    	while ( rs.next() ) 
	    	{
	    		UserInformation user = new UserInformation();
	    		user.fname = rs.getString("fname");
	    		user.mname = rs.getString("mname");
	    		user.lname = rs.getString("lname");
	    		user.gender = rs.getString("gender");
	    		user.email = rs.getString("email");
	    		user.pwd = rs.getString("pwd");
	    		user.month = rs.getInt("month");
	    		user.day = rs.getInt("day");
	    		user.year = rs.getInt("year");
	    		user.university = rs.getString("university");
	    		user.major = rs.getString("major");
	    		user.gpascale = Float.parseFloat(rs.getString("gpascale"));
	    		user.cgpa = Float.parseFloat(rs.getString("cgpa"));
	    		user.mgpa = Float.parseFloat(rs.getString("mgpa"));
	    		user.program = rs.getString("program");
	    		users.add(user);
	    	}
	    	rs.close();
	    	stmt.close();
	    	System.out.println("Select operation done successfully");
	    } 
	    catch ( Exception e ) 
	    {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	    disconnection();
	    return users;
	}
	
	public ArrayList<QATable> selectFromQATable(String sql)
	{
		connection();
	    Statement stmt = null;
	    ArrayList<QATable> qas = new ArrayList<QATable>();
	    
	    try 
	    {
	    	c.setAutoCommit(false);
	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	while ( rs.next() ) 
	    	{
	    		QATable qa = new QATable();
	    		qa.email = rs.getString("email");
	    		qa.question = rs.getString("question");
	    		qa.answer = rs.getString("answer");
	    		qas.add(qa);
	    	}
	    	rs.close();
	    	stmt.close();
	    	System.out.println("Select operation done successfully");
	    } 
	    catch ( Exception e ) 
	    {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	    disconnection();
	    return qas;
	}
	
	public ArrayList<WebLink> selectFromWebLinkTable(String sql)
	{
		connection();
	    Statement stmt = null;
	    ArrayList<WebLink> webLinks = new ArrayList<WebLink>();
	    
	    try 
	    {
	    	c.setAutoCommit(false);
	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	while ( rs.next() ) 
	    	{
	    		WebLink webLink = new WebLink();
	    		webLink.id = rs.getString("id");
	    		webLink.link = rs.getString("link");
	    		webLinks.add(webLink);
	    	}
	    	rs.close();
	    	stmt.close();
	    	System.out.println("Select operation done successfully");
	    } 
	    catch ( Exception e ) 
	    {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	    disconnection();
	    return webLinks;
	}
	
	public void update(String sql)
	{
		connection();
		Statement stmt = null;
		
		try 
		{
			c.setAutoCommit(false);
		    stmt = c.createStatement();
		    stmt.executeUpdate(sql);
		    c.commit();
		    stmt.close();
		    System.out.println("Update operation done successfully");
		} 
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		disconnection();
	}
	
	public void delete(String sql)
	{
		connection();
		Statement stmt = null;
		
		try 
		{
			c.setAutoCommit(false);
		    stmt = c.createStatement();
		    stmt.executeUpdate(sql);
		    c.commit();
		    stmt.close();
		    System.out.println("Delete operation done successfully");
		} 
		catch ( Exception e ) 
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		disconnection();
	}
}
