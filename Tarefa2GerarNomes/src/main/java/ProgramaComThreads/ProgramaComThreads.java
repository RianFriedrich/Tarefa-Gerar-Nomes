package ProgramaComThreads;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ProgramaComThreads {

    private static final int NUM_NOMES = 1000000;
    private static final String ARQUIVO_NOMES = "ProgramaComThreads.txt";
    private static List<String> nomes = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // Thread para gerar e armazenar nomes
        Thread gerarENomearNomesAleatorios = new Thread() {
            @Override
            public void run() {
                Random random = new Random();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_NOMES))) {
                    for (int i = 0; i < NUM_NOMES; i++) {
                        String nome = gerarNomeAleatorio(random);
                        writer.write(nome);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        gerarENomearNomesAleatorios.start();
        gerarENomearNomesAleatorios.join(); // Aguarda a conclusão da geração e armazenamento dos nomes

        // Thread para carregar nomes do arquivo
        Thread carregarNomesDoArquivo = new Thread() {
            @Override
            public void run() {
                try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_NOMES))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        nomes.add(linha);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        carregarNomesDoArquivo.start();
        carregarNomesDoArquivo.join(); // Aguarda a conclusão do carregamento

        // Thread para ordenar com Timsort
        long startTime = System.currentTimeMillis();
        Thread ordenarNomes = new Thread() {
            @Override
            public void run() {
                Collections.sort(nomes); // Timsort
            }
        };

        ordenarNomes.start();
        ordenarNomes.join(); // Aguarda a conclusão da ordenação
        long tempoOrdenacao = System.currentTimeMillis() - startTime;
        System.out.println("Tempo para ordenar com Collections.sort (Timsort): " + tempoOrdenacao + " ms");
    }
    
    private static String gerarNomeAleatorio(Random random) {
        StringBuilder nome = new StringBuilder();
        for (int i = 0; i < 8; i++) { // Nomes com exatamente 8 caracteres
            char letra = (char) ('a' + random.nextInt(26));
            nome.append(letra);
        }
        return nome.toString();
    }
}
