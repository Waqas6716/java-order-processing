# Order Processing Core

A dependency-free Java 17 domain module for accepting orders. It models explicit order states, validates line items, calculates money using `BigDecimal`, and prevents invalid transitions.

```bash
javac -d out $(find src -name '*.java')
java -ea -cp out com.waqas.orders.OrderServiceTest
```

The repository is deliberately framework-free so the domain code can be embedded in a Spring Boot, CLI, or messaging adapter.
