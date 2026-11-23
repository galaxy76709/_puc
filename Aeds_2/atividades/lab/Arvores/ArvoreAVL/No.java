package Aeds_2.atividades.lab.Arvores.ArvoreAVL;
/*
 * No da arvore binaria
 * @author Max do Val Machado
 */

class No {
    private static final No No = null;
    public int elemento; // Conteudo do no.
    public int nivel; 
    public No esq, dir;  // Filhos da esq e dir.

    /**
     * Construtor da classe.
     * @param elemento Conteudo do no.
     */
    public No(int elemento) {
        this(elemento, null, null, 0);
    }

    /**
     * Construtor da classe.
     * @param elemento Conteudo do no.
     * @param esq No da esquerda.
     * @param dir No da direita.
     */
    public No(int elemento, No esq, No dir, int nivel) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.nivel = nivel;
    }



public  static int getNivel(No i) {
    if (i == null) { return -1; }

    int heightLeft  = getNivel(i.esq);
    int heightRight = getNivel(i.dir);
    i.nivel = 1 + Math.max(heightLeft, heightRight);

    return i.nivel;
}

public void setNivel() {
    this.nivel = 1 + Math.max(getNivel(this.esq), getNivel(this.dir));
}



}