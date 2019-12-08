package javaConcurrencyInPractice;

import javaConcurrencyInPractice.GrandFather;

public class Father extends GrandFather {
    String name = "father";
    @Override
    void thinking() {
        System.out.println("I'm father");
    }
}
