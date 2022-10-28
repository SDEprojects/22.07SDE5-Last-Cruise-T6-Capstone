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

//    int entitySolidAreaX = 0;
//    int entitySolidAreaY = 0;
//    int itemSolidAreaX = 0;
//    int itemSolidAreaY = 0;

    // get the list of object in the current map
    if (inventory != null) {
      for (Item item : inventory.getInventory().values()) {

        // Get the entity's solid area position
//        entitySolidAreaX = (entity.getX() + entity.getSolidArea().x) / 16;
//        entitySolidAreaY = (entity.getY() + entity.getSolidArea().y) / 16;

        entity.setSolidAreaX((entity.getX() + entity.getSolidArea().x));
        entity.setSolidAreaY((entity.getY() + entity.getSolidArea().y));

        System.out.println("Entity: x " + entity.getSolidArea().x);
        System.out.println("Entity: y " + entity.getSolidArea().y);

        // Get the item's solid are position
//        itemSolidAreaX = item.getX() + item.getSolidArea().x / 16;
//        itemSolidAreaY = item.getY() + item.getSolidArea().y / 16;

        item.setSolidAreaX((item.getX() * tileSize) + item.getSolidArea().x);
        item.setSolidAreaY((item.getY() *tileSize) + item.getSolidArea().y);

        System.out.println(item.getName() + " x: " +  item.getSolidArea().x);
        System.out.println(item.getName() + " y: " +  item.getSolidArea().y);

        switch(entity.getDirection()) {
          case "up":
            entity.setSolidAreaY(entity.getSolidArea().y - entity.getSpeed());
            if(entity.getSolidArea().intersects(item.getSolidArea())){
              System.out.println("Collision up + " + item.getName());
              System.out.println("Player solid area: " + entity.getSolidArea());
              System.out.println("Item solid area: " + item.getSolidArea());
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
              System.out.println("Collision down + " + item.getName());
              System.out.println("Player solid area: " + entity.getSolidArea());
              System.out.println("Item solid area: " + item.getSolidArea());
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
              System.out.println("Collision left + " + item.getName());
              System.out.println("Player solid area: " + entity.getSolidArea());
              System.out.println("Item solid area: " + item.getSolidArea());
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
              System.out.println("Collision right + " + item.getName());
              System.out.println("Player solid area: " + entity.getSolidArea());
              System.out.println("Item solid area: " + item.getSolidArea());
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
