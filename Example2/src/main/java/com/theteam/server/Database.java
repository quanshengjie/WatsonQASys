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
		ArrayList<UserInformation> users = new ArrayList<UserInformation>();
		connection();
	    Statement stmt = null;
	    
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
	    		user.confirmpwd = rs.getString("confirmpwd");
	    		user.month = rs.getInt("month");
	    		user.day = rs.getInt("day");
	    		user.year = rs.getInt("year");
	    		user.university = rs.getString("university");
	    		user.major = rs.getString("major");
	    		user.gpascale = Float.parseFloat(rs.getString("gapscale"));
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
		ArrayList<QATable> qas = new ArrayList<QATable>();
		connection();
	    Statement stmt = null;
	    
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
		ArrayList<WebLink> webLinks = new ArrayList<WebLink>();
		connection();
	    Statement stmt = null;
	    
	    try 
	    {
	    	c.setAutoCommit(false);
	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery(sql);
	    	
	    	while ( rs.next() ) 
	    	{
	    		WebLink webLink = new WebLink();
	    		webLink.id = rs.getInt("id");
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
