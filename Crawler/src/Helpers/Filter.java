package Helpers;

public class Filter {

	public static String StringFilter(String s) 
	{
		if (s.contains("'"))
		{
			s = s.replace("'", "");
		}
		if (s.contains("’"))
		{
			s = s.replace("’", "");
		}
		if (s.contains("Ç"))
		{
			s = s.replace("Ç", "C");
		}
		if (s.contains("Ö"))
		{
			s = s.replace("Ö", "Ø");
		}
		if (s.contains("Ä"))
		{
			s = s.replace("Ä", "Æ");
		}
		if (s.contains("Ü"))
		{
			s = s.replace("Ü", "Y");
		}
		return s;
	}
}
