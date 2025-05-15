import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConvertidorMonedas {

    // Reemplaza con tu API Key real
    static final String API_KEY = "1277aaff288b19c12bbd4501";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Convertidor de Monedas ===");
            System.out.println("1) Dólar a peso argentino");
            System.out.println("2) Peso argentino a dólar");
            System.out.println("3) Dólar a real brasileño");
            System.out.println("4) Real brasileño a dólar");
            System.out.println("5) Dólar a peso colombiano");
            System.out.println("6) Peso colombiano a dólar");
            System.out.println("7) Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> convertirMoneda("USD", "ARS");
                case 2 -> convertirMoneda("ARS", "USD");
                case 3 -> convertirMoneda("USD", "BRL");
                case 4 -> convertirMoneda("BRL", "USD");
                case 5 -> convertirMoneda("USD", "COP");
                case 6 -> convertirMoneda("COP", "USD");
                case 7 -> System.out.println("¡Gracias por usar el convertidor!");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 7);

        scanner.close();
    }

    public static void convertirMoneda(String desde, String hacia) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a convertir: ");
        double cantidad = scanner.nextDouble();

        try {
            String endpoint = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", API_KEY, desde, hacia);
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder resultado = new StringBuilder();
            String linea;

            while ((linea = reader.readLine()) != null) {
                resultado.append(linea);
            }
            reader.close();

            JsonObject json = JsonParser.parseString(resultado.toString()).getAsJsonObject();
            double tasa = json.get("conversion_rate").getAsDouble();
            double resultadoFinal = tasa * cantidad;

            System.out.printf("Tasa actual: %.4f\n", tasa);
            System.out.printf("Resultado: %.2f %s = %.2f %s\n", cantidad, desde, resultadoFinal, hacia);

        } catch (Exception e) {
            System.out.println("Error al conectar con la API: " + e.getMessage());
        }
    }
}
