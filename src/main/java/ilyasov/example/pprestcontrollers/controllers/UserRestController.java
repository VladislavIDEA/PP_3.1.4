package ilyasov.example.pprestcontrollers.controllers;

import ilyasov.example.pprestcontrollers.mappers.UserMapper;
import ilyasov.example.pprestcontrollers.models.User;
import ilyasov.example.pprestcontrollers.models.UserDTO;
import ilyasov.example.pprestcontrollers.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
    private final UserMapper userMapper;
    private final UserService userService;

    @Autowired
    public UserRestController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping("user_profile")
    public ResponseEntity<UserDTO> userPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            log.warn("Unauthorized access to user_profile");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String email = authentication.getName(); // email из UserDetails
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("Fetching profile for user: {}", user.getEmail());
        UserDTO userDTO = userMapper.fromDTO(user);
        log.debug("UserDTO: {}", userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}