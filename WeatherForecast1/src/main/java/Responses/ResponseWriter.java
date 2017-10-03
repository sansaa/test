package Responses;

import UrlBuilder.UrlBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class ResponseWriter {
    static OkHttpClient ok = new OkHttpClient();

    public String getResponseBodyFromURL (URL url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = ok.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public JSONObject makeStringToJSON (String dataAsString) throws JSONException {
        JSONObject jsonObject = new JSONObject(dataAsString);
        return jsonObject;
    }

    public JSONArray getWeatherDataList (JSONObject dataAsJSONObject) throws JSONException {
        return dataAsJSONObject.getJSONArray("list");
    }

    public JSONObject getForecastDataJSONObject (String city) throws JSONException, IOException {
        UrlBuilder urlBuilder = new UrlBuilder();
        String APPID = "9b228ef8a0793abbdea7b9849333ecbb";
        URL url = urlBuilder.buildNewForecastRequestURL(city, APPID);
        ResponseWriter rw = new ResponseWriter();
        String data = rw.getResponseBodyFromURL(url);
        rw.makeStringToJSON(data);
        JSONObject jsonObject = rw.makeStringToJSON(data);
        return jsonObject;
    }

    public JSONArray getForecastDataJSONArray (String name) throws JSONException, IOException {
        JSONObject jsonObject = getForecastDataJSONObject(name);
        JSONArray forecastDataList = getWeatherDataList(jsonObject);
        return forecastDataList;
    }

    public Double getMaxTemp (JSONArray weatherDataList, Integer numberInArray) throws JSONException {
        return Double.parseDouble(weatherDataList.getJSONObject(numberInArray).getJSONObject("main").getString("temp_max"));
    }

    public Double getMinTemp (JSONArray getWeatherDataList, Integer numberInArray) throws JSONException {
        return Double.parseDouble(getWeatherDataList.getJSONObject(numberInArray).getJSONObject("main").getString("temp_min"));
    }

    public Double getLatitude (JSONObject dataAsJSONObject) throws JSONException {
        return Double.parseDouble(dataAsJSONObject.getJSONObject("city").getJSONObject("coord").getString("lat"));
    }

    public Double getLongitude(JSONObject dataAsJSONObject) throws JSONException {
        return Double.parseDouble(dataAsJSONObject.getJSONObject("city").getJSONObject("coord").getString("lon"));
    }

    public String getDateInText (JSONArray dataAsJSONObject, Integer numberInArray) throws JSONException {
        return dataAsJSONObject.getJSONObject(numberInArray).getString("dt_txt").substring(0, 10);
    }

    public static void main(String[] args) throws IOException, JSONException {
        UrlBuilder urlBuilder = new UrlBuilder();
        String city = "Tallinn";
        String APPID = "9b228ef8a0793abbdea7b9849333ecbb";
        URL url = urlBuilder.buildNewForecastRequestURL(city, APPID);
        System.out.println(url);
        ResponseWriter rw = new ResponseWriter();
        String data = rw.getResponseBodyFromURL(url);
        System.out.println(data);

    }
}
