#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <stdbool.h> 
#define NULO NULL 


// --------------------------------------------------------------------
// construtor baseado em uma celula e em uma lista simplemente encadeada
typedef struct No
{
    int key; 
    char * value; 
   struct No *next; 
  
} No;
// --------------------------------------------------------------------

// --------------------------------------------------------------------
// construtor padrao do No
No *newNo ( int key, char *value)
{
    No * no = (No *)  malloc (sizeof(No)); 

    no -> key   = key; 
    no -> value = value; 
    no -> next  = NULO; 
    return no; 
}
// --------------------------------------------------------------------

// --------------------------------------------------------------------
// construtor da tabela
typedef struct Table
    {
        No ** table; 
        int size; 
    }Table;
    
// --------------------------------------------------------------------


// --------------------------------------------------------------------
Table *newTable ( int size ){ 

    Table * h = ( Table  * ) malloc ( sizeof ( Table )); 

    h -> size = size; 
// usuando o No ** estou criando um arranjo de nos e multiplicando com o valore de um apontador do tipo No 
    h -> table =  (No ** ) malloc ( size * sizeof ( No * ) ) ; 

    for ( int i = 0; i < size; i ++ ) 
    { h -> table [ i ] = NULO;  }

    return h; 

}
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    int hash ( Table *h, int key ) { return key % h -> size; }
// --------------------------------------------------------------------


// --------------------------------------------------------------------
    bool search ( Table *h, int key) { 
        int idx = hash ( h, key );
        
        No * current = h -> table [ idx ]; 

            while ( current != NULO  )
            {
                if ( current -> key == key  )
                    { return true; }
                    current = current -> next; 
            }
            return false; 
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    bool itExistis ( Table * h, const int  key ){ 
        int pos = hash( h , key ); 
        No * current = h -> table [ pos ]; 

            while (current != NULO )
            {
                if ( current -> key == key  )
                    {  return true; }
                current = current -> next; 
            }
            return false;
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    void insert ( Table * h, int key, char * value  ) {

         if ( ! itExistis ( h, key ) )
        { 
// 1 passo e encontrar o valor do index
        int idx  = hash ( h, key  ); 
// 2 passo e "instanciar" uma novo no 
        No * tmp = newNo ( key, value ); 
// 3 passo e adicionoar ele na tabela
        tmp -> next = h -> table [ idx ];
        h -> table [ idx ] = tmp; 
        } else { printf ("O valor ja existe\n" ) ;}
}
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    bool removeKey ( Table *h, int key )
    {
        int idx = hash ( h, key ); 
        No * current = h -> table [ idx ]; 
        No * prev = NULO; 

        while ( current != NULO )
        {
           if ( current -> key == key ){ 
                if ( prev == NULO ){  h -> table [ idx ] = current -> next;  }
                else { prev -> next = current -> next; } 
                free ( current );
                return true; 
           }
           prev = current; 
           current = current -> next; 
        }
            return false; 
        
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    void print ( Table * h ){
        
        for ( int i = 0; i < h -> size; i++ ){
            
            printf ( "[%d ]", i ); 
            No * current = h -> table [ i ]; 

            while ( current != NULO )
            {
                printf ( " -> (%d , %s)", current -> key , current -> value);  
                current = current -> next;
            }

            printf ( "-> NULO\n" );
        }
        
    }
// --------------------------------------------------------------------


// --------------------------------------------------------------------
    void freeHash ( Table * h )
    {
        for ( int i = 0; i < h ->size; i++ ){ 
            No * current = h -> table [ i ]; 
            
            while (current != NULO )
            { 
                No * next = current -> next; 
                free ( current );
                current = next; 
            }
            
        }

        free ( h -> table); free ( h ); 
    }
// --------------------------------------------------------------------


// --------------------------------------------------------------------

int main ( ) { 
    Table * h =  newTable( 11 );

    insert ( h, 10, "Joao" );
    insert ( h, 21, "Maria" );
    insert ( h, 15, "Ana" );
    insert ( h, 32, "Pedro" );
    insert ( h, 7,  "Carlos" );
    insert ( h, 15, "Outra" );

    printf ( "Tabela inicial:\n" );
    print ( h );

    printf ( "\nBusca 21: %s\n", search ( h, 21 ) ? "sim" : "nao" );
    printf ( "Busca 99: %s\n", search ( h, 99 ) ? "sim" : "nao" );

    printf ( "\nRemove 21 (meio da lista): %s\n", removeKey ( h, 21 ) ? "ok" : "erro" );
    printf ( "Remove 32 (inicio da lista): %s\n", removeKey ( h, 32 ) ? "ok" : "erro" );
    printf ( "Remove 99 (inexistente): %s\n", removeKey ( h, 99 ) ? "ok" : "erro" );

    printf ( "\nDepois das remocoes:\n" );
    print ( h );

    freeHash ( h );
    return 0;
}
// --------------------------------------------------------------------
