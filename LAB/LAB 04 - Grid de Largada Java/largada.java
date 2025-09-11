import java.util.Scanner;

public class largada {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n = sc.nextInt();
        int[] Largada = new int[n];
        int[] Chegada = new int[n];
        for(int i = 0; i<n; i++){
            Largada[i] = sc.nextInt();
        }
        for(int i = 0; i<n; i++){
            Chegada[i] = sc.nextInt();
        }
        sc.close();
    }
}
