package concurrency;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THEADS = 100;
    private static final File DUMMY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    // 当试图向队列中添加元素而队列已满，或是想从队列中列出元素而队列为空的时候。
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter base directory: ");
            String directory = in.nextLine();
            System.out.println("Enter Keyword: ");
            String keyword = in.nextLine();

            Runnable enumerator = () -> {
                try {
                    enumerate(new File(directory));
                    queue.put(DUMMY);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            };

            new Thread(enumerator).start();
            for (int i = 0; i < SEARCH_THEADS; i++) {

                Runnable searcher = () -> {
                    try {
                        boolean done = false;
                        while (!done) {
                            File file = queue.take();
                            if (file == DUMMY) {
                                queue.put(file);
                                done = true;
                            } else {
                                search(file, keyword);
                            }
                        }
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                };

                new Thread(searcher).start();
            }
        }
    }

    // recursively enumerates all files in a given directory and its subdirectories.
    private static void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                enumerate(file);
            } else {
                queue.put(file);
            }
        }
    }

    public static void search(File file, String keyword) throws IOException {
        try (Scanner in = new Scanner(file, "UTF-8")) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                lineNumber++;
                String line = in.next();
                if (line.contains(keyword)) {
                    System.out.printf("%s%d:%s\n", file.getPath(), lineNumber, line);
                }
            }
        }
    }
}
