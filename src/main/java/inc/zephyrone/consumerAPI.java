package inc.zephyrone;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class consumerAPI {

    public static void main(String[] args) throws IOException, MalformedURLException {
        consumer(new Scanner(System.in));

    // Retornar o json no console
    // Converter para a saida esperada
    }
    public static void consumer(@NotNull Scanner input) throws IOException {
        // Recebe o valor do CLI
        System.out.println("Digite o nome do usuario");
        String user = input.nextLine();
        String endpoint = "/events";

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
            BufferedReader response = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            // Imprime a saida no console
            System.out.println(response.readLine());

        } finally {
            connection.disconnect();
        }
        Gson gson = new Gson();
    }
}
