package Collctions;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CollTest {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> wordSet = new HashMap<>();
        Path filePath = Path.of("C:\\Users\\陈翔宇\\Desktop\\Alice in wonderland.txt");
        try (Scanner in = new Scanner(filePath)) {
            String word;
            while (in.hasNext()) {
                word = in.next();
                if (wordSet.containsKey(word)) {
                    wordSet.put(word, wordSet.get(word) + 1);
                } else {
                    wordSet.put(word, 1);
                }
            }
        }

        Set<Map.Entry<String, Integer>> wordEntrySet = wordSet.entrySet();
        List<Map.Entry<String, Integer>> wordEntryList = new ArrayList<>(wordEntrySet);
        wordEntryList.sort(Comparator.comparingInt(Map.Entry::getValue));
        for (int i = 0; i < 20; i++) {
            System.out.println(wordEntryList.get(wordEntryList.size() - 1 - i));
        }
    }
}

class Outer {
    private int num = 10;

    private class Inner implements A{
        @Override
        public int getNum() {
            return num;
        }
    }

    public Inner bridge() {
        return new Inner();
    }
}

interface A {
    public int getNum();
}
