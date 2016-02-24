package com.theteam.server;

import java.sql.*;
import java.util.*;

public class Database {
	private Connection c = null;
	
	private void connection()
	{
	    try 
	    {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:QA.db");
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
	
	public void createTable(String sql)
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
	    
	    try 
	    {
	    	c.setAutoCommit(false);
	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	ArrayList<UserInformation> users = new ArrayList<UserInformation>();
	    	while ( rs.next() ) 
	    	{
	    		UserInformation user = new UserInformation();
	    		user.fname = rs.getString("fname");
	    		user.mname = rs.getString("mname");
	    		user.lname = rs.getString("lname");
	    		user.gender = rs.getString("gender");
	    		user.email = rs.getString("email");
	    		user.pwd = rs.getString("pwd");
	    		user.confirmpwd = rs.getString("confirmpwd");
	    		user.month = rs.getInt("month");
	    		user.day = rs.getInt("day");
	    		user.year = rs.getInt("year");
	    		user.university = rs.getString("university");
	    		user.major = rs.getString("major");
	    		user.gpascale = rs.getString("gapscale");
	    		user.cgpa = rs.getString("cgpa");
	    		user.mgpa = rs.getString("mgpa");
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
	}
	
	public ArrayList<QA> selectFromQATable(String sql)
	{
		connection();
	    Statement stmt = null;
	    
	    try 
	    {
	    	c.setAutoCommit(false);
	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	ArrayList<QA> qas = new ArrayList<QA>();
	    	while ( rs.next() ) 
	    	{
	    		QA qa = new QA();
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
	}
	
	public ArrayList<WebLink> selectFromWebLinkTable(String sql)
	{
		connection();
	    Statement stmt = null;
	    
	    try 
	    {
	    	c.setAutoCommit(false);
	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	ArrayList<WebLink> webLinks = new ArrayList<WebLink>();
	    	while ( rs.next() ) 
	    	{
	    		WebLink webLink = new WebLink();
	    		webLink.email = rs.getString("email");
	    		webLink.question = rs.getString("question");
	    		webLink.answer = rs.getString("answer");
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
	}
	
	public void update(String sql)
	{
		connection();
		Statement stmt = null;
		
		try 
		{
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
