import java.util.Scanner;

public class torneio{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char array[] = new char[6];
        int cont = 0;
        for(int i=0;i<6;i++){
            array[i]= sc.nextLine().charAt(0);
            if (array[i] == 'V') cont ++;
        }
        if (cont == 1 || cont == 2) System.out.println("3");
        else if (cont == 3 || cont == 4) System.out.println("2");
        else if (cont == 5 || cont == 6) System.out.println("1");
        sc.close();
    }
}