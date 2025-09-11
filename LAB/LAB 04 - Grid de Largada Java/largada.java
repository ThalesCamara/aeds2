
public class largada {
    

    public static void swap(int i, int j, int[] posicoes){
        int tmp = posicoes[i];
        posicoes[i] = posicoes[j];
        posicoes[j] = tmp;
    }
    
    public static int sort(int[] posicoes, int n) {
        int contador = 0;
		for (int i = (n - 1); i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (posicoes[j] > posicoes[j + 1]) {
               swap(j, j+1, posicoes);
               contador++;
				}
			}
		}
        return contador;
   }

    public static void main(String[] args) {
        int n = MyIO.readInt();
        String largada = MyIO.readLine();
        String chegada = MyIO.readLine();
        int[] largadaArray = new int[n];
        int[] chegadaArray = new int[n];
        int contador = 0;
        int [] posicoes = new int[n];
        for(int i = 0; i<n; i++){
            largadaArray[i] = largada.charAt(contador) - '0';
            contador+=2;
        }
        contador = 0;
        for(int i = 0; i<n; i++){
            chegadaArray[i] = chegada.charAt(contador) - '0';
            contador+=2;
        }
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(largadaArray[i] == chegadaArray[j]){
                    posicoes[j] = largadaArray[i];
                }
            }
        }
        int ultrapassagens = sort(posicoes, n);
        MyIO.println(ultrapassagens);
    }
}
