package com.lastcruise.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.lastcruise.model.Inventory;
import com.lastcruise.model.Item;
import com.lastcruise.model.Location;
import com.lastcruise.model.entity.Player;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void pickupItem() {

    Player player = new Player();
    Game game = new Game(player);
    game.setPlayer(player);

    Inventory inventory = player.getInventory();
    game.pickupItem("log");

  }
}