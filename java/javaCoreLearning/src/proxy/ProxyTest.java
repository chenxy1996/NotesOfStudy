package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;
import myReflection.MemberGetter;

public class ProxyTest {
    public static void main(String[] args) {
        Comparable[] elements = new Comparable[100];

        for (int i = 0; i < elements.length; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            Comparable proxy = (Comparable) Proxy.newProxyInstance(Comparable.class.getClassLoader(), new Class[] {Comparable.class}, handler);
            elements[i] = proxy;
        }

        int key = new Random().nextInt(elements.length) + 1;
        int result = Arrays.binarySearch(elements, key);
        if (result > 0) {
            System.out.println(elements[result]);
        }
    }
}

class TraceHandler implements InvocationHandler {
    private Object target;

    public TraceHandler(Object obj) {
        this.target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print(target);
        System.out.println('.' + method.getName());
        return method.invoke(target, args);
    }
}
