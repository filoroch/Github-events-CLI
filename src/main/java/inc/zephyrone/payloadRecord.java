package inc.zephyrone;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record payloadRecord(
        @JsonProperty("commits") List commits
        ) {
}
