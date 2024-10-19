package org.example.anagramchecker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnagramServiceTest {

    private AnagramService anagramService;

    @BeforeEach
    void setUp() {
        anagramService = new AnagramService();
    }

    @ParameterizedTest(name = "{3}")
    @MethodSource("provideAreAnagramTestCases")
    void testAreAnagrams(String s1, String s2, boolean expectedResult, String description) {
        boolean result = anagramService.areAnagrams(s1, s2);
        assertEquals(expectedResult, result, description);
    }

    @ParameterizedTest(name = "{3}")
    @MethodSource("provideFindAllAnagramsTestCases")
    void testFindAllAnagrams(String input, List<String[]> inputPairs, Set<String> expectedAnagrams, String description) {
        for (String[] anagramPair : inputPairs) {
            if (anagramService.areAnagrams(anagramPair[0], anagramPair[1])) {
                anagramService.storeAnagram(anagramPair[0], anagramPair[1]);
            }
        }

        Set<String> result = anagramService.findAllAnagrams(input);

        assertEquals(expectedAnagrams, result, description);
    }

    private Stream<Arguments> provideAreAnagramTestCases() {
        return Stream.of(
                of("evil", "vile", true, "Simple Anagram"),
                of("William Shakespeare", "I am a weakish speller", true, "Anagram with spaces and different cases"),
                of("12345", "54321", true, "Anagram with numbers"),
                of("test", "test", true, "Identical input"),

                of("test!", "test.", false, "Anagram with special characters"),
                of("hello", "world", false, "No anagram"),
                of("   ", "      ", false, "Only spaces and blanks"),
                of("test", "", false, "One empty"),
                of("", "", false, "Both empty"),
                of(null, "test", false, "First input null"),
                of("test", null, false, "Second input null"),
                of(null, null, false, "Both inputs null")
        );
    }

    private Stream<Arguments> provideFindAllAnagramsTestCases() {
        List<String[]> inputPairs = List.of(
                new String[]{"test", "tets"},
                new String[]{"test", "ttes"},
                new String[]{"test", "Tomate"});

        return Stream.of(
                of("test", inputPairs, Set.of("tets", "ttes"), "4 input with 3 anagrams check standard"),
                of("tets", inputPairs, Set.of("test", "ttes"), "4 input with 3 anagrams check for unrelated anagram"),
                of("tomate", inputPairs, emptySet(), "4 input with 3 anagrams check for non anagram"),
                of("apfel", inputPairs, emptySet(), "4 input with 3 anagrams check for different input"),
                of("", inputPairs, emptySet(), "4 input with 3 anagrams check empty"),
                of(null, inputPairs, emptySet(), "4 input with 3 anagrams check null")
        );
    }
}
