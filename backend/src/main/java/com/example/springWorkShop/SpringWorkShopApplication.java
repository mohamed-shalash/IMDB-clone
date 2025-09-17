package com.example.springWorkShop;

import com.example.springWorkShop.dto.User;
import com.example.springWorkShop.repo.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class SpringWorkShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWorkShopApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializeData(UserRepository userRepo,
                                            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepo.findByUserName("user").isEmpty()) {
                userRepo.save(new User(
                        "user",
                        passwordEncoder.encode("user"),
                        "ROLE_USER"         // full authority
                ));
            }
            if (userRepo.findByUserName("admin").isEmpty()) {
                userRepo.save(new User(
                        "admin",
                        passwordEncoder.encode("admin"),
                        "ROLE_ADMIN"
                ));
            }
        };
    }


}
