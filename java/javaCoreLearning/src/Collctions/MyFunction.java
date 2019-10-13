package Collctions;

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
        Map<String, Integer> scores = new HashMap<>();
        scores.put("chen", 100);
        scores.put("lele", 99);
        scores.put("xiaobai", 98);
        scores.put("ahuang", 99);
        Set<Map.Entry<String, Integer>> scoresSet = scores.entrySet();
        ArrayList<Map.Entry<String, Integer>> scoresList = new ArrayList<>(scoresSet);
        scoresList.sort(Comparator.comparing((Map.Entry<String, Integer> a) -> a.getValue()).reversed());

        scoresList.forEach((k) -> System.out.println(k.getKey() + ": " + k.getValue()));
        scoresList.remove(2);
        scoresList.forEach((k) -> System.out.println(k.getKey() + ": " + k.getValue()));
        System.out.println(scores);
    }
}
