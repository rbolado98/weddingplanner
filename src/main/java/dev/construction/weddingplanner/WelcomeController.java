package dev.construction.weddingplanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

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
        JSONArray weddingarray = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/weddings"))
                .build();
        List<Wedding> weddings = new ArrayList<Wedding>();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // System.out.println(response.body());
            weddingarray = new JSONArray(response.body());
            for (int i = 0; i < weddingarray.length(); i++) {
                System.out.println(i);
                JSONObject wedding = weddingarray.getJSONObject(i);
                /*
                 *  "createdBy": {
                    "id": null,
                    "name": "John Doe",
                    "email": "john.doe@example.com",
                    "password": "Password123",
                    "admin": true
                },
                 */
                User user = null;
                if(wedding.get("createdBy").equals("null")){
                    System.out.println(wedding.get("createdBy"));
                    JSONObject userobject = wedding.getJSONObject("createdBy");
                    user = new User(
                        userobject.getString("name"),
                        userobject.getString("email"),
                        userobject.getString("password"),
                        userobject.getBoolean("admin")
                    );
                }
                weddings.add(
                    new Wedding(wedding.getString("weddingId"), 
                    wedding.getString("weddingTitle"), 
                    wedding.getString("dateTime"), 
                    wedding.getString("location"), 
                    user,
                    wedding.getString("maxAttendees")));
            }
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
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