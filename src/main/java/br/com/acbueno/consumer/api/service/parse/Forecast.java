package br.com.acbueno.consumer.api.service.parse;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Forecast {

  @JsonProperty("weather")
  private List<Weather> weather;

  @JsonProperty("main")
  private Main main;

  @JsonProperty("name")
  private String name;

  @JsonProperty("wind")
  private Wind wind;

  @JsonProperty("sys")
  private Sys sys;

}
