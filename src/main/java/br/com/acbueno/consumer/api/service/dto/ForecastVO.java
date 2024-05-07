package br.com.acbueno.consumer.api.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForecastVO {

  @JsonProperty("city")
  private String city;

  @JsonProperty("description")
  private String description;

  @JsonProperty("current-temp")
  private Double currentTemp;

  @JsonProperty("temp-min")
  private Double tempMin;

  @JsonProperty("temp-max")
  private Double tempMax;

  @JsonProperty("win-speed")
  private Double winSpeed;

  @JsonProperty("sunrise")
  private String sunrise;

  @JsonProperty("sunset")
  private String sunset;

  @JsonProperty("humidity")
  Integer humidity;

}
