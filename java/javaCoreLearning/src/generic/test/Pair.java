package generic.test;

public class Pair<T, U> {
    private T first;
    private U second;

    public Pair(T t, U u) {
        this.first = t;
        this.second = u;
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

    public void setFirst(T newValue) { this.first = newValue; }

    public void setSecond(U newValue) { this.second = newValue; }

    public static void main(String[] args) {
        Student s = new Student("chen", 1);
        Grade math = new Grade("Math", 150);
        Pair<? extends Person, Grade> pair = new Pair<>(s, math);
        Object s2 = pair.getFirst();
        Pair<Person, Grade> pair1 = (Pair<Person, Grade>) pair;
    }
}
