import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;

class Games {
    private int id;
    private String name;
    private String releasedate;
    private int estimatedOwners;
    private float price;
    private String[] supportedLanguages;
    private int metacriticScore;
    private float userScore;
    private int achievements;
    private String[] publishers;
    private String[] developers;
    private String[] categories;
    private String[] genres;
    private String[] tags;

    // Construtor vazio
    public Games() {
        this.id = 0;
        this.name = "";
        this.releasedate = "";
        this.estimatedOwners = 0;
        this.price = 0.0f;
        this.supportedLanguages = new String[0];
        this.metacriticScore = -1;
        this.userScore = -1.0f;
        this.achievements = 0;
        this.publishers = new String[0];
        this.developers = new String[0];
        this.categories = new String[0];
        this.genres = new String[0];
        this.tags = new String[0];
    }

    // Construtor com todos os campos
    public Games(int id, String name, String releasedate, int estimatedOwners, float price, String[] supportedLanguages,
            int metacriticScore, float userScore, int achievements, String[] publishers, String[] developers,
            String[] categories, String[] genres, String[] tags) {
        this.id = id;
        this.name = name;
        this.releasedate = releasedate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;
        this.supportedLanguages = supportedLanguages;
        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
        this.publishers = publishers;
        this.developers = developers;
        this.categories = categories;
        this.genres = genres;
        this.tags = tags;
    }

    // Getters e setters básicos
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReleaseDate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getReleaseDate() {
        return releasedate;
    }

    public void setEstimatedOwners(int estimatedOwners) {
        this.estimatedOwners = estimatedOwners;
    }

    public int getEstimatedOwners() {
        return estimatedOwners;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setSupportedLanguages(String[] supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public float getUserScore() {
        return userScore;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }

    public int getAchievements() {
        return achievements;
    }

    public void setPublishers(String[] publishers) {
        this.publishers = publishers;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public void setDevelopers(String[] developers) {
        this.developers = developers;
    }

    public String[] getDevelopers() {
        return developers;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getTags() {
        return tags;
    }

    // Lê uma linha do CSV e preenche o objeto
    public void read(String linhaOriginal) {
        String linhaProcessada = "";
        boolean dentroDeAspas = false;

        for (int i = 0; i < linhaOriginal.length(); i++) {
            char c = linhaOriginal.charAt(i);

            if (c == '"') {
                dentroDeAspas = !dentroDeAspas;
            }

            if (!dentroDeAspas) {
                if (c == ',') {
                    linhaProcessada += ';';
                } else if (c != '\"') {
                    linhaProcessada += c;
                }
            } else {
                if (c != '"' && c != '[' && c != ']') {
                    linhaProcessada += c;
                }
            }
        }

        String linhaFinal = linhaProcessada;
        String[] campos = linhaFinal.split(";");

        try {
            setId(Integer.parseInt(campos[0]));
            setName(campos[1]);
            setReleaseDate(normalizeDate(campos[2]));
            setEstimatedOwners(normalizeOwners(campos[3]));
            setPrice(normalizePrice(campos[4]));
            setSupportedLanguages(parseArray(campos[5]));
            setMetacriticScore(normalizeScore(campos[6]));
            setUserScore(normalizeUserScore(campos[7]));
            setAchievements(normalizeAchievements(campos[8]));
            setPublishers(campos.length > 9 ? parseArray(campos[9]) : new String[0]);
            setDevelopers(campos.length > 10 ? parseArray(campos[10]) : new String[0]);
            setCategories(campos.length > 11 ? parseArray(campos[11]) : new String[0]);
            setGenres(campos.length > 12 ? parseArray(campos[12]) : new String[0]);
            setTags(campos.length > 13 ? parseArray(campos[13]) : new String[0]);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erro ao interpretar a linha: " + linhaFinal);
        }
    }

    // Ajusta a data para dd/MM/yyyy
    private String normalizeDate(String dataBruta) {
        if (dataBruta == null || dataBruta.trim().isEmpty()) {
            return "01/01/1970";
        }
        try {
            SimpleDateFormat input = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            Date data = input.parse(dataBruta);
            return output.format(data);
        } catch (ParseException e) {
            return "01/01/1970";
        }
    }

    // Pega só os números de owners
    private int normalizeOwners(String ownersBruto) {
        if (ownersBruto == null || ownersBruto.trim().isEmpty()) {
            return 0;
        }
        String numeros = ownersBruto.replaceAll("[^0-9]", "");
        return numeros.isEmpty() ? 0 : Integer.parseInt(numeros);
    }

    // Trata o preço
    private float normalizePrice(String precoBruto) {
        if (precoBruto == null || precoBruto.trim().isEmpty() || precoBruto.equalsIgnoreCase("Free to Play")) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(precoBruto);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    // Trata o score (int)
    private int normalizeScore(String scoreBruto) {
        if (scoreBruto == null || scoreBruto.trim().isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(scoreBruto);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Trata o userScore (float)
    private float normalizeUserScore(String userScoreBruto) {
        if (userScoreBruto == null || userScoreBruto.trim().isEmpty() || userScoreBruto.equals("tbd")) {
            return -1.0f;
        }
        try {
            return Float.parseFloat(userScoreBruto);
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    // Trata achievements
    private int normalizeAchievements(String achievementsBruto) {
        if (achievementsBruto == null || achievementsBruto.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(achievementsBruto);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Converte campo com lista para array de String
    private String[] parseArray(String campoBruto) {
        if (campoBruto == null || campoBruto.trim().isEmpty() || campoBruto.equals("[]")) {
            return new String[0];
        }

        String conteudo = campoBruto.replace("[", "").replace("]", "").trim();
        if (conteudo.isEmpty())
            return new String[0];

        String[] partes = conteudo.split("\\s*,\\s*");

        for (int i = 0; i < partes.length; i++) {
            partes[i] = partes[i].trim();
            if (partes[i].startsWith("'") && partes[i].endsWith("'") && partes[i].length() >= 2) {
                partes[i] = partes[i].substring(1, partes[i].length() - 1);
            }
        }
        return partes;
    }

    // Lê o arquivo /tmp/games.csv e devolve o vetor de jogos
    public static Games[] readDb() {
        Games[] jogos = new Games[19000];
        Scanner leitorCsv = null;

        try {
            leitorCsv = new Scanner(new FileReader("/tmp/games.csv"));
            leitorCsv.nextLine(); // pula cabeçalho

            for (int i = 0; leitorCsv.hasNextLine(); i++) {
                String linha = leitorCsv.nextLine();
                Games jogo = new Games();

                try {
                    jogo.read(linha);
                    jogos[i] = jogo;
                } catch (Exception e) {
                    System.err.println("Erro na linha " + (i + 2) + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            if (leitorCsv != null) {
                leitorCsv.close();
            }
        }
        return jogos;
    }
}

// Nó da árvore
class No {
    Games elemento;
    No esq, dir;

    public No(Games elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }
}

public class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    // Chama a pesquisa com o caminho inicial
    public boolean pesquisar(String nomeBusca) {
        return pesquisar(nomeBusca, raiz, nomeBusca + ": =>raiz");
    }

    // Pesquisa recursiva na árvore
    private boolean pesquisar(String nomeBusca, No noAtual, String caminho) {
        if (noAtual == null) {
            System.out.println(caminho + " NAO");
            return false;
        } else if (nomeBusca.equals(noAtual.elemento.getName())) {
            System.out.println(caminho + " SIM");
            return true;
        } else if (nomeBusca.compareTo(noAtual.elemento.getName()) < 0) {
            return pesquisar(nomeBusca, noAtual.esq, caminho + " esq");
        } else {
            return pesquisar(nomeBusca, noAtual.dir, caminho + " dir");
        }
    }

    // Insere um jogo na árvore
    public void inserir(Games novoJogo) {
        raiz = inserir(novoJogo, raiz);
    }

    // Inserção recursiva
    private No inserir(Games novoJogo, No noAtual) {
        if (noAtual == null) {
            noAtual = new No(novoJogo);
        } else if (novoJogo.getName().compareTo(noAtual.elemento.getName()) < 0) {
            noAtual.esq = inserir(novoJogo, noAtual.esq);
        } else if (novoJogo.getName().compareTo(noAtual.elemento.getName()) > 0) {
            noAtual.dir = inserir(novoJogo, noAtual.dir);
        }
        return noAtual;
    }

    // Procura jogo pelo id no vetor
    private static Games getById(Games[] jogos, int idProcurado) {
        for (int i = 0; i < jogos.length; i++) {
            if (jogos[i] != null && jogos[i].getId() == idProcurado) {
                return jogos[i];
            }
        }
        return null;
    }

    // Escreve uma linha no arquivo de log
    private static void registrarLog(String texto) {
        try (FileWriter fw = new FileWriter("88_binaria.txt", true)) {
            fw.write(texto + System.lineSeparator());
        } catch (IOException e) {
            // silencioso para não atrapalhar a saída do exercício
        }
    }

    public static void main(String[] args) {
        ArvoreBinaria arvore = new ArvoreBinaria();
        Scanner in = new Scanner(System.in);

        Games[] db = Games.readDb();

        // Leitura dos IDs para inserção
        String entradaId = in.nextLine();
        while (!entradaId.equals("FIM")) {
            int id = Integer.parseInt(entradaId);
            Games jogo = getById(db, id);
            if (jogo != null) {
                arvore.inserir(jogo);
            }
            entradaId = in.nextLine();
        }

        // Leitura dos nomes para pesquisa
        String nome = in.nextLine();
        while (!nome.equals("FIM")) {
            arvore.pesquisar(nome);
            nome = in.nextLine();
        }

        // Log simples no final da execução
        registrarLog("Execucao finalizada em ms: " + System.currentTimeMillis());

        in.close();
    }
}