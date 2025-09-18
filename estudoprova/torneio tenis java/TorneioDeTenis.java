import java.util.Scanner;

public class TorneioDeTenis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int vitorias = 0;

        for (int i = 0; i < 6; i++) {
            String resultado = sc.nextLine();
            if (resultado.equals("V")) {
                vitorias++;
            }
        }

        if (vitorias == 5 || vitorias == 6) {
            System.out.println(1);
        } else if (vitorias == 3 || vitorias == 4) {
            System.out.println(2);
        } else if (vitorias == 1 || vitorias == 2) {
            System.out.println(3);
        } else {
            System.out.println(-1);
        }
        sc.close();
    }
}