# Projeto NestedBoxes

Trabalho desenvolvido para a disciplina de Algoritmos e Estrutura de Dados II.

Desenvolvido por Caroline da Rocha de Lima e Eduardo Carlesso.

## Descrição do Problema

Quero dar um presente criativo para minha sogra. O plano é colocar uma caixa dentro de outra, e outra, e mais outra, até que ela finalmente encontre o presente na última delas. Para isso, baixei da internet o catálogo completo de uma fábrica de caixas de papelão e, antes de fazer minha encomenda, decidi achar a maior sequência de caixas que podem ser colocadas uma dentro da outra.

## Descrição do Catálogo
O catálogo é bem simples e lista todos os tamanhos de caixas que são fabricadas, fornecendo suas três dimensões. Infelizmente, largura, altura e comprimento não estão identificados e se misturam dentro do arquivo. Como o catálogo é um pouco longo, resolvi automatizar a tarefa de encontrar a maior sequência de caixas que podem ser colocadas uma dentro da outra, fornecendo como informação final o comprimento da sequência mais longa de caixas que cabem uma dentro da outra. Agora, você deve processar catálogos de várias empresas e fornecer a resposta para cada uma delas.

## Exemplo de Arquivo de Entrada
O arquivo de entrada (input.txt) deve seguir o formato abaixo, onde cada linha representa uma caixa com três dimensões separadas por espaços:

```
74 16 53
79 20 99
42 75 5
31 38 68
50 80 36
24 66 40
64 69 11
94 90 90
71 25 28
1 38 42
14 53 2
7 5 14
41 73 79
66 19 74
82 27 77
87 2 42
50 1 91
49 52 27
98 11 85
6 70 57
```


## Estrutura de Arquivos
**NestedBoxes.java**: Código fonte do projeto.

**input.txt**: Arquivo de entrada com as dimensões das caixas.