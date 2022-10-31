package com.lastcruise.model;

import com.lastcruise.model.entity.Entity;
import com.lastcruise.model.entity.Player;
import com.lastcruise.model.tile.Tile;

public class Collision {

  private Tile[] tile;
  private int tileSize;
  private int[][] mapTileIndex;

  public Collision(int tileSize, int[][] mapTileIndex, Tile[] tile) {
    this.tileSize = tileSize;
    this.mapTileIndex = mapTileIndex;
    this.tile = tile;
  }

  // checks if players next tile will collide with a collision tile
  public boolean checkTile(int topRow, int bottomRow, int leftCol, int rightCol, String direction) {

    int tileNum1, tileNum2;

    switch (direction) {
      case "up":
        tileNum1 = mapTileIndex[leftCol][topRow];
        tileNum2 = mapTileIndex[rightCol][topRow];
        if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
          return true;
        }
        break;
      case "down":
        tileNum1 = mapTileIndex[leftCol][bottomRow];
        tileNum2 = mapTileIndex[rightCol][bottomRow];
        if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
          return true;
        }
        break;
      case "left":
        tileNum1 = mapTileIndex[leftCol][bottomRow];
        tileNum2 = mapTileIndex[leftCol][topRow];
        if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
          return true;
        }
        break;
      case "right":
        tileNum1 = mapTileIndex[rightCol][bottomRow];
        tileNum2 = mapTileIndex[rightCol][topRow];
        if (tile[tileNum1].isCollision() || tile[tileNum2].isCollision()) {
          return true;
        }
        break;
    }
    return false;
  }


  public String checkItem(Entity entity, boolean player, Inventory inventory) {

    String itemName = "";

    // get the list of object in the current map
    if (inventory != null) {
      for (Item item : inventory.getInventory().values()) {

        entity.setSolidAreaX((entity.getX() + entity.getSolidArea().x));
        entity.setSolidAreaY((entity.getY() + entity.getSolidArea().y));

        item.setSolidAreaX((item.getX() * tileSize) + item.getSolidArea().x);
        item.setSolidAreaY((item.getY() *tileSize) + item.getSolidArea().y);

        item.setSolidAreaX((item.getX() * tileSize) + item.getSolidArea().x);
        item.setSolidAreaY((item.getY() *tileSize) + item.getSolidArea().y);

        switch(entity.getDirection()) {
          case "up":
            entity.setSolidAreaY(entity.getSolidArea().y - entity.getSpeed());
            if(entity.getSolidArea().intersects(item.getSolidArea())){
              if(item.isCollision()){
                entity.setCollisionOn(true);
              }
              if (player) {
                itemName = item.getName();
              }
            }
            break;
          case "down":
            entity.setSolidAreaY(entity.getSolidArea().y + entity.getSpeed());
            if(entity.getSolidArea().intersects(item.getSolidArea())){
              if(item.isCollision()){
                entity.setCollisionOn(true);
              }
              if (player) {
                itemName = item.getName();
              }
            }
            break;
          case "left":
            entity.setSolidAreaX(entity.getSolidArea().x - entity.getSpeed());
            if(entity.getSolidArea().intersects(item.getSolidArea())){
              if(item.isCollision()){
                entity.setCollisionOn(true);
              }
              if (player) {
                itemName = item.getName();
              }
            }
            break;
          case "right":
            entity.setSolidAreaX(entity.getSolidArea().x + entity.getSpeed());
            if(entity.getSolidArea().intersects(item.getSolidArea())){
              if(item.isCollision()){
                entity.setCollisionOn(true);
              }
              if (player) {
                itemName = item.getName();
              }
            }
            break;
        }
        entity.setSolidAreaX(entity.getSolidAreaDefaultX());
        entity.setSolidAreaY(entity.getSolidAreaDefaultY());
        item.setSolidAreaX(item.getSolidAreaDefaultX());
        item.setSolidAreaY(item.getSolidAreaDefaultY());
      }
    }
    return itemName;
  }

}
