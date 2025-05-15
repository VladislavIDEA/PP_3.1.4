package ilyasov.example.pprestcontrollers.controllers;

import ilyasov.example.pprestcontrollers.mappers.UserMapper;
import ilyasov.example.pprestcontrollers.models.User;
import ilyasov.example.pprestcontrollers.models.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserMapper userMapper;


    public UserRestController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("user_profile")
    public UserDTO userPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return userMapper.fromDTO(user);
    }
}
