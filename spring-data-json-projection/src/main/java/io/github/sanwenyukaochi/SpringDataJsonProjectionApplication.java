package io.github.sanwenyukaochi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class SpringDataJsonProjectionApplication {
    static void main(String[] args) {
        SpringApplication.run(SpringDataJsonProjectionApplication.class, args);
    }
}
