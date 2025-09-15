import java.util.Scanner;

public class somador {
    
    public static int somadorDeDigitos(int num) {
        // Caso base: se o número for zero, não há nada a somar.
        if (num == 0) {
            return 0;
        } else {
            // Soma o último dígito com o resultado da chamada recursiva no restante do número.
            return (num % 10) + somadorDeDigitos(num / 10);
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        // Continua processando enquanto houver números inteiros como entrada.
        while(sc.hasNextInt()){
            int num = sc.nextInt();
            int resp = somadorDeDigitos(num);
            System.out.println(resp);
        }
        
        sc.close();
    }
}
