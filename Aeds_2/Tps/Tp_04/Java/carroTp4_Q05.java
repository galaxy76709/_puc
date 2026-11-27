/*
27/11
aluno     >> Kayky Gabriel Marques
matricula >> 845963


*/
package Aeds_2.Tps.Tp_04.Java;

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
Ideia de lista com valor generico 
uma lista capaz de receber valores como int, String, double e afins
e matentendo a padronizacao. 

Ideia proposta pelo aluno: Kaiky
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
            throw new IndexOutOfBoundsException("Posição inválida: " + x);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Celula<T> atual = cabeca;
        while (atual != null) {
            sb.append(atual.getValor());
            if (atual.prox != null) sb.append(", ");
            atual = atual.prox;
        }
        sb.append("]");
        return sb.toString();
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

	public Carro(){ }

// funcoes destinadas para pegar os valores 
	public int getId() {
		return this.id;
	}

	public int getAno() {
		return this.ano ;
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

	public String getModelo() {
		return this.modelo;
	}

	// funcao destinada para alterar valores decimais
	public static double parseDouble(String s) {
		return Double.parseDouble(s.trim());
	}

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

}

// -----Arvore Binaria---------------------------------------------------------------

// no da arvore: guarda um carro e os ponteiros para os filhos esquerdo e direito
class No {
	public Carro elemento;
	public No esq;
	public No dir;

	public No(Carro elemento) {
		this.elemento = elemento;
		this.esq = null;
		this.dir = null;
	}
}

// arvore binaria de pesquisa: a chave de pesquisa e o atributo modelo
class ArvoreBinaria {
	private No raiz;

	public ArvoreBinaria() {
		this.raiz = null;
	}

	// criterio de comparacao lexicografica das chaves (modelo), sensivel a caixa
	private int comparar(String a, String b) {
		int limite = a.length();
		if (b.length() < limite) {
			limite = b.length();
		}
		for (int i = 0; i < limite; i++) {
			char caractereA = a.charAt(i);
			char caractereB = b.charAt(i);
			if (caractereA != caractereB) {
				return caractereA - caractereB;
			}
		}
		return a.length() - b.length();
	}

	/**
	 * Insere o carro na arvore.
	 * Nao insere se o modelo ja estiver na arvore.
	 * @return true se inseriu, false se a chave ja existia
	 */
	public boolean inserir(Carro carro) {
		No novo = new No(carro);

		if (raiz == null) {
			raiz = novo;
			return true;
		}

		No atual = raiz;
		while (true) {
			int cmp = comparar(carro.getModelo(), atual.elemento.getModelo());

			if (cmp == 0) {
				return false;
			} else if (cmp < 0) {
				if (atual.esq == null) {
					atual.esq = novo;
					return true;
				}
				atual = atual.esq;
			} else {
				if (atual.dir == null) {
					atual.dir = novo;
					return true;
				}
				atual = atual.dir;
			}
		}
	}

	/**
	 * Pesquisa o modelo na arvore.
	 * @return o caminho de ponteiros usado (raiz, esq, dir) seguido de SIM ou NAO
	 */
	public String pesquisar(String modelo) {
		StringBuilder caminho = new StringBuilder("raiz");
		No atual = raiz;

		while (atual != null) {
			int cmp = comparar(modelo, atual.elemento.getModelo());

			if (cmp == 0) {
				return caminho + " SIM";
			} else if (cmp < 0) {
				caminho.append(" esq");
				atual = atual.esq;
			} else {
				caminho.append(" dir");
				atual = atual.dir;
			}
		}
		return caminho + " NAO";
	}

	// caminhamento em ordem (esquerda, raiz, direita): mostra os registros ordenados por modelo
	public void caminharCentral() {
		caminharCentral(raiz);
	}

	private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq);
			System.out.println(i.elemento.formatar());
			caminharCentral(i.dir);
		}
	}
}
// ---------------------------------------------------------------------------

public class carroTp4_Q05 {
	public static void main(String[] args) throws IOException {
		Listam<Carro> carros = Carro.lerCsv();
		ArvoreBinaria arvore = new ArvoreBinaria();

		try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
			String linha;

// primeira parte da entrada: ids dos registros que serao inseridos na arvore
			while ((linha = entrada.readLine()) != null) {
				int id = Integer.parseInt(linha.trim());
				if (id == -1) {
					break;
				}

				Celula<Carro> atual = carros.vazia() ? null : carros.buscarCelula(0);
				while (atual != null) {
					Carro carro = atual.getValor();
					if (carro.getId() == id) {
						arvore.inserir(carro);
						break;
					}
					atual = atual.prox;
				}
			}

// segunda parte da entrada: modelos a serem pesquisados, ate a palavra FIM
			while ((linha = entrada.readLine()) != null) {
				if (linha.trim().equals("FIM")) {
					break;
				}
				System.out.println(arvore.pesquisar(linha));
			}
		}

// ao final, mostra os registros da arvore em ordem
		arvore.caminharCentral();
	}

/*bloco usado somente para testar a resposta com pub.in e pub.out.
para testar por arquivos, descomente este metodo e chama no main.
	  private static void testarComArquivos(Listam<Carro> carros) throws IOException {
	      File arquivoEntrada = new File("Questao01/pub.in");
	      File arquivoSaida = new File("Questao01/resp.out");
	      File arquivoOficial = new File("Questao01/pub.out");
	 
	      try (BufferedReader entrada = new BufferedReader(new FileReader(arquivoEntrada));
	           BufferedWriter saida = new BufferedWriter(new FileWriter(arquivoSaida))) {
	          String linha;
	          while ((linha = entrada.readLine()) != null && !linha.equals("-1")) {
	              int id = Integer.parseInt(linha.trim());
	              for (int i = 0; i < carros.tamanho(); i++) {
	                  Carro carro = carros.buscarCelula(i).getValor();
	                  if (carro.getId() == id) {
	                      saida.write(carro.formatar());
	                      saida.newLine();
	                      break;
	                  }
	              }
	          }
	      }
	 
	      System.out.println(compararArquivos(arquivoSaida, arquivoOficial)
	              ? "resp.out confere com pub.out"
	              : "resp.out difere de pub.out");
	  }
	 
	  private static boolean compararArquivos(File primeiro, File segundo) throws IOException {
	      try (BufferedReader leitor1 = new BufferedReader(new FileReader(primeiro));
	           BufferedReader leitor2 = new BufferedReader(new FileReader(segundo))) {
	          String linha1;
	          String linha2;
	          do {
	              linha1 = leitor1.readLine();
	              linha2 = leitor2.readLine();
	              if (!Objects.equals(linha1, linha2)) {
	                  return false;
	              }
	          } while (linha1 != null);
	          return true;
	      }
	  }
*/
}