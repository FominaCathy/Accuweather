package FominaKat.accuweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Location {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("LocalizedName")
    private String city;
    @JsonProperty("Type")
    private String type;

    private String country;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("Country")
    public void setCountry(Map<String, String> country) {
        this.country = country.get("LocalizedName");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
