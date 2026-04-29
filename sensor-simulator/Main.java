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
        for (int i = 0; i < 2; i++) {
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
        try {
            
            String correlationId = UUID.randomUUID().toString();

            URL url = new java.net.URI("http://localhost:8081/ingestion/sensors").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Correlation-ID", correlationId); // ✅ Header correto
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            long timestamp = System.currentTimeMillis();

            String json = String.format(
                Locale.US,
                "{\"sensorId\":\"%s\",\"temperature\":%.2f,\"timestamp\":%d}",
                sensorId,
                temperature,
                timestamp
            );

            // 📤 Envio do payload
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            int response = conn.getResponseCode();

            InputStream is = (response >= 200 && response < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            String resp;
            try (Scanner sc = new Scanner(is).useDelimiter("\\A")) {
                resp = sc.hasNext() ? sc.next() : "";
            }

            System.out.println(
                "[" + sensorId + "] " +
                "[correlationId=" + correlationId + "] " +
                "HTTP: " + response + " | Resp: " + resp
            );

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Erro ao enviar: " + e.getMessage());
        }
    }
}