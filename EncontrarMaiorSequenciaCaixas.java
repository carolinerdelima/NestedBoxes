import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncontrarMaiorSequenciaCaixas {

    // Função para ler o arquivo de catálogo de caixas
    private static List<Caixa> lerArquivo(String nomeArquivo) throws IOException {
        List<Caixa> caixas = new ArrayList<>();
        try (BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dimensoes = linha.split(" ");
                int largura = Integer.parseInt(dimensoes[0]);
                int altura = Integer.parseInt(dimensoes[1]);
                int profundidade = Integer.parseInt(dimensoes[2]);
                caixas.add(new Caixa(largura, altura, profundidade));
            }
        }
        return caixas;
    }

    // Função para verificar se uma caixa pode ser encaixada em outra
    private static boolean podeEncaixar(Caixa caixa1, Caixa caixa2) {
        return caixa1.largura < caixa2.largura && caixa1.altura < caixa2.altura && caixa1.profundidade < caixa2.profundidade;
    }

    // Função recursiva com poda para encontrar a maior sequência de caixas aninhadas
    private static int encontrarMaiorSequenciaComPoda(Map<Caixa, List<Caixa>> adjacencias, Caixa caixaAtual, int sequenciaAtual, int maiorSequencia) {
        if (sequenciaAtual > maiorSequencia) {
            maiorSequencia = sequenciaAtual;
        }


        // Verifica se a caixa atual pode ser encaixada em outras
        for (Caixa caixaAdjacente : adjacencias.get(caixaAtual)) {
            if (Caixa.podeEncaixar(caixaAtual, caixaAdjacente)) { // Corrigido aqui
                // Poda: descarta caixas que não podem ser encaixadas em nenhuma outra
                if (Caixa.podeEncaixar(caixaAdjacente, caixaAtual)) { // Corrigido aqui
                    maiorSequencia = encontrarMaiorSequenciaComPoda(adjacencias, caixaAdjacente, sequenciaAtual + 1, maiorSequencia);
                }
            }
        }

        return maiorSequencia;
    }

    public static void main(String[] args) throws IOException {
        String nomeArquivo = "teste.txt"; // Substitua pelo nome do seu arquivo

        List<Caixa> caixas = lerArquivo(nomeArquivo);

        // Cria um mapa de adjacências para armazenar as relações entre as caixas
        Map<Caixa, List<Caixa>> adjacencias = new HashMap<>();
        for (Caixa caixa1 : caixas) {
            adjacencias.putIfAbsent(caixa1, new ArrayList<>());
        }
        for (Caixa caixa1 : caixas) {
            for (Caixa caixa2 : caixas) {
                if (podeEncaixar(caixa1, caixa2)) {
                    adjacencias.get(caixa1).add(caixa2);
                }
            }
        }

        // Encontra a maior sequência de caixas aninhadas
        int maiorSequencia = encontrarMaiorSequenciaComPoda(adjacencias, caixas.get(0), 1, 0);

        // Imprime o resultado
        System.out.println("Maior sequência de caixas aninhadas: " + maiorSequencia);
    }
}

// Classe Caixa para representar as caixas do catálogo
class Caixa {
    int largura;
    int altura;
    int profundidade;

    public Caixa(int largura, int altura, int profundidade) {
        this.largura = largura;
        this.altura = altura;
        this.profundidade = profundidade;
    }

    // Getter para largura
    public int getLargura() {
        return largura;
    }

    // Setter para largura
    public void setLargura(int largura) {
        this.largura = largura;
    }

    // Getter para altura
    public int getAltura() {
        return altura;
    }

    // Setter para altura
    public void setAltura(int altura) {
        this.altura = altura;
    }

    // Getter para profundidade
    public int getProfundidade() {
        return profundidade;
    }

    // Setter para profundidade
    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    // Método estático para verificar se uma caixa pode ser encaixada em outra
    public static boolean podeEncaixar(Caixa caixa1, Caixa caixa2) {
        return caixa1.largura < caixa2.largura && caixa1.altura < caixa2.altura && caixa1.profundidade < caixa2.profundidade;
    }
}

