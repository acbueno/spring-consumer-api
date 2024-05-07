package br.com.acbueno.consumer.api.service.parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sys {

  @JsonProperty
  private long sunrise;

  @JsonProperty("sunset")
  private long sunset;
}
