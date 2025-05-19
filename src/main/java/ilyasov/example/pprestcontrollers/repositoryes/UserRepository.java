package ilyasov.example.pprestcontrollers.repositoryes;

import ilyasov.example.pprestcontrollers.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String username);
    Optional<User> findByUsername(String username);
}
