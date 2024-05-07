package br.com.acbueno.consumer.api.service.parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Main {

  @JsonProperty("temp")
  private Double temp;

  @JsonProperty("temp_min")
  private Double tempMin;

  @JsonProperty("temp_max")
  private Double tempMax;

  @JsonProperty("humidity")
  private Integer humidity;

}
