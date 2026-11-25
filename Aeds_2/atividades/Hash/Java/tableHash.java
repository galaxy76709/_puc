package Aeds_2.atividades.Hash.Java;

public class tableHash {

    private No[] table;
    private int size;

    public tableHash(int size) {
        this.size = size;
        this.table = new No[size];

    }

    // funcao destinada para criacao do hash
    private int hash(int key) {
        return key % size;
    }

    /*
     * usando o inserir dessa forma estamos corrigindo as coliscoes com a
     * implementacao
     * de criar uma lista do valor ( importante resaltar que a cabeca da tabela e a
     * propria posicao da tabela)
     * 
     */

    public void insert(int key, String value) {
        int indx = hash(key); // descobre o valor do hash

        // cria um novo no passando os paramentros (sua chave e seu valor)
        No newNo = new No(key, value);

        // apartir do indx ( que foi encontrado pelo hash da key ) cria o encadeamento
        // do codigo ( MUITO IMPORTANTE )
        newNo.next = table[indx]; // o priprio index e a cabeca da tabela gerada

        // no indx e atribuido o no (newNo)
        table[indx] = newNo;
    }

    public String search(int chave) {
        int index = hash(chave);

        No atual = table[index];

        while (atual != null) {
            if (atual.key == chave) {
                return atual.value;
            }
            atual = atual.next;
        }
        return null;
    }

    public Boolean remove(int chave) {
        int index = hash(chave);

        No atual = table[index];

        No anterior = null;

        while (atual != null) {
            if (atual.key == chave) {
                if (anterior == null) {
                    table[index] = atual.next;
                } else {
                    anterior.next = atual.next;
                }
                return true;
            }
            anterior = atual; 
            atual = atual.next; 

        }
        return  false; 
    }


    public void imprimir ( ) { 
        for ( int i = 0; i < size; i = i + 1 ){
            System.out.println(i + ">> ");

            No atual = table[ i ];
        
            while (atual != null ) { 
               System.out.println("( " + atual.key + ", " + atual.value + ") "
            );

            if (atual.next != null ) { 
                System.out.println("-> ");
            }

            atual = atual.next; 
            }
            System.out.println();
 
        }

    }
}
