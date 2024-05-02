package accuweatherMoks;

import FominaKat.accuweather.DailyForecasts;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MockForecastTest extends AbstractTest {


    @ParameterizedTest(name = "{index} - locationKey = {arguments}")
    @MethodSource("accuweatherMoks.DataProvider#dataDailyForecasts")
    void tenDayDailyResponseTest(int location, DailyForecasts forecasts) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String urlRequest = "/forecasts/v1/daily/10day/" + location;

        stubFor(get(urlPathEqualTo(urlRequest))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(mapper.writeValueAsString(forecasts)))
        );


        logger.info("Установлена заглушка на запрос погоды на 10 дней");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + urlRequest);

        logger.info("создан http клиент");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo(urlRequest)));

        logger.info("проверка статуса ответа");
        assertEquals(200, response.getStatusLine().getStatusCode());

        DailyForecasts dailyForecasts = mapper.readValue(response.getEntity().getContent(), DailyForecasts.class);
        logger.info("проверка размера списка ответа");
        assertEquals(10, dailyForecasts.getDailyForecasts().size());

        //проверим, что мин температура не превышает максимальную
        logger.info("проверка что мин температура не превышает максимальную");
        assertTrue(dailyForecasts.getDailyForecasts().stream()
                .allMatch(e -> e.getTemperature().getMaximum().getValue() >= e.getTemperature().getMinimum().getValue()));
    }
}
