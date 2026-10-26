/*
Uma plataforma de cinema deseja apresentar um ranking dos 
filmes mais premiados no Oscar. Para isso, possui um catálogo com informações 
sobre os filmes, incluindo o título, o ano de lançamento e a quantidade de prêmios
recebidos.

A classificação deverá seguir os seguintes critérios, nesta ordem:

Quantidade de prêmios: filmes com mais prêmios devem aparecer primeiro.

Ano de lançamento: em caso de empate na quantidade de prêmios, o filme mais
antigo deve aparecer primeiro.

Título: caso a quantidade de prêmios e o ano sejam iguais, os filmes devem
ser classificados em ordem alfabética.

Como apenas os K primeiros colocados são necessários, você deverá utilizar
uma adaptação do Heapsort para realizar uma ordenação parcial.

ENTRADA

A entrada contém diversos casos de teste e termina com o fim do arquivo (EOF).

Cada caso de teste começa com dois inteiros N e K, indicando, respectivamente,
a quantidade de filmes e a quantidade de filmes que deverão aparecer no ranking,
sendo 1 ≤ K ≤ N.

As N linhas seguintes contêm três campos, separados por espaços:

Quantidade de prêmios recebidos no Oscar (inteiro).

Ano de lançamento do filme (inteiro).

Título do filme (texto com até 100 caracteres, podendo conter espaços).

SAÍDA

Para cada caso de teste, imprima apenas os K primeiros filmes do ranking,
respeitando os três critérios de classificação.

Cada filme deverá ser apresentado em uma linha, contendo a quantidade de prêmios,
o ano de lançamento e o título, no mesmo formato da entrada.

Não imprima linhas em branco entre os casos de teste.

*/


package Aeds_2.atividades.lab;
import java.util.*;


public class filmeOscar 
{
//-------------------------------------------------------------
// classe dos filmes
    public static class Filme {
        public int    premio; 
        public int    ano; 
        public String titulo;

// construtor base para os filmes
        Filme ( ) {
            this.premio = 0; 
            this.ano    = 0;
            this.titulo = ""; 

        }

// construtor com os valores 
        Filme ( int premio, int ano,  String titulo){
            this.premio = premio;
            this.ano    = ano; 
            this.titulo = titulo;
        }
    }



    private static boolean prioridade ( Filme a, Filme b)
    {
// caso a e b nao sejam identicos retornara o filme com mais premios
        if ( a.premio != b.premio ) { return  a.premio > b.premio ; }

// caso a e b nao sejam identicos retornara o filme mais antigo
        if ( a.ano != b.ano ) { return  a.ano < b.ano; }

// retornara o titulo em ordem alfabetica
        return a.titulo.compareTo(b.titulo) < 0;
    }

// desce o elemento da posicao i ate a posicao correta no heap
// o melhor filme fica sempre na raiz do heap
    public static void sift (Filme filmes [ ], int i, int size ){
        int father = i;

        while ( 2 * father + 1 < size ) {
            int child = 2 * father + 1;

// escolhe o melhor entre os dois filhos
            if ( child + 1 < size && prioridade (filmes[child + 1], filmes[child]) ) {
                child++;
            }

// se o filho for melhor que o pai eles trocam de lugar
            if ( prioridade (filmes[child], filmes[father]) ) {
                Filme tmp = filmes[child];
                filmes[child] = filmes[father];
                filmes[father] = tmp;
                father = child;
            }

// caso contrario o elemento ja esta na posicao correta
            else {
                break;
            }
        }
    }

// heap sort parcial: retira somente os K melhores filmes do heap
// os K melhores ficam nas ultimas K posicoes, do melhor (N - 1) ao pior (N - K)
    public static void heapSort (Filme filmes [ ], int N, int K ){

// constroi o heap com todos os filmes
        for ( int i = N / 2 - 1; i >= 0; i-- ) {
            sift(filmes, i, N);
        }

        int size = N;

// repete somente K vezes, sem ordenar o restante
        for ( int x = 0; x < K; x++ ) {
            Filme tmp = filmes[0];
            filmes[0] = filmes[size - 1];
            filmes[size - 1] = tmp;

            size--;
            sift(filmes, 0, size);
        }
    }
// end classe dos filmes
//-------------------------------------------------------------



//-------------------------------------------------------------
// main 
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

            while (sc.hasNextInt( )) 
            {
                int N = sc.nextInt( ); 
                int K = sc.nextInt( ); 

                Filme [ ] filmes = new Filme [ N ];
                
                for ( int i = 0; i < N; i++ ) {
                    int    premio = sc.nextInt ( );
                    int       ano = sc.nextInt ( );
                    String titulo = sc.nextLine( );   // vem com o espaco na frente

                    filmes[i] = new Filme(premio, ano, titulo);
                }

                heapSort(filmes, N, K);

                for (int i = 0; i < K; i++) {
                    // sem " " antes do titulo: ele ja carrega o espaco
                    System.out.println(filmes[N - 1 - i].premio + " " + filmes[N - 1 - i].ano + filmes[N - 1 - i].titulo);
                }
                
            }

        
    }
    
}