package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Comparator;

public class ProxyTest1 {
    static class NumberClass implements I{
        private int number;

        public NumberClass(int x) {
            number = x;
        }

        @Override
        public int getNumber() {
            return number;
        }
    }

    public static void main(String[] args) {
        I proxy = (I) Proxy.newProxyInstance(ProxyTest1.class.getClassLoader(), new Class[]{I.class}, new ProxyHandler(new NumberClass(5)));
//        System.out.println(proxy.getNumber());
        System.out.println(proxy.getNumber());
    }
}

class ProxyHandler implements InvocationHandler, I {
    private Object target;

    public ProxyHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print(this.target);
        System.out.println(", method = " + method.getName() + ", args = " + Arrays.deepToString(args));
        return method.invoke(this.target, args);
    }

    @Override
    public int getNumber() {
        return (int) this.target;
    }
}

