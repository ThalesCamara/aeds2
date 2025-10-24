import java.io.*;      // Importa classes para leitura/escrita de arquivos
import java.util.Scanner;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mergesort {

    // Matrícula do aluno - será usada no nome do arquivo de log
    private static final String MATRICULA = "885470";

    // Contadores globais para estatísticas do algoritmo
    private static long comparisons = 0;  // Conta número de comparações feitas
    private static long movements = 0;     // Conta número de movimentações de elementos

    public static void main(String[] args) throws Exception {
        // Abre o arquivo CSV para leitura
        BufferedReader br = new BufferedReader(new FileReader("/tmp/games.csv"));
        br.readLine(); // Pula cabeçalho
        Game[] lista = new Game[60000];
        int listaCount = 0;
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] fields = CsvParser.parseLine(linha);
            if (fields == null || fields.length < 14) continue;
            lista[listaCount++] = Game.fromCsvFields(fields);
        }
        br.close();

        // DETECÇÃO RÁPIDA DE DUPLICATAS (opcional)
        int[] ids = new int[listaCount];
        int[] counts = new int[listaCount];
        int uniqueCount = 0;
        
        for (int i = 0; i < listaCount; i++) {
            int currentId = lista[i].id;
            boolean found = false;
            for (int j = 0; j < uniqueCount; j++) {
                if (ids[j] == currentId) {
                    counts[j]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                ids[uniqueCount] = currentId;
                counts[uniqueCount] = 1;
                uniqueCount++;
            }
        }
        
        for (int i = 0; i < uniqueCount; i++) {
            if (counts[i] > 1) {
                System.err.println("Duplicado AppID=" + ids[i] + " vezes=" + counts[i]);
            }
        }

        // REMOVER DUPLICATAS: mantém a primeira ocorrência de cada AppID
        Game[] arr = new Game[listaCount];
        int[] seenIds = new int[listaCount];
        int seenCount = 0;
        int arrCount = 0;
        
        for (int i = 0; i < listaCount; i++) {
            boolean found = false;
            for (int j = 0; j < seenCount; j++) {
                if (seenIds[j] == lista[i].id) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                arr[arrCount++] = lista[i];
                seenIds[seenCount++] = lista[i].id;
            }
        }

        // PREPARA ARRAY ORDENADO POR ID PARA BUSCA BINÁRIA
        Game[] arrById = new Game[arrCount];
        for (int i = 0; i < arrCount; i++) {
            arrById[i] = arr[i];
        }
        quickSortById(arrById, 0, arrCount - 1);

        // LÊ IDS DA ENTRADA PADRÃO E MONTA O VETOR DE PESQUISA USANDO BUSCA BINÁRIA
        Scanner sc = new Scanner(System.in);
        Game pesquisaTemp[] = new Game[100];
        int pesquisaAux = 0;
        while (sc.hasNextLine()) {
            String buscaId = sc.nextLine().trim();
            if (buscaId.equals("FIM")) break;
            if (buscaId.isEmpty()) continue;
            try {
                int idBusca = Integer.parseInt(buscaId);
                int idx = binarySearchById(arrById, idBusca, arrCount);
                if (idx >= 0) pesquisaTemp[pesquisaAux++] = arrById[idx];
            } catch (NumberFormatException e) { /* ignora */ }
        }
        sc.close();

        // copia apenas os pesquisados para um array sem nulls e ordena por preço (mergeSort)
        Game[] pesquisa = new Game[pesquisaAux];
        for (int i = 0; i < pesquisaAux; i++) {
            pesquisa[i] = pesquisaTemp[i];
        }
        long t0 = System.nanoTime();
        if (pesquisa.length > 1) mergeSort(pesquisa);
        long t1 = System.nanoTime();
        long tempoNano = t1 - t0;

        // IMPRESSÃO: 5 preços mais caros (pulando duplicatas por ID)
        System.out.println("| 5 preços mais caros |");
        int[] usedIds = new int[10];
        int usedIdsCount = 0;
        int printed = 0;

        for (int i = pesquisa.length - 1; i >= 0 && printed < 5; i--) {
            Game g = pesquisa[i];
            if (g == null) continue;
            boolean found = false;
            for (int j = 0; j < usedIdsCount; j++) {
                if (usedIds[j] == g.id) {
                    found = true;
                    break;
                }
            }
            if (found) continue;
            System.out.println(g.toString());
            usedIds[usedIdsCount++] = g.id;
            printed++;
        }
        System.out.println();

        // IMPRESSÃO: 5 preços mais baratos (pulando duplicatas)
        System.out.println("| 5 preços mais baratos |");
        usedIdsCount = 0;
        printed = 0;
        for (int i = 0; i < pesquisa.length && printed < 5; i++) {
            Game g = pesquisa[i];
            if (g == null) continue;
            boolean found = false;
            for (int j = 0; j < usedIdsCount; j++) {
                if (usedIds[j] == g.id) {
                    found = true;
                    break;
                }
            }
            if (found) continue;
            System.out.println(g.toString());
            usedIds[usedIdsCount++] = g.id;
            printed++;
        }

        // GERAÇÃO DO ARQUIVO DE LOG
        String logName = MATRICULA + "_mergesort.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(logName))) {
            pw.printf("%s\t%d\t%d\t%d\n", MATRICULA, comparisons, movements, tempoNano);
        }
    }

    // Quicksort para ordenar por ID
    private static void quickSortById(Game[] a, int left, int right) {
        if (left < right) {
            int pivotIndex = partitionById(a, left, right);
            quickSortById(a, left, pivotIndex - 1);
            quickSortById(a, pivotIndex + 1, right);
        }
    }
    
    private static int partitionById(Game[] a, int left, int right) {
        Game pivot = a[right];
        int i = (left - 1);
        for (int j = left; j < right; j++) {
            if (a[j].id <= pivot.id) {
                i++;
                Game temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        Game temp = a[i + 1];
        a[i + 1] = a[right];
        a[right] = temp;
        return (i + 1);
    }

    // === MÉTODO PRINCIPAL DO MERGESORT ===
    // Ponto de entrada para ordenar o array
    private static void mergeSort(Game[] a) {
        // Se array for nulo ou ter menos de 2 elementos, já está ordenado
        if (a == null || a.length < 2) return;
        
        // Cria array auxiliar do mesmo tamanho (usado no merge)
        Game[] aux = new Game[a.length];
        
        // Chama a função recursiva passando limites inicial e final
        mergeSortRecursive(a, aux, 0, a.length - 1);
    }

    // === FUNÇÃO RECURSIVA DO MERGESORT ===
    // Divide o array em metades e ordena recursivamente
    private static void mergeSortRecursive(Game[] a, Game[] aux, int left, int right) {
        // Caso base: se left >= right, subarray tem 0 ou 1 elemento (já ordenado)
        if (left >= right) return;
        
        // Calcula o índice do meio
        int mid = (left + right) / 2;
        
        // Ordena recursivamente a metade esquerda
        mergeSortRecursive(a, aux, left, mid);
        
        // Ordena recursivamente a metade direita
        mergeSortRecursive(a, aux, mid + 1, right);
        
        // Mescla as duas metades ordenadas
        merge(a, aux, left, mid, right);
    }

    // === FUNÇÃO DE MESCLAGEM (MERGE) ===
    // Combina duas metades ordenadas em uma única sequência ordenada
    private static void merge(Game[] a, Game[] aux, int left, int mid, int right) {
        // Copia elementos do intervalo [left, right] para o array auxiliar
        for (int k = left; k <= right; k++) {
            aux[k] = a[k];  // Copia elemento por elemento
        }
        
        // i: índice da metade esquerda (começa em left)
        int i = left;
        
        // j: índice da metade direita (começa em mid+1)
        int j = mid + 1;
        
        // k: índice de inserção no array original
        int k = left;
        
        // Mescla enquanto houver elementos em ambas as metades
        while (i <= mid && j <= right) {
            // Incrementa contador de comparações
            comparisons++;
            
            // Se elemento da esquerda <= elemento da direita
            if (compareAux(aux[i], aux[j]) <= 0) {
                a[k++] = aux[i++];  // Copia da esquerda e avança ambos índices
                movements++;         // Conta a movimentação
            } else {
                a[k++] = aux[j++];  // Copia da direita e avança ambos índices
                movements++;         // Conta a movimentação
            }
        }
        
        // Copia elementos restantes da metade esquerda (se houver)
        while (i <= mid) {
            a[k++] = aux[i++];  // Copia e avança
            movements++;         // Conta a movimentação
        }
        
        // Copia elementos restantes da metade direita (se houver)
        while (j <= right) {
            a[k++] = aux[j++];  // Copia e avança
            movements++;         // Conta a movimentação
        }
    }

    // === FUNÇÃO DE COMPARAÇÃO ===
    // Compara dois jogos: primeiro por preço, depois por AppID (desempate)
    // Retorna: -1 se x < y, 0 se x == y, 1 se x > y
    private static int compareAux(Game x, Game y) {
        // Compara preços
        if (Float.compare(x.price, y.price) < 0) return -1;  // x mais barato
        if (Float.compare(x.price, y.price) > 0) return 1;   // x mais caro
        
        // Preços são iguais, desempata por AppID (ordem crescente)
        if (x.id < y.id) return -1;  // x tem ID menor
        if (x.id > y.id) return 1;   // x tem ID maior
        return 0;  // IDs iguais (empate total)
    }

    // ========== CLASSES AUXILIARES ==========
    
    // === PARSER DE CSV ===
    // Classe responsável por fazer parse correto de linhas CSV
    // Trata aspas duplas, vírgulas dentro de campos, etc.
    public static class CsvParser {
        
        // Converte uma linha CSV em array de campos (strings)
        public static String[] parseLine(String line) {
            // Array temporário para armazenar campos
            String[] temp = new String[20];
            int count = 0;
            
            // Se linha for nula, retorna array vazio
            if (line == null) return new String[0];
            
            // StringBuilder para construir o campo atual
            StringBuilder cur = new StringBuilder();
            
            // Flag que indica se estamos dentro de aspas
            boolean inQuotes = false;
            
            // Percorre cada caractere da linha
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);  // Pega o caractere atual
                
                // Trata aspas duplas
                if (c == '"') {
                    // Se estamos dentro de aspas E o próximo char também é ", é escape ("")
                    if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        cur.append('"');  // Adiciona uma aspa ao campo
                        i++;              // Pula o próximo caractere
                    } else {
                        // Alterna o estado (abre/fecha aspas)
                        inQuotes = !inQuotes;
                    }
                }
                // Trata vírgula (separador de campos) somente se fora de aspas
                else if (c == ',' && !inQuotes) {
                    if (count < temp.length) {
                        temp[count++] = cur.toString();  // Adiciona campo completo ao array
                    }
                    cur.setLength(0);         // Limpa o buffer para o próximo campo
                }
                // Qualquer outro caractere: adiciona ao campo atual
                else {
                    cur.append(c);
                }
            }
            
            // Adiciona o último campo (após a última vírgula)
            if (count < temp.length) {
                temp[count++] = cur.toString();
            }
            
            // Copia para array do tamanho correto
            String[] result = new String[count];
            for (int i = 0; i < count; i++) {
                result[i] = temp[i];
            }
            
            // Retorna array com todos os campos
            return result;
        }
    }

    // === CLASSE GAME ===
    // Representa um jogo com todos os seus atributos
    public static class Game {
        public int id;
        public String name;
        public String releaseDate;
        public int estimatedOwners;
        public float price;
        public String[] supportedLanguages;
        public int supportedLanguagesCount;
        public int metacriticScore;
        public float userScore;
        public int achievements;
        public String[] publishers;
        public int publishersCount;
        public String[] developers;
        public int developersCount;
        public String[] categories;
        public int categoriesCount;
        public String[] genres;
        public int genresCount;
        public String[] tags;
        public int tagsCount;

        public static Game fromCsvFields(String[] f) {
            Game g = new Game();
            g.id = parseIntDefault(get(f,0), 0);
            g.name = unquote(get(f,1));
            g.releaseDate = normalizeDate(get(f,2));
            g.estimatedOwners = parseIntDefault(cleanNumber(get(f,3)), 0);
            g.price = parsePrice(get(f,4));
            String[] langResult = parseList(get(f,5), true);
            g.supportedLanguages = langResult;
            g.supportedLanguagesCount = langResult.length;
            g.metacriticScore = parseIntOrDefault(get(f,6), -1);
            g.userScore = parseUserScore(get(f,7));
            g.achievements = parseIntOrDefault(get(f,8), 0);
            String[] pubResult = parseList(get(f,9), false);
            g.publishers = pubResult;
            g.publishersCount = pubResult.length;
            String[] devResult = parseList(get(f,10), false);
            g.developers = devResult;
            g.developersCount = devResult.length;
            String[] catResult = parseList(get(f,11), false);
            g.categories = catResult;
            g.categoriesCount = catResult.length;
            String[] genResult = parseList(get(f,12), false);
            g.genres = genResult;
            g.genresCount = genResult.length;
            String[] tagResult = parseList(get(f,13), false);
            g.tags = tagResult;
            g.tagsCount = tagResult.length;
            return g;
        }

        private static String get(String[] f, int idx) {
            if (f == null || idx >= f.length) return "";
            return f[idx] == null ? "" : f[idx].trim();
        }

        private static String unquote(String s) {
            if (s == null) return "";
            s = s.trim();
            if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
                s = s.substring(1, s.length()-1);
            }
            return s;
        }

        private static String cleanNumber(String s) {
            if (s == null) return "";
            s = unquote(s).trim();
            return s.replaceAll("[^0-9]", "");
        }

        private static int parseIntDefault(String s, int def) {
            if (s == null) return def;
            s = s.trim();
            if (s.isEmpty()) return def;
            try { return Integer.parseInt(s); } catch (Exception e) { return def; }
        }

        private static int parseIntOrDefault(String s, int def) {
            s = unquote(s).trim();
            if (s.isEmpty()) return def;
            s = s.replaceAll("[^0-9-]", "");
            if (s.isEmpty()) return def;
            try { return Integer.parseInt(s); } catch (Exception e) { return def; }
        }

        private static float parseUserScore(String s) {
            s = unquote(s).trim().toLowerCase();
            if (s.isEmpty() || s.equals("tbd")) return -1.0f;
            if (s.equals("0") || s.equals("0.0")) return 0.0f;
            s = s.replaceAll("[^0-9,.-]", "");
            s = s.replace(',', '.');
            if (s.isEmpty()) return -1.0f;
            try { return Float.parseFloat(s); } catch (Exception e) { return -1.0f; }
        }

        private static float parsePrice(String s) {
            s = unquote(s).trim();
            if (s.isEmpty()) return 0.0f;
            if (s.equalsIgnoreCase("Free to Play")) return 0.0f;
            String cleaned = s.replaceAll("[^0-9,.-]", "");
            cleaned = cleaned.replace(',', '.');
            if (cleaned.isEmpty()) return 0.0f;
            try { return Float.parseFloat(cleaned); } catch (Exception e) { return 0.0f; }
        }

        private static String[] parseList(String s, boolean preserveSingleQuotes) {
            s = unquote(s).trim();
            String[] temp = new String[100];
            int count = 0;
            
            if (s.isEmpty()) return new String[0];
            if (s.startsWith("[") && s.endsWith("]") && s.length() >= 2) {
                s = s.substring(1, s.length()-1);
            }
            StringBuilder cur = new StringBuilder();
            boolean inSingle = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\'') {
                    inSingle = !inSingle;
                    continue;
                }
                if (c == ',' && !inSingle) {
                    String item = cur.toString().trim();
                    item = stripQuotes(item);
                    if (!item.isEmpty() && count < temp.length) {
                        temp[count++] = item;
                    }
                    cur.setLength(0);
                } else {
                    cur.append(c);
                }
            }
            String last = cur.toString().trim();
            last = stripQuotes(last);
            if (!last.isEmpty() && count < temp.length) {
                temp[count++] = last;
            }
            
            // Copia para array do tamanho correto
            String[] result = new String[count];
            for (int i = 0; i < count; i++) {
                result[i] = temp[i];
            }
            return result;
        }

        private static String stripQuotes(String s) {
            if (s == null) return "";
            s = s.trim();
            if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) s = s.substring(1, s.length()-1);
            if (s.length() >= 2 && s.startsWith("'") && s.endsWith("'")) s = s.substring(1, s.length()-1);
            return s.trim();
        }

        private static String normalizeDate(String raw) {
            raw = unquote(raw).trim();
            if (raw.isEmpty()) return "";
            String[] patterns = {
                "MMM d, uuuu", "MMMM d, uuuu", "MMM dd, uuuu", "MMMM dd, uuuu",
                "d MMM uuuu", "d MMM, uuuu", "uuuu-MM-dd", "MM/dd/uuuu", "dd/MM/uuuu", "uuuu"
            };
            for (String p : patterns) {
                try {
                    if (p.equals("uuuu")) {
                        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{4})").matcher(raw);
                        if (m.find()) {
                            int y = Integer.parseInt(m.group(1));
                            return String.format("01/01/%04d", y);
                        } else continue;
                    } else {
                        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern(p, Locale.ENGLISH);
                        java.time.LocalDate ld = java.time.LocalDate.parse(raw, fmt);
                        return ld.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                    }
                } catch (Exception ex) {}
            }

            // Array de meses para conversão
            String[] monthNames = {"jan", "january", "feb", "february", "mar", "march", 
                                   "apr", "april", "may", "jun", "june", "jul", "july",
                                   "aug", "august", "sep", "sept", "september", 
                                   "oct", "october", "nov", "november", "dec", "december"};
            int[] monthValues = {1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 
                                8, 8, 9, 9, 9, 10, 10, 11, 11, 12, 12};

            String low = raw.toLowerCase();
            int year = -1, month = 1, day = 1;

            Matcher my = Pattern.compile("(\\d{4})").matcher(raw);
            if (my.find()) year = Integer.parseInt(my.group(1));

            for (int i = 0; i < monthNames.length; i++) {
                if (low.contains(monthNames[i])) {
                    month = monthValues[i];
                    break;
                }
            }

            Matcher md = Pattern.compile("\\b(\\d{1,2})\\b").matcher(raw);
            while (md.find()) {
                int v = Integer.parseInt(md.group(1));
                if (v == year) continue;
                if (v >= 1 && v <= 31) { day = v; break; }
            }

            // Se não encontrou ano válido, retorna string original
            if (year == -1) return raw;
            
            // Retorna data formatada como dd/MM/yyyy
            return String.format("%02d/%02d/%04d", day, month, year);
        }

        // Sobrescreve toString para formatar saída do jogo
        @Override
        public String toString() { return mergesort.gameToString(this); }
    }

    // === MÉTODO DE FORMATAÇÃO DE SAÍDA ===
    // Converte um objeto Game para string no formato especificado
    public static String gameToString(Game g) {
        return "=> " + g.id + " ## " +                               // AppID
            g.name + " ## " +                                        // Nome do jogo
            g.releaseDate + " ## " +                                 // Data de lançamento
            g.estimatedOwners + " ## " +                             // Número estimado de donos
            g.price + " ## " +                                       // Preço
            auxiliarMostrar(g.supportedLanguages, g.supportedLanguagesCount) + " ## " +         // Idiomas suportados
            g.metacriticScore + " ## " +                             // Nota Metacritic
            g.userScore + " ## " +                                   // Nota dos usuários
            g.achievements + " ## " +                                // Número de conquistas
            auxiliarMostrar(g.publishers, g.publishersCount) + " ## " +                 // Publicadores
            auxiliarMostrar(g.developers, g.developersCount) + " ## " +                 // Desenvolvedores
            auxiliarMostrar(g.categories, g.categoriesCount) + " ## " +                 // Categorias
            auxiliarMostrar(g.genres, g.genresCount) + " ## " +                     // Gêneros
            auxiliarMostrar(g.tags, g.tagsCount) + " ##";                         // Tags
    }

    // === MÉTODO AUXILIAR PARA FORMATAR LISTAS ===
    // Converte um array de strings para o formato [item1, item2, item3]
    private static String auxiliarMostrar(String[] array, int count) {
        // Inicia a string com colchete de abertura
        String result = "[";
        
        // Se o array não for nulo, percorre todos os elementos
        if (array != null) {
            for (int i = 0; i < count; i++) {
                // Adiciona o item atual
                result += array[i];
                
                // Se não for o último item, adiciona vírgula e espaço
                if (i < count - 1) {
                    result += ", ";
                }
            }
        }
        
        // Fecha o colchete e retorna
        result += "]";
        return result;
    }

    // --- método auxiliar de busca binária por id ---
    private static int binarySearchById(Game[] a, int id, int n) {
        int l = 0, r = n - 1;
        while (l <= r) {
            int m = (l + r) >>> 1;
            if (a[m].id == id) return m;
            if (a[m].id < id) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }
}
