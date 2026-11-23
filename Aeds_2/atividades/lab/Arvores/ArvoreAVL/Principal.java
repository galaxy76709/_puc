package Aeds_2.atividades.lab.Arvores.ArvoreAVL;
/**
 * Principal para Arvore Binaria de Pesquisa
 * @author Max do Val Machado
 */
public class Principal {
    public static void main(String[] args) throws Exception {
        ArvoreBinaria arvoreBinaria = new ArvoreBinaria();

        for(int i=1; i<30; i++){
            arvoreBinaria.inserir(i);
            System.out.println("No. nos: " + i);
            System.out.print("\n");
            System.out.println("\t Nivel: " + arvoreBinaria.getNivel());
        }
    }
}