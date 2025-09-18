import java.util.Scanner;

class Medalhas {
    String pais;
    int ouro;
    int prata;
    int bronze;

    public Medalhas(String pais, int ouro, int prata, int bronze) {
        this.pais = pais;
        this.ouro = ouro;
        this.prata = prata;
        this.bronze = bronze;
    }
}

public class quadroMedalhas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int quantidade = scanner.nextInt();
        scanner.nextLine();

        Medalhas[] medalhas = new Medalhas[quantidade];

        for (int i = 0; i < quantidade; i++) {
            String pais = scanner.next();
            int ouro = scanner.nextInt();
            int prata = scanner.nextInt();
            int bronze = scanner.nextInt();
            medalhas[i] = new Medalhas(pais, ouro, prata, bronze);
        }

        // Ordenação manual (Bubble Sort)
        for (int i = 0; i < quantidade - 1; i++) {
            for (int j = 0; j < quantidade - 1 - i; j++) {
                if (precisaTrocar(medalhas[j], medalhas[j + 1])) {
                    Medalhas temp = medalhas[j];
                    medalhas[j] = medalhas[j + 1];
                    medalhas[j + 1] = temp;
                }
            }
        }

        // Impressão sem toString()
        for (int i = 0; i < quantidade; i++) {
            System.out.println(
                medalhas[i].pais + " " + 
                medalhas[i].ouro + " " + 
                medalhas[i].prata + " " + 
                medalhas[i].bronze
            );
        }

        scanner.close();
    }

    // Função que decide quem vem primeiro
    public static boolean precisaTrocar(Medalhas a, Medalhas b) {
        if (a.ouro < b.ouro) return true;
        if (a.ouro > b.ouro) return false;

        if (a.prata < b.prata) return true;
        if (a.prata > b.prata) return false;

        if (a.bronze < b.bronze) return true;
        if (a.bronze > b.bronze) return false;

        // Critério alfabético manual
        int minLen = Math.min(a.pais.length(), b.pais.length());
        for (int i = 0; i < minLen; i++) {
            char ca = a.pais.charAt(i);
            char cb = b.pais.charAt(i);
            if (ca > cb) return true;   // a vem depois de b, precisa trocar
            if (ca < cb) return false;  // a vem antes de b, não troca
        }
        // Se todos os caracteres são iguais até o menor tamanho, o maior nome vem depois
        return a.pais.length() > b.pais.length();
    }
}