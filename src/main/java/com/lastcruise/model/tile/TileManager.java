package com.lastcruise.model.tile;

import com.lastcruise.model.GamePanel;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {

  private Tile[] tile;
  private int mapTileIndex[][];
  private int maxScreenCol;
  private int maxScreenRow;
  public TileManager(int maxScreenCol, int maxScreenRow) {
    tile = new Tile[10];
    this.maxScreenCol = maxScreenCol;
    this.maxScreenRow = maxScreenRow;
    mapTileIndex = new int[maxScreenCol][maxScreenRow];
    getTileImage();
    loadMap("/maps/beach.txt");
  }

  public void getTileImage() {
    try {
      tile[0] = new Tile();
      tile[0].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png")));

      tile[1] = new Tile();
      tile[1].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png")));
      tile[1].setCollision(true);

      tile[2] = new Tile();
      tile[2].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")));
      tile[2].setCollision(true);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void loadMap(String filePath) {
    try {
      InputStream inputStream = getClass().getResourceAsStream(filePath);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      int col = 0;
      int row = 0;
      while (col < maxScreenCol && row < maxScreenRow) {
        String line = reader.readLine();
        while (col < maxScreenCol) {
          String[] numbers = line.split(" ");
          int num = Integer.parseInt(numbers[col]);
          mapTileIndex[col][row] = num;
          col++;
        }
        if (col == maxScreenCol) {
          col = 0;
          row++;
        }
      }
      reader.close();
    } catch (Exception e){
      e.printStackTrace();
    }
  }
  public void draw(Graphics2D g2, int tileSize) {

    int col = 0;
    int row = 0;
    int x = 0;
    int y = 0;

    while (col < maxScreenCol && row < maxScreenRow) {
      int tileIndex = mapTileIndex[col][row];
      g2.drawImage(tile[tileIndex].getImage(), x, y, tileSize, tileSize, null);
      col++;
      x += tileSize;

      if (col ==  maxScreenCol) {
        col = 0;
        x = 0;
        row ++;
        y += tileSize;
      }
    }
  }

  public Tile[] getTile() {
    return tile;
  }

  public int[][] getMapTileIndex() {
    return mapTileIndex;
  }
}
