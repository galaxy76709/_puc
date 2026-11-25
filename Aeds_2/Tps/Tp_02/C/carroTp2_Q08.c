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

/* igual ao Data.parseData(String s) do java "ano-mes-dia" */
Data* parseData(char *s)
{
    Data *d = malloc(sizeof(Data));

    if (d == NULL)
        return NULL;

    char copia[32];
    strcpy(copia, s);

    char *token = strtok(copia, "-");
    d->ano = atoi(token);

    token = strtok(NULL, "-");
    d->mes = atoi(token);

    token = strtok(NULL, "-");
    d->dia = atoi(token);

    return d;
}

/* equivalente ao Data.formatarData(), formato dd/mm/aaaa */
void formatData(Data *d, char *buffer)
{
    sprintf(buffer, "%02d/%02d/%04d", d->dia, d->mes, d->ano);
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
    Data *dataRegistro;

} Veiculo;

/* funcao equivalente ao Carro.parseVeiculos(String s) do Java */
Veiculo* parseVeiculo(char *s)
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

    /* combustivel: lista separada por ';' */
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
void formatVeiculo(Veiculo *v, char *buffer)
{
    char combustiveis[100] = "[";

    for (int i = 0; i < v->nCombustivel; i++)
    {
        strcat(combustiveis, v->combustivel[i]);

        if (i < v->nCombustivel - 1)
            strcat(combustiveis, ",");
    }

    strcat(combustiveis, "]");

    char dataFormatada[16];
    formatData(v->dataRegistro, dataFormatada);

    sprintf(buffer,
            "[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %.2f ## %.2f ## %.1f ## %s ## %s]",
            v->id,
            v->marca,
            v->modelo,
            v->ano,
            v->categoria,
            combustiveis,
            v->cilindros,
            v->cilindrada,
            v->transmissao,
            v->tracao,
            v->consumoCidade,
            v->consumoEstrada,
            v->co2,
            v->turbo ? "true" : "false",
            dataFormatada);
}

// -----leitura Csv---------------------------------------------------------------

Veiculo* lerCsv(char *caminhoArquivo, int *n)
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
        linha[strcspn(linha, "\r\n")] = '\0';

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

// -----Busca por ID---------------------------------------------------------------
//funcao aux destinada para buscar de Id
int buscarPorId(Veiculo *carros, int n, int id, Veiculo *destino)
{
    for (int i = 0; i < n; i++)
    {
        if (carros[i].id == id)
        {
            *destino = carros[i];
            return 1;
        }
    }

    return 0;
}

// -----ordenacao por Selection Sort---------------------------------------------------------------

// ordenacao pela chave modelo, funcao destinada para auxilio para a pesquisna binaria
// questao numero 2 da prova de Aeds 
void selectionsort(Veiculo *carros, int n)
{
    for (int i = 0; i < n - 1; i++)
    {
        int menor = i;

        for (int j = i + 1; j < n; j++)
        {
            if (strcmp(carros[j].modelo, carros[menor].modelo) < 0)
            { menor = j; }
        }

        if (menor != i)
        {
            Veiculo temp = carros[i];
            carros[i] = carros[menor];
            carros[menor] = temp;
        }
    }
}

// -----Pesquisa Binaria---------------------------------------------------------------

// funcao de pesquisa binaria 
bool pesquisaBinaria(Veiculo *carros, int n, char *modelo)
{
    int inicio = 0;
    int fim = n - 1;

    while (inicio <= fim)
    {
        int meio = (inicio + fim) / 2;

        int comparacao = strcmp(carros[meio].modelo, modelo);

        if (comparacao == 0)
        { return true; }
       
        else if (comparacao < 0)
        { inicio = meio + 1; }
       
        else
        {  fim = meio - 1;}
    }

    return false;
}

// ----- main ---------------------------------------------------------------

int main(void)
{
    int n = 0;

    Veiculo *carros = lerCsv("/tmp/veiculos.csv", &n);

    Veiculo *selecionados = malloc(n * sizeof(Veiculo));
    int qtdSelecionados = 0;

    char linha[500];

    while (fgets(linha, sizeof(linha), stdin) != NULL)
    {
        int id = atoi(linha);

        if (id == -1)
        { break; }

        Veiculo v;

        if (buscarPorId(carros, n, id, &v))
        {
            selecionados[qtdSelecionados] = v;
            qtdSelecionados++;
        }
    }

//ordena o arranjo separado pela chave modelo utilizando Selection Sort.
    selectionsort(selecionados, qtdSelecionados);

//recebe os modelos e realiza a pesquisa binaria.
    while (fgets(linha, sizeof(linha), stdin) != NULL)
    {
        linha[strcspn(linha, "\r\n")] = '\0';

        if (strcmp(linha, "FIM") == 0)
        {
            break;
        }

        if (pesquisaBinaria(selecionados, qtdSelecionados, linha))
        {
            printf("SIM\n");
        }
        else
        {
            printf("NAO\n");
        }
    }

    //libera as estruturas Data do vetor original.
    for (int i = 0; i < n; i++)
    { free(carros[i].dataRegistro); }

    free(selecionados);
    free(carros);

    return 0;
}

