package FominaKat.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecasts {
    @JsonProperty("DailyForecasts")
    private List<DayForecast> dailyForecasts;

    public DailyForecasts(List<DayForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    public DailyForecasts() {

    }


    public List<DayForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DayForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }
}
