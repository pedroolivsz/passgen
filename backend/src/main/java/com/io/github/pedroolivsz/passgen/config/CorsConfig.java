package com.io.github.pedroolivsz.passgen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Classe de configuração global do mecanismo de CORS (Cross-Origin Resource Sharing).
 * <p>
 * Define as políticas de segurança para requisições de origens cruzadas, permitindo
 * que aplicações front-end hospedadas em domínios diferentes consumam com segurança
 * os recursos expostos por esta API.
 * </p>
 */
@Configuration
public class CorsConfig {
    /**
     * Lista de origens permitidas injetada a partir das propriedades da aplicação
     * (ex: {@code application.properties} ou variáveis de ambiente).
     */
    @Value("${cors.allowed-origins}")
    private List<String> allowedOrigins;

    /**
     * Cria e configura o Bean do {@link CorsFilter} para interceptar as requisições HTTP
     * e aplicar as regras de CORS baseadas nos endpoints.
     *
     * @return Um {@link CorsFilter} configurado com as diretivas de origens, métodos e cabeçalhos permitidos
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Define quais domínios externos têm permissão para acessar a API
        config.setAllowedOrigins(allowedOrigins);

        // Define os métodos HTTP permitidos durante as chamadas cross-origin
        config.setAllowedMethods(List.of(
                "GET", "POST", "DELETE", "PUT", "OPTIONS"
        ));

        // Define quais cabeçalhos HTTP podem ser enviados na requisição real
        config.setAllowedHeaders(List.of(
                "Content-Type", "Authorization"
        ));

        // Permite o envio de credenciais (cookies, tokens de autenticação HTTP ou certificados TLS cliente)
        config.setAllowCredentials(true);

        // Configura o tempo máximo (em segundos) que a resposta de uma requisição prévia (Preflight) pode ser mantida em cache
        config.setMaxAge(3600L);

        // Mapeia as configurações de CORS criadas acima para caminhos específicos de URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
