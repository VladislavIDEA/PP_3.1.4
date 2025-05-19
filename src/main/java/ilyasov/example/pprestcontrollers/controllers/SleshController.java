package ilyasov.example.pprestcontrollers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SleshController {
    @GetMapping("/")
    public String redirectTiAdmin() {
        return "redirect:/admin.html";
    }
}
