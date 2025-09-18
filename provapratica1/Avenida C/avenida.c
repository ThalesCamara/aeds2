#include <stdio.h>

int main(){
    int escola; 
    scanf("%d", &escola); // declaracao e leitura da posicao da escola
    int pontos[6]= {0, 400, 800, 1200, 1600, 2000};
    int distancia[6];
    for (int i=0; i<6; i++){ // preenche array com a distancia de cada ponto até a escola
        if(escola>pontos[i]) distancia[i] = escola - pontos[i];
        else distancia[i] = pontos[i] - escola;
    }
    for(int i = 0; i< 5; i++){ //quicksort para ordenar da menor para a maior distancia
        for(int j=0; j<5-i; j++){
            if(distancia[j]> distancia[j+1]){
                int tmp = distancia[j];
                distancia[j] = distancia[j+1];
                distancia[j+1] = tmp;
            }
        }
    }
    printf("%d", distancia[0]); // imprime a maior distancia
    return 0;
}