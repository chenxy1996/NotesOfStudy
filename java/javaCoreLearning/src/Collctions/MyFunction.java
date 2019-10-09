package Collctions;

import java.util.function.Function;

public interface MyFunction<T, R> {
    R call(T t);

    default <V> Function<T, V> then(MyFunction<? super R, ? extends V> after) {
        return (T t) -> after.call(this.call(t));
    }

    default <V> Function<V, R> compose(MyFunction<? super V, ? extends T> before) {
        return (V v) -> this.call(before.call(v));
    }
}
