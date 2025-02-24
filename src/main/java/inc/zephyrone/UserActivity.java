package inc.zephyrone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserActivity(
        @JsonProperty("id") String id, //Não estara na saida
        // o tipo da ação
        @JsonProperty("type") typeENUM type,
        // o repo que esta sofrendo a ação
        @JsonProperty("repo") repoRecord repo,
        // A ação do usuario de fato
        @JsonProperty("payload") payloadRecord payload
){}

