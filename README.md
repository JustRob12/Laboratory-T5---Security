# Java Security Lab

This lab demonstrates common security vulnerabilities in Java applications and how to fix them using secure coding practices.

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven
- Your preferred IDE
- FindSecBugs plugin for your IDE

## Project Structure

```
src/main/java/com/security/lab/
├── UserInput.java         - Input validation example
├── DatabaseQuery.java     - SQL injection prevention
├── PasswordStorage.java   - Secure password handling
└── GreetingServlet.java  - XSS prevention
```

## Tasks Overview

1. **Input Validation**
   - Demonstrates proper input validation for numeric values
   - Includes range checking and error handling

2. **SQL Injection Prevention**
   - Shows how to use parameterized queries
   - Implements proper resource management with try-with-resources

3. **Secure Password Handling**
   - Uses char[] instead of String for passwords
   - Implements bcrypt hashing with salt
   - Demonstrates proper memory cleanup

4. **XSS Prevention**
   - Uses OWASP Java Encoder
   - Implements proper HTML encoding
   - Shows secure HTTP response handling

## Running the Lab

1. Clone the repository
2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Run FindSecBugs:
   ```bash
   mvn spotbugs:check
   ```

## Security Best Practices Demonstrated

1. Input Validation
   - Proper type checking
   - Range validation
   - Error handling

2. SQL Injection Prevention
   - Use of PreparedStatement
   - Proper resource management
   - Exception handling

3. Password Security
   - Use of char[] instead of String
   - Secure hashing with bcrypt
   - Proper memory cleanup

4. XSS Prevention
   - Output encoding
   - Content-Type headers
   - OWASP Encoder usage

## Common Vulnerabilities Addressed

- SQL Injection
- Cross-Site Scripting (XSS)
- Improper Input Validation
- Insecure Password Storage
- Resource Leaks
- Memory Leaks

## Additional Resources

- [OWASP Java Security Guidelines](https://owasp.org/www-project-proactive-controls/)
- [Java Secure Coding Guidelines](https://www.oracle.com/java/technologies/javase/seccodeguide.html)
- [FindSecBugs Documentation](https://find-sec-bugs.github.io/) 