package com.theteam.server;

import java.util.ArrayList;

public class AnswerURLManager implements IAnswerURLManager{
	Database database;
	
	/**
	 * @param id the answer id
	 * @return
	 * 		The URL corresponds to the Id
	 */
	public String GetURL(String id)
	{
		String sql = "SELECT * " + "FROM WebLink " + "WHERE id = '" + id + "';";
		ArrayList<WebLink> webLinks = database.selectFromWebLinkTable(sql);
		String url = null;
		if(!webLinks.isEmpty())
		{
			url = webLinks.get(0).link;
		}
		return url;
	}
	
	/**
	 * Initialize anything necessary
	 */
	public void Init()
	{
		database = new Database();
	}
}
