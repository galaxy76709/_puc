/*
Uma instituição de ensino deseja reconhecer os alunos que obtiveram os melhores resultados
acadêmicos durante o semestre. Para isso, será elaborado um ranking com os K alunos mais bem classificados
considerando o desempenho e a frequência de cada estudante.

A classificação deverá seguir os seguintes critérios, nesta ordem:

Nota final: alunos com maiores notas devem aparecer primeiro.

Número de faltas: em caso de empate na nota, o aluno com menos faltas deve aparecer primeiro.

Nome: caso a nota e o número de faltas sejam iguais, os alunos devem ser classificados em ordem alfabética.

Como apenas os K primeiros colocados são necessários, você deverá utilizar uma adaptação do Quicksort para
realizar uma ordenação parcial, evitando ordenar as partições que não são necessárias para determinar os K
primeiros colocados do ranking.

ENTRADA

A entrada contém diversos casos de teste e termina com o fim do arquivo (EOF).

Cada caso de teste começa com dois inteiros N e K, indicando, respectivamente, a quantidade de alunos
e a quantidade de alunos que deverão aparecer no ranking, sendo 1 ≤ K ≤ N.

As N linhas seguintes contêm a nota final, o número de faltas e o nome de cada aluno, separados por espaços.

A nota e o número de faltas são valores inteiros. O nome possui no máximo 50 caracteres e pode conter espaços.

SAÍDA

Para cada caso de teste, imprima apenas os K primeiros colocados, respeitando os três critérios de classificação.

Cada aluno deverá ser apresentado em uma linha, contendo a nota final, o número de faltas e o nome,
no mesmo formato da entrada.

Não imprima linhas em branco entre os casos de teste.

*/
package Aeds_2.atividades.lab;
import java.util.*;


public class destaqueAcademico 
{
//-------------------------------------------------------------
// classe dos alunos
    public static class Aluno {
        public int    nota; 
        public int  falta; 
        public String nome;

// construtor base para os alunos
        Aluno ( ) {
            this.nota  = 0; 
            this.falta = 0;
            this.nome  = ""; 

        }

// construtor com os valores 
        Aluno ( int nota, int falta,  String nome){
            this.nota  = nota;
            this.falta = falta; 
            this.nome  = nome;
        }
    }



    private static boolean prioridade ( Aluno a, Aluno b)
    {
// caso a e b nao sejam identicas retornara a maior nota
        if ( a.nota != b.nota ) { return  a.nota > b.nota ; }

// caso a e b nao sejam identicas retornara a menor falta
        if ( a.falta != b.falta ) { return  a.falta < b.falta; }

// retornara o nome em ordem alfabetica
        return a.nome.compareTo(b.nome) < 0;
    }

// quick sort de alunos e seus valores 
    public static void quickSort (Aluno alunos [ ], int left, int right, int K ){
        if (left >= right) {
            return;
        }

        int i = left, j = right; 

        Aluno pivo = alunos[ ( left + right ) / 2 ]; 

        while ( i <= j ) {
            while (prioridade (alunos[i], pivo) )  { i++; }
            while (prioridade (pivo, alunos[j]) ) { j--; }

            if ( i <= j )
            {
                Aluno tmp = alunos[i];
                alunos[i] = alunos[j];
                alunos[j] = tmp;
                i++;
                j--;
            }
        }

        if (left < j) {
            quickSort(alunos, left, j, K);
        }
        if (i < right && i < K) {
            quickSort(alunos, i, right, K);
        }
    }
// end classe dos alunos
//-------------------------------------------------------------



//-------------------------------------------------------------
// main 
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

            while (sc.hasNextInt( )) 
            {
                int N = sc.nextInt( ); 
                int K = sc.nextInt( ); 

                Aluno [ ] alunos = new Aluno [ N ];
                
                for ( int i = 0; i < N; i++ ) {
                    int    nota = sc.nextInt ( );
                    int   falta = sc.nextInt ( );
                    String nome = sc.nextLine().trim();

                    alunos[i] = new Aluno(nota, falta, nome);
                }

                quickSort(alunos, 0, N - 1, K);

                for (int i = 0; i < K; i++) {
                    System.out.println(alunos[i].nota + " " + alunos[i].falta + " " + alunos[i].nome);
                }
                
            }

        
    }
    
}
