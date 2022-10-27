package com.lastcruise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@JsonPropertyOrder({"locations", "currentLocationName"})
public class GameMap {

  // list of locations
  private java.util.Map<String, Map> locations;
  @JsonIgnore
  private Map currentLocation;

  // Constructors
  public GameMap() {
    java.util.Map<String, Map> mapOfLocations = generateLocations();
    this.locations = mapOfLocations;
  }

  public void setStartLocation(Map startLocation) {
    currentLocation = startLocation;
  }

  // Business Methods
  private java.util.Map<String, Map> generateLocations() {

    java.util.Map<String, Map> stringGameLocationHashMap = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    // try with resources to automatically close the file
    try(InputStream jsonLocations = Thread.currentThread().getContextClassLoader().getResourceAsStream(
        "json/maps.json")) {
      List<Map> locationsDecoded = mapper.readValue(jsonLocations,
          new TypeReference<List<Map>>() {
          });

      for (Map location : locationsDecoded) {
        stringGameLocationHashMap.put(location.getName(), location);
      }

    } catch (Exception e) {
      System.out.println(e);
    }
    return stringGameLocationHashMap;
  }

  public java.util.Map<String, Map> getLocations() {
    return locations;
  }

  public void setLocations(java.util.Map<String, Map> locations) {
    this.locations = locations;
  }

  public Map getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(Map currentLocation) {
    this.currentLocation = currentLocation;
  }

  public void updateCurrentLocation(String[] command) throws InvalidLocationException {
    String newLocation = null;
    switch (command[1].toLowerCase()) {
      case "north": {
        newLocation = currentLocation.getNorth();
        break;
      }
      case "south": {
        newLocation = currentLocation.getSouth();
        break;
      }
      case "east": {
        newLocation = currentLocation.getEast();
        break;
      }
      case "west": {
        newLocation = currentLocation.getWest();
        break;
      }
    }
    if (locations.containsKey(newLocation)) {
      currentLocation = locations.get(newLocation);
    } else {
       throw new InvalidLocationException();
    }
  }

  public String getCurrentLocationName() {
    return currentLocation.getName();
  }

  public void setCurrentLocationName(String currentLocationName) {
    currentLocation = locations.get(currentLocationName);
  }

  public static class InvalidLocationException extends Throwable {

  }
}
