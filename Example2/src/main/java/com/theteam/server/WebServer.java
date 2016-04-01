package com.theteam.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Bob
 */
public class WebServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IQuestionAnswerSystem qas = SmartBob.GetInstance();
	private Database database = new Database();
	private Processor processor = new Processor(qas, database);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebServer() {
        super();
    }
    
    public void init() throws ServletException
	{
	    // Do required initialization
	    qas.Init();
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