#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
funcao destinada para retornar o tamanho do arranjo
*/
int tamanhoString(char *s)
{
    int i = 0;

    while (s[i] != '\0')
    {
        i = i + 1;
    }

    return i;
}

void copiarString(char *destino, char *origem)
{
    int i = 0;

    while (origem[i] != '\0')
    {
        destino[i] = origem[i];
        i = i + 1;
    }

    destino[i] = '\0';
}

void swap(char **a, char **b)
{
    char *temp = *a;
    *a = *b;
    *b = temp;
}

/*
 * funcao destinada para ordenacao apartir de valores
 * Selection sort >> ordena do manor com o maior elemento da parte nao ordenada
 * com o primiero nao ordenado
 */
void selectionSort(char **nomes, int X)
{
    for (int i = 0; i < X - 1; i++)
    {
        int idx_min = i;

        for (int j = i + 1; j < X; j++)
        {
            if (strcmp(nomes[j], nomes[idx_min]) < 0)
            {
                idx_min = j;
            }
        }

        swap(&nomes[i], &nomes[idx_min]);
    }
}

int main(void)
{
    int X = 0;
    int plusNum = 0;
    int minusNum = 0;

    scanf("%d", &X);

    char **nomes = malloc(X * sizeof(char *));
    if (nomes == NULL)
    {
        return 1;
    }

    for (int i = 0; i < X; i++)
    {
        char sinal;
        char temp[300]; /* buffer temporario, so existe durante a leitura */

        scanf(" %c %299s", &sinal, temp);

        if (sinal == '+')
        {
            plusNum++;
        }
        else if (sinal == '-')
        {
            minusNum++;
        }

        int tamanho = tamanhoString(temp);
        nomes[i] = malloc(tamanho + 1); /* +1 para o '\0' */
        copiarString(nomes[i], temp);
    }

    selectionSort(nomes, X);

    for (int i = 0; i < X; i++)
    {
        printf("%s\n", nomes[i]);
        free(nomes[i]);
    }

    printf("Se comportaram: %d | Nao se comportaram: %d\n",
           plusNum, minusNum);

    free(nomes);
    return 0;
}