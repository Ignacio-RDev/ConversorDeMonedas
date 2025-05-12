package conversormonedas;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class ConversorMonedas {

    private static ArrayList<String> historialDeConversiones = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: por favor ingresa un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    realizarConversion(scanner);
                    break;
                case 2:
                    mostrarHistorial();
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida, por favor elige nuevamente.");
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println("\n--- Menú de Conversión de Monedas ---");
        System.out.println("\n1. Realizar una conversión");
        System.out.println("2. Ver historial de conversiones");
        System.out.println("3. Salir");
        System.out.print("\nElige una opción del menú: ");
    }

    public static void realizarConversion(Scanner scanner) {
        try {
            System.out.println("\nSelecciona la moneda de origen (por ejemplo: USD, EUR, CLP): ");
            String monedaOrigen = scanner.nextLine().toUpperCase();

            if (!esMonedaValida(monedaOrigen)) {
                throw new MonedaInvalidaException("La moneda de origen '" + monedaOrigen + "' no es válida.");
            }

            System.out.println("Selecciona la moneda de destino (por ejemplo: USD, EUR, CLP):");
            String monedaDestino = scanner.nextLine().toUpperCase();

            if (!esMonedaValida(monedaDestino)) {
                throw new MonedaInvalidaException("\nLa moneda de destino '" + monedaDestino + "' no es válida.");
            }

            System.out.println("Ingresa la cantidad a convertir:");
            double cantidad;
            try {
                cantidad = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                throw new CantidadInvalidaException("\nLa cantidad ingresada no es válida.");
            }

            double tasaDeCambio = obtenerTasaDeCambio(monedaOrigen, monedaDestino);

            if (tasaDeCambio != -1) {
                double resultado = cantidad * tasaDeCambio;

                MonedaInfo origenInfo = obtenerInformacionMoneda(monedaOrigen);
                MonedaInfo destinoInfo = obtenerInformacionMoneda(monedaDestino);

                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                nf.setMaximumFractionDigits(0);
                nf.setGroupingUsed(true);

                String cantidadFormateada = origenInfo.simbolo + nf.format(cantidad);
                String resultadoFormateado = destinoInfo.simbolo + nf.format(resultado);

                System.out.println("\n" + cantidadFormateada + " (" + origenInfo.nombre + ") equivale a "
                        + resultadoFormateado + " (" + destinoInfo.nombre + ")");

                historialDeConversiones.add(cantidadFormateada + " (" + origenInfo.nombre + ") = "
                        + resultadoFormateado + " (" + destinoInfo.nombre + ")");
            } else {
                throw new TasaDeCambioException("No se pudo obtener la tasa de cambio para esa conversión.");
            }

        } catch (MonedaInvalidaException | CantidadInvalidaException | TasaDeCambioException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado.");
            e.printStackTrace();
        }
    }

    public static boolean esMonedaValida(String moneda) {
        String[] monedasValidas = {"USD", "EUR", "CLP", "GBP", "JPY", "AUD"};
        for (String m : monedasValidas) {
            if (m.equals(moneda)) return true;
        }
        return false;
    }

    public static double obtenerTasaDeCambio(String monedaOrigen, String monedaDestino) throws TasaDeCambioException {
        try {
            String apiKey = "1cb8b4ba2039a9e89ac4e2a2"; // Tu clave de API
            String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaOrigen;

            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject objetoJson = new JSONObject(response.toString());
            JSONObject tasas = objetoJson.getJSONObject("conversion_rates");

            if (tasas.has(monedaDestino)) {
                return tasas.getDouble(monedaDestino);
            } else {
                throw new TasaDeCambioException("Moneda de destino no disponible.");
            }

        } catch (Exception e) {
            throw new TasaDeCambioException("Error al obtener la tasa de cambio: " + e.getMessage());
        }
    }

    public static void mostrarHistorial() {
        if (historialDeConversiones.isEmpty()) {
            System.out.println("\nNo has realizado ninguna conversión aún.");
        } else {
            System.out.println("\n--- Historial de Conversiones ---");
            for (String conversion : historialDeConversiones) {
                System.out.println(conversion);
            }
        }
    }

    static class MonedaInfo {
        String simbolo;
        String nombre;

        MonedaInfo(String simbolo, String nombre) {
            this.simbolo = simbolo;
            this.nombre = nombre;
        }
    }

    public static MonedaInfo obtenerInformacionMoneda(String codigo) {
        return switch (codigo) {
            case "USD" -> new MonedaInfo("$", "Dólares Estadounidenses");
            case "EUR" -> new MonedaInfo("€", "Euros");
            case "CLP" -> new MonedaInfo("$", "Pesos Chilenos");
            case "GBP" -> new MonedaInfo("£", "Libras Esterlinas");
            case "JPY" -> new MonedaInfo("¥", "Yenes Japoneses");
            case "AUD" -> new MonedaInfo("A$", "Dólares Australianos");
            default -> new MonedaInfo("", "Moneda Desconocida");
        };
    }

    // Excepciones personalizadas
    public static class MonedaInvalidaException extends Exception {
        public MonedaInvalidaException(String message) {
            super(message);
        }
    }

    public static class CantidadInvalidaException extends Exception {
        public CantidadInvalidaException(String message) {
            super(message);
        }
    }

    public static class TasaDeCambioException extends Exception {
        public TasaDeCambioException(String message) {
            super(message);
        }
    }
}
