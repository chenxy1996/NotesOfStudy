package concurrency;

/*
use DLC algorithm:
 */
public class StudentSingletonHolder {
    private volatile static StudentSingletonHolder instance;
    public int a;

    private StudentSingletonHolder() {
        a = 3;
    }

    public static StudentSingletonHolder getInstance() {
        if (instance == null) {
            synchronized (StudentSingletonHolder.class) {
                if (instance == null) {
                    instance = new StudentSingletonHolder();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {

    }
}