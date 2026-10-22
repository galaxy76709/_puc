package Aeds_2.Tps.Tp_01;

//dependencias 
import java.util.Scanner;

public class senhaVerif
{
    /*
       funcao destinada a verificar se a senha passada e valida,
       sendo considerada valida apenas quando:

       - tamanho de pelo menos 8
       - pelo menos 1 letra maiuscula
       - pelo menos 1 letra minuscula
       - pelo menos 1 numero
       - pelo menos 1 caractere especial

       Sua saida deverar ser SIM ou NAO.
     */
    public static boolean senhaValida ( String str )
    {
        if ( str == null )
        {
            return false;
        }

        int size = str.length();

        if ( size < 8 )
        {
            return false;
        }

        boolean upperC = false;
        boolean lowerC = false;
        boolean numb = false;
        boolean esp = false;

        for ( int i = 0; i < size; i = i + 1 )
        {
            char c = str.charAt(i);

            if ( c >= 'A' && c <= 'Z' )
            {
                upperC = true;
            }
            else if ( c >= 'a' && c <= 'z' )
            {
                lowerC = true;
            }
            else if ( c >= '0' && c <= '9' )
            {
                numb = true;
            }
            else
            {
                esp = true;
            }
        }

        return upperC && lowerC && numb && esp;
    }

    public static void main(String[] args)
    {
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

            if ( senhaValida(str) )
            {
                System.out.println("SIM");
            }
            else
            {
                System.out.println("NAO");
            }
        }

        sc.close();
    }
}
