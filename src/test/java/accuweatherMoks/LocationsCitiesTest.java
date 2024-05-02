package accuweatherMoks;


import FominaKat.accuweather.Location;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationsCitiesTest extends AbstractTest {


    @ParameterizedTest(name = "{index} - city: {0}")
    @MethodSource("accuweatherMoks.DataProvider#dataLocationsCities")
    void autocompleteCityMockTest(String city, Location location) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        String urlRequest = "/locations/v1/cities/autocomplete";

        stubFor(get(urlPathEqualTo(urlRequest))
                .withQueryParam("q", equalTo(city))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(mapper.writeValueAsString(location))));

        logger.info("Установлена заглушка на автоподбор городов");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + urlRequest);
        URI uriOk = new URIBuilder(request.getURI())
                .addParameter("q", city)
                .build();
        request.setURI(uriOk);
        logger.info("создан http клиент ");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo(urlRequest)));
        logger.info("проверка статуса ответа");
        assertEquals(200, response.getStatusLine().getStatusCode());

        Location responseCity = mapper.readValue(response.getEntity().getContent(), Location.class);

        logger.info("проверка названия города и его типа в ответе");
        assertTrue(responseCity.getCity().contains(city));
        assertEquals("City", responseCity.getType());

    }
}
