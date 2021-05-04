package PIIA.Meteo;

import PIIA.Agenda.Agenda;
import PIIA.Plante.Plante;
import javafx.geometry.Insets;
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

public class Meteo extends BorderPane {
    private final Agenda agenda;
    private Plante plante;
    private final VBox left;
    private final ArrayList<HashMap<String, String>> forecastWeather = new ArrayList<>();
    private final HashMap<String, String> weather;
    public static final String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    public static final String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

    public Meteo(VBox left, Plante plante, Agenda agenda) {
        this.left = left;
        this.setLeft(left);
        this.plante = plante;
        this.agenda = agenda;
        setButtonActions();
        getWeather();
        for(int i = 1; i < 2 + 7; i++){
            getForecastWeather(i);
            String path = "Resources/WeatherXML/forecastWeather" + i + ".xml";
            forecastWeather.add(XmlDomParser.forecastParse(path));
        }
        weather = XmlDomParser.parse("Resources/WeatherXML/weather.xml");
        this.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 30), CornerRadii.EMPTY, Insets.EMPTY)));
        top();
        center();
        right();
        left.getChildren().add(new DatePickerSkin(new DatePicker()).getPopupContent());
    }

    private void right(){
        VBox box = new VBox();
        box.getChildren().add(informationPane());
        this.setRight(box);
    }

    private FlowPane informationPane(){
        int size = 25;
        Text weatherTxt = txt("Météo globale : " + forecastWeather.get(0).get("weather"), size);
        Text clouds = txt("Climat du jour  : " + weather.get("clouds"), size);
        Text gust = txt("Moyenne des rafales : " + forecastWeather.get(0).get("windGust") + " m/s", size);
        Text wind = txt("Vitesse du vent : " + weather.get("windSpeed") + " m/s", size);
        Text windDir = txt("Direction du vent : " + weather.get("windDirection"), size);
        Text humidity = txt("Humidité : " + weather.get("humidity") + "%", size);
        Text precipitationProbability = txt("Probabilité de précipitations : " + forecastWeather.get(0).get("precipitationProbability"), size - 3);
        Text precipitationType = txt("Type de précipitation : " + forecastWeather.get(0).get("precipitationType"), size);
        Text precipitationVolume = txt("Précipitation attendue : " + forecastWeather.get(0).get("precipitationVolume") + " mm", size - 3);
        Text actualPrecipitation = txt("Precipitation en cours : " + (weather.get("precipitation").equals("no") ? "aucune" : weather.get("precipitationType")), size);

        VBox box = new VBox(weatherTxt, clouds, gust, wind, windDir, humidity, precipitationProbability, precipitationType, precipitationVolume, actualPrecipitation);
        FlowPane flowPane = new FlowPane(box);
        flowPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        flowPane.setMaxWidth(box.getWidth());
        return flowPane;
    }

    private VBox temperaturePane(){
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

    private VBox feelsLikePane(){
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

    private Text txt(String s, int size){
        Text text = new Text(s);
        text.setFill(Color.WHITE);
        text.setFont(new Font(size));
        return text;
    }

    private void top(){

    }

    private void center(){
        VBox box = new VBox();

        /* Date */
        String[] splits = LocalDate.now().toString().split("-");
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        Text date = txt(days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2] + " " + splits[2] + " " + months[Integer.parseInt(splits[1])] + " " + splits[0], 50);

        /* Time */
        String[] splits2 = LocalDateTime.now().toString().split("T");
        String[] splitTime = splits2[1].split(":");
        Text time = txt(splitTime[0] + ":" + splitTime[1], 70);

        /* Temperature */
        Text temperature = txt("Température : " + weather.get("temperature") + "°C", 70);
        Text temperatureFeelsLike = txt("Ressentie : " + weather.get("feelsLike") + "°C", 60);

        /* Weather */
        Text clouds = txt("Météo actuelle : " + weather.get("clouds"), 50);

        box.getChildren().add(date);
        box.getChildren().add(time);
        box.getChildren().add(temperature);
        box.getChildren().add(temperatureFeelsLike);
        box.getChildren().add(clouds);
        box.getChildren().add(temperaturePane());
        box.getChildren().add(feelsLikePane());
        this.setCenter(box);
    }

    private void getForecastWeather(int cnt){
        String api = "https://api.openweathermap.org/data/2.5/forecast/daily?q=Paris&cnt=" + cnt + "&mode=xml&appid=d5d132013b7e8d4712976e55c8e2121b&units=metric&lang=fr";
        String path = "Resources/WeatherXML/forecastWeather" + cnt + ".xml";
        generateXML(api, path);
    }

    private void getWeather(){
        String api = "https://api.openweathermap.org/data/2.5/weather?q=Paris&units=metric&lang=fr&appid=d5d132013b7e8d4712976e55c8e2121b&mode=xml";
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
                for (String line; (line = reader.readLine()) != null;) {
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
