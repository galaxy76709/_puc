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
   funcao destinada a verificar a maior substring de uma 
   string passada, so valerar a resposta assim que qualquer palavra
   seja repetida, abcabd >> sua subsString sera abc o valor sera 3
 */

int subString(char str[])
{
    int size = length(str);
    int seen[256];
    int left = 0;
    int best = 0;

    for (int i = 0; i < 256; i++)
    {
        seen[i] = -1;
    }

    for (int i = 0; i < size; i++)
    {
        int index = str[i] & 255;

        if (seen[index] != -1 && seen[index] >= left)
        {
            left = seen[index] + 1;
        }

        seen[index] = i;

        if (i - left + 1 > best)
        {
            best = i - left + 1;
        }
    }

    return best;
}
	


int main ( void )
{
	char str  [ 100 ];

	while ( scanf("%99s", str ) == 1 )
	{
		if ( theEnd(str) )
		{
			break;
		}
		printf("%d\n", subString(str));
	}
	return 0;
}
