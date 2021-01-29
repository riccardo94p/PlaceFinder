package sender;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConcurrentRequestsTask implements Runnable {
    int id;

    public ConcurrentRequestsTask(int i) {
        id = i;
    }

    public void run() {
        try {
            String url = "http://localhost:8080/testconcurrentreserve?username=aaa" + id +
                    "&slotId=0&room=f7&dateStr=2021-01-24";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
