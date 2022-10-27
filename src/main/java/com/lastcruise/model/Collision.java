package com.lastcruise.model;

import com.lastcruise.model.entity.Entity;
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
}
