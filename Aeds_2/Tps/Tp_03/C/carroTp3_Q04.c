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

// -----Lista simplesmente encadeada---------------------------------------------------------------
// ideia tirada dos meus proprios Tps usando listas Encadeadas !
/* equivalente a classe Celula do Java: guarda um ponteiro para o Veiculo
   e um ponteiro para a proxima celula */
typedef struct Celula
{
    Veiculo *valor;
    struct Celula *prox;
} Celula;

/* equivalente a classe Listam do Java */
typedef struct
{
    Celula *cabeca;
    Celula *cauda; // guarda o ultimo para inserir no fim em O(1)
    int tamanho;
} Lista;

void iniciarLista(Lista *l)
{
    l->cabeca = NULL;
    l->cauda = NULL;
    l->tamanho = 0;
}

/* insere um veiculo no fim da lista. retorna false se faltar memoria */
bool inserirFim(Lista *l, Veiculo *valor)
{
    Celula *nova = malloc(sizeof(Celula));
    if (nova == NULL)
        return false;

    nova->valor = valor;
    nova->prox = NULL;

    if (l->cabeca == NULL)
    {
        l->cabeca = nova;
    }
    else
    {
        l->cauda->prox = nova;
    }
    l->cauda = nova;
    l->tamanho++;
    return true;
}

/* libera so as celulas (os Veiculos continuam existindo).
   usada na lista de selecionados, que apenas aponta para veiculos de outra lista */
void liberarCelulas(Lista *l)
{
    Celula *atual = l->cabeca;
    while (atual != NULL)
    {
        Celula *prox = atual->prox;
        free(atual);
        atual = prox;
    }
    iniciarLista(l);
}

/* libera as celulas e tambem os Veiculos que elas guardam */
void liberarTudo(Lista *l)
{
    Celula *atual = l->cabeca;
    while (atual != NULL)
    {
        Celula *prox = atual->prox;
        free(atual->valor);
        free(atual);
        atual = prox;
    }
    iniciarLista(l);
}

// -----leitos Csv ---------------------------------------------------------------

// equivalente a lerCsv do Java: devolve uma lista encadeada (sem limite de 20000)
Lista lerCsv(char *caminhoArquivo)
{
    Lista carros;
    iniciarLista(&carros);

    FILE *arquivo = fopen(caminhoArquivo, "r");
    if (arquivo == NULL)
    {
        return carros;
    }

    char linha[9999];
    fgets(linha, 9999, arquivo); // pula o cabecalho

    while (fgets(linha, 9999, arquivo) != NULL)
    {
        linha[strcspn(linha, "\r\n")] = '\0'; // remove quebra de linha
        if (strlen(linha) == 0)
            continue;

        Veiculo *v = parseVeiculo(linha);
        if (v == NULL)
            break;

        if (!inserirFim(&carros, v))
        {
            free(v);
            break;
        }
    }

    fclose(arquivo);
    return carros;
}

// -----Questao 04 >> Ordenar por Selecao ---------------------------------------------------------------

// funcao de auxilo para a questao numero 3
// troca os ponteiros guardados nas celulas (nao precisa copiar o Veiculo inteiro)
void swap(Celula *a, Celula *b)
{
    Veiculo *tmp = a->valor;
    a->valor = b->valor;
    b->valor = tmp;
}

// compara modelos ignorando diferencas entre maiusculas e minusculas
int compararModelos(const char *a, const char *b)
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

//funcao destinada a ordenacao por selecao na lista encadeada
void selectionSort(Lista *lista)
{
    for (Celula *i = lista->cabeca; i != NULL && i->prox != NULL; i = i->prox)
    {
        Celula *menor = i;

        for (Celula *j = i->prox; j != NULL; j = j->prox)
        {
            if (compararModelos(j->valor->modelo, menor->valor->modelo) < 0)
            {
                menor = j;
            }
        }

        if (menor != i)
        {
            swap(i, menor);
        }
    }
}

// -----Questao 04 >> Ordenar por Selecao ---------------------------------------------------------------


// ----- main ---------------------------------------------------------------

int main(void)
{
    Lista carros = lerCsv("/tmp/veiculos.csv");
    if (carros.tamanho == 0)
    {
        carros = lerCsv("../veiculos.csv");
    }
    Lista selecionados;
    iniciarLista(&selecionados);

    char linha[64];

    while (fgets(linha, sizeof(linha), stdin) != NULL)
    {
        int id = atoi(linha);
        if (id == -1)
        {
            break;
        }

        for (Celula *c = carros.cabeca; c != NULL; c = c->prox)
        {
            if (c->valor->id == id)
            {
                inserirFim(&selecionados, c->valor);
                break;
            }
        }
    }

    selectionSort(&selecionados);

    for (Celula *c = selecionados.cabeca; c != NULL; c = c->prox)
    {
        char buffer[1024];
        formatVeiculo(*(c->valor), buffer);
        printf("%s\n", buffer);
    }

    liberarCelulas(&selecionados); // so as celulas: os Veiculos pertencem a lista carros
    liberarTudo(&carros);
    return 0;
}