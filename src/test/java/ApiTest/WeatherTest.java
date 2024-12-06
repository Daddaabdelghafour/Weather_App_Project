package ApiTest;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import Services.Apis.WeatherApi;
import org.junit.jupiter.api.Test;

public class WeatherTest {

    @Test
    void testGetCurrentWeatherByCityNotNull() {
        WeatherApi weatherApi = new WeatherApi();
        JsonNode currentWeatherCity = weatherApi.getCurrentWeatherByCity("Marrakech");
        assertNotNull(currentWeatherCity, "La réponse de la météo actuelle par ville ne doit pas être nulle.");
        System.out.println("Current Weather (City): " + currentWeatherCity);
    }

    @Test
    void testGetWeatherForecastByCityNotNull() {
        WeatherApi weatherApi = new WeatherApi();
        JsonNode forecastWeatherCity = weatherApi.getWeatherForecastByCity("Marrakech");
        assertNotNull(forecastWeatherCity, "La réponse de la prévision météo par ville ne doit pas être nulle.");
        System.out.println("Weather Forecast (City): " + forecastWeatherCity);
    }

    @Test
    void testGetCurrentWeatherByCoordinatesNotNull() {
        WeatherApi weatherApi = new WeatherApi();
        JsonNode currentWeatherCoords = weatherApi.getCurrentWeatherByCoordinates(31.63, -8.0);
        assertNotNull(currentWeatherCoords, "La réponse de la météo actuelle par coordonnées ne doit pas être nulle.");
        System.out.println("Current Weather (Coords): " + currentWeatherCoords);
    }

    @Test
    void testGetWeatherForecastByCoordinatesNotNull() {
        WeatherApi weatherApi = new WeatherApi();
        JsonNode forecastWeatherCoords = weatherApi.getWeatherForecastByCoordinates(31.63, -8.0);
        assertNotNull(forecastWeatherCoords, "La réponse de la prévision météo par coordonnées ne doit pas être nulle.");
        System.out.println("Weather Forecast (Coords): " + forecastWeatherCoords);
    }

    @Test
    void testFetchWeatherDataNotNull() {
        WeatherApi weatherApi = new WeatherApi();
        String endpoint = "http://api.weatherapi.com/v1/current.json?key=" + weatherApi.apiKey + "&q=Marrakech&aqi=no";
        JsonNode weatherData = weatherApi.fetchWeatherData(endpoint);
        assertNotNull(weatherData, "La réponse de la requête météo ne doit pas être nulle.");
        System.out.println("Fetched Weather Data: " + weatherData);
    }
}
