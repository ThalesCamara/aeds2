import java.util.*;

public class quick {
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();
    int n = 100;
    int[] array = new int[n];
    for (int i = 0; i < array.length; i++) {
        array[i] = rand.nextInt(100);
    }

    void swap (int i, int j){
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
    

    void quicksortprimeiropivo(int esq, int dir){
        int i = esq, j = dir;
        int pivo = array[esq];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksortprimeiropivo(esq, j);
        if (i < dir)  quicksortprimeiropivo(i, dir);
    }

    void quicksortultimopivo(int esq, int dir){
        int i = esq, j = dir;
        int pivo = array[dir];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksortultimopivo(esq, j);
        if (i < dir)  quicksortultimopivo(i, dir);
    }

    void quicksortpivorandom(int esq, int dir){
        int i = esq, j = dir;
        int pivo = array[esq + rand.nextInt(dir-esq+1)];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksortpivorandom(esq, j);
        if (i < dir)  quicksortpivorandom(i, dir);
    }

    void quicksortmediana(int esq, int dir){
        int i = esq, j = dir;
        int val1 = array[(dir+esq)/2];
        int val2 = array[esq];
        int val3 = array[dir];
        int pivo;
        if(val1 >= val2 && val1<= val3) pivo = val1;
        else if(val2>=val1 && val2 <= val3) pivo = val2;
        else if(val3>=val1 && val3 <= val2) pivo = val3;
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksortmediana(esq, j);
        if (i < dir)  quicksortmediana(i, dir);
    }
    public static void main(String[] args) {
        
    }
}
