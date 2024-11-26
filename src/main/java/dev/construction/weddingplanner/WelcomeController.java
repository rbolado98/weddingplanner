package dev.construction.weddingplanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    public String showProfilePage(Model model) {
        // Add attributes to the model for dynamic rendering
        /*
         * public Wedding(String weddingId, String weddingTitle, String dateTime, String location, User createdBy, int maxAttendees) {
        this.weddingId = weddingId;
        this.weddingTitle = weddingTitle;
        this.dateTime = dateTime;
        this.location = location;
        this.createdBy = createdBy;
        this.maxAttendees = maxAttendees;
    }
         */
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/weddings"))
                .build();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
        List<Wedding> weddings = List.of(
            new Wedding("12345", "Dream Wedding", "12/31/2024 6:00 PM", "Central Park, NYC", new User("John Doe"), 100),
            new Wedding("67890", "Beach Wedding", "06/30/2024 4:00 PM", "Miami Beach, FL", new User("Jane Doe"), 50)
        );
        model.addAttribute("weddings", weddings);
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

    // Route for My Events Page (myevents.html)
    @GetMapping("/myevents")
    public String showMyEventsPage() {
        return "myevents"; // Matches myevents.html in the templates folder
    }
}