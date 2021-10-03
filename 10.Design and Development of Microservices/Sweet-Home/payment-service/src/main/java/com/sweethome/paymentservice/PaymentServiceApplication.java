package com.sweethome.paymentservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    /**
     * Load Balanced RestTemplate that fetch remote service URL from Eureka service
     * @return
     */
    @Bean
    @LoadBalanced
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
