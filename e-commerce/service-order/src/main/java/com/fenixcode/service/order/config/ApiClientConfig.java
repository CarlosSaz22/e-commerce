package com.fenixcode.service.order.config;

import com.fenixcode.service.order.proxy.ApiClient;
import com.fenixcode.service.order.proxy.api.InventoryApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiClientConfig {
    @Value("${client-micro.micro-order.base-url}")
    private String urlApiInventory;

    @Bean
    public ApiClient apiClient(WebClient webClient){
        ApiClient apiClient = new ApiClient(webClient);
        apiClient.setBasePath(this.urlApiInventory);
        return apiClient;
    }

    @Bean
    public InventoryApi inventoryApi(ApiClient apiClient){
        return new InventoryApi(apiClient);
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder){
        return WebClient.builder().build();
    }
}
