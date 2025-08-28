import java.util.Scanner;

public class lab02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int a = sc.nextInt();
            if (!sc.hasNextInt()) break; // se não houver par para 'b', sai sem erro
            int b = sc.nextInt();

            System.out.println("Valores de A e B = " + a + " " + b);
        }
        sc.close();
    }
}