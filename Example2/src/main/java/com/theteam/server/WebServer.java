package com.theteam.server;
import java.io.* ;
import java.net.* ;
import java.util.* ;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Bob
 */
public class WebServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IQuestionAnswerSystem qas = new Watson();
	//private ArrayList<UserInformation> users=new ArrayList<UserInformation>();
	private Database database = new Database();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebServer() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException
	{
	    // Do required initialization
	    qas.Init();
	    
	    //*************************************
	    /* Test User
	    UserInformation user=new UserInformation();
    	user.email="1";
    	user.pwd="1";
    	users.add(user);
    	*/
	    //*************************************
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String type=request.getHeader("type");
    	if(type != null && type.equals("ask"))
    	{
    		ProcessAsk(request,response);
    	}
    	else if(type != null && type.equals("signup"))
    	{
    		ProcessSignUp(request,response);
    	}
    	else if(type != null && type.equals("login"))
    	{
    		ProcessLogIn(request, response);
    	}
    	else if(type != null && type.equals("profile"))
    	{
    		ProcessProfile(request,response);
    	}
	}
    
    private void ProcessAsk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String question=request.getHeader("question");
    	String answer="";
		answer = qas.GetAnswer(question);
		
		String hasUser = request.getHeader("hasUser");
		if(hasUser.equals("true"))
		{
			String email = request.getHeader("email");
			String sql;
			sql = "INSERT INTO QATable (email, question, answer) " + 
					"VALUES ('" + email + "', '" + question + "', '" + answer + "');";
			database.insert(sql);
		}
		
		PrintWriter out=response.getWriter();
		out.println(answer);
    }
    
    private void ProcessSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	UserInformation user = new UserInformation();
    	user.fname = request.getHeader("fname");
    	user.mname = request.getHeader("mname");
    	user.lname = request.getHeader("lname");
    	user.gender = request.getHeader("gender");
    	user.email = request.getHeader("email");
    	user.pwd = request.getHeader("pwd");
    	user.confirmpwd = request.getHeader("comfirmpwd");
    	user.month = Integer.parseInt(request.getHeader("month"));
    	user.day = Integer.parseInt(request.getHeader("day"));
    	user.year = Integer.parseInt(request.getHeader("year"));
    	user.university = request.getHeader("university");
    	user.major = request.getHeader("major");
    	user.gpascale = Float.parseFloat(request.getHeader("gpascale"));
    	user.cgpa = Float.parseFloat(request.getHeader("cgpa"));
    	user.mgpa = Float.parseFloat(request.getHeader("mgpa"));
    	user.program=request.getHeader("program");
    	
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
					+ " email, pwd, confirmpwd, month, day, year, university,"
					+ " major, gpascale, cgpa, mgpa, program) "
					+ " VALUES ('" + user.fname + "', '" + user.mname + "', '"
					+ user.lname + "', '" + user.gender + "', '" + user.email
					+ "', '" + user.pwd +"', '" + user.confirmpwd + "', "
					+ user.month + ", " + user.day + ", " + user.year + ", '"
					+ user.university + "', '" + user.major + "', " + user.gpascale
					+ ", " + user.cgpa + ", " + user.mgpa + ", '" + user.program + "');";
			database.insert(sql);
			out.print("true");
		}
    }
    
    private void ProcessLogIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=request.getHeader("email");
    	String pwd=request.getHeader("pwd");
    	
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
    
    private void ProcessProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=request.getHeader("email");
    	PrintWriter out=response.getWriter();
    
    	String sql = "SELECT * " + "FROM USERS " + "WHERE email = '" + email + "';";
    	ArrayList<UserInformation> users = database.selectFromUsersTable(sql);
    	sql = "SELECT * " + "FROM QATable " + "WHERE email = '" + email + "';";
    	ArrayList<QATable> qATable = database.selectFromQATable(sql);
    	
    	UserInformation user = users.get(0);
    	out.print("fnameCOLON"+user.fname + "COMMA");
    	out.print("mnameCOLON"+user.mname + "COMMA");
    	out.print("lnameCOLON"+user.lname + "COMMA");
    	out.print("genderCOLON"+user.gender + "COMMA");
    	out.print("emailCOLON"+user.email + "COMMA");
    	out.print("monthCOLON"+user.month + "COMMA");
    	out.print("dayCOLON"+user.day + "COMMA");
    	out.print("yearCOLON"+user.year + "COMMA");
    	out.print("universityCOLON"+user.university + "COMMA");
    	out.print("majorCOLON"+user.major + "COMMA");
    	out.print("gpascaleCOLON"+user.gpascale + "COMMA");
    	out.print("cgpaCOLON"+user.cgpa + "COMMA");
    	out.print("mgpaCOLON"+user.mgpa + "COMMA");
    	out.print("programCOLON"+user.program + "END");

    	for(int i = 0; i < qATable.size(); i++)
    	{
    		String question = qATable.get(i).question;
    		String answer = qATable.get(i).answer;
    		out.print(question + "COLON" + answer);
    		if(i < qATable.size() - 1)
    		{
    			out.print("COMMA");
    		}
    	}
    }
    
    private void sendBytes(FileInputStream fis, OutputStream out)
	{
//    	try
//    	{
//    		byte[] buffer=new byte[1024];
//    		int bytes=0;
//		
//    		while((bytes=fis.read(buffer))!=-1)
//    		{
//    			out.write(buffer,0,bytes);
//    		}
//    	}
//    	catch(Exception e)
//    	{
//    		System.out.println("Error");
//    	}
	}
	
	public void destroy()
	{
		// do nothing.
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		doGet(request, response);
	}

}