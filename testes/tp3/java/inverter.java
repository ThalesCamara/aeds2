import java.util.Scanner;

public class inverter {

    public static void inverteStringRec(String str, int i, int j) {
        // Continua enquanto ainda houver caracteres à direita para imprimir.
        if (j >= i) {
            System.out.print(str.charAt(j));
            inverteStringRec(str, i, j - 1);
        }
    }
    
    public static void inverteString(String str) {
        inverteStringRec(str, 0, str.length() - 1);
        
        // Adiciona uma quebra de linha depois de mostrar a string ao contrário.
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String str = sc.nextLine();
        
        // O laço só termina quando a palavra digitada for "FIM".
        while (!str.equals("FIM")) {
            inverteString(str);
            str = sc.nextLine();
        }
        
        sc.close();
    }
}
