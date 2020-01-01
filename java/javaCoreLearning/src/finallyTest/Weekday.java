package finallyTest;

import Colletions.WeekDay;

import java.util.EnumSet;

public enum Weekday {
    Monday, Tuesday;

    public static void main(String[] args) {
        EnumSet<WeekDay> always = EnumSet.allOf(WeekDay.class);
        System.out.println(always);
    }
}
