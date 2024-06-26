package accuweather;

import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {
    static Properties properties = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;

    @BeforeAll
    static void initTest() throws IOException {

        configFile = new FileInputStream("src/test/resources/accuweather.properties");
        properties.load(configFile);

        apiKey = properties.getProperty("apikey");
        baseUrl = properties.getProperty("base_url");

    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

}
