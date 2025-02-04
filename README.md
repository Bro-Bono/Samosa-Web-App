# Samosa Web App

Samosa Web App is a web-based order management system designed to help local food business owners efficiently track and process customer orders. Customers submit orders via an HTML Form, which are stored in a database and managed through a business "dashboard" in a Spring Boot web application.

## Features
- **Order Management:** Business owners can view and update orders through an intuitive interface.
- **Order Status Tracking:** Orders are categorized into "New," "In-Progress," and "Complete."
- **Email Notifications:** Customers receive order confirmation emails, and business owners are notified of new orders.
- **Spring Boot Backend:** Implements MVC architecture with controllers, services, and database integration.
- **MySQL Database:** Orders are stored and retrieved using a relational database.
- **Form Validation:** Ensures customers submit valid order details.

## Technologies Used
- **Backend:** Java, Spring Boot, Spring MVC, Spring Mail
- **Database:** MySQL
- **Frontend:** Thymeleaf, HTML, CSS
- **Version Control:** GitHub

## Project Structure
```
SamosaWebApp/
│── brobono.samosawebapp/
│   ├── controllers/
│   │   ├── OrderController.java
│   │   ├── CustomerController.java
│   │   ├── BusinessOwnerController.java
│   │   ├── HomeController.java
│   ├── models/
│   │   ├── Order.java
│   ├── services/
│   │   ├── OrderService.java
│   │   ├── EmailService.java
│   ├── repositories/
│   │   ├── OrderRepository.java
│── src/main/resources/
│   ├── static/
│   │   ├── css/
│   │   │   ├── styles.css
│   ├── templates/
│   │   ├── index.html
│   │   ├── order-form.html
│   │   ├── order-confirmation.html
│   │   ├── dashboard.html
│   │   ├── new-order-email.html
│── README.md
```

## Installation & Setup
### Prerequisites
- Install **Java 17+**
- Install **Spring Boot**
- Install **MySQL Server** and **MySQL Workbench**
- Install **Git** and **GitHub CLI**

### Steps to Run Locally
1. Clone the repository:
   ```sh
   git clone https://github.com/your-org/samosa-web-app.git
   cd samosa-web-app
   ```
2. Configure **application.properties** for database connection (you may need to set up your own if trying this independently):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/samosadb
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
3. Run the Spring Boot application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the app at:
   ```
   http://localhost:8081
   ```

## Usage
- **Customers**: Fill out the HTML Form to place an order.
- **Business Owners**: Log into the web app to manage incoming orders and update statuses.
- **Email Notifications**: Sent automatically to customers and business owners.

## Contributing
This project is being developed collaboratively by peers. Please reach out if you'd like to be added to our team on Jira.

## License
This project is open-source and available under the **MIT License**.

