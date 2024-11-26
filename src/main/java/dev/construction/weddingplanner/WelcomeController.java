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

    // Route for the Event Planning Page (EventPlanning.html)
    @GetMapping("/event")
    public String showEventPlanningPage(Model model) {
        // Add attributes to the model for dynamic rendering
        model.addAttribute("id", "12345");
        model.addAttribute("weddingTitle", "Dream Wedding");
        model.addAttribute("dateTime", "12/31/2024 6:00 PM");
        model.addAttribute("location", "Central Park, NYC");
        model.addAttribute("createdBy", new User("John Doe")); // Replace with your User object
        model.addAttribute("attendees", List.of(new User("Jane Doe"), new User("Emily Smith")));
        model.addAttribute("maxAttendees", 100);
        model.addAttribute("waitlist", List.of("John Smith", "Sarah Lee"));
        model.addAttribute("registry", List.of(new Item("Dinner Set"), new Item("Wine Glasses")));
        return "EventPlanning"; // Matches EventPlanning.html in the templates folder
    }
}