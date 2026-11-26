package Aeds_2.atividades.Hash.Java;
public class Main {

    public static void main(String[] args) throws Exception {

        //hashTableComLista tabela = new hashTableComLista(10);
        hashTableReserva.Hash tabela = new hashTableReserva.Hash(5, 3);


        tabela.insert("João");
        tabela.insert("Maria");
        tabela.insert("Pedro");
        tabela.insert("Ana");
        tabela.insert("Carlos");

        tabela.print();

        System.out.println();

        System.out.println(
            "Busca Carlos: " + tabela.search("Carlos")
        );

        System.out.println(
            "Busca Pedro: " + tabela.search("Pedro")
        );

        tabela.remove("Carlos");

        System.out.println();

        System.out.println("Depois de remover Carlos:");

        tabela.print();
    }
}