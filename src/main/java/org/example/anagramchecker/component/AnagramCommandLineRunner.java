package org.example.anagramchecker.component;

import lombok.extern.slf4j.Slf4j;
import org.example.anagramchecker.service.AnagramService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.Set;

@Slf4j
@Component
public class AnagramCommandLineRunner implements CommandLineRunner {
    private final AnagramService anagramService;

    public AnagramCommandLineRunner(AnagramService anagramService) {
        this.anagramService = anagramService;
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String option;
            while (true) {
                printToUser("Choose an option:");
                printToUser("1. Check if two strings are anagrams of each other");
                printToUser("2. Out of all inputs in 1, get all anagrams from input string");
                printToUser("3. Exit");
                option = scanner.nextLine();

                switch (option) {
                    case "1":
                        checkAnagram(scanner);
                        break;
                    case "2":
                        findAllAnagrams(scanner);
                        break;
                    case "3":
                        System.exit(0);
                        break;
                    default:
                        printToUser("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void checkAnagram(Scanner scanner) {
        printToUser("Enter the first string:");
        String s1 = scanner.nextLine();
        printToUser("Enter the second string:");
        String s2 = scanner.nextLine();

        if (anagramService.areAnagrams(s1, s2)) {
            printToUser("The strings are anagrams.");
            anagramService.storeAnagram(s1, s2);
        } else {
            printToUser("The strings are no anagrams.");
        }
    }

    private void findAllAnagrams(Scanner scanner) {
        printToUser("Enter a string to find all anagrams:");
        String s = scanner.nextLine();
        Set<String> anagrams = anagramService.findAllAnagrams(s);
        printToUser(anagrams.toString());

    }

    private void printToUser(String message) {
        System.out.println(message);
    }
}