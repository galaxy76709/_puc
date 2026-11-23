package Aeds_2.atividades.lab.Arvores.ArvoreBicolor;

/**
 * Arvore binaria de pesquisa
 * @author Max do Val Machado
 */

public class ArvoreBicolor {
	private NoAn raiz; // Raiz da arvore.

	/**
	 * Construtor da classe.
	 */
	public ArvoreBicolor() {
		raiz = null;
	}

// 4 -----------------------------------------------------------

	private  void fragmentar( NoAn i ) {
		i.color = true; 
		i.esq.color = false; 
		i.dir.color = false;

		if ( i == raiz ) { i.color = false; }
	}
//  -----------------------------------------------------------


// 5 -----------------------------------------------------------
	
	// a. rotacao simples esquerda 
	private NoAn rotacionarEsq(NoAn no ) {
		System.out.println("Rotacionar ESQ(" + no.elemento + ")");
		NoAn noDir = no.dir;
		NoAn noDirEsq = noDir.esq;

		noDir.esq = no;
		no.dir = noDirEsq;

		return noDir;
	}


	// b. rotacao simples direita
	private NoAn rotacionarDir(NoAn no) {
		System.out.println("Rotacionar DIR(" + no.elemento + ")");
		NoAn noEsq = no.esq;
		NoAn noEsqDir = noEsq.dir;

		noEsq.dir = no;
		no.esq = noEsqDir;

		return noEsq;
	}



	// c. rotacao dupla direita esquerda
	private NoAn rotacionarDirEsq(NoAn no) {
    no.dir = rotacionarDir(no.dir);
    return rotacionarEsq(no);
	}

	// d. rotacao dupla esquerda direita
	private NoAn rotacionarEsqDir(NoAn no) {
    no.esq = rotacionarEsq(no.esq);
    return rotacionarDir(no);
}	

// -----------------------------------------------------------

// 6 -----------------------------------------------------------

private void balancear(NoAn bisavo, NoAn avo, NoAn pai, NoAn i) {
    if (pai.color == true) {
        if (pai.elemento > avo.elemento) { 
            if (i.elemento > pai.elemento) {
                avo = rotacionarEsq(avo);    
            } else {
                avo = rotacionarDirEsq(avo);   
            }
        } else { 
            if (i.elemento < pai.elemento) 
			  		{ avo = rotacionarDir(avo);      
            } else  { avo = rotacionarEsqDir(avo); }
        }

        if (bisavo == null) {
            raiz = avo;
        } else if (avo.elemento < bisavo.elemento) {
            bisavo.esq = avo;
        } else {
            bisavo.dir = avo;
        }

        avo.color = false;
        avo.esq.color = true;
        avo.dir.color = true;
    }
}

// -------------------------------------------------------------

// 7------------------------------------------------------------

public void inserir(int elemento) throws Exception {
// 1. arvore vazia: cria a raiz sem cor (false)
    if (raiz == null) {
        raiz = new NoAn(elemento);

// 2. arvore com apenas 1 elemento (raiz)
    } else if (raiz.esq == null && raiz.dir == null) {
        if (elemento < raiz.elemento) {
            raiz.esq = new NoAn(elemento);
        } else if (elemento > raiz.elemento) {
            raiz.dir = new NoAn(elemento);
        } else {
            throw new Exception("Erro ao inserir (elemento repetido)!");
        }

// 3. arvore com 2 elementos (raiz e filho a direita)
    } else if (raiz.esq == null) {

        if (elemento < raiz.elemento) {
            raiz.esq = new NoAn(elemento);

        } else if (elemento < raiz.dir.elemento) {
            raiz.esq = new NoAn(raiz.elemento);
            raiz.elemento = elemento;

        } else if (elemento > raiz.dir.elemento) {
            raiz.esq = new NoAn(raiz.elemento);
            raiz.elemento = raiz.dir.elemento;
            raiz.dir.elemento = elemento;

        } else {
			raiz.esq.color = raiz.dir.color = false;
		}

// 4. arvore com 2 elementos (raiz e filho a esquerda)
    } else if (raiz.dir == null) {
        if (elemento > raiz.elemento) {
            raiz.dir = new NoAn(elemento);

        } else if (elemento > raiz.esq.elemento) {

            raiz.dir = new NoAn(raiz.elemento);
            raiz.elemento = elemento;

        } else if (elemento < raiz.esq.elemento) {

            raiz.dir = new NoAn(raiz.elemento);
            raiz.elemento = raiz.esq.elemento;
            raiz.esq.elemento = elemento;

        } 

        raiz.esq.color = raiz.dir.color = false;

// 5. Caso global (3 elementos ) descida recursiva com os 4 apontadores
    } else { inserir(elemento, null, null, null, raiz); }

    raiz.color = false;// >> forca a raiz ser preta pela regra 
}

private void inserir(int elemento, NoAn bisavo, NoAn avo, NoAn pai, NoAn i) throws Exception {
    if (i == null) {
// Todo novo NoAn folha entra COM cor (true)
        if (elemento < pai.elemento) {
            i = pai.esq = new NoAn(elemento, null, null, true);
        }
		 else {
            i = pai.dir = new NoAn(elemento, null, null, true);
        }

// caso o pai também tiver cor, a dois nos seguidos com cor >> rotaciona
        if (pai.color == true) {
            balancear(bisavo, avo, pai, i);
        }
    } else {
// verifica se 'i' e um no do tipo 4 (ambos os filhos com cor) na descida
        if (i.inNotTipo4(i)) {
            fragmentar(i);
            if (i == raiz) {
                i.color = false;
            } else if (pai.color == true) {
                balancear(bisavo, avo, pai, i);
            }
        }

// avanca para o prox nível deslocando a janela 
        if (elemento < i.elemento) {
            inserir(elemento, avo, pai, i, i.esq);
        } else if (elemento > i.elemento) {
            inserir(elemento, avo, pai, i, i.dir);
        } 
    }
}
// -------------------------------------------------------------


// 8 ------------------------------------------------------------
private void inserir_rec (int x, NoAn bisavo, NoAn avo, NoAn pai, NoAn i) throws Exception {
// a. Chegou à posição de inserção: insere como uma folha colorida (true)
    if (i == null) {
        if (pai == null) {
            raiz = new NoAn(x, null, null, false);
        } else {
            if (x < pai.elemento) {
                i = pai.esq = new NoAn(x, null, null, true);
            } else {
                i = pai.dir = new NoAn(x, null, null, true);
            }

            // Verifica se o pai existe e é colorido antes de balancear
            if (pai != null && pai.color == true) {
                balancear(bisavo, avo, pai, i);
            }
        }

// b. Caso contrário, continua percorrendo a árvore
    } else {
        // i. Se for um 4-nó, aplica o reequilíbrio de recoloração (fragmentação)
        if (i.inNotTipo4(i)) {
            fragmentar(i);

            // Ao fragmentar, 'i' ganha cor: se o pai existir e também for colorido, rotaciona
            if (pai != null && pai.color == true) {
                balancear(bisavo, avo, pai, i);
            }
        }

// ii. Chama recursivamente a inserção na subárvore esquerda ou direita
        if (x < i.elemento) {
            inserir_rec(x, avo, pai, i, i.esq);
        } else if (x > i.elemento) {
            inserir_rec(x, avo, pai, i, i.dir);
        } else {
            throw new Exception("Erro ao inserir (elemento repetido)!");
        }
    }
}
// --------------------------------------------------------------


// -------------------------------------------------------------

// 9 ------------------------------------------------------------
    public void caminharCentral() {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    private void caminharCentral(NoAn i) {
        if (i != null) {
            caminharCentral(i.esq);
            System.out.print(i.elemento + "(" + (i.color ? "Colorido" : "Sem cor") + ") ");
            caminharCentral(i.dir);
        }
    }

    public void caminharPre() {
        System.out.print("[ ");
        caminharPre(raiz);
        System.out.println("]");
    }

    private void caminharPre(NoAn i) {
        if (i != null) {
            System.out.print(i.elemento + "(" + (i.color ? "Colorido" : "Sem cor") + ") ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    public void caminharPos() {
        System.out.print("[ ");
        caminharPos(raiz);
        System.out.println("]");
    }

    private void caminharPos(NoAn i) {
        if (i != null) {
            caminharPos(i.esq);
            caminharPos(i.dir);
            System.out.print(i.elemento + "(" + (i.color ? "Colorido" : "Sem cor") + ") ");
        }

// --------------------------------------------------------------

    }

}