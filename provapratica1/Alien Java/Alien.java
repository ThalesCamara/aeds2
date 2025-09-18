import java.util.Scanner;

class Alien{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //leitura dos dados
        int K = sc.nextInt();
        int N = sc.nextInt(); 
        sc.nextLine(); // evita erros de leitura
        String alfabeto = sc.nextLine();
        String mensagem = sc.nextLine();
        boolean pertence = true;
        for(int i=0; i< N; i++){ // laco para verificar se a letra pertence ao alfabeto
            if(!pertence(alfabeto, mensagem.charAt(i), K)){ 
                pertence = false;
            }
        }
        if(pertence) System.out.println("S");
        else System.out.println("N");
        sc.close();
    }

    public static boolean pertence(String alfabeto, char letra, int K){ // funcao para verificar se a letra está no alfabeto
        boolean semelhanca = false;
        for(int i = 0; i< K; i++){ // laco que verifica se a letra está no alfabeto
            if(letra == alfabeto.charAt(i)){
                semelhanca = true;
            }
        }
        return(semelhanca);
    }
}