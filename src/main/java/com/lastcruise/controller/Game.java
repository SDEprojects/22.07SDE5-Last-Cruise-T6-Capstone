package com.lastcruise.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lastcruise.model.GameItems;
import com.lastcruise.model.GameMap;
import com.lastcruise.model.Inventory;
import com.lastcruise.model.Inventory.InventoryEmptyException;
import com.lastcruise.model.Item;
import com.lastcruise.model.Location;
import com.lastcruise.model.State;
import com.lastcruise.model.entity.Player;
import com.lastcruise.model.entity.Player.ItemNotEdibleException;
import com.lastcruise.model.entity.Player.NoEnoughStaminaException;
import java.util.Set;

public class Game {

    private Player player;

    private GameMap gameMap;

    private State state;

    private static final String STARTING_LOCATION = "BEACH";

    public Game() {

    }

    public Game(Player player) {
        this.gameMap = new GameMap();
        this.state = State.PLAY;
        gameMap.setStartLocation(gameMap.getLocations().get(STARTING_LOCATION));
    }

    public Game(String name){
        this.gameMap = new GameMap();
        this.state = State.PLAY;
        gameMap.setStartLocation(gameMap.getLocations().get(STARTING_LOCATION));
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }
    @JsonIgnore
    public Location getCurrentLocation() {
        return gameMap.getCurrentLocation();
    }

    @JsonIgnore
    public String getCurrentLocationName() {
        return gameMap.getCurrentLocation().getName();
    }

    @JsonIgnore
    public String getCurrentLocationDesc() {
        return gameMap.getCurrentLocation().getDescription();
    }

    @JsonIgnore
    public java.util.Map<String, Item> getCurrentLocationItems() {
        return gameMap.getCurrentLocation().getItems().getInventory();
    }

    @JsonIgnore
    public Inventory getCurrentLocationInventory() {
        return gameMap.getCurrentLocation().getItems();
    }

    @JsonIgnore
    public Inventory getPlayerInventory() {
        return player.getInventory();
    }

    @JsonIgnore
    public String getPlayerStamina() {
        return String.valueOf(player.getStamina());
    }


    public void transferItemFromTo(Inventory from, Inventory to, String itemName)
        throws InventoryEmptyException, NoEnoughStaminaException {
        if(!GameItems.GAME_ITEMS_HASHMAP.containsKey(itemName)){
            throw new InventoryEmptyException();
        }
        if (GameItems.GAME_ITEMS_HASHMAP.get(itemName).getEdible()) {
            Item removed = from.remove(itemName);
            to.add(itemName, removed);
        } else {
            player.reduceStaminaPickUp();
            Item removed = from.remove(itemName);
            to.add(itemName, removed);
        }
    }

    public void eatItem(String itemName) throws InventoryEmptyException, ItemNotEdibleException {
        player.consumeItem(itemName);
    }

    public boolean craftRaft() {
        java.util.Map<String, Item> currLocationInventory = getCurrentLocation().getItems().getInventory();
        Set<String> requiredForRaft = GameItems.GAME_ITEMS_HASHMAP.get("raft").getRequired();

        if (currLocationInventory.keySet()
            .containsAll(requiredForRaft)) {


            for (var requiredItem : requiredForRaft) {
                currLocationInventory.remove(requiredItem);
            }

            System.out.println("Building the raft");
            currLocationInventory.put("raft", GameItems.GAME_ITEMS_HASHMAP.get("raft"));
            return true;
        } else {
            System.out.println("You don't have all the required items to build the raft!");
        }
        return false;
    }

    public boolean escapeIsland(){
        return getCurrentLocationInventory().getInventory().get("raft") != null;
    }

    public  void playerSleep(){
        player.sleep();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState(){
        return state;
    }

}
