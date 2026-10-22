package Aeds_2.Tps.Tp_01;

import java.util.Random;
import java.util.Scanner;

class cesarSwap {
    public static String swapWord(String entrada, Random gerador) {
        char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < entrada.length(); i++) {
            char c = entrada.charAt(i);
            if (c == letra1) {
                sb.append(letra2);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random gerador = new Random();
        gerador.setSeed(4);

        String linha = sc.nextLine();

        while (!linha.equals("FIM")) {
            String resultado = swapWord(linha, gerador);
            System.out.println(resultado);
            linha = sc.nextLine();
        }

        sc.close();
    }
}
