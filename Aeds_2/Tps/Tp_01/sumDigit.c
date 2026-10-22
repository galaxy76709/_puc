// dependencias
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#define null NULL

//------------funcao auxl------------------

/*
   funcao para verificar quando a palavra FIM for escrita
 */

int length(const char str[]) {
    int size = 0;

    while (str[size] != '\0') {
        size++;
    }

    return size;
}

bool theEnd(char str[]) {
    int size = length(str);

    if (str == NULL) {
        return false;
    }

    if (size >= 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M') {
        return true;
    }

    return false;
}

int sumDigits(char str[]) {
    int sum = 0;
    int i = 0;

    while (str[i] != '\0') {
        if (str[i] >= '0' && str[i] <= '9') {
            sum += str[i] - '0';
        }
        i++;
    }

    return sum;
}

//------------funcao principal------------------
/*
funcao prevista para a soma dos digitos de cada numero lido
*/

int main(void) {
    char str[100];
    int values[100];
    int count = 0;

    while (scanf("%99s", str) == 1) {
        if (theEnd(str)) {
            break;
        }

        values[count++] = sumDigits(str);
    }

    for (int i = 0; i < count; i++) {
        if (i < count - 1) {
            printf("%d\n", values[i]);
        } else {
            printf("%d", values[i]);
        }
    }

    return 0;
}
