import Responses.ResponseWriter;
import UrlBuilder.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ResponsesTest {

    private UrlBuilder urlBuilder = new UrlBuilder();
    private String city = "Tallinn";
    private String appId = "9b228ef8a0793abbdea7b9849333ecbb";
    private URL url = urlBuilder.buildNewForecastRequestURL(city, appId);
    private ResponseWriter responseWriter = new ResponseWriter();
    private String responseBody = responseWriter.getResponseBodyFromURL(url);
    private String data = responseWriter.getResponseBodyFromURL(url);
    private JSONObject jsonObject = responseWriter.makeStringToJSON(data);
    private JSONArray jsonArray = responseWriter.getWeatherDataList(jsonObject);

    public ResponsesTest() throws JSONException, IOException {
    }

    @Test
    public void isGetResponseBodyFromUrlString() throws IOException, JSONException {
        assertThat(responseBody, instanceOf(String.class));
    }

    @Test
    public void didItMakeStringToJSONObject () throws JSONException {
        assertThat(jsonObject, instanceOf(JSONObject.class));
    }

    @Test
    public void isLongitudeDouble() throws JSONException {
            assertThat(responseWriter.getLongitude(jsonObject), instanceOf(Double.class));
    }

    @Test
    public void isLatitudeDouble() throws JSONException {
        assertThat(responseWriter.getLatitude(jsonObject), instanceOf(Double.class));
    }

    @Test
    public void isMaxTempDouble() throws JSONException {
        assertThat(responseWriter.getMaxTemp(jsonArray, 0), instanceOf(Double.class));
    }

    @Test
    public void isMinTempDouble() throws JSONException {
        assertThat(responseWriter.getMinTemp(jsonArray, 0), instanceOf(Double.class));
    }

    @Test
    public void isMaxTempBiggerThanMinTemp() throws JSONException {
        assertTrue(responseWriter.getMaxTemp(jsonArray, 0) > responseWriter.getMinTemp(jsonArray, 0));
    }

    @Test
    public void doesGetDateInTextReturnString() throws JSONException {
        assertThat(responseWriter.getDateInText(jsonArray, 0), instanceOf(String.class));
    }


}
