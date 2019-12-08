package javaConcurrencyInPractice.syncCacheTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    interface IHello {
        void sayHello();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler {
        Object originalObj;

        <T> T bind(T originalObj) {
            this.originalObj = originalObj;
            return (T) Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),
                    originalObj.getClass().getInterfaces(), this);
        }

        public void sayName() {
            System.out.println("lele, hello!");
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            this.sayName();
            System.out.println("welcome.");
            return method.invoke(originalObj, args);
        }
    }

    public static void main(String[] args) {
        DynamicProxy proxy = new DynamicProxy();
        IHello hello = proxy.bind(new Hello());
        hello.sayHello();
        System.out.println(hello);
    }
}
