package ilyasov.example.pprestcontrollers.initialization;

import ilyasov.example.pprestcontrollers.models.Role;
import ilyasov.example.pprestcontrollers.models.User;
import ilyasov.example.pprestcontrollers.services.RoleService;
import ilyasov.example.pprestcontrollers.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitializationUserFromDB {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitializationUserFromDB(UserService userService,
                                    RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if (roleService.findAll().isEmpty()) {

            Role adminRole = Role.builder().name("ROLE_ADMIN").build();
            Role userRole = Role.builder().name("ROLE_USER").build();

            Set<Role> rolesAdmin = new HashSet<>();
            Set<Role> rolesUser = new HashSet<>();
            rolesAdmin.add(adminRole);
            rolesUser.add(userRole);
            User admin = User.builder()
                    .username("Admin")
                    .lastname("Admin")
                    .age((byte) 30)
                    .email("admin@admin.ru")
                    .password("admin")
                    .roles(rolesAdmin)
                    .build();

            User user = User.builder()
                    .username("User")
                    .lastname("User")
                    .age((byte) 25)
                    .email("user@user.ru")
                    .password("user")
                    .roles(rolesUser)
                    .build();
            roleService.add(adminRole);
            roleService.add(userRole);
            userService.add(admin);
            userService.add(user);

        }
    }
}
