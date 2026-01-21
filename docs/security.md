## **Security documentation**

**1. Authentication**

Authentication method: form login.

**2. Authorization**

Access is controlled using role-based authorization. Authorization rules are 
enforced at the controller and service layer (where applicable).

Roles:
USER: allowed to perform standard operations like own account management and trading.

ADMIN: reserved for administrative privileges.

**3. Design decisions**

JWT was intentionally omitted for now in order to learn 
simple authentication and then build on top of it. Later it can be extended to
token-based authentication.

