#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define NULO NULL

// --------------------------------------------------------------------

typedef struct HashComReserva
{
    char **table;    // arranjo de String (cada String e um char *)
    int mainSize;   // total do arranjo 
    int reserveSize; // tamanho da reserva
    int reserveQuantity; 

    
} HashComReserva; 
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    HashComReserva * newHash ( int mainSize, int reserveSize)
    {
        HashComReserva *h = (HashComReserva *) malloc (sizeof ( HashComReserva ));
        
        h -> mainSize    = mainSize; 
        h -> reserveSize = reserveSize;
        h -> reserveQuantity = 0;

        h -> table = (char **) malloc (( mainSize + reserveSize ) * sizeof ( char * )); 
        
        for ( int i = 0; i < mainSize + reserveSize; i ++)
        { h -> table[ i ] =  NULO ; }

        return h;
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    int hash ( HashComReserva* h, const char* key )
    { 
        int sum = 0;
        int tam = strlen ( key ); 

        for ( int i = 0; i < tam ; i++ )
        { sum += ( unsigned char ) key [ i ];  }

        return sum % h -> mainSize; 

    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------

bool search ( HashComReserva *h, const char *key )
{
    int idx = hash ( h, key );
    char *current = h->table[ idx ];

    if ( current != NULL && strcmp ( current, key ) == 0 )
        { return true; }

    for ( int i = 0; i < h->reserveQuantity; i++ )
    {
        int reserveIndex = h->mainSize + i;
        if (h->table[reserveIndex] != NULL &&
            strcmp(h->table[reserveIndex], key) == 0)
            return true;
    }

    return false;
}

// --------------------------------------------------------------------

bool openPositive(HashComReserva *h, int pos)
{ return h->table[pos] == NULO;}
// --------------------------------------------------------------------


// --------------------------------------------------------------------
    bool itExists ( HashComReserva *h, const char * key)
    {
        int pos = hash ( h, key ); 

        if ( !openPositive( h ,pos ) && strcmp(h -> table [ pos ], key ) == 0 )
                {  return true; }

        for ( int i = 0; i < h ->reserveQuantity; i++ )
        { if (!openPositive ( h, h -> mainSize + i ) 
            && strcmp (h -> table [ h-> mainSize + i ], key) == 0)
                    {return true; }
        } 
        return false;
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
static char *copyString(const char *key)
{
    char *copy = (char *)malloc(strlen(key) + 1);
    if (copy != NULL)
        strcpy(copy, key);
    return copy;
}
// --------------------------------------------------------------------

// --------------------------------------------------------------------
void insert(HashComReserva *h, const char *key)
{
    if (itExists(h, key))
        {
        printf("O valor e duplicado, Erro\n");
        return;
    }

    int pos = hash(h, key);

    if (openPositive(h, pos))
    {
        h->table[pos] = copyString(key);
    }
    else if (h->reserveQuantity < h->reserveSize)
    {
        h->table[h->mainSize + h->reserveQuantity] = copyString(key);
        h->reserveQuantity++;
    }
    else
    {
        printf("O valor da reserva esta cheio, Erro\n");
    }
}
// --------------------------------------------------------------------

    void print(HashComReserva *h)
{
    for (int i = 0; i < h->mainSize + h->reserveSize; i++)
    {
        const char *area = (i < h->mainSize) ? "Principal" : "Reserva";
        const char *valor = (h->table[i] == NULO) ? "-" : h->table[i];

        printf("%d %s %s\n", i, area, valor);
    }
}
// --------------------------------------------------------------------

    bool removeKey ( HashComReserva *h, const char *key )
    {
        int pos = hash ( h , key ); 
        char* current = h -> table [ pos ]; 

        if ( current != NULO && strcmp( h -> table [ pos ], key ) == 0 )
            { h -> table [ pos ] = NULO; return true; }
    
        for ( int i = 0; i < h -> reserveQuantity; i++ )
            { 
                if ( strcmp(h -> table[ h -> mainSize + i ], key ) == 0 )
                    { 
                        for ( int j = i; j < h -> reserveQuantity - 1; j++  )
                        {  h -> table [ h -> mainSize + j ] = h -> table [ h -> mainSize + j + 1]; }
                         h -> table [ h -> mainSize + h -> reserveQuantity - 1] =  NULO; 
                         h -> reserveQuantity --; return true; 
                    }
            }
            return false; 
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
static char *trim ( char *s )
{
    while ( *s == ' ' || *s == '\t' || *s == '\n' || *s == '\r' )
        { s++; }

    int tam = strlen ( s );

    while ( tam > 0 && ( s [ tam - 1 ] == ' ' || s [ tam - 1 ] == '\t'
                      || s [ tam - 1 ] == '\n' || s [ tam - 1 ] == '\r' ) )
        { s [ tam - 1 ] = '\0'; tam--; }

    return s;
}
// --------------------------------------------------------------------

// --------------------------------------------------------------------
int main ( void )
{
    int M = 11; int R = 3;
    char linha [ 256 ];
    char pais [ 256 ];
    bool temLinha = false;

    if ( fgets ( linha, sizeof ( linha ), stdin ) != NULL )
    {
        if ( sscanf ( linha, "%d %d", &M, &R ) != 2 )
            { M = 11; R = 3; temLinha = true; }
    }

    HashComReserva *h = newHash ( M, R );

    while ( temLinha || fgets ( linha, sizeof ( linha ), stdin ) != NULL )
    {
        temLinha = false;

        char *l = trim ( linha );
        if ( l [ 0 ] == '\0' ) { continue; }
        char escolha = l [ 0 ];

        if ( escolha == 'M' )
            { print ( h ); }

        else
            {
                strcpy ( pais, trim ( l + 1 ) );

                if ( pais [ 0 ] == '\0' && fgets ( linha, sizeof ( linha ), stdin ) != NULL )
                    { strcpy ( pais, trim ( linha ) ); }

                if ( escolha == 'I' )
                    { insert ( h, pais ); }

                else if ( escolha == 'P' )
                    { printf ( "%s\n", search ( h, pais ) ? "sim" : "nao" ); }

                else if ( escolha == 'R' )
                    { if ( !removeKey ( h, pais ) )
                        { printf ( "Erro\n" ); }
                    }
            }
    }

    return 0;
}
// --------------------------------------------------------------------


