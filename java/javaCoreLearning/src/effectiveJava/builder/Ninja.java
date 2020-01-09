package effectiveJava.builder;

public class Ninja extends Person {
    private final int speed;

    public static class NinjaBuilder extends Person.PersonBuilder {
        private int speed = 0;

        public NinjaBuilder withSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        @Override
        public Ninja build() {
            return new Ninja(this);
        }
    }

     protected Ninja(NinjaBuilder nb) {
        super(nb);
        this.speed = nb.speed;
    }
}
