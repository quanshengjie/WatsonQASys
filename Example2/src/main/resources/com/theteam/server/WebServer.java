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
	private QuestionAnswerSystem qas = new Watson();
       
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
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String type=request.getHeader("type");
    	if(type != null && type.equals("Q&A"))
    	{
    		ProcessQuestionAndAnswer(request,response);
    	}
	}
    
    private void ProcessQuestionAndAnswer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String question=request.getHeader("question");
    	String answer="";
    	
		answer = qas.GetAnswer(question);
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