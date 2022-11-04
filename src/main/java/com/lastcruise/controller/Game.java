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
import com.lastcruise.view.GamePanel;
import com.lastcruise.view.audio.SoundEffect;
import java.util.ConcurrentModificationException;
import java.util.Set;

public class Game {

    private Player player;
    private GameMap gameMap;
    private State state;
    private Inventory inventory;
    private GamePanel gamePanel;
    private SoundEffect soundEffect;
    private static final String STARTING_LOCATION = "BEACH";
    public Game(Player player) {
        this.gameMap = new GameMap();
        gameMap.setStartLocation(gameMap.getLocations().get(STARTING_LOCATION));

        this.state = State.TITLE;
        this.inventory = getCurrentLocationInventory();
//        this.gamePanel = new GamePanel();
        this.soundEffect = new SoundEffect();
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

    public boolean updateMap(int tileSize) {
        int tileX = player.getX() / tileSize;
        int tileY = player.getY() / tileSize;
        Location current = gameMap.getCurrentLocation();
        // checks if the tile is a transition tile and returns current map
        String newMap = current.checkForMapTransition(tileX, tileY, player.getDirection());
        if (!current.getName().equals(newMap)) {
            // change current location to new location
            gameMap.setCurrentLocation(gameMap.getLocations().get(newMap));
            // places the player at the entrance for the new map
            int[] entrance = gameMap.getCurrentLocation().getEntranceCoordinates(player.getDirection());
            player.setX(entrance[0] * tileSize);
            player.setY(entrance[1] * tileSize);
            // load the new map
            soundEffect.loadAndPlayFx("run");
            this.inventory = getCurrentLocationInventory();
            return true;
        }
        return false;
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
    public void pickupItem (String itemName){
        if (!itemName.equals("") && (player.getInventory().getInventory().size() < 8)) {
            System.out.println("Num of items: " + player.getInventory().getInventory().size());
            try {
                player.reduceStaminaPickUp();
                // remove the item from the location inventory and add it to the player inventory
                System.out.println(inventory.getInventory().toString());
                transferItemFromTo(inventory, player.getInventory(), itemName);
                soundEffect.loadAndPlayFx("pickup");
            } catch (InventoryEmptyException e) {
                System.out.println("Item " + itemName + " is not in inventory!");
            } catch (NoEnoughStaminaException e) {
                //throw new RuntimeException(e);
                System.out.println("Not enough stamina to pickup item!");
            }
        }
    }
    public void useItem(int index) {
        Item foundItem = null;
        for (Item item : getPlayerInventory().getInventory().values()) {
            if (item.getIndex() == index) {
                foundItem = item;
                System.out.println("Index: " + index + " Name: " + item.getName());
            }
        }
        if (foundItem != null) {
            String action = foundItem.checkEatOrDrop();
            if (action.equals("eat")) {
                try {
                    eatItem(foundItem.getName());
                    soundEffect.loadAndPlayFx("eat");
                } catch (InventoryEmptyException | ItemNotEdibleException |
                         ConcurrentModificationException e) {
                    //System.out.println(e);
                }
            } else if (action.equals("drop")) {
                try {
                    foundItem.updateItemLocation(player.getX(), player.getY());
                    transferItemFromTo(getPlayerInventory(), getCurrentLocationInventory(),
                        foundItem.getName());
                    soundEffect.loadAndPlayFx("drop");
                } catch (InventoryEmptyException |
                         ConcurrentModificationException |
                         NoEnoughStaminaException e) {
                    //System.out.println(e);
                }
            }
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
