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

/**
 * Classe de configuração do SpringDoc OpenAPI (Swagger).
 * <p>
 * Define as informações globais da documentação da API, como título, descrição,
 * versão, licença, dados de contato e os servidores disponíveis para teste (ambientes).
 * </p>
 */
@Configuration
public class SwaggerConfig {
    /**
     * URL do ambiente de desenvolvimento local (injeta o valor mapeado no application.properties/yml).
     */
    @Value("${swagger.server.url.local}")
    private String localUrl;

    /**
     * URL do ambiente de produção (Render). Caso não esteja definida no ambiente,
     * utiliza {@code http://localhost:8080} como fallback.
     */
    @Value("${swagger.server.url.producao:http://localhost:8080}")
    private String prodUrl;

    /**
     * Cria e configura o Bean global do {@link OpenAPI}.
     * Este método centraliza as informações do ecossistema OpenAPI que o SpringDoc
     * utiliza para renderizar a interface web.
     *
     * @return Objeto OpenAPI configurado com metadados e servidores
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        server(localUrl, "Desenvolvimento Local"),
                        server(prodUrl, "Produção (Render)")
                ));
    }

    /**
     * Define os metadados de exibição da API (Informações de cabeçalho).
     *
     * @return Objeto {@link Info} contendo título, descrição, versão, contato e licença
     */
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

    /**
     * Método auxiliar para instanciar e configurar um objeto {@link Server}.
     * Facilitadores para a listagem de múltiplos ambientes no topo do Swagger UI.
     *
     * @param url Endereço base do servidor (ex: <a href="http://localhost:8080">...</a>)
     * @param description Descrição amigável do ambiente (ex: Produção)
     * @return Uma instância configurada de {@link Server}
     */
    private Server server(String url, String description) {
        return new Server().url(url).description(description);
    }
}
