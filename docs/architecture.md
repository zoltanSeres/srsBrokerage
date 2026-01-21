# **Architecture documentation**


### **Application structure:**

The application implements a layered architecture and a clear separation of concerns.

**1. Client** 

Handles external API from Alpha Vantage, used to fetch real asset prices.
It's an external integration client.

**2. Config**

Contains application configuration, including Spring Security and WebClient setup.

**3. Controller**

Handles HTTP requests and responses. Doesn't contain any business logic.

**4. DTO**

Contains Data Transfer Objects, used for incoming requests and outgoing responses.

**5. Enums**

Contains all the enumeration types used in the application.

**6.Exceptions**

Contains custom exceptions and the global exception handler.

**7. Mapper**

Contains mapper classes which are converting DTOs and domain entities.
 
**8. Model**

Contains the domain entities, represent business concepts like users, accounts, trades,
positions and assets.

**9. Repository**

Provides persistence access using Spring Data JPA.

**10. Service**

Contains all the business logic used in cash transactions, user management,
account operations,
and trade execution.

Transactional boundaries are found here.

**Why this structure?**

To keep business logic separated from web and persistence concerns. 
This makes the application easier to maintain and test.
