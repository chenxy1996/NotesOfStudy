package interfaceTest;

public interface Interface {
//    default String getName() {return "chenxaingyu";}

    default double add(double... args) {
        double sum = 0;
        for (double eachNum : args) {
            sum += eachNum;
        }
        return sum;
    }
}
