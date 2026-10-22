package Aeds_2.Tps.Tp_01;

//dependencias 
import java.util.Scanner;

public class sumDigitRec
{
    /*
       funcao para verirficar quando a palavra FIM for escrita
     */
    public static boolean theEnd ( String str )
    {
        if ( str == null )
        {
            return false;
        }

        int size = str.length();

        if ( size >= 3 &&
             str.charAt(0) == 'F' &&
             str.charAt(1) == 'I' &&
             str.charAt(2) == 'M' )
        {
            return true;
        }

        return false;
    }

    /*
       funcao recursiva para somar os digitos de um numero
     */
    public static int sumDigitsRec ( int value )
    {
        if ( value == 0 )
        {
            return 0;
        }

        return ( value % 10 ) + sumDigitsRec(value / 10);
    }

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int size = 0;
        int[] vect = new int[100];

        while ( size < 100 && sc.hasNextLine() )
        {
            String str = sc.nextLine();

            if ( theEnd(str) )
            {
                break;
            }

            vect[size] = Integer.parseInt(str);
            size = size + 1;
        }

        for ( int i = 0; i < size; i++ )
        {
            if ( i < size - 1 )
            {
                System.out.println(sumDigitsRec(vect[i]));
            }
            else
            {
                System.out.print(sumDigitsRec(vect[i]));
            }
        }

        sc.close();
    }
}
