package PIIA.Meteo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class XmlDomParser {
    /**
     * Parser for forecastWeather.xml file
     *
     * @param path path the file
     * @return the hashmap of desired information
     */
    public static HashMap<String, String> forecastParse(String path) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        HashMap<String, String> res = new HashMap<>();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(path));
            doc.getDocumentElement().normalize();

            /* forecast */
            NodeList list = doc.getElementsByTagName("forecast");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    /* Get sunrise & sunset */
                    NodeList sunList = element.getElementsByTagName("sun");
                    res.put("sunrise", sunList.item(0).getAttributes().getNamedItem("rise").getTextContent());
                    res.put("sunset", sunList.item(0).getAttributes().getNamedItem("set").getTextContent());

                    /* Get temperature */
                    NodeList temperatureList = element.getElementsByTagName("temperature");
                    res.put("temperatureDay", temperatureList.item(0).getAttributes().getNamedItem("day").getTextContent());
                    res.put("temperatureNight", temperatureList.item(0).getAttributes().getNamedItem("night").getTextContent());
                    res.put("temperatureEve", temperatureList.item(0).getAttributes().getNamedItem("eve").getTextContent());
                    res.put("temperatureMorn", temperatureList.item(0).getAttributes().getNamedItem("morn").getTextContent());
                    res.put("temperatureMax", temperatureList.item(0).getAttributes().getNamedItem("min").getTextContent());
                    res.put("temperatureMin", temperatureList.item(0).getAttributes().getNamedItem("max").getTextContent());

                    /* Get feels like */
                    NodeList feelsLikeList = element.getElementsByTagName("feels_like");
                    res.put("feelsLikeDay", feelsLikeList.item(0).getAttributes().getNamedItem("day").getTextContent());
                    res.put("feelsLikeNight", feelsLikeList.item(0).getAttributes().getNamedItem("night").getTextContent());
                    res.put("feelsLikeEve", feelsLikeList.item(0).getAttributes().getNamedItem("eve").getTextContent());
                    res.put("feelsLikeMorn", feelsLikeList.item(0).getAttributes().getNamedItem("morn").getTextContent());

                    /* Get humidity */
                    res.put("humidity", element.getElementsByTagName("humidity").item(0).getAttributes().getNamedItem("value").getTextContent());

                    /* Get wind */
                    res.put("windSpeed", element.getElementsByTagName("windSpeed").item(0).getAttributes().getNamedItem("mps").getTextContent());
                    res.put("windDirection", element.getElementsByTagName("windDirection").item(0).getAttributes().getNamedItem("code").getTextContent());
                    res.put("windGust", element.getElementsByTagName("windGust").item(0).getAttributes().getNamedItem("gust").getTextContent());

                    /* Get precipitation */
                    NodeList precipitationList = element.getElementsByTagName("precipitation");
                    res.put("precipitationProbability", precipitationList.item(0).getAttributes().getNamedItem("probability").getTextContent());
                    res.put("precipitationType", precipitationList.item(0).getAttributes().getNamedItem("type").getTextContent());
                    res.put("precipitationVolume", precipitationList.item(0).getAttributes().getNamedItem("value").getTextContent());

                    /* Get weather */
                    res.put("weather", element.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("name").getTextContent());

                    /* Get clouds */
                    res.put("clouds", element.getElementsByTagName("clouds").item(0).getAttributes().getNamedItem("value").getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Parser for weather.xml file
     *
     * @param path path the file
     * @return the hashmap of desired information
     */
    public static HashMap<String, String> parse(String path) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        HashMap<String, String> res = new HashMap<>();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(path));
            doc.getDocumentElement().normalize();

            /* forecast */
            NodeList list = doc.getElementsByTagName("current");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    /* Get temperature */
                    res.put("temperature", element.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getTextContent());

                    /* Get feels like */
                    res.put("feelsLike", element.getElementsByTagName("feels_like").item(0).getAttributes().getNamedItem("value").getTextContent());

                    /* Get humidity */
                    res.put("humidity", element.getElementsByTagName("humidity").item(0).getAttributes().getNamedItem("value").getTextContent());

                    /* Get wind */
                    res.put("windSpeed", element.getElementsByTagName("speed").item(0).getAttributes().getNamedItem("value").getTextContent());
                    res.put("windDirection", element.getElementsByTagName("direction").item(0).getAttributes().getNamedItem("code").getTextContent());

                    /* Get precipitation */
                    NodeList precipitationList = element.getElementsByTagName("precipitation");
                    res.put("precipitation", precipitationList.item(0).getAttributes().getNamedItem("mode").getTextContent());
                    if (!res.get("precipitation").equals("no"))
                        res.put("precipitationType", precipitationList.item(0).getAttributes().getNamedItem("value").getTextContent());
                    else res.put("precipitationType", "");

                    /* Get clouds */
                    res.put("clouds", element.getElementsByTagName("clouds").item(0).getAttributes().getNamedItem("name").getTextContent());
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
