import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.Locale;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        Thread sensor1 = new Thread(new Sensor("sensor-1"));
        Thread sensor2 = new Thread(new Sensor("sensor-2"));
        Thread sensor3 = new Thread(new Sensor("sensor-3"));

        sensor1.start();
        sensor2.start();
        sensor3.start();
    }
}

class Sensor implements Runnable {

    private final String sensorId;
    private final Random random = new Random();

    public Sensor(String sensorId) {
        this.sensorId = sensorId;
    }

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            try {
                double temperature = 20 + (40 - 20) * random.nextDouble();

                System.out.println("[" + sensorId + "] Gerado: " + temperature);

                sendData(temperature);

                Thread.sleep(10000);

            } catch (Exception e) {
                System.out.println("Erro no sensor " + sensorId + ": " + e.getMessage());
            }
        }
    }

    private void sendData(double temperature) {
        HttpURLConnection conn = null;

        try {
            String correlationId = UUID.randomUUID().toString();

            // ✅ URL correta via variável de ambiente (Docker)
            String baseUrl = System.getenv().getOrDefault(
                    "API_URL",
                    "http://api-gateway:8081"
            );

            URL url = new URL(baseUrl + "/ingestion/sensors");

            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            conn.setRequestProperty("X-Correlation-ID", correlationId);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            long timestamp = System.currentTimeMillis();

            String json = String.format(
                    Locale.US,
                    "{\"sensorId\":\"%s\",\"temperature\":%.2f,\"timestamp\":%d}",
                    sensorId,
                    temperature,
                    timestamp
            );

            // 📤 envia payload
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
                os.flush();
            }

            int responseCode = conn.getResponseCode();

            InputStream is = (responseCode >= 200 && responseCode < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            String responseBody = "";
            if (is != null) {
                try (Scanner sc = new Scanner(is).useDelimiter("\\A")) {
                    responseBody = sc.hasNext() ? sc.next() : "";
                }
            }

            System.out.println(
                    "[" + sensorId + "] " +
                    "[correlationId=" + correlationId + "] " +
                    "POST " + url +
                    " | HTTP: " + responseCode +
                    " | Resp: " + responseBody
            );

        } catch (Exception e) {
            System.out.println("Erro ao enviar: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}