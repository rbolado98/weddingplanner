package dev.construction.weddingplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

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
                JSONObject wedding = weddingarray.getJSONObject(i);
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

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup"; // Matches signup.html in the templates folder
    }
    // Handle login form submission
    @Autowired
    private UserService userService;
    @PostMapping("/validateUser")
    public String handleLogin(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        if (userService.authenticate(email, password)) {
            model.addAttribute("email", email);
            session.setAttribute("loggedInUser", email);
            return "redirect:/"; // Redirect to profile page on successful login
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "index"; // Return to login page with error message
        }
    }
    
    @Autowired
    private WeddingService weddingService;
    @PostMapping("/createwedding")
    public String createWedding(@RequestParam String weddingTitle, @RequestParam String location, @RequestParam String dateTime, @RequestParam String maxAttendees) {
        weddingService.createWedding(weddingTitle, dateTime, location, maxAttendees);
        return "redirect:/myevents";
        // return new ResponseEntity<Wedding>(weddingService.createWedding("weddingId", "weddingTitle", "dateTime", "location", "maxAtt"), HttpStatus.CREATED);
    }
    @PostMapping("/attendwedding")
    public String attendWedding(@RequestParam String weddingId, @RequestParam String email) {
        weddingService.attendWedding(weddingId, email);
        return "redirect:/myevents";
    }

    // Route for the Signup Page (signup.html)
    @PostMapping("/createuser")
    public String handleSignup(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        if (userService.checkIfUserExists(email)) {
            model.addAttribute("error", "User already exists");
        } else {
            userService.createUser(name, email, password);
            model.addAttribute("email", email);
            return "redirect:/profile"; // Redirect to profile page on successful signup
        }
        return "signup"; // Matches signup.html in the templates folder
    }


    // Route for My Events Page (myevents.html)
    @GetMapping("/myevents")
    public String showMyEventsPage(Model model) {
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
        return "myevents"; // Matches profile.html in the templates folder
    }

    // Route for the Lookup Page (lookup.html)
    @GetMapping("/attend")
    public String showAttendPage() {
        return "attend"; // Matches attend.html in the templates folder
    }

    @GetMapping("/EventPlanner")
    public String showEventPlannerPage() {
        return "EventPlanner"; // Matches EventPlanner.html in the templates folder
    }


    @GetMapping("/wedding")
public String showWeddingPage(Model model, @RequestParam String weddingId) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/api/v1/weddings/" + weddingId))
            .build();
    Wedding wedding = null;
    try {
        // Fetch wedding details
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject weddingObject = new JSONObject(response.body());

        User user = null;
        if (!weddingObject.get("createdBy").equals("null")) {
            JSONObject userObject = weddingObject.getJSONObject("createdBy");
            user = new User(
                    userObject.getString("name"),
                    userObject.getString("email"),
                    userObject.getString("password"),
                    userObject.getBoolean("admin")
            );
        }

        wedding = new Wedding(
                weddingObject.getString("weddingId"),
                weddingObject.getString("weddingTitle"),
                weddingObject.getString("dateTime"),
                weddingObject.getString("location"),
                user,
                weddingObject.getString("maxAttendees")
        );

        
    } catch (Exception e) {
        System.out.println("Error: " + e);
    }
    List<Item> registry = new ArrayList<Item>();
    try{
        // Fetch registry items for the wedding
        JSONArray registryArray = null;
        HttpRequest registryRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/items/" + weddingId))
                .build();
            HttpResponse<String> registryResponse = client.send(registryRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(registryResponse.body());
            registryArray = new JSONArray(registryResponse.body());
            for (int i = 0; i < registryArray.length(); i++) {
                JSONObject item = registryArray.getJSONObject(i);
                registry.add(new Item(
                        item.getString("name"),
                        item.getString("link")
                ));
            }
    } 
    catch (Exception e) {
        System.out.println("Error: " + e);
    }
    System.out.println(registry);
    // Add wedding and registry to the model
    model.addAttribute("wedding", wedding);
    model.addAttribute("registry", registry);
    return "wedding"; // Matches wedding.html in the templates folder
}

    
}