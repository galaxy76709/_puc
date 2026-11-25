package Aeds_2.atividades.Hash.Java;
public class Main {

    public static void main(String[] args) {

        tableHash tabela = new tableHash(10);

        tabela.insert(10, "João");
        tabela.insert(20, "Maria");
        tabela.insert(30, "Pedro");
        tabela.insert(15, "Ana");
        tabela.insert(25, "Carlos");

        tabela.imprimir();

        System.out.println();

        System.out.println(
            "Busca 25: " + tabela.search(25)
        );

        System.out.println(
            "Busca 30: " + tabela.search(30)
        );

        tabela.remove(25);

        System.out.println();

        System.out.println("Depois de remover 25:");

        tabela.imprimir();
    }
}