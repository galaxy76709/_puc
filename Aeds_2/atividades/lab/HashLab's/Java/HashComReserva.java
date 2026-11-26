import java.util.Scanner;
public class HashComReserva {

    public static class Hash {

        // a nossa tabela é um arranjo
        String table[];
        int tmPrincipal, tmReserva, nReserva;
        final String NULO = null;

        public Hash(int tmPrincipal, int tmReserva) {
            this.tmPrincipal = tmPrincipal;
            this.tmReserva = tmReserva;
            this.table = new String[tmPrincipal + tmReserva];

            for (int i = 0; i < tmPrincipal + tmReserva; i = i + 1) {
                table[i] = NULO;
            }

            nReserva = 0;
        }

        private int hash(String key) {
            int sum = 0;
            for (int i = 0; i < key.length(); i = i + 1) {
                sum += key.charAt(i);
            }
            return sum % tmPrincipal;
        }

        private boolean isPosicaoLivre(int pos) {
            return table[pos] == NULO;
        }

        private boolean existe(String key) {
            int pos = hash(key);

            // procura na área principal
            if (!isPosicaoLivre(pos) && table[pos].equals(key)) {
                return true;
            }

            // procura na reserva
            for (int i = 0; i < tmReserva; i = i + 1) {
                int posReserva = tmPrincipal + i;
                if (!isPosicaoLivre(posReserva) && table[posReserva].equals(key)) {
                    return true;
                }
            }
            return false;
        }

        public void inserir(String key) throws Exception {
            if (existe(key)) {
                throw new Exception("duplicado>> " + key);
            }

            int pos = hash(key);

            if (isPosicaoLivre(pos)) {
                table[pos] = key;
            } else if (nReserva < tmReserva) {
                table[tmPrincipal + nReserva] = key;
                nReserva = nReserva + 1;
            } else {
                throw new Exception("reserva cheia");
            }
        }

        public void print() {
            for (int i = 0; i < tmPrincipal + tmReserva; i = i + 1) {
                String area = i < tmPrincipal ? "Principal" : "Reserva";
                String valor = table[i] == NULO ? "-" : table[i];
                System.out.println(i + " " + area + " " + valor);
            }
        }

        public boolean pesquisa ( String key) {
            int index = hash ( key ); 

            String atual = table [ index ]; 

            if ( atual != NULO && atual.equals( key ) )
            { return  true; }

            for ( int i = 0; i < nReserva; i++ ){
                    if ( key.equals(table [ tmPrincipal + i ]))
                    { return  true; }
            }

            return  false; 
        }


        public Boolean remove ( String key ){
            int index = hash ( key ); 

            String atual = table[ index ];

            if ( atual != NULO && atual.equals( key ) ) 
            { table[ index ] = NULO; return  true; }

            for ( int i = 0; i < nReserva; i ++)
            {
                if ( key.equals(table [ tmPrincipal + i ]) )
                    { 
                        for ( int j = i; j < nReserva - 1; j ++ )
                        { table [ tmPrincipal + j ] =  table [ tmPrincipal + j + 1 ]; }
                            table [ tmPrincipal+ nReserva - 1 ] = NULO; nReserva--; return true;
                    } 
            }
                return false;
        }
    }

   public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner (System.in);
        int M = 11; int R = 3;
        if ( sc.hasNextInt() ) {
            M = sc.nextInt(); R = sc.nextInt();
            if ( sc.hasNextLine() ) { sc.nextLine(); }
        }
        Hash h = new Hash(M, R);  

        while ( sc.hasNextLine() ) { 
        String linha = sc.nextLine().trim();
        if ( linha.isEmpty() ) { continue; }
        char escolha = linha.charAt(0); 
        
        if ( escolha == 'M')
            { h.print(); }

        else 
            { String pais = linha.substring(1).trim();
        if ( pais.isEmpty() && sc.hasNextLine() )
            { pais = sc.nextLine( ).trim( ); }

        if ( escolha == 'I') 
        { 
            try { h.inserir( pais ); }           
            catch ( Exception e ) 
            { System.out.println("Erro");}
        }
            else if ( escolha == 'P')
            {System.out.println(h.pesquisa(pais) ? "sim" : "nao") ; }

            else if ( escolha == 'R')
            {   if ( !h.remove ( pais ) ) 
                {System.out.println("Erro");}
            }    
        }
    }
     sc.close();
   }
}