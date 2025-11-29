import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

class Consts {
    public static final int MAX_GAMES = 500;
    public static final int MAX_INNER_ARRAY = 50;
    public static final int MAX_IDS = 100;
}

class GameAS {
    int id;
    String name;
    String releaseDate;
    int estimatedOwners;
    float price;
    String[] supportedLanguages;
    int supportedLanguagesCount;
    int metacriticScore;
    float userScore;
    int achievements;
    String[] publishers;
    int publishersCount;
    String[] developers;
    int developersCount;
    String[] categories;
    int categoriesCount;
    String[] genres;
    int genresCount;
    String[] tags;
    int tagsCount;

    GameAS() {
        this.id = 0;
        this.name = "";
        this.releaseDate = "";
        this.estimatedOwners = 0;
        this.price = 0.0f;
        this.supportedLanguages = new String[Consts.MAX_INNER_ARRAY];
        this.supportedLanguagesCount = 0;
        this.metacriticScore = -1;
        this.userScore = -1.0f;
        this.achievements = 0;
        this.publishers = new String[Consts.MAX_INNER_ARRAY];
        this.publishersCount = 0;
        this.developers = new String[Consts.MAX_INNER_ARRAY];
        this.developersCount = 0;
        this.categories = new String[Consts.MAX_INNER_ARRAY];
        this.categoriesCount = 0;
        this.genres = new String[Consts.MAX_INNER_ARRAY];
        this.genresCount = 0;
        this.tags = new String[Consts.MAX_INNER_ARRAY];
        this.tagsCount = 0;
    }

    GameAS(int id, String name, String releaseDate, int estimatedOwners, float price,
            String[] supportedLanguages, int supportedLanguagesCount, int metacriticScore, float userScore,
            int achievements,
            String[] publishers, int publishersCount, String[] developers, int developersCount,
            String[] categories, int categoriesCount, String[] genres, int genresCount, String[] tags, int tagsCount) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;

        // Copiando arrays
        this.supportedLanguages = supportedLanguages;
        this.supportedLanguagesCount = supportedLanguagesCount;
        this.publishers = publishers;
        this.publishersCount = publishersCount;
        this.developers = developers;
        this.developersCount = developersCount;
        this.categories = categories;
        this.categoriesCount = categoriesCount;
        this.genres = genres;
        this.genresCount = genresCount;
        this.tags = tags;
        this.tagsCount = tagsCount;

        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
    }
}

// Célula dupla (renomeada)
class Cell {
    public Cell prox;
    public Cell ant;
    public GameAS game;

    public Cell() {
        this(null);
    }

    public Cell(GameAS x) {
        prox = null;
        ant = null;
        game = x;
    }
}

// Lista dupla
class ListaDupla {
    private Cell head, tail;

    public ListaDupla() {
        head = new Cell();
        tail = head;
    }

    // ------Inserções------
    // Inserir no início
    public void inserirInicio(GameAS game) {
        if (head == tail) {
            head.prox = new Cell(game);
            tail = head.prox;
            tail.ant = head;
        } else {
            Cell tmp = new Cell(game);
            tmp.prox = head.prox;
            head.prox = tmp;
            tmp.prox.ant = tmp;
            tmp.ant = head;
            tmp = null;
        }
    }

    // Inserir no fim
    public void inserirFim(GameAS game) {
        if (head == tail) {
            head.prox = new Cell(game);
            tail = head.prox;
            tail.ant = head;
        } else {
            Cell tmp = new Cell(game);
            tail.prox = tmp;
            tmp.ant = tail;
            tail = tmp;
        }
    }

    // Inserir em posição
    public void inserir(GameAS game, int pos) {
        if (pos < 0 || pos > tamanho() + 1)
            return;
        if (pos == 0)
            inserirInicio(game);
        else if (pos == tamanho())
            inserirFim(game);
        else {
            Cell i = head;
            for (int j = 0; j < pos; j++, i = i.prox) {
            }
            Cell tmp = new Cell(game);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp.ant = i;
            tmp.prox.ant = tmp;
            i = tmp = null;
        }
    }

    // Capturando tamanho
    public int tamanho() {
        int tam = 0;
        for (Cell i = head.prox; i != null; i = i.prox) {
            tam++;
        }
        return tam;
    }

    // ------Remoções------
    // Remover do início
    public GameAS removerInicio() throws Exception {
        if (head == tail)
            throw new Exception("Erro ao remover (vazia)!");
        printRemovido(0);
        GameAS elemento = head.prox.game;
        if (head.prox == tail) {
            Cell tmp = head.prox;
            head.prox = null;
            tail.ant = null;      
            tail = head;
            tmp = null;
        } else {
            Cell tmp = head.prox;
            head.prox = head.prox.prox;
            head.prox.ant = head;
            tmp.prox = tmp.ant = tmp = null;
        }
        return elemento;
    }

    // Remover do fim
    public GameAS removerFim() throws Exception {
        if (head == tail)
            throw new Exception("Erro ao remover (vazia)!");
        int tam = tamanho();
        printRemovido(tam);
        GameAS elemento = tail.game;
        tail.ant.prox = null;
        tail = tail.ant;
        tail.prox = null;
        return elemento;
    }

    // Remover de posição
    public GameAS remover(int pos) throws Exception {
        pos ++;
        if (tail == head)
            throw new Exception("Erro ao remover (vazia)!");
        GameAS elemento = null;
        int tam = tamanho();
        if (pos >= tam || pos < 0)
            throw new Exception("Erro ao remover (posição inválida)!");
        printRemovido(pos);
        if (pos == 0)
            elemento = removerInicio();
        else if (pos == tam - 1)
            elemento = removerFim();
        else {
            Cell i = head;
            for (int j = 0; j < pos; j++, i = i.prox) {
            }
            i.ant.prox = i.prox;
            i.prox.ant = i.ant;
            elemento = i.game;
            i = i.prox = i.ant = i = null;
        }
        return elemento;
    }

    // ------Mostrar------
    // Mostrar removido
    public void printRemovido(int pos) {
        Cell i = head.prox;
        int j = 1;
        for (; i != null && j < pos; i = i.prox, j++){}
        System.out.println("(R) " + i.game.name);
    }
    // Mostrar geral
    public void printando() {
        int j = 0;
        for (Cell i = head.prox; i != null; i = i.prox) {
            String releaseDate = (i.game.releaseDate.charAt(1) == '/'
                    ? "0" + i.game.releaseDate
                    : i.game.releaseDate);
            System.out.println("[" + j + "]" +
                    " => " + i.game.id + " ## " + i.game.name + " ## " + releaseDate
                    + " ## " + i.game.estimatedOwners + " ## " + i.game.price
                    + " ## "
                    + printArray(i.game.supportedLanguages,
                            i.game.supportedLanguagesCount)
                    + " ## " + i.game.metacriticScore + " ## " + i.game.userScore
                    + " ## " + i.game.achievements
                    + " ## " + printArray(i.game.publishers, i.game.publishersCount)
                    + " ## " + printArray(i.game.developers, i.game.developersCount)
                    + " ## " + printArray(i.game.categories, i.game.categoriesCount)
                    + " ## " + printArray(i.game.genres, i.game.genresCount)
                    + " ## "
                    + (i.game.tagsCount == 0 ? ""
                            : printArray(i.game.tags, i.game.tagsCount))
                    + (i.game.tagsCount == 0 ? "" : " ##"));
            j++;
        }
    }

    // Printando o Array
    static String printArray(String[] array, int tamanho) {
        String resultado = "";
        // Se a lista estiver vazia, retorna "[]"
        if (tamanho == 0) {
            return "[]";
        }
        // Se não estiver vazia, formata a lista
        resultado += "[";
        for (int i = 0; i < tamanho; i++) {
            resultado += array[i];
            if (i < tamanho - 1) {
                resultado += ", ";
            }
        }
        resultado += "]";
        return resultado;
    }
}

public class listaAlocacaoSequencial {
    // Scanner
    public static Scanner sc;

    static int cursor = 0;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        String[] ids = new String[Consts.MAX_IDS];
        int idsCount = 0;

        while (!entrada.equals("FIM") && idsCount < ids.length) {
            ids[idsCount++] = entrada;
            entrada = sc.nextLine();
        }

        // Capturando os jogos digitados em uma lista dupla flexível
        ListaDupla games = GamesLoader.inicializacao(ids, idsCount);

        int n = sc.nextInt();
        sc.nextLine();
        // Capturando as operações
        for (int i = 0; i < n; i++) {
            cursor = 0;
            // Lendo a entrada
            String entrada2 = sc.nextLine();
            // Capturando operação, posição e id
            String operacao = capturaOperacao(entrada2);

            String posicao = "";
            if (operacao.charAt(1) == '*')
                posicao = capturaPosicao(entrada2);

            String id = "";
            GameAS game = null;
            if (operacao.charAt(0) == 'I') {
                id = capturaIdFinal(entrada2);
                if (!id.isEmpty())
                    game = GamesLoader.getSingleGameById(id);
            }
            // Realizando a operação
            switch (operacao) {
                case "II":
                    games.inserirInicio(game);
                    break;
                case "I*":
                    games.inserir(game, Integer.parseInt(posicao));
                    break;
                case "IF":
                    games.inserirFim(game);
                    break;
                case "RI":
                    try {
                        games.removerInicio();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "R*":
                    try {
                        games.remover(Integer.parseInt(posicao));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "RF":
                    try {
                        games.removerFim();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
        // Printando a lista final
        games.printando();
    }

    // Capturando operação
    static String capturaOperacao(String entrada) {
        String operacao = "";
        while (cursor < 2 && cursor < entrada.length()) {
            operacao += entrada.charAt(cursor);
            cursor++;
        }
        cursor++; // Pulando espaço
        return operacao;
    }

    // Capturando posição
    static String capturaPosicao(String entrada) {
        String posicao = "";
        while (cursor < entrada.length() && entrada.charAt(cursor) != ' ') {
            posicao += entrada.charAt(cursor);
            cursor++;
        }
        cursor++; // Pulando espaço
        return posicao;
    }

    // Capturando id final
    static String capturaIdFinal(String entrada) {
        String id = "";
        while (cursor < entrada.length()) {
            if (entrada.charAt(cursor) != ' ') {
                id += entrada.charAt(cursor);
                cursor++;
            }
        }
        return id;
    }

}

// Capturando os jogos digitados através do id pelo usuário
class GamesLoader {
    // Scanner
    public static Scanner sc;
    // Variável que pula caracteres das linhas
    static int cursor = 0;
    // Ids de pesquisa e seu tamanho
    static String[] ids;
    static int idsCount;
    // Lista de jogos encontrados e seu tamanho
    static int gamesListCount;

    // Função para obter o tamanho final da lista de jogos
    public static int obterTamanhoGamesList() {
        return gamesListCount;
    }

    // Capturando os jogos
    static ListaDupla inicializacao(String[] idArray, int tamanho) {
        ListaDupla lista = new ListaDupla();
        gamesListCount = 0;

        // Copiando IDs para a variável de classe 'ids'
        ids = idArray;
        idsCount = tamanho;

        // Pesquisa por id
        for (int j = 0; j < tamanho; j++) {
            int indiceEncontrado = -1;

            try {
                java.io.File arquivo = new java.io.File("/tmp/games.csv");
                if (!arquivo.exists()) {
                    System.out.println("Arquivo 'games.csv' não encontrado!");
                    return lista;
                }

                InputStream is = new FileInputStream(arquivo); // abre do zero
                Scanner sc = new Scanner(is);

                // Pula cabeçalho
                if (sc.hasNextLine())
                    sc.nextLine();

                while (sc.hasNextLine() && indiceEncontrado == -1) {
                    String linha = sc.nextLine();
                    cursor = 0;

                    int id = capturaId(linha);
                    indiceEncontrado = igualId(id);

                    if (indiceEncontrado != -1) {
                        String name = capturaName(linha);
                        String releaseDate = capturaReleaseDate(linha);
                        int estimatedOwners = capturaEstimatedOwners(linha);
                        float price = capturaPrice(linha);

                        String[] supportedLanguages = new String[Consts.MAX_INNER_ARRAY];
                        int supportedLanguagesCount = capturaSupportedLanguages(linha, supportedLanguages);
                        int metacriticScore = capturaMetacriticScore(linha);
                        float userScore = capturaUserScore(linha);
                        int achievements = capturaAchievements(linha);

                        String[] publishers = new String[Consts.MAX_INNER_ARRAY];
                        int publishersCount = capturaUltimosArryays(linha, publishers);
                        String[] developers = new String[Consts.MAX_INNER_ARRAY];
                        int developersCount = capturaUltimosArryays(linha, developers);
                        String[] categories = new String[Consts.MAX_INNER_ARRAY];
                        int categoriesCount = capturaUltimosArryays(linha, categories);
                        String[] genres = new String[Consts.MAX_INNER_ARRAY];
                        int genresCount = capturaUltimosArryays(linha, genres);
                        String[] tags = new String[Consts.MAX_INNER_ARRAY];
                        int tagsCount = capturaUltimosArryays(linha, tags);

                        GameAS jogo = new GameAS(id, name, releaseDate, estimatedOwners, price,
                                supportedLanguages, supportedLanguagesCount, metacriticScore, userScore, achievements,
                                publishers, publishersCount, developers, developersCount, categories, categoriesCount,
                                genres, genresCount, tags, tagsCount);

                        lista.inserirFim(jogo);
                        removerId(indiceEncontrado);
                    }
                }

                sc.close();
                is.close();

            } catch (Exception e) {
                System.out.println("Erro ao abrir ou ler o arquivo: " + e.getMessage());
            }
        }

        return lista;
    }

    static GameAS getSingleGameById(String idV) {
        // Abrindo do arquivo
        InputStream is = null;
        // Criando o game
        try {
            java.io.File arquivo = new java.io.File("/tmp/games.csv");
            if (!arquivo.exists()) {
                System.out.println("Arquivo 'games.csv' não encontrado!");
                return null;
            }
            is = new FileInputStream(arquivo);
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
            return null;
        }
        sc = new Scanner(is);
        // Criando objeto
        GameAS jogo = new GameAS();
        // Pesquisa por id
        boolean teste = false;
        // Pula cabeçalho
        if (sc.hasNextLine())
            sc.nextLine();
        while (sc.hasNextLine() && teste == false) {
            String linha = sc.nextLine();
            // Resetando o cursor para a nova linha
            cursor = 0;
            // Capturando outras informações
            int id = capturaId(linha);

            if (id == Integer.parseInt(idV)) {
                teste = true;
                String name = capturaName(linha);
                String releaseDate = capturaReleaseDate(linha);
                int estimatedOwners = capturaEstimatedOwners(linha);
                float price = capturaPrice(linha);

                String[] supportedLanguages = new String[Consts.MAX_INNER_ARRAY];
                int supportedLanguagesCount = capturaSupportedLanguages(linha, supportedLanguages);
                int metacriticScore = capturaMetacriticScore(linha);
                float userScore = capturaUserScore(linha);
                int achievements = capturaAchievements(linha);

                String[] publishers = new String[Consts.MAX_INNER_ARRAY];
                int publishersCount = capturaUltimosArryays(linha, publishers);
                String[] developers = new String[Consts.MAX_INNER_ARRAY];
                int developersCount = capturaUltimosArryays(linha, developers);
                String[] categories = new String[Consts.MAX_INNER_ARRAY];
                int categoriesCount = capturaUltimosArryays(linha, categories);
                String[] genres = new String[Consts.MAX_INNER_ARRAY];
                int genresCount = capturaUltimosArryays(linha, genres);
                String[] tags = new String[Consts.MAX_INNER_ARRAY];
                int tagsCount = capturaUltimosArryays(linha, tags);

                // Adicionando a classe
                jogo = new GameAS(id, name, releaseDate, estimatedOwners, price,
                        supportedLanguages, supportedLanguagesCount, metacriticScore, userScore, achievements,
                        publishers, publishersCount, developers, developersCount, categories, categoriesCount, genres,
                        genresCount, tags, tagsCount);
            }
        }
        sc.close();
        return jogo;
    }

    static int igualId(int id) {
        if (Integer.parseInt(ids[0]) == id)
            return 0;
        else
            return -1;
    }

    // Função para remover o ID da lista de pesquisa
    static void removerId(int indice) {
        if (indice >= 0 && indice < idsCount) {
            for (int j = indice; j < idsCount - 1; j++) {
                ids[j] = ids[j + 1];
            }
            ids[idsCount - 1] = null; // Limpa a última posição
            idsCount--; // Decrementa o tamanho
        }
    }

    // Capturando Id
    static int capturaId(String jogo) {
        int id = 0;
        while (cursor < jogo.length() && Character.isDigit(jogo.charAt(cursor))) {
            id = id * 10 + (jogo.charAt(cursor) - '0');
            cursor++;
        }
        return id;
    }

    // Capturando nome
    static String capturaName(String jogo) {
        String name = "";
        while (jogo.charAt(cursor) != ',' && cursor < jogo.length()) {
            cursor++;
        }
        cursor++;
        while (jogo.charAt(cursor) != ',' && cursor < jogo.length()) {
            name += jogo.charAt(cursor);
            cursor++;
        }
        return name;
    }

    // Capturando Release Date
    static String capturaReleaseDate(String jogo) {
        while (cursor < jogo.length() && jogo.charAt(cursor) != '"') {
            cursor++;
        }
        cursor++;
        String dia = "", mes = "", ano = "";
        // Pegando mês
        for (int i = 0; cursor < jogo.length() && i < 3; i++) {
            mes += jogo.charAt(cursor);
            cursor++;
        }
        mes = mes.trim();
        switch (mes) {
            case "Jan":
                mes = "01";
                break;
            case "Feb":
                mes = "02";
                break;
            case "Mar":
                mes = "03";
                break;
            case "Apr":
                mes = "04";
                break;
            case "May":
                mes = "05";
                break;
            case "Jun":
                mes = "06";
                break;
            case "Jul":
                mes = "07";
                break;
            case "Aug":
                mes = "08";
                break;
            case "Sep":
                mes = "09";
                break;
            case "Oct":
                mes = "10";
                break;
            case "Nov":
                mes = "11";
                break;
            case "Dec":
                mes = "12";
                break;
            default:
                break;
        }
        // Pulando espaço
        while (cursor < jogo.length() && !Character.isDigit(jogo.charAt(cursor)) && jogo.charAt(cursor) != ',') {
            cursor++;
        }
        // Pegando dia
        while (cursor < jogo.length() && Character.isDigit(jogo.charAt(cursor))) {
            dia += jogo.charAt(cursor);
            cursor++;
        }
        // Pulando espaço
        while (cursor < jogo.length() && !Character.isDigit(jogo.charAt(cursor))) {
            cursor++;
        }
        // Pegando ano
        while (cursor < jogo.length() && jogo.charAt(cursor) != '"') {
            ano += jogo.charAt(cursor);
            cursor++;
        }
        if (dia.isEmpty())
            dia = "01";
        if (mes.isEmpty())
            mes = "01";
        if (ano.isEmpty())
            ano = "0000";
        return dia + "/" + mes + "/" + ano;
    }

    // Capturando Estimated Owners
    static int capturaEstimatedOwners(String jogo) {
        int estimatedOwners = 0;
        while (cursor < jogo.length() && jogo.charAt(cursor) != ',') {
            cursor++;
        }
        cursor++;
        while (cursor < jogo.length() && jogo.charAt(cursor) != ',') {
            estimatedOwners = estimatedOwners * 10 + (jogo.charAt(cursor) - '0');
            cursor++;
        }
        return estimatedOwners;
    }

    // Capturando Price
    static float capturaPrice(String jogo) {
        String price = "";
        while (cursor < jogo.length() && !Character.isDigit(jogo.charAt(cursor)) && jogo.charAt(cursor) != 'F') {
            cursor++;
        }
        while (cursor < jogo.length() && (Character.isDigit(jogo.charAt(cursor)) || jogo.charAt(cursor) == '.')) {
            price += jogo.charAt(cursor);
            cursor++;
        }
        price = price.trim();
        if (price.isEmpty() || price.equalsIgnoreCase("Free to play")) {
            return 0.0f;
        }
        price = price.replaceAll("[^0-9.]", "");
        try {
            return Float.parseFloat(price);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    // Capturando idiomas
    static int capturaSupportedLanguages(String jogo, String[] supportedLanguages) {
        int count = 0;
        while (cursor < jogo.length() && jogo.charAt(cursor) != ']' && count < supportedLanguages.length) {
            String lingua = "";
            while (cursor < jogo.length() && !Character.isAlphabetic(jogo.charAt(cursor))) {
                cursor++;
            }
            while (cursor < jogo.length() && jogo.charAt(cursor) != ',' && jogo.charAt(cursor) != ']') {
                if (Character.isAlphabetic(jogo.charAt(cursor)) || jogo.charAt(cursor) == ' '
                        || jogo.charAt(cursor) == '-') {
                    lingua += jogo.charAt(cursor);
                }
                cursor++;
            }
            supportedLanguages[count++] = lingua;
        }
        return count; // Retorna o tamanho real
    }

    // Capturando Metacritic Score
    static int capturaMetacriticScore(String jogo) {
        String metacriticScore = "";
        while (cursor < jogo.length() && jogo.charAt(cursor) != ',') {
            cursor++;
        }
        cursor++;
        while (cursor < jogo.length() && Character.isDigit(jogo.charAt(cursor))) {
            metacriticScore += jogo.charAt(cursor);
            cursor++;
        }
        if (metacriticScore.isEmpty())
            return -1;
        else
            return Integer.parseInt(metacriticScore);
    }

    // Capturando User Score
    static float capturaUserScore(String jogo) {
        String userScore = "";
        while (cursor < jogo.length() && jogo.charAt(cursor) != ',') {
            cursor++;
        }
        cursor++;
        while (cursor < jogo.length() && (Character.isDigit(jogo.charAt(cursor)) || jogo.charAt(cursor) == '.')) {
            userScore += jogo.charAt(cursor);
            cursor++;
        }
        if (userScore.isEmpty())
            return -1.0f;
        else
            return Float.parseFloat(userScore);
    }

    // Capturando Achievements
    static int capturaAchievements(String jogo) {
        String achievements = "";
        while (cursor < jogo.length() && jogo.charAt(cursor) != ',') {
            cursor++;
        }
        cursor++;
        while (cursor < jogo.length() && (Character.isDigit(jogo.charAt(cursor)) || jogo.charAt(cursor) == '.')) {
            achievements += jogo.charAt(cursor);
            cursor++;
        }
        if (achievements.isEmpty())
            return -1;
        else
            return Integer.parseInt(achievements);
    }

    // Capturando Últimos Arrays
    static int capturaUltimosArryays(String jogo, String[] categoria) {
        int count = 0;
        boolean teste = false;
        while (cursor < jogo.length() && !Character.isAlphabetic(jogo.charAt(cursor))
                && !Character.isDigit(jogo.charAt(cursor))) {
            if (jogo.charAt(cursor) == '"')
                teste = true;
            cursor++;
        }
        if (teste) {
            while (cursor < jogo.length() && jogo.charAt(cursor) != '"' && count < categoria.length) {
                String parte = "";
                while (cursor < jogo.length() && jogo.charAt(cursor) != ',' && jogo.charAt(cursor) != '"') {
                    parte += jogo.charAt(cursor);
                    cursor++;
                }
                while (cursor < jogo.length() && !Character.isAlphabetic(jogo.charAt(cursor))
                        && !Character.isDigit(jogo.charAt(cursor))
                        && jogo.charAt(cursor) != '"') {
                    cursor++;
                }
                categoria[count++] = parte; // Adiciona ao array e incrementa o contador
            }
            cursor++;
        } else {
            if (count < categoria.length) {
                String parte = "";
                while (cursor < jogo.length() && jogo.charAt(cursor) != ',') {
                    parte += jogo.charAt(cursor);
                    cursor++;
                }
                categoria[count++] = parte; // Adiciona ao array e incrementa o contador
            }
        }
        // Pula a vírgula depois do array (se houver)
        if (cursor < jogo.length() && jogo.charAt(cursor) == ',') {
            cursor++;
        }
        return count; // Retorna o tamanho real
    }
}
