package closure;

public class ClosureTest {
    public static void main(String[] args) {
        // java 中是没有函数的概念的, 这里用了
        // java 库中的 Runnable 接口。
        Runnable[] functionList = new Runnable[5];

        // 给上面的数组添加 Runnable 元素
        for (int i = 0; i < 5; i++) {
            int j = i;
            functionList[i] = () -> System.out.println(j);
        }

        // 依次执行数组中的 Runnable 元素
        for (Runnable elem : functionList) {
            elem.run();
        }
    }
}
