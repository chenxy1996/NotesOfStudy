package effectiveJava.selfTypeIdiom;

import java.util.ArrayList;
import java.util.List;

public class SpecialNode extends Node<SpecialNode> {
    static class TopClass {

    }

    static class MediumClass extends TopClass {

    }

    static class BottomClass extends MediumClass {

    }

    public static void main(String[] args) {
        List<MediumClass> list = new ArrayList<>();
        list.add(new MediumClass());
        List<?> aList = list;
        List newList = list;
        newList = aList;
    }
}
