package Aeds_2.Tps.Tp_02;

/*
Aluno: Kayky Gabriel Marques

Matricula: 845963
*/

// -----Dependencias---------------------------------------------------------------

import java.util.*;
import java.io.*;

// -----Data---------------------------------------------------------------

class Data {

    private int ano;
    private int mes;
    private int dia;

    public Data(int ano, int mes, int dia) {
        this.mes = mes;
        this.ano = ano;
        this.dia = dia;
    }

    public static Data parseData(String s) {
        String[] compos = s.split("-");

        int ano = Integer.parseInt(compos[0]);
        int mes = Integer.parseInt(compos[1]);
        int dia = Integer.parseInt(compos[2]);

        return new Data(ano, mes, dia);
    }

    public String formatarData() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

// -----Carro---------------------------------------------------------------

class Carro {

    private int id = 0;
    private int ano = 0;
    private int cilindros = 0;
    private double consumoCidade = 0.0;
    private double consumoEstrada = 0.0;
    private double cilindrada = 0.0;
    private double co2 = 0.0;
    private boolean turbo;
    private Data dataRegistro;

    private String marca = "";
    private String modelo = "";
    private String categoria = "";
    private String[] combustivel;
    private String transmissao = "";
    private String tracao = "";

    public Carro(int id, String marca, String modelo, int ano,
                 String categoria, String[] combustivel,
                 int cilindros, double cilindrada,
                 String transmissao, String tracao,
                 double consumoCidade, double consumoEstrada,
                 double co2, boolean turbo, Data dataRegistro) {

        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.categoria = categoria;
        this.combustivel = combustivel;
        this.cilindros = cilindros;
        this.cilindrada = cilindrada;
        this.consumoCidade = consumoCidade;
        this.consumoEstrada = consumoEstrada;
        this.co2 = co2;
        this.turbo = turbo;
        this.dataRegistro = dataRegistro;
        this.transmissao = transmissao;
        this.tracao = tracao;
    }

    public Carro() {
    }

    // -----Gets---------------------------------------------------------------

    public int getId() {
        return this.id;
    }

    public int getAno() {
        return this.ano;
    }

    public int getCilindros() {
        return this.cilindros;
    }

    public double getConsumeCidade() {
        return consumoCidade;
    }

    public double getConsumoEstrada() {
        return this.consumoEstrada;
    }

    public double getCilindrada() {
        return this.cilindrada;
    }

    public String getMarca() {
        return this.marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    // -----Parse---------------------------------------------------------------

    public static double parseDouble(String s) {
        return Double.parseDouble(s.trim());
    }

    public static Carro parseVeiculos(String S) {

        String[] compos = S.split(",", -1);

        int id = Integer.parseInt(compos[0]);

        String marca = compos[1];
        String modelo = compos[2];

        int ano = Integer.parseInt(compos[3]);

        String categoria = compos[4];

        String[] combustivel = compos[5].split(";");

        int cilindros = Integer.parseInt(compos[6]);

        double cilindrada = parseDouble(compos[7]);

        String transmissao = compos[8];
        String tracao = compos[9];

        double consumoCidade = parseDouble(compos[10]);
        double consumoEstrada = parseDouble(compos[11]);
        double co2 = parseDouble(compos[12]);

        boolean turbo = Boolean.parseBoolean(compos[13]);

        Data dt = Data.parseData(compos[14]);

        return new Carro(
                id,
                marca,
                modelo,
                ano,
                categoria,
                combustivel,
                cilindros,
                cilindrada,
                transmissao,
                tracao,
                consumoCidade,
                consumoEstrada,
                co2,
                turbo,
                dt
        );
    }

    // -----Formatacao---------------------------------------------------------------

    public String formatar() {

        String combustiveis = "[" + String.join(",", combustivel) + "]";

        String cilindradaFormatada =
                String.format(Locale.US, "%.1f", cilindrada);

        String consumoCidadeFormatado =
                String.format(Locale.US, "%.2f", consumoCidade);

        String consumoEstradaFormatado =
                String.format(Locale.US, "%.2f", consumoEstrada);

        String co2Formatado =
                String.format(Locale.US, "%.1f", co2);

        return "[" + id + " ## " + marca + " ## " + modelo + " ## " + ano
                + " ## " + categoria + " ## " + combustiveis
                + " ## " + cilindros + " ## " + cilindradaFormatada
                + " ## " + transmissao + " ## " + tracao
                + " ## " + consumoCidadeFormatado
                + " ## " + consumoEstradaFormatado
                + " ## " + co2Formatado
                + " ## " + turbo
                + " ## " + dataRegistro.formatarData() + "]";
    }

    // -----CSV---------------------------------------------------------------

    public static ArrayList<Carro> lerCsv() throws IOException {

        File arquivo = new File("/tmp/veiculos.csv");

        ArrayList<Carro> carros = new ArrayList<>();

        try (BufferedReader leitor =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(arquivo), "UTF-8"))) {

            leitor.readLine();

            String linha;

            while ((linha = leitor.readLine()) != null) {

                if (!linha.trim().isEmpty()) {
                    carros.add(parseVeiculos(linha));
                }
            }
        }

        return carros;
    }
}

// -----Lista com Alocacao Sequencial---------------------------------------------------------------

class Lista {

    private Carro[] array;
    private int n;

    public Lista() {
        this(20000);
    }

    public Lista(int tamanho) {

        array = new Carro[tamanho];
        n = 0;
    }

    public void inserirInicio(Carro carro) throws Exception {

        if (n >= array.length) {
            throw new Exception("Erro ao inserir!");
        }

        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = carro;

        n++;
    }

    public void inserir(Carro carro, int posicao) throws Exception {

        if (n >= array.length || posicao < 0 || posicao > n) {
            throw new Exception("Erro ao inserir!");
        }

        for (int i = n; i > posicao; i--) {
            array[i] = array[i - 1];
        }

        array[posicao] = carro;

        n++;
    }

    public void inserirFim(Carro carro) throws Exception {

        if (n >= array.length) {
            throw new Exception("Erro ao inserir!");
        }

        array[n] = carro;

        n++;
    }

    public Carro removerInicio() throws Exception {

        if (n == 0) {
            throw new Exception("Erro ao remover!");
        }

        Carro removido = array[0];

        for (int i = 0; i < n - 1; i++) {
            array[i] = array[i + 1];
        }

        array[n - 1] = null;

        n--;

        return removido;
    }

    public Carro remover(int posicao) throws Exception {

        if (n == 0 || posicao < 0 || posicao >= n) {
            throw new Exception("Erro ao remover!");
        }

        Carro removido = array[posicao];

        for (int i = posicao; i < n - 1; i++) {
            array[i] = array[i + 1];
        }

        array[n - 1] = null;

        n--;

        return removido;
    }

    public Carro removerFim() throws Exception {

        if (n == 0) {
            throw new Exception("Erro ao remover!");
        }

        Carro removido = array[n - 1];

        array[n - 1] = null;

        n--;

        return removido;
    }

    public int tamanho() {
        return n;
    }

    public Carro get(int posicao) {
        return array[posicao];
    }

    public void mostrar() {

        for (int i = 0; i < n; i++) {
            System.out.println(array[i].formatar());
        }
    }
}

// -----Funcoes auxiliares---------------------------------------------------------------

public class carroTp2_Q09 {

    public static Carro buscarPorId(ArrayList<Carro> carros, int id) {

        for (Carro carro : carros) {

            if (carro.getId() == id) {
                return carro;
            }
        }

        return null;
    }

    public static void imprimirRemovido(Carro carro) {

        System.out.println(
            "(R)" +
                carro.getMarca() +
                " " +
                carro.getModelo()
        );
    }

    // -----main---------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        ArrayList<Carro> carros = Carro.lerCsv();

        Lista lista = new Lista();

        BufferedReader entrada =
                new BufferedReader(
                        new InputStreamReader(System.in));

        String linha;

        // -----Primeira parte---------------------------------------------------------------

        while ((linha = entrada.readLine()) != null) {

            int id = Integer.parseInt(linha.trim());

            if (id == -1) {
                break;
            }

            Carro carro = buscarPorId(carros, id);

            if (carro != null) {
                lista.inserirFim(carro);
            }
        }

        // -----Segunda parte---------------------------------------------------------------

        int quantidade = Integer.parseInt(
                entrada.readLine().trim()
        );

        for (int i = 0; i < quantidade; i++) {

            linha = entrada.readLine();

            String[] partes = linha.split(" ");

            String comando = partes[0];

            // II = inserir no inicio
            if (comando.equals("II")) {

                int id = Integer.parseInt(partes[1]);

                Carro carro = buscarPorId(carros, id);

                if (carro != null) {
                    lista.inserirInicio(carro);
                }
            }

            // I* = inserir na posicao
            else if (comando.equals("I*")) {

                int posicao = Integer.parseInt(partes[1]);
                int id = Integer.parseInt(partes[2]);

                Carro carro = buscarPorId(carros, id);

                if (carro != null) {
                    lista.inserir(carro, posicao);
                }
            }

            // IF = inserir no fim
            else if (comando.equals("IF")) {

                int id = Integer.parseInt(partes[1]);

                Carro carro = buscarPorId(carros, id);

                if (carro != null) {
                    lista.inserirFim(carro);
                }
            }

            // RI = remover inicio
            else if (comando.equals("RI")) {

                Carro removido = lista.removerInicio();

                imprimirRemovido(removido);
            }

            // R* = remover posicao
            else if (comando.equals("R*")) {

                int posicao = Integer.parseInt(partes[1]);

                Carro removido = lista.remover(posicao);

                imprimirRemovido(removido);
            }

            // RF = remover fim
            else if (comando.equals("RF")) {

                Carro removido = lista.removerFim();

                imprimirRemovido(removido);
            }
        }

        // -----Saida final---------------------------------------------------------------

        lista.mostrar();
    }
}