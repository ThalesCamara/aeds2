#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_SHOWS 500
#define MAX_LEN 100

typedef struct {
    int id;
    char name[MAX_LEN];
} Show;

typedef struct {
    Show *array[MAX_SHOWS];
    int count;
} ShowList;

// ---------- Funções de criação ----------
ShowList* createList() {
    ShowList *list = (ShowList*) malloc(sizeof(ShowList));
    list->count = 0;
    return list;
}

Show* createShow(int id, const char *name) {
    Show *s = (Show*) malloc(sizeof(Show));
    s->id = id;
    strcpy(s->name, name);
    return s;
}

// ---------- Inserções ----------
void insertBegin(ShowList *list, Show *item) {
    if (list->count >= MAX_SHOWS) return;
    for (int i = list->count; i > 0; i--)
        list->array[i] = list->array[i - 1];
    list->array[0] = item;
    list->count++;
}

void insertEnd(ShowList *list, Show *item) {
    if (list->count >= MAX_SHOWS) return;
    list->array[list->count++] = item;
}

void insertAt(ShowList *list, Show *item, int pos) {
    if (list->count >= MAX_SHOWS || pos < 0 || pos > list->count) return;
    for (int i = list->count; i > pos; i--)
        list->array[i] = list->array[i - 1];
    list->array[pos] = item;
    list->count++;
}

// ---------- Remoções ----------
Show* removeBegin(ShowList *list) {
    if (list->count == 0) return NULL;
    Show *removed = list->array[0];
    for (int i = 0; i < list->count - 1; i++)
        list->array[i] = list->array[i + 1];
    list->count--;
    return removed;
}

Show* removeEnd(ShowList *list) {
    if (list->count == 0) return NULL;
    return list->array[--list->count];
}

Show* removeAt(ShowList *list, int pos) {
    if (list->count == 0 || pos < 0 || pos >= list->count) return NULL;
    Show *removed = list->array[pos];
    for (int i = pos; i < list->count - 1; i++)
        list->array[i] = list->array[i + 1];
    list->count--;
    return removed;
}

// ---------- Utilitários ----------
void printRemoved(Show *s) {
    if (s != NULL)
        printf("(R) %s\n", s->name);
}

void printList(ShowList *list) {
    for (int i = 0; i < list->count; i++)
        printf("[%d] %d ## %s\n", i, list->array[i]->id, list->array[i]->name);
}

// ---------- Leitura de arquivo ----------
Show* loadShowById(const char *idStr) {
    int id = atoi(idStr);
    FILE *file = fopen("/tmp/shows.csv", "r");
    if (!file) return NULL;

    char line[512];
    fgets(line, sizeof(line), file); // pula cabeçalho

    while (fgets(line, sizeof(line), file)) {
        int fileId;
        char name[MAX_LEN];
        sscanf(line, "%d,%99[^\n]", &fileId, name);
        if (fileId == id) {
            fclose(file);
            return createShow(id, name);
        }
    }

    fclose(file);
    return NULL;
}

// ---------- Função principal ----------
int main() {
    char input[MAX_LEN];
    char *ids[MAX_SHOWS];
    int idCount = 0;

    ShowList *list = createList();

    // Primeira parte - IDs até "FIM"
    while (true) {
        scanf("%s", input);
        if (strcmp(input, "FIM") == 0) break;
        ids[idCount++] = strdup(input);
    }

    for (int i = 0; i < idCount; i++) {
        Show *s = loadShowById(ids[i]);
        if (s != NULL) insertEnd(list, s);
    }

    // Segunda parte - comandos
    int n;
    scanf("%d", &n);
    getchar();

    for (int i = 0; i < n; i++) {
        char cmd[10], id[50];
        int pos;
        fgets(input, sizeof(input), stdin);

        if (sscanf(input, "%s %d %s", cmd, &pos, id) == 3 && strcmp(cmd, "I*") == 0) {
            Show *s = loadShowById(id);
            insertAt(list, s, pos);
        } else if (sscanf(input, "%s %s", cmd, id) == 2) {
            if (strcmp(cmd, "II") == 0) {
                Show *s = loadShowById(id);
                insertBegin(list, s);
            } else if (strcmp(cmd, "IF") == 0) {
                Show *s = loadShowById(id);
                insertEnd(list, s);
            }
        } else if (strncmp(input, "RI", 2) == 0) {
            Show *r = removeBegin(list);
            printRemoved(r);
        } else if (strncmp(input, "RF", 2) == 0) {
            Show *r = removeEnd(list);
            printRemoved(r);
        } else if (sscanf(input, "R* %d", &pos) == 1) {
            Show *r = removeAt(list, pos);
            printRemoved(r);
        }
    }

    printList(list);
    return 0;
}
