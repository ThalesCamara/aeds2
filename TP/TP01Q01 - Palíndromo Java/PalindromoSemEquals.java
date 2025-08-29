class PalindromoSemEquals{
    
    public static boolean ehPalindromo(String str, int n){ // funcao para verificar palindromo
        boolean palindromo = true;
        for(int i = 0; i < n/2; i++){
            if(str.charAt(i)!=str.charAt(n-1-i)){ // compara se as letras "espelhadas" sao iguais
                palindromo = false;
                i = n/2; // encerra o laço for
            }
        }
        return palindromo;
    }
    
    public static void main (String[] args){
        String str = MyIO.readLine();
        while (!(str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M')){ // executa enquanto a palavra nao "FIM"
            int n = str.length();
            if(ehPalindromo(str, n)){
                MyIO.println("SIM");
            } else {
                MyIO.println("NAO");
            }
            str = MyIO.readLine();
        }
    }
}