package ForecastProcessor;

import Responses.ResponseWriter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public interface ForecastInterface {

    HashMap<String, Object> createHashMapOfThreeDayForecast( JSONObject dataInJSONFormat, ResponseWriter responseWriter)throws JSONException, IOException;
    ArrayList<String> getCurrentWeatherInArrayList(String city) throws IOException, JSONException;
    ArrayList<Double> getLatLonOfCityFromUrlResponseInArrayList(JSONObject dataFromURLBodyInStringForm, ResponseWriter responseController) throws JSONException;

}
