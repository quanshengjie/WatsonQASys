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
	private IQuestionAnswerSystem qas = new SmartBob();
	private Database database = new Database();
	private Processor processor = new Processor(qas, database);
       
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
    		//ProcessAsk(request,response);
    		processor.ProcessAsk(request, response);
    	}
    	else if(type != null && type.equals("signup"))
    	{
    		//ProcessSignUp(request,response);
    		processor.ProcessSignUp(request, response);
    	}
    	else if(type != null && type.equals("login"))
    	{
    		//ProcessLogIn(request, response);
    		processor.ProcessLogIn(request, response);
    	}
    	else if(type != null && type.equals("profile"))
    	{
    		//ProcessProfile(request,response);
    		processor.ProcessLogIn(request, response);
    	}
    	else if(type != null && type.equals("history"))
    	{
    		//ProcessHistory(request,response);
    		processor.ProcessHistory(request, response);
    	}
    	else if(type != null && type.equals("changepwd"))
    	{
    		//ProcessChangePWD(request,response);
    		processor.ProcessChangePWD(request, response);
    	}
	}
    
    /*
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
			String[] temp = question.split("'");
			String newQuestion = temp[0];
			for(int i = 1; i < temp.length; i++)
			{
				newQuestion = newQuestion + "SINGLEQUOTE" + temp[i];
			}
			temp = answer.split("'");
			String newAnswer = temp[0];
			for(int i = 1; i < temp.length; i++)
			{
				newAnswer = newAnswer + "SINGLEQUOTE" + temp[i];
			}
			sql = "INSERT INTO QATable (email, question, answer) " + 
					"VALUES ('" + email + "', '" + newQuestion + "', '" + newAnswer + "');";
			database.insert(sql);
		}
		
		PrintWriter out=response.getWriter();
		out.println(answer);
    }
    */
    
    /*
    private void ProcessSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	UserInformation user = new UserInformation();
    	user.fname = request.getHeader("fname");
    	user.mname = request.getHeader("mname");
    	user.lname = request.getHeader("lname");
    	user.gender = request.getHeader("gender");
    	user.email = request.getHeader("email");
    	user.pwd = request.getHeader("pwd");
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
    */
    
    /*
    private void ProcessLogIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=request.getHeader("email");
    	String pwd=request.getHeader("pwd");
    	
    	boolean isVerified=false;
    	
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
    */
    
    /*
    private void ProcessProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=request.getHeader("email");
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
    */
    
    /*
    private void ProcessHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=request.getHeader("email");
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
    */
    
    /*
    private void ProcessChangePWD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String email=request.getHeader("email");
    	String newPWD=request.getHeader("pwd");
    	PrintWriter out=response.getWriter();
    
    	String sql = "UPDATE USERS SET pwd = '" + newPWD + "' WHERE email='" + email + "';";
    	database.update(sql);
    }
	*/
    
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