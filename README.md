💍 Wedding Planner App

A full-stack web application to plan and manage wedding events. This platform allows users to create wedding events, manage guest lists (RSVP, invites, waitlist), handle registries, and collaborate using a clean user interface and robust backend services.

⸻

📁 Project Structure

weddingplanner/
├── backend/
│   ├── controllers/       # REST APIs (Wedding, User, Item, Comment)
│   ├── services/          # Business logic (WeddingService, ItemService, etc.)
│   ├── repositories/      # MongoDB Repositories
│   ├── models/            # Domain models (Wedding, User, Item, Comment)
│   └── WeddingplannerApplication.java
├── frontend/
│   ├── index.html
│   ├── signup.html
│   ├── profile.html
│   ├── wedding.html
│   ├── myevents.html
│   ├── welcome.html
│   └── EventPlanner.html
└── README.md


⸻

🚀 Features
	•	👰 Create and manage weddings (title, location, datetime, max attendees)
	•	📩 Invite guests, manage RSVP responses, and handle waitlists
	•	🎁 Gift registry system tied to each wedding
	•	💬 Commenting system for guests
	•	👤 User authentication and profile view
	•	📦 REST API endpoints for all major operations
	•	🌐 Session handling for logged-in users
	•	📄 Thymeleaf templates and modern frontend UI design

⸻

🛠️ Technologies Used

Backend
	•	Java 17
	•	Spring Boot
	•	Spring Data MongoDB
	•	RESTful APIs
	•	Thymeleaf for server-side rendering

Frontend
	•	HTML5 / CSS3
	•	Vanilla JavaScript
	•	Thymeleaf (Template engine)

Database
	•	MongoDB (Cloud or Local)

⸻

📌 Installation Instructions

Prerequisites
	•	Java 17+ (OpenJDK 21 recommended)
	•	MongoDB Atlas account (cloud database)
	•	Git

Backend Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd weddingplanner
```

2. Install dependencies using Maven wrapper (no need to install Maven globally):
```bash
./mvnw clean install
```

3. Start the application:
```bash
./mvnw spring-boot:run
```

4. Access the application:
- **Login page**: http://localhost:8080/login
- **Home page**: http://localhost:8080
- **Sign up**: http://localhost:8080/signup

Note: The application uses MongoDB Atlas (cloud database) and is pre-configured with the necessary connection settings in `application.properties`.

Frontend Setup

The HTML templates are located under `src/main/resources/templates/` and are served by the Spring Boot app via Thymeleaf. No separate frontend setup is required.


⸻

🔐 Authentication Flow
	•	index.html → Login form
	•	/validateUser → Validates email & password
	•	Stores session variable: loggedInUser
	•	Redirects to /welcome, /profile, or /myevents

⸻

🔄 API Endpoints (Partial)

Method	Endpoint	Description
POST	/api/v1/weddings	Create a new wedding
GET	/api/v1/weddings/{id}	Get a single wedding by ID
POST	/api/v1/items	Add a registry item
GET	/api/v1/items/{weddingId}	Get all registry items for a wedding
POST	/api/v1/weddings/addInvited	Invite someone to a wedding
POST	/api/v1/weddings/addToWaitlist	Add someone to the waitlist


⸻

🧪 Testing
	•	Use Postman for API testing.
	•	Use browser-based UI to validate workflows:
	•	Login and session management
	•	Create a wedding
	•	Add RSVP and registry items
	•	Invite guests and manage waitlist

⸻

💡 Future Improvements
	•	Add Spring Security for stronger authentication
	•	Persist sessions across restarts (e.g., JWT or cookies)
	•	Enhance RSVP status tracking
	•	Improve registry item tracking (e.g., claimed vs unclaimed)
	•	Add calendar/event reminders via email

⸻

👥 Authors
	•	Developed by Ruben Bolado (Full-Stack) and Luis Sanchez (Contributed with HTML Templates)
	•	University of Texas at El Paso (UTEP)


