package Aeds_2.atividades.lab;

import java.util.Scanner;

/*

RESULTADO 100% 


Faça a implementação de uma árvore binária de pesquisa (ABP) em Java para armazenar números inteiros. 
Crie um programa que instancia uma nova árvore e processa as operações solicitadas na entrada de dados, obedecendo o seguinte formato:

    I X: insere o número X;
    P Y: pesquisa se o número Y está na árvore, imprimindo S caso verdadeiro e N caso contrário. O algoritmo de pesquisa deve imprimir cada nó visitado;
    PRE: imprime os elementos da árvore por caminhamento pré-ordem ou V se a árvore estiver vazia;
    POS: imprime os elementos da árvore por caminhamento pós-ordem ou V se a árvore estiver vazia;
    EM: imprime os elementos da árvore por caminhamento em-ordem ou V se a árvore estiver vazia;

Veja os arquivos de entrada (pub.in) e saída (pub.out).

Instruções:

    Criar a classe N >> feito

    Criar a classe ArvoreBinaria, com a seguinte interface:

    public void inserir(int x); >> feito

    public boolean pesquisar(int x); >> feito

    public void caminharPre(); >> feito

    public void caminharPos(); >> feito

    public void caminharEm(); >> feito 

    Criar a classe LabArvoreBinaria com o método main, responsável por ler e processar a entrada de dados. 
*/
public class labArvore
{
// implementaca do no 
    class No 
    { 

        public  int elemento;
        public  No left;
        public  No right; 
        
// construtor padrao
         No ( int elemento ){
            this.elemento = elemento; 
            left  = null;
            right = null; 

         } 
// construtor com dir e esq
         No (int elemento, No left, No right ){
            this.elemento = elemento; 
            this.left = left; 
            this.right = right;
         }

    }    

public  class arvoreBinaria { 
        private No root; 

        public arvoreBinaria(  ) { root = null; }// aterrando a raiz


// funcao destinado para busca na arvore
    public boolean serch ( int target) { return serchRec(target, root); }

        private boolean serchRec (int target, No i) {
            boolean find = false;

// condicao de existencia
            if ( i == null ) { return  find; }

            System.out.print(i.elemento + " ");

// verificacao de valor
            if ( target == i.elemento ) { find = true; }

// condicao andar pela arvore
            else if ( target < i.elemento) { find = serchRec( target, i.left); }
            else   { find = serchRec( target, i.right); }

             return  find; 
    }
// end serch 


// metodo de insercao nao tratara valores iguais
        public void insert ( int X ) { root = insert( X, root ); }

        private  No insert ( int X, No i )
        { 
            if ( i == null ) { i = new No ( X ); }

            else if ( X < i.elemento ) { i.left = insert(X, i.left);}
            else    { i.right = insert(X, i.right); }  return i; 
        }
// end metodo e funcao de insercao


// funcao de caminho central (Em ordem da raiz)
        public void toWalkCentral( ) {
            if (root == null) {
                System.out.println("V");
                return;
            }
            toWalkCentral(root);
            System.out.println( );
        }

        private void toWalkCentral ( No i ){
            if ( i != null ) { 
                toWalkCentral(i.left);
                System.out.print(i.elemento + " ");
                toWalkCentral(i.right);
            }
        }
// em ordem 


// funcao de caminho pre (visita os menores valores primeiro)
        public void toWalkPre ( ) {
            if (root == null) {
                System.out.println("V");
                return;
            }
            toWalkPre(root);
            System.out.println( );
        }

        public void toWalkPre ( No i) {
            if ( i != null ){
                System.out.print(i.elemento + " ");
                toWalkPre(i.left);
                toWalkPre(i.right);

            }
        }

// funcao de caminho pos (visita os maior valores primeiro)
        public void toWalkPos ( ) {
            if (root == null) {
                System.out.println("V");
                return;
            }
            toWalkPos(root);
            System.out.println( );
        }

        public void toWalkPos ( No i) {
            if ( i != null ){
                toWalkPos(i.left);
                toWalkPos(i.right);
                System.out.print(i.elemento + " ");
            }
        }


    }

// main para testes
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        labArvore instancia = new labArvore(); // instanciando o obj
        labArvore.arvoreBinaria arvore = instancia.new arvoreBinaria();

        while (sc.hasNext()) {
            String comand = sc.next();

            if (comand.equals("I")) {
                arvore.insert(sc.nextInt());
            }
            
            else if (comand.equals("P")) {
                boolean find = arvore.serch(sc.nextInt());
                System.out.println(find ? "S" : "N");  
            } 
            
            else if (comand.equals("PRE")) {
                arvore.toWalkPre();
            } 
            
            else if (comand.equals("POS")) {
                arvore.toWalkPos();
            } 
            
            else if (comand.equals("EM")) {
                arvore.toWalkCentral();
            }
        }
        sc.close();
    }

}

