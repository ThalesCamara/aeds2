#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <ctype.h> // Mantido para isspace (necessário para 'apararEspacos')

#define TAM_LINHA_MAX 4096
#define TAM_CAMPO_MAX 512
#define MAX_ELEM_ARRAY 50

// Estrutura renomeada de 'Game' para 'Jogo'
typedef struct {
    int id;
    char* name;
    char* releaseDate;
    int estimatedOwners;
    float price;
    char** supportedLanguages;
    int supportedLanguagesCount;
    int metacriticScore;
    float userScore;
    int achievements;
    char** publishers;
    int publishersCount;
    char** developers;
    int developersCount;
    char** categories;
    int categoriesCount;
    char** genres;
    int genresCount;
    char** tags;
    int tagsCount;
} Jogo;

// Variáveis globais renomeadas
int g_num_comparacoes = 0;
int g_num_movimentacoes = 0;

// Protótipos de funções renomeadas
void processarLinhaCSV(Jogo* jogo, char* linha);
void exibirJogo(Jogo* jogo);
void liberarJogo(Jogo* jogo);
char* lerCampoCSV(char* linha, int* pos);
char** dividirString(const char* str, char delimitador, int* contagem);
char* apararEspacos(char* str);
char* formatarDataEntrada(char* strData);
void imprimirArrayStrings(char** arr, int contagem);
void ordenarRapido(Jogo* jogos, int esquerda, int direita);
int particionar(Jogo* jogos, int esquerda, int direita);
void trocar(Jogo* a, Jogo* b);
int compararJogos(Jogo* a, Jogo* b);
void escreverLog(const char* matricula, int comp, int mov, double tempo);
void extrairData(const char* strData, int* dia, int* mes, int* ano);

// Lógica Principal
int main() {
    char buffer[TAM_LINHA_MAX];
    const char* caminho_arquivo = "/tmp/games.csv";
    
    FILE* arq = fopen(caminho_arquivo, "r");
    if (arq == NULL) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }
    
    int totalJogos = 0;
    fgets(buffer, TAM_LINHA_MAX, arq); // Pula cabeçalho
    while (fgets(buffer, TAM_LINHA_MAX, arq) != NULL) {
        totalJogos++;
    }
    fclose(arq);

    Jogo* listaCompleta = (Jogo*) malloc(sizeof(Jogo) * totalJogos);
    if (listaCompleta == NULL) {
        printf("Erro de alocação de memória\n");
        return 1;
    }
    
    arq = fopen(caminho_arquivo, "r");
    if (arq == NULL) {
        perror("Erro ao reabrir o arquivo");
        free(listaCompleta);
        return 1;
    }

    fgets(buffer, TAM_LINHA_MAX, arq); // Pula cabeçalho
    int i = 0;
    while (fgets(buffer, TAM_LINHA_MAX, arq) != NULL) {
        processarLinhaCSV(&listaCompleta[i], buffer);
        i++;
    }
    fclose(arq);

    char id_entrada[TAM_CAMPO_MAX];
    Jogo* listaFiltrada = (Jogo*) malloc(sizeof(Jogo) * totalJogos);
    int numFiltrados = 0;
    
    while (fgets(id_entrada, TAM_CAMPO_MAX, stdin) != NULL) {
        id_entrada[strcspn(id_entrada, "\n")] = 0;
        if (strcmp(id_entrada, "FIM") == 0) {
            break;
        }

        int idAlvo = atoi(id_entrada);
        for (i = 0; i < totalJogos; i++) {
            if (listaCompleta[i].id == idAlvo) {
                listaFiltrada[numFiltrados] = listaCompleta[i];
                numFiltrados++;
                break;
            }
        }
    }

    clock_t tempo_inicio = clock();
    ordenarRapido(listaFiltrada, 0, numFiltrados - 1);
    clock_t tempo_fim = clock();
    double tempo_exec = ((double)(tempo_fim - tempo_inicio)) / CLOCKS_PER_SEC;

    for (i = 0; i < numFiltrados; i++) {
        exibirJogo(&listaFiltrada[i]);
    }

    escreverLog("885470", g_num_comparacoes, g_num_movimentacoes, tempo_exec);

    free(listaFiltrada);
    
    for (i = 0; i < totalJogos; i++) {
        liberarJogo(&listaCompleta[i]);
    }
    free(listaCompleta);

    return 0;
}

// Renomeada de 'parseDate' para 'extrairData'
void extrairData(const char* strData, int* dia, int* mes, int* ano) {
    sscanf(strData, "%d/%d/%d", dia, mes, ano);
}

// Função de comparação entre dois jogos (renomeada)
int compararJogos(Jogo* j1, Jogo* j2) {
    g_num_comparacoes++;
    
    int dia1, mes1, ano1;
    int dia2, mes2, ano2;
    
    extrairData(j1->releaseDate, &dia1, &mes1, &ano1);
    extrairData(j2->releaseDate, &dia2, &mes2, &ano2);

    if (ano1 != ano2) {
        return ano1 - ano2;
    }
    if (mes1 != mes2) {
        return mes1 - mes2;
    }
    if (dia1 != dia2) {
        return dia1 - dia2;
    }
    
    // Critério de desempate: ID
    if (j1->id != j2->id) {
        return (j1->id < j2->id) ? -1 : 1;
    }
    return 0;
}

// Função para trocar dois jogos (renomeada)
void trocar(Jogo* a, Jogo* b) {
    Jogo temp = *a;
    *a = *b;
    *b = temp;
    g_num_movimentacoes += 3; // Usa variável global renomeada
}

// Função de partição do Quicksort (renomeada)
int particionar(Jogo* jogos, int esquerda, int direita) {
    Jogo pivo = jogos[direita];
    int idx_menor = esquerda - 1;
    
    for (int idx_atual = esquerda; idx_atual < direita; idx_atual++) {
        if (compararJogos(&jogos[idx_atual], &pivo) <= 0) {
            idx_menor++;
            trocar(&jogos[idx_menor], &jogos[idx_atual]);
        }
    }
    
    trocar(&jogos[idx_menor + 1], &jogos[direita]);
    return idx_menor + 1;
}

// Implementação do Quicksort (renomeada)
void ordenarRapido(Jogo* jogos, int esquerda, int direita) {
    if (esquerda < direita) {
        int pivo_idx = particionar(jogos, esquerda, direita);
        ordenarRapido(jogos, esquerda, pivo_idx - 1);
        ordenarRapido(jogos, pivo_idx + 1, direita);
    }
}

// Função para criar arquivo de log (renomeada)
void escreverLog(const char* matricula, int comp, int mov, double tempo) {
    char nomeArquivo[100];
    sprintf(nomeArquivo, "%s_quicksort.txt", matricula);
    
    FILE* logArq = fopen(nomeArquivo, "w");
    if (logArq != NULL) {
        fprintf(logArq, "%s\t%d\t%d\t%f\n", matricula, comp, mov, tempo);
        fclose(logArq);
    }
}


// Função que preenche uma struct Jogo a partir de uma linha do CSV (renomeada)
void processarLinhaCSV(Jogo* jogo, char* linha) {
    int pos = 0;

    jogo->id = atoi(lerCampoCSV(linha, &pos));
    jogo->name = lerCampoCSV(linha, &pos);
    jogo->releaseDate = formatarDataEntrada(lerCampoCSV(linha, &pos));
    jogo->estimatedOwners = atoi(lerCampoCSV(linha, &pos));
    
    char* precoStr = lerCampoCSV(linha, &pos);
    jogo->price = (strcmp(precoStr, "Free to Play") == 0 || strlen(precoStr) == 0) ? 0.0f : atof(precoStr);
    free(precoStr);

    char* idiomasStr = lerCampoCSV(linha, &pos);
    idiomasStr[strcspn(idiomasStr, "]")] = 0; 
    memmove(idiomasStr, idiomasStr + 1, strlen(idiomasStr)); 
    for(int i = 0; idiomasStr[i]; i++) if(idiomasStr[i] == '\'') idiomasStr[i] = ' ';
    jogo->supportedLanguages = dividirString(idiomasStr, ',', &jogo->supportedLanguagesCount);
    free(idiomasStr);
    
    jogo->metacriticScore = atoi(lerCampoCSV(linha, &pos));
    jogo->userScore = atof(lerCampoCSV(linha, &pos));
    jogo->achievements = atoi(lerCampoCSV(linha, &pos));

    jogo->publishers = dividirString(lerCampoCSV(linha, &pos), ',', &jogo->publishersCount);
    jogo->developers = dividirString(lerCampoCSV(linha, &pos), ',', &jogo->developersCount);
    jogo->categories = dividirString(lerCampoCSV(linha, &pos), ',', &jogo->categoriesCount);
    jogo->genres = dividirString(lerCampoCSV(linha, &pos), ',', &jogo->genresCount);
    jogo->tags = dividirString(lerCampoCSV(linha, &pos), ',', &jogo->tagsCount);
}

// Imprime uma struct Jogo no formato exigido (renomeada)
void exibirJogo(Jogo* jogo) {
    char dataFormatada[12];
    strcpy(dataFormatada, jogo->releaseDate);
    if(dataFormatada[1] == '/') { // Adiciona 0 à esquerda se necessário
        memmove(dataFormatada + 1, dataFormatada, strlen(dataFormatada) + 1);
        dataFormatada[0] = '0';
    }

    char precoStr[16];
    if (jogo->price == 0.0f) {
        strcpy(precoStr, "0.0"); // Mantém lógica original
    } else {
        sprintf(precoStr, "%.2f", jogo->price);
        
        char *ponto = strchr(precoStr, '.');
        if (ponto != NULL) {
            char *fim = precoStr + strlen(precoStr) - 1;
            while (fim > ponto && *fim == '0') {
                *fim = '\0';
                fim--;
            }
            if (fim == ponto) {
                *ponto = '\0';
            }
        }
    }

    char scoreStr[16];
    if (jogo->userScore == 0.0f) {
        strcpy(scoreStr, "0.0"); // Mantém lógica original
    } else {
        sprintf(scoreStr, "%.1f", jogo->userScore);
    }

    printf("=> %d ## %s ## %s ## %d ## %s ## ", 
        jogo->id, jogo->name, dataFormatada, jogo->estimatedOwners, precoStr);
    imprimirArrayStrings(jogo->supportedLanguages, jogo->supportedLanguagesCount);
    printf(" ## %d ## %s ## %d ## ", 
        jogo->metacriticScore == 0 ? -1 : jogo->metacriticScore, 
        scoreStr, 
        jogo->achievements);
    imprimirArrayStrings(jogo->publishers, jogo->publishersCount);
    printf(" ## ");
    imprimirArrayStrings(jogo->developers, jogo->developersCount);
    printf(" ## ");
    imprimirArrayStrings(jogo->categories, jogo->categoriesCount);
    printf(" ## ");
    imprimirArrayStrings(jogo->genres, jogo->genresCount);
    printf(" ## ");
    imprimirArrayStrings(jogo->tags, jogo->tagsCount);
    printf(" ##\n");
}

// Libera a memória de uma única struct Jogo (renomeada)
void liberarJogo(Jogo* jogo) {
    free(jogo->name);
    free(jogo->releaseDate);
    for (int i = 0; i < jogo->supportedLanguagesCount; i++) free(jogo->supportedLanguages[i]);
    free(jogo->supportedLanguages);
    for (int i = 0; i < jogo->publishersCount; i++) free(jogo->publishers[i]);
    free(jogo->publishers);
    for (int i = 0; i < jogo->developersCount; i++) free(jogo->developers[i]);
    free(jogo->developers);
    for (int i = 0; i < jogo->categoriesCount; i++) free(jogo->categories[i]);
    free(jogo->categories);
    for (int i = 0; i < jogo->genresCount; i++) free(jogo->genres[i]);
    free(jogo->genres);
    for (int i = 0; i < jogo->tagsCount; i++) free(jogo->tags[i]);
    free(jogo->tags);
}

// Pega o próximo campo do CSV, tratando aspas (renomeada)
char* lerCampoCSV(char* linha, int* pos) {
    char* campo = (char*) malloc(sizeof(char) * TAM_CAMPO_MAX);
    int i = 0;
    bool entreAspas = false;
    
    if (linha[*pos] == '"') {
        entreAspas = true;
        (*pos)++;
    }
    
    while (linha[*pos] != '\0') {
        if (entreAspas) {
            if (linha[*pos] == '"') {
                (*pos)++;
                break;
            }
        } else {
            if (linha[*pos] == ',') {
                break;
            }
        }
        campo[i++] = linha[(*pos)++];
    }
    
    if (linha[*pos] == ',') {
        (*pos)++;
    }
    
    campo[i] = '\0';
    return campo;
}

// Divide uma string em um array de strings (renomeada)
char** dividirString(const char* str, char delimitador, int* contagem) {
    int contagemInicial = 0;
    for(int i = 0; str[i]; i++) if(str[i] == delimitador) contagemInicial++;
    *contagem = contagemInicial + 1;

    char** resultado = (char**) malloc(sizeof(char*) * (*contagem));
    char buffer[TAM_CAMPO_MAX];
    int str_idx = 0;
    int resultado_idx = 0;

    for (int i = 0; i <= strlen(str); i++) {
        if (str[i] == delimitador || str[i] == '\0') {
            buffer[str_idx] = '\0';
            resultado[resultado_idx] = (char*) malloc(sizeof(char) * (strlen(buffer) + 1));
            strcpy(resultado[resultado_idx], apararEspacos(buffer));
            resultado_idx++;
            str_idx = 0;
        } else {
            buffer[str_idx++] = str[i];
        }
    }
    return resultado;
}

// Remove espaços das bordas (renomeada)
char* apararEspacos(char* str) {
    char *fim;
    while(isspace((unsigned char)*str)) str++;
    if(*str == 0) return str;
    fim = str + strlen(str) - 1;
    while(fim > str && isspace((unsigned char)*fim)) fim--;
    fim[1] = '\0';
    return str;
}

// Formata a data para dd/MM/yyyy (renomeada e lógica interna alterada)
char* formatarDataEntrada(char* strData) {
    char* dataFormatada = (char*) malloc(sizeof(char) * 12);
    char strMes[4] = {0};
    char dia[3] = "01";
    char ano[5] = "0000";

    sscanf(strData, "%s", strMes);

    // Lógica alterada para usar arrays, mas com o mesmo resultado
    const char* mesesAbrev[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    const char* mesesNum[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    char* numMes = "01"; // Padrão "Jan"

    for (int k = 0; k < 12; k++) {
        if (strcmp(strMes, mesesAbrev[k]) == 0) {
            numMes = (char*)mesesNum[k];
            break;
        }
    }

    char* ptr = strData;
    while(*ptr && !isdigit(*ptr)) ptr++;
    if(isdigit(*ptr)) {
        // Tenta ler "dd, yyyy"
        if (sscanf(ptr, "%[^,], %s", dia, ano) != 2) {
            // Se falhar, lê "yyyy" (formato "MMM yyyy")
            sscanf(ptr, "%s", ano);
            strcpy(dia, "01"); // Dia padrão
        }
    }
    
    sprintf(dataFormatada, "%s/%s/%s", dia, numMes, ano);
    free(strData);
    return dataFormatada;
}

// Imprime um array de strings (renomeada)
void imprimirArrayStrings(char** arr, int contagem) {
    printf("[");
    for (int i = 0; i < contagem; i++) {
        printf("%s", arr[i]);
        if (i < contagem - 1) {
            printf(", ");
        }
    }
    printf("]");
}