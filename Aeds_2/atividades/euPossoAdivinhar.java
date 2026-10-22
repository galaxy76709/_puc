package Aeds_2.atividades;
import java.util.Scanner;


public class euPossoAdivinhar 
{
    private static Scanner scanner = new Scanner(System.in);

    public  static String inQuaque ( int [] vet )
    {
        int quantidadeOperacoes = vet.length / 2;
        int[] pilha = new int[quantidadeOperacoes];
        int[] fila = new int[quantidadeOperacoes];
        int[] filaPrioridade = new int[quantidadeOperacoes];

        int topoPilha = 0;
        int inicioFila = 0;
        int fimFila = 0;
        int tamanhoFilaPrioridade = 0;

        boolean podeSerPilha = true;
        boolean podeSerFila = true;
        boolean podeSerFilaPrioridade = true;

        for (int i = 0; i < vet.length; i += 2)
        {
            int operacao = vet[i];
            int valor = vet[i + 1];

            if (operacao == 1)
            {
                if (podeSerPilha)
                {
                    pilha[topoPilha] = valor;
                    topoPilha++;
                }

                if (podeSerFila)
                {
                    fila[fimFila] = valor;
                    fimFila++;
                }

                if (podeSerFilaPrioridade)
                {
                    filaPrioridade[tamanhoFilaPrioridade] = valor;
                    tamanhoFilaPrioridade++;
                }
            }
            else
            {
                if (podeSerPilha &&
                    (topoPilha == 0 || pilha[topoPilha - 1] != valor))
                {
                    podeSerPilha = false;
                }
                else if (podeSerPilha)
                {
                    topoPilha--;
                }

                if (podeSerFila &&
                    (inicioFila == fimFila || fila[inicioFila] != valor))
                {
                    podeSerFila = false;
                }
                else if (podeSerFila)
                {
                    inicioFila++;
                }

                int posicaoMaior = -1;
                for (int j = 0; j < tamanhoFilaPrioridade; j++)
                {
                    if (posicaoMaior == -1 ||
                        filaPrioridade[j] > filaPrioridade[posicaoMaior])
                    {
                        posicaoMaior = j;
                    }
                }

                if (podeSerFilaPrioridade &&
                    (posicaoMaior == -1 || filaPrioridade[posicaoMaior] != valor))
                {
                    podeSerFilaPrioridade = false;
                }
                else if (podeSerFilaPrioridade)
                {
                    for (int j = posicaoMaior; j < tamanhoFilaPrioridade - 1; j++)
                    {
                        filaPrioridade[j] = filaPrioridade[j + 1];
                    }
                    tamanhoFilaPrioridade--;
                }
            }
        }

        int estruturasPossiveis = 0;
        String resposta = "";

        if (podeSerPilha)
        {
            estruturasPossiveis++;
            resposta = "stack";
        }
        if (podeSerFila)
        {
            estruturasPossiveis++;
            resposta = "queue";
        }
        if (podeSerFilaPrioridade)
        {
            estruturasPossiveis++;
            resposta = "priority queue";
        }

        if (estruturasPossiveis == 0)
        {
            return "impossible";
        }
        if (estruturasPossiveis > 1)
        {
            return "not sure";
        }
        return resposta;
    }


    public static void main (String [] args)
    {
        while (scanner.hasNextInt())
        {
            int n = scanner.nextInt();
            int[] operacoes = new int[2 * n];

            for (int i = 0; i < n; i++)
            {
                operacoes[2 * i] = scanner.nextInt();
                operacoes[2 * i + 1] = scanner.nextInt();
            }

            System.out.println(inQuaque(operacoes));
        }
    }



}
