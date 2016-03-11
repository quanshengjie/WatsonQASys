package com.theteam.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Component;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Servlet implementation class WatsonMonitor
 */
@WebListener
@Component("WatsonMonitorBean")
public final class WatsonMonitor extends HttpServlet implements ServletContextListener {
	private static final long serialVersionUID = 1L;
	private IWatsonMonitor watsonMoniter;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WatsonMonitor() {
        super();
        
    }
    
    public void TestWatsonAvaliability()
    {
    	watsonMoniter.TestWatson();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("Watson Monitor Init");
		watsonMoniter = new WatsonMonitor1();
		ServletContext sc = getServletContext();
		sc.setAttribute("WatsonMoniter", watsonMoniter);
	    watsonMoniter.Init();
	}

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ContextDestory");
		IWatsonMonitor watsonMoniter = (IWatsonMonitor)sce.getServletContext().getAttribute("WatsonMoniter");
		watsonMoniter.Stop();
	}

}
