package com.lastcruise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@JsonPropertyOrder({"locations", "currentLocationName"})
public class GameMap {

  // list of locations
  private java.util.Map<String, Location> locations;
  @JsonIgnore
  private Location currentLocation;

  // Constructors
  public GameMap() {
    java.util.Map<String, Location> mapOfLocations = generateLocations();
    this.locations = mapOfLocations;
  }

  public void setStartLocation(Location startLocation) {
    currentLocation = startLocation;
  }

  // Business Methods
  private java.util.Map<String, Location> generateLocations() {

    java.util.Map<String, Location> stringGameLocationHashMap = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    // try with resources to automatically close the file
    try(InputStream jsonLocations = Thread.currentThread().getContextClassLoader().getResourceAsStream(
        "json/maps.json")) {
      List<Location> locationsDecoded = mapper.readValue(jsonLocations,
          new TypeReference<List<Location>>() {
          });

      for (Location location : locationsDecoded) {
        stringGameLocationHashMap.put(location.getName(), location);
      }

    } catch (Exception e) {
      System.out.println(e);
    }
    return stringGameLocationHashMap;
  }

  public java.util.Map<String, Location> getLocations() {
    return locations;
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
  }

}
