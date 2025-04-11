# Ecommerce-Case-study--Hexaware

# Ecommerce Application 

A menu-driven console-based Ecommerce application built in Java using JDBC and MySQL, focusing on real-world business logic, SQL operations, custom exception handling, and unit testing.


## 🔧 Utilities

### `PropertyUtil`
Reads DB properties from `.properties` file (host, dbname, username, password, port) and returns a connection string.

### `DBConnection`
Uses the connection string to return a static SQL `ConnectionHelper` object.

---

## ⚠️ Custom Exceptions

Located in the `myexceptions` package:
- `CustomerNotFoundException`
- `ProductNotFoundException`
- `OrderNotFoundException`


## 🧪 Unit Testing (JUnit)

Essential test cases to validate:
- ✅ Product creation
- ✅ Adding product to cart
- ✅ Order placement





