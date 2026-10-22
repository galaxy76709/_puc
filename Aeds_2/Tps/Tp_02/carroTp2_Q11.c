/*
Aluno: Kayky Gabriel Marques
Matricula: 845963
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_REGISTROS 20000

typedef struct
{
	int ano;
	int mes;
	int dia;
} Data;

Data parseData(char *s)
{
	Data data;
	sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia);
	return data;
}

void formatData(Data data, char *buffer)
{
	sprintf(buffer, "%02d/%02d/%04d", data.dia, data.mes, data.ano);
}

typedef struct
{
	int id;
	char marca[500];
	char modelo[500];
	int ano;
	char categoria[500];
	char combustivel[5][500];
	int nCombustivel;
	int cilindros;
	double cilindrada;
	char transmissao[500];
	char tracao[500];
	double consumoCidade;
	double consumoEstrada;
	double co2;
	bool turbo;
	Data dataRegistro;
} Veiculo;

Veiculo *parseVeiculo(char *s)
{
	Veiculo *veiculo = malloc(sizeof(Veiculo));
	if (veiculo == NULL)
		return NULL;

	char copia[1024];
	strcpy(copia, s);

	char *campo = strtok(copia, ",");
	veiculo->id = atoi(campo);
	strcpy(veiculo->marca, strtok(NULL, ","));
	strcpy(veiculo->modelo, strtok(NULL, ","));
	veiculo->ano = atoi(strtok(NULL, ","));
	strcpy(veiculo->categoria, strtok(NULL, ","));

	char combustivel[500];
	strcpy(combustivel, strtok(NULL, ","));
	veiculo->nCombustivel = 0;
	char *inicio = combustivel;
	while (inicio != NULL && veiculo->nCombustivel < 5)
	{
		char *separador = strchr(inicio, ';');
		if (separador != NULL)
		{
			*separador = '\0';
			strcpy(veiculo->combustivel[veiculo->nCombustivel++], inicio);
			inicio = separador + 1;
		}
		else
		{
			strcpy(veiculo->combustivel[veiculo->nCombustivel++], inicio);
			inicio = NULL;
		}
	}

	veiculo->cilindros = atoi(strtok(NULL, ","));
	veiculo->cilindrada = atof(strtok(NULL, ","));
	strcpy(veiculo->transmissao, strtok(NULL, ","));
	strcpy(veiculo->tracao, strtok(NULL, ","));
	veiculo->consumoCidade = atof(strtok(NULL, ","));
	veiculo->consumoEstrada = atof(strtok(NULL, ","));
	veiculo->co2 = atof(strtok(NULL, ","));
	veiculo->turbo = strcmp(strtok(NULL, ","), "true") == 0;
	veiculo->dataRegistro = parseData(strtok(NULL, ","));

	return veiculo;
}

void formatVeiculo(Veiculo veiculo, char *buffer)
{
	char combustiveis[100] = "[";
	for (int i = 0; i < veiculo.nCombustivel; i++)
	{
		strcat(combustiveis, veiculo.combustivel[i]);
		if (i + 1 < veiculo.nCombustivel)
			strcat(combustiveis, ",");
	}
	strcat(combustiveis, "]");

	char data[16];
	formatData(veiculo.dataRegistro, data);
	sprintf(buffer,
			"[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
			veiculo.id, veiculo.marca, veiculo.modelo, veiculo.ano,
			veiculo.categoria, combustiveis, veiculo.cilindros,
			veiculo.cilindrada, veiculo.transmissao, veiculo.tracao,
			veiculo.consumoCidade, veiculo.consumoEstrada, veiculo.co2,
			veiculo.turbo ? "true" : "false", data);
}

Veiculo *lerCsv(char *caminhoArquivo, int *n)
{
	FILE *arquivo = fopen(caminhoArquivo, "r");
	if (arquivo == NULL)
	{
		*n = 0;
		return NULL;
	}

	Veiculo *carros = malloc(MAX_REGISTROS * sizeof(Veiculo));
	char linha[1024];
	*n = 0;
	fgets(linha, sizeof(linha), arquivo);

	while (*n < MAX_REGISTROS && fgets(linha, sizeof(linha), arquivo) != NULL)
	{
		linha[strcspn(linha, "\r\n")] = '\0';
		if (linha[0] == '\0')
			continue;

		Veiculo *veiculo = parseVeiculo(linha);
		if (veiculo != NULL)
		{
			carros[(*n)++] = *veiculo;
			free(veiculo);
		}
	}

	fclose(arquivo);
	return carros;
}

typedef struct
{
	Veiculo dados[MAX_REGISTROS];
	int tamanho;
} Lista;

void inicializarLista(Lista *lista)
{
	lista->tamanho = 0;
}

void inserirInicio(Lista *lista, Veiculo veiculo)
{
	for (int i = lista->tamanho; i > 0; i--)
		lista->dados[i] = lista->dados[i - 1];
	lista->dados[0] = veiculo;
	lista->tamanho++;
}

void inserirPosicao(Lista *lista, Veiculo veiculo, int posicao)
{
	for (int i = lista->tamanho; i > posicao; i--)
		lista->dados[i] = lista->dados[i - 1];
	lista->dados[posicao] = veiculo;
	lista->tamanho++;
}

void inserirFim(Lista *lista, Veiculo veiculo)
{
	lista->dados[lista->tamanho++] = veiculo;
}

Veiculo removerInicio(Lista *lista)
{
	Veiculo removido = lista->dados[0];
	for (int i = 0; i < lista->tamanho - 1; i++)
		lista->dados[i] = lista->dados[i + 1];
	lista->tamanho--;
	return removido;
}

Veiculo removerPosicao(Lista *lista, int posicao)
{
	Veiculo removido = lista->dados[posicao];
	for (int i = posicao; i < lista->tamanho - 1; i++)
		lista->dados[i] = lista->dados[i + 1];
	lista->tamanho--;
	return removido;
}

Veiculo removerFim(Lista *lista)
{
	return lista->dados[--lista->tamanho];
}

Veiculo *buscarPorId(Veiculo *carros, int n, int id)
{
	for (int i = 0; i < n; i++)
		if (carros[i].id == id)
			return &carros[i];
	return NULL;
}

void imprimirRemovido(Veiculo veiculo)
{
	printf("(R)%s %s\n", veiculo.marca, veiculo.modelo);
}

int main(void)
{
	int n = 0;
	Veiculo *carros = lerCsv("/tmp/veiculos.csv", &n);
	Lista *lista = malloc(sizeof(Lista));
	if (carros == NULL || lista == NULL)
	{
		free(carros);
		free(lista);
		return 1;
	}
	inicializarLista(lista);

	char linha[64];
	while (fgets(linha, sizeof(linha), stdin) != NULL)
	{
		int id = atoi(linha);
		if (id == -1)
			break;

		Veiculo *veiculo = buscarPorId(carros, n, id);
		if (veiculo != NULL)
			inserirFim(lista, *veiculo);
	}

	int quantidade = 0;
	if (fgets(linha, sizeof(linha), stdin) != NULL)
		quantidade = atoi(linha);

	for (int i = 0; i < quantidade; i++)
	{
		if (fgets(linha, sizeof(linha), stdin) == NULL)
			break;

		char comando[4];
		int posicao;
		int id;

		if (sscanf(linha, "%3s %d %d", comando, &posicao, &id) >= 1)
		{
			if (strcmp(comando, "II") == 0 && buscarPorId(carros, n, posicao) != NULL)
				inserirInicio(lista, *buscarPorId(carros, n, posicao));
			else if (strcmp(comando, "IF") == 0 && buscarPorId(carros, n, posicao) != NULL)
				inserirFim(lista, *buscarPorId(carros, n, posicao));
			else if (strcmp(comando, "I*") == 0 && buscarPorId(carros, n, id) != NULL)
				inserirPosicao(lista, *buscarPorId(carros, n, id), posicao);
			else if (strcmp(comando, "RI") == 0)
				imprimirRemovido(removerInicio(lista));
			else if (strcmp(comando, "RF") == 0)
				imprimirRemovido(removerFim(lista));
			else if (strcmp(comando, "R*") == 0)
				imprimirRemovido(removerPosicao(lista, posicao));
		}
	}

	for (int i = 0; i < lista->tamanho; i++)
	{
		char buffer[1024];
		formatVeiculo(lista->dados[i], buffer);
		printf("%s\n", buffer);
	}

	free(lista);
	free(carros);
	return 0;
}
