package be.jimmyd.cm;

import io.github.jimmydbe.imdb.IMDB;
import io.github.jimmydbe.imdb.IMDBFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class CollectionManagerApplication {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public IMDB getImdbApi() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/io/github/jimmydbe/imdb/parsing.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        IMDBFactory factory = new IMDBFactory();
        return factory.createInstance(properties);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(CollectionManagerApplication.class, args);
    }

}
