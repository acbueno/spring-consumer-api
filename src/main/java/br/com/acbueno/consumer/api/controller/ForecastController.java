package br.com.acbueno.consumer.api.controller;

import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.acbueno.consumer.api.service.ForecastService;
import br.com.acbueno.consumer.api.service.dto.ForecastVO;
import br.com.acbueno.consumer.api.service.exception.CityNotFoundException;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

  @Autowired
  private ForecastService forecastService;

  @GetMapping("/{country}/{state}/{city}")
  public ResponseEntity<ForecastVO> getData(@PathVariable("country") String country,
      @PathVariable("state") String state, @PathVariable("city") String city)
      throws CityNotFoundException, UnsupportedEncodingException {
    return ResponseEntity.ok(forecastService.getForecast(country, state, city));
  }

}
