package br.com.raac_system.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        final String tenantHeaderName = "X-Tenant-ID";

        return new OpenAPI()
                .info(new Info()
                        .title("RaacSystem API")
                        .version("1.0")
                        .description("Medical & Authentication Software Integration APIs"))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName)
                        .addList(tenantHeaderName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                        .addSecuritySchemes(tenantHeaderName, new SecurityScheme()
                                .name(tenantHeaderName)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("Requires X-Tenant-ID header mapping for resolution to access paths")));
    }
}
