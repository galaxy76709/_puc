package Aeds_2.Tps.Tp_01;

//dependencias 
import java.util.Scanner;

class Is { 

	/*Funcao destinada para saber quando a palvra FIM for escrita*/
	public static boolean theEnd (String End)
	{	
		int n = End.length();
		boolean isTheEnd = false; 

		if (      n >= 3 &&
				End.charAt(0) == 'F' &&
				End.charAt(1) == 'I' && 
				End.charAt(2) == 'M'  )
		{ isTheEnd = true; }

		return isTheEnd;
	}


	public static boolean Is_vogal ( String arg )
	{	
		if (arg == null || arg.length() == 0)
		{
			return false;
		}

		for (int i = 0; i < arg.length(); i++) {
			char c = Character.toLowerCase(arg.charAt(i));
			if (c >= 'a' && c <= 'z') {
				if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u')
				{
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}


	public static boolean Is_constant ( String arg )
	{	
		if (arg == null || arg.length() == 0)
		{
			return false;
		}

		for (int i = 0; i < arg.length(); i++) {
			char c = Character.toLowerCase(arg.charAt(i));
			if (c >= 'a' && c <= 'z') {
				if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
				{
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}


	public static boolean Is_number ( String arg  ) 
	{
		if (arg == null || arg.length() == 0)
		{ return false; }

		int i = 0;
		if (arg.charAt(0) == '+' || arg.charAt(0) == '-')
		{
			i++;
		}
		if (i >= arg.length())
		{
			return false;
		}

		for (; i < arg.length(); i++) {
			if (arg.charAt(i) < '0' || arg.charAt(i) > '9') {
				return false;
			}
		}

		return true;
	}

	
	public static boolean Is_natural ( String arg  ) 
	{
		if (arg == null || arg.length() == 0)
		{ return false; }

		int i = 0;
		if (arg.charAt(0) == '+' || arg.charAt(0) == '-')
		{
			i++;
		}

		boolean hasDigit = false;
		boolean hasSeparator = false;

		for (; i < arg.length(); i++) {
			char c = arg.charAt(i);
			if (c == '.' || c == ',') {
				if (hasSeparator) return false;
				hasSeparator = true;
			} else if (c >= '0' && c <= '9') {
				hasDigit = true;
			} else {
				return false;
			}
		}

		return hasDigit;
	}



	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNextLine())
		{
			String str = sc.nextLine();
			
		            if ( str.length() == 3 &&
                 str.charAt(0) == 'F' &&
                 str.charAt(1) == 'I' &&
                 str.charAt(2) == 'M' )
			{
				break; 
			}

			boolean X1 = Is_vogal(str);
			boolean X2 = Is_constant(str);
			boolean X3 = Is_number(str);
			boolean X4 = Is_natural(str);
			
			System.out.println((X1 ? "SIM" : "NAO") + " " +
						   (X2 ? "SIM" : "NAO") + " " +
						   (X3 ? "SIM" : "NAO") + " " +
						   (X4 ? "SIM" : "NAO"));

		}		

		sc.close();
	}
}

