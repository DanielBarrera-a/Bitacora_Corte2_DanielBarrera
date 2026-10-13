package edu.dosw.wok.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/*
 * =====================================================================
 *  SWAGGER / OPENAPI  (config)
 * =====================================================================
 *  QUÉ ES
 *    OpenAPI = el estándar (contrato JSON/YAML que describe la API).
 *    Swagger UI = la página web que lee ese contrato y deja probar los endpoints.
 *    springdoc-openapi genera el contrato desde los @RestController.
 *    (S08 diapos 35-42 · guía controller, Paso 08)
 *
 *  CÓMO SE USA
 *    1. Dependencia springdoc-openapi-starter-webmvc-ui en el pom (ya está).
 *       Con solo eso, la UI funciona: http://localhost:8080/swagger-ui/index.html
 *       y el contrato en:              http://localhost:8080/v3/api-docs
 *    2. (Opcional) Esta clase con @OpenAPIDefinition para título, versión y descripción.
 *    3. En cada controller: @Tag, @Operation, @ApiResponses (ver PlatoController).
 *    4. Con Spring Security hay que dejar públicas /swagger-ui/** y /v3/api-docs/**
 *       (ver SecurityConfig), si no responde 401/403.
 *
 *  PROBAR UN ENDPOINT EN LA UI
 *    Abrir el endpoint -> "Try it out" -> llenar parámetros/body -> "Execute"
 *    -> abajo se ve el código HTTP y el JSON de respuesta.
 * =====================================================================
 */
@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Wok System API",
        version = "v1",
        description = "API del restaurante Wok System: carta, pedidos, cocina y cuentas"))
public class SwaggerConfig {
}
