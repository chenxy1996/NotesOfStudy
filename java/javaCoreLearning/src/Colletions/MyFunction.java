package Colletions;

import java.util.*;
import java.util.function.Function;

public interface MyFunction<T, R> {
    R call(T t);

    default <V> Function<T, V> then(MyFunction<? super R, ? extends V> after) {
        return (T t) -> after.call(this.call(t));
    }

    default <V> Function<V, R> compose(MyFunction<? super V, ? extends T> before) {
        return (V v) -> this.call(before.call(v));
    }

    interface MyInnerFunction<T, R> {
        R apply(T... t);
    }

    static void main(String[] args) {
        Map<String, Integer> scores = new LinkedHashMap<>(16, 0.75f, true) {
          protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
              return size() > 2;
          }
        };

        scores.put("chen", 100);
        scores.put("lele", 99);
        System.out.println(scores);
        scores.get("chen");
        System.out.println(scores);
        scores.put("xiaobai", 98);

        System.out.println(scores);
    }
}
