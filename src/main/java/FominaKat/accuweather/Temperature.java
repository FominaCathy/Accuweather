package FominaKat.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Temperature {
    @JsonProperty("Minimum")
    private UnitTemperature minimum;
    @JsonProperty("Maximum")
    private UnitTemperature maximum;


    @JsonProperty("Minimum")
    public UnitTemperature getMinimum() {
        return minimum;
    }

    @JsonProperty("Minimum")
    public void setMinimum(UnitTemperature minimum) {
        this.minimum = minimum;
    }

    @JsonProperty("Maximum")
    public UnitTemperature getMaximum() {
        return maximum;
    }

    @JsonProperty("Maximum")
    public void setMaximum(UnitTemperature maximum) {
        this.maximum = maximum;
    }
}
