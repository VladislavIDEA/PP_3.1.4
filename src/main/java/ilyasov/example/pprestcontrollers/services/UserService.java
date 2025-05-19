package ilyasov.example.pprestcontrollers.services;

import ilyasov.example.pprestcontrollers.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);

    void update(Long id, User user);

    void delete(Long id);

    List<User> findAll();

    User findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
