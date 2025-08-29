class CiframentoCesar{
    
    public static String cifra(String str, int n){ 
        String codigo = "";
        for(int i = 0; i<n; i++){
            char letra = (char)(str.charAt(i) + 3); // aplica a cifra de cesar na letra
            codigo = codigo + letra; //concatena o caractere na string
        }
        return codigo;
    }
    
    public static void main(String[] args) {
        String str = MyIO.readLine(); // Lê a string
        while(!str.equals("FIM")){ // executa enquanto a palavra não é "FIM"
            int n = str.length();
            MyIO.println(cifra(str, n)); // aplica a cifra de cesar e imprime a string
            str = MyIO.readLine();
        }
    }
}