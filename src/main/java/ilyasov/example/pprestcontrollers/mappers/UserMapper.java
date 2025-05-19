package ilyasov.example.pprestcontrollers.mappers;

import ilyasov.example.pprestcontrollers.models.Role;
import ilyasov.example.pprestcontrollers.models.User;
import ilyasov.example.pprestcontrollers.models.UserDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO fromDTO(User user) {
        Set<String> roles = user.getRoles() == null ? new HashSet<>() :
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toCollection(LinkedHashSet::new));

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .lastName(user.getLastname())
                .age(user.getAge())
                .email(user.getEmail())
                .roles(roles)
                .password(null)
                .build();
    }

    public User fromEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .lastname(userDTO.getLastName())
                .age(userDTO.getAge())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles(userDTO.getRoles().stream()
                        .map(roleName -> Role.builder().name(roleName).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
