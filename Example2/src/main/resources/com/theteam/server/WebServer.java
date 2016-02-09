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
    	String type=request.getHeader("type");
    	if(type.equals("Q&A"))
    	{
    		ProcessQuestionAndAnswer(request,response);
    	}
    	
		
    	/*
    	String realPath = this.getServletContext().getRealPath("User.html");
    	InputStream in = new FileInputStream(realPath);
    	int len=0;
    	byte[] buffer = new byte[1024];
    	OutputStream out = response.getOutputStream();
    	while ((len = in.read(buffer)) > 0) {
    		out.write(buffer,0,len);
    	}
    	in.close();
		*/
		// Set response content typec
		//response.setContentType("text/html");
		
		// Actual logic goes here.
	}
    
    private void ProcessQuestionAndAnswer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String question=request.getHeader("question");
    	String answer=null;
    	
		if(question.equals("When is the deadline for CSE master program?"))
		{
			answer = "January 31st";
		}
		else if(question.equals("What is the address of CSE Department?"))
		{
			answer = "395 Dreese Laboratories 2015 Neil Avenue Columbus, OH 43210-1277";
		}
		else if(question.equals("Based on my profile, what master program fits me best?"))
		{
			answer = "The Computer Science & Engineering requires a GPA of 3.0. Each candidate is required to pursue a program of study in courses approved by his/her academic advisor. Students in the thesis track need to complete 20 graded cr-hrs. Students in the non-thesis track need to complete 30 graded cr-hrs, or 24 graded cr-hrs and a masters project.";
		}
		else
		{
			answer = "I don't know";
		}
		
		PrintWriter out=response.getWriter();
		out.println(answer);
    }
    
    private void sendBytes(FileInputStream fis, OutputStream out)
	{
    	try
    	{
    		byte[] buffer=new byte[1024];
    		int bytes=0;
		
    		while((bytes=fis.read(buffer))!=-1)
    		{
    			out.write(buffer,0,bytes);
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error");
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