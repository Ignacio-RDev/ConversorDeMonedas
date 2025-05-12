# Conversor de Monedas en Java 💱

Este proyecto es una aplicación de consola desarrollada en Java que permite convertir entre diferentes monedas utilizando tasas de cambio en tiempo real. 
Además, mantiene un historial de conversiones realizadas durante la ejecución del programa.

---

## 🧠 Descripción

El **Conversor de Monedas** permite al usuario:

- Seleccionar una moneda de origen y destino (USD, EUR, CLP, GBP, JPY, AUD).
- Ingresar una cantidad a convertir.
- Obtener el resultado con la tasa de cambio actualizada.
- Ver un historial de conversiones.
- Validar entradas y manejar errores comunes de manera amigable con excepciones personalizadas.

El programa obtiene las tasas de cambio utilizando la **API pública de ExchangeRate-API**.

---

## 🔧 Tecnologías utilizadas

- **Java 8+**
- **Librería org.json** para manejar datos JSON (se recomienda usar [json.org](https://mvnrepository.com/artifact/org.json/json))
- **API REST** para tasas de cambio: [ExchangeRate-API](https://www.exchangerate-api.com)

---

## 🔑 API utilizada

Se utiliza la API de [ExchangeRate-API](https://www.exchangerate-api.com/), la cual permite acceder a tasas de cambio actualizadas.

- Endpoint: `https://v6.exchangerate-api.com/v6/YOUR_API_KEY/latest/{base_currency}`
- Método: `GET`
- Formato de respuesta: JSON
- Ejemplo de URL solicitada: https://v6.exchangerate-api.com/v6/1cb8b4ba2039a9e89ac4e2a2/latest/USD

--- 
⚠️ **Importante:** Este proyecto utiliza una API key de ejemplo incluida directamente en el código. Para uso en producción se recomienda registrar una clave propia.
---

---
## 💻 Cómo usar este conversor paso a paso

### 1. Clona el repositorio
```
git clone https://github.com/tu-usuario/conversor-monedas-java.git
cd conversor-monedas-java
```
## 2. Abre el proyecto en tu IDE favorito
Puedes usar IntelliJ IDEA, Eclipse o cualquier otro entorno de desarrollo Java.

## 3. Agrega la librería org.json
Puedes descargar el JAR manualmente desde:
https://mvnrepository.com/artifact/org.json/json

## 4. Ejecuta el programa
Puedes ejecutar la clase ConversorMonedas.java directamente desde tu IDE o desde la terminal:
```
java ConversorMonedas.java
java conversormonedas.ConversorMonedas
 ```

## 📋 Menú de opciones
Al ejecutar el programa verás el siguiente menú:

--- Menú de Conversión de Monedas ---
1. Realizar una conversión
2. Ver historial de conversiones
3. Salir
Elige una opción:

## Flujo de uso:

- Eliges la opción 1 para hacer una conversión.
- Ingresas la moneda de origen (ej: USD).
- Ingresas la moneda de destino (ej: CLP).
- Ingresas la cantidad.
- El programa consulta la API y muestra el resultado.
- Luego puedes elegir entre: realizar otra conversión, ver el historial o salir.

## ✅ Monedas disponibles

| Código | Moneda               | Símbolo |
| ------ | -------------------- | -------|
| USD    | Dólar estadounidense | $      |
| EUR    | Euro                 | €      |
| CLP    | Peso chileno         | $      |
| GBP    | Libra esterlina      | £      |
| JPY    | Yen japonés          | ¥      |
| AUD    | Dólar australiano    | A$     |

## 🧪 Manejo de errores
El programa incluye validaciones para:

- Monedas no soportadas → MonedaInvalidaException
- Cantidades no numéricas → CantidadInvalidaException
- Problemas con la API → TasaDeCambioException

## 🗂 Historial de conversiones

Cada vez que se realiza una conversión exitosa, se guarda en una lista en memoria. Puedes consultar este historial desde el menú principal o después de una conversión.

## 📌 Notas finales

Este programa es educativo y no debe utilizarse para propósitos financieros reales.
La API utilizada tiene límites de uso por día. Si se excede, puede fallar la obtención de datos.
Puedes ampliar el sistema para que guarde el historial en un archivo o base de datos.

## 🤝 Contribuciones
¡Las contribuciones son bienvenidas! Puedes proponer mejoras, agregar nuevas monedas o adaptar el programa a una interfaz gráfica si lo encuentras útil.


## 🧑 Autor
Desarrollado por Ignacio Rodríguez
Estudiante de Ingeniería en Informática - Duoc UC 🇨🇱






