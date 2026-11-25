/*
Aluno: Kayky Gabriel Marques
Matricula: 845963
*/
// -----Dependencias---------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// -----Data---------------------------------------------------------------

typedef struct
{
    int ano;
    int mes;
    int dia;
} Data;

/* igual ao Data.parseData(String s) do java "ano-mes-dia" ex: 2019-03-05 */
Data parseData(char *s)
{
    Data d;
    char copia[32];
    strcpy(copia, s);

    char *token = strtok(copia, "-");
    d.ano = atoi(token);

    token = strtok(NULL, "-");
    d.mes = atoi(token);

    token = strtok(NULL, "-");
    d.dia = atoi(token);

    return d;
}

/* equivalente ao Data.formatarData(), formato dd/mm/aaaa */
void formatData(Data d, char *buffer)
{
    sprintf(buffer, "%02d/%02d/%04d", d.dia, d.mes, d.ano);
}

// -----Veiculos---------------------------------------------------------------

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

/* funcao equivalente ao Carro.parseVeiculos(String s) do Java.
   aloca um Veiculo* e retorna,
*/
Veiculo *parseVeiculo(char *s)
{
    Veiculo *v = malloc(sizeof(Veiculo));
    if (v == NULL)
        return NULL;

    char copia[1024];
    strcpy(copia, s);

    char *campo = strtok(copia, ",");
    v->id = atoi(campo);

    campo = strtok(NULL, ",");
    strcpy(v->marca, campo);

    campo = strtok(NULL, ",");
    strcpy(v->modelo, campo);

    campo = strtok(NULL, ",");
    v->ano = atoi(campo);

    campo = strtok(NULL, ",");
    strcpy(v->categoria, campo);

    /* combustivel: lista separada por ';' dentro do campo.
    usar strok daria erro por conta q ele guarda apenas o valor de um
    apontador daria erro */
    campo = strtok(NULL, ",");
    char combustivelStr[100];

    strcpy(combustivelStr, campo);
    v->nCombustivel = 0;

    char *ini = combustivelStr;
    char *pv;

    while ((pv = strchr(ini, ';')) != NULL && v->nCombustivel < 5)
    {
        int len = (int)(pv - ini);
        strncpy(v->combustivel[v->nCombustivel], ini, len);
        v->combustivel[v->nCombustivel][len] = '\0';
        v->nCombustivel++;
        ini = pv + 1;
    }
    if (v->nCombustivel < 5)
    {
        strcpy(v->combustivel[v->nCombustivel], ini);
        v->nCombustivel++;
    }

    campo = strtok(NULL, ",");
    v->cilindros = atoi(campo);

    campo = strtok(NULL, ",");
    v->cilindrada = atof(campo);

    campo = strtok(NULL, ",");
    strcpy(v->transmissao, campo);

    campo = strtok(NULL, ",");
    strcpy(v->tracao, campo);

    campo = strtok(NULL, ",");
    v->consumoCidade = atof(campo);

    campo = strtok(NULL, ",");
    v->consumoEstrada = atof(campo);

    campo = strtok(NULL, ",");
    v->co2 = atof(campo);

    campo = strtok(NULL, ",");
    v->turbo = (strcmp(campo, "true") == 0);

    campo = strtok(NULL, ",");
    v->dataRegistro = parseData(campo);

    return v;
}

/* igual ao Carro.formatar() do Java */
void formatVeiculo(Veiculo v, char *buffer)
{
    char combustiveis[100] = "[";
    for (int i = 0; i < v.nCombustivel; i++)
    {
        strcat(combustiveis, v.combustivel[i]);
        if (i < v.nCombustivel - 1)
        {
            strcat(combustiveis, ",");
        }
    }
    strcat(combustiveis, "]");

    char dataFormatada[16];
    formatData(v.dataRegistro, dataFormatada);

    sprintf(buffer,
            "[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
            v.id, v.marca, v.modelo, v.ano, v.categoria, combustiveis,
            v.cilindros, v.cilindrada, v.transmissao, v.tracao,
            v.consumoCidade, v.consumoEstrada, v.co2,
            v.turbo ? "true" : "false", dataFormatada);
}

// -----leitos Csv ---------------------------------------------------------------

// equivalente a lerCsv do Java, so que aqui usamos um vetor de
Veiculo *lerCsv(char *caminhoArquivo, int *n)
{
    FILE *arquivo = fopen(caminhoArquivo, "r");
    if (arquivo == NULL)
    {
        *n = 0;
        return NULL;
    }

    Veiculo *carros = malloc(20000 * sizeof(Veiculo));
    *n = 0;

    char linha[9999];
    fgets(linha, 9999, arquivo); // pula o cabecalho

    while (fgets(linha, 9999, arquivo) != NULL)
    {
        linha[strcspn(linha, "\r\n")] = '\0'; // remove quebra de linha
        if (strlen(linha) == 0)
            continue;

        if (*n >= 20000)
            break;

        Veiculo *v = parseVeiculo(linha);
        carros[*n] = *v;
        free(v);
        (*n)++;
    }

    fclose(arquivo);
    return carros;
}

// -----fila circular com alocacao sequencial---------------------------------

#define CAPACIDADE 5 // capacidade maxima da fila (enunciado)

typedef struct
{
    Veiculo dados[CAPACIDADE];
    int inicio;
    int tamanho;
} FilaCircular;

void inicializarFila(FilaCircular *fila)
{
    fila->inicio = 0;
    fila->tamanho = 0;
}

Veiculo removerFila(FilaCircular *fila)
{
    Veiculo removido = fila->dados[fila->inicio];
    fila->inicio = (fila->inicio + 1) % CAPACIDADE;
    fila->tamanho--;
    return removido;
}

/* se a fila estiver cheia, antes de inserir faz uma remocao
   (imprime o removido, igual a um comando R) */
void inserirFila(FilaCircular *fila, Veiculo veiculo)
{
    if (fila->tamanho == CAPACIDADE)
    {
        Veiculo removido = removerFila(fila);
        printf("(R)%s %s\n", removido.marca, removido.modelo);
    }

    int posicao = (fila->inicio + fila->tamanho) % CAPACIDADE;
    fila->dados[posicao] = veiculo;
    fila->tamanho++;
}

Veiculo *buscarPorId(Veiculo *carros, int n, int id)
{
    for (int i = 0; i < n; i++)
    {
        if (carros[i].id == id)
            return &carros[i];
    }
    return NULL;
}

// ----- main ---------------------------------------------------------------

int main(void)
{
    int n = 0;
    Veiculo *carros = lerCsv("/tmp/veiculos.csv", &n);
    FilaCircular *fila = malloc(sizeof(FilaCircular));
    if (fila == NULL)
    {
        free(carros);
        return 1;
    }
    inicializarFila(fila);

    char linha[64];
    while (fgets(linha, sizeof(linha), stdin) != NULL)
    {
        int id = atoi(linha);
        if (id == -1)
        {
            break;
        }

        Veiculo *veiculo = buscarPorId(carros, n, id);
        if (veiculo != NULL)
        {
            inserirFila(fila, *veiculo);
        }
    }

    if (fgets(linha, sizeof(linha), stdin) != NULL)
    {
        int quantidadeComandos = atoi(linha);

        for (int i = 0; i < quantidadeComandos; i++)
        {
            if (fgets(linha, sizeof(linha), stdin) == NULL)
                break;

            if (linha[0] == 'R')
            {
                Veiculo removido = removerFila(fila);
                printf("(R)%s %s\n", removido.marca, removido.modelo);
            }
            else if (linha[0] == 'I')
            {
                int id = atoi(linha + 2);
                Veiculo *veiculo = buscarPorId(carros, n, id);
                if (veiculo != NULL)
                    inserirFila(fila, *veiculo);
            }
        }
    }

    for (int i = 0; i < fila->tamanho; i++)
    {
        int posicao = (fila->inicio + i) % CAPACIDADE;
        char buffer[1024];
        formatVeiculo(fila->dados[posicao], buffer);
        printf("%s\n", buffer);
    }

    free(fila);
    free(carros);
    return 0;
}