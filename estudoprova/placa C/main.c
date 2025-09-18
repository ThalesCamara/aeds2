#include <stdio.h>
#include <stdlib.h>

int main() {
    char str[9];
    fgets(str, 9, stdin);

    int len = 0;
    while (str[len] != '\0' && str[len] != '\n') {
        len++;
    }

    // se terminar no '\n', troca por '\0'
    if (str[len] == '\n') {
        str[len] = '\0';
    }

    int tipo = 0;
    if (len == 8 && str[0] >= 'A' && str[0] <= 'Z' && str[1] >= 'A' && str[1] <= 'Z' && str[2] >= 'A' && str[2] <= 'Z' && str[3] == '-' && str[4] >= '0' && str[4] <= '9' && str[5] >= '0' && str[5] <= '9' && str[6] >= '0' && str[6] <= '9' && str[7] >= '0' && str[7] <= '9') {
        tipo = 1;
    }
    else if (len == 7 && str[0] >= 'A' && str[0] <= 'Z' && str[1] >= 'A' && str[1] <= 'Z' && str[2] >= 'A' && str[2] <= 'Z' && str[3] >= '0' && str[3] <= '9' && str[4] >= 'A' && str[4] <= 'Z' && str[5] >= '0' && str[5] <= '9' && str[6] >= '0' && str[6] <= '9') {
        tipo = 2;
    }

    printf("%i", tipo);
    return 0;
}
