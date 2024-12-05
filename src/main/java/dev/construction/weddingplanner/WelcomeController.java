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
import java.util.Optional;

import org.bson.types.ObjectId;
import org.json.*;

@Controller
public class WelcomeController {
    @Autowired
    private UserService userService;
    // Route for the Welcome Page
    @GetMapping("/")
    public String showWelcomePage(Model model, HttpSession session) {
        if (userService.findUserByEmail(session.getAttribute("loggedInUser").toString()).get().isAdmin()) {
            model.addAttribute("admin", true);
        }
        return "welcome"; // Matches welcome.html in the templates folder
    }

    // Route for the Profile Page
    @GetMapping("/profile")
    public String showProfilePage(Model model, HttpSession session) {
        HttpClient client0 = HttpClient.newHttpClient();
        HttpRequest request0 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/users/" + session.getAttribute("loggedInUser").toString()))
                .build();
        User userm = null;
        try {
            // Fetch user details
            HttpResponse<String> response0 = client0.send(request0, HttpResponse.BodyHandlers.ofString());
            JSONObject userObject = new JSONObject(response0.body());
            userm = new User(
                userObject.getString("name"),
                userObject.getString("email"),
                userObject.getString("password"),
                userObject.getBoolean("admin")
            );
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        
        model.addAttribute("user", userm);
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
    
    @PostMapping("/validateUser")
    public String handleLogin(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findUserByEmail(email).get();
        if (userService.authenticate(email, password)) {
            model.addAttribute("email", email);
            session.setAttribute("loggedInUser", email);
            if (user.isAdmin()) {
                model.addAttribute("admin", true);
            }
            return "redirect:/"; // Redirect to profile page on successful login
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "index"; // Return to login page with error message
        }
    }
    
    @Autowired
    private WeddingService weddingService;
    @PostMapping("/createwedding")
    public String createWedding(@RequestParam String weddingTitle, @RequestParam String location, @RequestParam String dateTime, @RequestParam String maxAttendees, @RequestParam String email, Model model, HttpSession session) {   
        User user = userService.findUserByEmail(session.getAttribute("loggedInUser").toString()).get();
        weddingService.createWedding(weddingTitle, dateTime, location, maxAttendees, user);
        return "redirect:/myevents";
        // return new ResponseEntity<Wedding>(weddingService.createWedding("weddingId", "weddingTitle", "dateTime", "location", "maxAtt"), HttpStatus.CREATED);
    }
    @PostMapping("/attendwedding")
    public String attendWedding(@RequestParam String weddingId, @RequestParam String email) {
        weddingService.attendWedding(weddingId, email);
        return "redirect:/attending";
    }

    @GetMapping("/logout")
    public String handleLogout(Model model, HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/login"; // Redirect to home page on logout
    }

    // Route for the Signup Page (signup.html)
    @PostMapping("/createuser")
    public String handleSignup(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String confirmpassword, HttpSession session, Model model) {
        if (userService.checkIfUserExists(email)) {
            model.addAttribute("error", "User already exists");
        } if (!password.equals(confirmpassword)) {
            model.addAttribute("error", "Passwords do not match");
        } else {
            userService.createUser(name, email, password);
            session.setAttribute("loggedInUser", email);
            model.addAttribute("email", email);
            return "redirect:/"; // Redirect to home page on successful signup
        }
        return "signup"; // Matches signup.html in the templates folder
    }


    // Route for My Events Page (myevents.html)
    @GetMapping("/myevents")
    public String showMyEventsPage(Model model, HttpSession session) {
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
                // .uri(URI.create("http://localhost:8080/api/v1/weddings"))
                // call by user
                .uri(URI.create("http://localhost:8080/api/v1/weddings/created/" + session.getAttribute("loggedInUser").toString()))

                .build();
        List<Wedding> weddings = new ArrayList<Wedding>();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
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

    @GetMapping("/attending")
    public String showAttendingPage(Model model, HttpSession session) {
        
        JSONArray weddingarray = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                // .uri(URI.create("http://localhost:8080/api/v1/weddings"))
                // call by user
                .uri(URI.create("http://localhost:8080/api/v1/weddings/attending/" + session.getAttribute("loggedInUser").toString()))

                .build();
        List<Wedding> weddings = new ArrayList<Wedding>();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
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
        return "attending"; // Matches profile.html in the templates folder
    }

    @GetMapping("/allweddings")
    public String showAllWeddings(Model model, HttpSession session) {
        
        JSONArray weddingarray = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/weddings"))
                .build();
        List<Wedding> weddings = new ArrayList<Wedding>();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
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
        return "allweddings"; // Matches profile.html in the templates folder
    }

    // Route for the Lookup Page (lookup.html)
    @GetMapping("/attend")
    public String showAttendPage(HttpSession session) {
        System.out.println(session.getAttribute("loggedInUser").toString());
        return "attend"; // Matches attend.html in the templates folder
    }

    @GetMapping("/EventPlanner")
    public String showEventPlannerPage(Model model, HttpSession session) {
        Optional<User> user = userService.findUserByEmail(session.getAttribute("loggedInUser").toString());
        System.out.println(session.getAttribute("loggedInUser").toString());
        System.out.println(user);
        model.addAttribute("loggedUser", user.get()); 
        return "EventPlanner"; // Matches EventPlanner.html in the templates folder
    }


    @SuppressWarnings("null")
    @GetMapping("/wedding")
    public String showWeddingPage(Model model, HttpSession session, @RequestParam String weddingId) {
        User loggedInUser = userService.findUserByEmail(session.getAttribute("loggedInUser").toString()).get();
        
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
            model.addAttribute("admin", false);
            if(loggedInUser.isAdmin()){
                model.addAttribute("admin", true);
            }
            if (!weddingObject.get("createdBy").equals("null")) {
                JSONObject userObject = weddingObject.getJSONObject("createdBy");
                System.out.println("UserObject: " + userObject);
                user = new User(
                        userObject.getString("name"),
                        userObject.getString("email"),
                        userObject.getString("password"),
                        userObject.getBoolean("admin")
                );
                
            }
            List<String> invitedList = new ArrayList<>();
            if (weddingObject.has("invited") && !weddingObject.isNull("invited")) {
                JSONArray invitedArray = weddingObject.getJSONArray("invited");
                for (int i = 0; i < invitedArray.length(); i++) {
                    invitedList.add(invitedArray.getString(i)); // Extract each string
                }
                wedding = new Wedding(
                    weddingObject.getString("weddingId"), 
                    weddingObject.getString("weddingTitle"),
                    weddingObject.getString("dateTime"),
                    weddingObject.getString("location"),
                    user,
                    invitedList,
                    weddingObject.getString("maxAttendees")
            );
            }
            List<String> waitlist = new ArrayList<>();
            if (weddingObject.has("waitlist") && !weddingObject.isNull("waitlist")) {
                JSONArray waitlistArray = weddingObject.getJSONArray("waitlist");
                for (int i = 0; i < waitlistArray.length(); i++) {
                    waitlist.add(waitlistArray.getString(i)); // Extract each string
                }
                wedding.setWaitlist(waitlist);
            } else{
            wedding = new Wedding(
                    weddingObject.getString("weddingId"),
                    weddingObject.getString("weddingTitle"),
                    weddingObject.getString("dateTime"),
                    weddingObject.getString("location"),
                    user,
                    weddingObject.getString("maxAttendees")
            );
            }

            
            if(wedding.getCreatedBy().getEmail().equalsIgnoreCase(loggedInUser.getEmail())){
                model.addAttribute("admin", true);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }


        // Fetch registry items for the wedding
        List<Item> registry = new ArrayList<Item>();
        try{
            // Fetch registry items for the wedding
            JSONArray registryArray = null;
            HttpRequest registryRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/items/" + weddingId))
                    .build();
                HttpResponse<String> registryResponse = client.send(registryRequest, HttpResponse.BodyHandlers.ofString());
                registryArray = new JSONArray(registryResponse.body());
                for (int i = 0; i < registryArray.length(); i++) {
                    JSONObject item = registryArray.getJSONObject(i);
                    System.out.println("Item: " + item);
                    registry.add(new Item(
                            item.getString("itemId"),
                            item.getString("name"),
                            item.getString("link"),
                            item.getBoolean("purchased")
                    ));
                }
        } 
        catch (Exception e) {
            System.out.println("Error: " + e);
        }

        // Fetch Attendees for the wedding
        List<User> attendees = new ArrayList<User>();
        try{
            JSONArray attendeesArray = null;
            HttpRequest attendeesRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/weddings/getAttendees/" + weddingId))
                    .build();
                HttpResponse<String> attendeesResponse = client.send(attendeesRequest, HttpResponse.BodyHandlers.ofString());
                attendeesArray = new JSONArray(attendeesResponse.body());
                for (int i = 0; i < attendeesArray.length(); i++) {
                    JSONObject attendee = attendeesArray.getJSONObject(i);
                    attendees.add(new User(
                            attendee.getString("name"),
                            attendee.getString("email")
                    ));
                }
        } 
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
        // Add wedding and registry to the model
        model.addAttribute("attendeesCount", attendees.size());
        model.addAttribute("attendees", attendees);
        model.addAttribute("wedding", wedding);
        model.addAttribute("registry", registry);
        return "wedding"; // Matches wedding.html in the templates folder
    }

    
}