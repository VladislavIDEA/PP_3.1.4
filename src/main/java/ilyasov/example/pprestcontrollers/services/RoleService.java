package ilyasov.example.pprestcontrollers.services;

import ilyasov.example.pprestcontrollers.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    void add(Role role);

    Role findByName(String name);
}
