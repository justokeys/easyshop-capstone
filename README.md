**EasyShop — E-Commerce REST API**

EasyShop is a REST API for an online store, built with **Spring Boot** and backed by a **MySQL** database. It powers a clothing-store web front-end (provided) and handles everything from browsing the catalog to logging in, managing a shopping cart, and checking out. The project is the e-commerce capstone for the Year Up application-development track.

This repository delivers **Version 2** of the API: it adds category management and product CRUD, fixes two reported bugs, and implements the shopping cart, user profile, and checkout features on top of the starter code.

**Features**

- **Browse the catalog** — list all categories, list products in a category, and view a single product.
- **Search & filter products** — filter by category, minimum price, maximum price, and subcategory, in any combination.
- **User accounts** — register a new account and log in; passwords are hashed with BCrypt.
- **JWT authentication** — protected endpoints require a bearer token issued at login.
- **Role-based access** — regular users can shop; only **admins** can create, update, or delete categories and products.
- **Shopping cart** — add products, update quantities, view the cart with a running total, and clear it.
- **User profile** — view and update profile details (name, address, phone, email, etc.).
- **Checkout** — convert the current cart into an order with line items.
- **Interactive API docs** — Swagger UI generated from an OpenAPI spec.

**Tech Stack**

| **Layer** | **Technology** |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 4 (Web MVC, Data JPA, Security, Validation) |
| Persistence | Spring Data JPA / Hibernate, MySQL |
| Security | Spring Security + JWT (jjwt) |
| API docs | springdoc-openapi (Swagger UI) |
| Build | Maven (with Maven Wrapper) |
| Testing | JUnit, Spring Boot Test, H2 (in-memory) |

**Getting Started**

**Prerequisites**

- **JDK 17** or newer
- **MySQL Server 8** and (optionally) **MySQL Workbench**
- **Maven** — or just use the included wrapper (./mvnw)

**1\. Create the database**

Run the EasyShop schema script in MySQL Workbench (or the MySQL CLI). It creates the easyshop database, all tables, and seed data.

database/create_database_easyshop.sql

This script **drops and recreates** the database each time it runs, so it's safe to re-run whenever you want a clean slate.

**2\. Configure your connection**

Database settings live in src/main/resources/application.properties and read from environment variables with sensible defaults:

| **Variable** | **Default** | **Purpose** |
| --- | --- | --- |
| DB_NAME | easyshop | Database name |
| DB_USERNAME | root | MySQL username |
| DB_PASSWORD | password | MySQL password |

Either set those environment variables or edit the defaults to match your local MySQL setup.

**3\. Run the application**

bash

./mvnw spring-boot:run

The API starts on [**http://localhost:8080**](http://localhost:8080).

**API Documentation (Swagger)**

Once the app is running, open the interactive docs:

http://localhost:8080/swagger-ui.html

From here you can explore every endpoint and try requests directly in the browser.

**Seed Accounts**

The schema script seeds these accounts for testing (password for all three is password):

| **Username** | **Role** | **Use for** |
| --- | --- | --- |
| admin | ROLE_ADMIN | Creating/editing/deleting catalog |
| user | ROLE_USER | Shopping as a normal customer |
| george | ROLE_USER | A second regular customer |

**API Endpoints**

Endpoints marked **Admin** require an account with ROLE_ADMIN. Endpoints marked **Auth** require any logged-in user.

**Authentication**

| **Method** | **Path** | **Access** | **Description** |
| --- | --- | --- | --- |
| POST | /register | Public | Create a new user account |
| POST | /login | Public | Log in and receive a JWT token |

**Categories**

| **Method** | **Path** | **Access** | **Description** |
| --- | --- | --- | --- |
| GET | /categories | Public | List all categories |
| GET | /categories/{id} | Public | Get one category |
| GET | /categories/{id}/products | Public | List products in a category |
| POST | /categories | Admin | Create a category |
| PUT | /categories/{id} | Admin | Update a category |
| DELETE | /categories/{id} | Admin | Delete a category |

**Products**

| **Method** | **Path** | **Access** | **Description** |
| --- | --- | --- | --- |
| GET | /products | Public | Search/filter (cat, minPrice, maxPrice, subCategory, isFeatured) |
| GET | /products/{id} | Public | Get one product |
| POST | /products | Admin | Create a product |
| PUT | /products/{id} | Admin | Update a product |
| DELETE | /products/{id} | Admin | Delete a product |

**Shopping Cart**

| **Method** | **Path** | **Access** | **Description** |
| --- | --- | --- | --- |
| GET | /cart | Auth | View the current user's cart |
| POST | /cart/products/{id} | Auth | Add a product to the cart |
| PUT | /cart/products/{id} | Auth | Update the quantity of a cart item |
| DELETE | /cart | Auth | Empty the cart |

**Profile**

| **Method** | **Path** | **Access** | **Description** |
| --- | --- | --- | --- |
| GET | /profile | Auth | View the user's profile |
| PUT | /profile | Auth | Update the user's profile |

**Orders**

| **Method** | **Path** | **Access** | **Description** |
| --- | --- | --- | --- |
| POST | /orders | Auth | Check out — turn the cart into an order |

&lt;!-- SCREENSHOT: An API client (Insomnia/Postman) showing a successful request + response, e.g. GET /products with filters, or a successful /login returning a token. Save as docs/screenshots/api-request.png --&gt;

Show Image

**Bug Fixes (Phase 2)**

Two defects in the starter code were reported and fixed in this version:

**1\. Search leaves products out.** GET /products and its filters were returning an incomplete list — some products that should have matched never appeared. The query logic was corrected so that every matching product is returned for any combination of filters. A unit test covers the fix.

**2\. Product edits don't fully save.** Updating a product through PUT /products/{id} returned OK, but some fields (notably **stock**) never actually changed in the database, while others like price and description updated fine. The update was fixed so that **every** field persists, and a test verifies the corrected behavior.

&lt;!-- OPTIONAL SCREENSHOT: Side-by-side "before/after" of the stock value, or a passing test run. Save as docs/screenshots/bugfix.png --&gt;

**Testing**

Current coverage includes a product repository test and a categories controller test, including the two bug-fix regression tests.

**Project Structure**

src/main/java/org/yearup

├── ECommerceApplication.java # Spring Boot entry point

├── controllers/ # REST endpoints

├── service/ # Business logic

├── repository/ # Spring Data JPA repositories

├── models/ # Entities & DTOs

└── security/ # JWT auth, filters, security config

database/ # SQL schema + seed scripts (easyshop and others)

capstone-starter-files/ # Provided web front-end + Insomnia collection

**A Favorite Piece of Code**

The shopping cart was the hardest part of this project for me. The database stores the cart as plain rows — just a user_id, a product_id, and a quantity — but the API needs to return a full cart with each product's name, price, and a running total. Figuring out how to loop over those rows and rebuild a complete cart was the piece I spent the most time on:

What finally made it click was realizing each stored row only points _at_ a product by id — so the loop has to fetch the real product for every row before the cart has enough information to show prices and totals. Once I understood that, the add and update methods were easy, because both just change the database and then call this same method to rebuild and return the up-to-date cart.

**Future Improvements**

- Order history and an endpoint to retrieve past orders
- Product image upload instead of filename-only references
- Pagination on product search for large catalogs
- More complete test coverage across services and controllers
