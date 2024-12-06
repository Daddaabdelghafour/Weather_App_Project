package GraphTest;


import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import Dao.GraphManager;
import Services.Apis.WeatherApi;
import Services.Graphs.WeatherGraphService;

class WeatherGraphServiceTest {

    private WeatherGraphService weatherGraphService;
    private WeatherApi weatherApiMock;
    private GraphManager graphManagerMock;
    private JsonNode forecastDataMock;

    @BeforeEach
    void setUp() {
        graphManagerMock = mock(GraphManager.class);
        weatherApiMock = mock(WeatherApi.class);
        forecastDataMock = mock(JsonNode.class);

        // Inject mocked dependencies
        weatherGraphService = new WeatherGraphService(graphManagerMock);
        weatherGraphService.weatherApi = weatherApiMock;
    }

    @Test
    void testGenerateGraphsForCity() throws Exception {
        String cityName = "TestCity";
        int userId = 1;

        // Mocking WeatherApi response
        when(weatherApiMock.getWeatherForecastByCity(cityName)).thenReturn(forecastDataMock);
        when(forecastDataMock.get("forecast")).thenReturn(forecastDataMock);

        // Mocking GraphManager behavior
        when(graphManagerMock.isUpdateNeeded(userId, cityName, "RainGraph")).thenReturn(true);
        when(graphManagerMock.isUpdateNeeded(userId, cityName, "TemperatureGraph")).thenReturn(true);
        when(graphManagerMock.isUpdateNeeded(userId, cityName, "WindGraph")).thenReturn(true);

        // Mock RainGraph, TemperatureGraph, and WindGraph generation methods
        RainGraph rainGraphMock = mock(RainGraph.class);
        TemperatureGraph temperatureGraphMock = mock(TemperatureGraph.class);
        WindGraph windGraphMock = mock(WindGraph.class);

        weatherGraphService.rainGraph = rainGraphMock;
        weatherGraphService.temperatureGraph = temperatureGraphMock;
        weatherGraphService.windGraph = windGraphMock;

        // Call the method under test
        weatherGraphService.generateGraphsForCity(cityName, userId);

        // Verify interactions with weather API
        verify(weatherApiMock).getWeatherForecastByCity(cityName);

        // Verify graph generation calls
        verify(rainGraphMock).generateRainGraph(forecastDataMock, cityName, userId, graphManagerMock);
        verify(temperatureGraphMock).generateTemperatureGraph(forecastDataMock, cityName, userId, graphManagerMock);
        verify(windGraphMock).generateWindGraph(forecastDataMock, cityName, userId, graphManagerMock);

        // Verify isUpdateNeeded calls
        verify(graphManagerMock).isUpdateNeeded(userId, cityName, "RainGraph");
        verify(graphManagerMock).isUpdateNeeded(userId, cityName, "TemperatureGraph");
        verify(graphManagerMock).isUpdateNeeded(userId, cityName, "WindGraph");
    }

    @Test
    void testGenerateGraphsForCity_NoUpdateNeeded() throws Exception {
        String cityName = "TestCity";
        int userId = 1;

        // Mocking WeatherApi response
        when(weatherApiMock.getWeatherForecastByCity(cityName)).thenReturn(forecastDataMock);
        when(forecastDataMock.get("forecast")).thenReturn(forecastDataMock);

        // Mocking GraphManager behavior: No update needed for any graph
        when(graphManagerMock.isUpdateNeeded(userId, cityName, "RainGraph")).thenReturn(false);
        when(graphManagerMock.isUpdateNeeded(userId, cityName, "TemperatureGraph")).thenReturn(false);
        when(graphManagerMock.isUpdateNeeded(userId, cityName, "WindGraph")).thenReturn(false);

        // Call the method under test
        weatherGraphService.generateGraphsForCity(cityName, userId);

        // Verify no graph generation occurs
        verifyNoInteractions(weatherGraphService.rainGraph);
        verifyNoInteractions(weatherGraphService.temperatureGraph);
        verifyNoInteractions(weatherGraphService.windGraph);
    }
}
