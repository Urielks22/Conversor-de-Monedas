import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Main {

    public static void main(String[] args) {
        Convertir convertidor = new Convertir();
        Scanner scanner = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#.00");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Map<String, Object>> conversiones = new ArrayList<>();

        while (true) {
            // Mostrar opciones al usuario
            System.out.println("¿Qué moneda deseas convertir?");
            System.out.println("Opción 1: MXN");
            System.out.println("Opción 2: USD");
            System.out.println("Opción 3: EUR");
            System.out.println("Escriba 'salir' para terminar el programa.");
            System.out.print("Seleccione una opción (1, 2 o 3): ");

            // Leer la opción del usuario
            String input = scanner.next();
            if (input.equalsIgnoreCase("salir")) {
                break;
            }

            int opcion;
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Intente de nuevo.");
                continue;
            }

            String monedaOrigen = "";
            switch (opcion) {
                case 1:
                    monedaOrigen = "MXN";
                    break;
                case 2:
                    monedaOrigen = "USD";
                    break;
                case 3:
                    monedaOrigen = "EUR";
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    continue;
            }

            // Solicitar cantidad al usuario
            System.out.print("Ingrese la cantidad en " + monedaOrigen + " a convertir: ");
            double cantidad;
            try {
                cantidad = scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Cantidad no válida. Intente de nuevo.");
                scanner.next(); // limpiar el scanner
                continue;
            }

            // Obtener la fecha y hora actuales
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Realizar conversiones y almacenar los resultados
            Map<String, Object> conversion = new HashMap<>();
            conversion.put("fecha", now.format(formatter));
            conversion.put("monedaOrigen", monedaOrigen);
            conversion.put("cantidad", cantidad);
            Map<String, String> resultados = new HashMap<>();
            conversion.put("resultados", resultados);

            System.out.println("Conversiones desde " + cantidad + " " + monedaOrigen + " a " + now.format(formatter) + ":");
            if (!monedaOrigen.equals("MXN")) {
                double conversionMXN = convertidor.convertir(monedaOrigen, "MXN", cantidad);
                String resultadoMXN = df.format(conversionMXN) + " MXN";
                System.out.println(resultadoMXN);
                resultados.put("MXN", resultadoMXN);
            }
            if (!monedaOrigen.equals("USD")) {
                double conversionUSD = convertidor.convertir(monedaOrigen, "USD", cantidad);
                String resultadoUSD = df.format(conversionUSD) + " USD";
                System.out.println(resultadoUSD);
                resultados.put("USD", resultadoUSD);
            }
            if (!monedaOrigen.equals("EUR")) {
                double conversionEUR = convertidor.convertir(monedaOrigen, "EUR", cantidad);
                String resultadoEUR = df.format(conversionEUR) + " EUR";
                System.out.println(resultadoEUR);
                resultados.put("EUR", resultadoEUR);
            }

            // Agregar la conversión a la lista de conversiones
            conversiones.add(conversion);
        }

        // Guardar todas las conversiones en un archivo JSON
        try (FileWriter writer = new FileWriter("conversiones.json")) {
            gson.toJson(conversiones, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar las conversiones en el archivo JSON: " + e.getMessage());
        }

        // Cerrar scanner
        scanner.close();
    }
}
