package ilyasov.example.pprestcontrollers.services;

import ilyasov.example.pprestcontrollers.models.Role;
import ilyasov.example.pprestcontrollers.models.User;
import ilyasov.example.pprestcontrollers.repositoryes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void add(User user) {
        // Если роли не передали, добавляем роль по умолчанию
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleService.findByName("ROLE_USER");
            user.setRoles(Set.of(defaultRole));
        } else {
            // Маппинг ролей по имени на сущности из БД
            Set<Role> roles = user.getRoles().stream()
                    .map(role -> roleService.findByName(role.getName()))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void update(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setLastname(user.getLastname());
            userToUpdate.setEmail(user.getEmail());

            // Обновляем пароль, только если он не пустой (чтобы не затирать существующий)
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            // Маппинг ролей по имени
            Set<Role> roles = user.getRoles().stream()
                    .map(role -> roleService.findByName(role.getName()))
                    .collect(Collectors.toSet());
            userToUpdate.setRoles(roles);

            userRepository.save(userToUpdate);
        }
    }


    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
