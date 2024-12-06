package Services.Graphs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.fasterxml.jackson.databind.JsonNode;

import Dao.GraphManager;

public class TemperatureGraph {

    // Generate and save the temperature graph
    public void generateTemperatureGraph(JsonNode forecastData, String cityName, int userId, GraphManager graphManager) {
        try {
            // Prepare the dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            JsonNode forecastDays = forecastData.get("forecast").get("forecastday");
            for (JsonNode day : forecastDays) {
                String date = day.get("date").asText();
                double maxTemp = day.get("day").get("maxtemp_c").asDouble(); // Max temperature in °C
                double minTemp = day.get("day").get("mintemp_c").asDouble(); // Min temperature in °C

                dataset.addValue(maxTemp, "Max Temp (°C)", date);
                dataset.addValue(minTemp, "Min Temp (°C)", date);
            }

            // Create the chart
            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Temperature Forecast for " + cityName,
                    "Date",
                    "Temperature (°C)",
                    dataset
            );

            // Save as a temporary image file
            File tempFile = new File("temperature_graph.png");
            ChartUtils.saveChartAsPNG(tempFile, lineChart, 800, 600);

            // Read the file as byte array
            byte[] imageData = tempFile.toPath().toString().getBytes();

            // Insert or update the graph in the database
            graphManager.upsertGraph(userId, cityName, "Temperature", imageData);

            // Delete the temporary file
            tempFile.delete();

            System.out.println("Temperature graph saved for city: " + cityName);
        } catch (IOException  e) {
            System.err.println("Error generating or saving temperature graph: " + e.getMessage());
        }
    }
}
