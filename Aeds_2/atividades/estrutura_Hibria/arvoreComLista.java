package Aeds_2.atividades.estrutura_Hibria;
import java.util.*;

import Aeds_2.Tps.Tp_01.Java.invertRec;

/*
Uma arvore Binaria com uma lista simplesmente encadeda

    sua ideia e emular um sistema de agenda de telefone
por onde cada no, guarda um caractere 

e cada no contem sua lista tendo em suas celulas (tendo a celula cabeca)
Nome, Numero, Email, cpf

*/

public class arvoreComLista{
//--------------------------------------------------------

//--------------------------------------------------------
    class Contato {
        public String nome;
        public int numero;
        public String email;
        public int cpf; 

        Contato( )
        { this("",0,"",-1); }

        Contato (String nome, int numero, String email, int cpf ){
            this.nome    = nome; 
            this.numero  = numero; 
            this.email   = email; 
            this.cpf     = cpf;
        }

        
    } // end contato 
    
    class Celula {
        Contato contato; 
        Celula prox;

        Celula( ) {
            this( null, null ); 
        }

            Celula ( Contato contato ){
            this.contato = contato; 
            this.prox = null;
        }


        Celula ( Contato contato, Celula prox){
            this.contato = contato; 
            this.prox = prox;
        }



    }
//--------------------------------------------------------


// classe de No
     class No {
        public char letra; 
        public No esq;
        public No dir;
        public Celula primeiro; 
        public Celula ultimo; 


// construtor padrao 
        No (){
            this.letra = ' ';
            this.esq = null;
            this.dir = null;
        }

//--------------------------------------------------------

        No ( char letra  )
        {
            this.letra = letra;
            this.esq = null;
            this.dir = null;
           primeiro = ultimo = new Celula();
        }


    } // end No
//--------------------------------------------------------

    class Agenda {
        private No root; 

        public Agenda ( ){ root = null; }

        
//--------------------------------------------------------

        public void inserir (char letra)
        { root = inserir(letra, root); }

        public  No inserir ( char letra, No i ){
            if ( i == null ) { i = new No ( letra ) ;}

             else if ( i.letra < letra ) { i.esq = inserir(letra, i.esq); }
             else  i.dir = inserir(letra, i.dir);
             return  i;

        }

//--------------------------------------------------------

        public boolean pesquisaNome (String nome )
        { return   pesquisaNome (root, nome ); }

        private boolean pesquisaNome (No no, String nome){
            boolean resp = false; 

            if ( no == null ) { resp = false; }

            else if (Character.toUpperCase ( nome.charAt ( 0 ) ) == no.letra ){
                for (Celula i = no.primeiro.prox; (!resp && i != null ); i = i.prox )
                { if ( i.contato.nome.equals(nome) == true ) { resp = true; }  }
            }
            else if (Character.toUpperCase ( nome.charAt ( 0 ) ) <  no.letra ) { resp = pesquisaNome(no.esq,nome); }

            else { resp = pesquisaNome(no.dir,nome); }

            return  resp; 
        }

//--------------------------------------------------------

        public void inserir ( Contato contato ) throws Exception 
        { 
            if ( Character.isLetter(contato.nome.charAt ( 0 ) ) ) {  
                root = inserir(contato, root) ;
            }
            else { throw new Error("Erro, contato sem nome aparente"); }
        } 

//--------------------------------------------------------


        private No inserir( Contato contato, No no ){
            char tmpLetra = Character.toUpperCase(contato.nome.charAt(0));
            if ( no == null ) { no = new No ( tmpLetra ); 
                no.ultimo.prox = new Celula(contato); 
                no.ultimo = no.ultimo.prox; 
            }

            else if ( tmpLetra < no.letra ) { return inserir(contato, no.esq); }
            else { return inserir(contato, no.dir ) ; }

            return  no; 
        }

//--------------------------------------------------------

        public boolean pesquisarCpf  ( int cpf ){
            return  pesquisarCpf(root,cpf);
        }

        private  boolean pesquisarCpf (No no, int cpf ){
            boolean resp = false; 

            if ( no != null ) { resp = pesquisarCpf(no.primeiro.prox, cpf) ; 
            if ( resp == false ) {  resp = pesquisarCpf(no.esq, cpf); }

            if (resp == false ) {resp = pesquisarCpf(no.dir, cpf);}

            }
            return resp;
        }

//--------------------------------------------------------

        private boolean pesquisarCpf(Celula celula, int cpf) {
            for (Celula i = celula; i != null; i = i.prox) {
                if (i.contato.cpf == cpf) {
                    return true;
                }
            }
            return false;
        }

//--------------------------------------------------------

        public void remover(String nome) throws Exception {
            remover(nome, root);
        }

        private void remover(String nome, No no) {
            if (no == null) {
                throw new Error("Erro, sem conteudo para ser removido. . . ");
            }

            char inicial = Character.toUpperCase(nome.charAt(0));

            if (inicial < no.letra) { remover(nome, no.esq); } 
            
            else if (inicial > no.letra) { remover(nome, no.dir); } 
            
            else {
                boolean achou = false;

// a celula de i nunca chega no ultimo, ela verifica ate a penultima posicao dos valores
                for (Celula i = no.primeiro; !achou && i.prox != null; i = i.prox) {
                    if (i.prox.contato.nome.equals(nome)) {
// condicao de exisitenca da ultima celula igual 
                        if (no.ultimo == i.prox) { no.ultimo = i; }
                        i.prox = i.prox.prox;
                        achou = true;
                    }
                }

                if (!achou) { throw new Error("Erro, contato nao existe na lista de contatos..."); }
            }
        }

//--------------------------------------------------------

        public void removerLetra (char letra ) throws Exception { root = removerLetra(root, Character.toUpperCase(letra)) ;}

        private No removerLetra ( No no , char letra) {
            if ( no == null ) { throw new Error("Erro: Letra nao encontrada"); }

            else if ( letra < no.letra ) { no.esq = removerLetra ( no.esq ,letra );}
            else if ( letra > no.letra ) { no.dir = removerLetra ( no.dir ,letra );}

// condicao caso nao tenha valores na direita
            else {
                if ( no.dir == null ) {  no = no.esq; } // o valor sera o da esquerda 

// condicao caso nao tenha valores na esquerda 
                else if ( no.esq == null ) { no = no.dir; } // o valor sera o da dir

                else {
                    no.esq = subPeloMaiorEsquerda ( no ,no.esq ); 
                }
            }

            return  no; 
        }


        private No subPeloMaiorEsquerda ( No noRemovido, No i ){

// caminha para direia para achar o maior valor
            if ( i.dir == null ){
                noRemovido.letra    = i.letra;
                noRemovido.primeiro = i.primeiro;
                noRemovido.ultimo   = i.ultimo;

// remove i( copiado ) reconectando os valores que sobrou a esquerda 
                  i = i.esq;
              } 
              else { i.dir = subPeloMaiorEsquerda ( noRemovido, i.dir ); }

              return i; 

           }

//--------------------------------------------------------

}
}