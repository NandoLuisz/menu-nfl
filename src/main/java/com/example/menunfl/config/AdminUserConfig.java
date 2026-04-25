package com.example.menunfl.config;

import com.example.menunfl.entity.role.Role;
import com.example.menunfl.entity.user.User;
import com.example.menunfl.repository.RoleRepository;
import com.example.menunfl.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name())
                .orElseGet(() -> {
                    var role = new Role();
                    role.setName(Role.Values.ADMIN.name());
                    return roleRepository.save(role);
                });
        var roleBasic = roleRepository.findByName(Role.Values.BASIC.name())
                .orElseGet(() -> {
                    var role = new Role();
                    role.setName(Role.Values.BASIC.name());
                    return roleRepository.save(role);
                });

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("Admin already exist.");
                },
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin, roleBasic));
                    user.setEmail("admin.default@gmail.com");
                    user.setPhone("85999998888");
                    userRepository.save(user);
                }
        );
    }
}