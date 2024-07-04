import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ConsultaApi {

    private static final String API_KEY = "8828328d602cd6e130c4d562";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        String endpoint = "latest/USD";  // Puedes cambiar la moneda base según tus necesidades
        String url = BASE_URL + API_KEY + "/" + endpoint;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                // Parsear la respuesta JSON usando Gson
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

                // Filtrar y mostrar solo las tasas de cambio para MXN, USD y EUR
                Map<String, Double> rates = new HashMap<>();
                rates.put("MXN", jsonObject.getAsJsonObject("conversion_rates").get("MXN").getAsDouble());
                rates.put("USD", jsonObject.getAsJsonObject("conversion_rates").get("USD").getAsDouble());
                rates.put("EUR", jsonObject.getAsJsonObject("conversion_rates").get("EUR").getAsDouble());

                System.out.println("Tasas de cambio:");
                for (Map.Entry<String, Double> entry : rates.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
