package com.auspost.postcode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Contact;
import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Value("${SERVER_URL:http://localhost:8080}")
    private String serverUrl;

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Stacey Fanner");
        myContact.setEmail("staceyfanner@gmail.com");

        Info information = new Info()
                .title("PostCheck API")
                .version("1.0")
                .description("Provides postcode and suburb information via CRUD")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
