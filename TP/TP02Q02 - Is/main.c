#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>

// Verifica se a string contém apenas vogais
bool compostaVogais(char string[]) {
    for (int i = 0; string[i] != '\0'; i++) {
        if (tolower(string[i]) != 'a' && tolower(string[i]) != 'e' &&
            tolower(string[i]) != 'i' && tolower(string[i]) != 'o' &&
            tolower(string[i]) != 'u') {
            return false;
        }
    }
    return true;
}

// Verifica se a string contém apenas consoantes
bool compostaConsoantes(char string[]) {
    for (int i = 0; string[i] != '\0'; i++) {
        char c = tolower(string[i]);
        if (!('a' <= c && c <= 'z')) {
            return false;
        } else if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            return false;
        }
    }
    return true;
}

// Verifica se a string é um número inteiro
bool compostaInteiros(char string[]) {
    for (int i = 0; string[i] != '\0'; i++) {
        if (!isdigit(string[i])) {
            return false;
        }
    }
    return true;
}

// Verifica se a string é um número real
bool compostaReais(char string[]) {
    int ponto = 0;
    for (int i = 0; string[i] != '\0'; i++) {
        if (string[i] == '.' || string[i] == ',') {
            ponto++;
            if (ponto > 1) {
                return false;
            }
        } else if (!isdigit(string[i])) {
            return false;
        }
    }
    return true;
}

int main() {
    char str[1000];
    fgets(str, sizeof(str), stdin);
    str[strcspn(str, "\n")] = '\0'; // Remove o '\n' do final da string

    while(strcmp(str, "FIM") != 0) {
        // Verifica e imprime se a string contém apenas vogais
        if (compostaVogais(str) == true) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }
        // Verifica e imprime se a string contém apenas consoantes
        if (compostaConsoantes(str) == true) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }
        // Verifica e imprime se a string é um número inteiro
        if (compostaInteiros(str) == true) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }
        // Verifica e imprime se a string é um número real
        if (compostaReais(str) == true) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = '\0'; // Remove o '\n' do final da string
    }
    return 0;
}
