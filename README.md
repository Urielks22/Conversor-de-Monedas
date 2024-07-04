# Conversor-de-Monedas
Convertidor de Divisas en tiempo real

# Aplicación de Conversión de Monedas

Esta aplicación de conversión de monedas permite convertir cantidades entre MXN (Peso Mexicano), USD (Dólar Estadounidense) y EUR (Euro). Utiliza la API de ExchangeRate-API para obtener las tasas de cambio y guarda un registro de todas las conversiones realizadas, incluyendo información sobre las monedas y el momento de la conversión, en un archivo JSON.

## Funcionalidades

- **Consultar tasas de cambio**: La aplicación consulta las tasas de cambio actuales desde la API de ExchangeRate-API.
- **Convertir monedas**: Permite convertir cantidades entre MXN, USD y EUR.
- **Registro de conversiones**: Guarda un registro de cada conversión realizada, incluyendo la fecha y hora, las monedas y la cantidad convertida, en un archivo JSON (`conversiones.json`).
- **Interfaz de usuario interactiva**: Ofrece una interfaz de línea de comandos para que el usuario seleccione la moneda y la cantidad a convertir.

## Requisitos

- **Java 11** o superior
- **Librería Gson** para la manipulación de JSON

Ejemplo de uso
Al ejecutar la aplicación, se te pedirá que selecciones la moneda de origen y la cantidad a convertir. Por ejemplo:

¿Qué moneda deseas convertir?
Opción 1: MXN
Opción 2: USD
Opción 3: EUR
Escriba 'salir' para terminar el programa.
Seleccione una opción (1, 2 o 3): 1
Ingrese la cantidad en MXN a convertir: 100
Conversiones desde 100.00 MXN:
4.84 USD
4.36 EUR

Al finalizar, las conversiones se guardarán en un archivo conversiones.json con el siguiente formato:

[
  {
    "fecha": "2024-07-04 15:30:00",
    "monedaOrigen": "MXN",
    "cantidad": 100.0,
    "resultados": {
      "USD": "4.84 USD",
      "EUR": "4.36 EUR"
    }
  }
]

Estructura del proyecto
Convertir.java: Clase que maneja la lógica de consulta de tasas de cambio y conversión de monedas.
Main.java: Clase principal que proporciona la interfaz de usuario y maneja la lógica de interacción y registro de conversiones.
conversiones.json: Archivo generado que contiene el registro de todas las conversiones realizadas.
Notas
La aplicación usa java.time para obtener y formatear la fecha y hora de cada conversión.
Las tasas de cambio se obtienen en tiempo real desde la API de ExchangeRate-API.
