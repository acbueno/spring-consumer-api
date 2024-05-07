package br.com.acbueno.consumer.api.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import br.com.acbueno.consumer.api.service.dto.ForecastVO;
import br.com.acbueno.consumer.api.service.exception.CityNotFoundException;
import br.com.acbueno.consumer.api.service.parse.City;
import br.com.acbueno.consumer.api.service.parse.Forecast;
import br.com.acbueno.consumer.api.service.parse.Weather;

@Service
public class ForecastService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${api.url.forecast}")
  private String apiUrlForecast;

  @Value("${api.url.geo.references.city}")
  private String apiUrlGeoReferenceCity;

  @Value("${api.id}")
  private String appId;

  @Value("${api.lang}")
  private String codeLang;

  @Value("${unit.code}")
  private String uniCode;

  public ForecastVO getForecast(String country, String state, String city)
      throws CityNotFoundException, UnsupportedEncodingException {
    Optional<City> cityData = getDataGeoReferenceFromApi(city).stream()
        .filter(
            c -> c.getCountry().equalsIgnoreCase(country) && c.getState().equalsIgnoreCase(state))
        .findFirst();

    if (cityData.isPresent()) {
      City cityInfo = cityData.get();
      Forecast dataFromApi = getDataForecastFromApi(cityInfo.getLon(), cityInfo.getLat());
      Weather weather = dataFromApi.getWeather().get(0);
      String hourSunrise = convertDateTime(dataFromApi.getSys().getSunrise());
      String hourSunset = convertDateTime(dataFromApi.getSys().getSunset());
      return createForecastVO(dataFromApi, hourSunrise, hourSunset, weather);
    } else {
      throw new CityNotFoundException(
          String.format("City not found for country %s and state %s", country, state));
    }
  }

  private List<City> getDataGeoReferenceFromApi(String city) {
    String encodedCity = UriUtils.encode(city, StandardCharsets.UTF_8);
    String url = apiUrlGeoReferenceCity + "?q=" + encodedCity + "&appId=" + appId;
    URI uri = URI.create(url);
    ResponseEntity<List<City>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<City>>() {});
    return response.getBody();
  }

  private Forecast getDataForecastFromApi(Double lon, Double lat) {
    //@formatter:off
    UriComponentsBuilder builder = UriComponentsBuilder
        .fromUriString(apiUrlForecast)
        .queryParam("lat", lat)
        .queryParam("lon", lon)
        .queryParam("appId", appId)
        .queryParam("lang", codeLang)
        .queryParam("units", uniCode);
    String url = builder.toUriString();
    //@formatter:on
    return restTemplate.getForObject(url, Forecast.class);
  }

  private String convertDateTime(Long time) {
    Instant instant = Instant.ofEpochSecond(time);
    LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
    String formattedDateTime = localDateTime.format(formatter);

    return formattedDateTime;
  }

  private ForecastVO createForecastVO(Forecast dataFromApi, String hourSunrise, String hourSusnet,
      Weather weather) {
    //@formatter:off
    ForecastVO forecastVO = ForecastVO.builder()
        .city(dataFromApi.getName())
        .description(weather.getDescription())
        .currentTemp(dataFromApi.getMain().getTemp())
        .tempMin(dataFromApi.getMain().getTempMin())
        .tempMax(dataFromApi.getMain().getTempMax())
        .winSpeed(dataFromApi.getWind().getSpeed())
        .sunrise(hourSunrise)
        .sunset(hourSusnet) 
        .humidity(dataFromApi.getMain().getHumidity())
        .build();
    //@formatter:on
    return forecastVO;
  }

}
