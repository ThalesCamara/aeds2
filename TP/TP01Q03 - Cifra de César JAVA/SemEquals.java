//codigo final cifra cesar sem usar equals Thales Duque Câmara
class SemEquals{

    public static String cifra(String str, int n){ 
        String codigo = "";
        for(int i = 0; i<n; i++){
            char letra = (char)(str.charAt(i) + 3); // aplica a cifra de cesar na letra
            codigo = codigo + letra; //concatena o caractere na string
        }
        return codigo;
    }

    // Função para comparar se a string é igual a "FIM"
    public static boolean ehFim(String str) {
        if (str.length() != 3) return false;
        return str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M';
    }

    public static void main(String[] args) {
        String str = MyIO.readLine(); // Lê a string
        while(!ehFim(str)){ // executa enquanto a palavra não é "FIM"
            int n = str.length();
            MyIO.println(cifra(str, n)); // aplica a cifra de cesar e imprime a string
            str = MyIO.readLine();
        }
    }
}