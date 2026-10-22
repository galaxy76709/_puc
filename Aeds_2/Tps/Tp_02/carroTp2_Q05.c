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
    apontador daria erro 
    */
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


// -----Ordenacao por Counting Sort---------------------------------------------------------------

// funcao aux destinada para pegar o maior valor do arranho 
int getMaior(Veiculo *carros, int n) {
    int biggest = carros[0].cilindros;
 
    for (int i = 0; i < n; i++) {
        if (biggest < carros[i].cilindros) {
            biggest = carros[i].cilindros;
        }
    }
    return biggest;
}


// ideia de funcao retirada do Github de Aeds 2, adaptada para o uso do Tp_Q05
void countingsort(Veiculo *carros, int n) {
    //Array para contar o numero de ocorrencias de cada valor de cilindros
    int tamCount = getMaior(carros, n) + 1;
    int count[tamCount];
    Veiculo *ordenado = malloc(n * sizeof(Veiculo));
 
//inicializar cada posicao do array de contagem
    for (int i = 0; i < tamCount; count[i] = 0, i++);
 
//o count[i] contem o numero de carros com cilindros == i
    for (int i = 0; i < n; count[carros[i].cilindros]++, i++);
 
// count[i] contem o numero de carros com cilindros <= i
    for (int i = 1; i < tamCount; count[i] += count[i-1], i++);
 
//ordenando
    for (int i = n-1; i >= 0; ordenado[count[carros[i].cilindros]-1] = carros[i], count[carros[i].cilindros]--, i--);
 
//copiando para o array original
    for (int i = 0; i < n; carros[i] = ordenado[i], i++);
 
    free(ordenado);
}




// ----- main ---------------------------------------------------------------

int main(void)
{
    int n = 0;
    Veiculo *carros = lerCsv("/tmp/veiculos.csv", &n);
    char linha[64];
    Veiculo *selecionados = malloc(n * sizeof(Veiculo));
    int quantidadeSelecionados = 0;

    while (fgets(linha, sizeof(linha), stdin) != NULL)
    {
        int id = atoi(linha);
        if (id == -1)
        {
            break;
        }

        for (int i = 0; i < n; i++)
        {
            if (carros[i].id == id)
            {
                selecionados[quantidadeSelecionados] = carros[i];
                quantidadeSelecionados++;
                break;
            }
        }
    }

    countingsort(selecionados, quantidadeSelecionados);

    for (int i = 0; i < quantidadeSelecionados; i++)
    {
        char buffer[1024];
        formatVeiculo(selecionados[i], buffer);
        printf("%s\n", buffer);
    }

    free(selecionados);
    free(carros);
    return 0;
}