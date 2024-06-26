package accuweather;

import FominaKat.accuweather.DailyForecasts;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ForecastTest extends AbstractTest {

    @ParameterizedTest(name = "{index} - locationKey = {arguments}")
    @ValueSource(ints = {294463, 290396, 294021}) //
    @Owner("Fomina Kat")
    @DisplayName("Check one day daily")
    @Description("checking the weather for one day")
    @Story(value = "Forecast")
    void oneDayDailyTest(int location) {
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
    @Owner("Fomina Kat")
    @DisplayName("Check fifteen day daily")
    @Description("checking the weather for fifteen day")
    @Story(value = "Forecast")
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

    @ParameterizedTest(name = "{index} - locationKey = {arguments}")
    @ValueSource(ints = {294463, 290396, 294021})
    @Owner("Fomina Kat")
    @DisplayName("Check five day daily")
    @Description("checking the weather for five day")
    @Story(value = "Forecast")
    void fiveDayDaily1Test(int location) {
        DailyForecasts response = given()
                .queryParam("apikey", getApiKey())
                .pathParam("locationKey", location)
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/5day/{locationKey}")
                .then().log().body()
                .statusCode(200)
                .extract()
                .as(DailyForecasts.class);
        System.out.println();
    }
}
