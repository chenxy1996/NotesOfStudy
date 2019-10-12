package Collctions;

import interfaceTest.Interface;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public abstract class Dog {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public abstract String sayName();

    public static void main(String[] args) throws IOException {
        HashSet<String> wordsSet= new HashSet<>();
        Path filePath = Path.of("C:\\Users\\陈宇\\Desktop\\Alice in wonderland.txt");
        long totalTime = 0;
        try (Scanner in = new Scanner(filePath))
        {
            while (in.hasNext()) {
                String word = in.next();
                long callTime = System.currentTimeMillis();
                wordsSet.add(word);
                callTime = System.currentTimeMillis() - callTime;
                totalTime += callTime;
            }
        } catch (Exception e) {
            System.out.println(e);
            for (StackTraceElement each : e.getStackTrace()) {
                System.out.println(each);
            }
        }

        Iterator<String> iter = wordsSet.iterator();
        for (int i = 0; i <= 20 && iter.hasNext(); i++) {
            System.out.println(iter.next());
        }
        System.out.println("....");
        System.out.println(wordsSet.size() + " distinct words.\n" + totalTime + " milliseconds.");
    }
}
