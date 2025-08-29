class Palindromo{
    
    public static boolean ehPalindromo(String str, int n){
        boolean palindromo = true;
        for(int i = 0; i < n/2; i++){
            if(str.charAt(i)!=str.charAt(n-1-i)){
                palindromo = false;
                i = n/2;
            }
        }
        return palindromo;
    }
    
    public static void main (String[] args){
        String str = MyIO.readLine();
        while (!str.equals("FIM")){
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