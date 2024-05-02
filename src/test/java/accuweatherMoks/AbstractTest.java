package accuweatherMoks;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

/**
 *
 */
public abstract class AbstractTest {
    static Properties properties = new Properties();
    private static InputStream configFile;
    private static int port = 8080;
    static WireMockServer wireMockServer = new WireMockServer();

    protected static final Logger logger = LoggerFactory.getLogger("MockTestsAccuweather");

    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/test/resources/accuweather.properties");
        properties.load(configFile);

        wireMockServer.start();

        logger.info("WireMockServer запущен, localhost = {}", port);
        configureFor("localhost", port);

    }

    public static String getBaseUrl() {
        return "http://localhost:8080/";
    }

    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
        logger.info("WireMockServer остановлен, localhost = {}", port);
    }

}
