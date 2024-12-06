package GraphTest;


import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import Dao.GraphManager;
import Services.Graphs.WindGraph;

class WindGraphTest {

    private WindGraph windGraph;
    private GraphManager graphManagerMock;
    private JsonNode forecastDataMock;

    @BeforeEach
    void setUp() {
        windGraph = new WindGraph();
        graphManagerMock = mock(GraphManager.class);
        forecastDataMock = mock(JsonNode.class);
    }

    @Test
    void testGenerateWindGraph() throws IOException, Exception {
        String cityName = "TestCity";
        int userId = 1;

        // Mock forecast data
        JsonNode forecastDaysMock = mock(JsonNode.class);
        JsonNode dayMock = mock(JsonNode.class);
        JsonNode dayDetailsMock = mock(JsonNode.class);

        when(forecastDataMock.get("forecast")).thenReturn(forecastDataMock);
        when(forecastDataMock.get("forecastday")).thenReturn(forecastDaysMock);
        when(forecastDaysMock.iterator()).thenReturn(java.util.Arrays.asList(dayMock).iterator());

        when(dayMock.get("date")).thenReturn(mock(JsonNode.class));
        when(dayMock.get("date").asText()).thenReturn("2024-12-07");

        when(dayMock.get("day")).thenReturn(dayDetailsMock);
        when(dayDetailsMock.get("maxwind_kph")).thenReturn(mock(JsonNode.class));
        when(dayDetailsMock.get("maxwind_kph").asDouble()).thenReturn(20.5);

        // Mock GraphManager behavior
        when(graphManagerMock.upsertGraph(userId, cityName, "Wind", new byte[]{})).thenReturn(true);

        // Run the method
        windGraph.generateWindGraph(forecastDataMock, cityName, userId, graphManagerMock);

        // Verify interactions with the GraphManager
        verify(graphManagerMock, atLeastOnce()).upsertGraph(eq(userId), eq(cityName), eq("Wind"), any(byte[].class));
    }
}
