package challeger;

import java.util.Scanner;

public class ConversorDeMoedas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        System.out.println("Informe sua chave de API:");
        String apiKey = scanner.nextLine();

        do {
            System.out.println("Selecione a opção de conversão de moedas:");
            System.out.println("1. Dólar para Real");
            System.out.println("2. Real para Dólar");
            System.out.println("3. Euro para Real");
            System.out.println("4. Real para Euro");
            System.out.println("5. Libra para Real");
            System.out.println("6. Real para Libra");
            System.out.println("0. Sair");

            opcao = scanner.nextInt();

            if (opcao != 0) {
                System.out.println("Informe o valor a ser convertido:");
                double valor = scanner.nextDouble();

                try {
                    double taxaConversao = 0;
                    String moedaBase = "";
                    String moedaDestino = "";

                    switch (opcao) {
                        case 1: // Dólar para Real
                            moedaBase = "USD";
                            moedaDestino = "BRL";
                            break;
                        case 2: // Real para Dólar
                            moedaBase = "BRL";
                            moedaDestino = "USD";
                            break;
                        case 3: // Euro para Real
                            moedaBase = "EUR";
                            moedaDestino = "BRL";
                            break;
                        case 4: // Real para Euro
                            moedaBase = "BRL";
                            moedaDestino = "EUR";
                            break;
                        case 5: // Libra Esterlina para Real
                            moedaBase = "GBP";
                            moedaDestino = "BRL";
                            break;
                        case 6: // Real para Libra Esterlina
                            moedaBase = "BRL";
                            moedaDestino = "GBP";
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            continue;
                    }

                    // Chama o método que acessa a API e retorna a taxa de conversão
                    String jsonResponse = ApiClient.getCotacaoMoeda(moedaBase, moedaDestino, apiKey);
                    
                    // Agora passa o parâmetro correto para obter a taxa de conversão
                    taxaConversao = ConversaoMoeda.obterTaxaDeConversao(jsonResponse, moedaDestino);

                    // Realiza a conversão
                    double valorConvertido = valor * taxaConversao;
                    System.out.printf("Valor convertido: %.2f\n", valorConvertido);

                } catch (Exception e) {
                    System.out.println("Erro ao obter as cotações. Tente novamente.");
                }
            }

        } while (opcao != 0);

        scanner.close();
    }
}


