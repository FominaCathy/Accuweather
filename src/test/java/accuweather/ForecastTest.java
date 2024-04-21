package accuweather;

import FominaKat.accuweather.DailyForecasts;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ForecastTest extends AbstractTest {

    @ParameterizedTest(name = "{index} - locationKey = {arguments}")
    @ValueSource(ints = {294463, 290396, 294021})
    void oneDayDailyTest(int location) throws JsonProcessingException {
        DailyForecasts forecasts = given()
                .queryParam("apikey", getApiKey())
                .pathParam("locationKey", location)
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/1day/{locationKey}")
                .then().log().body()
                .statusCode(200)
                .extract()
                .as(DailyForecasts.class);


        //проверим, что мин температура не превышает максимальную
        assertTrue(forecasts.getDailyForecasts().stream()
                .allMatch(e -> e.getTemperature().getMaximum().getValue() >= e.getTemperature().getMinimum().getValue()));

        // проверим, что единица измерения температуры "F"
        assertTrue(forecasts.getDailyForecasts().stream()
                .allMatch(e -> e.getTemperature().getMaximum().getUnit().equals("F")
                        && e.getTemperature().getMinimum().getUnit().equals("F")));

        //проверим дату.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertTrue(forecasts.getDailyForecasts().stream()
                .map(e -> e.getDate().split("T")[0])
                .toList().stream()
                .map(e -> LocalDate.parse(e, formatter))
                .allMatch(e -> e.equals(LocalDate.now()))
        );
    }

    @ParameterizedTest(name = "{index} - locationKey = {arguments}")
    @ValueSource(ints = {294463, 290396, 294021})
    void fifteenDayDailyResponse401Test(int location) {
        JsonPath response = given()
                .queryParam("apikey", getApiKey())
                .pathParam("locationKey", location)
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/15day/{locationKey}")
                .then().log().body()
                .statusCode(401)
                .extract()
                .jsonPath();

        assertEquals("Unauthorized", response.get("Code"));
        assertEquals("Api Authorization failed", response.get("Message"));

    }
}
