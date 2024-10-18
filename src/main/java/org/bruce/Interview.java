package org.bruce;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Interview {

    private static Map<String, Long> findRepeatedWordCountOptimizedUsingStream(List<String> list) {
        return list.parallelStream().flatMap(str -> Arrays.stream(str.split("\\s"))).collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }


    private static Map<Character, Long> findRepeatedCharacterCountOptimizedUsingStream(List<String> list) {
        return list.parallelStream().flatMap(str -> str.chars().mapToObj(m -> (char) m)).filter(f -> !Character.isWhitespace(f)).collect(Collectors.groupingBy(ch -> ch, Collectors.counting()));
    }

    private static Map<String, Long> findWordRepeatedCount(List<String> list) {
        Map<String, Long> counterMap = new HashMap<>();
        list.forEach(str -> {
            final String[] arr = str.split("\\s");
            for (String word : arr) {
                if (counterMap.containsKey(word)) {
                    counterMap.put(word, counterMap.get(word) + 1);
                } else {
                    counterMap.put(word, 1L);
                }
            }
        });
        return counterMap;
    }


    public static Map<Character, Long> findCharacterCount(List<String> list) {
        Map<Character, Long> letterCounter = new HashMap<>();
        list.forEach(str -> {
            final String[] arr = str.split(" ");
            for (String word : arr) {
                for (char c : word.toCharArray()) {
                    if (letterCounter.containsKey(c)) {
                        letterCounter.put(c, letterCounter.get(c) + 1);
                    } else {
                        letterCounter.put(c, 1L);
                    }
                }
            }
        });
        return letterCounter;
    }

    public static void testWordCount(Map<String, Long> data, String word, int count) {
        if (data.containsKey(word) && data.get(word) == count) {
            log.info("Word testcase passed");
        } else {
            log.error("Word testcase failed");
        }
    }

    public static void testCharCount(Map<Character, Long> data, char c, int count) {
        if (data.containsKey(c) && data.get(c) == count) {
            log.info("Character testcase passed");
        } else {
            log.error("Character testcase failed");
        }
    }

    public static void main(String[] args) {

        final List<String> list = new ArrayList<>(Arrays.asList("Manikandan Rajalingam", "Rajalingam Mari", "Mariyai Rajalingam", "Palani Rajalingam", "Chidambaram kandasamy"));

        final Map<String, Long> wordDataMap = findWordRepeatedCount(list);
        final Map<Character, Long> charDataMap = findCharacterCount(list);

        final Map<String, Long> wordDatMapOptimized = findRepeatedWordCountOptimizedUsingStream(list);
        final Map<Character, Long> charDatMapOptimized = findRepeatedCharacterCountOptimizedUsingStream(list);

        testWordCount(wordDataMap, "Rajalingam", 4);
        testCharCount(charDataMap, 'a', 26);
        log.info("-----------------------------------------------------------------\n");
        testWordCount(wordDatMapOptimized, "Rajalingam", 4);
        testCharCount(charDatMapOptimized, 'a', 26);
    }
}


