package methodReference;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Test {
    private String attr;
    private static String classAttr = "Test Class";

    public Test(String arg) {
        attr = arg;
    }

    public String getAttr(String description) {
        return "instance " + attr + ": " + description;
    }

    public static String getClassAttr(String description) {
        return "static " + classAttr + ": " + description;
    }

    public static void main(String[] args) {
        Test aTest = new Test("chen");
        Test bTest = new Test("lele");

        // 相当于 aTestMethodRef = (String s) -> aTest.getAttr(s)
        Function<String, String> aTestMethodRef = aTest::getAttr;
        // 相当于 bTestMethodRef = (String s) => bTest.getAttr(s)
        Function<String, String> bTestMethodRef = bTest::getAttr;

        // instance chen: nihao
        System.out.println(aTestMethodRef.apply("nihao"));
        // instance lele: nihao
        System.out.println(bTestMethodRef.apply("nihao"));

        // -----------------------------------------------------------
        Function<String, String> staticMethodRef = Test::getClassAttr;

        System.out.println(staticMethodRef.apply("nihao"));

        // -----------------------------------------------------------
        // 相当于 instanceMethodRef = (Test obj, String s) -> obj.getAttr(s)
        BiFunction<Test, String, String> instanceMethodRef = Test::getAttr;

        // instance chen: nihao
        System.out.println(instanceMethodRef.apply(aTest, "nihao"));
        // instance lele: nihao
        System.out.println(instanceMethodRef.apply(bTest, "nihao"));
    }
}
