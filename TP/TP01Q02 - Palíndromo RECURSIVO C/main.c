#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// Função recursiva para verificar se é palíndromo
bool descobrirPalindromo(char str[], int TAM, int i) {
    if (i >= TAM) { // Caso base
        return true;
    }
    if (str[i] != str[TAM]) { // Se os caracteres "espelhados" forem diferentes, não é palíndromo
        return false;
    } else {
        return descobrirPalindromo(str, TAM - 1, i + 1); // Chamada recursiva
    }
}

int main() {
    char str[10000];
    while (fgets(str, sizeof(str), stdin) != NULL) {
        int TAM = strlen(str);
        int i = 0;
        // Remove o '\n' do final da string
        if (TAM > 0 && str[TAM - 1] == '\n') {
            str[--TAM] = '\0';
        }
        bool palindromo = descobrirPalindromo(str, TAM - 1, i);
        if (palindromo == 1) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }
    return 0;
}