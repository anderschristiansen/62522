package Helpers;

public class Filter {

	public static String StringFilter(String s) 
	{
		if (s.contains("'"))
		{
			s = s.replace("'", "");
		}
		if (s.contains("�"))
		{
			s = s.replace("�", "");
		}
		if (s.contains("�"))
		{
			s = s.replace("�", "C");
		}
		if (s.contains("�"))
		{
			s = s.replace("�", "�");
		}
		if (s.contains("�"))
		{
			s = s.replace("�", "�");
		}
		if (s.contains("�"))
		{
			s = s.replace("�", "Y");
		}
		return s;
	}
}
