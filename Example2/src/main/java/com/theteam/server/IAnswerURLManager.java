package com.theteam.server;

public interface IAnswerURLManager {
	/**
	 * @param id the answer id
	 * @return
	 * 		The URL corresponds to the Id
	 */
	String GetURL(String id);
	
	/**
	 * Initialize anything necessary
	 */
	void Init();
}
