package com.fintechcorp;

import com.fintechcorp.engine.GenericFileParser;
import com.fintechcorp.validation.Validator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting\n");

        GenericFileParser parser = new GenericFileParser();
        Validator validator = new Validator();

        processFile("transactions.txt", Transaction.class, parser, validator);

        processFile("customers.csv", Customer.class, parser, validator);

        processFile("security.log", SecurityAudit.class, parser, validator);
    }

    private static <T> void processFile(String filePath, Class<T> clazz, GenericFileParser parser, Validator validator) {
        File file = new File(filePath);

        System.out.println("Checking for file: " + filePath + "...");

        if (!file.exists()) {
            System.out.println(" SKIP | File not found.\n");
            return;
        }

        try {
            System.out.println(" START | Parsing " + clazz.getSimpleName() + "...");

            List<T> data = parser.parse(filePath, clazz);

            Map<T, Set<String>> errors = validator.validate(data);

            for (T item : data) {
                if (errors.containsKey(item)) {
                    System.out.println("   INVALID | " + item);
                    for (String err : errors.get(item)) {
                        System.out.println("       ERROR: " + err);
                    }
                } else {
                    System.out.println("   VALID |" + item);
                }
            }
            System.out.println(" DONE | Finished processing " + filePath + "\n");

        } catch (Exception e) {
            System.err.println(" ERROR | Failed to process " + filePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}