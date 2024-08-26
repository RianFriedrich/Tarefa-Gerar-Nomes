package ProgramaSemThreads;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProgramaSemThreads {
    private static final int NUM_NOMES = 1000000;
    private static final String ARQUIVO_NOMES = "NomesSemThreads.txt"; // Novo nome do arquivo
    private static final int TAMANHO_NOME = 8;
    private static final String CARACTERES = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        List<String> nomes = new ArrayList<>();
        Random random = new Random();

        long inicioGeracao = System.currentTimeMillis();

        // Gerar e salvar nomes
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_NOMES))) {
            for (int i = 0; i < NUM_NOMES; i++) {
                String nome = gerarNomeAleatorio(random);
                nomes.add(nome);
                writer.write(nome);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long fimGeracao = System.currentTimeMillis();
        System.out.println("Tempo de geração e salvamento: " + (fimGeracao - inicioGeracao) + " ms");

        // Ordenar usando Arrays.sort
        long inicioOrdenacaoSort = System.currentTimeMillis();
        nomes.sort(String::compareTo);
        long fimOrdenacaoSort = System.currentTimeMillis();
        System.out.println("Tempo de ordenação com Arrays.sort: " + (fimOrdenacaoSort - inicioOrdenacaoSort) + " ms");

        // Ordenar usando Merge Sort
        List<String> nomesParaMergeSort = new ArrayList<>(nomes);
        long inicioOrdenacaoMergeSort = System.currentTimeMillis();
        mergeSort(nomesParaMergeSort);
        long fimOrdenacaoMergeSort = System.currentTimeMillis();
        System.out.println("Tempo de ordenação com Merge Sort: " + (fimOrdenacaoMergeSort - inicioOrdenacaoMergeSort) + " ms");
    }

    private static String gerarNomeAleatorio(Random random) {
        StringBuilder nome = new StringBuilder(TAMANHO_NOME);
        for (int i = 0; i < TAMANHO_NOME; i++) {
            nome.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
        }
        return nome.toString();
    }

    // Implementação do Merge Sort
    public static void mergeSort(List<String> lista) {
        if (lista.size() <= 1) {
            return;
        }

        int meio = lista.size() / 2;
        List<String> esquerda = new ArrayList<>(lista.subList(0, meio));
        List<String> direita = new ArrayList<>(lista.subList(meio, lista.size()));

        mergeSort(esquerda);
        mergeSort(direita);

        merge(lista, esquerda, direita);
    }

    public static void merge(List<String> lista, List<String> esquerda, List<String> direita) {
        int i = 0, j = 0, k = 0;

        while (i < esquerda.size() && j < direita.size()) {
            if (esquerda.get(i).compareTo(direita.get(j)) <= 0) {
                lista.set(k++, esquerda.get(i++));
            } else {
                lista.set(k++, direita.get(j++));
            }
        }

        while (i < esquerda.size()) {
            lista.set(k++, esquerda.get(i++));
        }

        while (j < direita.size()) {
            lista.set(k++, direita.get(j++));
        }
    }
}
