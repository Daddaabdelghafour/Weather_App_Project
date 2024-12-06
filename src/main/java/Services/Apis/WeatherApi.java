package Services.Apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherApi {

    public String apiKey = "1c69d1bd05d947c5aa8115353241811";  // API key
    public String baseUrl = "http://api.weatherapi.com/v1/current.json";  // Base URL for current weather
    
    // Get current weather by city name
    public JsonNode getCurrentWeatherByCity(String ville) {
        String endpoint = this.baseUrl + "?key=" + this.apiKey + "&q=" + ville + "&aqi=no";
        return fetchWeatherData(endpoint);
    }

    // Get weather forecast by city name
    public JsonNode getWeatherForecastByCity(String ville) {
        String endpoint = this.baseUrl.replace("current.json", "forecast.json") 
                        + "?key=" + this.apiKey + "&q=" + ville + "&days=5&aqi=no";
        return fetchWeatherData(endpoint);
    }

    // Get current weather by latitude and longitude
    public JsonNode getCurrentWeatherByCoordinates(double latitude, double longitude) {
        String query = latitude + "," + longitude;
        String endpoint = this.baseUrl + "?key=" + this.apiKey + "&q=" + query + "&aqi=no";
        return fetchWeatherData(endpoint);
    }

    // Get weather forecast by latitude and longitude
    public JsonNode getWeatherForecastByCoordinates(double latitude, double longitude) {
        String query = latitude + "," + longitude;
        String endpoint = this.baseUrl.replace("current.json", "forecast.json") 
                        + "?key=" + this.apiKey + "&q=" + query + "&days=5&aqi=no";
        return fetchWeatherData(endpoint);
    }

    // Fetch weather data (common method for HTTP requests)
    public JsonNode fetchWeatherData(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        WeatherApi weatherApi = new WeatherApi();

        // Test: Current weather by city
        //JsonNode currentWeatherCity = weatherApi.getCurrentWeatherByCity("Marrakech");
        //System.out.println("Current Weather (City): " + currentWeatherCity);

         //Test: Weather forecast by city
        JsonNode forecastWeatherCity = weatherApi.getWeatherForecastByCity("Marrakech");
        System.out.println("Weather Forecast (City): " + forecastWeatherCity);

        // Test: Current weather by coordinates
        //JsonNode currentWeatherCoords = weatherApi.getCurrentWeatherByCoordinates(31.63, -8.0);
        //System.out.println("Current Weather (Coords): " + currentWeatherCoords);

        // Test: Weather forecast by coordinates
        //JsonNode forecastWeatherCoords = weatherApi.getWeatherForecastByCoordinates(31.63, -8.0);
        //System.out.println("Weather Forecast (Coords): " + forecastWeatherCoords);
    }
}
