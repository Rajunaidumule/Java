package com.td.productCatalog.config;

import com.td.productCatalog.builder.WebClientBuilder;
import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;


@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }
    @Bean("customWebClientBuilder")
    public WebClientBuilder getcustomwebClientBuilder() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8083")
                .defaultHeaders(headers -> headers.addAll(httpHeaders))
                .clientConnector(new ReactorClientHttpConnector(getHttpClientConfig()))
                .build();
        return WebClientBuilder.builder().client(webClient).build();
    }

    private HttpClient getHttpClientConfig() {
        return HttpClient.create(ConnectionProvider.builder("webClientConnectionProvider")
                .maxConnections(100)
                .maxIdleTime(Duration.ofMillis(20000))
                .maxLifeTime(Duration.ofMillis(20000))
                .build())
                .responseTimeout(Duration.ofMillis(10000))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000).secure();
    }

}
