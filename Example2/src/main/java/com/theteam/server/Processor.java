package com.theteam.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import java.security.*;

public class Processor {
	private static IQuestionAnswerSystem qas;
	private static Database database;
	
	public Processor(IQuestionAnswerSystem qas, Database database)
	{
		this.qas = qas;
		this.database = database;
	}
	
	public static void ProcessAsk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String question=request.getHeader("question");
    	String answer="";
		answer = qas.GetAnswer(question);
		
		String hasUser = request.getHeader("hasUser");
		if(hasUser.equals("true"))
		{
			String email = request.getHeader("email");
			String sql;
			String newQuestion;
			String newAnswer;
			
			/*
			String[] temp = question.split("'");
			String newQuestion = temp[0];
			for(int i = 1; i < temp.length; i++)
			{
				newQuestion = newQuestion + "SINGLEQUOTE" + temp[i];
			}
			*/
			
			/*
			temp = answer.split("'");
			String newAnswer = temp[0];
			for(int i = 1; i < temp.length; i++)
			{
				newAnswer = newAnswer + "SINGLEQUOTE" + temp[i];
			}
			*/
			
			newQuestion = StringEscapeUtils.escapeSql(question);
			newAnswer = StringEscapeUtils.escapeSql(answer);
			
			sql = "INSERT INTO QATable (email, question, answer) " + 
					"VALUES ('" + email + "', '" + newQuestion + "', '" + newAnswer + "');";
			database.insert(sql);
		}
		
		PrintWriter out=response.getWriter();
		out.println(answer);
    }
	
	public static void ProcessSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		
		
    	UserInformation user = new UserInformation();
    	user.fname = StringEscapeUtils.escapeSql(request.getHeader("fname"));
    	user.mname = StringEscapeUtils.escapeSql(request.getHeader("mname"));
    	user.lname = StringEscapeUtils.escapeSql(request.getHeader("lname"));
    	user.gender = StringEscapeUtils.escapeSql(request.getHeader("gender"));
    	user.email = StringEscapeUtils.escapeSql(request.getHeader("email"));
    	user.pwd = StringEscapeUtils.escapeSql(encrypt(request.getHeader("pwd")));
    	user.month = Integer.parseInt(request.getHeader("month"));
    	user.day = Integer.parseInt(request.getHeader("day"));
    	user.year = Integer.parseInt(request.getHeader("year"));
    	user.university = StringEscapeUtils.escapeSql(request.getHeader("university"));
    	user.major = StringEscapeUtils.escapeSql(request.getHeader("major"));
    	user.gpascale = Float.parseFloat(request.getHeader("gpascale"));
    	user.cgpa = Float.parseFloat(request.getHeader("cgpa"));
    	user.mgpa = Float.parseFloat(request.getHeader("mgpa"));
    	user.program=StringEscapeUtils.escapeSql(request.getHeader("program"));
    	
    	boolean isExist=false;
    	/*
    	Iterator<UserInformation> iterator=users.iterator();
		while(iterator.hasNext())
		{
			UserInformation element=iterator.next();
			if(element.email.equals(user.email))
			{
				isExist=true;
			}
		}
		*/
    	String sql = "SELECT * " + "FROM USERS " + "WHERE email = '" + user.email + "';";
    	ArrayList<UserInformation> users = database.selectFromUsersTable(sql);
    	if(!users.isEmpty())
    	{
    		isExist = true;
    	}
    	
		PrintWriter out=response.getWriter();
		if(isExist)
		{
			out.print("false");
		}
		else
		{
			sql = "INSERT INTO USERS (fname, mname, lname, gender,"
					+ " email, pwd, month, day, year, university,"
					+ " major, gpascale, cgpa, mgpa, program) "
					+ " VALUES ('" + user.fname + "', '" + user.mname + "', '"
					+ user.lname + "', '" + user.gender + "', '" + user.email
					+ "', '" + user.pwd + "', "
					+ user.month + ", " + user.day + ", " + user.year + ", '"
					+ user.university + "', '" + user.major + "', " + user.gpascale
					+ ", " + user.cgpa + ", " + user.mgpa + ", '" + user.program + "');";
			database.insert(sql);
			out.print("true");
		}
    }
	
	public static void ProcessLogIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=StringEscapeUtils.escapeSql(request.getHeader("email"));
    	String pwd=StringEscapeUtils.escapeSql(encrypt(request.getHeader("pwd")));
    	
    	boolean isVerified=false;
    	/*
    	Iterator<UserInformation> iterator=users.iterator();
		while(iterator.hasNext())
		{
			UserInformation element=iterator.next();
			if(element.email.equals(email) && element.pwd.equals(pwd))
			{
				isVerified=true;
			}
		}
		*/
    	String sql = "SELECT * " + "FROM USERS " + "WHERE email = '" + email + "' AND pwd = '" + pwd + "';";
    	ArrayList<UserInformation> users = database.selectFromUsersTable(sql);
    	if(!users.isEmpty())
    	{
    		isVerified = true;
    	}
		
		PrintWriter out=response.getWriter();
		if(isVerified)
		{
			out.print("true");
		}
		else
		{
			out.print("false");
		}
    }
	
	public static void ProcessProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=StringEscapeUtils.escapeSql(request.getHeader("email"));
    	PrintWriter out=response.getWriter();
    
    	String sql = "SELECT * " + "FROM USERS " + "WHERE email = '" + email + "';";
    	ArrayList<UserInformation> users = database.selectFromUsersTable(sql);
    	
    	UserInformation user = users.get(0);
    	out.print("fnameCOLON"+user.fname + "COMMA");
    	out.print("mnameCOLON"+user.mname + "COMMA");
    	out.print("lnameCOLON"+user.lname + "COMMA");
    	//out.print("genderCOLON"+user.gender + "COMMA");
    	//out.print("emailCOLON"+user.email + "COMMA");
    	out.print("monthCOLON"+user.month + "COMMA");
    	out.print("dayCOLON"+user.day + "COMMA");
    	out.print("yearCOLON"+user.year + "COMMA");
    	out.print("universityCOLON"+user.university + "COMMA");
    	out.print("majorCOLON"+user.major + "COMMA");
    	out.print("gpascaleCOLON"+user.gpascale + "COMMA");
    	out.print("cgpaCOLON"+user.cgpa + "COMMA");
    	out.print("mgpaCOLON"+user.mgpa + "COMMA");
    	out.print("programCOLON"+user.program);
    }
    
    public static void ProcessHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=StringEscapeUtils.escapeSql(request.getHeader("email"));
    	PrintWriter out=response.getWriter();
    
    	String sql = "SELECT * " + "FROM QATable " + "WHERE email = '" + email + "';";
    	ArrayList<QATable> qATable = database.selectFromQATable(sql);
    	
    	for(int i = 0; i < qATable.size(); i++)
    	{
    		String question = qATable.get(i).question;
    		String[] temp = question.split("SINGLEQUOTE");
    		question = temp[0];
    		for(int j = 1; j < temp.length; j++)
    		{
    			question = question + "'" + temp[j];
    		}
    		String answer = qATable.get(i).answer;
    		temp = answer.split("SINGLEQUOTE");
    		answer = temp[0];
    		for(int j = 1; j < temp.length; j++)
    		{
    			answer = answer + "'" + temp[j];
    		}
    		out.print(question + "COLON" + answer);
    		if(i < qATable.size() - 1)
    		{
    			out.print("COMMA");
    		}
    	}
    }
    
    public static void ProcessChangePWD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=StringEscapeUtils.escapeSql(request.getHeader("email"));
    	String newPWD=StringEscapeUtils.escapeSql(encrypt(request.getHeader("pwd")));
    	PrintWriter out=response.getWriter();
    
    	String sql = "UPDATE USERS SET pwd = '" + newPWD + "' WHERE email='" + email + "';";
    	database.update(sql);
    }
    
    private static String encrypt(String password)
    {
    	String generatedPassword = null;
    	try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
    	
    	return generatedPassword;
    }
}
