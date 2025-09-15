#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>

// --- FUNÇÕES RECURSIVAS ---

bool eSoVogaisRec(char string[], int i) {
    // Condição de parada: se o fim da string for alcançado, todos os caracteres foram vogais.
    if (string[i] == '\0') {
        return true;
    }

    char c = tolower(string[i]);
    // Etapa da recursão: caso o caractere atual não seja vogal, a verificação falha.
    if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u') {
        return false;
    }
    // Se o caractere for vogal, continua analisando o restante da string.
    return eSoVogaisRec(string, i + 1);
}

bool eSoConsoantesRec(char string[], int i) {
    // Condição de término: ao atingir o fim da string, todos os caracteres foram consoantes.
    if (string[i] == '\0') {
        return true;
    }

    char c = tolower(string[i]);
    // Etapa da recursão: se o caractere não for uma letra ou for vogal, a checagem falha.
    if (!('a' <= c && c <= 'z') || (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')) {
        return false;
    }
    return eSoConsoantesRec(string, i + 1);
}

bool eSoInteirosRec(char string[], int i) {
    // Condição de encerramento: ao chegar ao final, todos os caracteres foram dígitos.
    if (string[i] == '\0') {
        return true;
    }

    // Etapa recursiva: se algum caractere não for número, a verificação não passa.
    if (!isdigit(string[i])) {
        return false;
    }
    return eSoInteirosRec(string, i + 1);
}

// Para analisar números reais, é necessário um parâmetro extra para contar quantos pontos já apareceram.
bool eSoReaisRec(char string[], int i, int pontoCount) {
    // Condição final: se chegou ao término da string, a validação deu certo.
    if (string[i] == '\0') {
        return true;
    }

    // Etapa recursiva: se encontrar ponto ou vírgula, atualiza o contador.
    if (string[i] == '.' || string[i] == ',') {
        // Se já houver ponto ou vírgula antes, a string não é válida.
        if (pontoCount > 0) {
            return false;
        }
        return eSoReaisRec(string, i + 1, pontoCount + 1);
    }
    // Se não for um dígito (nem ponto/vírgula), a checagem falha.
    if (!isdigit(string[i])) {
        return false;
    }
    return eSoReaisRec(string, i + 1, pontoCount);
}

// --- FUNÇÕES "WRAPPER" PARA INICIAR A RECURSÃO ---

bool eSoVogais(char string[]) { return eSoVogaisRec(string, 0); }
bool eSoConsoantes(char string[]) { return eSoConsoantesRec(string, 0); }
bool eSoInteiros(char string[]) { return eSoInteirosRec(string, 0); }
bool eSoReais(char string[]) { return eSoReaisRec(string, 0, 0); }


int main() {
    char str[1000];
    fgets(str, sizeof(str), stdin);
    // Retira o caractere de quebra de linha ('\n') capturado pelo fgets.
    str[strcspn(str, "\n")] = '\0';

    // O laço continua rodando até que a entrada seja a palavra "FIM".
    while(strcmp(str, "FIM") != 0) {
        printf(eSoVogais(str) ? "SIM " : "NAO ");
        printf(eSoConsoantes(str) ? "SIM " : "NAO ");
        printf(eSoInteiros(str) ? "SIM " : "NAO ");
        printf(eSoReais(str) ? "SIM\n" : "NAO\n");

        fgets(str, sizeof(str), stdin);
        // Remove o caractere '\n' da próxima leitura.
        str[strcspn(str, "\n")] = '\0';
    }
    return 0;
}
