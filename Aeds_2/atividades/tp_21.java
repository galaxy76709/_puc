
package Aeds_2.atividades;
import java.util.*;

class tp_21 {
    /*
     * entrada:
     * 
     * N >> 3
     * 
     * 3
     * + (/n) Lucas
     * - (/n) Arthur
     * + (/n) Caio
     * 
     */

    public static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int myCompareTo(String a, String b) {
        return a.compareTo(b);
    }

    /*
     * funcao destinada para ordenacao apartir de valores
     * Selection sort >> ordena do manor com o maior elemento da parte nao ordenada
     * com o primiero nao ordenado
     */
    public static void selectionSort(String[] arr, int n) {
        for (int i = 0; i < n - 1; i = i + 1) {
            int idx_min = i; // >> pega o menor valor

            for (int j = i + 1; j < n; j = j + 1) { // verifica os valores a frente da posicao autal
            if ( myCompareTo(arr[j], arr[idx_min]) < 0 )
                    { idx_min = j; }

            }

            swap(arr, i, idx_min);
        }
    }

    public static void main(String[] args) {
        int X = 0;
        int plusNum = 0;
        int minusNum = 0;
        Scanner sc = new Scanner(System.in);
        X = sc.nextInt();
        sc.nextLine();
        String[] nomes = new String[X];

        for (int i = 0; i < X; i = i + 1) {
            String S = sc.nextLine();
            String[] C = S.split(" ");

            if (C[0].charAt(0) == '+') {
                plusNum = plusNum + 1;
            } else if (C[0].charAt(0) == '-') {
                minusNum = minusNum + 1;
            }

            nomes[i] = C[1];
        }

        selectionSort(nomes, X);

        for (int i = 0; i < X; i = i + 1) {
            System.out.println(nomes[i]);
        }

        System.out.println("Se comportaram: " + plusNum + " | Nao se comportaram: " + minusNum);

        sc.close();
    }

}