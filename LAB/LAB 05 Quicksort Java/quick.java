import java.util.*;

public class quick {
    private int[] array;
    private Random rand;
    
    // Construtor
    public quick(int n) {
        rand = new Random();
        array = new int[n];
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(100);
        }
    }

    // Método para imprimir array
    public void mostrar() {
        for(int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
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
    // Funções para preencher arrays
    public void fillOrdered() {
        for (int i = 0; i < array.length; i++) array[i] = i;
    }
    public void fillAlmostOrdered() {
        fillOrdered();
        int swaps = Math.max(1, array.length / 100);
        for (int i = 0; i < swaps; i++) {
            int a = rand.nextInt(array.length);
            int b = rand.nextInt(array.length);
            swap(a, b);
        }
    }
    public void fillRandom() {
        for (int i = 0; i < array.length; i++) array[i] = rand.nextInt(array.length);
    }

    // Mede tempo de execução de cada estratégia
    public static double sortAndTime(quick q, String tipo, String metodo) {
        if (tipo.equals("ordenado")) q.fillOrdered();
        else if (tipo.equals("quase ordenado")) q.fillAlmostOrdered();
        else q.fillRandom();

        long start = System.nanoTime();
        if (metodo.equals("primeiro")) q.quicksortprimeiropivo(0, q.array.length-1);
        else if (metodo.equals("ultimo")) q.quicksortultimopivo(0, q.array.length-1);
        else if (metodo.equals("random")) q.quicksortpivorandom(0, q.array.length-1);
        else q.quicksortmediana(0, q.array.length-1);
        long end = System.nanoTime();
        return (end - start) / 1_000_000.0; // milissegundos com casas decimais
    }

    public static void main(String[] args) {
        int[] tamanhos = {100, 1000, 10000};
        String[] tipos = {"ordenado", "quase ordenado", "aleatório"};
        String[] nomes = {"primeiro", "ultimo", "random", "mediana"};
        String[] metodos = {
            "Primeiro elemento (pivô)",
            "Último elemento (pivô)",
            "Pivô aleatório",
            "Mediana"
        };

        System.out.println("| Estratégia                | Tipo de Array      | 100 elementos | 1000 elementos | 10000 elementos |");
        System.out.println("|---------------------------|--------------------|---------------|----------------|-----------------|");

        for (int m = 0; m < nomes.length; m++) {
            for (int t = 0; t < tipos.length; t++) {
                System.out.print("| " + String.format("%-26s", metodos[m]));
                System.out.print("| " + String.format("%-18s", tipos[t]));
                for (int s = 0; s < tamanhos.length; s++) {
                    quick q = new quick(tamanhos[s]);
                    double tempo = sortAndTime(q, tipos[t], nomes[m]);
                    System.out.print("| " + String.format("%13.3f", tempo));
                }
                System.out.println("|");
            }
        }
    }
}
