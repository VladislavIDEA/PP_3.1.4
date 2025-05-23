package ilyasov.example.pprestcontrollers.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Builder
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String lastName;
    private Byte age;
    private String email;
    private Set<String> roles = new HashSet<>();
    private String password;
}
