package Collctions;

import interfaceTest.Interface;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Dog implements Comparable<Dog>{
    private String name;
    private int age;

    public Dog(String name, int age) {
        this.age = age;
        this.name = name;
    }

    @Override
    public int compareTo(@NotNull Dog o) {
        return Integer.compare(age, o.age);
    }

    @Override
    public String toString() {
        return "[" + name + ", " + age + "]";
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) throws IOException {
        TreeSet<Dog> dogSet = new TreeSet<>();
        dogSet.add(new Dog("lele", 12));
        dogSet.add(new Dog("xiaobao", 3));
        dogSet.add(new Dog("ahuang", 19));
        System.out.println(dogSet);

        dogSet = new TreeSet<>(Comparator.comparing(Dog::getName));
        dogSet.add(new Dog("lele", 12));
        dogSet.add(new Dog("xiaobao", 3));
        dogSet.add(new Dog("ahuang", 19));
        System.out.println(dogSet);
    }
}
