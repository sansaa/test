import ForecastProcessor.ForecastProcessor;
import Responses.ResponseWriter;
import UrlBuilder.UrlBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ForecastProcessorTest {

    private UrlBuilder urlBuilder = new UrlBuilder();
    private String city = "Tallinn";
    private String appId = "9b228ef8a0793abbdea7b9849333ecbb";
    private URL url = urlBuilder.buildNewForecastRequestURL(city, appId);
    private ResponseWriter responseWriter = new ResponseWriter();
    private String data = responseWriter.getResponseBodyFromURL(url);
    private JSONObject jsonObject = responseWriter.makeStringToJSON(data);
    private ForecastProcessor forecastProcessor = new ForecastProcessor();


    public ForecastProcessorTest() throws JSONException, IOException {
    }


    @Test
    public void doesGetLatLonOfCityFromUrlResponseInArrayListReturnArrayList() throws IOException, JSONException {
        ArrayList<Double> response = forecastProcessor.getLatLonOfCityFromUrlResponseInArrayList(jsonObject, responseWriter);
        assertThat(response, instanceOf(ArrayList.class));
    }

    @Test
    public void doesGetLatLonOfCityFromUrlResponseInArrayListReturnTallinnCoordinates() throws Exception {
        ArrayList<Double> response = forecastProcessor.getLatLonOfCityFromUrlResponseInArrayList(jsonObject, responseWriter);
        ArrayList<Double> testresponse = new ArrayList<>();
        testresponse.add(59.4372);
        testresponse.add(24.7454);
        assertEquals(response, testresponse);
    }

    @Test
    public void doesCreateHashMapOfThreeDayForecastReturnHashMap() throws IOException, JSONException {
        ForecastProcessor forecastProcessor = new ForecastProcessor();
        assertEquals(forecastProcessor.createHashMapOfThreeDayForecast(jsonObject, responseWriter).getClass(), HashMap.class);
    }
    @Test
    public void doesGetCurrentWeatherInArrayListReturnArrayList() throws IOException, JSONException {
        ArrayList<String> response = forecastProcessor.getCurrentWeatherInArrayList(city);
        assertThat(response, instanceOf(ArrayList.class));
    }



}
