package GraphTest;


import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.JsonNode;

import Dao.GraphManager;
import Services.Graphs.RainGraph;

class RainGraphTest {

    private RainGraph rainGraph;
    private GraphManager graphManagerMock;
    private JsonNode forecastDataMock;
    private JsonNode forecastDayMock;
    private JsonNode dayNodeMock;

    @BeforeEach
    void setUp() {
        rainGraph = new RainGraph();
        graphManagerMock = mock(GraphManager.class);
        forecastDataMock = mock(JsonNode.class);
        forecastDayMock = mock(JsonNode.class);
        dayNodeMock = mock(JsonNode.class);
    }

    @Test
    void testGenerateRainGraph() throws IOException, SQLException {
        // Mocking the forecast data structure
        when(forecastDataMock.get("forecast")).thenReturn(forecastDataMock);
        when(forecastDataMock.get("forecastday")).thenReturn(forecastDayMock);

        // Simulate multiple days
        when(forecastDayMock.iterator()).thenReturn(java.util.Arrays.asList(dayNodeMock, dayNodeMock).iterator());
        when(dayNodeMock.get("date")).thenReturn(mock(JsonNode.class));
        when(dayNodeMock.get("date").asText()).thenReturn("2024-12-06");

        JsonNode dayMock = mock(JsonNode.class);
        when(dayNodeMock.get("day")).thenReturn(dayMock);
        when(dayMock.get("totalprecip_mm")).thenReturn(mock(JsonNode.class));
        when(dayMock.get("totalprecip_mm").asDouble()).thenReturn(15.0);

        // Mock the GraphManager behavior
        when(graphManagerMock.upsertGraph(anyInt(), anyString(), anyString(), any(byte[].class)))
                .thenReturn(true);

        // Call the method under test
        rainGraph.generateRainGraph(forecastDataMock, "TestCity", 1, graphManagerMock);

        // Verify the upsertGraph method was called with expected parameters
        verify(graphManagerMock).upsertGraph(eq(1), eq("TestCity"), eq("Rainfall"), any(byte[].class));
    }
}
