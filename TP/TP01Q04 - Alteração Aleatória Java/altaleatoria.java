import java.util.Random;

class altaleatoria {

    // Substitui as letras sorteadas
    public static String alteracao(String str, int n, Random gerador){
        String palavra = "";
        // Gera duas letras minúsculas
        char x = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        char y = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        for(int i = 0; i < n; i++){
            // Substitui a letra sorteada 'x' por 'y'
            if(str.charAt(i) == x){
                palavra += y;
            }
            else{
                palavra += str.charAt(i);
            }
        }
        return palavra;
    }

    public static void main(String[] args) {
        Random gerador = new Random(4); // Define a semente do sorteio
        String str = MyIO.readLine();
        // Processa cada linha até encontrar "FIM"
        while (!(str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M')){
            int n = str.length();
            MyIO.println(alteracao(str, n, gerador));
            str = MyIO.readLine();
        }
    }
}
