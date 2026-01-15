# Reflective Data Engine

A generic, annotation-driven framework for parsing flat files into Java POJOs and validating them. Designed for the FinTechCorp legacy migration project.

## Features
* **Generic Parsing**: Converts any delimited text file to Java Objects.
* **Validation**: Supports `@NotNull`, `@Range`, and `@Regex` via Strategy Pattern.
* Built using standard JDK 21 (Reflection, NIO, JUnit 5).
* Includes Unit Tests with >65% code coverage.

How to Build
This is a standard Maven project.

1. Ensure you have JDK 21 installed.
2. Run the following command in the project root:
   ```bash
   mvn clean install
   
## Formats & Samples

  1.Transactions
   * 1001|500.50|2025-01-01
   * 1002|-10.00|2025-01-02
  
  2.Customers
   * John Doe,john@email.com,30
   * Jane Smith,jane@work.org,25
  
  3.Security Logs
   * 192.168.0.1;HIGH
   * 10.0.0.5;CRITICAL
