package br.com.acbueno.consumer.api.service.exception;

public class CityNotFoundException extends Exception {

  private static final long serialVersionUID = -6443367307137657720L;

  public CityNotFoundException(String message) {
    super(message);
  }

  public CityNotFoundException(String message, Throwable ex) {
    super(message, ex);
  }

}
