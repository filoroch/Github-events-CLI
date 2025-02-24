# CLI: Github Events
Essa CLI recebe um `user` do github e, caso exista, retorna um JSON com suas atividades `èventos`

## Aprendizados
#### 1. Conectar com uma api usando `HttpsURLConnection``
```java
// Conectar com a api
        String urlParams = "https://api.github.com/users/" + user + endpoint;
        URL url = new URL(urlParams);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
```

Agora entendo como funciona o processo de uma linguagem conectar a uma URL para obter ou enviar alguma informação

## BufferedReader para armazenar dados em buffer
```java
        BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
```
Tenho uma noção de como funciona a leitura de dados provenientes da web

### StringBuilder
- Aprendi que dentro do Objeto StringBuilder, as strings são mutaveis e podem ser adicionados caracteres e linhas da mesma forma que um array
```java
    StringBuilder response = new StringBuilder();
    String line;
    while ((line = reader.readLine()) !=null){
    response.append(line);
    }
reader.close();
```
### Jackson para salvar JSON
```java
public static void responseJSON (String response, String nomeArquivo) throws StreamWriteException, DatabindException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(nomeArquivo), mapper.readTree(response));
        System.out.println("[LOG] JSON salvo em: " + nomeArquivo);
    }
```

## Ações
- [ ] Construir um objeto java que seja capaz de retornar no terminal:
  ```
  - Pushed 3 commits to kamranahmedse/developer-roadmap
  - Opened a new issue in kamranahmedse/developer-roadmap
  - Starred kamranahmedse/developer-roadmap
  ```
- [ ] Aprender a desserializar 