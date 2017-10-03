package ForecastProcessor;

import Responses.ResponseWriter;
import UrlBuilder.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ForecastProcessor implements ForecastInterface{

    private static ForecastProcessor forecastProcessor = new ForecastProcessor();


    public ArrayList<String> getCurrentWeatherInArrayList(String city) throws IOException, JSONException {
            ArrayList<String> content = new ArrayList<>();
            ResponseWriter responseWriter = new ResponseWriter();
            JSONObject forecastAsJSONObject = responseWriter.getForecastDataJSONObject(city);
            JSONArray forecastAsJSONArray = responseWriter.getForecastDataJSONArray(city);
            Double lat = responseWriter.getLatitude(forecastAsJSONObject);
            Double lon = responseWriter.getLongitude(forecastAsJSONObject);
            Double maxtemp = responseWriter.getMaxTemp(forecastAsJSONArray, 0);
            Double mintemp = responseWriter.getMinTemp(forecastAsJSONArray, 0);
            String date = responseWriter.getDateInText(forecastAsJSONArray, 0);
            content.add("Latitude: " + lat.toString());
            content.add("Logitude: " + lon.toString());
            content.add("Max temp: " + maxtemp.toString());
            content.add("Min temp: " + mintemp.toString());
            content.add("Date: " + date);
        return content;
    }

    @Override
    public HashMap<String, Object> createHashMapOfThreeDayForecast (JSONObject dataAsJSONObject, ResponseWriter responseWriter) throws JSONException, IOException {
        JSONArray dataInJsonArray = responseWriter.getWeatherDataList(dataAsJSONObject);
        double minTemperatuur = Integer.MAX_VALUE;
        double maxTemperatuur = Integer.MIN_VALUE;
        HashMap<String, Object> threeDaysWithTheirMinAndMaxTemperatures = new HashMap<>();
        ArrayList<Double> minAndMaxTemperatures = new ArrayList<>();

        for(int i=0; i < dataInJsonArray.length(); i++) {
            if (!Objects.equals(responseWriter.getDateInText(dataInJsonArray, i), responseWriter.getDateInText(dataInJsonArray, 0)) &&
                    (!dataInJsonArray.isNull(i + 1) && (Objects.equals(responseWriter.getDateInText(dataInJsonArray, i),
                            responseWriter.getDateInText(dataInJsonArray,i+1)))) ){
                if (responseWriter.getMaxTemp(dataInJsonArray, i) > maxTemperatuur) {
                    maxTemperatuur = responseWriter.getMaxTemp(dataInJsonArray, i);
                }
                if (responseWriter.getMinTemp(dataInJsonArray, i) < minTemperatuur) {
                    minTemperatuur = responseWriter.getMinTemp(dataInJsonArray, i);
                }
            } else if (!Objects.equals(responseWriter.getDateInText(dataInJsonArray, i), responseWriter.getDateInText(dataInJsonArray, 0)) &&
                    (dataInJsonArray.isNull(i + 1) || (!Objects.equals(responseWriter.getDateInText(dataInJsonArray, i),
                            responseWriter.getDateInText(dataInJsonArray,i+1))))) {
                if (responseWriter.getMaxTemp(dataInJsonArray, i) > maxTemperatuur) {
                    maxTemperatuur = responseWriter.getMaxTemp(dataInJsonArray, i);
                }
                if (responseWriter.getMinTemp(dataInJsonArray, i) < minTemperatuur) {
                    minTemperatuur = responseWriter.getMinTemp(dataInJsonArray, i);
                }
                minAndMaxTemperatures.add(maxTemperatuur);
                minAndMaxTemperatures.add(minTemperatuur);
                if (threeDaysWithTheirMinAndMaxTemperatures.size() < 3) {
                    threeDaysWithTheirMinAndMaxTemperatures.put( responseWriter.getDateInText(dataInJsonArray, i), minAndMaxTemperatures.clone());
                }
                minAndMaxTemperatures.clear();
                maxTemperatuur = Integer.MAX_VALUE;
                minTemperatuur = Integer.MIN_VALUE;
            }
        }
        return threeDaysWithTheirMinAndMaxTemperatures;
    }

    @Override
    public ArrayList<Double> getLatLonOfCityFromUrlResponseInArrayList(JSONObject dataAsJSONObject, ResponseWriter responseWriter) throws JSONException {
        Double lat = responseWriter.getLatitude(dataAsJSONObject);
        Double lon = responseWriter.getLongitude(dataAsJSONObject);
        ArrayList<Double> latAndLon = new ArrayList<>();
        latAndLon.add(lat);
        latAndLon.add(lon);
        return latAndLon;
    }

    public static void main(String[] args) throws JSONException, IOException {
        UrlBuilder urlBuilder = new UrlBuilder();
        String city = "Tallinn";
        String APPID = "9b228ef8a0793abbdea7b9849333ecbb";
        URL url = urlBuilder.buildNewForecastRequestURL(city, APPID);
        ResponseWriter rw = new ResponseWriter();
        String data = rw.getResponseBodyFromURL(url);
        JSONObject jo = rw.makeStringToJSON(data);
        System.out.println(forecastProcessor.createHashMapOfThreeDayForecast(jo, rw));
        System.out.println(forecastProcessor.getLatLonOfCityFromUrlResponseInArrayList(jo, rw));
    }

}
