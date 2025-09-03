class inversao{

    // Inverte a string recebida
    public static String inverte(String str, int n){
        String palavra = "";
        for(int i = 0; i< n; i++){
            char c = str.charAt(n - 1 - i);
            palavra += c;
        }
        return palavra;
    }

    public static void main(String[] args) {
        String str = MyIO.readLine();
        // Processa até encontrar "FIM"
        while(!(str.length() == 3 && str.charAt(0) == 'F' && str.charAt(1) == 'I' && str.charAt(2) == 'M')){
            int n = str.length();
            MyIO.println(inverte(str, n));
            str = MyIO.readLine();
        }
    }
}