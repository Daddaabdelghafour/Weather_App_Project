package JavaProject.WeatherApp;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather App");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1e90ff, #87cefa);");

        // Top bar with sign in and sign up buttons
        HBox topBar = createTopBar();
        root.setTop(topBar);

        // Left sidebar
        VBox leftSidebar = createLeftSidebar();
        root.setLeft(leftSidebar);

        // Main content
        VBox mainContent = createMainContent();
        root.setCenter(mainContent);

        // Right sidebar for forecasting
        VBox rightSidebar = createRightSidebar();
        root.setRight(rightSidebar);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);");

        Button signInButton = new Button("Sign In");
        Button signUpButton = new Button("Sign Up");

        styleButton(signInButton);
        styleButton(signUpButton);

        topBar.getChildren().addAll(signInButton, signUpButton);
        return topBar;
    }

    private VBox createLeftSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
        sidebar.setPrefWidth(200);

        Label menuTitle = new Label("Menu");
        menuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        menuTitle.setTextFill(Color.WHITE);

        VBox menuItems = new VBox(10);
        String[] items = {"Dashboard", "Maps", "Settings", "Help"};
        for (String item : items) {
            Label menuItem = new Label(item);
            menuItem.setTextFill(Color.WHITE);
            menuItems.getChildren().add(menuItem);
        }

        sidebar.getChildren().addAll(menuTitle, menuItems);
        return sidebar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPadding(new Insets(20));

        // Search box
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        TextField cityInput = new TextField();
        cityInput.setPromptText("Enter city name");
        cityInput.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-text-fill: white;");
        Button searchButton = new Button("Search");
        styleButton(searchButton);
        searchBox.getChildren().addAll(cityInput, searchButton);

        // Weather icon
        ImageView weatherIcon = new ImageView(new Image("https://cdn-icons-png.flaticon.com/512/1163/1163661.png"));
        weatherIcon.setFitWidth(100);
        weatherIcon.setFitHeight(100);

        // Temperature
        Label temperatureLabel = new Label("25°C");
        temperatureLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        temperatureLabel.setTextFill(Color.WHITE);

        // City name
        Label cityLabel = new Label("New York");
        cityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        cityLabel.setTextFill(Color.WHITE);

        // Weather description
        Label descriptionLabel = new Label("Partly Cloudy");
        descriptionLabel.setFont(Font.font("Arial", 18));
        descriptionLabel.setTextFill(Color.WHITE);

        // Additional weather info
        HBox weatherInfoBox = new HBox(30);
        weatherInfoBox.setAlignment(Pos.CENTER);

        VBox humidityBox = createInfoBox("Humidity", "60%");
        VBox windBox = createInfoBox("Wind", "5 km/h");
        VBox pressureBox = createInfoBox("Pressure", "1015 hPa");

        weatherInfoBox.getChildren().addAll(humidityBox, windBox, pressureBox);

        // Temperature graph
        LineChart<String, Number> temperatureChart = createTemperatureChart();

        mainContent.getChildren().addAll(searchBox, weatherIcon, temperatureLabel, cityLabel, 
                                         descriptionLabel, weatherInfoBox, temperatureChart);

        return mainContent;
    }

    private VBox createRightSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
        sidebar.setPrefWidth(200);

        Label forecastTitle = new Label("5-Day Forecast");
        forecastTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        forecastTitle.setTextFill(Color.WHITE);

        VBox forecastItems = new VBox(10);
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] temps = {"24°C", "26°C", "23°C", "25°C", "22°C"};
        for (int i = 0; i < days.length; i++) {
            HBox forecastItem = new HBox(10);
            Label dayLabel = new Label(days[i]);
            Label tempLabel = new Label(temps[i]);
            dayLabel.setTextFill(Color.WHITE);
            tempLabel.setTextFill(Color.WHITE);
            forecastItem.getChildren().addAll(dayLabel, tempLabel);
            forecastItems.getChildren().add(forecastItem);
        }

        sidebar.getChildren().addAll(forecastTitle, forecastItems);
        return sidebar;
    }

    private VBox createInfoBox(String title, String value) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label valueLabel = new Label(value);
        valueLabel.setTextFill(Color.WHITE);
        valueLabel.setFont(Font.font("Arial", 18));

        box.getChildren().addAll(titleLabel, valueLabel);
        return box;
    }

    private LineChart<String, Number> createTemperatureChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Temperature (°C)");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Temperature Variation");
        lineChart.setCreateSymbols(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Temperature");

        series.getData().add(new XYChart.Data<>("00:00", 22));
        series.getData().add(new XYChart.Data<>("06:00", 19));
        series.getData().add(new XYChart.Data<>("12:00", 25));
        series.getData().add(new XYChart.Data<>("18:00", 27));
        series.getData().add(new XYChart.Data<>("23:59", 23));

        lineChart.getData().add(series);
        lineChart.setMaxSize(500, 300);

        return lineChart;
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}