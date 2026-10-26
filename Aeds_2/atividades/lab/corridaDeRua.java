/*
A organização de uma corrida de rua possui um relatório com os 
resultados dos participantes. Para cada corredor, são registrados seu nome e o 
tempo gasto para completar a prova, representado por horas, minutos e segundos.

O relatório original apresenta os corredores em ordem alfabética pelo nome.
Para a premiação, a organização deseja identificar os K primeiros colocados,
considerando os menores tempos de conclusão.

Quando dois ou mais corredores tiverem exatamente o mesmo tempo, a
ordem em que aparecem no relatório original deverá ser mantida.

Como apenas os K primeiros colocados são necessários,
você deverá utilizar uma adaptação do Insertion Sort para realizar uma
ordenação parcial, mantendo ordenados somente os K melhores resultados
durante o processamento dos corredores, sem ordenar todos os participantes.

ENTRADA

A entrada contém diversos casos de teste e termina com o fim do arquivo (EOF).

Cada caso começa com dois inteiros N e K, indicando, respectivamente, a
quantidade de corredores e a quantidade de primeiros colocados que devem ser
apresentados, sendo 1 ≤ K ≤ N.

As N linhas seguintes contêm quatro campos separados por espaços: o nome do
corredor, as horas, os minutos e os segundos gastos para completar a prova.

O nome possui no máximo 30 caracteres, contém apenas letras e não possui espaços.

Os corredores são fornecidos em ordem alfabética pelo nome.

SAÍDA

Para cada caso de teste, imprima apenas os K primeiros colocados, do 
menor para o maior tempo de conclusão.

Em caso de empate, preserve a ordem em que os corredores aparecem na entrada.

Cada corredor deverá ser apresentado em uma linha, contendo o nome 
seguido das horas, minutos e segundos, no mesmo formato da entrada.

Não imprima linhas em branco entre os casos de teste.
*/package Aeds_2.atividades.lab;
import java.util.*;


public class corridaDeRua 
{
//-------------------------------------------------------------
// classe dos corredores
    public static class Corredor {
        public String nome; 
        public int    hora; 
        public int    minuto; 
        public int    segundo;

// construtor base para os corredores
        Corredor ( ) {
            this.nome    = ""; 
            this.hora    = 0;
            this.minuto  = 0; 
            this.segundo = 0;

        }

// construtor com os valores 
        Corredor ( String nome, int hora, int minuto, int segundo ){
            this.nome    = nome;
            this.hora    = hora;
            this.minuto  = minuto; 
            this.segundo = segundo;
        }
    }



    private static boolean prioridade ( Corredor a, Corredor b)
    {
// tempo de cada corredor convertido para segundos
        int timeA = a.hora * 3600 + a.minuto * 60 + a.segundo;
        int timeB = b.hora * 3600 + b.minuto * 60 + b.segundo;

// retorna true somente se o tempo de a for estritamente menor
// em caso de empate retorna false, assim quem chegou primeiro na entrada nao e ultrapassado 
        return timeA < timeB;
    }

// insertion sort parcial: mantem ordenados somente os K melhores
// retorna a nova quantidade de corredores guardados em top
    public static int insertionSort (Corredor top [ ], int size, Corredor corredor, int K ){
        int j;

// caso ainda exista espaco, o novo corredor entra no final
        if ( size < K ) {
            j = size;
            size++;
        }

// caso o vetor esteja cheio, o novo so entra se for melhor que o ultimo
        else if ( prioridade (corredor, top[K - 1]) ) {
            j = K - 1;
        }

// caso contrario o corredor e descartado
        else {
            return size;
        }

// desloca para a direita os corredores com tempo estritamente pior
        while ( j > 0 && prioridade (corredor, top[j - 1]) ) {
            top[j] = top[j - 1];
            j--;
        }

        top[j] = corredor;

        return size;
    }
// end classe dos corredores
//-------------------------------------------------------------



//-------------------------------------------------------------
// main 
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

            while (sc.hasNextInt( )) 
            {
                int N = sc.nextInt( ); 
                int K = sc.nextInt( ); 

                Corredor [ ] top = new Corredor [ K ];
                int size = 0;
                
                for ( int i = 0; i < N; i++ ) {
                    String nome    = sc.next ( );
                    int    hora    = sc.nextInt ( );
                    int    minuto  = sc.nextInt ( );
                    int    segundo = sc.nextInt ( );

                    Corredor corredor = new Corredor(nome, hora, minuto, segundo);

                    size = insertionSort(top, size, corredor, K);
                }

                for (int i = 0; i < K; i++) {
                    System.out.println(top[i].nome + " " + top[i].hora + " " + top[i].minuto + " " + top[i].segundo);
                }
                
            }

        
    }
    
}