package dev.construction.weddingplanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/") // Maps the root URL
    public String showWelcomePage() {
        return "welcome"; // Matches the welcome.html file in templates
    }
}