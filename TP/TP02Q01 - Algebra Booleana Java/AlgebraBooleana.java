
import java.util.Scanner;

public class AlgebraBooleana {
    private static final Scanner scanner = new Scanner(System.in);


    //Verifica se o caractere é uma letra maiúscula válida 
    static boolean isLetter(char c){
        return (c >= 'A' && c <= 'Z');
    }//Fim da função isLette

    //Converte letra A..Z no índice do vetor de valores 
    static int letterToIndex(char c){
        return (c - 'A');
    }//Fim da função letterToIndex

    //Pula espaços em branco na string
    static int skipSpaces(String s, int i){
        while(i < s.length() && s.charAt(i) == ' '){
            i++;
        }//Fim do while
        return i;
    }//Fim da função skipSpaces

    static Result eval(String expr, int i, int[] values){
        i = skipSpaces(expr, i);

        // Caso 1: variável simples
        if(isLetter(expr.charAt(i))){
            int val = values[letterToIndex(expr.charAt(i))];
            return new Result(val == 1, i + 1);
        }//Fim do if variável

        // Caso 2: not(...)
        if(expr.charAt(i) == 'n'){ // not
            i += 3; // pula "not"
            i = skipSpaces(expr, i);
            if(expr.charAt(i) == '(') i++;
            Result r = eval(expr, i, values);
            i = r.pos;
            i = skipSpaces(expr, i);
            if(expr.charAt(i) == ')') i++;
            return new Result(!r.value, i);
        }//Fim do if not

        // Caso 3: and(...) com múltiplos argumentos
        if(expr.charAt(i) == 'a'){ // and
            i += 3; // pula "and"
            i = skipSpaces(expr, i);
            if(expr.charAt(i) == '(') i++;
            boolean resultado = true;
            boolean primeiro = true;
            while(true){
                i = skipSpaces(expr, i);
                if(expr.charAt(i) == ')'){
                    i++;
                    break;
                }//Fim do if fecha parêntese
                if(!primeiro){
                    if(expr.charAt(i) == ',') i++;
                }//Fim do if pula vírgula
                primeiro = false;
                Result r = eval(expr, i, values);
                resultado = resultado && r.value;
                i = r.pos;
            }//Fim do while argumentos
            return new Result(resultado, i);
        }//Fim do if and

        // Caso 4: or(...) com múltiplos argumentos
        if(expr.charAt(i) == 'o'){ // or
            i += 2; // pula "or"
            i = skipSpaces(expr, i);
            if(expr.charAt(i) == '(') i++;
            boolean resultado = false;
            boolean primeiro = true;
            while(true){
                i = skipSpaces(expr, i);
                if(expr.charAt(i) == ')'){
                    i++;
                    break;
                }//Fim do if fecha parêntese
                if(!primeiro){
                    if(expr.charAt(i) == ',') i++;
                }//Fim do if pula vírgula
                primeiro = false;
                Result r = eval(expr, i, values);
                resultado = resultado || r.value;
                i = r.pos;
            }//Fim do while argumentos
            return new Result(resultado, i);
        }//Fim do if or

        // Se não for reconhecido, retorna falso
        return new Result(false, i + 1);
    }//Fim da função eval

    //Estrutura auxiliar para armazenar valor e posição atual
    static class Result{
        boolean value;
        int pos;
        Result(boolean v, int p){
            value = v;
            pos = p;
        }//Fim do construtor
    }//Fim da classe Result

    public static void main(String[] args){
        while(true){
            String linha = scanner.nextLine();
            if(linha.equals("0")) break; // critério de parada

            int i = 0;
            linha = linha.trim();

            // 1) Pegar número de variáveis n
            int n = 0;
            while(i < linha.length() && linha.charAt(i) != ' '){
                n = n * 10 + (linha.charAt(i) - '0');
                i++;
            }//Fim do while n

            // 2) Pegar os n valores
            int[] values = new int[26]; // suporta A..Z
            for(int j = 0; j < n; j++){
                while(i < linha.length() && linha.charAt(i) == ' ') i++;
                values[j] = linha.charAt(i) - '0';
                i++;
            }//Fim do for valores

            // 3) Restante é a expressão
            while(i < linha.length() && linha.charAt(i) == ' ') i++;
            String expr = "";
            for(; i < linha.length(); i++){
                expr += linha.charAt(i);
            }//Fim do for expressão

            // 4) Avaliar expressão
            Result r = eval(expr, 0, values);

            // 5) Imprimir resultado 1 ou 0
            System.out.println(r.value ? "1" : "0");

        }//Fim do while principal
        scanner.close();
    }//Fim da main
}//Fim da classe BooleanExpression
