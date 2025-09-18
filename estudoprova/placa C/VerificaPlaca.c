#include <stdio.h>
#include <ctype.h>
#include <string.h>

int main() {
    char placa[16];
    int resultado = 0;

    fgets(placa, sizeof(placa), stdin);

    int len = strlen(placa);
    if (len > 0 && placa[len - 1] == '\n') {
        placa[len - 1] = '\0';
        len--;
    }

    if (len == 8) {
        if (isalpha(placa[0]) &&
            isalpha(placa[1]) &&
            isalpha(placa[2]) &&
            placa[3] == '-' &&
            isdigit(placa[4]) &&
            isdigit(placa[5]) &&
            isdigit(placa[6]) &&
            isdigit(placa[7])) {
            resultado = 1;
        }
    } else if (len == 7) {
        if (isalpha(placa[0]) &&
            isalpha(placa[1]) &&
            isalpha(placa[2]) &&
            isdigit(placa[3]) &&
            isalpha(placa[4]) &&
            isdigit(placa[5]) &&
            isdigit(placa[6])) {
            resultado = 2;
        }
    }

    printf("%d\n", resultado);
    return 0;
}