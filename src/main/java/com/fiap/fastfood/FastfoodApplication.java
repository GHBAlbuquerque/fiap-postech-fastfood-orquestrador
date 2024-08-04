package com.fiap.fastfood;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableDynamoDBRepositories(basePackages = "com.fiap.fastfood.common.interfaces.datasources")
@OpenAPIDefinition(info = @Info(title = "Fast Food FIAP", description = "Microsserviço de Orquestração SAGA realizado para a Pós-Graduação de Arquitetura de Sistemas da FIAP", version = "v1"))
public class FastfoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastfoodApplication.class, args);
    }

}
