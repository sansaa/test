import UrlBuilder.UrlBuilder;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UrlBuilderTest {

    private String city = "Tallinn";
    private String appId = "9b228ef8a0793abbdea7b9849333ecbb";
    private URL correctForecastURL = new URL("http://api.openweathermap.org//data/2.5/forecast?q=Tallinn&APPID=9b228ef8a0793abbdea7b9849333ecbb&units=metric");
    private UrlBuilder urlBuilder = new UrlBuilder();

    public UrlBuilderTest() throws MalformedURLException {
    }

    @Test
    public void testIfBuildNewForecastURLIsURL() throws Exception{
            URL url = urlBuilder.buildNewForecastRequestURL(city, appId);
            System.out.println(url);
            assertThat(url, instanceOf(URL.class));
    }
    @Test
    public void doesBuildNewForecastURLReturnCorrectURL() throws Exception{
        URL currentWeatherRequestURL = urlBuilder.buildNewForecastRequestURL(city, appId);
        assertEquals(currentWeatherRequestURL, correctForecastURL);
    }

}
