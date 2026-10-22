/*
26/02 
aluno     >> Kayky Gabriel Marques
matricula >> 845963
*/

package Aeds_2.Tps.Tp_03;

import java.util.*;
import java.io.*;

class Celula<T> {
	public T valor;
	public Celula<T> prox;

	public Celula(T valor) {
		this.valor = valor;
		this.prox = null;
	}

	public T getValor() {
		return this.valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Celula [" + this.valor + "]";
	}
}

/*
 * Ideia de lista com valor generico
 * uma lista capaz de receber valores como int, String, double e afins
 * e matentendo a padronizacao.
 * 
 * Ideia proposta pelo aluno: Kaiky
 */
class Listam<T> {
	private Celula<T> cabeca;
	private int tamanho;

	public Listam() {
		this.cabeca = null;
		this.tamanho = 0;
	}

	public Celula<T> inserirCelula(int x, T valor) {
		if (x < 0 || x > tamanho) {
			throw new IndexOutOfBoundsException("Posição inválida: " + x);
		}

		Celula<T> nova = new Celula<>(valor);

		if (x == 0) {
			nova.prox = cabeca;
			cabeca = nova;
		} else {
			Celula<T> atual = cabeca;
			for (int i = 0; i < x - 1; i++) {
				atual = atual.prox;
			}
			nova.prox = atual.prox;
			atual.prox = nova;
		}

		tamanho++;
		return nova;
	}

	public T removerCelula(int x) {
		if (x < 0 || x >= tamanho) {
			throw new IndexOutOfBoundsException("Posição inválida: " + x);
		}

		Celula<T> removida;

		if (x == 0) {
			removida = cabeca;
			cabeca = cabeca.prox;
		} else {
			Celula<T> atual = cabeca;
			for (int i = 0; i < x - 1; i++) {
				atual = atual.prox;
			}
			removida = atual.prox;
			atual.prox = removida.prox;
		}

		tamanho--;
		return removida.getValor();
	}

	public Celula<T> buscarCelula(int x) {
		if (x < 0 || x >= tamanho) {
			throw new IndexOutOfBoundsException("Posicao invalida: " + x);
		}

		Celula<T> atual = cabeca;
		for (int i = 0; i < x; i++) {
			atual = atual.prox;
		}
		return atual;
	}

	public int tamanho() {
		return tamanho;
	}

	public boolean vazia() {
		return cabeca == null;
	}

}

// classe voltada para criacao e formatacao da Data
// funcao de para criacao e formatacao de data

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

		Data data = new Data(ano, mes, dia);

		return data;

	}

	public String formatarData() {

		String s = String.format("%02d/%02d/%04d", dia, mes, ano);
		return s;
	}

}

// --------------------------------------------

// classe voltada para criacao e formatacao dos carros
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

	public Carro(int id, String marca, String modelo, int ano, String categoria,
			String[] combustivel, int cilindros, double cilindrada,
			String transmissao, String tracao, double consumoCidade,
			double consumoEstrada, double co2, boolean turbo, Data dataRegistro) {
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

	// ------------Gets--------------------------------------------------------
	public Carro() {
	}

	// funcoes destinadas para pegar os valores
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

	public double getCo2() {
		return this.co2;
	}

	public String getTransmissao() {
		return this.transmissao;
	}

	// funcao destinada para alterar valores decimais
	public static double parseDouble(String s) {
		return Double.parseDouble(s.trim());
	}

	public String getMarca() {
		return this.marca;
	}
	// ------------endGets--------------------------------------------------------

	// --------------------------------------------------------------------
	// construtor da classe Carro
	// construtor destinado para receber valores e formatacao

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

		Carro v = new Carro(id, marca, modelo, ano, categoria, combustivel,
				cilindros, cilindrada, transmissao, tracao, consumoCidade,
				consumoEstrada, co2, turbo, dt);
		return v;

	}

	// --------------------------------------------------------------------

	// --------------------------------------------------------------------
	public String formatar() {
		String combustiveis = "[" + String.join(",", combustivel) + "]";
		String cilindradaFormatada = String.format(Locale.US, "%.1f", cilindrada);
		String consumoCidadeFormatado = String.format(Locale.US, "%.2f", consumoCidade);
		String consumoEstradaFormatado = String.format(Locale.US, "%.2f", consumoEstrada);
		String co2Formatado = String.format(Locale.US, "%.1f", co2);
		return "[" + id + " ## " + marca + " ## " + modelo + " ## " + ano
				+ " ## " + categoria + " ## " + combustiveis + " ## " + cilindros
				+ " ## " + cilindradaFormatada + " ## " + transmissao + " ## " + tracao
				+ " ## " + consumoCidadeFormatado + " ## " + consumoEstradaFormatado + " ## " + co2Formatado
				+ " ## " + turbo + " ## " + dataRegistro.formatarData() + "]";
	}
	// --------------------------------------------------------------------

	// --------------------------------------------------------------------
	public static Listam<Carro> lerCsv() throws IOException {
		File arquivo = new File("/tmp/veiculos.csv");
		if (!arquivo.isFile()) {
			arquivo = new File("../../veiculos.csv");
		}
		Listam<Carro> carros = new Listam<>();

		try (BufferedReader leitor = new BufferedReader(new InputStreamReader(
				new FileInputStream(arquivo), "UTF-8"))) {
			leitor.readLine();
			String linha;
			while ((linha = leitor.readLine()) != null) {
				if (!linha.trim().isEmpty()) {
					carros.inserirCelula(carros.tamanho(), parseVeiculos(linha));
				}
			}
		}
		return carros;
	}
	// --------------------------------------------------------------------

}

// -----Heapsort---------------------------------------------------------------
/*
codigo tirado do Github de Aeds 2 e reaproveitado mudando formas e estilo
Autor: Max do Val
*/
class Heapsort {
	private Carro[] array;
	private int n;
	private Listam<Carro> lista;

	/**
	 * Construtor.
	 * @param lista lista de carros que sera ordenada (ordena a propria lista)
	 */
	public Heapsort(Listam<Carro> lista) {
		this.lista = lista;
		this.n = lista.tamanho();
	}

	/**
	 * Criterio: co2 crescente, desempate por transmissao.
	 * retorna <0 se a vem antes de b, >0 se depois, 0 se iguais nos dois criterios
	 */
	private int comparar(Carro a, Carro b) {
		int cmp = Double.compare(a.getCo2(), b.getCo2());
		if (cmp != 0) {
			return cmp;
		}
		return a.getTransmissao().compareToIgnoreCase(b.getTransmissao());
	}

	private void swap(int i, int j) {
		Carro tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}

	/**
	 * Algoritmo de ordenacao Heapsort.
	 */
	public void sort() {
		if (n <= 1) {
			return;
		}

// copia a lista para um vetor ignorando a posicao zero
		array = new Carro[n + 1];
		Celula<Carro> atual = lista.buscarCelula(0);
		for (int i = 1; i <= n; i++) {
			array[i] = atual.getValor();
			atual = atual.prox;
		}

// construcao do heap
		for (int tamHeap = 2; tamHeap <= n; tamHeap++) {
			construir(tamHeap);
		}

// 0rdenacao propriamente dita
		int tamHeap = n;
		while (tamHeap > 1) {
			swap(1, tamHeap--);
			reconstruir(tamHeap);
		}

// devolve o resultado para a lista original
		atual = lista.buscarCelula(0);
		for (int i = 1; i <= n; i++) {
			atual.setValor(array[i]);
			atual = atual.prox;
		}
	}

	public void construir(int tamHeap) {
		for (int i = tamHeap; i > 1 && comparar(array[i], array[i / 2]) > 0; i /= 2) {
			swap(i, i / 2);
		}
	}

	public void reconstruir(int tamHeap) {
		int i = 1;
		while (i <= (tamHeap / 2)) {
			int filho = getMaiorFilho(i, tamHeap);
			if (comparar(array[i], array[filho]) <= 0) {
				swap(i, filho);
				i = filho;
			} else {
				i = tamHeap;
			}
		}
	}

	public int getMaiorFilho(int i, int tamHeap) {
		int filho;
		if (2 * i == tamHeap || comparar(array[2 * i], array[2 * i + 1]) >= 0) {
			filho = 2 * i;
		} else {
			filho = 2 * i + 1;
		}
		return filho;
	}
}
// ---------------------------------------------------------------------------

public class carroTp3_Q03 {

	// -----main---------------------------------------------------------------
	public static void main(String[] args) throws IOException {
		Listam<Carro> carros = Carro.lerCsv();
		Listam<Carro> selecionados = new Listam<>();

		try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
			String linha;
			while ((linha = entrada.readLine()) != null) {
				int id = Integer.parseInt(linha.trim());
				if (id == -1) {
					break;
				}

				for (int i = 0; i < carros.tamanho(); i++) {
					Carro carro = carros.buscarCelula(i).getValor();
					if (carro.getId() == id) {
						selecionados.inserirCelula(selecionados.tamanho(), carro);
						break;
					}
				}
			}
		}

		new Heapsort(selecionados).sort();

		for (int i = 0; i < selecionados.tamanho(); i++) {
			System.out.println(selecionados.buscarCelula(i).getValor().formatar());
		}
	}

	/*
	 * bloco usado somente para testar a resposta com pub.in e pub.out.
	 * para testar por arquivos, descomente este metodo e chama no main.
	 * private static void testarComArquivos(Listam<Carro> carros) throws
	 * IOException {
	 * File arquivoEntrada = new File("Questao07/pub.in");
	 * File arquivoSaida = new File("Questao07/resp.out");
	 * File arquivoOficial = new File("Questao07/pub.out");
	 * 
	 * try (BufferedReader entrada = new BufferedReader(new
	 * FileReader(arquivoEntrada));
	 * BufferedWriter saida = new BufferedWriter(new FileWriter(arquivoSaida))) {
	 * String linha;
	 * while ((linha = entrada.readLine()) != null && !linha.equals("-1")) {
	 * int id = Integer.parseInt(linha.trim());
	 * for (int i = 0; i < carros.tamanho(); i++) {
	 * Carro carro = carros.buscarCelula(i).getValor();
	 * if (carro.getId() == id) {
	 * saida.write(carro.formatar());
	 * saida.newLine();
	 * break;
	 * }
	 * }
	 * }
	 * }
	 * 
	 * System.out.println(compararArquivos(arquivoSaida, arquivoOficial)
	 * ? "resp.out confere com pub.out"
	 * : "resp.out difere de pub.out");
	 * }
	 * 
	 * private static boolean compararArquivos(File primeiro, File segundo) throws
	 * IOException {
	 * try (BufferedReader leitor1 = new BufferedReader(new FileReader(primeiro));
	 * BufferedReader leitor2 = new BufferedReader(new FileReader(segundo))) {
	 * String linha1;
	 * String linha2;
	 * do {
	 * linha1 = leitor1.readLine();
	 * linha2 = leitor2.readLine();
	 * if (!Objects.equals(linha1, linha2)) {
	 * return false;
	 * }
	 * } while (linha1 != null);
	 * return true;
	 * }
	 * }
	 */
}