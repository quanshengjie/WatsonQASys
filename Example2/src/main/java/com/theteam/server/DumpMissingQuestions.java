package com.theteam.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DumpMissingQuestions
 */
public final class DumpMissingQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IDumpMissingQuestions dumper = new DumpMissingQuestionsFromMongoDB();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DumpMissingQuestions() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<MissingQuestionItem> listOfItem = dumper.Dump();
		for(MissingQuestionItem item : listOfItem)
		{
			response.getWriter().println("Question: " + item.getQuestion());
			response.getWriter().println("Confidence: " + item.getConfidence());
			response.getWriter().println();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
