package com.security.lab;

import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class UserInput {
    public static void main(String[] args) {
        // Use explicit UTF-8 encoding
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        int age = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter your age (1-120): ");
                String input = scanner.nextLine().trim();
                
                // Validate input format using regex before parsing
                if (!input.matches("\\d{1,3}")) {
                    System.out.println("Error: Please enter a valid number (1-120)");
                    continue;
                }
                
                // Convert to integer and validate range
                age = Integer.parseInt(input);
                if (age < 1 || age > 120) {
                    System.out.println("Error: Age must be between 1 and 120");
                    continue;
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        System.out.println("You are " + age + " years old.");
        scanner.close();
    }
} 