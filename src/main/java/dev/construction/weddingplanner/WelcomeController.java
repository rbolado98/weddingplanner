package dev.construction.weddingplanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WelcomeController {

    // Route for the Welcome Page
    @GetMapping("/")
    public String showWelcomePage() {
        return "welcome"; // Matches welcome.html in the templates folder
    }

    // Route for the Profile Page
    @GetMapping("/profile")
    public String showProfilePage() {
        return "profile"; // Matches profile.html in the templates folder
    }

    // Route for the Login Page (index.html)
    @GetMapping("/login")
    public String showLoginPage() {
        return "index"; // Matches index.html in the templates folder
    }

    // Route for the Signup Page (signup.html)
    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup"; // Matches signup.html in the templates folder
    }

    // Route for the Event Planner Page (eventplanner.html)
    @GetMapping("/eventplanner")
    public String showEventPlannerPage() {
        return "eventplanner"; // Matches eventplanner.html in the templates folder
    }
}