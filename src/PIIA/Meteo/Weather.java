package PIIA.Meteo;

import PIIA.Agenda.Agenda;
import PIIA.Plante.Plante;
import PIIA.PopUp.CityPopUp;
import PIIA.PopUp.FilterPopUp;
import PIIA.PopUp.PopUp;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Weather extends BorderPane {
    private final Agenda agenda;
    private Plante plante;
    private final VBox left;
    private ArrayList<HashMap<String, String>> forecastWeather;
    private HashMap<String, String> weather;
    public static final String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    public static final String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    private String city = "Saint-Chéron";

    public Weather(VBox left, Plante plante, Agenda agenda) {
        this.left = left;
        this.setLeft(left);
        this.plante = plante;
        this.agenda = agenda;
        setButtonActions();

        weathered();

        /* Display things */
        this.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 30), CornerRadii.EMPTY, Insets.EMPTY)));
        center();
        this.setRight(new VBox(informationPane(), nextDayForecastPane()));
        left.getChildren().add(new DatePickerSkin(new DatePicker()).getPopupContent());
        changeCityButton();
        left.getChildren().add(txt("Ville sélectionnée : ", 20));
        left.getChildren().add(txt(city, 25));
    }

    private void weathered() {
        /* Getting the weather */
        getWeather();
        getForecastWeather();
        forecastWeather = XmlDomParser.forecastParse("Resources/WeatherXML/forecastWeather.xml");
        weather = XmlDomParser.parse("Resources/WeatherXML/weather.xml");
    }

    private void changeCityButton() {
        Button button = new Button("Changer de ville");
        button.setPrefSize(225, 50);
        button.setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60), CornerRadii.EMPTY, Insets.EMPTY)));
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        button.setTextFill(Color.WHITE);
        button.setOnMouseClicked(mouseEvent -> new PopUp(agenda.getStage(), new CityPopUp(agenda, this), "Filter Creator"));
        left.getChildren().add(button);
    }

    public void changeCity(String city){
        this.city = city;
        weathered();
        center();
    }

    private FlowPane dayPane(int i){
        int size = 10;
        String[] splits = forecastWeather.get(i).get("date").split("-");
        Text date = txt(days[(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2 + i)%7] + " " + splits[2] + " " + months[Integer.parseInt(splits[1])] + " " + splits[0], size);
        Text min = txt("Température min : " + forecastWeather.get(i).get("temperatureMin") + "°C", size);
        Text max = txt("Température max : " + forecastWeather.get(i).get("temperatureMax") + "°C", size);
        Text weatherTxt = txt("Météo globale : " + forecastWeather.get(i).get("weather"), size);
        Text clouds = txt("Climat du jour  : " + forecastWeather.get(i).get("clouds"), size);
        Text precipitationProbability = txt("Probabilité de précipitations : " + forecastWeather.get(i).get("precipitationProbability"), size);

        VBox box = new VBox(date, min, max, weatherTxt, clouds, precipitationProbability);
        box.setMaxWidth(180);
        box.setMinWidth(180);

        FlowPane flowPane = new FlowPane(box);
        flowPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        flowPane.setMaxWidth(box.getMaxWidth());
        flowPane.setMinWidth(box.getMinWidth());
        return flowPane;
    }

    private GridPane nextDayForecastPane() {
        GridPane res = new GridPane();
        for(int i = 1; i < forecastWeather.size(); i++){
            res.add(dayPane(i), (i-1)%2, (i-1)/2);
        }
        return res;
    }

    private FlowPane informationPane() {
        int size = 25;
        Text weatherTxt = txt("Météo globale : " + forecastWeather.get(0).get("weather"), size);
        Text clouds = txt("Climat du jour  : " + weather.get("clouds"), size - 3);
        Text gust = txt("Moyenne des rafales : " + forecastWeather.get(0).get("windGust") + " m/s", size);
        Text wind = txt("Vitesse du vent : " + weather.get("windSpeed") + " m/s", size);
        Text windDir = txt("Direction du vent : " + weather.get("windDirection"), size);
        Text humidity = txt("Humidité : " + weather.get("humidity") + "%", size);
        Text precipitationProbability = txt("Probabilité de précipitations : " + forecastWeather.get(0).get("precipitationProbability"), size - 3);
        Text precipitationType = txt("Type de précipitation : " + forecastWeather.get(0).get("precipitationType"), size);
        Text precipitationVolume = txt("Précipitation attendue : " + forecastWeather.get(0).get("precipitationVolume") + " mm", size - 3);
        Text actualPrecipitation = txt("Precipitation en cours : " + (weather.get("precipitation").equals("no") ? "aucune" : weather.get("precipitationType")), size);

        VBox box = new VBox(weatherTxt, clouds, gust, wind, windDir, humidity, precipitationProbability, precipitationType, precipitationVolume, actualPrecipitation);
        box.setMaxWidth(360);
        box.setMinWidth(360);

        FlowPane flowPane = new FlowPane(box);
        flowPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        flowPane.setMaxWidth(box.getMaxWidth());
        flowPane.setMinWidth(box.getMinWidth());
        return flowPane;
    }

    private VBox temperaturePane() {
        int titleSize = 30;
        int valueSize = 35;

        GridPane pane = new GridPane();
        pane.setHgap(10);
        Text txt = txt("Températures", 60);

        int xPos = 0;

        Text morn = txt("Matinée", titleSize);
        Text mornT = txt(forecastWeather.get(0).get("temperatureMorn") + "°C", valueSize);
        pane.add(morn, xPos, 0);
        pane.add(mornT, xPos, 1);
        xPos++;

        Text day = txt("Journée", titleSize);
        Text dayT = txt(forecastWeather.get(0).get("temperatureDay") + "°C", valueSize);
        pane.add(day, xPos, 0);
        pane.add(dayT, xPos, 1);
        xPos++;

        Text eve = txt("Soirée", titleSize);
        Text eveT = txt(forecastWeather.get(0).get("temperatureEve") + "°C", valueSize);
        pane.add(eve, xPos, 0);
        pane.add(eveT, xPos, 1);
        xPos++;

        Text night = txt("Nuit", titleSize);
        Text nightT = txt(forecastWeather.get(0).get("temperatureNight") + "°C", valueSize);
        pane.add(night, xPos, 0);
        pane.add(nightT, xPos, 1);
        xPos++;

        Text min = txt("Minimum", titleSize);
        Text minT = txt(forecastWeather.get(0).get("temperatureMin") + "°C", valueSize);
        pane.add(min, xPos, 0);
        pane.add(minT, xPos, 1);
        xPos++;

        Text max = txt("Maximum", titleSize);
        Text maxT = txt(forecastWeather.get(0).get("temperatureMax") + "°C", valueSize);
        pane.add(max, xPos, 0);
        pane.add(maxT, xPos, 1);

        FlowPane flowPane = new FlowPane(pane);
        flowPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        flowPane.setMaxWidth(pane.getWidth());

        return new VBox(txt, flowPane);
    }

    private VBox feelsLikePane() {
        int titleSize = 30;
        int valueSize = 35;

        GridPane pane = new GridPane();
        pane.setHgap(10);
        Text txt = txt("Ressenti", 60);

        int xPos = 0;

        Text morn = txt("Matinée", titleSize);
        Text mornT = txt(forecastWeather.get(0).get("feelsLikeMorn") + "°C", valueSize);
        pane.add(morn, xPos, 0);
        pane.add(mornT, xPos, 1);
        xPos++;

        Text day = txt("Journée", titleSize);
        Text dayT = txt(forecastWeather.get(0).get("feelsLikeDay") + "°C", valueSize);
        pane.add(day, xPos, 0);
        pane.add(dayT, xPos, 1);
        xPos++;

        Text eve = txt("Soirée", titleSize);
        Text eveT = txt(forecastWeather.get(0).get("feelsLikeEve") + "°C", valueSize);
        pane.add(eve, xPos, 0);
        pane.add(eveT, xPos, 1);
        xPos++;

        Text night = txt("Nuit", titleSize);
        Text nightT = txt(forecastWeather.get(0).get("feelsLikeNight") + "°C", valueSize);
        pane.add(night, xPos, 0);
        pane.add(nightT, xPos, 1);

        FlowPane flowPane = new FlowPane(pane);
        flowPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        flowPane.setMaxWidth(pane.getWidth());
        return new VBox(txt, flowPane);
    }

    private Text txt(String s, int size) {
        Text text = new Text(s);
        text.setFill(Color.WHITE);
        text.setFont(new Font(size));
        return text;
    }

    private void center() {
        /* Date */
        String[] splits = LocalDate.now().toString().split("-");
        Text date = txt(days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2] + " " + splits[2] + " " + months[Integer.parseInt(splits[1])] + " " + splits[0], 50);

        /* Time */
        String[] splits2 = LocalDateTime.now().toString().split("T");
        String[] splitTime = splits2[1].split(":");
        Text time = txt(splitTime[0] + ":" + splitTime[1], 70);

        /* Temperature */
        Text temperature = txt("Température : " + weather.get("temperature") + "°C", 70);
        Text temperatureFeelsLike = txt("Ressentie : " + weather.get("feelsLike") + "°C", 60);

        /* Weather */
        Text clouds = txt("Météo actuelle : " + weather.get("clouds"), 40);

        this.setCenter(new VBox(date, time, temperature, temperatureFeelsLike, clouds, temperaturePane(), feelsLikePane()));
    }

    private void getForecastWeather() {
        String api = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "&cnt=11&mode=xml&appid=d5d132013b7e8d4712976e55c8e2121b&units=metric&lang=fr";
        String path = "Resources/WeatherXML/forecastWeather.xml";
        generateXML(api, path);
    }

    private void getWeather() {
        String api = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&lang=fr&appid=d5d132013b7e8d4712976e55c8e2121b&mode=xml";
        String path = "Resources/WeatherXML/weather.xml";
        generateXML(api, path);
    }

    private void generateXML(String api, String path) {
        try {
            File f = new File(path);
            FileWriter writer = new FileWriter(f);
            URL url = new URL(api);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            try (var reader = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    writer.write(line);
                }
            }
            c.disconnect();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setButtonActions() {
        left.getChildren().get(0).setOnMouseClicked(mouseEvent -> getScene().setRoot(agenda));
        left.getChildren().get(1).setOnMouseClicked(mouseEvent -> getScene().setRoot(plante));
    }

    public void setPlante(Plante plante) {
        this.plante = plante;
    }
}
