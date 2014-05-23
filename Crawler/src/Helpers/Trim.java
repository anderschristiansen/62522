package Helpers;

public class Trim {

	public static String trimEnd(String s)
	{
	    if ( s == null || s.length() == 0 )
	        return s;
	    int i = s.length();
	    while ( i > 0 &&  Character.isWhitespace(s.charAt(i - 1)) )
	        i--;
	    if ( i == s.length() )
	        return s;
	    else
	        return s.substring(0, i);
	}
}
