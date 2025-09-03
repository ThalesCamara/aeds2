#include <stdio.h>
#include <string.h>


int somarec(char str[], int length, int soma)
{
    if (length < 0) // Caso base: terminou a string
        return soma;
    soma = soma + str[length] - '0'; // Converte caractere para inteiro e soma
    return somarec(str, length - 1, soma); // Chamada recursiva
}

int main() {
    int TAM = 100;
    char str[TAM];
    fgets(str, TAM, stdin);
    str[strcspn(str, "\n")] = '\0'; // Remove o '\n' do final
    while(strcmp(str, "FIM") != 0) // Para quando a entrada for "FIM"
    {
        int length = strlen(str);
        int soma = somarec(str, length - 1, 0); // Soma os dígitos
        printf("%i\n", soma); // Imprime resultado
        fgets(str, TAM, stdin); 
        str[strcspn(str, "\n")] = '\0'; // Remove o '\n'
    }
    return 0;
}