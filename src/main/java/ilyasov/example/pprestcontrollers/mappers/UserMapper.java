package ilyasov.example.pprestcontrollers.mappers;

import ilyasov.example.pprestcontrollers.models.Role;
import ilyasov.example.pprestcontrollers.models.User;
import ilyasov.example.pprestcontrollers.models.UserDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO fromDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .password(null)
                .build();
    }

    public User fromEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .lastname(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles(null)
                .build();
    }
}
