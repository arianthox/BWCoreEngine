package com.globant.brainwaves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableEurekaServer
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.globant.brainwaves.commons","com.globant.brainwaves"})
public class CoreEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreEngineApplication.class, args);
    }
}
