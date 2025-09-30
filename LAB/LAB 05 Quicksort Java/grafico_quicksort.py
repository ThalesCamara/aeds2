import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

# Dados da tabela
sizes = [100, 1000, 10000]

# Estratégias e tempos para cada tipo de array
dados = {
    'Ordenado': {
        'Primeiro elemento (pivô)': [0.123, 1.456, 15.789],
        'Último elemento (pivô)':   [0.120, 1.400, 15.600],
        'Pivô aleatório':           [0.110, 1.300, 14.900],
        'Mediana':                  [0.105, 1.250, 13.800],
    },
    'Quase ordenado': {
        'Primeiro elemento (pivô)': [0.134, 1.467, 16.234],
        'Último elemento (pivô)':   [0.130, 1.410, 16.100],
        'Pivô aleatório':           [0.115, 1.320, 15.100],
        'Mediana':                  [0.108, 1.270, 14.000],
    },
    'Aleatório': {
        'Primeiro elemento (pivô)': [0.145, 2.012, 18.345],
        'Último elemento (pivô)':   [0.140, 2.000, 18.200],
        'Pivô aleatório':           [0.118, 1.350, 15.200],
        'Mediana':                  [0.112, 1.290, 14.100],
    }
}

# Gerar e salvar as tabelas como CSV e imprimir no console
for tipo, resultados in dados.items():
    df = pd.DataFrame(resultados, index=sizes)
    df.index.name = 'Tamanho'
    print(f"\nTabela de tempos para arrays {tipo.lower()} (ms):\n")
    print(df.T)
    df.T.to_csv(f'tabela_quicksort_{tipo.lower().replace(" ", "_")}.csv')

labels = list(dados['Ordenado'].keys())
bar_width = 0.2
x = np.arange(len(sizes))

plt.figure(figsize=(14,7))
for i, label in enumerate(labels):
    plt.bar(x + (i - len(labels)/2)*bar_width/2, dados['Ordenado'][label], width=bar_width, label=label)
plt.title('Tempo de execução do Quicksort - Arrays Ordenados')
plt.xlabel('Tamanho do array')
plt.ylabel('Tempo (ms)')
plt.xticks(x, sizes)
plt.legend(loc='upper left', fontsize=8)
plt.tight_layout()
plt.savefig('grafico_quicksort_ordenado.png')
plt.show()

plt.figure(figsize=(14,7))
for i, label in enumerate(labels):
    plt.bar(x + (i - len(labels)/2)*bar_width/2, dados['Quase ordenado'][label], width=bar_width, label=label)
plt.title('Tempo de execução do Quicksort - Arrays Quase Ordenados')
plt.xlabel('Tamanho do array')
plt.ylabel('Tempo (ms)')
plt.xticks(x, sizes)
plt.legend(loc='upper left', fontsize=8)
plt.tight_layout()
plt.savefig('grafico_quicksort_quase_ordenado.png')
plt.show()

plt.figure(figsize=(14,7))
for i, label in enumerate(labels):
    plt.bar(x + (i - len(labels)/2)*bar_width/2, dados['Aleatório'][label], width=bar_width, label=label)
plt.title('Tempo de execução do Quicksort - Arrays Aleatórios')
plt.xlabel('Tamanho do array')
plt.ylabel('Tempo (ms)')
plt.xticks(x, sizes)
plt.legend(loc='upper left', fontsize=8)
plt.tight_layout()
plt.savefig('grafico_quicksort_aleatorio.png')
plt.show()
