package inc.zephyrone;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class consumerAPI {

    public static void main(String[] args) throws IOException, MalformedURLException {
        var response = consumer(new Scanner(System.in));

        // Salva a resposta da api consultada em um JSON
        try{
            responseJSON(response, "GithubUser.json");
        } catch(IOException e) {
            System.err.println("Erro ao salvar o JSON: " + e.getMessage());
        }

    // Retornar o json no console
    // Converter para a saida esperada
    }
    public static String consumer(@NotNull Scanner input) throws IOException {
        // Recebe o valor do CLI
        System.out.println("\nDigite o nome do usuario");
        String user = input.nextLine();
        final String endpoint = "/events";

        input.close();

        // Conectar com a api
        String urlParams = "https://api.github.com/users/" + user + endpoint;
        URL url = new URL(urlParams);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        try {
            // Pegar o codigo da resposta
            int responseCode = connection.getResponseCode();
            if (responseCode != 200){
                throw new RuntimeException("HTTP ERROR CODE : " + responseCode);
            }
            // Para ler a saida em JSON/XML/STRING
            BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) !=null){
                response.append(line);
            }
            reader.close();

            System.out.println("[LOG] Resposta da API: " + response.toString());

            return response.toString();

        } finally {
            connection.disconnect();
        }
    }

    public static void responseJSON (String response, String nomeArquivo) throws StreamWriteException, DatabindException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(nomeArquivo), mapper.readTree(response));
        System.out.println("[LOG] JSON salvo em: " + nomeArquivo);
    }
}
