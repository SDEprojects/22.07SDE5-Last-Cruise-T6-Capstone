package com.lastcruise.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.HashMap;
import java.util.List;

@JsonTypeInfo(
    use = Id.NAME,
    include = As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @Type(value = CraftingLocation.class, name = "crafting"),
    @Type(value = Map.class, name = "normal")
})
public class Map {
    private String name;
    private String description;
    private HashMap<String, String> north;
    private HashMap<String, String> south;
    private HashMap<String, String> east;
    private HashMap<String, String> west;
    private Inventory items;
    private String filepath;

    // Constructor
    public Map() {
    }

    public String checkForMapTransition(int x, int y, String direction) {
        String xString = String.valueOf(x);
        String yString = String.valueOf(y);
        if (direction.equals("up") && north.get("x").equals(xString) && north.get("y").equals(yString)) {
            return north.get("name");
        } else if (direction.equals("down") && south.get("x").equals(xString) && south.get("y").equals(yString)) {
            return south.get("name");
        } else if (direction.equals("left") && west.get("x").equals(xString) && west.get("y").equals(yString)) {
            return west.get("name");
        } else if (direction.equals("right") && east.get("x").equals(xString) && east.get("y").equals(yString)) {
            return east.get("name");
        } else {
            return null;
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getNorth() {
        return north;
    }

    public void setNorth(HashMap<String, String> north) {
        this.north = north;
    }

    public HashMap<String, String> getSouth() {
        return south;
    }

    public void setSouth(HashMap<String, String> south) {
        this.south = south;
    }

    public HashMap<String, String> getEast() {
        return east;
    }

    public void setEast(HashMap<String, String> east) {
        this.east = east;
    }

    public HashMap<String, String> getWest() {
        return west;
    }

    public void setWest(HashMap<String, String> west) {
        this.west = west;
    }

    public Inventory getItems() {
        return items;
    }

    public void setItemNames(List<String> items) {

       Inventory result  =  new Inventory();

        for (String itemName : items) {
            result.add(itemName, GameItems.GAME_ITEMS_HASHMAP.get(itemName));
        }
        this.items = result;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setItems(Inventory items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "\nGameLocation{" +
            "\n    name='" + name + '\'' +
            ", \n   description='" + description + '\'' +
            ", \n   north='" + north + '\'' +
            ", \n   south='" + south + '\'' +
            ", \n   east='" + east + '\'' +
            ", \n   west='" + west + '\'' +
            ", \n   items=" + items +
            "\n }";
    }
}
