//dependencias-----------------------------------------------------
package Aeds_2.atividades.Arvore;

//--------------------------------------------------------
/*classe voltada a criacao de no  */
     class No {
          public int elemento; 
          public  No left; 
          public  No right; 

          No ( int elemento)
          {
               this.elemento = elemento; 
               this.left = null; 
               this.right = null; 
          }

          No (int elemento, No left, No right){
               this.elemento = elemento; 
               this.left = left; 
               this.right = right; 

          }

     }
//--------------------------------------------------------


//--------------------------------------------------------
     public class arvoreBinaria 
{
// criacao da raiz principal 
	private No root;

// construtor basico
     public  arvoreBinaria( ) {
     root = null; 
}

//--------------------------------------------------------

//--------------------------------------------------------
// funcoes de pesquisa


// funcao de pesquisa em arvore binaria, a ideia segue a mesma da pesquisa binaria
// ja vista em outras atividadaes e modelos presentes 
     public  boolean search (int X, No i ) {

     boolean answ = false; 

// condicao de verificao de existencia
     if ( i == null ){ return  answ; }

     else if ( X == i.elemento ) { answ = true; }

// realizando uma recuesao basica capaz de percorrer ( "caminhar ou varrer" ) a arvore
     else if ( X < i.elemento ) { answ = search (X, i.left) ; }
     else   { answ = search( X, i.right) ; }

     return answ; 

}
//--------------------------------------------------------


//--------------------------------------------------------
// funcoes de caminhada


//
// metodo interativo
     public void toWalkCentral ( ) {
          System.out.println("[ ");
          toWalkCentral(root);
          System.out.println(" ]");

     }
// metodo recursivo
        public void toWalkCentral (No i ) {
          if ( i != null ) { 
               toWalkCentral(i.left);
               System.out.println(i.elemento + " ");
               toWalkCentral(i.right);
          }
        }


//   


//
// metodo para caminhas comencando pela raiz
     public void toWalkPre ( ) {
          System.out.println("[ ");
          toWalkCentral(root);
          System.out.println(" ]");

     }
// metodo recursivo
        public void toWalkPre (No i ) {
          if ( i != null ) {
               System.out.println(i.elemento + " "); // conteudo do no
               toWalkPre(i.left);// elemento da esqueda
               toWalkPre(i.right);// elemento da direita
          }
        }
//


// metodo para caminhas comencando pelo final
     public void toWalkPos( ) {
          System.out.println("[ ");
          toWalkCentral(root);
          System.out.println(" ]");

     }
// metodo recursivo
        public void toWalkPos (No i ) {
          if ( i != null ) {
               toWalkPre(i.left);// elemento da esqueda
               toWalkPre(i.right);// elemento da direita
               System.out.println(i.elemento + " "); // conteudo do no

          }
        }
//--------------------------------------------------------


//--------------------------------------------------------
// metodo de insersao 
     public void insert ( int X) 
     { root = insert(X, root); }

//


//funcao vai tratar a condicao de valores iguais como maiore logo 
// se X = X, o valor sera inserido a sua direita 
     private  No insert ( int X, No i ){

// passo base do no, caso nao ouver sera criado um novo objeto em sua referencia
     if ( i == null ){ i = new No ( X ); }

     else if( X < i.elemento ) {
          i.left = insert(X, i.left );
     }

     else { i.right = insert(X, i.right);}

     return  i; 

}
//--------------------------------------------------------

//--------------------------------------------------------
//inserir pai
     public  void insertFather ( int X ){
     if ( root == null) {  root = new No ( X ) ; } // >> instanciando a raiz e adicionando valores a ela

     else if ( X < root.elemento ) { insertFather( X, root.left, root.right);}

     else  {insertFather(X, root.right, root.left);}
}

//

// funcao recusiva de inserir 
     private  void insertFather ( int X, No i, No father ){
// condicao de existencia de valor ( uma folha)
     if ( i == null ) { 
          if ( X < father.elemento) { father.left = new No ( X ); }
          
          else { father.right = new No ( X ); }
      } 

      else if ( X < i.elemento ) { insertFather(X, i.left, i);}

      else   { insertFather(X, i.right, i); }

}
//--------------------------------------------------------


//--------------------------------------------------------
// remover 

     public   void remove ( int X ) {
     root = remove( X, root );
}

//

     private  No remove ( int X, No i ) {
     if ( i == null ) { 
          System.out.println("Erro ao remover o valor da arvore...");
     }

     else if ( X < i.elemento ) { i.left = remove(X, i.left ); }

     else if ( X > i.elemento ) { i.right = remove(X, i.right ); }

// condicao caso nao tenha No na direita ou na esquerda
     else if (  i.right == null ) { i = i.left ; }

     else  { i.left = bigLeft ( i, i.left) ; }

     return  i ;
}

//
//--------------------------------------------------------
// metodo para pegar os maiores valores
     private  No bigLeft ( No i, No L  ) {

     if ( L.right == null ) { 
          i.elemento = L.elemento; 
          L = L.left; 
     } else {   L.right = bigLeft(i, L.right); }

     return L;
}

     public  int getBiggest ( ){
     int answ = -1; // valores sendo considerados >= 0 
     
     if ( root != null ){
          No i; 
          for ( i = root; i.right != null; i = i.right );
          answ = i.elemento;
     }

     return answ;
}

     public  int getMinor ( ){
     int answ = -1; // valores sendo considerados >= 0 
     
     if ( root != null ){
          No i; 
          for ( i = root; i.left != null; i = i.left );
          answ = i.elemento;
     }

     return answ;
}

     public  int getHigh( ){ return  getHigh(); }

     private  int getHigh ( No i, int high ){

          if ( i == null ) { high = high - 1; }
          else {

                int highLeft   = getHigh(i.left, high + 1); 
                int highRight  = getHigh(i.left, high + 1); 

                high = ( highLeft > highRight ) ? highLeft : highRight; 

          }

          return high;


}






}
