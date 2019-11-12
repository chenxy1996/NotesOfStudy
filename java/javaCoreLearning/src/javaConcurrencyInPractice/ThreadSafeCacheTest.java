package javaConcurrencyInPractice;

public class ThreadSafeCacheTest {
    private volatile CacheHolder cache = new CacheHolder();
    public static int errorTimes = 0;

    private class CacheHolder {
        private final Integer key;
        private final Character value;

        public CacheHolder(Integer key, Character value) {
            this.key = key;
            this.value = value;
        }

        public CacheHolder() {
            this.key = null;
            this.value = null;
        }

        public Character getValue(Integer key) {
            return key == this.key ? this.value : null;
        }
    }

    public CacheHolder getCache() {
        return this.cache;
    }

    public void setCache(Integer key, Character value) {
        cache = new CacheHolder(key, value);
    }

    public static Character calc(Integer key) {
        char ret = (char) (key + 97);
        return ret;
    }

    public static void main(String[] args) {
        ThreadSafeCacheTest test = new ThreadSafeCacheTest();

        for (int i = 0; i < 10000; i++) {
            int threadNumber = i;
            Thread t = new Thread(() -> {
                while (true) {
                    int key = (int) Math.floor(Math.random() * 10);
                    Character value = test.getCache().getValue(key);
                    if (value == null) {
                        value = calc(key);
                        test.setCache(key, value);
                    }

                    boolean res = ((char) (key + 97)) == value;

                    if (!res) {
                        System.out.println("----------------------------------------------------------------");
                        errorTimes += 1;
                    }

                    System.out.println(threadNumber + " " + "key: " + key + " value: " + value + " " + res + " errorTimes: " + errorTimes);
                }
            });

            t.start();
        }
    }
}
