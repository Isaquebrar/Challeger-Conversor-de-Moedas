package challeger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConversaoMoeda {

    // Método para obter a resposta da API
    public static String obterRespostaDaApi(String moedaBase, String apiKey) {
        StringBuilder response = new StringBuilder();
        try {
            // Construção da URL com a chave da API e moeda base
            String urlString = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + moedaBase;
            URL url = new URL(urlString);

            // Criando a conexão HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lendo a resposta da API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao fazer a requisição: " + e.getMessage());
        }
        return response.toString();
    }

    // Método para obter a taxa de conversão de uma moeda para outra
    public static double obterTaxaDeConversao(String jsonResponse, String moedaDestino) {
        try {
            // Verificando se a resposta não está vazia
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                System.out.println("Resposta da API está vazia ou nula.");
                return -1;
            }

            // Desserializar o JSON com a resposta da API
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

            // Verificando se a chave "conversion_rates" existe na resposta
            if (jsonObject.has("conversion_rates")) {
                JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

                // Verifica se a moeda destino existe nas taxas de conversão
                if (conversionRates.has(moedaDestino)) {
                    return conversionRates.get(moedaDestino).getAsDouble();
                } else {
                    System.out.println("Moeda destino não encontrada.");
                    return -1;
                }
            } else {
                System.out.println("Erro ao processar a resposta da API: chave 'conversion_rates' não encontrada.");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar o JSON: " + e.getMessage());
            return -1;
        }
    }

    public static void main(String[] args) {
        try {
            // Exemplo de uso
            String moedaBase = "USD";  // Moeda base para conversão
            String moedaDestino = "BRL";  // Moeda destino (ex: Real brasileiro)
            String apiKey = "663d5b64b2be8472047a88b9";  // Sua chave de API

            // Obtendo a resposta da API corretamente
            String jsonResponse = obterRespostaDaApi(moedaBase, apiKey);

            // Verificando se a resposta foi obtida corretamente
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                // Agora chamando o método correto com os dois parâmetros
                double taxaDeConversao = obterTaxaDeConversao(jsonResponse, moedaDestino);

                // Exibindo o valor da conversão
                if (taxaDeConversao != -1) {
                    System.out.printf("Taxa de conversão de %s para %s: %.2f%n", moedaBase, moedaDestino, taxaDeConversao);
                } else {
                    System.out.println("Erro ao obter a taxa de conversão.");
                }
            } else {
                System.out.println("Erro ao obter a resposta da API.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
