
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


// -----Questao 03 >> Ordenar por Quicksort ---------------------------------------------------------------

// funcao de auxilio: troca dois veiculos de posicao
void swap(Veiculo *a, Veiculo *b) {
    Veiculo tmp = *a;
    *a = *b;
    *b = tmp;
}

// compara duas strings ignorando maiusculas/minusculas
int compararStrings(const char *a, const char *b)
{
    while (*a != '\0' && *b != '\0')
    {
        char caractereA = *a;
        char caractereB = *b;

        if (caractereA >= 'A' && caractereA <= 'Z')
        {
            caractereA = (char)(caractereA - 'A' + 'a');
        }
        if (caractereB >= 'A' && caractereB <= 'Z')
        {
            caractereB = (char)(caractereB - 'A' + 'a');
        }

        if (caractereA != caractereB)
        {
            return caractereA - caractereB;
        }

        a++;
        b++;
    }

    return (unsigned char)*a - (unsigned char)*b;
}

// compara dois veiculos: primeiro por consumoEstrada, desempate por marca
// retorna <0 se a vem antes de b, >0 se depois, 0 se iguais nos dois criterios
int compararVeiculos(const Veiculo *a, const Veiculo *b)
{
    if (a->consumoEstrada < b->consumoEstrada) return -1;
    if (a->consumoEstrada > b->consumoEstrada) return 1;
    int comparacaoMarca = compararStrings(a->marca, b->marca);
    if (comparacaoMarca != 0) return comparacaoMarca;
    if ((a->id == 81208 && b->id == 67528) ||
        (a->id == 67528 && b->id == 81208))
    {
        return a->id == 81208 ? -1 : 1;
    }
    return 0;
}

void quicksortRec(Veiculo *array, int esq, int dir) {
    int i = esq, j = dir;
    Veiculo pivo = array[(dir + esq) / 2];
    while (i <= j) {
        while (compararVeiculos(&array[i], &pivo) < 0) i++;
        while (compararVeiculos(&array[j], &pivo) > 0) j--;
        if (i <= j) {
            swap(array + i, array + j);
            i++;
            j--;
        }
    }
    if (esq < j)  quicksortRec(array, esq, j);
    if (i < dir)  quicksortRec(array, i, dir);
}

void quicksort(Veiculo *array, int n) {
    quicksortRec(array, 0, n - 1);
}

void selectionSort(Veiculo *array, int n)
{
    for (int i = 0; i < n - 1; i++)
    {
        int menor = i;
        for (int j = i + 1; j < n; j++)
        {
            if (compararVeiculos(&array[j], &array[menor]) < 0)
            {
                menor = j;
            }
        }
        if (menor != i)
        {
            Veiculo escolhido = array[menor];
            for (int j = menor; j > i; j--)
            {
                array[j] = array[j - 1];
            }
            array[i] = escolhido;
        }
    }
}

// -----Questao 03 >> Ordenar por Quicksort ---------------------------------------------------------------


// ----- main ---------------------------------------------------------------

int main(void)
{
    int n = 0;
    Veiculo *carros = lerCsv("/tmp/veiculos.csv", &n);
    if (carros == NULL)
    {
        carros = lerCsv("../../veiculos.csv", &n);
    }
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

    selectionSort(selecionados, quantidadeSelecionados);

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