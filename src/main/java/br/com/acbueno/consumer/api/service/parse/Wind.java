package br.com.acbueno.consumer.api.service.parse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Wind {

  @JsonProperty("speed")
  private Double speed;

}
