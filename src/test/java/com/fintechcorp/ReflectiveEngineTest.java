package com.fintechcorp;

import com.fintechcorp.engine.GenericFileParser;
import com.fintechcorp.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ReflectiveEngineTest {

    @Test
    void testTransactions() throws Exception {
        String content = "100|50.00|2025-01-01\n200|100.00|2025-01-02";
        Path tempFile = Files.createTempFile("test_trans", ".txt");
        Files.writeString(tempFile, content);

        GenericFileParser parser = new GenericFileParser();
        List<Transaction> result = parser.parse(tempFile.toString(), Transaction.class);

        Assertions.assertEquals(2, result.size(), "Трябва да има 2 записа");

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testValidation() throws Exception {
        String content = "1|-50.00|2025-01-01";
        Path tempFile = Files.createTempFile("test_invalid", ".txt");
        Files.writeString(tempFile, content);

        GenericFileParser parser = new GenericFileParser();
        List<Transaction> result = parser.parse(tempFile.toString(), Transaction.class);

        Validator validator = new Validator();
        Map<Transaction, Set<String>> errors = validator.validate(result);

        Assertions.assertFalse(errors.isEmpty());
        Assertions.assertTrue(errors.containsKey(result.getFirst()));

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testParserThrowsException() throws IOException {
        String content = "1|NotANumber|2025-01-01";
        Path tempFile = Files.createTempFile("test_broken", ".txt");
        Files.writeString(tempFile, content);

        GenericFileParser parser = new GenericFileParser();

        Assertions.assertThrows(RuntimeException.class, () -> {
            parser.parse(tempFile.toString(), Transaction.class);
        });

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testCustomer() throws Exception {
        String content = "John Doe,john@example.com,30\nJane Smith,jane@test.org,25";
        Path tempFile = Files.createTempFile("test_customers", ".csv");
        Files.writeString(tempFile, content);

        GenericFileParser parser = new GenericFileParser();
        List<Customer> customers = parser.parse(tempFile.toString(), Customer.class);

        Assertions.assertEquals(2, customers.size());

        Assertions.assertTrue(customers.getFirst().toString().contains("John Doe"));
        Assertions.assertTrue(customers.getFirst().toString().contains("30"));

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testSecurity() throws Exception {
        String content = "192.168.0.1;HIGH\n10.10.10.5;CRITICAL";
        Path tempFile = Files.createTempFile("test_security", ".log");
        Files.writeString(tempFile, content);

        GenericFileParser parser = new GenericFileParser();
        List<SecurityAudit> audits = parser.parse(tempFile.toString(), SecurityAudit.class);

        Assertions.assertEquals(2, audits.size());

        Assertions.assertTrue(audits.getFirst().toString().contains("HIGH"));
        Assertions.assertTrue(audits.get(1).toString().contains("CRITICAL"));

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testInvalidEmail() throws Exception {
        String content = "Bad User,invalid-email-format,25";
        Path tempFile = Files.createTempFile("test_bad_cust", ".csv");
        Files.writeString(tempFile, content);

        GenericFileParser parser = new GenericFileParser();
        List<Customer> customers = parser.parse(tempFile.toString(), Customer.class);

        Validator validator = new Validator();
        Map<Customer, Set<String>> errors = validator.validate(customers);

        Assertions.assertFalse(errors.isEmpty());
        Assertions.assertTrue(errors.containsKey(customers.getFirst()));

        Files.deleteIfExists(tempFile);
    }
}