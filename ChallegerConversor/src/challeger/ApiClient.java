package challeger;                                                                                                
                                                                                                                                                         
import java.io.BufferedReader;                                                                                    
import java.io.InputStreamReader;                                                                                 
import java.io.IOException;                                                                                       
import java.net.HttpURLConnection;                                                                                
import java.net.URL;                                                                                              
                                                                                                                  
public class ApiClient {                                                                                          
                                                                                                                  
    // Método para obter a cotação da moeda da API                                                                
    public static String getCotacaoMoeda(String moedaBase, String moedaDestino, String apiKey) {                  
        StringBuilder response = new StringBuilder();                                                      
        try {                                                                                              
            // Construção da URL com a chave de API e moedas base e destino                                
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
}                                                                                                                                                                                                                  
                                                                                                                                                                                                                                                             



