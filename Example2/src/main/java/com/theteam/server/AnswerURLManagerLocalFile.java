/**
 * 
 */
package com.theteam.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author shengjie
 *
 */
public class AnswerURLManagerLocalFile implements AnswerURLManager {

	private Map<String, String> idToUrlMap = new HashMap<String, String>();
	private final String urlListPath = "/urlList.txt";
	
	/* (non-Javadoc)
	 * @see com.theteam.server.AnswerURLManager#GetURL(java.lang.String)
	 */
	public String GetURL(String id)
	{
		String url = null;
		if(idToUrlMap.containsKey(id))
		{
			url = idToUrlMap.get(id);
		}
		return url;
	}

	/**
	 * @param line the line in urlList.txt in String form
	 * Split the line into id and url, store it into idToUrlMap
	 */
	private void SplitAndStoreUrlListLine(String line)
	{
		String[] pair = line.split(" : ");
		if(pair.length == 2)
		{
			idToUrlMap.put(pair[0].trim(), pair[1].trim());
		}
		else
		{
			System.err.println("Wrong line in urlList.txt\n" + line + "\n");
		}
	}
	
	/* 
	 * read in id url pair from urlList and store in the idToUrlMap
	 */
	public void Init()
	{
		File file = new File(urlListPath);
		if(!file.exists())
		{
			file = new File("src/main/resources/urlList.txt");
		}
		try {

	        Scanner sc = new Scanner(file);

	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
	            SplitAndStoreUrlListLine(line);
	        }
	        sc.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

}
