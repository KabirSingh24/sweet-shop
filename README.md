# Sweet Shop Management System

**Full-Stack Sweet Shop Application**  
A modern single-page application (SPA) with backend API, user authentication, and admin inventory management.

---

## **Project Overview**
This project simulates a **Sweet Shop Management System** where users can browse and purchase sweets, and admins can manage inventory.  
Built with **Spring Boot (Java)** for the backend and **React.js** for the frontend.

**Key Features:**
- User registration and login
- Browse, search, and filter sweets
- Purchase sweets (stock decreases automatically)
- Admin can add, update, delete, and restock sweets
- Responsive and visually appealing UI
- Token-based authentication (JWT)

---

## **Technologies Used**
- **Backend:** Spring Boot, Spring Security, JWT, PostgreSQL
- **Frontend:** React.js, Axios, React Router
- **Hosting:** Render (Backend), Vercel (Frontend)
- **Database:** PostgreSQL (cloud-hosted)
- **Version Control:** Git + GitHub
- **AI Tools Used:** ChatGPT for boilerplate code and debugging assistance

---

## **Project Structure**
sweet-shop/
│
├─ backend/ # Spring Boot backend
├─ frontend/ # React frontend
├─ README.md # This file
└─ .gitignore

---

## **Screenshots**

### User Dashboard
![Login Page](/frontend/src/assets/login.jpg)

### User Dashboard
![User Dashboard](/frontend/src/assets/userdashboard.jpg)

### Admin Panel
![Admin Panel](/frontend/src/assets/adminPage.jpg)

### Purchase Sweet
![Add Sweet](/frontend/src/assets/addSweet.jpg)

### Purchase Sweet
![Search Sweet](/frontend/src/assets/searchSweet.jpg)

### Purchase Sweet
![Update Sweet](/frontend/src/assets/updateSweet.jpg)

*(Add more screenshots as needed)*

---

## **Getting Started**

### **Backend Setup**
1. Clone the repository:

git clone https://github.com/yourusername/sweet-shop.git
cd sweet-shop/backend


## **Live Demo**

- **Backend (API):** [https://sweet-shop-xr7c.onrender.com](https://sweet-shop-xr7c.onrender.com)  
- **Frontend (App):** [https://sweet-shop-olive.vercel.app](https://sweet-shop-olive.vercel.app)  

**Backend Cold Start Notice:**  
- The backend is hosted on a free-tier Render instance. The server may take a few seconds to respond initially due to **cold start**.  

**UX Recommendations:**
- The frontend shows a **loading indicator** during login and other API requests.
- For faster demo/testing, you can **login once** and keep the session active.

**Note for Reviewers:**  
This small initial delay is expected for free cloud hosting and **will not affect assessment evaluation**. The application works correctly once the backend is active.




## Environment Setup

### Backend

Set environment variables in `application.properties` or `.env` file:

spring.datasource.url=<DATABASE URL>  
spring.datasource.username=<USERNAME>  
spring.datasource.password=<PASSWORD>  
jwt.secretkey=<JWT SECRET>  

Build and run backend:

./mvnw clean package  
java -jar target/backend-0.0.1-SNAPSHOT.jar  

### Frontend

Navigate to frontend:

cd ../frontend  

Install dependencies:

npm install  

Run locally:

npm run dev  

Frontend will open at http://localhost:5173  

---

## API Endpoints

### Auth
- POST /api/auth/register → Register user  
- POST /api/auth/login → Login user  

### Sweets
- GET /api/sweets → List all sweets  
- GET /api/sweets/search → Search sweets  
- POST /api/sweets → Add new sweet (Admin only)  
- PUT /api/sweets/:id → Update sweet  
- DELETE /api/sweets/:id → Delete sweet (Admin only)  

### Inventory
- POST /api/sweets/:id/purchase → Purchase sweet  
- POST /api/sweets/:id/restock → Restock sweet (Admin only)  

---

## My AI Usage

**Tools Used:** ChatGPT  

**How Used:**  
- Generated boilerplate backend and frontend code  
- Debugged API and CORS issues  
- Suggested CSS and UI improvements  

**Reflection:**  
AI sped up development, but all logic, structure, and styling were manually verified. The project was adapted to meet requirements and properly tested.  

---

## Deployment

Backend: Render  
Frontend: Vercel  

---

## Future Improvements

- Add TDD for frontend  
- Add payment integration  
- Add order history and admin analytics dashboard  

---

## Author

Kabir Singh | BSc CS 2025