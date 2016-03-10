package com.theteam.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class WatsonMonitor
 */
public final class WatsonMonitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IWatsonMonitor watsonMoniter = new WatsonMonitor1();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WatsonMonitor() {
        super();
        
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Watson Monitor Init");
	    watsonMoniter.Init();
	}

}
