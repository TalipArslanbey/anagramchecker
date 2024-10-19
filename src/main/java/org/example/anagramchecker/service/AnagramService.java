package org.example.anagramchecker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AnagramService {
    private final Map<String, Set<String>> anagramMap = new HashMap<>();

    public boolean areAnagrams(String s1, String s2) {

        if (s1 == null || s2 == null || s1.isBlank() || s2.isBlank()) {
            return false;
        }

        String cleanedS1 = removeBlanksAndLowerCase(s1);
        String cleanedS2 = removeBlanksAndLowerCase(s2);

        if (cleanedS1.length() != cleanedS2.length()) {
            return false;
        }

        final Map<Character, Integer> charCountMap = new HashMap<>();

        for (char c : cleanedS1.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        for (char c : cleanedS2.toCharArray()) {
            if (!charCountMap.containsKey(c)) {
                return false;
            }
            charCountMap.put(c, charCountMap.get(c) - 1);
            if (charCountMap.get(c) == 0) {
                charCountMap.remove(c);
            }
        }

        return charCountMap.isEmpty();
    }

    public Set<String> findAllAnagrams(String s) {
        if (s == null || s.isBlank()) {
            return new HashSet<>();
        }
        String key = sortString(removeBlanksAndLowerCase(s));
        Set<String> anagrams = anagramMap.getOrDefault(key, new HashSet<>());

        anagrams = new HashSet<>(anagrams);
        anagrams.remove(s);

        return anagrams;
    }

    public void storeAnagram(String s1, String s2) {
        if (s1 == null || s2 == null || s1.isBlank() || s2.isBlank()) {
            log.warn("Input can not be empty or null");
            return;
        }
        String key = sortString(removeBlanksAndLowerCase(s1));
        anagramMap.putIfAbsent(key, new HashSet<>());
        anagramMap.get(key).add(s1);
        anagramMap.get(key).add(s2);
    }

    private String removeBlanksAndLowerCase(String s) {
        return s.replaceAll("\\s", "").toLowerCase();
    }

    private String sortString(String input) {
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

}
