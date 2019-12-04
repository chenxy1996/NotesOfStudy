package javaConcurrencyInPractice;

import java.util.concurrent.BlockingQueue;

public class LogService {
    private final BlockingQueue<String> loggerThread;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;
    private boolean isShutdown;
    private int reservation;

    public void start() {
        loggerThread.start();
    }


}
