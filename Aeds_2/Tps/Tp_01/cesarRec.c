// dependencias
#include <stdio.h> 
#include <stdlib.h>
#include <stdbool.h> 
#define null NULL

int length ( char str [  ] );

//------------funcao auxl------------------

/*
   funcao para verirficar quando a palavra FIM for escrita
 */

bool theEnd ( char str [  ] )
{
	if ( str == NULL )
	{
		return false;
	}

	int size = length ( str );
	if ( size != 3 )
	{
		return false;
	}

	return ( str[0] == 'F' && str[1] == 'I' && str[2] == 'M' );
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
   funcao prevista para receber uma String e realizar um
   cast adiconando +3 para todos seus caracteres (em base a tabela
   ASCII), de forma recursiva
 */

void cesarRec ( char str [ ], char resp [ ], int index, int size )
{
	if ( index >= size )
	{
		resp[index] = '\0';
		return;
	}

	char oW = (char) ( str[index] + 3 );
	resp[index] = oW;

	cesarRec ( str, resp, index + 1, size );
}


int main ( void )
{
	char str  [ 1000 ];
	char resp [ 1000 ];

	while ( fgets(str, sizeof(str), stdin) != NULL )
	{
		int size = length(str);
		if ( size > 0 && str[size - 1] == '\n' )
		{
			str[size - 1] = '\0';
			size--;
		}
		if ( size > 0 && str[size - 1] == '\r' )
		{
			str[size - 1] = '\0';
			size--;
		}

		if ( theEnd(str) )
		{
			break;
		}

		cesarRec ( str, resp, 0, size );
		printf("%s\n", resp );
	}
	return 0;
}
