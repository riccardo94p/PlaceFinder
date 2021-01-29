package sender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentRequestsExecutor {

    public static void insertString(int startIndex, int endIndex) {
        String query = "INSERT INTO `placefinder`.`User` (`username`, `password`, `role`, `covidNotification`) VALUES ";
        for (int i = startIndex; i <= endIndex; i++) {
            if (i != startIndex)
                query += ", ";
            query += "('aaa" + i + "', 'test', 'STUDENT', '0')";
        }
        query += ";";
        System.out.println(query);
    }

    public static void main(String[] args) {
        //insertString(9, 50);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 1; i < 51; i++) {
            Runnable runnableTask = new ConcurrentRequestsTask(i);
            executorService.submit(runnableTask);
        }
        executorService.shutdown();
    }
}
