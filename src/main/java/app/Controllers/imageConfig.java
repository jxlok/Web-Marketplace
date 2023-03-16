package app.Controllers;

import java.util.HashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class imageConfig {

    @Bean
    public HashMap<Integer, Integer> imageID() {
        return new HashMap<Integer, Integer>();
    }
}