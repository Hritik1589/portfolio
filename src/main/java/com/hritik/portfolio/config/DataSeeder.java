package com.hritik.portfolio.config;

import com.hritik.portfolio.entity.Role;
import com.hritik.portfolio.entity.User;
import com.hritik.portfolio.enums.RoleType;
import com.hritik.portfolio.repository.RoleRepository;
import com.hritik.portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value; // Naya Import
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Yahan hum Render se variables lenge. Agar Render par nahi hue (jaise aapke PC par), toh default wale use honge.
    @Value("${ADMIN_EMAIL:admin@portfolio.com}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:admin123}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
        seedAdminUser();
    }

    private void seedRoles() {
        if (roleRepository.findByName(RoleType.ROLE_USER).isEmpty()) {
            roleRepository.save(Role.builder().name(RoleType.ROLE_USER).build());
            log.info("Seeded database with ROLE_USER");
        }

        if (roleRepository.findByName(RoleType.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(Role.builder().name(RoleType.ROLE_ADMIN).build());
            log.info("Seeded database with ROLE_ADMIN");
        }
    }

    private void seedAdminUser() {
        // Ab yahan hardcoded string ki jagah variables aayenge
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin Role not found"));

            User admin = User.builder()
                    .name("Super Admin")
                    .email(adminEmail) // Variable use kiya
                    .password(passwordEncoder.encode(adminPassword)) // Variable use kiya
                    .roles(Set.of(adminRole))
                    .isActive(true)
                    .build();

            userRepository.save(admin);
            log.info("Seeded database with Admin user: " + adminEmail);
        }
    }
}