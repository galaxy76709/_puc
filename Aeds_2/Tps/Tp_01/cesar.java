package Aeds_2.Tps.Tp_01;

//dependencias 
import java.util.Scanner;

public class cesar 
{
	/*
	   funcao prevista para receber uma String e realizar um
	   cast adiconando +3 para todos seus caracteres (em base a tabela
	   ASCII)
	 */
	public static String cesarCod (String str) 
	{
		int const_ = 3; 
		int size = str.length();
		char [] resp = new char [size] ;

		for (int i = 0; i < size; i++ )
		{
			char oW = (char)(str.charAt(i) + const_);
			resp[i] = oW; 

		}
		

		return new String (resp);	
	}



public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) 
        {
            String str = sc.nextLine();

            if   ( str.length() == 3 &&
                 str.charAt(0) == 'F' &&
                 str.charAt(1) == 'I' &&
                 str.charAt(2) == 'M' ) {
                break;
            }

            String result = cesarCod(str); 

            System.out.println(result);
        }

        sc.close();
    }


}
