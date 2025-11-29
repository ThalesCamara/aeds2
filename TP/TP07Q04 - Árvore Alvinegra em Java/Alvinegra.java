import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;

class Games {
    // ============================================================================
    // CAMPOS PRINCIPAIS DA ENTIDADE
    // ============================================================================
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

    // ============================================================================
    // CONSTRUTOR PADRÃO (ESTADO INICIAL)
    // ============================================================================
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

    // ============================================================================
    // CONSTRUTOR COMPLETO (PREENCHIMENTO TOTAL)
    // ============================================================================
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

    // ============================================================================
    // MÉTODOS DE ACESSO (GET / SET)
    // ============================================================================
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

    // ============================================================================
    // INTERPRETAÇÃO DE UMA LINHA DO CSV
    // ============================================================================
    public void read(String linhaBruta) {
        // Ajusta a linha substituindo vírgulas “soltas” por ponto e vírgula, mantendo
        // conteúdo entre aspas intacto
        String linhaTratada = "";
        boolean entreAspas = false;
        for (int indice = 0; indice < linhaBruta.length(); indice++) {
            char ch = linhaBruta.charAt(indice);

            if (ch == '"') {
                entreAspas = !entreAspas;
            }

            if (!entreAspas) {
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

        linhaBruta = linhaTratada;

        String partes[] = linhaBruta.split(";");

        try {
            // BLOCO DE CONVERSÃO DOS CAMPOS NUMÉRICOS E BÁSICOS
            setId(Integer.parseInt(partes[0]));
            setName(partes[1]);
            setReleaseDate(normalizeDate(partes[2]));
            setEstimatedOwners(normalizeOwners(partes[3]));
            setPrice(normalizePrice(partes[4]));

            // CONVERSÃO DOS CAMPOS QUE SÃO LISTAS OU OPCIONAIS
            setSupportedLanguages(parseArray(partes[5]));
            setMetacriticScore(normalizeScore(partes[6]));
            setUserScore(normalizeUserScore(partes[7]));
            setAchievements(normalizeAchievements(partes[8]));
            setPublishers(partes.length > 9 ? parseArray(partes[9]) : new String[0]);
            setDevelopers(partes.length > 10 ? parseArray(partes[10]) : new String[0]);
            setCategories(partes.length > 11 ? parseArray(partes[11]) : new String[0]);
            setGenres(partes.length > 12 ? parseArray(partes[12]) : new String[0]);
            setTags(partes.length > 13 ? parseArray(partes[13]) : new String[0]);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException excecaoConversao) {
            System.err.println("Falha ao interpretar a linha: " + linhaBruta);
        }
    }

    private String normalizeDate(String dataBruta) {
        // Garante um formato padrão de data; se vier vazio ou inválido, usa um valor de
        // referência
        if (dataBruta == null || dataBruta.trim().isEmpty()) {
            return "01/01/1970";
        }
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");
            Date dataConvertida = formatoEntrada.parse(dataBruta);
            return formatoSaida.format(dataConvertida);
        } catch (ParseException excecaoData) {
            return "01/01/1970";
        }
    }

    private int normalizeOwners(String proprietariosBrutos) {
        // Extrai apenas dígitos da string de owners e converte para inteiro
        if (proprietariosBrutos == null || proprietariosBrutos.trim().isEmpty()) {
            return 0;
        }
        String apenasNumeros = proprietariosBrutos.replaceAll("[^0-9]", "");
        return apenasNumeros.isEmpty() ? 0 : Integer.parseInt(apenasNumeros);
    }

    private float normalizePrice(String precoBruto) {
        // Trata o campo de preço, lidando com jogo gratuito ou valores vazios
        if (precoBruto == null || precoBruto.trim().isEmpty() || precoBruto.equalsIgnoreCase("Free to Play")) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(precoBruto);
        } catch (NumberFormatException excecaoPreco) {
            return 0.0f;
        }
    }

    private int normalizeScore(String pontuacaoBruta) {
        // Converte o score para inteiro; se não for possível, usa -1 como ausência de
        // dado
        if (pontuacaoBruta == null || pontuacaoBruta.trim().isEmpty()) {
            return -1;
        }
        try {
            return Integer.parseInt(pontuacaoBruta);
        } catch (NumberFormatException excecaoScore) {
            return -1;
        }
    }

    private float normalizeUserScore(String avaliacaoUsuarioBruta) {
        // Interpreta o userScore, desconsiderando valores “tbd” ou vazios
        if (avaliacaoUsuarioBruta == null || avaliacaoUsuarioBruta.trim().isEmpty()
                || avaliacaoUsuarioBruta.equals("tbd")) {
            return -1.0f;
        }
        try {
            return Float.parseFloat(avaliacaoUsuarioBruta);
        } catch (NumberFormatException excecaoUserScore) {
            return -1.0f;
        }
    }

    private int normalizeAchievements(String conquistasBrutas) {
        // Converte a quantidade de achievements para inteiro, assumindo zero na dúvida
        if (conquistasBrutas == null || conquistasBrutas.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(conquistasBrutas);
        } catch (NumberFormatException excecaoAchievements) {
            return 0;
        }
    }

    private String[] parseArray(String campoBruto) {
        // Prepara os campos que representam listas para virarem vetores de String
        if (campoBruto == null || campoBruto.trim().isEmpty() || campoBruto.equals("[]")) {
            return new String[0];
        }

        String conteudoLimpo = campoBruto.replace("[", "").replace("]", "").trim();
        if (conteudoLimpo.isEmpty())
            return new String[0];

        String[] itens = conteudoLimpo.split("\\s*,\\s*");

        // Remove aspas simples apenas das extremidades para não afetar palavras
        // internas
        for (int idx = 0; idx < itens.length; idx++) {
            itens[idx] = itens[idx].trim();
            if (itens[idx].startsWith("'") && itens[idx].endsWith("'") && itens[idx].length() >= 2) {
                itens[idx] = itens[idx].substring(1, itens[idx].length() - 1);
            }
        }

        return itens;
    }

    // ============================================================================
    // LEITURA COMPLETA DO ARQUIVO CSV
    // ============================================================================
    public static Games[] readDb() {
        Games vetorJogos[] = new Games[19000];
        Scanner leitorArquivo = null;

        try {
            leitorArquivo = new Scanner(new FileReader("/tmp/games.csv"));
            leitorArquivo.nextLine(); // pula cabeçalho

            for (int indiceLinha = 0; leitorArquivo.hasNextLine(); indiceLinha++) {
                String linhaLida = leitorArquivo.nextLine();
                Games jogoAtual = new Games();

                try {
                    jogoAtual.read(linhaLida);
                    vetorJogos[indiceLinha] = jogoAtual;
                } catch (Exception excecaoProcessamento) {
                    System.err.println("Problema ao tratar a linha " + (indiceLinha + 2) + ": "
                            + excecaoProcessamento.getMessage());
                }
            }
        } catch (FileNotFoundException excecaoArquivo) {
            System.err.println("Não foi possível localizar o arquivo: " + excecaoArquivo.getMessage());
        } catch (Exception erroGenerico) {
            System.err.println("Ocorrência inesperada durante a leitura: " + erroGenerico.getMessage());
        } finally {
            if (leitorArquivo != null) {
                leitorArquivo.close();
            }
        }
        return vetorJogos;
    }

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

// ================================================= ÁRVORE RUBRO-NEGRA (TP07)
// ==========================================

class NoAN {
    public Games elemento;
    public boolean cor;
    public NoAN esq, dir;

    public NoAN(Games elemento) {
        this(elemento, false, null, null);
    }

    public NoAN(Games elemento, boolean cor) {
        this(elemento, cor, null, null);
    }

    public NoAN(Games elemento, boolean cor, NoAN esq, NoAN dir) {
        this.elemento = elemento;
        this.cor = cor;
        this.esq = esq;
        this.dir = dir;
    }
}

public class Alvinegra {
    private NoAN raiz;

    public Alvinegra() {
        raiz = null;
    }

    public boolean pesquisar(String nomeBusca) {
        return pesquisar(nomeBusca, raiz, nomeBusca + ": =>raiz");
    }

    private boolean pesquisar(String nomeBusca, NoAN noAtual, String trajeto) {
        // Caminho termina aqui: nó nulo indica que o elemento não foi encontrado
        if (noAtual == null) {
            System.out.println(trajeto + " NAO");
            return false;
        } else if (nomeBusca.equals(noAtual.elemento.getName())) {
            // Se o nome do jogo bater, encerramos com sucesso
            System.out.println(trajeto + " SIM");
            return true;
        } else if (nomeBusca.compareTo(noAtual.elemento.getName()) < 0) {
            // Descida para a subárvore esquerda caso o nome procurado seja “menor”
            return pesquisar(nomeBusca, noAtual.esq, trajeto + " esq");
        } else {
            // Caso contrário, seguimos explorando a direita
            return pesquisar(nomeBusca, noAtual.dir, trajeto + " dir");
        }
    }

    public void inserir(Games novoJogo) throws Exception {
        String chaveNome = novoJogo.getName();

        if (raiz == null) {
            // Primeiro elemento insere diretamente na raiz
            raiz = new NoAN(novoJogo);

        } else if (raiz.esq == null && raiz.dir == null) {
            // Situação com apenas a raiz na árvore
            if (chaveNome.compareTo(raiz.elemento.getName()) < 0) {
                raiz.esq = new NoAN(novoJogo);
            } else {
                raiz.dir = new NoAN(novoJogo);
            }

        } else if (raiz.esq == null) {
            // Quando há raiz e filho direito apenas
            if (chaveNome.compareTo(raiz.elemento.getName()) < 0) {
                raiz.esq = new NoAN(novoJogo);

            } else if (chaveNome.compareTo(raiz.dir.elemento.getName()) < 0) {
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = novoJogo;

            } else {
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = raiz.dir.elemento;
                raiz.dir.elemento = novoJogo;
            }
            raiz.esq.cor = raiz.dir.cor = false;

        } else if (raiz.dir == null) {
            // Quando há raiz e filho esquerdo apenas
            if (chaveNome.compareTo(raiz.elemento.getName()) > 0) {
                raiz.dir = new NoAN(novoJogo);

            } else if (chaveNome.compareTo(raiz.esq.elemento.getName()) > 0) {
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = novoJogo;

            } else {
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = novoJogo;
            }
            raiz.esq.cor = raiz.dir.cor = false;

        } else {
            // Estado geral com ambos os filhos preenchidos
            inserir(novoJogo, null, null, null, raiz);
        }
        // A raiz deve sempre permanecer preta
        raiz.cor = false;
    }

    private void inserir(Games novoJogo, NoAN noBisavo, NoAN noAvo, NoAN noPai, NoAN noAtual) throws Exception {
        if (noAtual == null) {
            String chaveNome = novoJogo.getName();
            if (chaveNome.compareTo(noPai.elemento.getName()) < 0) {
                noAtual = noPai.esq = new NoAN(novoJogo, true);
            } else {
                noAtual = noPai.dir = new NoAN(novoJogo, true);
            }
            // Se o pai é vermelho, pode haver quebra de regra da árvore rubro-negra
            if (noPai.cor == true) {
                balancear(noBisavo, noAvo, noPai, noAtual);
            }
        } else {

            // Caso clássico de split de 4-no: pai com dois filhos vermelhos
            if (noAtual.esq != null && noAtual.dir != null && noAtual.esq.cor == true && noAtual.dir.cor == true) {
                noAtual.cor = true;
                noAtual.esq.cor = noAtual.dir.cor = false;
                if (noAtual == raiz) {
                    noAtual.cor = false;
                } else if (noPai.cor == true) {
                    balancear(noBisavo, noAvo, noPai, noAtual);
                }
            }

            String chaveNome = novoJogo.getName();
            int comparacao = chaveNome.compareTo(noAtual.elemento.getName());

            if (comparacao < 0) {
                inserir(novoJogo, noAvo, noPai, noAtual, noAtual.esq);
            } else if (comparacao > 0) {
                inserir(novoJogo, noAvo, noPai, noAtual, noAtual.dir);
            } else {
                throw new Exception("Erro inserir (elemento repetido)!");
            }
        }
    }

    private void balancear(NoAN noBisavo, NoAN noAvo, NoAN noPai, NoAN noAtual) {
        // Ajusta a estrutura da árvore quando aparecem duas arestas vermelhas seguidas
        if (noPai.cor == true) {

            if (noPai.elemento.getName().compareTo(noAvo.elemento.getName()) > 0) {
                if (noAtual.elemento.getName().compareTo(noPai.elemento.getName()) > 0) {
                    noAvo = rotacaoEsq(noAvo);
                } else {
                    noAvo = rotacaoDirEsq(noAvo);
                }
            } else {
                if (noAtual.elemento.getName().compareTo(noPai.elemento.getName()) < 0) {
                    noAvo = rotacaoDir(noAvo);
                } else {
                    noAvo = rotacaoEsqDir(noAvo);
                }
            }

            // Reconecta o avo ao bisavô ou à raiz
            if (noBisavo == null) {
                raiz = noAvo;
            } else if (noAvo.elemento.getName().compareTo(noBisavo.elemento.getName()) < 0) {
                noBisavo.esq = noAvo;
            } else {
                noBisavo.dir = noAvo;
            }

            // Corrige as cores após a rotação
            noAvo.cor = false;
            if (noAvo.esq != null)
                noAvo.esq.cor = true;
            if (noAvo.dir != null)
                noAvo.dir.cor = true;
        }
    }

    private NoAN rotacaoDir(NoAN noPivo) {
        // Rotação simples para a direita
        NoAN noEsquerdo = noPivo.esq;
        NoAN subArvoreDireitaEsq = noEsquerdo.dir;

        noEsquerdo.dir = noPivo;
        noPivo.esq = subArvoreDireitaEsq;

        return noEsquerdo;
    }

    private NoAN rotacaoEsq(NoAN noPivo) {
        // Rotação simples para a esquerda
        NoAN noDireito = noPivo.dir;
        NoAN subArvoreEsquerdaDir = noDireito.esq;

        noDireito.esq = noPivo;
        noPivo.dir = subArvoreEsquerdaDir;

        return noDireito;
    }

    private NoAN rotacaoDirEsq(NoAN noPivo) {
        // Rotação dupla: direita no filho, esquerda no pivô
        noPivo.dir = rotacaoDir(noPivo.dir);
        return rotacaoEsq(noPivo);
    }

    private NoAN rotacaoEsqDir(NoAN noPivo) {
        // Rotação dupla: esquerda no filho, direita no pivô
        noPivo.esq = rotacaoEsq(noPivo.esq);
        return rotacaoDir(noPivo);
    }

    private static Games getById(Games[] vetorJogos, int idProcurado) {
        // Percorre o vetor de jogos até encontrar o ID solicitado
        for (int indice = 0; indice < vetorJogos.length; indice++) {
            if (vetorJogos[indice] != null && vetorJogos[indice].getId() == idProcurado) {
                return vetorJogos[indice];
            }
        }
        return null;
    }

    // ===================== FUNÇÃO DE LOG: CRIA / ATUALIZA matrícula_alvinegra.txt
    // =====================
    private static void registrarLog(String mensagemLog) {
        String nomeArquivoLog = "885470_alvinegra.txt";
        try (FileWriter escritorLog = new FileWriter(nomeArquivoLog, true)) {
            escritorLog.write(mensagemLog + System.lineSeparator());
        } catch (IOException erroLog) {
            System.err.println("Não foi possível registrar a mensagem de log: " + erroLog.getMessage());
        }
    }

    // ===================== MAIN: ENTRADA / SAÍDA CONFORME ESPECIFICAÇÃO
    // =====================

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Games[] baseJogos = Games.readDb();

        Alvinegra arvoreAlvinegra = new Alvinegra();

        String idLido = entrada.nextLine();
        while (!idLido.equals("FIM")) {
            int idJogo = Integer.parseInt(idLido.trim());
            Games jogoEncontrado = getById(baseJogos, idJogo);
            if (jogoEncontrado != null) {
                try {
                    arvoreAlvinegra.inserir(jogoEncontrado);
                } catch (Exception e) {
                    // Em caso de inserção repetida ou erro estrutural, apenas ignora a inserção
                }
            }
            idLido = entrada.nextLine();
        }

        String nomeBusca = entrada.nextLine();
        while (!nomeBusca.equals("FIM")) {
            arvoreAlvinegra.pesquisar(nomeBusca);
            nomeBusca = entrada.nextLine();
        }

        entrada.close();

        // Registro simples no arquivo de log ao final da execução
        long instanteAtual = System.currentTimeMillis();
        registrarLog("Execução finalizada em milissegundos: " + instanteAtual);
    }
}