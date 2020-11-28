package com.ncuz.gateway;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ncuz.encryption.service.PropertiesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@EnableDiscoveryClient
@SpringBootApplication
//@RestController
@EnableZuulProxy
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @PostConstruct
    private void post() {
        PropertiesService.initApplicationProperties();
        System.out.println("POST GATEWAY_SERVICE");
    }
    @Bean
    public HttpTraceRepository htttpTraceRepository()
    {
        return new InMemoryHttpTraceRepository();
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        // This line below is the magic one for me. Obviously, if need to switch properties to true, use featuresToEnable
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


        return builder;
    }

}
