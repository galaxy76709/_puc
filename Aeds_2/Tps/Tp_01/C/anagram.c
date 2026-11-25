// dependencias
#include <stdio.h> 
#include <stdlib.h>
#include <stdbool.h> 
#define null NULL


//------------funcao auxl------------------

int length ( char str [  ] );

/*
   funcao para verirficar quando a palavra FIM for escrita
 */

bool theEnd ( char str [  ] )
{

	if ( str == NULL )
	{
		return 0;
	}

	bool end = false;
	int size = length ( str );

	if ( size   >=  3   && 
			str[0] == 'F' &&
			str[1] == 'I' &&
			str[2] == 'M')
	{
		end = true;
	}

	return end;
}


/*
   funcao destinada a ler e
   retornar o valor total do tamanho de um arranjo
 */
int length ( char str [  ]  )
{
	if ( str == NULL  )	
	{
		return 0;
	} else 	{


		int i = 0;
		int size = 0;
		while ( str[i] != '\0'  )
		{
			size = size + 1; 
			i = i + 1;

		}
		return size; 
	}
}	



//------------funcao principal------------------

/*
   funcao destinada a verificar se a as duas string passadas 
   sao um anagrama (seu inverso e o mesmo lido de frente )
 */

bool anagram(char str[], char rts[])
{
    int freq[26] = {0};

    for (int i = 0; str[i] != '\0'; i++)
    {
        char c = str[i];

        if (c >= 'A' && c <= 'Z')
        {
            c = c + ('a' - 'A');
        }

        if (c >= 'a' && c <= 'z')
        {
            freq[c - 'a']++;
        }
    }

    for (int i = 0; rts[i] != '\0'; i++)
    {
        char c = rts[i];

        if (c >= 'A' && c <= 'Z')
        {
            c = c + ('a' - 'A');
        }

        if (c >= 'a' && c <= 'z')
        {
            freq[c - 'a']--;
        }
    }

    for (int i = 0; i < 26; i++)
    {
        if (freq[i] != 0)
        {
            return false;
        }
    }

    return true;
}
	


int main ( void )
{
	char str  [ 100 ];
	char rts [ 100 ];

	//printf ( ">> " );

	while ( scanf("%99s", str ) == 1 &&   scanf("%99s", rts ) == 1 )
	{
		if ( theEnd(str) ||  theEnd(rts) )
		{
			break;
		}

		bool isAnagram = anagram ( str, rts );

		if ( isAnagram )
		{
			printf("SIM\n");
		} else {
			printf("NAO\n");
		}

	}
	return 0;
}
