package Aeds_2.atividades.lab.Arvores.ArvoreBicolor;

public class Main {

    public static void main(String[] args) {
        try {
            // a. Criar uma árvore vazia
            ArvoreBicolor arvore = new ArvoreBicolor();

            // b. Inserir os elementos na árvore
            // Sequência que aciona fragmentação e as 4 rotações (simples e duplas)
            int[] elementos = {10, 20, 30, 5, 3, 15, 12, 25, 27};

            for (int x : elementos) {
                System.out.println("Inserindo: " + x);
                arvore.inserir(x);
            }

            // c. Caminhar na árvore e imprimir cada elemento com sua cor
            System.out.println("\n--- Caminhamento Central (Em-Ordem) ---");
            arvore.caminharCentral();

            System.out.println("\n--- Caminhamento Pré-Ordem ---");
            arvore.caminharPre();

            System.out.println("\n--- Caminhamento Pós-Ordem ---");
            arvore.caminharPos();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

