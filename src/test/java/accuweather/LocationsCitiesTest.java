package accuweather;

import FominaKat.accuweather.Location;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationsCitiesTest extends AbstractTest {

    @ParameterizedTest(name = "{index} - City: {0}, Country: {1}")
    @CsvSource(value = {
            "Samara, Russia",
            "Omsk, Russia",
            "Rome, Italy"})

    @Owner("Fomina Kat")
    @DisplayName("Check autocomplete city")
    @Description("checking autocomplete of the search text")
    @Story(value = "Locations")
    void autocompleteCityTest(String city, String country) {
        List<Location> listCity = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", city)
                .when()
                .get(getBaseUrl() + "/locations/v1/cities/autocomplete")
                .then().log().body()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("", Location.class);

        assertTrue(listCity.stream().map(Location::getCity).allMatch(e -> e.contains(city)));
        assertTrue(listCity.stream().map(Location::getType).allMatch(e -> e.equals("City")));
        assertTrue(listCity.stream().map(Location::getCountry).anyMatch(e -> e.equals(country)));
    }
}
