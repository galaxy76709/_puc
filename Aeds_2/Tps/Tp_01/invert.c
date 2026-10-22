#include <stdio.h>
#include <stdbool.h>
#include <string.h>

bool theEnd(const char *str) {
    if (str == NULL) {
        return false;
    }

    return strcmp(str, "FIM") == 0;
}

void invertString(const char *str, char *copy) {
    if (str == NULL || copy == NULL) {
        return;
    }

    int size = (int) strlen(str);
    for (int i = 0; i < size; i++) {
        copy[size - 1 - i] = str[i];
    }
    copy[size] = '\0';
}

int main(void) {
    char str[1000];
    char copy[1000];

    while (fgets(str, sizeof(str), stdin) != NULL) {
        size_t len = strlen(str);

        while (len > 0 && (str[len - 1] == '\n' || str[len - 1] == '\r')) {
            str[--len] = '\0';
        }

        if (theEnd(str)) {
            break;
        }

        invertString(str, copy);
        printf("%s\n", copy);
    }

    return 0;
}
