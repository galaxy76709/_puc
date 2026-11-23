package Aeds_2.atividades.lab.Arvores.ArvoreBicolor;

/**
 * No da arvore binaria
 * @author Max do Val Machado
 */
class NoAn {
	public int elemento; // Conteudo do no.
	public boolean color ; // 2. novo atributo >> cor <<
	public NoAn esq, dir; // Filhos da esq e dir.

	public NoAn ( ) {  this ( -1 ); }


	/**
	 * Construtor da classe
	 * @param elemento Conteudo do noAn.
	 */
	public NoAn (int elemento) {
		this (elemento, null, null, false);
	}

	/**
	 * Construtor da classe.
	 * @param elemento Conteudo do no.
	 * @param esq      No da esquerda.
	 * @param dir      No da direita.
	 */
	public NoAn (int elemento, NoAn esq, NoAn dir, boolean color) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
		this.color  = color;
	}


// 4 -----------------------------------------------------------

	/**
	 * Verificador de no tipo 4.
	 * @param elemento Conteudo do no.
	 * @param esq      No da esquerda.
	 * @param dir      No da direita.
	 */
    public boolean inNotTipo4( NoAn i  ){
        return ( i.esq != null && i.dir != null 
             && i.esq.color == true && i.dir.color == true );

    }
// -----------------------------------------------------------


}