package concurrency;

/*
use DLC algorithm:
 */
public class StudentSingletonHolder {
    class Student {
        private String name;
        private Student(String name) {
            this.name = name;
        }
    }

    private volatile Student singleton;

    private StudentSingletonHolder() {
        singleton = new Student("chen");
    }

    public Student getSingleton() {
        if (singleton == null) {
            synchronized (this) {
                if (singleton == null) {
                    singleton = new Student("chen");
                }
            }
        }
        return singleton;
    }
}