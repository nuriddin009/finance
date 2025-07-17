package uz.banking.finance.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import uz.banking.finance.constants.Constants;

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = Constants.AUTHORIZATION_HEADER,
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        scheme = "Authorization"
)
@OpenAPIDefinition(
        info = @Info(title = "Template API"),
        security = @SecurityRequirement(name = Constants.AUTHORIZATION_HEADER),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Server")
        }
)
public class OpenApiConfiguration {
}


