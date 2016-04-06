package com.theteam.server;

public class SQLUtil {
	public static String escapeString(String x) {
		String result = "";
		
		if(x != null)
		{
	        StringBuilder sBuilder = new StringBuilder(x.length() * 2);
	        int stringLength = x.length();
	
	        for (int i = 0; i < stringLength; ++i) {
	            char c = x.charAt(i);
	
	            switch (c) {
	            case 0: /* Must be escaped for 'mysql' */
	                sBuilder.append('\\');
	                sBuilder.append('0');
	
	                break;
	
	            case '\n': /* Must be escaped for logs */
	                sBuilder.append('\\');
	                sBuilder.append('n');
	
	                break;
	
	            case '\r':
	                sBuilder.append('\\');
	                sBuilder.append('r');
	
	                break;
	
	            case '\\':
	                sBuilder.append('\\');
	                sBuilder.append('\\');
	
	                break;
	
	            case '\'':
	                sBuilder.append('\'');
	                sBuilder.append('\'');
	
	                break;
	
	            case '"': /* Better safe than sorry */
	                sBuilder.append('\\');
	                sBuilder.append('"');
	
	                break;
	
	            case '\032': /* This gives problems on Win32 */
	                sBuilder.append('\\');
	                sBuilder.append('Z');
	
	                break;
	
	            default:
	                sBuilder.append(c);
	            }
	        }
	        
	        result = sBuilder.toString();
		}
	    return result;
    }
	
	public static String unescapeString(String x) {
		String result = "";
		
		if(x != null)
		{
	        StringBuilder sBuilder = new StringBuilder(x.length());
	
	        int stringLength = x.length();
	
	        for (int i = 0; i < stringLength; ++i) {
	            char c = x.charAt(i);
	            
	            if(c == '\\')
	            {
	            	char nextC = x.charAt(i + 1);
	            	if(!(nextC == 0 || nextC == '\n' || nextC == '\r'
	            			|| nextC == '\\' || nextC =='"'
	            			|| nextC =='\032'))
	            	{
	            		sBuilder.append(c);
	            	}
	            }
	            else if(c == '\'')
	            {
	            	char nextC = x.charAt(i + 1);
	            	if(nextC != '\'')
	            	{
	            		sBuilder.append(c);
	            	}
	            }
	            else
	            {
	            	sBuilder.append(c);
	            }
	        }
	
	        result = sBuilder.toString();
		}
		
		return result;
    }
}
