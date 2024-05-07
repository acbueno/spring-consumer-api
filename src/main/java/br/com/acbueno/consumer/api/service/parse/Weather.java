package br.com.acbueno.consumer.api.service.parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Weather {

  @JsonProperty("description")
  private String description;

}
