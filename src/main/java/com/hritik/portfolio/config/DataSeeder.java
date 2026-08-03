package com.hritik.portfolio.config;

import com.hritik.portfolio.entity.Role;
import com.hritik.portfolio.entity.User; // Ensure this matches your User entity import
import com.hritik.portfolio.enums.RoleType;
import com.hritik.portfolio.repository.RoleRepository;
import com.hritik.portfolio.repository.UserRepository; // Ensure this matches your repository
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if (userRepository.findByEmail("admin@portfolio.com").isEmpty()) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Admin Role not found"));

            User admin = User.builder()
                    .name("Super Admin") // <-- THIS WAS MISSING!
                    .email("admin@portfolio.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(adminRole))
                    .isActive(true) // Uncomment if your entity requires an active flag directly
                    .build();

            userRepository.save(admin);
            log.info("Seeded database with default Admin user: admin@portfolio.com / admin123");
        }
    }
}