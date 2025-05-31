Voici le fichier au format `.md` prêt à être utilisé :

📄 [Télécharger le fichier en Markdown](sandbox:/mnt/data/banking-backend-documentation.md)

Et voici un aperçu du contenu que j’ai mis dans le fichier :

---

````markdown
# Banking Backend System - Technical Documentation

## Architecture Overview

**Three-layer architecture:**

1. **Presentation Layer**: REST Controllers (`web` package)  
2. **Business Layer**: Services (`services` package)  
3. **Data Access Layer**: Repositories + JPA Entities

---

## Core Components Explanation

### Domain Model

```java
@Entity
public abstract class BankAccount {
    @Id private String id;
    private double balance;
    
    @ManyToOne private Customer customer;
    
    @OneToMany(mappedBy="bankAccount") 
    private List<AccountOperation> operations;
}
````

* Base class for account inheritance (**SINGLE\_TABLE** strategy)
* **Relationships**:

  * `ManyToOne` with `Customer`
  * `OneToMany` with `AccountOperation`

---

### DTO Design Pattern

```java
@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
```

**Used for:**

* Decoupling API from domain model
* Preventing lazy loading issues
* Custom data representation

---

### Security Implementation

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
            .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
```

* JWT-based stateless authentication
* OAuth2 resource server configuration
* Role-based authorization with `@PreAuthorize`

---

## Key Technical Decisions

### Transaction Management

```java
@Transactional
@Service
public class BankAccountServiceImpl {
    public void transfer(String sourceId, String targetId, double amount) {
        debit(sourceId, amount);
        credit(targetId, amount);
    }
}
```

* `@Transactional` ensures **ACID** properties
* Spring's declarative transaction management

---

### Exception Handling

**Custom exceptions hierarchy:**

* `BankAccountNotFoundException`
* `BalanceNotEnoughException`
* `CustomerNotFoundException`

---

### API Pagination

```java
@GetMapping("/accounts/{id}/pageoperations")
public AccountHistoryDTO getHistory(
    @PathVariable String id,
    @RequestParam int page,
    @RequestParam int size) {
    
    Page<AccountOperation> pageOps = operationRepo.findByAccountId(
        id, PageRequest.of(page, size));
    
    return mapToDTO(pageOps);
}
```

* Spring Data pagination
* DTO contains:

  * Current page
  * Total pages
  * Page content

---

## Database Schema

### Tables Structure

* `bank_account` (with `TYPE` discriminator)
* `customer`
* `account_operation`
* JWT token storage (in-memory)

### Entity Relationships

**Diagram**

```text
Code
![image](https://github.com/user-attachments/assets/bfa280d4-cdbc-4bc0-b4e7-933eec5f8176)
```

---

## Development Guide

### Building the Project

```bash
mvn clean package -DskipTests
```

---

### Testing Strategy

* **Unit Tests**:

  * Service layer mocking
  * Controller tests with `@WebMvcTest`
* **Integration Tests**:

  * `@DataJpaTest` for repositories
  * `@SpringBootTest` for full context

---

## Code Style

* Lombok for boilerplate reduction
* Google Java Format
* 4 spaces indentation

---

## Deployment Considerations

### Configuration

Environment-specific properties:

```properties
# application-prod.properties
spring.datasource.url=${DB_URL}
spring.jpa.hibernate.ddl-auto=validate
```

---

### Monitoring

**Recommended endpoints:**

* `/actuator/health`
* `/actuator/metrics`

---

## Troubleshooting Guide

### Common Issues

**LazyInitializationException**

* **Solution**: Use DTOs instead of entities in responses

**JWT Validation Failures**

* **Verify**: Secret key matching, token expiration

**Transaction Rollbacks**

* **Check**: Exception propagation behavior

---

## Cette version technique inclut :

1. Une explication approfondie de l'architecture
2. Des extraits de code commentés
3. Les décisions techniques clés
4. Le schéma de base de données
5. Des guides pratiques pour les développeurs
6. Des considérations de déploiement
7. Un guide de dépannage

**Elle conviendra mieux à :**

* Une documentation technique interne
* Un passage de relai entre développeurs
* Une revue de code approfondie

```

---

```
