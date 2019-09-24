package inheritance;

import java.util.Arrays;

public enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

    private String abbreviation;

    private Size(String abbreviation) {this.abbreviation = abbreviation;}
    public String getAbbreviation() {return this.abbreviation;}

    public static void main(String[] args) {
        Size s = Enum.valueOf(Size.class, "SMALL");
        System.out.println(s);
        System.out.println(Arrays.toString(Size.values()));
        System.out.println(Size.class);
    }
}

