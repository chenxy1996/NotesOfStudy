package effectiveJava.selfTypeIdiom;

import java.util.List;

public class SpecialNode extends Node<SpecialNode> {
    public static void main(String[] args) {
        SpecialNode specialNode = new SpecialNode();
        List<SpecialNode> list = specialNode.children;
    }
}
