# retailers
* [Project Description](#project-description)
* [Technologies used](#project-technologies)
* [DB Scheme](#db-scheme)
* [Roles](#roles)
* [Authorized user's scope](#authorized-users-scope)
* [System admin's scope](#system-admins-scope)
* [Admin's scope](#admins-scope)
* [Dispatcher's scope](#dispatchers-scope)
* [Warehouse Manager's scope](#warehouse-managers-scope)
* [Shop Manager's scope](#shop-managers-scope)
* [Director's scope](#directors-scope)
* [Additional Info](#additional-info)

# Project Description
Retailers system

# Technologies used
* Spring, Spring Boot, Spring Security, Spring Data JPA
* React
* MySql
* Slf4j
* JUnit 5, Mockito, H2 db

# DB Scheme
![image](https://user-images.githubusercontent.com/62715846/149124824-99e2385c-7df8-42a8-80c9-ea2c1e10affb.png)
![image](https://user-images.githubusercontent.com/62715846/149124854-a4287da2-d8e0-4f23-a40d-e65244760db5.png)

# Roles
* System Admin
* Admin
* Dispatcher
* Warehouse Manager
* Shop Manager
* Director

# Authorized user's scope
* Login
* View profile
* Update profile information

# System admin's scope
* Registrate new customers(retail companies)
* Activate/deactivate customers

# Admin's scope
* Managing locations of a retail company (warehouses & offline shops)
* Adding, disabling and enabling customer employees (Dispatchers, Warehouse Managers, Shop Managers, Directors)
* Registrate new items for the company
* Managing supplier companies that are clients of the retail company

# Dispatcher's scope
* Managing items stored in a warehouse
* Registering items coming in a warehouse
* Register a write-off act for the items lost or damaged in the warehouse

# Warehouse Manager's scope
* Dispatching items out of a warehouse
* Distribute items across offline shops

# Shop Manager's scope
* Accepting items to the shop
* Selling availavle in the shop items
* Register a write-off act for the items lost or damaged in the shop

# Director's scope
* Setting rental taxes for each shop
* Setting taxes on each item category being sold by for the retail company

# Additional Info
* Don't forget to enter email address and password in the properties file for correct email sending
 ```java
spring.mail.username=yourEmail
spring.mail.password=yourPassword