package lambda;

@FunctionalInterface
public interface Handler<T> {
    T process(T... args);
}

