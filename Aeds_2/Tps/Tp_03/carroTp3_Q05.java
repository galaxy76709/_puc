/*
26/02 
aluno     >> Kayky Gabriel Marques
matricula >> 845963
*/
package Aeds_2.Tps.Tp_03;
import java.util.*;
import java.io.*;

/*
 * celula da lista DUPLAMENTE encadeada:
 * alem do prox (proxima celula), agora tem o ant (celula anterior)
 */
class Celula<T> {
	public T valor;
	public Celula<T> prox;
	public Celula<T> ant; // add ant para a lista duplamente

	public Celula(T valor) {
		this.valor = valor;
		this.prox = null;
		this.ant = null;
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
 * Agora duplamente encadeada: guarda a cabeca e a cauda, e cada celula
 * aponta para a anterior e para a proxima.
 * 
 * Ideia proposta pelo aluno: Kaiky
 */
class Listam<T> {
	private Celula<T> cabeca;
	private Celula<T> cauda;
	private int tamanho;

	public Listam() {
		this.cabeca = null;
		this.cauda = null;
		this.tamanho = 0;
	}

	public Celula<T> getCabeca() {
		return this.cabeca;
	}

	public Celula<T> getCauda() {
		return this.cauda;
	}

	public Celula<T> inserirCelula(int x, T valor) {
		if (x < 0 || x > tamanho) {
			throw new IndexOutOfBoundsException("Posição inválida: " + x);
		}

		Celula<T> nova = new Celula<>(valor);

		if (tamanho == 0) {
			// lista vazia: a nova celula e cabeca e cauda ao mesmo tempo
			cabeca = nova;
			cauda = nova;
		} else if (x == 0) {
			// inicio
			nova.prox = cabeca;
			cabeca.ant = nova;
			cabeca = nova;
		} else if (x == tamanho) {
			// fim (O(1) por causa da cauda)
			nova.ant = cauda;
			cauda.prox = nova;
			cauda = nova;
		} else {
			// meio: entra antes da celula que esta hoje na posicao x
			Celula<T> atual = buscarCelula(x);
			nova.ant = atual.ant;
			nova.prox = atual;
			atual.ant.prox = nova;
			atual.ant = nova;
		}

		tamanho++;
		return nova;
	}

	public T removerCelula(int x) {
		if (x < 0 || x >= tamanho) {
			throw new IndexOutOfBoundsException("Posição inválida: " + x);
		}

		Celula<T> removida = buscarCelula(x);

		if (removida.ant != null) {
			removida.ant.prox = removida.prox;
		} else {
			cabeca = removida.prox;
		}

		if (removida.prox != null) {
			removida.prox.ant = removida.ant;
		} else {
			cauda = removida.ant;
		}

		tamanho--;
		return removida.getValor();
	}

	// percorre a partir da extremidade mais proxima da posicao x
	public Celula<T> buscarCelula(int x) {
		if (x < 0 || x >= tamanho) {
			throw new IndexOutOfBoundsException("Posicao invalida: " + x);
		}

		Celula<T> atual;
		if (x < tamanho / 2) {
			atual = cabeca;
			for (int i = 0; i < x; i++) {
				atual = atual.prox;
			}
		} else {
			atual = cauda;
			for (int i = tamanho - 1; i > x; i--) {
				atual = atual.ant;
			}
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

// -----Quicksort---------------------------------------------------------------
/*
codigo tirado do Github de Aeds 2 e reaproveitado mudando formas e estilo
Autor: Max do Val

Adaptado para lista duplamente encadeada: os indices i e j do quicksort
original viram ponteiros de celula (ci e cj). O ci anda para frente (prox)
e o cj anda para tras (ant), por isso a lista precisa ser duplamente encadeada.
Os indices continuam sendo contados para saber quando i e j se cruzam.
*/
class Quicksort {
	private Listam<Carro> lista;
	private int n;

	/**
	 * construtor.
	 * @param lista lista de carros que sera ordenada (ordena a propria lista)
	 */
	public Quicksort(Listam<Carro> lista) {
		this.lista = lista;
		this.n = lista.tamanho();
	}

	/**
	 * criterio: consumoEstrada crescente, desempate por marca.
	 * retorna <0 se a vem antes de b, >0 se depois, 0 se iguais nos dois criterios
	 */
	private int comparar(Carro a, Carro b) {
		int cmp = Double.compare(a.getConsumoEstrada(), b.getConsumoEstrada());
		if (cmp != 0) {
			return cmp;
		}
		return a.getMarca().compareToIgnoreCase(b.getMarca());
	}

	// troca os valores guardados em duas celulas
	private void swap(Celula<Carro> a, Celula<Carro> b) {
		Carro tmp = a.getValor();
		a.setValor(b.getValor());
		b.setValor(tmp);
	}

	/**
	 * esq e dir sao os indices; cEsq e cDir sao as celulas desses indices.
	 */
	private void quicksortRec(Celula<Carro> cEsq, Celula<Carro> cDir, int esq, int dir) {
		int i = esq, j = dir;
		Celula<Carro> ci = cEsq, cj = cDir;

		// pivo = valor do meio: anda (dir-esq)/2 celulas a partir de cEsq
		Celula<Carro> cPivo = cEsq;
		for (int k = 0; k < (dir - esq) / 2; k++) {
			cPivo = cPivo.prox;
		}
		Carro pivo = cPivo.getValor();

		while (i <= j) {
			while (comparar(ci.getValor(), pivo) < 0) {
				ci = ci.prox;
				i++;
			}
			while (comparar(cj.getValor(), pivo) > 0) {
				cj = cj.ant;
				j--;
			}
			if (i <= j) {
				swap(ci, cj);
				ci = ci.prox;
				i++;
				cj = cj.ant;
				j--;
			}
		}
		if (esq < j) {
			quicksortRec(cEsq, cj, esq, j);
		}
		if (i < dir) {
			quicksortRec(ci, cDir, i, dir);
		}
	}

	/**
	 * Algoritmo de ordenacao Quicksort.
	 */
	public void sort() {
		if (n <= 1) {
			return;
		}
		quicksortRec(lista.getCabeca(), lista.getCauda(), 0, n - 1);
	}
}
// ---------------------------------------------------------------------------

public class carroTp3_Q05 {

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

				for (Celula<Carro> c = carros.getCabeca(); c != null; c = c.prox) {
					if (c.getValor().getId() == id) {
						selecionados.inserirCelula(selecionados.tamanho(), c.getValor());
						break;
					}
				}
			}
		}

		new Quicksort(selecionados).sort();

		for (Celula<Carro> c = selecionados.getCabeca(); c != null; c = c.prox) {
			System.out.println(c.getValor().formatar());
		}
	}

	
}