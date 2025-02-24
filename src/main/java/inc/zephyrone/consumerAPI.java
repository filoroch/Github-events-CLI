package inc.zephyrone;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class consumerAPI {

    public static void main(String[] args) throws IOException, MalformedURLException {
        Scanner inputSc = new Scanner(System.in);
        String input = "";

        while (!input.equalsIgnoreCase("SAIR")){
            var response = consumer(new Scanner(System.in));

            // Salva a resposta da api consultada em um JSON
            try{
                responseJSON(response, "GithubUserActivity.json");
            } catch(IOException e) {
                System.err.println("Erro ao salvar o JSON: " + e.getMessage());
            }
            // Mostra no CLI
            Parser();

            System.out.println("\nDigite SAIR, para SAIR");
            input = inputSc.nextLine();
        }

        inputSc.close();
    // Retornar o json no console
    // Converter para a saida esperada
    }
    public static String consumer(@NotNull Scanner input) throws IOException {
        // Recebe o valor do CLI
        System.out.println("\nDigite o nome do usuario");
        String user = input.nextLine();
        final String endpoint = "/events";


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
    public static void Parser(){
        try {
            // Ler o JSON do arquivo
            ObjectMapper mapper = new ObjectMapper();
            List<JsonNode> events = mapper.readValue(new File("GithubUserActivity.json"), new TypeReference<>() {});

            for (int i=0; i < 3; i++){
                JsonNode event = events.get(i);
                String type = event.get("type").asText();
                String repoName = event.get("repo").get("name").asText();

                switch (type) {
                    case "PushEvent" -> {
                        int commitCount = event.get("payload").get("size").asInt();
                        System.out.println("- Pushed " + commitCount + " commits to " + repoName);
                    }
                    case "IssuesEvent" -> System.out.println("- Opened a new issue in " + repoName);
                    case "WatchEvent" -> System.out.println("- Starred " + repoName);
                    default -> System.out.println("- " + type + " event in " + repoName);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o JSON: " + e.getMessage());
        }
    }
}
