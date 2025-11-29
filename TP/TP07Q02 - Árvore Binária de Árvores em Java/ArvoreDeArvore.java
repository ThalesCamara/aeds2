import java.util.*;
import java.io.*;
import java.text.*;

class Games {
    // Atributos do jogo
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

    // Construtor completo
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

    // Getters e setters
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

    // Trata uma linha do CSV e preenche o objeto
    public void read(String linhaBruta) {
        String linhaTratada = "";
        boolean dentroDeAspas = false;

        for (int idx = 0; idx < linhaBruta.length(); idx++) {
            char ch = linhaBruta.charAt(idx);

            if (ch == '"') {
                dentroDeAspas = !dentroDeAspas; // alterna estado
            }

            if (!dentroDeAspas) {
                if (ch == ',') {
                    linhaTratada += ';';
                } else if (ch != '\"') {
                    linhaTratada += ch;
                }
            } else {
                if (ch != '"' && ch != '[' && ch != ']') {
                    linhaTratada += ch;
                }
            }
        }

        String[] campos = linhaTratada.split(";");

        try {
            // Conversões principais
            setId(Integer.parseInt(campos[0]));
            setName(campos[1]);
            setReleaseDate(normalizeDate(campos[2]));
            setEstimatedOwners(normalizeOwners(campos[3]));
            setPrice(normalizePrice(campos[4]));

            // Listas
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
            System.err.println("Erro ao processar a linha: " + linhaTratada);
        }
    }

    // Normaliza data
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

    // Normaliza owners (só números)
    private int normalizeOwners(String ownersBruto) {
        if (ownersBruto == null || ownersBruto.trim().isEmpty()) {
            return 0;
        }
        String numeros = ownersBruto.replaceAll("[^0-9]", "");
        return numeros.isEmpty() ? 0 : Integer.parseInt(numeros);
    }

    // Normaliza preço
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

    // Normaliza score (int)
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

    // Normaliza userScore (float)
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

    // Normaliza achievements
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

    // Converte campo de lista em array
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

    // Lê o arquivo de jogos e monta o vetor
    public static Games[] readDb() {
        Games[] jogos = new Games[19000];
        Scanner leitor = null;

        try {
            leitor = new Scanner(new FileReader("/tmp/games.csv"));
            leitor.nextLine(); // pula cabeçalho

            for (int idx = 0; leitor.hasNextLine(); idx++) {
                String linha = leitor.nextLine();
                Games jogo = new Games();

                try {
                    jogo.read(linha);
                    jogos[idx] = jogo;
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha " + (idx + 2) + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            if (leitor != null) {
                leitor.close();
            }
        }
        return jogos;
    }

    // Impressão completa (se precisar depurar)
    public void print() {
        System.out.print(this.id + " ## " + this.name + " ## " + this.releasedate + " ## " + this.estimatedOwners +
                " ## " + this.price + " ## " + Arrays.toString(this.supportedLanguages) + " ## " + this.metacriticScore
                + " ## " +
                this.userScore + " ## " + this.achievements + " ## " + Arrays.toString(this.publishers) + " ## "
                + Arrays.toString(this.developers) + " ## "
                + Arrays.toString(this.categories) + " ## " + Arrays.toString(this.genres) + " ## "
                + Arrays.toString(this.tags) + " ##");
        System.out.println();
    }
}

// Nó da primeira árvore (índice)
class No1 {
    int chave;
    No1 esq, dir;
    No2 raiz; // raiz da segunda árvore

    public No1(int chave) {
        this.chave = chave;
        this.esq = this.dir = null;
        this.raiz = null;
    }
}

// Nó da segunda árvore (jogos por nome)
class No2 {
    String nome;
    Games jogo;
    No2 esq, dir;

    public No2(String nome, Games jogo) {
        this.nome = nome;
        this.jogo = jogo;
        this.esq = this.dir = null;
    }
}

public class ArvoreDeArvore {
    private No1 raiz1;

    public ArvoreDeArvore() {
        raiz1 = null;
    }

    // Insere nó na primeira árvore (por chave)
    public void inserirNo1(int chave) {
        raiz1 = inserirNo1(raiz1, chave);
    }

    public No1 inserirNo1(No1 noAtual, int chave) {
        if (noAtual == null)
            return new No1(chave);
        if (chave < noAtual.chave)
            noAtual.esq = inserirNo1(noAtual.esq, chave);
        else if (chave > noAtual.chave)
            noAtual.dir = inserirNo1(noAtual.dir, chave);
        return noAtual;
    }

    // Busca na primeira árvore
    public No1 buscarNo1(No1 noAtual, int chave) {
        if (noAtual == null)
            return null;
        if (chave == noAtual.chave)
            return noAtual;
        if (chave < noAtual.chave)
            return buscarNo1(noAtual.esq, chave);
        return buscarNo1(noAtual.dir, chave);
    }

    // Insere na segunda árvore (por nome)
    public No2 inserirNo2(No2 noAtual, String nomeJogo, Games jogo) {
        if (noAtual == null)
            return new No2(nomeJogo, jogo);
        if (nomeJogo.compareTo(noAtual.nome) < 0)
            noAtual.esq = inserirNo2(noAtual.esq, nomeJogo, jogo);
        else if (nomeJogo.compareTo(noAtual.nome) > 0)
            noAtual.dir = inserirNo2(noAtual.dir, nomeJogo, jogo);
        return noAtual;
    }

    // Insere jogo na estrutura (usa estimatedOwners % 15)
    public void inserirJogo(Games jogo) {
        int chave1 = jogo.getEstimatedOwners() % 15;
        No1 noIndice = buscarNo1(raiz1, chave1);
        if (noIndice != null) {
            noIndice.raiz = inserirNo2(noIndice.raiz, jogo.getName(), jogo);
        }
    }

    // Pesquisa jogo pelo nome (imprime caminho)
    public void pesquisarJogo(String nome) {
        System.out.print("=> " + nome + " => raiz ");
        if (pesquisarNo1(raiz1, nome)) {
            System.out.println(" SIM");
        } else {
            System.out.println(" NAO");
        }
    }

    // Pesquisa na primeira árvore, varrendo as segundas árvores
    private boolean pesquisarNo1(No1 noAtual, String nome) {
        boolean achou = false;
        if (noAtual != null) {
            // primeiro tenta na segunda árvore do nó atual
            achou = pesquisarNo2(noAtual.raiz, nome);

            // se não achou, vai para a esquerda
            if (!achou) {
                System.out.print(" ESQ ");
                achou = pesquisarNo1(noAtual.esq, nome);
            }

            // se ainda não achou, vai para a direita
            if (!achou) {
                System.out.print(" DIR ");
                achou = pesquisarNo1(noAtual.dir, nome);
            }
        }
        return achou;
    }

    // Pesquisa na segunda árvore (por nome)
    public boolean pesquisarNo2(No2 noAtual, String nome) {
        if (noAtual == null)
            return false;

        if (nome.equals(noAtual.nome)) {
            return true;
        } else if (nome.compareTo(noAtual.nome) < 0) {
            System.out.print("esq ");
            return pesquisarNo2(noAtual.esq, nome);
        } else {
            System.out.print("dir ");
            return pesquisarNo2(noAtual.dir, nome);
        }
    }

    // Log simples no arquivo pedido
    private static void registrarLog(String texto) {
        try (FileWriter fw = new FileWriter("88_aVaV.txt", true)) {
            fw.write(texto + System.lineSeparator());
        } catch (IOException e) {
            // não interrompe a execução se o log falhar
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Games[] jogos = Games.readDb();

        ArvoreDeArvore arvore = new ArvoreDeArvore();

        // Monta a primeira árvore com as chaves fixas
        int[] chavesFixas = { 7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14 };
        for (int chave : chavesFixas) {
            arvore.inserirNo1(chave);
        }

        // Primeira parte: leitura de IDs
        String linha = scanner.nextLine();
        while (!linha.equals("FIM")) {
            int id = Integer.parseInt(linha.trim());
            for (int i = 0; i < jogos.length; i++) {
                if (jogos[i] != null && jogos[i].getId() == id) {
                    arvore.inserirJogo(jogos[i]);
                    break;
                }
            }
            linha = scanner.nextLine();
        }

        // Segunda parte: pesquisa por nome
        linha = scanner.nextLine();
        while (!linha.equals("FIM")) {
            arvore.pesquisarJogo(linha);
            linha = scanner.nextLine();
        }

        scanner.close();

        // Escreve no log ao final
        registrarLog("Execucao ArvoreDeArvore finalizada em ms: " + System.currentTimeMillis());
    }
}