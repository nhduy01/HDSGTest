package com.example.HDSGTest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "Bearer Authentication";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(info())
                .servers(servers());
    }
    private Info info() {
        Info info = new Info();
        info.setTitle("API Quản lý người dùng HDSGTest");
        info.setVersion("1.0.0");
        info.setDescription("Tài liệu API cho hệ thống quản lý người dùng, xác thực, đổi mật khẩu, so khớp ảnh bằng OpenCV.");
        License license = new License();
        license.setName("Apache 2.0");
        license.setUrl("https://www.apache.org/licenses/LICENSE-2.0.html");
        info.setLicense(license);
        return info;
    }

    private List<Server> servers() {
        return Arrays.asList(
                new Server().url("http://localhost:8080").description("Máy chủ cục bộ")
        );
    }
}