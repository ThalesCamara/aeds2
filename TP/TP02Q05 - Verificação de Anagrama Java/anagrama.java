public class anagrama {
    
    public static String ordenar(String str) {
        int tam = str.length();
        char[] string = new char[tam];

        for (int i = 0; i < tam; i++) {
            string[i] = str.charAt(i);
        }

        for (int i = 0; i < (tam - 1); i++) {
            int menor = i;
            for (int j = (i + 1); j < tam; j++) {
                if (string[menor] > string[j]) {
                    menor = j;
                }
            }
            char temp = string[i];
            string[i] = string[menor];
            string[menor] = temp;
        }

        String resultado = "";
        for (int i = 0; i < tam; i++) {
            resultado += string[i];
        }

        return resultado;
    }

    public static boolean ehAnagrama(String str){
        String str1 = "";
        String str2 = "";
        int i = 0;
        int tam = str.length();

        while(i < tam && str.charAt(i) != ' '){
            str1 += str.charAt(i);
            i++;
        }

        i += 3;

        for(int j = i; j < tam; j++){
            str2 += str.charAt(j);
        }

        String ordenado1 = ordenar(str1);
        String ordenado2 = ordenar(str2);
        return ordenado1.equals(ordenado2);
    }

    public static void main(String[] args) {
        String str = MyIO.readLine();

        while(!str.equals("FIM")){
            String strmin = str.toLowerCase();
            if(ehAnagrama(strmin)){
                MyIO.println("SIM");
            } else {
                MyIO.println("NÃO");
            }
            str = MyIO.readLine();
        }
    }
}
