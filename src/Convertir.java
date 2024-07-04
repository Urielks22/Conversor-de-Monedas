import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class Convertir {

    private static final String API_KEY = "8828328d602cd6e130c4d562";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private Map<String, Double> rates;

    public Convertir() {
        this.rates = obtenerTasasDeCambio();
    }

    private Map<String, Double> obtenerTasasDeCambio() {
        String endpoint = "latest/USD";  // Puedes cambiar la moneda base según tus necesidades
        String url = BASE_URL + API_KEY + "/" + endpoint;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
                Map<String, Double> rates = new HashMap<>();
                rates.put("MXN", jsonObject.getAsJsonObject("conversion_rates").get("MXN").getAsDouble());
                rates.put("USD", jsonObject.getAsJsonObject("conversion_rates").get("USD").getAsDouble());
                rates.put("EUR", jsonObject.getAsJsonObject("conversion_rates").get("EUR").getAsDouble());
                return rates;
            } else {
                System.out.println("Error al obtener las tasas de cambio: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Excepción al obtener las tasas de cambio: " + e.getMessage());
        }
        return null;
    }

    public double convertir(String monedaOrigen, String monedaDestino, double cantidad) {
        if (rates == null || !rates.containsKey(monedaOrigen) || !rates.containsKey(monedaDestino)) {
            throw new IllegalArgumentException("Las monedas especificadas no están soportadas o no se han cargado las tasas de cambio correctamente.");
        }
        double tasaOrigen = rates.get(monedaOrigen);
        double tasaDestino = rates.get(monedaDestino);
        return cantidad / tasaOrigen * tasaDestino;
    }
}
