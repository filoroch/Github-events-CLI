package inc.zephyrone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        final var json = "{\"id\":\"46735508605\"}";
        final var objectMapper = new ObjectMapper();
        final var filoroch = objectMapper.readValue(json, EventUser.class);
        System.out.println(filoroch);
    }

}