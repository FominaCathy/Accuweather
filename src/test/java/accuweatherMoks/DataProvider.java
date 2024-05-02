package accuweatherMoks;


import FominaKat.accuweather.*;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataProvider {

    public static Stream<Arguments> dataDailyForecasts() {
        List<DayForecast> forecasts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            UnitTemperature min = new UnitTemperature(10.0 + i, "F", 18);
            UnitTemperature max = new UnitTemperature(15.0 + i, "F", 18);
            String date = String.format("0{%d}-05-2024", i + 1);
            forecasts.add(new DayForecast(date, new Temperature(min, max)));
        }
        return Stream.of(Arguments.of(294463, new DailyForecasts(forecasts)));

    }

    public static Stream<Arguments> dataLocationsCities() {
        return Stream.of(Arguments.of("Sochi",
                new Location("293687", "Sochi", "City")));
    }
}
