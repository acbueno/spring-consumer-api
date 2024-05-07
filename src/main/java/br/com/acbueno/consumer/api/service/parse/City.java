package br.com.acbueno.consumer.api.service.parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class City {

  @JsonProperty("name")
  private String name;

  @JsonProperty("lat")
  private double lat;

  @JsonProperty("lon")
  private double lon;

  @JsonProperty("country")
  private String country;

  @JsonProperty("state")
  private String state;

}
