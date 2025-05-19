package ilyasov.example.pprestcontrollers.initialization;

import ilyasov.example.pprestcontrollers.models.Role;
import ilyasov.example.pprestcontrollers.models.User;
import ilyasov.example.pprestcontrollers.services.RoleService;
import ilyasov.example.pprestcontrollers.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitializationUserFromDB {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitializationUserFromDB(UserService userService,
                                    RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if (userService.findAll().isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleService.add(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleService.add(userRole);

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            User admin = new User();
            admin.setUsername("Admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.ru");
            admin.setRoles(adminRoles);
            userService.add(admin);

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            User user = new User();
            user.setUsername("User");
            user.setPassword(passwordEncoder.encode("user"));
            user.setEmail("user@user.ru");
            user.setRoles(userRoles);
            userService.add(user);
        }
    }
}
