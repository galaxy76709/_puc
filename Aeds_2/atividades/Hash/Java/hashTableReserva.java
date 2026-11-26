package Aeds_2.atividades.Hash.Java;

public class hashTableReserva 
{

   public static  class Hash { 
    String table []; // arranjo de string
    int mainSize; // total do arranjo 
    int reserveSize; // tamanho da reserva
    int reserveQuantity; // quantidade disponivel na reserva
// variavel que nao pode ser mudada, vai ser ultilizada para setar os valores para nao ter lixo
    final String NULO = null;  

// --------------------------------------------------------------------
// construtor padrao 
    public  Hash (int mainSize, int reserveSize ){

        this.mainSize = mainSize;
        this.reserveSize = reserveSize;
        this.table = new String[mainSize + reserveSize ]; 

        for ( int i = 0; i < mainSize + reserveSize; i++ )
            {  table [ i ] = NULO; }

        reserveQuantity = 0; 
    }// fim do construtor
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    private int hash ( String key ){ 
        int sum = 0; 

        for ( int i = 0; i < key.length( ); i++ )
            { sum += key.charAt(i); } // assim eu faco a conversao de String > Char > int
              return sum % mainSize; // pega o modolo da soma com o o valor da posicao principal  
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    public boolean search ( String key ){
        boolean flag = false; 

        int idx = hash(key);
        String current = table [idx];
        
// verificao de posicao valida (existencia de valor ) e so o valor da chave e compativel com o da tabela
        if ( current != NULO && table [ idx ].equals(key) )
            { flag = true; }

        for ( int i = 0; i < reserveQuantity; i++ )
                { if (table[ mainSize + i ].equals(key) )
                    {flag = true; } 
                }
            return flag; 
    }
// --------------------------------------------------------------------

   private boolean openPosition ( int pos )
   { return table [ pos ] == NULO ? true : false; }

// --------------------------------------------------------------------

// --------------------------------------------------------------------

    private boolean itExists ( String key )
    {
        int pos = hash( key );
        
        if ( !openPosition( pos ) && table[ pos ].equals(key))
            { return true; }

        for ( int i = 0; i < reserveQuantity; i ++ ){
                if ( !openPosition( mainSize + i ) && table [ mainSize + i ].equals(key) )
                     {return true; }
        }
            return false; 
    }

// --------------------------------------------------------------------

// --------------------------------------------------------------------
    public void insert ( String key) throws Exception{
        if ( itExists(key) ) { throw new Exception("O valor e duplicado >> " + key) ; }

        int pos = hash(key);

// condicao de 
        if ( openPosition(pos) ) { table [ pos ] = key; }
        else if ( reserveQuantity < reserveSize ) 
                 {table [ mainSize + reserveQuantity ] = key; reserveQuantity = reserveQuantity + 1; }
        else { throw new Exception("O valor da reserva esta lotado"); }
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    public void print ( ) {
        for (int i = 0; i < mainSize + reserveQuantity; i ++)
        { 
            String area = i < mainSize ? "Principal" : "Reserva";
            String value = table [ i ] == NULO ? "-" : table[ i ]; 
            System.out.println(i + " " + area + " " + value );

        }
    }
// --------------------------------------------------------------------

// --------------------------------------------------------------------
    public boolean remove ( String key )
    { 
        int pos = hash( key );
        String current = table [ pos ]; 

        if ( current != NULO && table[ pos ].equals(key) )
        { table [ pos ] = NULO ; return true; }

        for ( int i = 0; i < reserveQuantity; i ++ )
        {  
            if ( table[mainSize + i ].equals(key) )
            {
                for ( int j = i; j < reserveQuantity - 1; j ++ )
                {
                    table [ mainSize + j ] = table [ mainSize + j + 1 ];
                }
                table[ mainSize + reserveQuantity - 1] = NULO; reserveQuantity-- ;return true; 
            }
        }

        return false;
    }
// --------------------------------------------------------------------



    
}
}