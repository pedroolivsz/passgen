package com.io.github.pedroolivsz.passgen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${swagger.server.url.local}")
    private String localUrl;

    @Value("${swagger.server.url.producao:http://localhost:8080}")
    private String prodUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        server(localUrl, "Desenvolvimento Local"),
                        server(prodUrl, "Produção (Render)")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title("PassGen API")
                .description("API para geração de senhas aleatórias e criptograficamente seguras.")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("João Pedro")
                        .url("https://github.com/pedroolivsz"))
                .license(new License()
                        .name("MIT")
                        .url("https://opensource.org/licenses/MIT"));
    }

    private Server server(String url, String description) {
        return new Server().url(url).description(description);
    }
}
