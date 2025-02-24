package inc.zephyrone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record Events (
        String id,
        typeENUM type,
        String repo,
        String payload) {
}
