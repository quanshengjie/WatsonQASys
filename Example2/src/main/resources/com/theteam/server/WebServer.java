package com.theteam.server;

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
	private String message;
       
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
	    message = "This is the message !";
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Set response content typec
		response.setContentType("text/html");
		
		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println(message);
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
