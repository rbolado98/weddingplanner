Here’s a comprehensive README.md for your Wedding Planner system, tailored to the components you’ve shared:

⸻

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
	•	Java 17+
	•	Maven
	•	MongoDB (running locally or via Atlas)
	•	Git

Backend Setup

cd backend
mvn clean install
mvn spring-boot:run

Ensure your application.properties includes:

spring.data.mongodb.database=weddingplanner
spring.data.mongodb.uri=mongodb://localhost:27017
server.port=8080

Frontend Setup

The HTML files are located under src/main/resources/templates/ and are served by the Spring Boot app via Thymeleaf.

To access the app:

http://localhost:8080/


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
	•	Developed by Ruben Bolado and Luis
	•	University of Texas at El Paso (UTEP)

⸻

Let me know if you want a PDF version or if you’d like a shortened summary for a poster/demo handout.
